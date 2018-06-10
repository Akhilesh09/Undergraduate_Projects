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
/**
 *
 * @author AKHILESH VIJAYAKUMAR
 */
public class StaffAllocApp  {
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton Btn_Choose_Image;
private JButton jbtnFIRST;
private JButton jbtnINSERT;
private JButton jbtnLAST;
private JButton jbtnNEXT;
private JButton jbtnPREVIOUS;
private JTable  JTable_StaffAlloc;
private JButton jbtnUPDATE;
private JButton jbtnDELETE;
//select sec section
private JLabel jlbSID;
private JComboBox Jcbtxt_sid;
private JLabel jlbSUBCODE;
private JComboBox Jcbtxt_subcode;
private JLabel jlbSEC;
private JComboBox Jcbtxt_sec;
private JLabel jlbBRANCH;
private JComboBox Jcbtxt_branch;
private JPanel jPanel1;
private JPanel jPnlButton;
private JScrollPane jScrollPane1;
private JLabel lbl_image;
private java.sql.Date sqladate;

private String ImgPath = null;
private int pos = 0,sid;
private String loginName,loginRole,usn,staffid;
private Connection myconnection;
private PreparedStatement pstmt=null;
private ResultSet rs=null;
private JFrame f;
private StaffAlloc staffalloc;
private ArrayList<StaffAlloc> list,staffallocList;
private ArrayList<String> al_subcodelist,al_branchlist,al_seclist,al_stafflist,al_slotlist;
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form StaffAllocApp
  */
public StaffAllocApp(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String staffid, String usn) {
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
  
public void CreateStaffAllocForm()  { 
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
     Show_StaffAlloc_In_JTable();
}

//select SID,SUB_CODE,SEC,BRANCH from STAFF_ALLOC
// Check Input Fields
public boolean checkInputs(){
if((String)Jcbtxt_sid.getSelectedItem() == null && (String)Jcbtxt_subcode.getSelectedItem() == null && (String)Jcbtxt_sec.getSelectedItem() == null && (String)Jcbtxt_branch.getSelectedItem() == null)
return false;   
else
return true; 
}

public boolean GetStaffSubCode(){
myconnection = getConnection();
al_subcodelist=new ArrayList<String>();
//List<String> ls = new ArrayList<String>(); 
//jComboBox.setModel(new DefaultComboBoxModel(al_subcodelist.toArray()));
String sql2 = "select distinct SUB_CODE from SCOURSE ORDER BY SUB_CODE DESC";
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

//SID,SUB_CODE,SEC,BRANCH
public boolean GetStudBranch(){
myconnection = getConnection();
al_branchlist=new ArrayList<String>();
//List<String> ls = new ArrayList<String>(); 
//jComboBox.setModel(new DefaultComboBoxModel(al_subcodelist.toArray()));
String sql3 = "select distinct BRANCH from STUDENT ORDER BY BRANCH";
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
// Display Data In JTable:
//      1 - Fill ArrayList With The Data

public boolean GetStaffId(){
myconnection = getConnection();
al_stafflist=new ArrayList<String>();
//List<String> ls = new ArrayList<String>(); 
//jComboBox.setModel(new DefaultComboBoxModel(al_subcodelist.toArray()));
String sql4 = "select distinct SID from STAFF order by SID";
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

public boolean GetStudSection(){
myconnection = getConnection();
al_seclist=new ArrayList<String>();
//List<String> ls = new ArrayList<String>(); 
//jComboBox.setModel(new DefaultComboBoxModel(al_subcodelist.toArray()));
String sql4 = "select distinct SEC from STUDENT ORDER BY SEC";
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

public ArrayList<StaffAlloc> getStaffAllocList(){
staffallocList  = new ArrayList<StaffAlloc>();
myconnection = getConnection();
String query = "SELECT SID,SUB_CODE,SEC,BRANCH  FROM STAFF_ALLOC ORDER BY SID";
try{
pstmt=myconnection.prepareStatement(query);
rs=pstmt.executeQuery();
if(rs==null) return null;
else
{
while(rs.next()){
staffalloc = new StaffAlloc(rs.getInt("SID"),rs.getString("SUB_CODE"),rs.getString("SEC"),rs.getString("BRANCH"));
staffallocList.add(staffalloc);}
}
}
catch (SQLException ex) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+ex.getMessage());
return null;
}
return staffallocList;
}
//      2 - Populate The JTable
public void Show_StaffAlloc_In_JTable(){
if(getStaffAllocList()==null){
JOptionPane.showMessageDialog(null,"NO DATA IN STAFF ALLOC TABLE");    
}
else
{    
list = getStaffAllocList();
DefaultTableModel model = (DefaultTableModel)JTable_StaffAlloc.getModel();
model.setRowCount(0);
Object[] row = new Object[4];
for(int i = 0; i < list.size(); i++){
row[0] = list.get(i).getsid();
row[1] = list.get(i).getsubcode();
row[2] = list.get(i).getsec();
row[3] = list.get(i).getbranch();
model.addRow(row);
}
}
}
// Show Data In Inputs
public void ShowItem(int index){
Jcbtxt_sid.setSelectedItem(String.valueOf(getStaffAllocList().get(index).getsid()));
Jcbtxt_subcode.setSelectedItem(getStaffAllocList().get(index).getsubcode());
Jcbtxt_sec.setSelectedItem(getStaffAllocList().get(index).getsec());
Jcbtxt_branch.setSelectedItem(getStaffAllocList().get(index).getbranch());
}
private void initComponents() {
jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 153));
jPanel1.setLayout(new GridLayout(1,8,25,25));
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
System.exit(0);
}
Jcbtxt_sid.setFont(new java.awt.Font("Tahoma", 1, 14));  
Jcbtxt_sid.setPreferredSize(new java.awt.Dimension(24, 20));
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
System.exit(0);
}
Jcbtxt_subcode.setFont(new java.awt.Font("Tahoma", 1, 14));  
Jcbtxt_subcode.setPreferredSize(new java.awt.Dimension(24, 20));
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
System.exit(0);
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
jPanel1.add(jlbSID);
jPanel1.add(Jcbtxt_sid);
jPanel1.add(jlbSUBCODE);
jPanel1.add(Jcbtxt_subcode);
jPanel1.add(jlbSEC);
jPanel1.add(Jcbtxt_sec);
jPanel1.add(jlbBRANCH);
jPanel1.add(Jcbtxt_branch);
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
jPnlButton.setLayout(new GridLayout(1,7,12,12));
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
JTable_StaffAlloc = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},
//SELECT SID,SUB_CODE,SEC,BRANCH from STAFF_ALLOC
new String [] {"STAFF ID","SUBJECT CODE","SEC","BRANCH"});
JTable_StaffAlloc.setModel(MytableModel);
JTableHeader header = JTable_StaffAlloc.getTableHeader();
header.setUpdateTableInRealTime(true);
jPnlscrtb.add(jScrollPane1);
JTable_StaffAlloc.setSelectionForeground(Color.white);
JTable_StaffAlloc.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_StaffAlloc);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbaptitle.setText("SUBJECT AND CLASS/SECTION ALLOTMENT TO STAFF FORM");
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
jlbIns.setText("IMPORTANT NOTE: STAFF, SUBJECT AND STUDENT TABLE DATA MUST BE SETUP PRIOR TO USING THIS FORM");
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
f.setMinimumSize(new Dimension(900, 600));
f.setPreferredSize(new Dimension(1200, 600));
f.setSize(1200,600);
f.setLocationRelativeTo(null); 
f.pack();
f.setVisible(true); JTable_StaffAlloc.addMouseListener(new java.awt.event.MouseAdapter() {
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

public boolean GetSTAFFALLOC(){
myconnection = getConnection();
String sql1 = "select SID,SUB_CODE,SEC, BRANCH from STAFF_ALLOC where SID=? AND SUB_CODE=? AND SEC=? AND BRANCH=?";
try{
pstmt = myconnection.prepareStatement(sql1);
pstmt.setInt(1,Integer.parseInt((String)Jcbtxt_sid.getSelectedItem()));
pstmt.setString(2, (String)Jcbtxt_subcode.getSelectedItem());
pstmt.setString(3, (String)Jcbtxt_sec.getSelectedItem());
pstmt.setString(4, (String)Jcbtxt_branch.getSelectedItem());
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
return true;
else
return false; 
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-GetSTAFFALLOC ERROR"+e.getMessage());
return false;
}   
}

private void jbtnINSERTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnINSERTActionPerformed
if(checkInputs()){
if(GetSTAFFALLOC()){    
try {
myconnection = getConnection();
pstmt = myconnection.prepareStatement("INSERT INTO STAFF_ALLOC values(?,UPPER(?),UPPER(?),UPPER(?))");
////SELECT SID,SUB_CODE,SEC, BRANCH FROM STAFF_ALLOC
pstmt.setInt(1,Integer.parseInt((String)Jcbtxt_sid.getSelectedItem()));
pstmt.setString(2, (String)Jcbtxt_subcode.getSelectedItem());
pstmt.setString(3, (String)Jcbtxt_sec.getSelectedItem());
pstmt.setString(4, (String)Jcbtxt_branch.getSelectedItem());
int rowsUpdated=pstmt.executeUpdate();
System.out.println("INSERT-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_StaffAlloc_In_JTable();
JOptionPane.showMessageDialog(null, "STAFF ALLOCATION Data Inserted");
}
catch (Exception ex) {
JOptionPane.showMessageDialog(null, ex.getMessage());
}
}
else
{
JOptionPane.showMessageDialog(null, "STAFF ALLOCATION Data exists, DUPLICATION NOT ALLOWED");
}
}
else
{
JOptionPane.showMessageDialog(null, "One Or More Field Are Empty");
}
System.out.println("STAFF_ALLOC => "+(String)Jcbtxt_sid.getSelectedItem());
//System.out.println("Image => "+ImgPath);
}//GEN-LAST:event_jbtnINSERTActionPerformed

private void jbtnUPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUPDATEActionPerformed
if(checkInputs()){
String UpdateQuery = null;
myconnection = getConnection();
// update without image
UpdateQuery = "UPDATE STAFF_ALLOC SET UPPER(SUB_CODE) = UPPER(?), UPPER(SEC)=UPPER(?), UPPER(BRANCH)=UPPER(?) WHERE SID = ? ";
try {
pstmt = myconnection.prepareStatement(UpdateQuery);
////SELECT SID,SUB_CODE,SEC, BRANCH FROM STAFF_ALLOC
pstmt.setString(1, (String)Jcbtxt_subcode.getSelectedItem());
pstmt.setString(2, (String)Jcbtxt_sec.getSelectedItem());
pstmt.setString(3, (String)Jcbtxt_branch.getSelectedItem());
pstmt.setInt(4,Integer.parseInt((String)Jcbtxt_sid.getSelectedItem()));
int rowsUpdated=pstmt.executeUpdate();
System.out.println("UPDATE-1-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_StaffAlloc_In_JTable();
JOptionPane.showMessageDialog(null, "StaffAlloc Updated");
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
if(!Jcbtxt_sid.getSelectedItem().equals("")){
try {
myconnection= getConnection();
pstmt = myconnection.prepareStatement("DELETE STAFF_ALLOC WHERE SID=? AND UPPER(SUB_CODE)=UPPER(?) AND UPPER(SEC)=UPPER(?) AND UPPER(BRANCH)=UPPER(?)");
pstmt.setInt(1,Integer.parseInt((String)Jcbtxt_sid.getSelectedItem()));
pstmt.setString(2, (String)Jcbtxt_subcode.getSelectedItem());
pstmt.setString(3, (String)Jcbtxt_sec.getSelectedItem());
pstmt.setString(4, (String)Jcbtxt_branch.getSelectedItem());
int rowsUpdated=pstmt.executeUpdate();
System.out.println("DELETE-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_StaffAlloc_In_JTable();
JOptionPane.showMessageDialog(null, "StaffAlloc Deleted");
}
catch (SQLException ex) {
System.err.println("SQL Exception-01 Occurred for the DELETE OF SCOURSE");
ex.printStackTrace();
JOptionPane.showMessageDialog(null, "StaffAlloc Not Deleted");
}
}
else{
JOptionPane.showMessageDialog(null, "StaffAlloc Not Deleted : No Id To Delete");
}

}//GEN-LAST:event_jbtnDELETEActionPerformed
private void JTable_SectionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_SectionsMouseClicked
int index = JTable_StaffAlloc.getSelectedRow();
ShowItem(index);
}//GEN-LAST:event_JTable_SectionsMouseClicked

// Button First Show The First Record
private void jbtnFIRSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFIRSTActionPerformed
pos = 0;
ShowItem(pos);
}//GEN-LAST:event_jbtnFIRSTActionPerformed
// Button Last Show The Last Record
private void jbtnLASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLASTActionPerformed
pos = getStaffAllocList().size()-1;
ShowItem(pos);
}//GEN-LAST:event_jbtnLASTActionPerformed
// Button Next Show The Next Record
private void jbtnNEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNEXTActionPerformed
pos++;
if(pos >= getStaffAllocList().size()){
pos = getStaffAllocList().size()-1;
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
