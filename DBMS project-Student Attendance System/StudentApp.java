import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
/**
 *
 * @author AKHILESH VIJAYAKUMAR
 */
public class StudentApp  {
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton Btn_Choose_Image;
private JButton jbtnFIRST;
private JButton jbtnINSERT;
private JButton jbtnLAST;
private JButton jbtnNEXT;
private JButton jbtnPREVIOUS;
private JTable  JTable_Students;
private JButton jbtnUPDATE;
private JButton jbtnDELETE;
//select sec section
private JLabel jlbUSN;
private JTextField txt_usn;
private JLabel jlbNAME;
private JTextField txt_name;
private JLabel jlbSEC;
private JComboBox Jcbtxt_sec;
private JLabel jlbBRANCH;
private JComboBox Jcbtxt_branch;
private JLabel jlbSTATUS;
private JComboBox Jcbtxt_status;
private JPanel jPanel1;
private JPanel jPnlButton;
private JScrollPane jScrollPane1;
private JLabel lbl_image;
private java.sql.Date sqladate;

private String ImgPath = null;
private int pos = 0,sid;
private String loginName,loginRole,usn,staffid;
private Connection myconnection;
private PreparedStatement pstmt;
private ResultSet rs;
private JFrame f;
private ArrayList<String> al_branchlist,al_seclist;
private ArrayList<Students> studentList,list;
private String [] statuslov ={"AC","NA"};
private Students student; 
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form StudentApp
  */
public StudentApp(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String staffid, String usn) {
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
  
public void CreateStudentForm()  { 
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
     Show_Students_In_JTable();
}

//select USN,NAME,SEC,BRANCH,STATUS from STUDENT
// Check Input Fields
public boolean checkInputs(){
if(txt_usn.getText().equals("") || txt_name.getText().equals("")  || (String)Jcbtxt_sec.getSelectedItem() == null || (String)Jcbtxt_branch.getSelectedItem() == null || (String)Jcbtxt_status.getSelectedItem() == null)
return false;   
else
return true; 
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



public boolean GetStudSection(){
myconnection = getConnection();
al_seclist=new ArrayList<String>();
//List<String> ls = new ArrayList<String>(); 
//jComboBox.setModel(new DefaultComboBoxModel(al_subcodelist.toArray()));
String sql4 = "select distinct SEC from SECTION ORDER BY SEC";
try{
pstmt = myconnection.prepareStatement(sql4);
rs=pstmt.executeQuery();
while(rs.next()){
al_seclist.add(rs.getString("SEC"));
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-get SECTION lov"+e.getMessage());
return false;
}   
return true;
}
// Display Data In JTable:
//      1 - Fill ArrayList With The Data

public ArrayList<Students> getStudentsList(){
studentList  = new ArrayList<Students>();
myconnection = getConnection();
String query = "SELECT USN,NAME,SEC,BRANCH,STATUS  FROM STUDENT ORDER BY USN";
try {
pstmt=myconnection.prepareStatement(query);
rs=pstmt.executeQuery();  

if(rs==null) return null;
else
{
while(rs.next()){
student = new Students(rs.getString("USN"),rs.getString("NAME"),rs.getString("SEC"),rs.getString("BRANCH"),rs.getString("STATUS"));
studentList.add(student);}
}
}
catch (SQLException ex) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+ex.getMessage());
return null;
}
return studentList;
}
//      2 - Populate The JTable
public void Show_Students_In_JTable(){
if(getStudentsList()==null){
JOptionPane.showMessageDialog(null,"NO student DATA");    
}
else
{     
list = getStudentsList();
DefaultTableModel model = (DefaultTableModel)JTable_Students.getModel();
// clear jtable content
//select usn,name, sec, branch, status from student
model.setRowCount(0);
Object[] row = new Object[5];
for(int i = 0; i < list.size(); i++){
row[0] = list.get(i).getusn();
row[1] = list.get(i).getname();
row[2] = list.get(i).getsec();
row[3] = list.get(i).getbranch();
row[4] = list.get(i).getstatus();
model.addRow(row);
}
}
}
// Show Data In  (String)Jcbtxt_sec.getSelectedItem
public void ShowItem(int index){
txt_usn.setText(getStudentsList().get(index).getusn());
txt_name.setText(getStudentsList().get(index).getname());
Jcbtxt_sec.setSelectedItem(getStudentsList().get(index).getsec());
Jcbtxt_branch.setSelectedItem(getStudentsList().get(index).getbranch());
Jcbtxt_status.setSelectedItem(getStudentsList().get(index).getstatus());

}
private void initComponents() {
jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 153));
jPanel1.setLayout(new GridLayout(3,4,20,20));
jlbUSN = new JLabel();
jlbUSN.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbUSN.setText("USN NO.:");
txt_usn = new JTextField(5);
txt_usn.setFont(new java.awt.Font("Tahoma", 1, 14));  
txt_usn.setPreferredSize(new java.awt.Dimension(24, 20));
jlbNAME = new JLabel();
jlbNAME.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbNAME.setText("STUDENT NAME:");
txt_name = new JTextField(42);
txt_name.setFont(new java.awt.Font("Tahoma", 1, 14));  
txt_name.setPreferredSize(new java.awt.Dimension(24, 20));
jlbSEC = new JLabel();
jlbSEC.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbSEC.setText("SECTION:");
if(GetStudSection()){
// lov to be implemented  
Jcbtxt_sec = new JComboBox();
Jcbtxt_sec.setModel(new DefaultComboBoxModel(al_seclist.toArray()));
}
else
{
JOptionPane.showMessageDialog(null,"Student subcode is invalid");
//System.exit(0);
}
Jcbtxt_sec.setFont(new java.awt.Font("Tahoma", 1, 14));  
Jcbtxt_sec.setPreferredSize(new java.awt.Dimension(24, 20));
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
jlbSTATUS = new JLabel();
jlbSTATUS.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbSTATUS.setText("STATUS:");
Jcbtxt_status = new JComboBox(statuslov);
Jcbtxt_status.setFont(new java.awt.Font("Tahoma", 1, 14));  
Jcbtxt_status.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbUSN);
jPanel1.add(txt_usn);
jPanel1.add(jlbNAME);
jPanel1.add(txt_name);
jPanel1.add(jlbSEC);
jPanel1.add(Jcbtxt_sec);
jPanel1.add(jlbBRANCH);
jPanel1.add(Jcbtxt_branch);
jPanel1.add(jlbSTATUS);
jPanel1.add(Jcbtxt_status);
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
jPnlButton.setBackground(new java.awt.Color(255, 153, 51));
jPnlButton.setLayout(new GridLayout(1,7,25,25));
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
JTable_Students = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},
//SELECT USN,NAME,SEC,BRANCH,STATUS from STUDENT
new String [] {"USN","STUDENT NAME","SEC","BRANCH","STATUS"});
JTable_Students.setModel(MytableModel);
JTableHeader header = JTable_Students.getTableHeader();
header.setUpdateTableInRealTime(true);
jPnlscrtb.add(jScrollPane1);
JTable_Students.setSelectionForeground(Color.white);
JTable_Students.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_Students);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbaptitle.setText("STUDENT DATA FORM FOR ADMIN");
jPanelptitle.add(jlbaptitle);
JPanel jPanel2N = new JPanel();
jPanel2N.setLayout(new GridLayout(3,1,12,12));
JPanel jPanelIns = new JPanel();
jPanelIns.setBackground(new java.awt.Color(255, 255,153));
f=new JFrame();
jPanel2N.add(jPanelptitle);
jPanel2N.add(jPnlButton);
jPanel2N.add(jPanelIns);
JLabel jlbIns = new JLabel();
jlbIns.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbIns.setText("IMPORTANT NOTE: BEFORE FOR SETTING UP STUDENT DATA, BRANCH AND SECTION DATA MUST BE SETUP BY ADMIN");
jPanelIns.add(jlbIns);
f.add(jPanel2N, BorderLayout.NORTH);
//f.add(jPanel1, BorderLayout.NORTH);
f.add(jPnlscrtb, BorderLayout.CENTER);
f.add(jPanel1, BorderLayout.SOUTH);
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
f.setMinimumSize(new Dimension(1200, 500));
f.setPreferredSize(new Dimension(1200, 500));
f.setSize(1200,500);
f.setLocationRelativeTo(null); 
f.pack();
f.setVisible(true); JTable_Students.addMouseListener(new java.awt.event.MouseAdapter() {
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

public boolean GetUSN(){
myconnection = getConnection();
String sql1 = "select USN from STUDENT where USN=?";
try{
pstmt = myconnection.prepareStatement(sql1);
pstmt.setString(1,txt_usn.getText());
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
return true;
else
return false; 
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-getUSN ERROR"+e.getMessage());
return false;
}   
}

//GEN-LAST:event_Btn_Choose_ImageActionPerformed

private void jbtnINSERTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnINSERTActionPerformed
if(checkInputs()){
if(GetUSN()){    
try {
myconnection = getConnection();
pstmt = myconnection.prepareStatement("INSERT INTO STUDENT values(UPPER(?),?,UPPER(?),UPPER(?),UPPER(?))");
////SELECT USN,NAME,SEC,BRANCH,STATUS FROM STUDENT
pstmt.setString(1,txt_usn.getText());
pstmt.setString(2, txt_name.getText());
pstmt.setString(3, (String) Jcbtxt_sec.getSelectedItem());
pstmt.setString(4, (String) Jcbtxt_branch.getSelectedItem());
pstmt.setString(5, (String) Jcbtxt_status.getSelectedItem());
int rowsUpdated=pstmt.executeUpdate();
System.out.println("INSERT-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Students_In_JTable();
JOptionPane.showMessageDialog(null, "Data Inserted into student table");
}
catch (Exception ex) {
JOptionPane.showMessageDialog(null, ex.getMessage());
}
}
else
{
JOptionPane.showMessageDialog(null, "USN already exists, Duplicatation not allowed");
}
}
else
{
JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty");
}
System.out.println("STUDENT => "+txt_usn.getText());
//System.out.println("Image => "+ImgPath);
}//GEN-LAST:event_jbtnINSERTActionPerformed

private void jbtnUPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUPDATEActionPerformed
if(checkInputs()){
String UpdateQuery = null;
myconnection = getConnection();
// update without image
UpdateQuery = "UPDATE STUDENT SET NAME = ?, SEC=UPPER(?), BRANCH=UPPER(?), STATUS=UPPER(?) WHERE UPPER(USN) = UPPER(?) ";
try{
pstmt = myconnection.prepareStatement(UpdateQuery);
////SELECT USN,NAME,SEC, BRANCH,STATUS FROM STUDENT
pstmt.setString(1, txt_name.getText());
pstmt.setString(2, (String) Jcbtxt_sec.getSelectedItem());
pstmt.setString(3, (String)Jcbtxt_branch.getSelectedItem());
pstmt.setString(4, (String)Jcbtxt_status.getSelectedItem());
pstmt.setString(5,txt_usn.getText());
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
JOptionPane.showMessageDialog(null, "No Students Updated, invalid id");
else
{
System.out.println("UPDATE-1-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Students_In_JTable();
JOptionPane.showMessageDialog(null, "Existing Student data Updated");
}
}
catch (SQLException ex) {
    System.err.println("SQL Exception-02 Occurred for the UPDATE OF STUDENT");
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
if(!txt_usn.getText().equals("")){
try{
Connection con = getConnection();
PreparedStatement ps = con.prepareStatement("DELETE STUDENT WHERE UPPER(USN)=UPPER(?)");
ps.setString(1,txt_usn.getText());
int rowsUpdated=ps.executeUpdate();
if(rowsUpdated==0)
JOptionPane.showMessageDialog(null, "No Students Deleted, invalid id");
else
{
System.out.println("DELETE-Commiting data here...."+rowsUpdated);
con.commit();
Show_Students_In_JTable();
JOptionPane.showMessageDialog(null, "Students Deleted");
}
}
catch (SQLException ex) {
System.err.println("SQL Exception-01 Occurred for the DELETE OF Student");
ex.printStackTrace();
JOptionPane.showMessageDialog(null, "Students Not Deleted");
}
}
else{
JOptionPane.showMessageDialog(null, "Students Not Deleted : No Id To Delete");
}

}//GEN-LAST:event_jbtnDELETEActionPerformed
private void JTable_SectionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_SectionsMouseClicked
int index = JTable_Students.getSelectedRow();
ShowItem(index);
}//GEN-LAST:event_JTable_SectionsMouseClicked

// Button First Show The First Record
private void jbtnFIRSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFIRSTActionPerformed
pos = 0;
ShowItem(pos);
}//GEN-LAST:event_jbtnFIRSTActionPerformed
// Button Last Show The Last Record
private void jbtnLASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLASTActionPerformed
pos = getStudentsList().size()-1;
ShowItem(pos);
}//GEN-LAST:event_jbtnLASTActionPerformed
// Button Next Show The Next Record
private void jbtnNEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNEXTActionPerformed
pos++;
if(pos >= getStudentsList().size()){
pos = getStudentsList().size()-1;
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
