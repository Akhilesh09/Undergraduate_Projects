import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
/**
 *
 * @author AKHILESH
 */
public class ScourseApp{
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton Btn_Choose_Image;
private JButton jbtnFIRST;
private JButton jbtnINSERT;
private JButton jbtnLAST;
private JButton jbtnNEXT;
private JButton jbtnPREVIOUS;
private JTable  JTable_Scourses;
private JButton jbtnUPDATE;
private JButton jbtnDELETE;
//select sec section
private JLabel jlbUSN;
private JComboBox Jcbtxt_usn;
private JLabel jlbSUBCODE;
private JComboBox Jcbtxt_subcode;
private JPanel jPanel1;
private JPanel jPnlButton;
private JScrollPane jScrollPane1;
private JLabel lbl_image;
private java.sql.Date sqladate;
private JFrame f;
private String ImgPath = null;
private int pos = 0,sid;
private String loginName,loginRole,usn,staffid;
private Connection myconnection;
private PreparedStatement pstmt;
private ResultSet rs=null;
private Scourse scourse;
private ArrayList<Scourse> scourseList, list;
private ArrayList<String> al_subcodelist,al_studentlist;
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form ScourseApp
  */
public ScourseApp(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String staffid, String usn) {
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

public void CreateSCourseForm()  {  
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
Show_Scourse_In_JTable();
}  

//select usn,sub_code from SCOURSE
// Check Input Fields
public boolean checkInputs(){
if((String)Jcbtxt_usn.getSelectedItem() == null && (String)Jcbtxt_subcode.getSelectedItem() == null)
return false;   
else
return true; 
}

public boolean GetStudentUsn(){
myconnection = getConnection();
al_studentlist=new ArrayList<String>();
String sql4 = "select distinct USN from STUDENT where STATUS='AC' order by usn";
try{
pstmt = myconnection.prepareStatement(sql4);
rs=pstmt.executeQuery();
while(rs.next()){
al_studentlist.add(rs.getString("USN"));
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-get STUDENT USN lov"+e.getMessage());
return false;
}   
return true;
}

public boolean GetStaffSubCode(){
myconnection = getConnection();
al_subcodelist=new ArrayList<String>();
//List<String> ls = new ArrayList<String>(); 
//jComboBox.setModel(new DefaultComboBoxModel(al_subcodelist.toArray()));
String sql2 = "select distinct sub_code from subjects order by sub_code";
try{
pstmt = myconnection.prepareStatement(sql2);
rs=pstmt.executeQuery();
while(rs.next()){
al_subcodelist.add(rs.getString("SUB_CODE"));
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-get subject code lov"+e.getMessage());
return false;
}   
return true;
}
// Display Data In JTable:
//      1 - Fill ArrayList With The Data

public ArrayList<Scourse> getScourseList(){
scourseList  = new ArrayList<Scourse>();
Connection con = getConnection();
String query = "SELECT A.USN, A.SUB_CODE FROM SCOURSE A,STUDENT B WHERE A.USN=B.USN AND UPPER(B.STATUS)='AC' ORDER BY USN";
PreparedStatement pstmt;
try {
pstmt=con.prepareStatement(query);
ResultSet rs=pstmt.executeQuery();  

if(rs==null) return null;
else
{
while(rs.next()){
scourse = new Scourse(rs.getString("USN"),rs.getString("SUB_CODE"));
scourseList.add(scourse);}
}
}
catch (SQLException ex) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+ex.getMessage());
return null;
}
return scourseList;
}
//      2 - Populate The JTable
public void Show_Scourse_In_JTable(){
if(getScourseList()==null){
JOptionPane.showMessageDialog(null,"NO student course DATA");    
}
else
{      
list = getScourseList();
DefaultTableModel model = (DefaultTableModel)JTable_Scourses.getModel();
// clear jtable content
//select usn,sub_code from scourse
model.setRowCount(0);
Object[] row = new Object[2];
for(int i = 0; i < list.size(); i++){
row[0] = list.get(i).getusn();
row[1] = list.get(i).getsubcode();
model.addRow(row);
}
}
}
// Show Data In Inputs
public void ShowItem(int index){
Jcbtxt_usn.setSelectedItem(getScourseList().get(index).getusn());
Jcbtxt_subcode.setSelectedItem(getScourseList().get(index).getsubcode());
}
private void initComponents() {

jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 153));
jPanel1.setLayout(new GridLayout(1,4,25,25));
jlbUSN = new JLabel();
jlbUSN.setFont(new java.awt.Font("Tahoma", 1, 14));
jlbUSN.setText("USN:");
if(GetStudentUsn()){
// lov to be implemented  
Jcbtxt_usn = new JComboBox();
Jcbtxt_usn.setModel(new DefaultComboBoxModel(al_studentlist.toArray()));
}
else
{
JOptionPane.showMessageDialog(null,"Student usn is invalid");
//System.exit(0);
}
Jcbtxt_usn.setFont(new java.awt.Font("Tahoma", 1, 14));
Jcbtxt_usn.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbUSN);
jPanel1.add(Jcbtxt_usn);
jlbSUBCODE = new JLabel();
jlbSUBCODE.setFont(new java.awt.Font("Tahoma", 1, 14));
jlbSUBCODE.setText("SUBJECT CODE:");
if(GetStaffSubCode()){
// lov to be implemented  
Jcbtxt_subcode = new JComboBox();
Jcbtxt_subcode.setModel(new DefaultComboBoxModel(al_subcodelist.toArray()));
}
else
{
JOptionPane.showMessageDialog(null,"Student subcode is invalid");
//System.exit(0);
}
Jcbtxt_subcode.setFont(new java.awt.Font("Tahoma", 1, 14));
Jcbtxt_subcode.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbSUBCODE);
jPanel1.add(Jcbtxt_subcode);
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
jPnlButton.setLayout(new GridLayout(1,7,20,20));
jPnlButton.setBackground(new java.awt.Color(255, 153, 51));
jPnlButton.add(jbtnINSERT);
jPnlButton.add(jbtnUPDATE);
jPnlButton.add(jbtnDELETE);
jPnlButton.add(jbtnFIRST);
jPnlButton.add(jbtnNEXT);
jPnlButton.add(jbtnPREVIOUS);
jPnlButton.add(jbtnLAST);
JPanel jPnlscrtb = new JPanel();
jPnlscrtb.setLayout(new GridLayout(1,1,12,12));
jScrollPane1 = new JScrollPane();
JTable_Scourses = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},
//SELECT USN,SUB_CODE from SCOURSE
new String [] {"USN","SUBJECT CODE"});
JTable_Scourses.setModel(MytableModel);
JTableHeader header = JTable_Scourses.getTableHeader();
header.setUpdateTableInRealTime(true);
jPnlscrtb.add(jScrollPane1);
JTable_Scourses.setSelectionForeground(Color.white);
JTable_Scourses.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_Scourses);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 16));
jlbaptitle.setText("SUBJECT ALLOTMENT TO STUDENT FORM");
jPanelptitle.add(jlbaptitle);
JPanel jPanel2N = new JPanel();
jPanel2N.setLayout(new GridLayout(3,1,20,20));
f=new JFrame();
jPanel2N.add(jPanelptitle);
jPanel2N.add(jPnlButton);
jPanel2N.add(jPanel1);
JPanel jPanelIns = new JPanel();
jPanelIns.setBackground(new java.awt.Color(255, 255,153));
JLabel jlbIns = new JLabel();
jlbIns.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbIns.setText("IMPORTANT NOTE: SUBJECT AND STUDENT TABLE DATA MUST BE SETUP PRIOR TO USING THIS FORM");
jPanelIns.add(jlbIns);
f.add(jPanel2N, BorderLayout.NORTH);
//f.add(jPanel1, BorderLayout.CENTER);
f.add(jPnlscrtb, BorderLayout.CENTER);
f.add(jPanelIns, BorderLayout.SOUTH);
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
f.setMinimumSize(new Dimension(1000, 500));
f.setPreferredSize(new Dimension(1300, 500));
f.setSize(1300,500);
f.setLocationRelativeTo(null); 
f.pack();
f.setVisible(true); 
JTable_Scourses.addMouseListener(new java.awt.event.MouseAdapter() {
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


public boolean GetSCOURSE(){
myconnection = getConnection();
String sql1 = "select USN,SUB_CODE from SCOURSE where USN=? AND SUB_CODE=?";
try{
pstmt = myconnection.prepareStatement(sql1);
pstmt.setString(1, (String)Jcbtxt_usn.getSelectedItem());
pstmt.setString(2, (String)Jcbtxt_subcode.getSelectedItem());
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
return true;
else
return false; 
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-getSCOURSE ERROR"+e.getMessage());
return false;
}   
}

private void jbtnINSERTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnINSERTActionPerformed
if(checkInputs()){
if(GetSCOURSE()){    
try {
myconnection = getConnection();
pstmt = myconnection.prepareStatement("INSERT INTO SCOURSE values(UPPER(?),UPPER(?))");
////SELECT USN,SUB_CODE FROM SCOURSE
pstmt.setString(1, (String)Jcbtxt_usn.getSelectedItem());
pstmt.setString(2, (String)Jcbtxt_subcode.getSelectedItem());
int rowsUpdated=pstmt.executeUpdate();
System.out.println("INSERT-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Scourse_In_JTable();
JOptionPane.showMessageDialog(null, "Student Course Data Inserted");
}
catch (Exception ex) {
JOptionPane.showMessageDialog(null, ex.getMessage());
}
}
else
{
JOptionPane.showMessageDialog(null, "Student Course Data already exists, Duplication not allowed");
}
}
else
{
JOptionPane.showMessageDialog(null, "One Or More Field Are Empty");
}
System.out.println("SCOURSE => "+(String)Jcbtxt_usn.getSelectedItem());
//System.out.println("Image => "+ImgPath);
}//GEN-LAST:event_jbtnINSERTActionPerformed

private void jbtnUPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUPDATEActionPerformed
if(checkInputs()){
String UpdateQuery = null;
myconnection = getConnection();
// update without image
UpdateQuery = "UPDATE SCOURSE SET SUB_CODE = UPPER(?) WHERE UPPER(USN) = UPPER(?) ";
try {
pstmt = myconnection.prepareStatement(UpdateQuery);
////SELECT USN,SUB_CODE FROM SCOURSE
pstmt.setString(2,(String) Jcbtxt_usn.getSelectedItem());
pstmt.setString(1, (String)Jcbtxt_subcode.getSelectedItem());
int rowsUpdated=pstmt.executeUpdate();
System.out.println("UPDATE-1-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Scourse_In_JTable();
JOptionPane.showMessageDialog(null, "Scourse Updated");
}

catch (SQLException ex) {
	System.err.println("SQL Exception-02 Occurred for the UPDATE OF SCOURSE");
	ex.printStackTrace();
}
}
else
{
JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty Or Wrong");
}
}//GEN-LAST:event_jbtnUPDATEActionPerformed



// Button Delete The Data From MySQL Database
private void jbtnDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDELETEActionPerformed
if(!Jcbtxt_usn.getSelectedItem().equals("")){
try {
myconnection = getConnection();
pstmt = myconnection.prepareStatement("DELETE SCOURSE WHERE UPPER(USN)=UPPER(?)");
pstmt.setString(1, (String)Jcbtxt_usn.getSelectedItem());
int rowsUpdated=pstmt.executeUpdate();
System.out.println("DELETE-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Scourse_In_JTable();
JOptionPane.showMessageDialog(null, "Scourse Deleted");
}
catch (SQLException ex) {
System.err.println("SQL Exception-01 Occurred for the DELETE OF SCOURSE");
ex.printStackTrace();
JOptionPane.showMessageDialog(null, "Scourse Not Deleted");
}
}
else{
JOptionPane.showMessageDialog(null, "Scourse Not Deleted : No Id To Delete");
}

}//GEN-LAST:event_jbtnDELETEActionPerformed
private void JTable_SectionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_SectionsMouseClicked
int index = JTable_Scourses.getSelectedRow();
ShowItem(index);
}//GEN-LAST:event_JTable_SectionsMouseClicked

// Button First Show The First Record
private void jbtnFIRSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFIRSTActionPerformed
pos = 0;
ShowItem(pos);
}//GEN-LAST:event_jbtnFIRSTActionPerformed
// Button Last Show The Last Record
private void jbtnLASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLASTActionPerformed
pos = getScourseList().size()-1;
ShowItem(pos);
}//GEN-LAST:event_jbtnLASTActionPerformed
// Button Next Show The Next Record
private void jbtnNEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNEXTActionPerformed
pos++;
if(pos >= getScourseList().size()){
pos = getScourseList().size()-1;
}
ShowItem(pos);
}//GEN-LAST:event_jbtnNEXTActionPerformed
// Button Previous Show The Previous Record
private void jbtnPREVIOUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPREVIOUSActionPerformed
pos--;
if(pos < 0){
pos = 0;
}
ShowItem(pos);
}//GEN-LAST:event_jbtnPREVIOUSActionPerformed
/**************************************************************/
}
