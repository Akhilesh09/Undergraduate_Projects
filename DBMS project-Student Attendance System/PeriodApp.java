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
import com.toedter.calendar.*;
/**
 *
 * @author AKHILESH
 */
public class PeriodApp {
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton Btn_Choose_Image;
private JButton jbtnFIRST;
private JButton jbtnINSERT;
private JButton jbtnLAST;
private JButton jbtnNEXT;
private JButton jbtnPREVIOUS;
private JTable  JTable_Periods;
private JButton jbtnUPDATE;
private JButton jbtnDELETE;
//select ac_year,sem,st_date,end_date from period
private JLabel jlbACADYR;
private JComboBox Jcbtxt_acadyr;
private JLabel jlbSEM;
private JComboBox Jcbtxt_sem;
private JLabel jlbSTARTDATE;
private com.toedter.calendar.JDateChooser txt_startdate;
//private JTextField txt_startdate;
private JLabel jlbENDDATE;
//private JTextField txt_enddate;
private com.toedter.calendar.JDateChooser txt_enddate;
private JPanel jPanel1;
private JPanel jPnlButton;
private JScrollPane jScrollPane1;
private JLabel lbl_image;
private java.sql.Date sqlstdate;
private java.sql.Date sqlendate;

private String ImgPath = null;
private int pos = 0,sid,i_acadyr;
private String loginName,loginRole,usn,staffid;
private Connection myconnection;
private JFrame f;
private ArrayList<Periods> periodList,list;
private ArrayList<String> al_acadyrlist; 
private String [] semlov={"1","2","3","4","5","6","7","8"}; //DONE
private PreparedStatement pstmt=null;
private ResultSet rs=null;
private Periods period;
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form PeriodApp
  */
public PeriodApp(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String staffid, String usn) {
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
  
public void CreatePeriodForm()  { 
    
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
Show_Periods_In_JTable();    
}  

//select ac_year,sem,st_date,end_date from period
// Check Input Fields
public boolean checkInputs(){
if((String)Jcbtxt_acadyr.getSelectedItem() == null || (String)Jcbtxt_sem.getSelectedItem() == null    || txt_startdate.getDate() == null|| txt_enddate.getDate() == null)
return false;   
else
return true; 
}


//      2 - Populate The JTable
public void Show_Periods_In_JTable(){
if(getPeriodsList()==null){
JOptionPane.showMessageDialog(null,"NO PERIOD DATA");    
}
else
{
list = getPeriodsList();
DefaultTableModel model = (DefaultTableModel)JTable_Periods.getModel();
model.setRowCount(0);
Object[] row = new Object[4];
for(int i = 0; i < list.size(); i++){
row[0] = list.get(i).getacyear();
row[1] = list.get(i).getsem();
row[2] = list.get(i).getstartdate();
row[3] = list.get(i).getenddate();
model.addRow(row);
}
}
}


public ArrayList<Periods> getPeriodsList(){
periodList= new ArrayList<Periods>();
myconnection = getConnection();
System.out.println("Creating the Period array list");
String query = "SELECT AC_YEAR,SEM,to_char(ST_DATE,'dd-MM-yyyy') ST_DATE,to_char(END_DATE,'dd-MM-yyyy') END_DATE FROM PERIOD ORDER BY AC_YEAR,SEM DESC";
try {
pstmt=myconnection.prepareStatement(query);
rs=pstmt.executeQuery();
if(rs==null) return null;
else
{
while(rs.next()){
System.out.println("Retreiving the date");    
int per_acadyr=  Integer.parseInt(rs.getString("AC_YEAR"));
int per_sem=  Integer.parseInt(rs.getString("SEM"));
String   per_stdate=rs.getString("ST_DATE");
String  per_enddate=rs.getString("END_DATE");
System.out.println("Start Date:"+per_stdate);
System.out.println("End Date:"+per_enddate);
period = new Periods(per_acadyr, per_sem,per_stdate,per_enddate );
periodList.add(period);
}
}
}
catch (SQLException ex) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+ex.getMessage());
return null;
}
return periodList;
}

public boolean GetAcadyr(){
myconnection = getConnection();
al_acadyrlist=new ArrayList<String>();
String sql1 = "select distinct AC_YEAR from ACADYR ORDER BY AC_YEAR DESC";
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

// Show Data In Inputs
public void ShowItem(int index){
Jcbtxt_acadyr.setSelectedItem(String.valueOf(getPeriodsList().get(index).getacyear()));
Jcbtxt_sem.setSelectedItem(String.valueOf(getPeriodsList().get(index).getsem()));
try {
    Date mstrtDate=null;
    Date mendDate =null;
    mstrtDate = new SimpleDateFormat("dd-MM-yyyy").parse((String)getPeriodsList().get(index).getstartdate());
    txt_startdate.setDate(mstrtDate);
    mendDate = new SimpleDateFormat("dd-MM-yyyy").parse((String)getPeriodsList().get(index).getenddate());
    txt_enddate.setDate(mendDate);
}
catch (ParseException ex) {
            System.out.println("Date Parse Exception:"+ex);
            ex.printStackTrace();
        }
}
private void initComponents() {
jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 153));
jPanel1.setLayout(new GridLayout(1,8,25,25));
//row1
jlbACADYR = new JLabel();
jlbACADYR.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jlbACADYR.setText("ACADYR:");
if (GetAcadyr()){
Jcbtxt_acadyr = new JComboBox();
Jcbtxt_acadyr.setModel(new DefaultComboBoxModel(al_acadyrlist.toArray()));
}
else
{
JOptionPane.showMessageDialog(null,"ACADEMIC YEAR is invalid");
//System.exit(0);
}

Jcbtxt_acadyr.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
Jcbtxt_acadyr.setPreferredSize(new java.awt.Dimension(24, 20)); // NOI14N
jPanel1.add(jlbACADYR);
jPanel1.add(Jcbtxt_acadyr);

jlbSEM = new JLabel();
jlbSEM.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jlbSEM.setText("SEMESTER:");
Jcbtxt_sem = new JComboBox(semlov);
Jcbtxt_sem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
Jcbtxt_sem.setPreferredSize(new java.awt.Dimension(24, 20)); // NOI14N
jPanel1.add(jlbSEM);
jPanel1.add(Jcbtxt_sem);
//row2
jlbSTARTDATE = new JLabel();
jlbSTARTDATE.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jlbSTARTDATE.setText("SEM START DATE:");
//txt_startdate = new JTextField(15);
txt_startdate = new com.toedter.calendar.JDateChooser();
txt_startdate.setDateFormatString("dd-MM-yyyy");
txt_startdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
//txt_startdate.setEnabled(false);
txt_startdate.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbSTARTDATE);
jPanel1.add(txt_startdate);
jlbENDDATE = new JLabel();
jlbENDDATE.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jlbENDDATE.setText("SEM END DATE:");
//txt_enddate = new JTextField(10);
txt_enddate = new com.toedter.calendar.JDateChooser();
txt_enddate.setDateFormatString("dd-MM-yyyy");
txt_enddate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
txt_enddate.setPreferredSize(new java.awt.Dimension(24, 20)); // NOI14N
jPanel1.add(jlbENDDATE);
jPanel1.add(txt_enddate);
/**************************/
jbtnUPDATE = new JButton();
jbtnUPDATE.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jbtnUPDATE.setIcon(new ImageIcon(getClass().getResource("Renew.png"))); // NOI14N
jbtnUPDATE.setText("UPDATE");
jbtnUPDATE.setIconTextGap(15);
jbtnDELETE = new JButton();
jbtnDELETE.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jbtnDELETE.setIcon(new ImageIcon(getClass().getResource("delete.png"))); // NOI14N
jbtnDELETE.setText("DELETE");
jbtnDELETE.setIconTextGap(15);
jbtnINSERT = new JButton();
jbtnINSERT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jbtnINSERT.setIcon(new ImageIcon(getClass().getResource("add.png"))); // NOI14N
jbtnINSERT.setText("Insert");
jbtnINSERT.setIconTextGap(15);
jbtnFIRST = new JButton();
jbtnFIRST.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jbtnFIRST.setIcon(new ImageIcon(getClass().getResource("first.png"))); // NOI14N
jbtnFIRST.setText("First");
jbtnFIRST.setIconTextGap(15);
jbtnPREVIOUS = new JButton();
jbtnPREVIOUS.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jbtnPREVIOUS.setIcon(new ImageIcon(getClass().getResource("previous.png"))); // NOI14N
jbtnPREVIOUS.setText("Previous");
jbtnPREVIOUS.setIconTextGap(15);
jbtnLAST = new JButton();
jbtnLAST.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jbtnLAST.setIcon(new ImageIcon(getClass().getResource("last.png"))); // NOI14N
jbtnLAST.setText("Last");
jbtnLAST.setIconTextGap(15);
jbtnNEXT = new JButton();
jbtnNEXT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jbtnNEXT.setIcon(new ImageIcon(getClass().getResource("next.png"))); // NOI14N
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
JTable_Periods = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},
//SELECT AC_YEAR,SEM,ST_DATE,END_DATE FROM PERIOD
new String [] {"ACADYR", "SEM","START DATE","END DATE"});
JTable_Periods.setModel(MytableModel);
JTableHeader header = JTable_Periods.getTableHeader();
header.setUpdateTableInRealTime(true);
jPnlscrtb.add(jScrollPane1);
JTable_Periods.setSelectionForeground(Color.white);
JTable_Periods.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_Periods);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbaptitle.setText("PERIOD CONFIGURATION FORM");
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
jlbIns.setFont(new java.awt.Font("Tahoma", 1, 16));
jlbIns.setText("IMPORTANT NOTE: ACADYR TABLE DATA MUST BE SETUP PRIOR TO USING THIS FORM, ONLY ODD OR EVEN SEM MUST BE SETUP");
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
f.setMinimumSize(new Dimension(1300,400));
f.setPreferredSize(new Dimension(1300, 400));
f.setSize(1300,400);
f.setLocationRelativeTo(null); 
f.pack();
f.setVisible(true); JTable_Periods.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseClicked(java.awt.event.MouseEvent evt) {
JTable_StudentsMouseClicked(evt);
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

public boolean GetPERIOD(){
myconnection = getConnection();
String sql1 = "select AC_YEAR,SEM from PERIOD where AC_YEAR=? AND SEM=?";
try{
pstmt = myconnection.prepareStatement(sql1);
pstmt.setInt(1, Integer.parseInt((String)Jcbtxt_acadyr.getSelectedItem()));
pstmt.setInt(2, Integer.parseInt((String)Jcbtxt_sem.getSelectedItem()));
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
return true;
else
return false; 
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-getPERIOD ERROR"+e.getMessage());
return false;
}   
}

private void jbtnINSERTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnINSERTActionPerformed
if(checkInputs()){
if(GetPERIOD()){    
try {
Connection con = getConnection();
PreparedStatement ps = con.prepareStatement("INSERT INTO PERIOD(AC_YEAR,SEM,ST_DATE,END_DATE)"
+ "values(?,?,?,?)");
////SELECT AC_YEAR,SEM,ST_DATE,END_DATE FROM PERIOD
ps.setInt(1, Integer.parseInt((String)Jcbtxt_acadyr.getSelectedItem()));
ps.setInt(2, Integer.parseInt((String)Jcbtxt_sem.getSelectedItem()));
try{
//String stdate=txt_startdate.getText();
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String stdate = sdf.format(txt_startdate.getDate());
java.util.Date udob=sdf.parse(stdate);
sqlstdate=new java.sql.Date(udob.getTime());
ps.setDate(3, sqlstdate);
}
catch(ParseException ex){
System.out.println("INSERT-date parse Exception");   
System.out.println(ex);
}
try{
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String enddate=sdf.format(txt_enddate.getDate());
java.util.Date udob=sdf.parse(enddate);
sqlendate=new java.sql.Date(udob.getTime());
ps.setDate(4, sqlendate);
}
catch(ParseException ex){
System.out.println("INSERT-date parse Exception");   
System.out.println(ex);
}
int rowsUpdated=ps.executeUpdate();
System.out.println("INSERT-Committing data here...."+rowsUpdated);
con.commit();
Show_Periods_In_JTable();
JOptionPane.showMessageDialog(null, "PERIOD Data Inserted");
}
catch (Exception ex) {
JOptionPane.showMessageDialog(null, ex.getMessage());
}
}
else
{
JOptionPane.showMessageDialog(null, "PERIOD Data Already Exists, Duplicates Not allowed");    
}
}
else
{
JOptionPane.showMessageDialog(null, "One Or More Field Are Empty");
}
System.out.println("Start date => "+txt_startdate.getDate());
System.out.println("end date => "+txt_enddate.getDate());
//System.out.println("Image => "+ImgPath);
}//GEN-LAST:event_jbtnINSERTActionPerformed

private void jbtnUPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUPDATEActionPerformed
if(checkInputs()){
String UpdateQuery = null;
PreparedStatement ps = null;
Connection con = getConnection();
UpdateQuery = "UPDATE PERIOD SET ST_DATE = ?, END_DATE=? WHERE ac_year = ? and sem = ?";
try {
ps = con.prepareStatement(UpdateQuery);
////SELECT AC_YEAR,SEM,ST_DATE,END_DATE FROM PERIOD
int yr=Integer.parseInt((String)Jcbtxt_acadyr.getSelectedItem());
ps.setInt(3,yr);
int s =Integer.parseInt((String)Jcbtxt_sem.getSelectedItem());
ps.setInt(4,s);
try{
//String mystdate=txt_startdate.getText();
//String stdate= mystdate.substring(0,10);
//System.out.println("util date="+stdate);
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String mystdate=sdf.format(txt_startdate.getDate());
String stdate= mystdate.substring(0,10);
java.util.Date udob=sdf.parse(stdate);
sqlstdate=new java.sql.Date(udob.getTime());
System.out.println("sql start date="+sqlstdate);
ps.setDate(1,sqlstdate);
}
catch(ParseException ex){
System.out.println("UPDATE-1-date parse Exception");   
System.out.println(ex);
}
try{
//String myendate=txt_enddate.getText();
//String endate= myendate.substring(0,10);
//System.out.println("util date="+endate);
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String myendate=sdf.format(txt_enddate.getDate());
String endate= myendate.substring(0,10);
java.util.Date udob=sdf.parse(endate);
sqlendate=new java.sql.Date(udob.getTime());
System.out.println("sql end date="+sqlendate);
ps.setDate(2,sqlendate);
}
catch(ParseException ex){
System.out.println("UPDATE-1-date parse Exception");   
System.out.println(ex);
}
int rowsUpdated=ps.executeUpdate();
System.out.println("UPDATE-1-Commiting data here...."+rowsUpdated);
con.commit();
Show_Periods_In_JTable();
JOptionPane.showMessageDialog(null, "PERIOD Updated");
} 
catch (SQLException ex) {
    System.err.println("SQL Exception-01 Occurred for the UPDATE OF PERIOD");
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
if(!txt_startdate.getDate().equals("")){
try {
Connection con = getConnection();
PreparedStatement ps = con.prepareStatement("DELETE PERIOD WHERE ac_year=? and sem=?");
ps.setInt(1, Integer.parseInt((String)Jcbtxt_acadyr.getSelectedItem()));
ps.setInt(2, Integer.parseInt((String)Jcbtxt_sem.getSelectedItem()));
int rowsUpdated=ps.executeUpdate();
System.out.println("DELETE-Commiting data here...."+rowsUpdated);
con.commit();
Show_Periods_In_JTable();
JOptionPane.showMessageDialog(null, "Period Deleted");
}
catch (SQLException ex) {

JOptionPane.showMessageDialog(null, "Period Not Deleted");
    System.err.println("SQL Exception-02 Occurred for the UPDATE OF SCOURSE");
    ex.printStackTrace();
}
}
else{
JOptionPane.showMessageDialog(null, "Student Not Deleted : No Id To Delete");
}

}//GEN-LAST:event_jbtnDELETEActionPerformed
// JTable Mouse Clicked
// Display The Selected Row Data Into JTextFields
// And The Image Into JLabel
private void JTable_StudentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_StudentsMouseClicked
int index = JTable_Periods.getSelectedRow();
ShowItem(index);
}//GEN-LAST:event_JTable_StudentsMouseClicked

// Button First Show The First Record
private void jbtnFIRSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFIRSTActionPerformed
pos = 0;
ShowItem(pos);
}//GEN-LAST:event_jbtnFIRSTActionPerformed
// Button Last Show The Last Record
private void jbtnLASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLASTActionPerformed
pos = getPeriodsList().size()-1;
ShowItem(pos);
}//GEN-LAST:event_jbtnLASTActionPerformed
// Button Next Show The Next Record
private void jbtnNEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNEXTActionPerformed
pos++;
if(pos >= getPeriodsList().size()){
pos = getPeriodsList().size()-1;
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
