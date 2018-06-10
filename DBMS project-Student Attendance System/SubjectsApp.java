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
public class SubjectsApp  {
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton Btn_Choose_Image;
private JButton jbtnFIRST;
private JButton jbtnINSERT;
private JButton jbtnLAST;
private JButton jbtnNEXT;
private JButton jbtnPREVIOUS;
private JTable  JTable_Subjects;
private JButton jbtnUPDATE;
private JButton jbtnDELETE;
//select sec section
private JLabel jlbSUBNAME;
private JTextField txt_subname;
private JLabel jlbSUBCODE;
private JTextField txt_subcode;
private JLabel jlbSEM;
private JComboBox Jcbtxt_sem;
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
private String [] semlov={"1","2","3","4","5","6","7","8"};
private PreparedStatement pstmt=null;
private ResultSet rs=null;
private ArrayList<Subjects> subjectsList, list;
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form SubjectsApp
  */
public SubjectsApp(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String staffid, String usn) {
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
  
public void CreateSubjectsForm()  { 
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
     Show_Subjects_In_JTable();
}
//select usn,sub_code from SCOURSE
// Check Input Fields
public boolean checkInputs(){
if(txt_subname.getText().equals("") || txt_subcode.getText().equals("") || (String)Jcbtxt_sem.getSelectedItem() == null)
return false;   
else
return true; 
}

// Display Data In JTable:
//      1 - Fill ArrayList With The Data

public ArrayList<Subjects> getSubjectsList(){
subjectsList  = new ArrayList<Subjects>();
myconnection = getConnection();
String query = "SELECT SUB_CODE,SUB_NAME,SEM FROM SUBJECTS ORDER BY SUB_CODE DESC";
try {
pstmt=myconnection.prepareStatement(query);
rs=pstmt.executeQuery();  
Subjects subjects;
if(rs==null) return null;
else
{
while(rs.next()){
subjects = new Subjects(rs.getString("SUB_CODE"),rs.getString("SUB_NAME"),rs.getInt("SEM"));
subjectsList.add(subjects);}
}
}
catch (SQLException ex) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+ex.getMessage());
return null;
}
return subjectsList;
}
//      2 - Populate The JTable
public void Show_Subjects_In_JTable(){
if(getSubjectsList()==null){
JOptionPane.showMessageDialog(null,"NO subject DATA");    
}
else
{     
list = getSubjectsList();
DefaultTableModel model = (DefaultTableModel)JTable_Subjects.getModel();
// clear jtable content
//select sub_code,sub_name, sem from subjects
model.setRowCount(0);
Object[] row = new Object[3];
for(int i = 0; i < list.size(); i++){
row[0] = list.get(i).getsubcode();
row[1] = list.get(i).getsubname();
row[2] = list.get(i).getsem();
model.addRow(row);
}
}
}
// Show Data In Inputs
public void ShowItem(int index){
txt_subcode.setText(getSubjectsList().get(index).getsubcode());
txt_subname.setText(getSubjectsList().get(index).getsubname());
Jcbtxt_sem.setSelectedItem(String.valueOf(getSubjectsList().get(index).getsem()));
}
private void initComponents() {
jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 153));
jPanel1.setLayout(new GridLayout(1,6,25,25));
jlbSUBCODE = new JLabel();
jlbSUBCODE.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jlbSUBCODE.setText("SUBJECT CODE:");
txt_subcode = new JTextField(10);
txt_subcode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
txt_subcode.setPreferredSize(new java.awt.Dimension(24, 20));
jlbSUBNAME = new JLabel();
jlbSUBNAME.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jlbSUBNAME.setText("SUBJECT NAME:");
txt_subname = new JTextField(10);
txt_subname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
txt_subname.setPreferredSize(new java.awt.Dimension(24, 20));
jlbSEM = new JLabel();
jlbSEM.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jlbSEM.setText("SEMESTER:");
Jcbtxt_sem = new JComboBox(semlov);
Jcbtxt_sem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
Jcbtxt_sem.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbSUBCODE);
jPanel1.add(txt_subcode);
jPanel1.add(jlbSUBNAME);
jPanel1.add(txt_subname);
jPanel1.add(jlbSEM);
jPanel1.add(Jcbtxt_sem);
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
jPnlscrtb.setLayout(new GridLayout(1,1,20,20));
jScrollPane1 = new JScrollPane();
JTable_Subjects = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},
//SELECT SUB_CODE,SUB_NAME,SEM from SUBJECTS
new String [] {"SUBJECT CODE","SUBJECT NAME","SEMESTER"});
JTable_Subjects.setModel(MytableModel);
JTableHeader header = JTable_Subjects.getTableHeader();
header.setUpdateTableInRealTime(true);
jPnlscrtb.add(jScrollPane1);
JTable_Subjects.setSelectionForeground(Color.white);
JTable_Subjects.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_Subjects);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbaptitle.setText("ADMIN FORM TO CONFIGURE THE SUBJECTS");
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
jlbIns.setText("IMPORTANT NOTE: ADMIN MUST SETUP ALL THE SUBJECT FOR ATTENDANCE APP");
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
f.setMinimumSize(new Dimension(1200, 500));
f.setPreferredSize(new Dimension(1200, 500));
f.setSize(1200,500);
f.setLocationRelativeTo(null); 
f.pack();
f.setVisible(true); JTable_Subjects.addMouseListener(new java.awt.event.MouseAdapter() {
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

public boolean GetSUBCODE(){
myconnection = getConnection();
String sql1 = "select SUB_CODE from SUBJECTS where SUB_CODE=?";
try{
pstmt = myconnection.prepareStatement(sql1);
pstmt.setString(1, txt_subcode.getText());
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
return true;
else
return false; 
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-getSUBCODE ERROR"+e.getMessage());
return false;
}   
}


private void jbtnINSERTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnINSERTActionPerformed
if(checkInputs()){
if(GetSUBCODE()){
    try {
myconnection = getConnection();
pstmt = myconnection.prepareStatement("INSERT INTO SUBJECTS values(UPPER(?),?,?)");
////SELECT SUB_CODE,SUB_CODE,SEM FROM SUBJECTS
pstmt.setString(1, txt_subcode.getText());
pstmt.setString(2, txt_subname.getText());
pstmt.setInt(3, Integer.parseInt((String)Jcbtxt_sem.getSelectedItem()));
int rowsUpdated=pstmt.executeUpdate();
System.out.println("INSERT-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Subjects_In_JTable();
JOptionPane.showMessageDialog(null, "New Subject Data Inserted");
}
catch (Exception ex) {
JOptionPane.showMessageDialog(null, ex.getMessage());
}
}
else
{
JOptionPane.showMessageDialog(null, "SUBJECT CODE EXISTS, NO DUPLICATION ALLOWED");
}
}
else
{
JOptionPane.showMessageDialog(null, "One Or More Field Are Empty");
}
System.out.println("SUBJECTS => "+txt_subname.getText());
//System.out.println("Image => "+ImgPath);
}//GEN-LAST:event_jbtnINSERTActionPerformed

private void jbtnUPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUPDATEActionPerformed
if(checkInputs()){
String UpdateQuery = null;
myconnection = getConnection();
// update without image
UpdateQuery = "UPDATE SUBJECTS SET SUB_NAME = ?, SEM=? WHERE UPPER(SUB_CODE) = UPPER(?) ";
try {
pstmt = myconnection.prepareStatement(UpdateQuery);
////SELECT SUB_CODE,SUB_NAME,SEM FROM SUBJECTS
pstmt.setString(1, txt_subname.getText());
pstmt.setInt(2, Integer.parseInt((String)Jcbtxt_sem.getSelectedItem()));
pstmt.setString(3, txt_subcode.getText());

int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
JOptionPane.showMessageDialog(null, "No Subjects Updated, invalid subject code");
else
{
System.out.println("UPDATE-1-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Subjects_In_JTable();
JOptionPane.showMessageDialog(null, "Subject table Updated");
}
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
if(!txt_subcode.getText().equals("")){
try{
myconnection = getConnection();
pstmt = myconnection.prepareStatement("DELETE SUBJECTS WHERE UPPER(SUB_CODE)=UPPER(?) AND SEM=?");
pstmt.setString(1, txt_subcode.getText());
pstmt.setInt(2, Integer.parseInt((String)Jcbtxt_sem.getSelectedItem()));
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
JOptionPane.showMessageDialog(null, "No Subjects Deleted, invalid subject code");
else
{
System.out.println("DELETE-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Subjects_In_JTable();
JOptionPane.showMessageDialog(null, "Subjects Deleted");
}
}
catch (SQLException ex) {
System.err.println("SQL Exception-01 Occurred for the DELETE OF SCOURSE");
ex.printStackTrace();
JOptionPane.showMessageDialog(null, "Subjects Not Deleted");
}
}
else{
JOptionPane.showMessageDialog(null, "Subjects Not Deleted : No Id To Delete");
}

}//GEN-LAST:event_jbtnDELETEActionPerformed
private void JTable_SectionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_SectionsMouseClicked
int index = JTable_Subjects.getSelectedRow();
ShowItem(index);
}//GEN-LAST:event_JTable_SectionsMouseClicked

// Button First Show The First Record
private void jbtnFIRSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFIRSTActionPerformed
pos = 0;
ShowItem(pos);
}//GEN-LAST:event_jbtnFIRSTActionPerformed
// Button Last Show The Last Record
private void jbtnLASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLASTActionPerformed
pos = getSubjectsList().size()-1;
ShowItem(pos);
}//GEN-LAST:event_jbtnLASTActionPerformed
// Button Next Show The Next Record
private void jbtnNEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNEXTActionPerformed
pos++;
if(pos >= getSubjectsList().size()){
pos = getSubjectsList().size()-1;
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
