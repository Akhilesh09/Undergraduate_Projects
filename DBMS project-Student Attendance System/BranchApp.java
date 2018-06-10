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
 * @author AKHILESH
 */
public class BranchApp {
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton Btn_Choose_Image;
private JButton jbtnFIRST;
private JButton jbtnINSERT;
private JButton jbtnLAST;
private JButton jbtnNEXT;
private JButton jbtnPREVIOUS;
private JTable  JTable_Branches;
private JButton jbtnUPDATE;
private JButton jbtnDELETE;
//select sec section
private JLabel jlbBRANCH;
private JTextField txt_branch;
private JLabel jlbBNAME;
private JTextField txt_bname;
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
private PreparedStatement pstmt;
private ResultSet rs;
private Branch branch;
private ArrayList<Branch> branchList,list;
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form BranchApp
  */
public BranchApp(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String staffid, String usn) {
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
  
public void CreateBranchForm()  { 
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
Show_Branches_In_JTable(); 
}  

//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
// Check Input Fields
public boolean checkInputs(){
if(txt_branch.getText().equals("") || txt_bname.getText().equals(""))
return false;   
else
return true; 
}

// Display Data In JTable:
//      1 - Fill ArrayList With The Data

public ArrayList<Branch> getBranchList(){
branchList  = new ArrayList<Branch>();
myconnection = getConnection();
String query = "SELECT BRANCH, BNAME FROM BRANCH ORDER BY BRANCH";
try{
pstmt=myconnection.prepareStatement(query);
rs=pstmt.executeQuery();
if(rs==null) return null;
else
{
while(rs.next()){
branch = new Branch(rs.getString("BRANCH"),rs.getString("BNAME"));
branchList.add(branch);}
}
}
catch (SQLException ex) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+ex.getMessage());
return null;
}
return branchList;
}
//      2 - Populate The JTable
public void Show_Branches_In_JTable(){
if(getBranchList()==null){
JOptionPane.showMessageDialog(null,"NO BRANCH DATA");    
}
else
{ 
list = getBranchList();
DefaultTableModel model = (DefaultTableModel)JTable_Branches.getModel();
model.setRowCount(0);
Object[] row = new Object[2];
for(int i = 0; i < list.size(); i++){
row[0] = list.get(i).getBranch();
row[1] = list.get(i).getBname();
model.addRow(row);
}
}
}
// Show Data In Inputs
public void ShowItem(int index){
txt_branch.setText(getBranchList().get(index).getBranch());
txt_bname.setText(getBranchList().get(index).getBname());
}
private void initComponents() {
jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 153));
jPanel1.setLayout(new GridLayout(1,4,25,25));
jlbBRANCH = new JLabel();
jlbBRANCH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jlbBRANCH.setText("BRANCH:");
txt_branch = new JTextField(10);
txt_branch.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
txt_branch.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbBRANCH);
jPanel1.add(txt_branch);
jlbBNAME = new JLabel();
jlbBNAME.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jlbBNAME.setText("BRANCH NAME:");
txt_bname = new JTextField(10);
txt_bname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
txt_bname.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbBNAME);
jPanel1.add(txt_bname);
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
jPnlButton.setLayout(new GridLayout(1,7,25,25));
jPnlButton.setBackground(new java.awt.Color(255, 153, 51));
jPnlButton.add(jbtnINSERT);
jPnlButton.add(jbtnUPDATE);
jPnlButton.add(jbtnDELETE);
jPnlButton.add(jbtnFIRST);
jPnlButton.add(jbtnNEXT);
jPnlButton.add(jbtnPREVIOUS);
jPnlButton.add(jbtnLAST);
JPanel jPnlscrtb = new JPanel();
jPnlscrtb.setLayout(new GridLayout(1,1,25,25));
jScrollPane1 = new JScrollPane();
JTable_Branches = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},
//SELECT BRANCH,BNAME from BRANCH
new String [] {"BRANCH","BRANCH NAME"});
JTable_Branches.setModel(MytableModel);
JTableHeader header = JTable_Branches.getTableHeader();
header.setUpdateTableInRealTime(true);
jPnlscrtb.add(jScrollPane1);
JTable_Branches.setSelectionForeground(Color.white);
JTable_Branches.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_Branches);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbaptitle.setText("ADMIN FORM TO SETUP DEPARTMENTS/BRANCHES");
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
jlbIns.setText("IMPORTANT NOTE:ADMIN MUST SETUP THE DEPARTMENTS BEFORE ATTENDANCE APP CAN USED BY STAFF");
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
f.setMinimumSize(new Dimension(1200, 400));
f.setPreferredSize(new Dimension(1200, 400));
f.setSize(1200,400);
f.setLocationRelativeTo(null); 
f.pack();
f.setVisible(true); 
JTable_Branches.addMouseListener(new java.awt.event.MouseAdapter() {
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

public boolean GetBRANCH(){
myconnection = getConnection();
String sql1 = "select BRANCH from BRANCH where BRANCH=?";
try{
pstmt = myconnection.prepareStatement(sql1);
pstmt.setString(1, txt_branch.getText());
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
return true;
else
return false; 
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-getBRANCH ERROR"+e.getMessage());
return false;
}   
}

private void jbtnINSERTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnINSERTActionPerformed
if(checkInputs()){
if(GetBRANCH()){
try{
Connection con = getConnection();
PreparedStatement ps = con.prepareStatement("INSERT INTO BRANCH values(UPPER(?),?)");
////SELECT BRANCH,BNAME FROM BRANCH
ps.setString(1, txt_branch.getText());
ps.setString(2, txt_bname.getText());
int rowsUpdated=ps.executeUpdate();
System.out.println("INSERT-Commiting data here...."+rowsUpdated);
con.commit();
Show_Branches_In_JTable();
JOptionPane.showMessageDialog(null, "Branch Data Inserted");
}
catch (Exception ex) {
JOptionPane.showMessageDialog(null, ex.getMessage());
}
}
else
{
JOptionPane.showMessageDialog(null, "BRANCH ALREADY EXISTS, DUPLICATION NOT ALLOWED");    
}
}
else
{
JOptionPane.showMessageDialog(null, "One Or More Field Are Empty");
}
System.out.println("BRANCH => "+txt_branch.getText());
//System.out.println("Image => "+ImgPath);
}//GEN-LAST:event_jbtnINSERTActionPerformed

private void jbtnUPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUPDATEActionPerformed
if(checkInputs()){
String UpdateQuery = null;
PreparedStatement ps = null;
Connection con = getConnection();
// update without image
UpdateQuery = "UPDATE BRANCH SET BNAME = ? WHERE UPPER(BRANCH) = UPPER(?) ";
try{
ps = con.prepareStatement(UpdateQuery);
////SELECT BRANCH,BNAME FROM BRANCH
ps.setString(2, txt_branch.getText());
ps.setString(1, txt_bname.getText());
int rowsUpdated=ps.executeUpdate();
if(rowsUpdated==0)
JOptionPane.showMessageDialog(null, "Invalid branch");
else
{
System.out.println("UPDATE-1-Commiting data here...."+rowsUpdated);
con.commit();
Show_Branches_In_JTable();
JOptionPane.showMessageDialog(null, "Branches Updated");
}
} 
catch (SQLException ex) {
//Logger.getLogger(BranchApp.class.getName()).log(Level.SEVERE, null, ex);
JOptionPane.showMessageDialog(null, "Branches Not Updated");
}


}
else
{
JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty Or Wrong");
}
}//GEN-LAST:event_jbtnUPDATEActionPerformed



// Button Delete The Data From MySQL Database
private void jbtnDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDELETEActionPerformed
if(!txt_branch.getText().equals("")){
try{
Connection con = getConnection();
PreparedStatement ps = con.prepareStatement("DELETE BRANCH WHERE UPPER(BRANCH)=UPPER(?)");
ps.setString(1, txt_branch.getText());
int rowsUpdated=ps.executeUpdate();
if(rowsUpdated==0)
JOptionPane.showMessageDialog(null, "Invalid branch, delete unsuccessful");
else
{
System.out.println("DELETE-Commiting data here...."+rowsUpdated);
con.commit();
Show_Branches_In_JTable();
JOptionPane.showMessageDialog(null, "Branch Deleted");
}
}
catch (SQLException ex) {
//Logger.getLogger(BranchApp.class.getName()).log(Level.SEVERE, null, ex);
JOptionPane.showMessageDialog(null, "Branch Not Deleted");
}
}
else{
JOptionPane.showMessageDialog(null, "Branch Not Deleted : No Id To Delete");
}

}//GEN-LAST:event_jbtnDELETEActionPerformed
private void JTable_SectionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_SectionsMouseClicked
int index = JTable_Branches.getSelectedRow();
ShowItem(index);
}//GEN-LAST:event_JTable_SectionsMouseClicked

// Button First Show The First Record
private void jbtnFIRSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFIRSTActionPerformed
pos = 0;
ShowItem(pos);
}//GEN-LAST:event_jbtnFIRSTActionPerformed
// Button Last Show The Last Record
private void jbtnLASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLASTActionPerformed
pos = getBranchList().size()-1;
ShowItem(pos);
}//GEN-LAST:event_jbtnLASTActionPerformed
// Button Next Show The Next Record
private void jbtnNEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNEXTActionPerformed
pos++;
if(pos >= getBranchList().size()){
pos = getBranchList().size()-1;
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
