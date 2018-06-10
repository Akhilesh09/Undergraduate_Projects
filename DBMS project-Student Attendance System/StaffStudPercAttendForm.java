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
import com.toedter.calendar.*;
/**
 *
 * @author AKHILESH
 */
public class StaffStudPercAttendForm{
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton jbtnGENREPORT;
private JButton jbtnVIEWATTENDANCE;
private JTable  JTable_StaffStudPercAttnd;
//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
private JLabel jlbTitle;
private JLabel jlbACADYR;
private JTextField txt_acadyr;
private JLabel jlbSEM;
private JTextField txt_sem;
private JLabel jlbBRANCH;
private JComboBox Jcbtxt_branch;
//private JTextField txt_branch;
private JLabel jlbSEC;
private JComboBox Jcbtxt_sec;
//private JTextField txt_sec;
private JLabel jlbSID;
private JTextField txt_sid;
private JLabel jlbNAME;
private JTextField txt_name;
private JLabel jlbSUBCODE;
private JComboBox Jcbtxt_subcode;
//private JTextField txt_subcode;

private JLabel jlbSTFNAME;
private JTextField txt_stfname;
private JPanel jPanel1;
private JPanel jPnlButton;
private JScrollPane jScrollPane1;
private java.sql.Date sqladate;
private JLabel lbl_image;
private String ImgPath = null;
private int pos = 0,sid,i_acyr,i_sem,perc;
private String loginName,loginRole,ssid,usn,studname,staffname,sec,subcode,branch;
private Connection myconnection;
private PreparedStatement pstmt;
private ResultSet rs=null;
private JFrame f;
private ArrayList<StaffStudPercAttndTempl> staffstudattndpercList,list;
private FileWriter fileWriter;
private ArrayList<String> al_subcodelist,al_branchlist,al_seclist;
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form StaffStudPercAttendForm
  */
public StaffStudPercAttendForm(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String ssid, String staffname) {
this.loginName = loginName;
this.loginRole = loginRole;
this.myconnection = myconnection;
this.staffname=staffname;
this.ssid = ssid;
this.sid =Integer.parseInt(ssid);
}

public String getstaffname(){return this.staffname;}
public Connection getConnection(){return this.myconnection;}
public String getLoginrole(){return this.loginRole;}
public String getLoginName(){return this.loginName;}
public int getsid(){return this.sid;}
public String getssid(){return this.ssid;}
  
public void CreateStaffStudPercAttendForm()  { 
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
//Show_StudPercAttnd_In_JTable();
}
//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
// Check Input Fields
public boolean checkInputs(){
if(txt_acadyr.getText().trim().equals("") || txt_sem.getText().trim().equals("") || (String)Jcbtxt_sec.getSelectedItem() == null 
|| (String)Jcbtxt_branch.getSelectedItem() == null || (String)Jcbtxt_subcode.getSelectedItem() == null || txt_sid.getText() == null)
return false;   
else
return true; 
}

public boolean GetStaffSubCode(){
myconnection = getConnection();
al_subcodelist=new ArrayList<String>();
//List<String> ls = new ArrayList<String>(); 
//jComboBox.setModel(new DefaultComboBoxModel(al_subcodelist.toArray()));
String sql2 = "select distinct sub_code from staff_alloc where sid=?";
try{
pstmt = myconnection.prepareStatement(sql2);
pstmt.setInt(1,sid);
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


public boolean GetStudBranch(){
myconnection = getConnection();
al_branchlist=new ArrayList<String>();
//List<String> ls = new ArrayList<String>(); 
//jComboBox.setModel(new DefaultComboBoxModel(al_subcodelist.toArray()));
String sql3 = "select distinct branch from staff_alloc where sid=?";
try{
pstmt = myconnection.prepareStatement(sql3);
pstmt.setInt(1,sid);
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
String sql4 = "select distinct sec from staff_alloc where sid=?";
try{
pstmt = myconnection.prepareStatement(sql4);
pstmt.setInt(1,sid);
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





public Boolean GetAcadyrSem(){
myconnection = getConnection();
sid=getsid();
subcode=(String)Jcbtxt_subcode.getSelectedItem();
branch=(String)Jcbtxt_branch.getSelectedItem();
sec=(String)Jcbtxt_sec.getSelectedItem();
String query  = "select distinct  ac_year, sem from period where sysdate between st_date and end_date and (ac_year,sem) in" 
+ "(select distinct ac_year, sem from attendance where sid=? and sub_code=? and branch=? and sec=?)";
try {
pstmt=myconnection.prepareStatement(query);
pstmt.setInt(1,sid);
pstmt.setString(2,subcode);
pstmt.setString(3,branch);
pstmt.setString(4,sec);
rs=pstmt.executeQuery();
if (rs.next()){
i_acyr=Integer.parseInt(rs.getString("AC_YEAR"));
i_sem=Integer.parseInt(rs.getString("SEM"));
return true;
}
else
{
System.out.println("There are no attendance records matching the input condition");
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

public boolean GenAttendReport(){
txt_acadyr.setText(String.valueOf(i_acyr));
txt_sem.setText(String.valueOf(i_sem));
txt_sid.setText(String.valueOf(sid));
txt_stfname.setText(staffname);      
myconnection = getConnection();
String query = "SELECT A.AC_YEAR,A.SEM,A.SUB_CODE,A.USN,B.NAME NAME, A.SEC,A.PERC PERC FROM V3 A,STUDENT B "
+" WHERE A.USN=B.USN AND UPPER(B.STATUS)='AC' AND A.AC_YEAR=? AND A.SEM=? AND (A.SEC,A.SUB_CODE)"
+ " IN (SELECT DISTINCT SEC,SUB_CODE FROM STAFF_ALLOC WHERE SEC=? AND BRANCH=? AND SID=?)" 
+  "  ORDER BY A.USN";
String FILE_HEADER = "USN\t\tSTUDENTNAME\t\tPERCENTAGE\n";
fileWriter = null;
try {
fileWriter = new FileWriter("ClassPerc"+"_"+branch+"_"+i_sem+"_"+sec+".xls");
fileWriter.append("\t\tRNS INSITUTE OF TECHNOLOGY\n");
fileWriter.append("\t\tClass Percentage Attendance Report\n");
fileWriter.append("\t\tDepartment"+branch+"\n");
fileWriter.append("ACAD YEAR:\t"+i_acyr+"\t\tCLASS:\t"+i_sem+sec+"\n");
fileWriter.append("STAFF ID:\t"+sid+"\t\tSTAFFNAME:\t"+staffname+"\n");
fileWriter.append(FILE_HEADER);
//System.out.println("CSV file Header successfully Created!!!");
} 
catch (Exception e) {
System.out.println("Error in CsvFileWriter !!!");
e.printStackTrace();
} 
try {
pstmt=myconnection.prepareStatement(query);
pstmt.setInt(1,i_acyr);
pstmt.setInt(2,i_sem);
pstmt.setString(3,sec);
pstmt.setString(4,branch);
pstmt.setInt(5,sid);
rs=pstmt.executeQuery();
if(rs==null) return false;
else
{
while(rs.next()){
usn=rs.getString("USN");
studname=rs.getString("NAME");
perc=Integer.parseInt(rs.getString("PERC"));
try{
fileWriter.append(usn+"\t\t"+studname+"\t\t"+perc+"\n");
}
catch (Exception e) {
System.out.println("Error in CsvFileWriter !!!");
e.printStackTrace();
return false;
}
}
//System.out.println("data added successfully to file!!!");
}
try
{
fileWriter.flush();
fileWriter.close();
} 
catch (IOException e) {
System.out.println("Error while flushing/closing fileWriter !!!");
e.printStackTrace();
return false;
}
}
catch (SQLException ex) {
System.err.println("SQL exception while retreiving Attendance Percentage");
ex.printStackTrace();
return false;
}
return true;    
}    

public ArrayList<StaffStudPercAttndTempl> getStudPercAttndList(){
staffstudattndpercList  = new ArrayList<StaffStudPercAttndTempl>();
if(GetAcadyrSem()){
txt_acadyr.setText(String.valueOf(i_acyr));
txt_sem.setText(String.valueOf(i_sem));
txt_sid.setText(String.valueOf(sid));
txt_stfname.setText(staffname);      
myconnection = getConnection();
String query = "SELECT A.AC_YEAR,A.SEM,A.SUB_CODE,A.USN,B.NAME NAME, A.SEC,A.PERC PERC FROM V3 A,STUDENT B "
+" WHERE A.USN=B.USN AND UPPER(B.STATUS)='AC' AND A.AC_YEAR=? AND A.SEM=? AND (A.SEC,A.SUB_CODE) IN (SELECT DISTINCT SEC,SUB_CODE FROM STAFF_ALLOC WHERE SEC=? AND BRANCH=? AND SID=?)" 
+  "  ORDER BY A.USN";
try {
pstmt=myconnection.prepareStatement(query);
pstmt.setInt(1,i_acyr);
pstmt.setInt(2,i_sem);
pstmt.setString(3,sec);
pstmt.setString(4,branch);
pstmt.setInt(5,sid);
rs=pstmt.executeQuery();  
StaffStudPercAttndTempl studattendperc;
if(rs==null) return null;
else
{
while(rs.next()){
//studattendperc = new StaffStudPercAttndTempl(Integer.parseInt(rs.getString("AC_YEAR")),Integer.parseInt(rs.getString("SEM")),branch,sec,rs.getString("SUB_CODE"),sid,rs.getString("USN"),rs.getString("NAME"),Integer.parseInt(rs.getString("PERC")));
studattendperc = new StaffStudPercAttndTempl(Integer.parseInt(rs.getString("AC_YEAR")),Integer.parseInt(rs.getString("SEM")),branch,sec,rs.getString("SUB_CODE"),sid,rs.getString("USN"),rs.getString("NAME"),Integer.parseInt(rs.getString("PERC")));
staffstudattndpercList.add(studattendperc);}
}
}
catch (SQLException ex) {
System.err.println("SQL exception while retreiving Attendance Percentage");
ex.printStackTrace();
}
return staffstudattndpercList;
}
else
return null;
}
//      2 - Populate The JTable
public void Show_StudPercAttnd_In_JTable(){   
if(getStudPercAttndList()==null){
JOptionPane.showMessageDialog(null,"NO ATTENDANCE DATA FOR THE SUBJECT OR SECTION OR BRANCH");    
}
else
{
list =getStudPercAttndList();
DefaultTableModel model = (DefaultTableModel)JTable_StaffStudPercAttnd.getModel();
// clear jtable content
//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
model.setRowCount(0);
//Object[] row = new Object[9];
Object[] row = new Object[3];
for(int i = 0; i < list.size(); i++){
//row[0] = list.get(i).getacyear();
//row[1] = list.get(i).getsem();
//row[2] = list.get(i).getbranch();
//row[3] = list.get(i).getsec();
//row[4] = list.get(i).getsubcode();
//row[5] = list.get(i).getsid();
row[0] = list.get(i).getusn();
row[1] = list.get(i).getname();
row[2] = list.get(i).getperc();
model.addRow(row);
}
}
}
// Show Data In Inputs
public void ShowItem(int index){
if(getStudPercAttndList().size()>0) {    
txt_acadyr.setText(String.valueOf(getStudPercAttndList().get(index).getacyear()));
txt_sem.setText(String.valueOf(getStudPercAttndList().get(index).getsem()));
Jcbtxt_branch.setSelectedItem(getStudPercAttndList().get(index).getbranch());
Jcbtxt_sec.setSelectedItem(getStudPercAttndList().get(index).getsec());
Jcbtxt_subcode.setSelectedItem(getStudPercAttndList().get(index).getsubcode());
txt_sid.setText(String.valueOf(getStudPercAttndList().get(index).getsid()));
}
else
{
JOptionPane.showMessageDialog(null,"EMPTY ATTENDANCE DATA");      
}
}
private void initComponents() {   
jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 153));
jPanel1.setLayout(new GridLayout(3,6,25,25));
//ROW1
JLabel jlbloginid = new JLabel();
jlbloginid.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbloginid.setText("loginid:"+getLoginName());
jPanel1.add(jlbloginid);

JLabel jlbsid = new JLabel();
jlbsid.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbsid.setText("STAFFID is:"+getsid());
jPanel1.add(jlbsid);

JLabel jlbusername = new JLabel();
jlbusername.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbusername.setText("You are:"+getstaffname());
jPanel1.add(jlbusername);

JLabel jlbrole = new JLabel();
jlbrole.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbrole.setText("Your role is:"+getLoginrole());
jPanel1.add(jlbrole);
//jPanel1.add(txt_acadyr);
//row2
jlbACADYR = new JLabel();
jlbACADYR.setFont(new java.awt.Font("Tahoma", 1, 18));  
jlbACADYR.setText("ACADYR:");
txt_acadyr = new JTextField(5);
txt_acadyr.setEnabled(false);
txt_acadyr.setFont(new java.awt.Font("Tahoma", 1, 18));  
txt_acadyr.setPreferredSize(new java.awt.Dimension(24, 20));  
jPanel1.add(jlbACADYR);
jPanel1.add(txt_acadyr);
//row 2
jlbSEM = new JLabel();
jlbSEM.setFont(new java.awt.Font("Tahoma", 1, 18));  
jlbSEM.setText("SEMESTER:");
txt_sem = new JTextField(3);
txt_sem.setEnabled(false);
txt_sem.setFont(new java.awt.Font("Tahoma", 1, 18));  
txt_sem.setPreferredSize(new java.awt.Dimension(24, 20));  
jPanel1.add(jlbSEM);
jPanel1.add(txt_sem);
//row3
jlbBRANCH = new JLabel();
jlbBRANCH.setFont(new java.awt.Font("Tahoma", 1, 18));  
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
//txt_branch = new JTextField(5);
Jcbtxt_branch.setEnabled(true);
Jcbtxt_branch.setFont(new java.awt.Font("Tahoma", 1, 18));  
Jcbtxt_branch.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbBRANCH);
jPanel1.add(Jcbtxt_branch);
jlbSEC = new JLabel();
jlbSEC.setFont(new java.awt.Font("Tahoma", 1, 18));  
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
//txt_sec = new JTextField(3);
Jcbtxt_sec.setEnabled(true);
Jcbtxt_sec.setFont(new java.awt.Font("Tahoma", 1, 18));  
Jcbtxt_sec.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbSEC);
jPanel1.add(Jcbtxt_sec);
// row 4
jlbSUBCODE = new JLabel();
jlbSUBCODE.setFont(new java.awt.Font("Tahoma", 1, 18));  
jlbSUBCODE.setText("SUBJECT CODE:");
//txt_subcode = new JTextField(8);
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
Jcbtxt_subcode.setEnabled(true);
Jcbtxt_subcode.setFont(new java.awt.Font("Tahoma", 1, 18));  
Jcbtxt_subcode.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbSUBCODE);
jPanel1.add(Jcbtxt_subcode);

jlbSID = new JLabel();
jlbSID.setFont(new java.awt.Font("Tahoma", 1, 18));  
jlbSID.setText("STAFF ID:");
txt_sid = new JTextField(5);
txt_sid.setEnabled(false);
txt_sid.setFont(new java.awt.Font("Tahoma", 1, 14));  
txt_sid.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbSID);
jPanel1.add(txt_sid);
//ROW 5
jlbSTFNAME = new JLabel();
jlbSTFNAME.setFont(new java.awt.Font("Tahoma", 1, 18));  
jlbSTFNAME.setText("STAFF NAME:");
txt_stfname = new JTextField(25);
txt_stfname.setEnabled(false);
txt_stfname.setFont(new java.awt.Font("Tahoma", 1, 18));  
txt_stfname.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbSTFNAME);
jPanel1.add(txt_stfname);
/**************************/
jbtnVIEWATTENDANCE = new JButton();
jbtnVIEWATTENDANCE.setFont(new java.awt.Font("Tahoma", 1, 18)); 
jbtnVIEWATTENDANCE.setIcon(new ImageIcon(getClass().getResource("view.jpg"))); 
jbtnVIEWATTENDANCE.setText("View Class Percentage Attendance");
jbtnVIEWATTENDANCE.setIconTextGap(15);

jbtnGENREPORT= new JButton();
jbtnGENREPORT.setFont(new java.awt.Font("Tahoma", 1, 18));  
jbtnGENREPORT.setIcon(new ImageIcon(getClass().getResource("report.png")));  
jbtnGENREPORT.setText("Class Percentage Attendance Report");
jbtnGENREPORT.setIconTextGap(15);

jPnlButton = new JPanel();
jPnlButton.setBackground(new java.awt.Color(255, 153, 51));
jPnlButton.setLayout(new GridLayout(1,2,20,20));
jPnlButton.add(jbtnVIEWATTENDANCE);
jPnlButton.add(jbtnGENREPORT);
JPanel jPnlscrtb = new JPanel();
jPnlscrtb.setLayout(new GridLayout(1,1,20,20));
jScrollPane1 = new JScrollPane();
JTable_StaffStudPercAttnd = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},
//SELECT AC_YEAR,SEM,SUB_CODE,USN,PERC FROM V3
//new String [] {"ACADYR", "SEM","BRANCH","SECTION","SUBJECTCODE","SID","USN","STUDENT NAME","PERCENTAGE"});
new String [] {"USN","STUDENT NAME","PERCENTAGE"});
JTable_StaffStudPercAttnd.setModel(MytableModel);
jPnlscrtb.add(jScrollPane1);
JTable_StaffStudPercAttnd.setSelectionForeground(Color.white);
JTable_StaffStudPercAttnd.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_StaffStudPercAttnd);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 16));
jlbaptitle.setText("STAFF CLASS PERCENTAGE ATTENDANCE FORM");
jPanelptitle.add(jlbaptitle);
JPanel jPanel2N = new JPanel();
jPanel2N.setLayout(new GridLayout(3,1,20,20));
f=new JFrame();
JPanel jPanelIns = new JPanel();
jPanelIns.setBackground(new java.awt.Color(255, 255,153));
JLabel jlbIns = new JLabel();
jlbIns.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbIns.setText("IMPORTANT NOTE: FORM DATA CAN BE VIEWED ONLY AFTER ATTENDANCE HAS BEEN TAKEN");
jPanelIns.add(jlbIns);
jPanel2N.add(jPanelptitle);
jPanel2N.add(jPnlButton);
jPanel2N.add(jPanelIns);
f.add(jPanel2N,BorderLayout.NORTH);
f.add(jPnlscrtb,BorderLayout.CENTER);
f.add(jPanel1,BorderLayout.SOUTH);
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
f.setMinimumSize(new Dimension(1100, 500));
f.setPreferredSize(new Dimension(1100, 500));
f.setSize(1100,500);
f.setLocationRelativeTo(null); 
f.pack();
f.setVisible(true); 
JTable_StaffStudPercAttnd.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseClicked(java.awt.event.MouseEvent evt) {
JTable_StudentsMouseClicked(evt);
}
});

jbtnVIEWATTENDANCE.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnVIEWATTENDANCEActionPerformed(evt);
}
});
jbtnGENREPORT.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnGENREPORTActionPerformed(evt);
}
});
}



private void JTable_StudentsMouseClicked(java.awt.event.MouseEvent evt) {
int index = JTable_StaffStudPercAttnd.getSelectedRow();
ShowItem(index);
}

private void jbtnVIEWATTENDANCEActionPerformed(java.awt.event.ActionEvent evt) {
Show_StudPercAttnd_In_JTable();
}
private void jbtnGENREPORTActionPerformed(java.awt.event.ActionEvent evt) {
if(GenAttendReport())
JOptionPane.showMessageDialog(null,"Report Generated successfully");
else
JOptionPane.showMessageDialog(null,"Error:Failed to Generate the Report");
}
}
