import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author AKHILESH VIJAYAKUMAR
 */
public class StaffApp extends JFrame {
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton jbtnLOADSTAFFPHOTO;
private JButton jbtnFIRST;
private JButton jbtnINSERT;
private JButton jbtnLAST;
private JButton jbtnNEXT;
private JButton jbtnPREVIOUS;
private JTable  JTable_Staffs;
private JButton jbtnUPDATE;
private JButton jbtnDELETE;
//select sec section
private JLabel jlbSID;
private JTextField txt_sid;
private JLabel jlbSNAME;
private JTextField txt_sname;
private JLabel jlbBRANCH;
private JComboBox Jcbtxt_branch;
private JPanel jPanel1;
private JPanel jPnlButton;
private JScrollPane jScrollPane1;
private JLabel jlbSTAFFPHOTO;
private java.sql.Date sqladate;
private String ImgPath = null;
private int pos = 0,sid;
private String loginName,loginRole,usn,staffid;
private Connection myconnection;
private JFrame f;
private ArrayList<Staffs> list;
private ArrayList<Staffs> staffsList;
private ArrayList<String> al_branchlist;
private ResultSet rs=null;
private PreparedStatement pstmt=null;
private Staffs staffs;
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form StaffApp
  */
public StaffApp(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String staffid, String usn) {
this.loginName = loginName;
this.loginRole = loginRole;
this.myconnection = myconnection;
this.staffid=staffid;
this.sid = Integer.parseInt(staffid);
this.usn = usn;
}
public int getsid(){return this.sid;}
public String getstaffid(){return this.staffid;}
public Connection getConnection(){return this.myconnection;}
public String getLoginrole(){return this.loginRole;}
public String getLoginName(){return this.loginName;}
public String getusn(){return this.usn;}
  
public void CreateStaffForm()  { 
String lookAndFeel = null;
lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();         
try {
UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
}
catch (ClassNotFoundException ex) {
System.err.println("Couldn't find class for specified look and feel:"+ lookAndFeel);
System.err.println("Did you include the L&F library in the class path?");
System.err.println("Using the default look and feel.");
} 
catch (UnsupportedLookAndFeelException ex) {
System.err.println("Can't use the specified look and feel ("+ lookAndFeel+ ") on this platform.");
System.err.println("Using the default look and feel.");
}
catch (Exception ex) {
System.err.println("Couldn't get specified look and feel (" + lookAndFeel + "), for some reason.");
System.err.println("Using the default look and feel.");
ex.printStackTrace();
}    
     initComponents();
     Show_Staffs_In_JTable();

}  



public boolean GetStudBranch(){
myconnection = getConnection();
al_branchlist=new ArrayList<String>();
//List<String> ls = new ArrayList<String>(); 
//jComboBox.setModel(new DefaultComboBoxModel(al_subcodelist.toArray()));
String sql3 = "select distinct branch from branch order by branch";
try{
pstmt = myconnection.prepareStatement(sql3);
rs=pstmt.executeQuery();
while(rs.next()){
al_branchlist.add(rs.getString("BRANCH"));
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-get BRANCH lov"+e.getMessage());
return false;
}   
return true;
}
//select SID,SNAME,BRANCH from STAFF
// Check Input Fields
public boolean checkInputs(){
if(txt_sid.getText().equals("") || txt_sname.getText().equals("") )
return false;   
else
return true; 
}

  // Function To Resize The Image To Fit Into JLabel
public ImageIcon ResizeImage(String imagePath, byte[] pic){
ImageIcon myImage = null;       
if(imagePath != null){
myImage = new ImageIcon(imagePath);
}
else
{
myImage = new ImageIcon(pic);
}      
Image img = myImage.getImage();
Image img2 = img.getScaledInstance(jlbSTAFFPHOTO.getWidth(), jlbSTAFFPHOTO.getHeight(), Image.SCALE_SMOOTH);
ImageIcon image = new ImageIcon(img2);
return image;   
}
// Display Data In JTable:
//      1 - Fill ArrayList With The Data

public ArrayList<Staffs> getStaffsList(){
staffsList  = new ArrayList<Staffs>();
myconnection = getConnection();
String query = "SELECT SID,SNAME,BRANCH,IMAGE  FROM STAFF ORDER BY SID";
try {
pstmt=myconnection.prepareStatement(query);
rs=pstmt.executeQuery();  
if(rs==null) return null;
else
{
while(rs.next()){
staffs = new Staffs(rs.getInt("SID"),rs.getString("SNAME"),rs.getString("BRANCH"),rs.getBytes("IMAGE"));
staffsList.add(staffs);}
}
}
catch (SQLException ex) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+ex.getMessage());
return null;
}
return staffsList;
}
//      2 - Populate The JTable
public void Show_Staffs_In_JTable(){
if(getStaffsList()==null){
JOptionPane.showMessageDialog(null,"NO STAFF DATA");    
}
else
{    
list = getStaffsList();
DefaultTableModel model = (DefaultTableModel)JTable_Staffs.getModel();
// clear jtable content
//select sid,sname, branch from staff
model.setRowCount(0);
Object[] row = new Object[3];
for(int i = 0; i < list.size(); i++){
row[0] = list.get(i).getsid();
row[1] = list.get(i).getsname();
row[2] = list.get(i).getbranch();
model.addRow(row);
}
}
}
// Show Data In Inputs
public void ShowItem(int index){
System.out.println("Showitem entered:index="+index);   
txt_sid.setText(String.valueOf(list.get(index).getsid()));
txt_sname.setText(list.get(index).getsname());
Jcbtxt_branch.setSelectedItem(list.get(index).getbranch());
if(list.get(index).getImage()!=null)
jlbSTAFFPHOTO.setIcon(ResizeImage(null,list.get(index).getImage()));
else{
jlbSTAFFPHOTO.setBackground(new java.awt.Color(204, 255, 255));
jlbSTAFFPHOTO.setOpaque(true);
jlbSTAFFPHOTO.setIcon(null);
}
}

private void initComponents() {
jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 204));
jPanel1.setLayout(new GridLayout(1,6,10,10));
jlbSID = new JLabel();
jlbSID.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbSID.setText("STAFF ID:");
txt_sid = new JTextField(5);
txt_sid.setFont(new java.awt.Font("Tahoma", 1, 14)); 
txt_sid.setPreferredSize(new java.awt.Dimension(24, 20));
jlbSNAME = new JLabel();
jlbSNAME.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbSNAME.setText("STAFF NAME:");
txt_sname = new JTextField(25);
txt_sname.setFont(new java.awt.Font("Tahoma", 1, 14)); 
txt_sname.setPreferredSize(new java.awt.Dimension(24, 20));
jlbBRANCH = new JLabel();
jlbBRANCH.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbBRANCH.setText("BRANCH:");
if(GetStudBranch()){
// lov to be implemented  
Jcbtxt_branch = new JComboBox();
Jcbtxt_branch.setModel(new DefaultComboBoxModel(al_branchlist.toArray()));
}
else
{
JOptionPane.showMessageDialog(null,"Student subcode is invalid");
//System.exit(0);
}
Jcbtxt_branch.setFont(new java.awt.Font("Tahoma", 1, 14)); 
Jcbtxt_branch.setPreferredSize(new java.awt.Dimension(24, 20));
/****************************************************/
JPanel jPanelIMAGE = new JPanel(new GridLayout(2,1,10,10));
jlbSTAFFPHOTO = new JLabel();
jlbSTAFFPHOTO.setPreferredSize(new java.awt.Dimension(50, 50));
jlbSTAFFPHOTO.setBackground(new java.awt.Color(64, 64, 64));
jlbSTAFFPHOTO.setOpaque(true);
jbtnLOADSTAFFPHOTO = new JButton();
jbtnLOADSTAFFPHOTO.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnLOADSTAFFPHOTO.setIcon(new ImageIcon(getClass().getResource("Renew.png"))); 
jbtnLOADSTAFFPHOTO.setText("LOAD IMAGE");
jbtnLOADSTAFFPHOTO.setIconTextGap(15);
jbtnLOADSTAFFPHOTO.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
Btn_Choose_ImageActionPerformed(evt);
}
});
jPanel1.add(jlbSID);
jPanel1.add(txt_sid);
jPanel1.add(jlbSNAME);
jPanel1.add(txt_sname);
jPanel1.add(jlbBRANCH);
jPanel1.add(Jcbtxt_branch);
jPanelIMAGE.add(jlbSTAFFPHOTO);
jPanelIMAGE.add(jbtnLOADSTAFFPHOTO);
/**************************/
jbtnUPDATE = new JButton();
jbtnUPDATE.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnUPDATE.setIcon(new ImageIcon(getClass().getResource("Renew.png"))); 
jbtnUPDATE.setText("UPDATE");
jbtnUPDATE.setIconTextGap(15);
jbtnDELETE = new JButton();
jbtnDELETE.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnDELETE.setIcon(new ImageIcon(getClass().getResource("delete.png"))); 
jbtnDELETE.setText("DELETE");
jbtnDELETE.setIconTextGap(15);
jbtnINSERT = new JButton();
jbtnINSERT.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnINSERT.setIcon(new ImageIcon(getClass().getResource("add.png"))); 
jbtnINSERT.setText("Insert");
jbtnINSERT.setIconTextGap(15);
jbtnFIRST = new JButton();
jbtnFIRST.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnFIRST.setIcon(new ImageIcon(getClass().getResource("first.png"))); 
jbtnFIRST.setText("First");
jbtnFIRST.setIconTextGap(15);
jbtnPREVIOUS = new JButton();
jbtnPREVIOUS.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnPREVIOUS.setIcon(new ImageIcon(getClass().getResource("previous.png"))); 
jbtnPREVIOUS.setText("Previous");
jbtnPREVIOUS.setIconTextGap(15);
jbtnLAST = new JButton();
jbtnLAST.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnLAST.setIcon(new ImageIcon(getClass().getResource("last.png"))); 
jbtnLAST.setText("Last");
jbtnLAST.setIconTextGap(15);
jbtnNEXT = new JButton();
jbtnNEXT.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnNEXT.setIcon(new ImageIcon(getClass().getResource("next.png"))); 
jbtnNEXT.setText("Next");
jbtnNEXT.setIconTextGap(15);
jPnlButton = new JPanel();
jPnlButton.setBackground(new java.awt.Color(51, 102, 0));
jPnlButton.setLayout(new GridLayout(7,1,25,25));
jPnlButton.add(jbtnINSERT);
jPnlButton.add(jbtnUPDATE);
jPnlButton.add(jbtnDELETE);
jPnlButton.add(jbtnFIRST);
jPnlButton.add(jbtnNEXT);
jPnlButton.add(jbtnPREVIOUS);
jPnlButton.add(jbtnLAST);
JPanel jPnlscrtb = new JPanel();
jPnlscrtb.setLayout(new GridLayout(1,1,20,20));
jScrollPane1 = new JScrollPane();
JTable_Staffs = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},
//SELECT SID,SNAME,BRANCH from STAFF
new String [] {"STAFF ID","STAFF NAME","BRANCH"});
JTable_Staffs.setModel(MytableModel);
JTableHeader header = JTable_Staffs.getTableHeader();
header.setUpdateTableInRealTime(true);
jPnlscrtb.add(jScrollPane1);
JTable_Staffs.setSelectionForeground(Color.white);
JTable_Staffs.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_Staffs);
f=new JFrame();	
f.add(jPanel1, BorderLayout.NORTH);
f.add(jPnlscrtb, BorderLayout.CENTER);
f.add(jPnlButton, BorderLayout.WEST);
f.add(jPanelIMAGE, BorderLayout.EAST);

f.setTitle("Student Attendance Application");
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //exit upon closing
//get the dimension of the screen
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
int screenWidth =screenSize.width;
int screenHeight =screenSize.height;
//Get the Dimension of the Frame
Dimension frameSize = f.getSize();
int x =(screenWidth-frameSize.width)/2;
int y =(screenHeight-frameSize.height)/2;
f.setLocation(x,y); //set the frame to the center of the screen
f.setMinimumSize(new Dimension(1200, 600));
f.setPreferredSize(new Dimension(1200, 600));
f.setSize(1200,600);
f.setLocationRelativeTo(null); 
f.pack();
f.setVisible(true); 


JTable_Staffs.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseClicked(java.awt.event.MouseEvent evt) {
JTable_SectionsMouseClicked(evt);
}
});

jbtnUPDATE.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnUPDATEActionPerformed(evt);
}
});

jbtnDELETE.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnDELETEActionPerformed(evt);
}
});

jbtnINSERT.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnINSERTActionPerformed(evt);
}
});
jbtnFIRST.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnFIRSTActionPerformed(evt);
}
});
jbtnPREVIOUS.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnPREVIOUSActionPerformed(evt);
}
});
jbtnLAST.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnLASTActionPerformed(evt);
}
});
jbtnNEXT.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnNEXTActionPerformed(evt);
}
});
}
// </editor-fold>//GEN-END:initComponents

public boolean GetSID(){
myconnection = getConnection();
String sql1 = "select SID from STAFF where SID=?";
try{
pstmt = myconnection.prepareStatement(sql1);
pstmt.setInt(1,Integer.parseInt(txt_sid.getText()));
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
return true;
else
return false; 
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-getSID ERROR"+e.getMessage());
return false;
}   
}
//GEN-LAST:event_Btn_Choose_ImageActionPerformed

private void jbtnINSERTActionPerformed(java.awt.event.ActionEvent evt){
if(checkInputs()){
if(GetSID()){    
try{
myconnection = getConnection();
pstmt = myconnection.prepareStatement("INSERT INTO STAFF values(?,?,UPPER(?),?)");
pstmt.setInt(1,Integer.parseInt(txt_sid.getText()));
pstmt.setString(2, txt_sname.getText());
pstmt.setString(3, (String)Jcbtxt_branch.getSelectedItem());
InputStream img =null;
if(ImgPath!=null)
{
img = new FileInputStream(new File(ImgPath));
}
int rowsUpdated;
pstmt.setBlob(4, img);
rowsUpdated=pstmt.executeUpdate();
//System.out.println("INSERT-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Staffs_In_JTable();
JOptionPane.showMessageDialog(null, "Data Inserted");
}
catch (Exception ex) 
{
JOptionPane.showMessageDialog(null,ex.getMessage());
}
}
else
{
JOptionPane.showMessageDialog(null, "STAFF ID already exists, DUPLICATION not allowed");
}
}
else
{
JOptionPane.showMessageDialog(null, "One Or More Field Are Empty");
}
System.out.println("STAFF => "+txt_sid.getText());
System.out.println("Image => "+ImgPath);
}

private void jbtnUPDATEActionPerformed(java.awt.event.ActionEvent evt) {
if(checkInputs()){
String UpdateQuery = null;
myconnection = getConnection();
if(ImgPath == null){
UpdateQuery = "update staff set sname=?, branch=upper(?) where sid=?";
try{
pstmt = myconnection.prepareStatement(UpdateQuery);
pstmt.setString(1, txt_sname.getText());
pstmt.setString(2, (String)Jcbtxt_branch.getSelectedItem());
String s_ssid=txt_sid.getText();
pstmt.setInt(3,Integer.parseInt(txt_sid.getText()));
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
JOptionPane.showMessageDialog(null,"Not Updated, Invalid staff id="+s_ssid);
else
{
System.out.println("UPDATE-1-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Staffs_In_JTable();
JOptionPane.showMessageDialog(null, "Only Staffs name or branch Updated for staffid="+s_ssid);
}
}
catch (SQLException ex) {
System.err.println("SQL Exception-01 Occurred for the UPDATE OF STAFF");
ex.printStackTrace();
}
}
else
{
// update with image
UpdateQuery = "update staff set sname=?,branch=UPPER(?),image=? where sid=?";
try{
InputStream img = new FileInputStream(new File(ImgPath));
System.out.println("Image path"+ImgPath);
pstmt = myconnection.prepareStatement(UpdateQuery);
pstmt.setString(1, txt_sname.getText());
pstmt.setString(2, (String)Jcbtxt_branch.getSelectedItem());
pstmt.setBlob(3,img);
String s_ssid=txt_sid.getText();
pstmt.setInt(4,Integer.parseInt(txt_sid.getText()));
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
JOptionPane.showMessageDialog(null,"Not Updated, Invalid staff id="+s_ssid);
else
{
System.out.println("UPDATE-2-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Staffs_In_JTable();
JOptionPane.showMessageDialog(null, "Staffs name or branch Updated along with photo for staffid="+s_ssid);
}
}
catch(IOException ex) {
System.err.println("I/O exception image file opening error");
ex.printStackTrace();
} 
catch (SQLException ex) {
	System.err.println("SQL Exception-02 Occurred for the UPDATE OF STAFF");
	ex.printStackTrace();
}	
}
}
else	
{
JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty Or Wrong");
}
}//GEN-LAST:event_jbtnUPDATEActionPerformed



// Button Delete The Data From MySQL Database
private void jbtnDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDELETEActionPerformed
if(!txt_sid.getText().equals("")){
try {
myconnection = getConnection();
pstmt = myconnection.prepareStatement("DELETE STAFF WHERE SID=?");
String s_ssid=txt_sid.getText();
pstmt.setInt(1,Integer.parseInt(txt_sid.getText()));
int rowsUpdated=pstmt.executeUpdate();
System.out.println("DELETE-Commiting data here...."+rowsUpdated);
if (rowsUpdated==0)
JOptionPane.showMessageDialog(null, "Invalid StaffId:No records deleted");
else
{
myconnection.commit();
Show_Staffs_In_JTable();
JOptionPane.showMessageDialog(null, "Deleted Staff with SID ="+s_ssid);
jlbSTAFFPHOTO.setIcon(null);
}
}
catch (SQLException ex) {
System.err.println("SQL Exception-01 Occurred for the DELETE OF SCOURSE");
ex.printStackTrace();
JOptionPane.showMessageDialog(null, "Staffs Not Deleted");
}
}
else{
JOptionPane.showMessageDialog(null, "Staffs Not Deleted : No Id To Delete");
}

}//GEN-LAST:event_jbtnDELETEActionPerformed
private void JTable_SectionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_SectionsMouseClicked
int index = JTable_Staffs.getSelectedRow();
ShowItem(index);
}//GEN-LAST:event_JTable_SectionsMouseClicked

// Button First Show The First Record
private void jbtnFIRSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFIRSTActionPerformed
pos = 0;
System.out.println("FIRST:"+pos);
ShowItem(pos);
}//GEN-LAST:event_jbtnFIRSTActionPerformed
// Button Last Show The Last Record
private void jbtnLASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLASTActionPerformed
pos = list.size()-1;
System.out.println("LAST:"+pos);
ShowItem(pos);
}//GEN-LAST:event_jbtnLASTActionPerformed
// Button Next Show The Next Record
private void jbtnNEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNEXTActionPerformed
pos++;
if(pos >= list.size()){
pos = list.size()-1;
}
System.out.println("NEXT:"+pos);
ShowItem(pos);
}//GEN-LAST:event_jbtnNEXTActionPerformed
// Button Previous Show The Previous Record
private void jbtnPREVIOUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPREVIOUSActionPerformed
pos--;

if(pos < 0){
pos = 0;
}
System.out.println("Previous:"+pos);
ShowItem(pos);
}




private void Btn_Choose_ImageActionPerformed(java.awt.event.ActionEvent evt) {
String path=null;
ImgPath=null;
JFileChooser file = new JFileChooser();
file.setCurrentDirectory(new File(System.getProperty("user.dir")));
FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images", "jpg","png","gif","tiff","bmp");
file.addChoosableFileFilter(filter);
int result = file.showSaveDialog(null);
if(result == JFileChooser.APPROVE_OPTION){
File selectedFile = file.getSelectedFile();
path = selectedFile.getAbsolutePath();
jlbSTAFFPHOTO.setIcon(ResizeImage(path, null));
ImgPath = path;
}
else{
System.out.println("No File Selected");
}
/**************************************************************/
}
}
