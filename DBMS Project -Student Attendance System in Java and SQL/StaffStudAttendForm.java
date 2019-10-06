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
public class StaffStudAttendForm{
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton Btn_Choose_Image;
private JButton jbtnGENREPORT;
private JButton jbtnGENERATEATTENDANCE;
private JButton jbtnLAST;
private JButton jbtnNEXT;
private JButton jbtnPREVIOUS;
private JTable  JTable_StaffStudAttend;
private JButton jbtnSAVEATTENDANCE;
private JButton jbtnDELETE;
//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
private JLabel jlbACADYR;
private JTextField txt_acadyr;
private JLabel jlbSEM;
private JComboBox Jcbtxt_sem;
private JLabel jlbUSN;
private JTextField txt_usn;
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
private JTextField txt_sid;
private JLabel jlbADATE;
//private JTextField txt_adate;
private com.toedter.calendar.JDateChooser txt_adate;
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
private String loginName,loginRole,ssid,usn,status,staffname,studname,sec,subcode,branch,s_branch,s_sec,s_subcode,s_status;
private Connection myconnection;
private PreparedStatement pstmt=null;
private ResultSet rs=null;
private CallableStatement cps=null;
private JFrame f;
private boolean once=true;
private int pos = 0,i_sem,i_acadyr,i_sid,i_slot;
//private ArrayList<StaffStudentAttndTemp> list;
private DefaultTableModel model;
private ArrayList<StaffStudentAttndTemp> StaffstudattendList,list;
private FileWriter fileWriter;
//private int acadyrlov ={}; //DONE
private String [] slotlov={"1","2","3","4","5","6","7","8"}; //DONE
private ArrayList<String> al_subcodelist,al_branchlist,al_seclist,al_semlist;
private String [] statuslov ={"P","A"}; //DONE
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form StaffStudAttendForm
  */
public StaffStudAttendForm(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String ssid, String staffname) {
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
  
public void CreateStaffAttendForm()  {
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
//Show_Students_In_JTable();
}

//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance.trim().equals("")
// Check Input Fields()
public boolean checkInputs(){
if(txt_acadyr.getText().trim().equals("")  || (String)Jcbtxt_sem.getSelectedItem()== null || (String)Jcbtxt_branch.getSelectedItem() == null || (String)Jcbtxt_sec.getSelectedItem() == null 
|| Jcbtxt_subcode.getSelectedItem() == null || txt_sid.getText().trim().equals("") || txt_adate.getDate().equals("") || Jcbtxt_slot.getSelectedItem() == null )
return false;   
else
return true; 
}

public boolean GetStudSem(){
myconnection = getConnection();
al_semlist=new ArrayList<String>();
String sql5 = "select distinct B.SEM SEM from subjects B,staff_alloc A  where A.sub_code=B.sub_code and A.sid=?  ORDER BY SEM DESC";
try{
pstmt = myconnection.prepareStatement(sql5);
pstmt.setInt(1,sid);
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


public boolean GetCurrentAcadyr(){
myconnection = getConnection();
String sql1 = "select distinct ac_year from period where sysdate between st_date and end_date ORDER BY AC_YEAR DESC";
try{
pstmt = myconnection.prepareStatement(sql1);
rs=pstmt.executeQuery();
if(rs.next()){
i_acadyr=Integer.parseInt(rs.getString("AC_YEAR"));
}
else
return false;
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
//List<String> ls = new ArrayList<String>(); 
//jComboBox.setModel(new DefaultComboBoxModel(al_subcodelist.toArray()));
String sql2 = "select distinct sub_code from staff_alloc where sid=? ORDER BY SUB_CODE";
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
String sql3 = "select distinct branch from staff_alloc where sid=? order by branch";
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
String sql4 = "select distinct SEC from staff_alloc where sid=? ORDER BY SEC";
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

    
public boolean GenClassAttndReport(){
myconnection = getConnection();
String sqlattnd = "select A.ac_year,A.sem,A.branch,A.sec,A.sub_code,A.usn,B.name, A.sid,to_char(A.adate,'dd-MM-yyyy') adate,A.slot,A.status from attendance A, student B"
+" where A.ac_year=? and A.sem=? and A.branch=? and A.sec=? and A.sub_code=? and A.sid=? and A.adate =? and A.slot=? and A.usn=B.usn and A.adate <= sysdate  order by usn";
i_acadyr=Integer.parseInt(txt_acadyr.getText());
i_sem=Integer.parseInt((String)Jcbtxt_sem.getSelectedItem());
s_branch=(String)Jcbtxt_branch.getSelectedItem();
s_branch=s_branch.toUpperCase();
s_sec=(String)Jcbtxt_sec.getSelectedItem();
s_sec=s_sec.toUpperCase();
s_subcode =(String)Jcbtxt_subcode.getSelectedItem();
s_subcode=s_subcode.toUpperCase();
i_sid=Integer.parseInt(txt_sid.getText());
i_slot=Integer.parseInt((String)Jcbtxt_slot.getSelectedItem());  
try{
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String adate=sdf.format(txt_adate.getDate());
java.util.Date udob=sdf.parse(adate);
sqladate=new java.sql.Date(udob.getTime());
}
catch(ParseException ex){
System.out.println("catch block Take Attendance- date parse Exception");   
System.out.println(ex);
return false;
}
String FILE_HEADER = "USN\t\tSTUDENTNAME\t\tATTENDANCE STATUS\n";
fileWriter = null;
try {
fileWriter = new FileWriter("ClassAttend"+"_"+s_branch+"_"+i_sem+s_sec+"_"+i_slot+"_"+sqladate+".xls");
fileWriter.append("\t\tRNS INSITUTE OF TECHNOLOGY\n");
fileWriter.append("\t\tDaily Class Attendance Report\n");
fileWriter.append("\t\tDepartment"+branch+"\n");
fileWriter.append("ACAD YEAR:\t"+i_acadyr+"\t\tCLASS:\t"+i_sem+s_sec+"\n");
fileWriter.append("STAFF ID:\t"+i_sid+"\t\tSTAFF NAME:\t"+staffname+"\n");
fileWriter.append("SLOT:\t"+i_slot+"\t\tATTND DATE:\t"+sqladate+"\n");
fileWriter.append(FILE_HEADER);
//System.out.println("CSV file Header successfully Created!!!");
} 
catch (Exception e) {
System.out.println("Error in CsvFileWriter !!!");
e.printStackTrace();
} 
try {
pstmt = myconnection.prepareStatement(sqlattnd);
pstmt.setInt(1,i_acadyr);
pstmt.setInt(2,i_sem);
pstmt.setString(3,s_branch);
pstmt.setString(4,s_sec);
pstmt.setString(5,s_subcode);
pstmt.setInt(6,i_sid);
pstmt.setDate(7,sqladate);
pstmt.setInt(8,i_slot);
//System.out.println("Try block Take attendance:"+sqladate);
rs=pstmt.executeQuery();
int rscntr=0;
if(rs==null) return false;
else
{
while(rs.next()){
usn=rs.getString("USN");
studname =rs.getString("NAME");
status =rs.getString("STATUS");
try{
fileWriter.append(usn+"\t\t"+studname+"\t\t"+status+"\n");
rscntr++;
}
catch (Exception e) {
System.out.println("Error in CsvFileWriter !!!");
e.printStackTrace();
return false;
}
}
if (rscntr==0)
return false;
else
System.out.println("data added successfully to file!!!");
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


public ArrayList<StaffStudentAttndTemp> getStaffstudattendList(){  
StaffstudattendList  = new ArrayList<StaffStudentAttndTemp>();
myconnection = getConnection();
String sqlattnd = "select A.ac_year,A.sem,A.branch,A.sec,A.sub_code,A.usn,B.name, A.sid,to_char(A.adate,'dd-MM-yyyy') adate,A.slot,A.status from attendance A, student B"
+" where A.ac_year=? and A.sem=? and A.branch=? and A.sec=? and A.sub_code=? and A.sid=? and A.adate =? and A.slot=? and A.usn=B.usn and A.adate <= sysdate  order by usn";
i_acadyr=Integer.parseInt(txt_acadyr.getText());
i_sem=Integer.parseInt((String)Jcbtxt_sem.getSelectedItem());
s_branch=(String)Jcbtxt_branch.getSelectedItem();
s_branch=s_branch.toUpperCase();
s_sec=(String)Jcbtxt_sec.getSelectedItem();
s_sec=s_sec.toUpperCase();
s_subcode =(String)Jcbtxt_subcode.getSelectedItem();
s_subcode=s_subcode.toUpperCase();
i_sid=Integer.parseInt(txt_sid.getText());
i_slot=Integer.parseInt((String)Jcbtxt_slot.getSelectedItem());
try{
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String adate=sdf.format(txt_adate.getDate());
java.util.Date udob=sdf.parse(adate);
sqladate=new java.sql.Date(udob.getTime());
}
catch(ParseException ex){
System.out.println("catch block Take Attendance- date parse Exception");   
System.out.println(ex);
}
try {
pstmt = myconnection.prepareStatement(sqlattnd);
pstmt.setInt(1,i_acadyr);
pstmt.setInt(2,i_sem);
pstmt.setString(3,s_branch);
pstmt.setString(4,s_sec);
pstmt.setString(5,s_subcode);
pstmt.setInt(6,i_sid);
pstmt.setDate(7,sqladate);
pstmt.setInt(8,i_slot);
//System.out.println("Try block Take attendance:"+sqladate);
rs=pstmt.executeQuery();
StaffStudentAttndTemp staffstudattend;
if(rs==null) return null;
else
{
while(rs.next()){
staffstudattend = new StaffStudentAttndTemp(Integer.parseInt(rs.getString("AC_YEAR")),Integer.parseInt(rs.getString("SEM")),rs.getString("BRANCH"),rs.getString("SEC"),rs.getString("SUB_CODE"),rs.getString("USN"),rs.getString("NAME"),Integer.parseInt(rs.getString("SID")),rs.getString("ADATE"),Integer.parseInt(rs.getString("SLOT")),rs.getString("STATUS"));
StaffstudattendList.add(staffstudattend);
//System.out.println("Printing row ="+ii++);
}
}
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+e.getMessage());
return null;
}   
return StaffstudattendList; 
}
//      2 - Populate The JTable
public void Show_Students_In_JTable(){
//ArrayList<StaffStudentAttndTemp> list = getStaffstudattendList();
if(getStaffstudattendList()==null){
JOptionPane.showMessageDialog(null,"NO ATTENDANCE DATA FOR THE STAFF OR SUBJECT OR SECTION OR BRANCH OR SLOT");    
}
else
{
list = getStaffstudattendList();
model = (DefaultTableModel)JTable_StaffStudAttend.getModel();
model.setRowCount(0);
Object[] col = new Object[3];
//System.out.println("List size = "+list.size());
for(int i = 0; i < list.size(); i++){
//col[0] = list.get(i).getacyear();
//col[1] = list.get(i).getsem();
//col[2] = list.get(i).getbranch();
//col[3] = list.get(i).getsec();
//col[4] = list.get(i).getsubcode();
col[0] = list.get(i).getusn();
col[1] = list.get(i).getstudname();
//col[6] = list.get(i).getsid();
//col[7] = list.get(i).getadate();
//col[8] = list.get(i).getslot();
col[2] = list.get(i).getstatus();
model.addRow(col);
//System.out.println("Model col ="+i);
}
}
}
// Show Data In Inputs
public void ShowItem(int index){
//System.out.printl n("Show size of list "+list.size());
if(list.size()>0) {
txt_acadyr.setText(String.valueOf(i_acadyr));
Jcbtxt_sem.setSelectedItem(String.valueOf(i_sem));//(String)Jcbtxt_branch.getSelectedItem
Jcbtxt_branch.setSelectedItem(s_branch);
Jcbtxt_sec.setSelectedItem(s_sec);
Jcbtxt_subcode.setSelectedItem(s_subcode);
txt_usn.setText(list.get(index).getusn());
txt_sid.setText(String.valueOf(i_sid));
try {
    Date maDate=null;
    maDate = new SimpleDateFormat("dd-MM-yyyy").parse((String)list.get(index).getadate());
    txt_adate.setDate(maDate);
}
catch (ParseException ex) {
            System.out.println("Date Parse Exception:"+ex);
            ex.printStackTrace();
        }
Jcbtxt_slot.setSelectedItem(i_slot);
//Jcbtxt_status.setSelectedItem(s_status);
Jcbtxt_status.setSelectedItem(list.get(index).getstatus());
}
else
{
JOptionPane.showMessageDialog(null,"List is empty");
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
txt_acadyr = new JTextField(32);
txt_acadyr.setFont(new java.awt.Font("Tahoma", 1, 14)); 
txt_acadyr.setPreferredSize(new java.awt.Dimension(24, 20)); 
jPanel1.add(jlbACADYR);
jPanel1.add(txt_acadyr);
if (GetCurrentAcadyr()){
txt_acadyr.setText(String.valueOf(i_acadyr));
txt_acadyr.setEnabled(false);
}
else
{
    JOptionPane.showMessageDialog(null,"Academic year Not setup");
 //   System.exit(0);
}
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

//txt_branch.setText(al_subcodelist);al_subcodelist.get(i).s_branch;
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

//txt_sec = new JTextField(10);
Jcbtxt_sec.setFont(new java.awt.Font("Tahoma", 1, 14)); 
Jcbtxt_sec.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbSEC);
jPanel1.add(Jcbtxt_sec);



// row3


jlbADATE = new JLabel();
jlbADATE.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbADATE.setText("ATTND DATE:");
txt_adate = new com.toedter.calendar.JDateChooser();
txt_adate.setDateFormatString("dd-MM-yyyy");
Date date = new Date(System.currentTimeMillis());
txt_adate.setDate(date);
txt_adate.setFont(new java.awt.Font("Tahoma", 1, 14)); 
txt_adate.setPreferredSize(new java.awt.Dimension(24, 20)); 
jPanel1.add(jlbADATE);
jPanel1.add(txt_adate);

//row5
jlbSLOT = new JLabel();
jlbSLOT.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbSLOT.setText("SLOT NO:");
Jcbtxt_slot = new JComboBox(slotlov);
Jcbtxt_slot.setFont(new java.awt.Font("Tahoma", 1, 14)); 
Jcbtxt_slot.setPreferredSize(new java.awt.Dimension(24, 20)); 
jPanel1.add(jlbSLOT);
jPanel1.add(Jcbtxt_slot);

jlbSTATUS = new JLabel();
jlbSTATUS.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbSTATUS.setText("STATUS:");
Jcbtxt_status = new JComboBox(statuslov);
Jcbtxt_status.setFont(new java.awt.Font("Tahoma", 1, 14)); 
Jcbtxt_status.setPreferredSize(new java.awt.Dimension(24, 20)); 
jPanel1.add(jlbSTATUS);
jPanel1.add(Jcbtxt_status);
jlbUSN = new JLabel();
jlbUSN.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbUSN.setText("USN:");
txt_usn = new JTextField(15);
txt_usn.setFont(new java.awt.Font("Tahoma", 1, 14)); 
//txt_usn.setEnabled(false);
txt_usn.setPreferredSize(new java.awt.Dimension(24, 20));
txt_usn.setEnabled(false);
jPanel1.add(jlbUSN);
jPanel1.add(txt_usn);

//row4
jlbSID = new JLabel();
jlbSID.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbSID.setText("STAFF ID:");
txt_sid = new JTextField(32);
txt_sid.setFont(new java.awt.Font("Tahoma", 1, 14)); 
txt_sid.setPreferredSize(new java.awt.Dimension(24, 20));
txt_sid.setText(ssid);
txt_sid.setEnabled(false);
jPanel1.add(jlbSID);
jPanel1.add(txt_sid);
/**************************/
jbtnSAVEATTENDANCE = new JButton();
jbtnSAVEATTENDANCE.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnSAVEATTENDANCE.setIcon(new ImageIcon(getClass().getResource("Renew.png"))); 
jbtnSAVEATTENDANCE.setText("SAVE ATTENDANCE");
jbtnSAVEATTENDANCE.setBackground(new java.awt.Color(255, 255, 200));
jbtnSAVEATTENDANCE.setIconTextGap(15);
jbtnSAVEATTENDANCE.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnSAVEATTENDANCEActionPerformed(evt);
}
});
jbtnGENERATEATTENDANCE = new JButton();
jbtnGENERATEATTENDANCE.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnGENERATEATTENDANCE.setIcon(new ImageIcon(getClass().getResource("generate.png"))); 
jbtnGENERATEATTENDANCE.setText("GENERATE ATTENDANCE");
jbtnGENERATEATTENDANCE.setIconTextGap(15);
jbtnGENERATEATTENDANCE.setBackground(new java.awt.Color(255, 255, 200));
jbtnGENERATEATTENDANCE.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jbtnGENERATEATTENDANCEActionPerformed(evt);
}
});

jbtnGENREPORT = new JButton();
jbtnGENREPORT.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jbtnGENREPORT.setIcon(new ImageIcon(getClass().getResource("report.png"))); 
jbtnGENREPORT.setText("Class Attendance Day Report");
jbtnGENREPORT.setIconTextGap(15);
jbtnGENREPORT.setBackground(new java.awt.Color(255, 255, 200));
jbtnGENREPORT.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
System.out.println("Generate attendance report button clicked");      
jbtnGENREPORTActionPerformed(evt);
}
});

jPnlButton = new JPanel();
jPnlButton.setLayout(new GridLayout(1,3,20,20));
jPnlButton.setBackground(new java.awt.Color(255, 153, 51));
jPnlButton.add(jbtnGENERATEATTENDANCE);
jPnlButton.add(jbtnSAVEATTENDANCE);
//jPnlButton.add(jbtnDELETE);
jPnlButton.add(jbtnGENREPORT);
//jPnlButton.add(jbtnNEXT);
////jPnlButton.add(jbtnPREVIOUS);
//jPnlButton.add(jbtnLAST);
JPanel jPnlscrtb = new JPanel();
jPnlscrtb.setLayout(new GridLayout(1,1,20,20));
jScrollPane1 = new JScrollPane();
JTable_StaffStudAttend = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
//new Object [][] {},new String [] {"AC_YEAR", "SEM","BRANCH","SEC","SUB_CODE","USN","SID","ADATE","SLOT","STATUS"});
new Object [][] {},new String [] {"USN","STUDENT NAME","STATUS"});
JTable_StaffStudAttend.setModel(MytableModel);
jPnlscrtb.add(jScrollPane1);
JTable_StaffStudAttend.setSelectionForeground(Color.white);
JTable_StaffStudAttend.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_StaffStudAttend);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbaptitle.setText("STAFF ATTENDANCE FORM -ONLY FOR STAFF ROLE");
jPanelptitle.add(jlbaptitle);
JPanel jPanel2N = new JPanel();
jPanel2N.setLayout(new GridLayout(3,1,20,20));
JPanel jPanelIns = new JPanel();
jPanelIns.setBackground(new java.awt.Color(255, 255,153));
JLabel jlbIns = new JLabel();
jlbIns.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbIns.setText("IMPORTANT NOTE: SUBJECT ALLOC AND SCOURSE, PERIOD TABLE DATA MUST BE SETUP PRIOR TO USING THIS FORM");
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
f.setMinimumSize(new Dimension(1400, 500));
f.setPreferredSize(new Dimension(1400, 500));
f.setSize(1400,500);
f.setLocationRelativeTo(null); 
f.pack();
f.setVisible(true); 
JTable_StaffStudAttend.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseClicked(java.awt.event.MouseEvent evt) {
JTable_StaffStudAttendMouseClicked(evt);
}
});
}

public Boolean GetAcadyrSem(){
myconnection = getConnection();
i_acadyr=Integer.parseInt(txt_acadyr.getText());
i_sem=Integer.parseInt((String)Jcbtxt_sem.getSelectedItem());
try{
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String adate=sdf.format(txt_adate.getDate());
java.util.Date udob=sdf.parse(adate);
sqladate=new java.sql.Date(udob.getTime());
}
catch(ParseException ex){
System.out.println("GENERATE-date parse Exception");   
System.out.println(ex);
}
String query  = "select count(*) onerecord from period where ac_year=? and sem =? and ? <=sysdate and ? between st_date and end_date";

try {
pstmt=myconnection.prepareStatement(query);
pstmt.setInt(1,i_acadyr);
pstmt.setInt(2,i_sem);
pstmt.setDate(3,sqladate);
pstmt.setDate(4,sqladate);
//pstmt.setString(3,branch);
//pstmt.setString(4,sec);
ResultSet rs=pstmt.executeQuery();  
StaffStudentAttndTemp studattendperc;
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


private void jbtnGENERATEATTENDANCEActionPerformed(java.awt.event.ActionEvent evt) {
if(checkInputs()){ 
if (GetAcadyrSem()) { 
try {
cps = myconnection.prepareCall("{?= call GEN_ATTENDANCE(?,?,?,?,?,?,?,?,?)}");
i_acadyr=Integer.parseInt(txt_acadyr.getText());
i_sem=Integer.parseInt((String)Jcbtxt_sem.getSelectedItem());
s_branch=(String)Jcbtxt_branch.getSelectedItem();
s_branch=s_branch.toUpperCase();
s_sec=(String)Jcbtxt_sec.getSelectedItem();
s_sec=s_sec.toUpperCase();
s_subcode =(String)Jcbtxt_subcode.getSelectedItem();
s_subcode=s_subcode.toUpperCase();
i_sid=Integer.parseInt(txt_sid.getText());
cps.setInt(2,i_acadyr);
cps.setInt(3,i_sem);
cps.setString(4,s_branch);
cps.setString(5,s_sec);
cps.setString(6,s_subcode);
cps.setInt(7,i_sid);
try{
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String adate=sdf.format(txt_adate.getDate());
java.util.Date udob=sdf.parse(adate);
sqladate=new java.sql.Date(udob.getTime());
cps.setDate(8, sqladate);
cps.setInt(9,Integer.parseInt((String)Jcbtxt_slot.getSelectedItem()));
//cps.setInt(9,Jcbtxt_slot.getSelectedItem());
//System.out.println("Data input Given 2:"+sqladate+" " +adate);
}
catch(ParseException ex){
System.out.println("GENERATE-date parse Exception");   
System.out.println(ex);
}
s_status=(String)Jcbtxt_status.getSelectedItem();
s_status=s_status.toUpperCase();
cps.setString(10, s_status);
//System.out.println("Data input Given 2:"+Jcbtxt_status.getText());
cps.registerOutParameter(1,Types.VARCHAR);
cps.executeUpdate();
String Exstatus = cps.getString(1);
if (Exstatus.equals("DUPL"))
{
//System.out.println("Attendance already generated");
JOptionPane.showMessageDialog(null, "Attendance already generated, Click take attendance");
Show_Students_In_JTable();
}
else if (Exstatus.equals("CLASH")){
//System.out.println("Attendance Cannot be generated, change the slot or date");
JOptionPane.showMessageDialog(null, "Attendance Cannot be generated, change the slot or date");    
}
else if (Exstatus.equals("MISM")){
//System.out.println("Attendance Cannot be generated,ALLOCATION MISMATCH");
JOptionPane.showMessageDialog(null, "Attendance Cannot be generated, CHANGE SEM OR BRANCH OR SEC OR SUBCODE");    
}
else 
{
//System.out.println("Attendance Successfully generated");
JOptionPane.showMessageDialog(null, "Attendance Successfully generated, Click take attendance");
Show_Students_In_JTable();
}
cps.close();
}
catch (SQLException e) {
int ret_code = e.getErrorCode();
System.err.println(ret_code + e.getMessage()); 
//cps.close();
}    
//JOptionPane.showMessageDialog(null, "Data Inserted");
}
else
{
JOptionPane.showMessageDialog(null, "Academic year, Semester or attendance date is not currently active");
}
System.out.println("SID => "+txt_sid.getText());
System.out.println("SECTION => "+(String)Jcbtxt_sec.getSelectedItem());
}
else
{
JOptionPane.showMessageDialog(null, "One Or More Field Are Empty");
}
}

private void jbtnSAVEATTENDANCEActionPerformed(java.awt.event.ActionEvent evt) {
if(checkInputs()){
String UpdateQuery = null;
myconnection = getConnection();
UpdateQuery = "UPDATE ATTENDANCE SET status = ?" 
+" WHERE ac_year = ? and sem = ? and  branch = ? and  sec = ?"
+ "  and sub_code = ? and  usn = ?  and sid=?  and adate=?  and slot=?";
try {
pstmt = myconnection.prepareStatement(UpdateQuery);
s_status=(String)Jcbtxt_status.getSelectedItem();
s_status=s_status.toUpperCase();
pstmt.setString(1,s_status);
i_acadyr=Integer.parseInt(txt_acadyr.getText());
pstmt.setInt(2,i_acadyr);
i_sem =Integer.parseInt((String)Jcbtxt_sem.getSelectedItem());
pstmt.setInt(3,i_sem);
s_branch = (String)Jcbtxt_branch.getSelectedItem();
s_branch=s_branch.toUpperCase();
pstmt.setString(4, s_branch);
s_sec = (String)Jcbtxt_sec.getSelectedItem();
s_sec=s_sec.toUpperCase();
pstmt.setString(5,s_sec );
s_subcode = (String)Jcbtxt_subcode.getSelectedItem();
s_subcode=s_subcode.toUpperCase();
pstmt.setString(6,s_subcode);
String s_usn = txt_usn.getText();
s_usn=s_usn.toUpperCase();
pstmt.setString(7, s_usn);
int staffid=Integer.parseInt(txt_sid.getText());
pstmt.setInt(8,staffid);
try{
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String adate=sdf.format(txt_adate.getDate());
java.util.Date udob=sdf.parse(adate);
sqladate=new java.sql.Date(udob.getTime());
//System.out.println("sqldate="+sqladate);
pstmt.setDate(9,sqladate);
}
catch(ParseException ex){
System.out.println("UPDATE-1-date parse Exception");   
System.out.println(ex);
}
i_slot=Integer.parseInt((String)Jcbtxt_slot.getSelectedItem());
pstmt.setInt(10,i_slot);
int rowsUpdated=pstmt.executeUpdate();
//System.out.println("UPDATE-1-Commiting data here...."+rowsUpdated);
myconnection.commit();
JOptionPane.showMessageDialog(null, "Student attendance status Updated for "+s_usn);
int index = JTable_StaffStudAttend.getSelectedRow();
JTable_StaffStudAttend.setValueAt(s_status,index,2);
list.get(index).setstatus(s_status);
} 
catch (SQLException ex) {
System.err.println("SQL EXCPETION."+ex);
ex.printStackTrace();
}
}
else
{
JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty Or Wrong");
}
}
private void JTable_StaffStudAttendMouseClicked(java.awt.event.MouseEvent evt) {
int index = JTable_StaffStudAttend.getSelectedRow();
//System.out.println("Selected row index ="+index);
ShowItem(index);
}

private void jbtnGENREPORTActionPerformed(java.awt.event.ActionEvent evt) {
if(GenClassAttndReport())
JOptionPane.showMessageDialog(null, "Attendance Report Generated Succesfully ");
else
JOptionPane.showMessageDialog(null, "Failed to generate the Attendance report");
}
}
