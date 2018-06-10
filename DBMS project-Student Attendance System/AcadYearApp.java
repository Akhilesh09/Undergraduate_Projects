import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
/**
 *
 * @author akhilesh
 */
public class AcadYearApp{
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton Btn_Choose_Image;
private JButton jbtnFIRST;
private JButton jbtnINSERT;
private JButton jbtnLAST;
private JButton jbtnNEXT;
private JButton jbtnPREVIOUS;
private JTable  JTable_AcadYears;
private JButton jbtnDELETE;
//select sec section
private JLabel jlbACADYR;
private JTextField txt_acadyr;
private JPanel jPanel1;
private JPanel jPnlButton;
private JScrollPane jScrollPane1;
private JLabel lbl_image;
private java.sql.Date sqladate;

private String ImgPath = null;
private int pos = 0,sid;
private String loginName,loginRole,usn,staffid;
private Connection myconnection;
private JFrame f;
ArrayList<AcadYear> AcadYearList,list;
private AcadYear acadyr;
private ResultSet rs;
private PreparedStatement pstmt=null;
//private com.toedter.calendar.JDateChooser txt_AddDate;

// End of variables declaration//GEN-END:variables
 /**
  * Creates new form AcadYearApp
  */
public AcadYearApp(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String staffid, String usn) {
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
  
public void CreateAcadYearForm()  { 
    
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
     Show_AcadYears_In_JTable();
}

//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
// Check Input Fields
public boolean checkInputs(){
if(txt_acadyr.getText().equals(""))
return false;   
else
return true; 
}

// Display Data In JTable:
//      1 - Fill ArrayList With The Data

public ArrayList<AcadYear> getAcadYearList(){
AcadYearList  = new ArrayList<AcadYear>();
myconnection = getConnection();
String query = "SELECT AC_YEAR FROM ACADYR ORDER BY AC_YEAR DESC";
try {
pstmt=myconnection.prepareStatement(query);
rs=pstmt.executeQuery();  

if(rs==null) return null;
else
{
while(rs.next()){
acadyr = new AcadYear(rs.getInt("AC_YEAR"));
AcadYearList.add(acadyr);}
}
}
catch (SQLException ex) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+ex.getMessage());
return null;
}
return AcadYearList;
}
//      2 - Populate The JTable
public void Show_AcadYears_In_JTable(){
if(getAcadYearList()==null){
JOptionPane.showMessageDialog(null,"NO ACAD YEAR DATA");    
}
else
{    
list = getAcadYearList();
DefaultTableModel model = (DefaultTableModel)JTable_AcadYears.getModel();
// clear jtable content
//select sec from section
model.setRowCount(0);
Object[] row = new Object[1];
for(int i = 0; i < list.size(); i++){
row[0] = list.get(i).getAcadyr();
model.addRow(row);
}
}
}
// Show Data In Inputs
public void ShowItem(int index){
txt_acadyr.setText(String.valueOf(getAcadYearList().get(index).getAcadyr()));
}
private void initComponents() {
jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 153));
jPanel1.setLayout(new GridLayout(1,1,25,25));
jlbACADYR = new JLabel();
jlbACADYR.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbACADYR.setText("ACADEMIC YEAR:");
txt_acadyr = new JTextField(10);
txt_acadyr.setFont(new java.awt.Font("Tahoma", 1, 14)); 
txt_acadyr.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbACADYR);
jPanel1.add(txt_acadyr);
/**************************/
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
jPnlButton.setLayout(new GridLayout(1,6,25,25));
jPnlButton.add(jbtnINSERT);
jPnlButton.add(jbtnDELETE);
jPnlButton.add(jbtnFIRST);
jPnlButton.add(jbtnNEXT);
jPnlButton.add(jbtnPREVIOUS);
jPnlButton.add(jbtnLAST);
JPanel jPnlscrtb = new JPanel();
jPnlscrtb.setLayout(new GridLayout(1,1,25,25));
jScrollPane1 = new JScrollPane();
JTable_AcadYears = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},
//SELECT AC_YEAR from ACADYR
new String [] {"AC_YEAR"});
JTable_AcadYears.setModel(MytableModel);
JTableHeader header = JTable_AcadYears.getTableHeader();
header.setUpdateTableInRealTime(true);
jPnlscrtb.add(jScrollPane1);
JTable_AcadYears.setSelectionForeground(Color.white);
JTable_AcadYears.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_AcadYears);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbaptitle.setText("ACADEMIC YEAR CONFIGURATION FORM FOR ADMIN");
jPanelptitle.add(jlbaptitle);
JPanel jPanel2N = new JPanel();
jPanel2N.setLayout(new GridLayout(3,1,12,12));
f=new JFrame();
jPanel2N.add(jPanelptitle);
jPanel2N.add(jPnlButton);
jPanel2N.add(jPanel1);
JPanel jPanelIns = new JPanel();
jPanelIns.setBackground(new java.awt.Color(255, 255,153));
JLabel jlbIns = new JLabel();
jlbIns.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbIns.setText("IMPORTANT NOTE:MUST BE SETUP FIRST FOR ATTENDANCE APP");
jPanelIns.add(jlbIns);
f.add(jPanel2N, BorderLayout.NORTH);
//f.add(jPanel1, BorderLayout.NORTH);
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
f.setMinimumSize(new Dimension(1100, 400));
f.setPreferredSize(new Dimension(1100, 400));
f.setSize(1100,400);
f.setLocationRelativeTo(null); 
f.pack();
f.setVisible(true); JTable_AcadYears.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseClicked(java.awt.event.MouseEvent evt) {
JTable_AcadYearsMouseClicked(evt);
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

public boolean GetAcadyr(){
myconnection = getConnection();
String sql1 = "select AC_YEAR from ACADYR where AC_YEAR=?";
try{
pstmt = myconnection.prepareStatement(sql1);
pstmt.setInt(1, Integer.parseInt(txt_acadyr.getText()));
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
return true;
else
return false; 
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-getacadyr ERROR"+e.getMessage());
return false;
}   
}
//GEN-LAST:event_Btn_Choose_ImageActionPerformed

private void jbtnINSERTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnINSERTActionPerformed
if(checkInputs()){
if (GetAcadyr()) {
try{
Connection con = getConnection();
PreparedStatement ps = con.prepareStatement("INSERT INTO ACADYR values(?)");
////SELECT AC_YEAR FROM ACADYR
ps.setInt(1, Integer.parseInt(txt_acadyr.getText()));
int rowsUpdated=ps.executeUpdate();
System.out.println("INSERT-Commiting data here...."+rowsUpdated);
con.commit();
Show_AcadYears_In_JTable();
JOptionPane.showMessageDialog(null, "ACAD YEAR table Data Inserted");
}
catch (Exception ex) {
JOptionPane.showMessageDialog(null, ex.getMessage());
}
}
else
{
JOptionPane.showMessageDialog(null,"ACAD YEAR ALREADY EXISTS, DUPLICATION NOT ALLOWED");   
}
}
else
{
JOptionPane.showMessageDialog(null, "ACADYR  Field is Empty");
}
System.out.println("ACAD YEAR => "+txt_acadyr.getText());
//System.out.println("Image => "+ImgPath);
}//GEN-LAST:event_jbtnINSERTActionPerformed

// Button Delete The Data From MySQL Database
private void jbtnDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDELETEActionPerformed
if(!txt_acadyr.getText().equals("")){
try {
Connection con = getConnection();
PreparedStatement ps = con.prepareStatement("DELETE ACADYR WHERE AC_YEAR=?");
ps.setInt(1, Integer.parseInt(txt_acadyr.getText()));
int rowsUpdated=ps.executeUpdate();
System.out.println("DELETE-Commiting data here...."+rowsUpdated);
con.commit();
Show_AcadYears_In_JTable();
JOptionPane.showMessageDialog(null, "ACAD YEAR Deleted");
}
catch (SQLException ex) {
JOptionPane.showMessageDialog(null, "ACAD YEAR Not Deleted");
}
}
else{
JOptionPane.showMessageDialog(null, "ACAD YEAR Not Deleted : No Id To Delete");
}

}//GEN-LAST:event_jbtnDELETEActionPerformed
private void JTable_AcadYearsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_AcadYearsMouseClicked
int index = JTable_AcadYears.getSelectedRow();
ShowItem(index);
}//GEN-LAST:event_JTable_AcadYearsMouseClicked

// Button First Show The First Record
private void jbtnFIRSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFIRSTActionPerformed
pos = 0;
ShowItem(pos);
}//GEN-LAST:event_jbtnFIRSTActionPerformed
// Button Last Show The Last Record
private void jbtnLASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLASTActionPerformed
pos = getAcadYearList().size()-1;
ShowItem(pos);
}//GEN-LAST:event_jbtnLASTActionPerformed
// Button Next Show The Next Record
private void jbtnNEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNEXTActionPerformed
pos++;
if(pos >= getAcadYearList().size()){
pos = getAcadYearList().size()-1;
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
