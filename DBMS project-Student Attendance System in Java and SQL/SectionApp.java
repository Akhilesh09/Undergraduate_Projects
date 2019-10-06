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
public class SectionApp {
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton Btn_Choose_Image;
private JButton jbtnFIRST;
private JButton jbtnINSERT;
private JButton jbtnLAST;
private JButton jbtnNEXT;
private JButton jbtnPREVIOUS;
private JTable  JTable_Sections;
private JButton jbtnDELETE;
//select sec section
private JLabel jlbSEC;
private JComboBox Jcbtxt_sec;
private JPanel jPanel1;
private JPanel jPnlButton;
private JScrollPane jScrollPane1;
private JLabel lbl_image;
private java.sql.Date sqladate;
private PreparedStatement pstmt=null;
private String ImgPath = null;
private int pos = 0,sid;
private String loginName,loginRole,usn,staffid;
private Connection myconnection;
private JFrame f;
private ResultSet rs;
private String [] al_section={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T"};
private ArrayList<Section> sectionList,list;
private Section section;
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form SectionApp
  */
public SectionApp(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String staffid, String usn) {
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
  
public void CreateSectionForm()  { 
    
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
     Show_Sections_In_JTable();
}
//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
// Check Input Fields
public boolean checkInputs(){
if((String)Jcbtxt_sec.getSelectedItem() == null)
return false;   
else
return true; 
}

// Display Data In JTable:
//      1 - Fill ArrayList With The Data

public ArrayList<Section> getSectionList(){
sectionList  = new ArrayList<Section>();
myconnection = getConnection();
String query = "SELECT SEC FROM SECTION ORDER BY SEC";
try {
pstmt=myconnection.prepareStatement(query);
rs=pstmt.executeQuery();
//System.out.println("No of rows="+rowcount);
if(rs == null) return null;
else
{
while(rs.next()){
section = new Section(rs.getString("SEC"));
sectionList.add(section);}
}
}
catch (SQLException ex) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+ex.getMessage());
return null;
}
return sectionList;
}
//      2 - Populate The JTable
public void Show_Sections_In_JTable(){
if(getSectionList()==null){
JOptionPane.showMessageDialog(null,"NO SECTION DATA");    
}
else
{      
list = getSectionList();
DefaultTableModel model = (DefaultTableModel)JTable_Sections.getModel();
// clear jtable content
//select sec from section
model.setRowCount(0);
Object[] row = new Object[1];
for(int i = 0; i < list.size(); i++){
row[0] = list.get(i).getSec();
model.addRow(row);
}
}
}
// Show Data In Inputs
public void ShowItem(int index){
Jcbtxt_sec.setSelectedItem(getSectionList().get(index).getSec());
}
private void initComponents() {
jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 153));
jPanel1.setLayout(new GridLayout(1,1,25,25));
jlbSEC = new JLabel();
jlbSEC.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbSEC.setText("SECTION:");
Jcbtxt_sec = new JComboBox(al_section);
Jcbtxt_sec.setFont(new java.awt.Font("Tahoma", 1, 14));  
Jcbtxt_sec.setPreferredSize(new java.awt.Dimension(24, 20));
jPanel1.add(jlbSEC);
jPanel1.add(Jcbtxt_sec);
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
jPnlButton.setLayout(new GridLayout(1,7,12,12));
jPnlButton.setBackground(new java.awt.Color(255, 153, 51));
jPnlButton.add(jbtnINSERT);
jPnlButton.add(jbtnDELETE);
jPnlButton.add(jbtnFIRST);
jPnlButton.add(jbtnNEXT);
jPnlButton.add(jbtnPREVIOUS);
jPnlButton.add(jbtnLAST);
JPanel jPnlscrtb = new JPanel();
jPnlscrtb.setLayout(new GridLayout(1,1,12,12));
jScrollPane1 = new JScrollPane();
JTable_Sections = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},
//SELECT SEC from section
new String [] {"SECTION"});
JTable_Sections.setModel(MytableModel);
JTableHeader header = JTable_Sections.getTableHeader();
header.setUpdateTableInRealTime(true);
jPnlscrtb.add(jScrollPane1);
JTable_Sections.setSelectionForeground(Color.white);
JTable_Sections.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_Sections);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbaptitle.setText("ADMIN FORM TO CONFIGURE THE SECTIONS");
jPanelptitle.add(jlbaptitle);
JPanel jPanel2N = new JPanel();
jPanel2N.setLayout(new GridLayout(3,1,25,25));
f=new JFrame();
jPanel2N.add(jPanelptitle);
jPanel2N.add(jPnlButton);
jPanel2N.add(jPanel1);
JPanel jPanelIns = new JPanel();
jPanelIns.setBackground(new java.awt.Color(255, 255,153));
JLabel jlbIns = new JLabel();
jlbIns.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbIns.setText("IMPORTANT NOTE: ADMIN MUST CONFIGURE THE REQUIRED NUMBER OF SECTIONS HERE FOR ATTENDANCE APP");
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
f.setVisible(true); JTable_Sections.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseClicked(java.awt.event.MouseEvent evt) {
JTable_SectionsMouseClicked(evt);
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

public boolean GetSECTION(){
myconnection = getConnection();
String sql1 = "select SEC from SECTION where SEC=?";
try{
pstmt = myconnection.prepareStatement(sql1);
pstmt.setString(1, (String)Jcbtxt_sec.getSelectedItem());
int rowsUpdated=pstmt.executeUpdate();
if(rowsUpdated==0)
return true;
else
return false; 
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION-getSECTION ERROR"+e.getMessage());
return false;
}   
}

private void jbtnINSERTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnINSERTActionPerformed
if(checkInputs()){
if(GetSECTION()){    
try {
myconnection = getConnection();
pstmt = myconnection.prepareStatement("INSERT INTO SECTION values(UPPER(?))");
////SELECT SEC FROM SECTION
pstmt.setString(1, (String)Jcbtxt_sec.getSelectedItem());
int rowsUpdated=pstmt.executeUpdate();
System.out.println("INSERT-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Sections_In_JTable();
JOptionPane.showMessageDialog(null, "Data Inserted into Section table");
}
catch (Exception ex) {
JOptionPane.showMessageDialog(null, ex.getMessage());
}
}
else
{
JOptionPane.showMessageDialog(null, "Section Already exists, duplication not allowed");    
}
}
else
{
JOptionPane.showMessageDialog(null, "One Or More Field Are Empty");
}
System.out.println("SECTION => "+Jcbtxt_sec.getSelectedItem());
//System.out.println("Image => "+ImgPath);
}//GEN-LAST:event_jbtnINSERTActionPerformed

// Button Delete The Data From MySQL Database
private void jbtnDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDELETEActionPerformed
if(!Jcbtxt_sec.getSelectedItem().equals("")){
try {
myconnection = getConnection();
pstmt = myconnection.prepareStatement("DELETE SECTION WHERE UPPER(SEC)=UPPER(?)");
pstmt.setString(1,(String)Jcbtxt_sec.getSelectedItem());
int rowsUpdated=pstmt.executeUpdate();
System.out.println("DELETE-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Sections_In_JTable();
JOptionPane.showMessageDialog(null, "Sectioin Deleted");
}
catch (SQLException ex) {
JOptionPane.showMessageDialog(null, "Sectioin Not Deleted");
}
}
else{
JOptionPane.showMessageDialog(null, "Sectioin Not Deleted : No Id To Delete");
}

}//GEN-LAST:event_jbtnDELETEActionPerformed
private void JTable_SectionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_SectionsMouseClicked
int index = JTable_Sections.getSelectedRow();
ShowItem(index);
}//GEN-LAST:event_JTable_SectionsMouseClicked

// Button First Show The First Record
private void jbtnFIRSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFIRSTActionPerformed
pos = 0;
ShowItem(pos);
}//GEN-LAST:event_jbtnFIRSTActionPerformed
// Button Last Show The Last Record
private void jbtnLASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLASTActionPerformed
pos = getSectionList().size()-1;
ShowItem(pos);
}//GEN-LAST:event_jbtnLASTActionPerformed
// Button Next Show The Next Record
private void jbtnNEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNEXTActionPerformed
pos++;
if(pos >= getSectionList().size()){
pos = getSectionList().size()-1;
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
