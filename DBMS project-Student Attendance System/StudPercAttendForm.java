import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author AKHILESH
 */
public class StudPercAttendForm{
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton Btn_Choose_Image;
private JButton jbtnREFRESH;
private JTable  JTable_StudPercAttend;
//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
private JLabel jlbTitle;
private JLabel jlbACADYR;
private JTextField txt_acadyr;
private JLabel jlbSEM;
private JTextField txt_sem;
private JLabel jlbBRANCH;
private JTextField txt_branch;
private JLabel jlbSEC;
private JTextField txt_sec;
private JLabel jlbUSN;
private JTextField txt_usn;
private JLabel jlbNAME;
private JTextField txt_name;
private JLabel jlbSUBCODE;
private JTextField txt_subcode;
private JLabel jlbPERC;
private JTextField txt_perc;
private JPanel jPanel1;
private JPanel jPnlButton;
private JScrollPane jScrollPane1;
private java.sql.Date sqladate;
private JLabel lbl_image;
private String ImgPath = null;
private int pos = 0,sid,i_acyr,i_sem;
private String loginName,loginRole,usn,studname,ssid,staffname,sec,subcode,branch;
private Connection myconnection;
private JFrame f;
private ArrayList<StudentPercAttndTempl> list,studentList;
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form StudPercAttendForm
  */
public StudPercAttendForm(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String usn, String studname) {
this.loginName = loginName;
this.loginRole = loginRole;
this.myconnection = myconnection;
this.studname=studname;
this.usn = usn;
}

public String getstudname(){return this.studname;}
public Connection getConnection(){return this.myconnection;}
public String getLoginrole(){return this.loginRole;}
public String getLoginName(){return this.loginName;}
public String getusn(){return this.usn;}
  
public void CreateStudPercAttendForm()  { 
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
//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance


public Boolean GetAcadyrSem(){
myconnection = getConnection();
//String.valueOf(getStudentPercList().get(index).getAcyear());
usn=getusn();
txt_usn.setText(String.valueOf(usn));
txt_name.setText(studname);      
String query  = "select distinct  ac_year,sem from period where sysdate between st_date and end_date and (ac_year,sem) in" 
+ "(select distinct ac_year,sem from attendance where usn=?)";
PreparedStatement pstmt;
try {
pstmt=myconnection.prepareStatement(query);
pstmt.setString(1,usn);
//pstmt.setString(2,subcode);
//pstmt.setString(3,branch);
//pstmt.setString(4,sec);
ResultSet rs=pstmt.executeQuery();  
StudentPercAttndTempl studattendperc;
if (rs.next()){
i_acyr=Integer.parseInt(rs.getString("AC_YEAR"));
i_sem=Integer.parseInt(rs.getString("SEM"));
return true;
}
else
{
//System.out.println("There are no attendance records matching the input condition");
JOptionPane.showMessageDialog(null, "There are no attendance records matching the input condition. ");
return false;   
}
}
catch (SQLException ex) {
System.err.println("SQL exception while retreiving acadyr and semester");
ex.printStackTrace();
return false;
}
}
// Display Data In JTable:
//      1 - Fill ArrayList With The Data

public ArrayList<StudentPercAttndTempl> getStudentPercList(){
studentList  = new ArrayList<StudentPercAttndTempl>();
if(GetAcadyrSem()){
Connection con = getConnection();
usn=getusn();
txt_acadyr.setText(String.valueOf(i_acyr));
txt_sem.setText(String.valueOf(i_sem));
String query = "SELECT AC_YEAR,SEM,SUB_CODE,USN,PERC FROM V3 "
+" WHERE AC_YEAR=? AND SEM=? AND USN=? AND USN IN (SELECT USN FROM STUDENT WHERE UPPER(STATUS)='AC') ORDER BY SUB_CODE";
//+" WHERE AC_YEAR=? AND SEM=? AND USN=? ORDER BY USN";
//Statement st;
//ResultSet rs;
PreparedStatement pstmt;
try {
pstmt=con.prepareStatement(query);
pstmt.setInt(1,i_acyr);
pstmt.setInt(2,i_sem);
pstmt.setString(3,usn);
ResultSet rs=pstmt.executeQuery();	
//st = con.createStatement();
//rs = st.executeQuery(query);
StudentPercAttndTempl studattend;
if(rs==null) return null;
else
{
while(rs.next()){
studattend = new StudentPercAttndTempl(Integer.parseInt(rs.getString("AC_YEAR")),Integer.parseInt(rs.getString("SEM")),rs.getString("SUB_CODE"),rs.getString("USN"),studname,Integer.parseInt(rs.getString("PERC")));
studentList.add(studattend);}
}
}
catch (SQLException ex) {
System.err.println("SQL exception while retreiving Student Percentage Attendance");
ex.printStackTrace();
return null;
}
return studentList;
}
else
return null;
}
//      2 - Populate The JTable
public void Show_Students_In_JTable(){
if(getStudentPercList()==null){
JOptionPane.showMessageDialog(null,"NO ATTENDANCE DATA FOR THE STUDENT");    
}
else
{        
list = getStudentPercList();
DefaultTableModel model = (DefaultTableModel)JTable_StudPercAttend.getModel();
// clear jtable content
//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
model.setRowCount(0);
Object[] row = new Object[2];
for(int i = 0; i < list.size(); i++){
row[0] = list.get(i).getSubcode();
row[1] = list.get(i).getPerc();
model.addRow(row);
}
}
}
// Show Data In Inputs
public void ShowItem(int index){
//txt_acadyr.setText(String.valueOf(list.get(index).getAcyear()));
//txt_sem.setText(String.valueOf(list.get(index).getSem()));
txt_subcode.setText(list.get(index).getSubcode());
//txt_usn.setText(usn);
//txt_name.setText(studname);
txt_perc.setText(String.valueOf(list.get(index).getPerc()));
}
private void initComponents() {
    
jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 153));
jPanel1.setLayout(new GridLayout(2,4,25,25));
JLabel jlbTitle =new JLabel();
jlbTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); 
jlbTitle.setText("STUDENT PERCENTAGE ATTENDANCE FORM");
JPanel jPaneltit = new JPanel();
jPaneltit.add(jlbTitle);
//ROW1
JLabel jlbloginid = new JLabel();
jlbloginid.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbloginid.setText("Your loginid is:"+getLoginName());
jPanel1.add(jlbloginid);

JLabel jlbusnno = new JLabel();
jlbusnno.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbusnno.setText("Your usn is:"+getusn());
jPanel1.add(jlbusnno);

JLabel jlbusername = new JLabel();
jlbusername.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbusername.setText("You are:"+getstudname());
jPanel1.add(jlbusername);

JLabel jlbrole = new JLabel();
jlbrole.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbrole.setText("Your role is:"+getLoginrole());
jPanel1.add(jlbrole);

//row2
jlbACADYR = new JLabel();
jlbACADYR.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbACADYR.setText("ACADYR:");
txt_acadyr = new JTextField(15);
txt_acadyr.setEnabled(false);
txt_acadyr.setFont(new java.awt.Font("Tahoma", 1, 14)); 
txt_acadyr.setPreferredSize(new java.awt.Dimension(24, 15)); 
jPanel1.add(jlbACADYR);
jPanel1.add(txt_acadyr);

jlbSEM = new JLabel();
jlbSEM.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbSEM.setText("SEMESTER:");
txt_sem = new JTextField(5);
txt_sem.setEnabled(false);
txt_sem.setFont(new java.awt.Font("Tahoma", 1, 14)); 
txt_sem.setPreferredSize(new java.awt.Dimension(24, 15)); 
jPanel1.add(jlbSEM);
jPanel1.add(txt_sem);
// row 3
jlbUSN = new JLabel();
jlbUSN.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbUSN.setText("USN:");
txt_usn = new JTextField(15);
txt_usn.setEnabled(false);
txt_usn.setFont(new java.awt.Font("Tahoma", 1, 14)); 
txt_usn.setPreferredSize(new java.awt.Dimension(24, 15));
//jPanel1.add(jlbUSN);
//jPanel1.add(txt_usn);

jlbNAME = new JLabel();
jlbNAME.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbNAME.setText("STUDENT NAME:");
txt_name = new JTextField(25);
txt_name.setEnabled(false);
txt_name.setFont(new java.awt.Font("Tahoma", 1, 14)); 
txt_name.setPreferredSize(new java.awt.Dimension(24, 15));
//jPanel1.add(jlbNAME);
//jPanel1.add(txt_name);

/**************************/
jbtnREFRESH = new JButton();
jbtnREFRESH.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnREFRESH.setIcon(new ImageIcon(getClass().getResource("refresh.png"))); 
jbtnREFRESH.setText("REFRESH");
jbtnREFRESH.setIconTextGap(15);
jPnlButton = new JPanel();
jPnlButton.setLayout(new GridLayout(1,7,20,20));
jPnlButton.setBackground(new java.awt.Color(255, 153, 51));
jPnlButton.setLayout(new GridLayout(1,1,30,30));
jPnlButton.add(jbtnREFRESH);
JPanel jPnlscrtb = new JPanel();
jPnlscrtb.setLayout(new GridLayout(1,1,30,30));
jScrollPane1 = new JScrollPane();
JTable_StudPercAttend = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},
//SELECT AC_YEAR,SEM,SUB_CODE,USN,PERC FROM V3
new String [] {"SUBJCODE","PERCENTAGE"});
JTable_StudPercAttend.setModel(MytableModel);
JTableHeader header = JTable_StudPercAttend.getTableHeader();
header.setUpdateTableInRealTime(true);
jPnlscrtb.add(jScrollPane1);
JTable_StudPercAttend.setSelectionForeground(Color.white);
JTable_StudPercAttend.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_StudPercAttend);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 16));
jlbaptitle.setText("STUDENT PERCENTAGE ATTENDANCE VIEW - ONLY FOR STUDENT ROLE");
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
jlbIns.setText("IMPORTANT NOTE:VIEW STUDENT PERCENTAGE ATTEDANCE FORM FOR CURRENT ACTIVE SEMESTER");
jPanelIns.add(jlbIns);
f.add(jPanel2N, BorderLayout.NORTH);
//f.add(jPnlButton, BorderLayout.SOUTH);
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
f.setPreferredSize(new Dimension(1000, 500));
f.setSize(1000,500);
f.setLocationRelativeTo(null); 
f.pack();
f.setVisible(true); 
JTable_StudPercAttend.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseClicked(java.awt.event.MouseEvent evt) {
JTable_StudPercAttendMouseClicked(evt);
}
});

jbtnREFRESH.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnREFRESHActionPerformed(evt);
}
});

}
// </editor-fold>//GEN-END:initComponents

//GEN-LAST:event_Btn_Choose_ImageActionPerformed

// Display The Selected Row Data Into JTextFields
// And The Image Into JLabel
private void JTable_StudPercAttendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_StudPercAttendMouseClicked
int index = JTable_StudPercAttend.getSelectedRow();
//ShowItem(index);
}//GEN-LAST:event_JTable_StudPercAttendMouseClicked

// Button First Show The First Record
private void jbtnREFRESHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnREFRESHActionPerformed
pos = 0;
Show_Students_In_JTable();
//ShowItem(pos);
}//GEN-LAST:event_jbtnREFRESHActionPerformed
// Button Last Show The Last Record
}
