import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.*;
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
public class AdminAttendenceViewForm{
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton Btn_Choose_Image;
private JButton jbtnGENREPORT;
private JButton jbtnFIRST;
private JButton jbtnLAST;
private JButton jbtnNEXT;
private JButton jbtnPREVIOUS;
private JTable  JTable_adminattend;
private JButton jbtnDELETE;
//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
private JLabel jlbACADYR;
private JComboBox Jcbtxt_acadyr;
private JLabel jlbSEM;
private JComboBox Jcbtxt_sem;
private JLabel jlbUSN;
private JComboBox Jcbtxt_usn;
private JLabel jlbNAME;
private JTextField txt_name;
private JLabel jlbBRANCH;
//private JTextField txt_branch;
private JComboBox Jcbtxt_branch;
private JLabel jlbSEC;
private JComboBox Jcbtxt_sec;
private JLabel jlbSUBCODE;
private JComboBox Jcbtxt_subcode;
private JLabel jlbSID;
private JComboBox Jcbtxt_sid;
private JLabel jlbADATE;
//private JTextField txt_adate;
//private com.toedter.calendar.JDateChooser txt_adate;
private JComboBox Jcbtxt_adate;
private JLabel jlbSLOT;
//private JTextField txt_slot;
private JComboBox Jcbtxt_slot;
private JLabel jlbSTATUS;
private JTextField txt_statu11s;
private JComboBox Jcbtxt_status;
private JPanel jPanel1;
private JPanel jPnlButton;
private JScrollPane jScrollPane1;
private JLabel lbl_image;
private java.sql.Date sqladate;
private String ImgPath = null;
private int sid;
private String loginName,loginRole,ssid,usn,status,staffname,studname,sec,subcode,branch,s_branch,s_sec,s_subcode,s_status,adate;
private Connection myconnection;
private PreparedStatement pstmt=null;
private ResultSet rs=null;
private CallableStatement cps=null;
private JFrame f;
private boolean once=true;
private int pos = 0,i_sem,i_acadyr,i_sid,i_slot;
private DefaultTableModel model;
private ArrayList<AdminAttndTempl> AdminAttndList,list;
private FileWriter fileWriter;
private AdminAttndTempl adminattend;
//private int acadyrlov ={}; //DONE
//private String [] slotlov={"1","2","3","4","5","6","7","8"}; //DONE
private ArrayList<String>  al_usnlist,al_subcodelist,al_branchlist,al_seclist,al_semlist,al_sidlist,al_stafflist,al_acadyrlist,al_adatelist,al_slotlist,al_statuslist;
//private String [] statuslov ={"P","A"}; //DONE
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form AdminAttendenceViewForm
  */
public AdminAttendenceViewForm(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String ssid, String staffname) {
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
  
public void CreateAdminAttendView()  {
pos = 0;    
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
Show_Attendance_in_JTable();
}

//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance.trim().equals("")
// Check Input Fields()
public boolean checkInputs(){
if((String)Jcbtxt_acadyr.getSelectedItem()==null  || (String)Jcbtxt_sem.getSelectedItem()== null || (String)Jcbtxt_branch.getSelectedItem() == null || (String)Jcbtxt_sec.getSelectedItem() == null 
|| (String)Jcbtxt_subcode.getSelectedItem() == null || (String)Jcbtxt_sid.getSelectedItem()==null || (String)Jcbtxt_adate.getSelectedItem()==null || (String)Jcbtxt_slot.getSelectedItem() == null )
return false;   
else
return true; 
}

public boolean GetStaffId(){
myconnection = getConnection();
al_stafflist=new ArrayList<String>();
String sql4 = "select distinct SID from ATTENDANCE order by SID";
try{
pstmt = myconnection.prepareStatement(sql4);
rs=pstmt.executeQuery();
while(rs.next()){
al_stafflist.add(rs.getString("SID"));
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-get STAFF ID lov"+e.getMessage());
return false;
}   
return true;
}

public boolean GetStudSem(){
myconnection = getConnection();
al_semlist=new ArrayList<String>();
String sql5 = "select distinct SEM from ATTENDANCE ORDER BY SEM DESC";
try{
pstmt = myconnection.prepareStatement(sql5);
rs=pstmt.executeQuery();
while(rs.next()){
al_semlist.add(rs.getString("SEM"));
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-get SEM lov"+e.getMessage());
return false;
}   
return true;
}

public boolean GetSlot(){
myconnection = getConnection();
al_slotlist=new ArrayList<String>();
String sql7 = "select distinct SLOT from ATTENDANCE ORDER BY SLOT DESC";
try{
pstmt = myconnection.prepareStatement(sql7);
rs=pstmt.executeQuery();
while(rs.next()){
al_slotlist.add(rs.getString("SLOT"));
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-get SLOT lov"+e.getMessage());
return false;
}   
return true;
}

public boolean GetAcadyr(){
myconnection = getConnection();
al_acadyrlist =new ArrayList<String>();
String sql1 = "select distinct AC_YEAR from ATTENDANCE ORDER BY AC_YEAR DESC";
try{
pstmt = myconnection.prepareStatement(sql1);
rs=pstmt.executeQuery();
while(rs.next()){
al_acadyrlist.add(rs.getString("AC_YEAR"));
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-getacadyrlov"+e.getMessage());
return false;
}   
return true;
}

public boolean GetStaffSubCode(){
myconnection = getConnection();
al_subcodelist=new ArrayList<String>();
String sql2 = "select distinct SUB_CODE from ATTENDANCE ORDER BY SUB_CODE DESC";
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

public boolean GetUSN(){
myconnection = getConnection();
al_usnlist=new ArrayList<String>();
String sql9 = "select distinct USN from ATTENDANCE ORDER BY USN DESC";
try{
pstmt = myconnection.prepareStatement(sql9);
rs=pstmt.executeQuery();
while(rs.next()){
al_usnlist.add(rs.getString("USN"));
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-get USN lov"+e.getMessage());
return false;
}   
return true;
}
public boolean GetAdate(){
myconnection = getConnection();
al_adatelist=new ArrayList<String>();
String sql11 = "select distinct to_char(adate,'dd-MM-yyyy') ADATE from ATTENDANCE ORDER BY ADATE DESC";
try{
pstmt = myconnection.prepareStatement(sql11);
rs=pstmt.executeQuery();
while(rs.next()){
al_adatelist.add(rs.getString("ADATE"));
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-get DATE LOV lov"+e.getMessage());
return false;
}   
return true;
}


public boolean GetStatus(){
myconnection = getConnection();
al_statuslist=new ArrayList<String>();
String sql8 = "select distinct STATUS from ATTENDANCE ORDER BY STATUS DESC";
try{
pstmt = myconnection.prepareStatement(sql8);
rs=pstmt.executeQuery();
while(rs.next()){
al_statuslist.add(rs.getString("STATUS"));
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-get STATUS lov"+e.getMessage());
return false;
}   
return true;
}


public boolean GetStudBranch(){
myconnection = getConnection();
al_branchlist=new ArrayList<String>();
String sql3 = "select distinct BRANCH from ATTENDANCE ORDER BY BRANCH";
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
String sql4 = "select distinct SEC from ATTENDANCE ORDER BY SEC";
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

    
public boolean GenClassAttndReport(){
myconnection = getConnection();
//String sqlattnd = "select A.ac_year,A.sem,A.branch,A.sec,A.sub_code,A.usn,B.name, A.sid,to_char(A.adate,'dd-MM-yyyy') adate,A.slot,A.status from attendance A, student B, staff C"
//+" where A.ac_year=? and A.sem=? and A.branch=? and A.sec=? and A.sub_code=? and A.sid=? and A.adate =? and A.slot=? and A.usn=B.usn and A.SID=C.SID and A.adate <= sysdate  order by usn";
String sqlattnd = "select A.ac_year,A.sem,A.branch,A.sec,A.sub_code,A.usn,B.name, A.sid,C.SNAME,to_char(A.adate,'dd-MM-yyyy') adate,A.slot,A.status from attendance A, student B, staff C"
+" where A.usn=B.usn and A.SID=C.SID order by A.adate,A.ac_year,A.sem,A.branch,A.sec,A.sub_code,A.sid";

//SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String adate=(String)Jcbtxt_adate.getSelectedItem();
String FILE_HEADER = "ACAD_YR\t\tSEMESTER\t\tBRANCH\t\tSECTION\t\tSUBJECTCODE\t\tUSN\t\tSTUDENTNAME\t\tSTAFF ID\t\tSTAFF NAME\t\tATTNDDATE\t\tSLOT\t\tSTATUS\n";
fileWriter = null;
try {
fileWriter = new FileWriter("AdminAttendExport"+"_"+adate+".xls");
fileWriter.append("\t\tRNS INSITUTE OF TECHNOLOGY\n");
fileWriter.append("\t\tAll Attendance Report\n");
fileWriter.append(FILE_HEADER);
} 
catch (Exception e) {
System.out.println("Error in CsvFileWriter !!!");
e.printStackTrace();
} 
try {
pstmt = myconnection.prepareStatement(sqlattnd);
rs=pstmt.executeQuery();
if(rs==null) return false;
else
{
while(rs.next()){
i_acadyr=Integer.parseInt(rs.getString("AC_YEAR"));
i_sem=Integer.parseInt(rs.getString("SEM"));
branch=rs.getString("BRANCH");
sec=rs.getString("SEC");
subcode=rs.getString("SUB_CODE");
usn=rs.getString("USN");
studname =rs.getString("NAME");
i_sid=Integer.parseInt(rs.getString("SID"));
staffname =rs.getString("SNAME");
adate =rs.getString("ADATE");
i_slot =Integer.parseInt(rs.getString("SLOT"));
status =rs.getString("STATUS");
try{
//String FILE_HEADER = "ACAD_YR\t\tSEMESTER\t\tBRANCH\t\tSECTION\t\tSUBJECTCODE\t\tUSN\t\tSTUDENTNAME\t\tSTAFF ID\t\tSTAFF NAME\t\tATTNDDATE\t\tSLOT\t\tSTATUS\n";
    
fileWriter.append(i_acadyr+"\t\t"+i_sem+"\t\t"+branch+"\t\t"+sec+"\t\t"+subcode+"\t\t"+usn+"\t\t"+studname+"\t\t"+ i_sid+"\t\t"+staffname+"\t\t"+ adate+"\t\t"+i_slot +"\t\t"+ status+"\n");
}
catch (Exception e) {
System.out.println("Error in CsvFileWriter !!!");
e.printStackTrace();
return false;
}
}
//System.out.println("data added successfully to file!!!");
}
try {
fileWriter.flush();
fileWriter.close();
} 
catch (IOException e) {
System.out.println("Error while flushing/closing fileWriter !!!");
e.printStackTrace();
return false;
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+e.getMessage());
return false;
}   
return true; 
}    


public ArrayList<AdminAttndTempl> getAdminAttndList(){   
AdminAttndList  = new ArrayList<AdminAttndTempl>();
myconnection = getConnection();
String sqlattnd = "select A.ac_year,A.sem,A.branch,A.sec,A.sub_code,A.usn,B.name, A.sid, C.sname, to_char(A.adate,'dd-MM-yyyy') adate,A.slot,A.status from attendance A, student B, staff C"
+" where  A.usn=B.usn and A.SID=C.SID  order by A.adate,A.ac_year,A.sem,A.branch,A.sec,A.sub_code,A.sid";
try {
pstmt = myconnection.prepareStatement(sqlattnd);
rs=pstmt.executeQuery();

if(rs==null) return null;
else
{
while(rs.next()){
adminattend = new AdminAttndTempl(Integer.parseInt(rs.getString("AC_YEAR")),Integer.parseInt(rs.getString("SEM")),rs.getString("BRANCH"),rs.getString("SEC"),rs.getString("SUB_CODE"),rs.getString("USN"),rs.getString("NAME"),Integer.parseInt(rs.getString("SID")),rs.getString("SNAME"),rs.getString("ADATE"),Integer.parseInt(rs.getString("SLOT")),rs.getString("STATUS"));
AdminAttndList.add(adminattend);
}
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+e.getMessage());
return null;
}   
return AdminAttndList; 
}
//      2 - Populate The JTable
public void Show_Attendance_in_JTable(){
if(getAdminAttndList()==null){
JOptionPane.showMessageDialog(null,"NO ATTENDANCE DATA AVAILABLE");    
}
else
{  
list = getAdminAttndList();
model = (DefaultTableModel)JTable_adminattend.getModel();
model.setRowCount(0);
Object[] col = new Object[12];
for(int i = 0; i < list.size(); i++){
col[0] = list.get(i).getacyear();
col[1] = list.get(i).getsem();
col[2] = list.get(i).getbranch();
col[3] = list.get(i).getsec();
col[4] = list.get(i).getsubcode();
col[5] = list.get(i).getusn();
col[6] = list.get(i).getstudname();
col[7] = list.get(i).getsid();
col[8] = list.get(i).getstaffname();
col[9] = list.get(i).getadate();
col[10] = list.get(i).getslot();
col[11] = list.get(i).getstatus();
model.addRow(col);
}
}
}
// Show Data In Inputs
public void ShowItem(int index){
if(list.size()>0){
Jcbtxt_acadyr.setSelectedItem(list.get(index).getacyear());
Jcbtxt_sem.setSelectedItem(list.get(index).getsem());//(String)Jcbtxt_branch.getSelectedItem
Jcbtxt_branch.setSelectedItem(list.get(index).getbranch());
Jcbtxt_sec.setSelectedItem(list.get(index).getsec());
Jcbtxt_subcode.setSelectedItem(list.get(index).getsubcode());
Jcbtxt_usn.setSelectedItem(list.get(index).getusn());
Jcbtxt_sid.setSelectedItem(list.get(index).getsid()); //(String)Jcbtxt_sid.getSelectedItem()
Jcbtxt_adate.setSelectedItem(list.get(index).getadate());
Jcbtxt_slot.setSelectedItem(list.get(index).getslot());
Jcbtxt_status.setSelectedItem(list.get(index).getstatus());
}
else
{
JOptionPane.showMessageDialog(null,"NO ATTENDANCE DATA AVAILABLE");
}
}

private void initComponents() {
jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 153));
jPanel1.setLayout(new GridLayout(4,6,20,20));
JLabel jlbloginid = new JLabel();
jlbloginid.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbloginid.setText("Your loginid is: "+getLoginName());
jPanel1.add(jlbloginid);

JLabel jlbstaffid = new JLabel();
jlbstaffid.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbstaffid.setText("Your staff id is: "+getssid());
jPanel1.add(jlbstaffid);

JLabel jlbusername = new JLabel();
jlbusername.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbusername.setText("You are: "+getstaffname());
jPanel1.add(jlbusername);

JLabel jlbrole = new JLabel();
jlbrole.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbrole.setText("Your role is: "+getLoginrole());
jPanel1.add(jlbrole);
//jPanel1.add(txt_acadyr);
//row1
jlbACADYR = new JLabel();
jlbACADYR.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbACADYR.setText("ACADYR:");
if(GetAcadyr()){
// lov to be implemented  
Jcbtxt_acadyr = new JComboBox();
Jcbtxt_acadyr.setModel(new DefaultComboBoxModel(al_acadyrlist.toArray()));
}
else
{
JOptionPane.showMessageDialog(null,"Student acadyear is invalid");
//System.exit(0);
}
Jcbtxt_acadyr.setFont(new java.awt.Font("Tahoma", 1, 14));  
Jcbtxt_acadyr.setPreferredSize(new java.awt.Dimension(24, 20));  
jPanel1.add(jlbACADYR);
jPanel1.add(Jcbtxt_acadyr);
jlbSEM = new JLabel();
jlbSEM.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbSEM.setText("SEMESTER:");
if(GetStudSem()){
// lov to be implemented  
Jcbtxt_sem = new JComboBox();
Jcbtxt_sem.setModel(new DefaultComboBoxModel(al_semlist.toArray()));
}
else
{
JOptionPane.showMessageDialog(null,"Student semester is invalid");
//System.exit(0);
}
Jcbtxt_sem.setFont(new java.awt.Font("Tahoma", 1, 14));  
Jcbtxt_sem.setPreferredSize(new java.awt.Dimension(24, 20));  
jPanel1.add(jlbSEM);
jPanel1.add(Jcbtxt_sem);

//row 2
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
jPanel1.add(jlbBRANCH);
jPanel1.add(Jcbtxt_branch);
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
jPanel1.add(jlbSEC);
jPanel1.add(Jcbtxt_sec);
// row3
jlbADATE = new JLabel();
jlbADATE.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbADATE.setText("ATTND DATE:");
if(GetAdate()){
// lov to be implemented  
Jcbtxt_adate = new JComboBox();
Jcbtxt_adate.setModel(new DefaultComboBoxModel(al_adatelist.toArray()));
}
else
{
JOptionPane.showMessageDialog(null,"Student subcode is invalid");
//System.exit(0);
}
Jcbtxt_adate.setFont(new java.awt.Font("Tahoma", 1, 14)); 
Jcbtxt_adate.setPreferredSize(new java.awt.Dimension(24, 20)); 
jPanel1.add(jlbADATE);
jPanel1.add(Jcbtxt_adate);

//row5
jlbSLOT = new JLabel();
jlbSLOT.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbSLOT.setText("SLOT NO:");
if(GetSlot()){
// lov to be implemented  
Jcbtxt_slot = new JComboBox();
Jcbtxt_slot.setModel(new DefaultComboBoxModel(al_slotlist.toArray()));
}
else
{
JOptionPane.showMessageDialog(null,"Student subcode is invalid");
//System.exit(0);
}
Jcbtxt_slot.setFont(new java.awt.Font("Tahoma", 1, 14));  
Jcbtxt_slot.setPreferredSize(new java.awt.Dimension(24, 20));  
jPanel1.add(jlbSLOT);
jPanel1.add(Jcbtxt_slot);

jlbSTATUS = new JLabel();
jlbSTATUS.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbSTATUS.setText("STATUS:");
// = new JComboBox();/////GetStatus
if(GetStatus()){
// lov to be implemented  
Jcbtxt_status = new JComboBox();
Jcbtxt_status.setModel(new DefaultComboBoxModel(al_statuslist.toArray()));
}
else
{
JOptionPane.showMessageDialog(null,"ATTENDANCE STATUS CODE is invalid");
//System.exit(0);
}
Jcbtxt_status.setFont(new java.awt.Font("Tahoma", 1, 14));  
Jcbtxt_status.setPreferredSize(new java.awt.Dimension(24, 20));  
jPanel1.add(jlbSTATUS);
jPanel1.add(Jcbtxt_status);
jlbUSN = new JLabel();
jlbUSN.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbUSN.setText("USN:");
//txt_usn = new JTextField(15);
if(GetUSN()){
// lov to be implemented  
Jcbtxt_usn = new JComboBox();
Jcbtxt_usn.setModel(new DefaultComboBoxModel(al_usnlist.toArray()));
}
else
{
JOptionPane.showMessageDialog(null,"Student USN is invalid");
//System.exit(0);
}
Jcbtxt_usn.setFont(new java.awt.Font("Tahoma", 1, 14));  
//txt_usn.setEnabled(false);
Jcbtxt_usn.setPreferredSize(new java.awt.Dimension(24, 20));

jPanel1.add(jlbUSN);
jPanel1.add(Jcbtxt_usn);

//row4
jlbSID = new JLabel();
jlbSID.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbSID.setText("STAFF ID:");
if(GetStaffId()){
// lov to be implemented  
Jcbtxt_sid = new JComboBox();
Jcbtxt_sid.setModel(new DefaultComboBoxModel(al_stafflist.toArray()));
}
else
{
JOptionPane.showMessageDialog(null,"Student semester is invalid");
//System.exit(0);
}
Jcbtxt_sid.setFont(new java.awt.Font("Tahoma", 1, 14));  
Jcbtxt_sid.setPreferredSize(new java.awt.Dimension(24, 20));
//Jcbtxt_sid.setText(ssid);
jPanel1.add(jlbSID);
jPanel1.add(Jcbtxt_sid);
/**************************/
jbtnGENREPORT = new JButton();
jbtnGENREPORT.setFont(new java.awt.Font("Tahoma", 1, 14));  
jbtnGENREPORT.setIcon(new ImageIcon(getClass().getResource("report.png")));  
jbtnGENREPORT.setText("EXCEL REPORT");
jbtnGENREPORT.setIconTextGap(15);
//jbtnGENREPORT.setBackground(new java.awt.Color(255, 255, 200));
jbtnGENREPORT.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
//System.out.println("GENERATE EXCEL REPORT");      
jbtnGENREPORTActionPerformed(evt);
}
});
jbtnFIRST = new JButton();
jbtnFIRST.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnFIRST.setIcon(new ImageIcon(getClass().getResource("first.png"))); 
jbtnFIRST.setText("First");
jbtnFIRST.setIconTextGap(15);
jbtnFIRST.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnFIRSTActionPerformed(evt);
}
});
jbtnPREVIOUS = new JButton();
jbtnPREVIOUS.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnPREVIOUS.setIcon(new ImageIcon(getClass().getResource("previous.png"))); 
jbtnPREVIOUS.setText("Previous");
jbtnPREVIOUS.setIconTextGap(15);
jbtnPREVIOUS.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnPREVIOUSActionPerformed(evt);
}
});
jbtnLAST = new JButton();
jbtnLAST.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnLAST.setIcon(new ImageIcon(getClass().getResource("last.png"))); 
jbtnLAST.setText("Last");
jbtnLAST.setIconTextGap(15);
jbtnLAST.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnLASTActionPerformed(evt);
}
});
jbtnNEXT = new JButton();
jbtnNEXT.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnNEXT.setIcon(new ImageIcon(getClass().getResource("next.png"))); 
jbtnNEXT.setText("Next");
jbtnNEXT.setIconTextGap(15);
jbtnNEXT.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnNEXTActionPerformed(evt);
}
});
jPnlButton = new JPanel();
jPnlButton.setLayout(new GridLayout(1,3,20,20));
jPnlButton.setBackground(new java.awt.Color(255, 153, 51));
jPnlButton.add(jbtnGENREPORT);
jPnlButton.add(jbtnFIRST);
jPnlButton.add(jbtnNEXT);
jPnlButton.add(jbtnPREVIOUS);
jPnlButton.add(jbtnLAST);
JPanel jPnlscrtb = new JPanel();
jPnlscrtb.setLayout(new GridLayout(1,1,20,20));
jScrollPane1 = new JScrollPane();
JTable_adminattend = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},new String [] {"ACAD YEAR", "SEMESTER","BRANCH","SECTION","SUBJECT CODE","USN","STUDENTNAME", "SID","STAFF NAME","ADATE","SLOT","STATUS"});
//new Object [][] {},new String [] {"USN","STUDENT NAME","STATUS"});
JTable_adminattend.setModel(MytableModel);
jPnlscrtb.add(jScrollPane1);
JTable_adminattend.setSelectionForeground(Color.white);
JTable_adminattend.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_adminattend);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbaptitle.setText("ATTENDANCE VIEW-FOR ADMIN ROLE");
jPanelptitle.add(jlbaptitle);
JPanel jPanel2N = new JPanel();
jPanel2N.setLayout(new GridLayout(3,1,20,20));
JPanel jPanelIns = new JPanel();
jPanelIns.setBackground(new java.awt.Color(255, 255,153));
JLabel jlbIns = new JLabel();
jlbIns.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbIns.setText("IMPORTANT NOTE: ATTENDANCE DATA CAN BE VIEWED ONLY IF ATTENDANCE HAS BEEN GENERATED");
jPanelIns.add(jlbIns);
f=new JFrame();
jPanel2N.add(jPanelptitle);
jPanel2N.add(jPnlButton);
jPanel2N.add(jPanelIns);
f.add(jPanel1, BorderLayout.SOUTH);
f.add(jPanel2N,BorderLayout.NORTH);
//f.add(jPnlButton, BorderLayout.WEST);
f.add(jPnlscrtb, BorderLayout.CENTER);
//f.add(jPanelIns, BorderLayout.NORTH);
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
f.setMinimumSize(new Dimension(1300, 800));
f.setPreferredSize(new Dimension(1300, 800));
f.setSize(1300,800);
f.setLocationRelativeTo(null); 
f.pack();
f.setVisible(true); 
JTable_adminattend.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseClicked(java.awt.event.MouseEvent evt) {
JTable_adminattendMouseClicked(evt);
}
});
}

public Boolean GetAcadyrSem(){
myconnection = getConnection();
i_acadyr=Integer.parseInt((String)Jcbtxt_acadyr.getSelectedItem());
i_sem=Integer.parseInt((String)Jcbtxt_sem.getSelectedItem());
try{
//String adate=txt_adate.getText();
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String adate=sdf.format((String)Jcbtxt_adate.getSelectedItem());
java.util.Date udob=sdf.parse(adate);
sqladate=new java.sql.Date(udob.getTime());
//System.out.println("Data input Given 2:"+sqladate+" " +adate);
}
catch(ParseException ex){
System.out.println("GENERATE-date parse Exception");   
System.out.println(ex);
}
String query  = "select count(*) onerecord from attendance";
try {
pstmt=myconnection.prepareStatement(query);
ResultSet rs=pstmt.executeQuery();  
if (rs.next()){
int i_mcount=Integer.parseInt(rs.getString("onerecord"));
//System.out.println("No of records="+i_mcount);
if(i_mcount > 0)
return true;
else
return false;
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

private void JTable_adminattendMouseClicked(java.awt.event.MouseEvent evt) {
int index = JTable_adminattend.getSelectedRow();
ShowItem(index);
}

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
private void jbtnGENREPORTActionPerformed(java.awt.event.ActionEvent evt) {
if(GenClassAttndReport())
//System.out.println("");
JOptionPane.showMessageDialog(null,"Report Generated Sucessfully");   
else
//System.out.println("Report Not generated");
JOptionPane.showMessageDialog(null,"Report Not generated");   
}
}
