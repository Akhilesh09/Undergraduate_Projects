import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 *
 * @author AKHILESH VIJAYAKUMAR
 */
public class LoginApp{
// Variables declaration - do not modify//GEN-BEGIN:variables
private JButton Btn_Choose_Image;
private JButton jbtnFIRST;
private JButton jbtnINSERT;
private JButton jbtnLAST;
private JButton jbtnNEXT;
private JButton jbtnPREVIOUS;
private JTable  JTable_Logins;
private JButton jbtnUPDATE;
private JButton jbtnDELETE;
//select sec section
private JLabel jlbUSERNAME;
private JTextField txt_username;
private JLabel jlbSID;
private JTextField txt_sid;
private JLabel jlbUSN;
private JTextField txt_usn;
private JLabel jlbUSRROLE;
private JComboBox  Jcbtxt_usrrole;
private JPanel jPanel1;
private JPanel jPnlButton;
private JScrollPane jScrollPane1;
private JLabel lbl_image;
private java.sql.Date sqladate;

private String ImgPath = null;
private int pos = 0,sid,i_sid;
private String loginName,loginRole,usn,staffid,s_psw,s_epsw,s_usnrname,s_usn,s_srole,s_sid;
private Connection myconnection;
private JFrame f;
private ResultSet rs;
private PreparedStatement ps,ps1;
private String [] userrole_lov={"STAFF","STUDENT","ADMIN"}; 
private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//private final static String salt="AKHILESH-RNSIT Attendance Application PROJECT IN  CORE JAVA";
final String fromEmail = "principalgat@gmail.com"; //requires valid gmail id
final String password = "Gatprincipal2016$"; // correct password for gmail id
private md5Hash PswHash;
private Logins login;      
// End of variables declaration//GEN-END:variables
 /**
  * Creates new form LoginApp
  */
public LoginApp(String loginName,String loginRole, Boolean loginStatus, Connection myconnection, String staffid, String usn) {
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
  
public void CreateLoginAppForm()  {
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
     Show_Logins_In_JTable();
}
// Function To Connect To ORACLE Database
//select USERNAME,PASSWORD,ENCRYPTEDPASS,SID, USN,USRROLE FROM LOGIN
// Check Input Fields
public boolean checkInputs(){
if(txt_username.getText().equals("")  ||  (String)Jcbtxt_usrrole.getSelectedItem() == null)
return false;   
else
return true; 
}

public String randomAlphaNumeric(int count){
StringBuilder builder = new StringBuilder();
while (count-- != 0) {
int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
builder.append(ALPHA_NUMERIC_STRING.charAt(character));
}
return builder.toString();
}


public void SSLEmail(String RstEmail,String RstPsw) {
final String toEmail = RstEmail; // can be any email id
System.out.println("SSLEmail Start");
Properties props = new Properties();
props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
props.put("mail.smtp.port", "465"); //SMTP Port
Authenticator auth = new Authenticator() {
//override the getPasswordAuthentication method
protected PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication(fromEmail, password);
}
};
Session session = Session.getDefaultInstance(props, auth);
System.out.println("Session created");
EmailUtil.sendEmail(session, toEmail,"You have Requested for New Account", "Dear User, New Attendance Application Account has been created. Your New password for Attendance Application is "+RstPsw);
//EmailUtil.sendAttachmentEmail(session, toEmail,"SSLEmail Testing Subject with Attachment", "SSLEmail Testing Body with Attachment");
//EmailUtil.sendImageEmail(session, toEmail,"SSLEmail Testing Subject with Image", "SSLEmail Testing Body with Image");
}

// Display Data In JTable:
//      1 - Fill ArrayList With The Data

public ArrayList<Logins> getLoginsList(){
ArrayList<Logins> loginList  = new ArrayList<Logins>();
myconnection = getConnection();
String query = "select USERNAME,SID, USN,USRROLE FROM LOGIN ORDER BY USERNAME";
try {
ps=myconnection.prepareStatement(query);
rs=ps.executeQuery();  
if(rs==null) return null;
else
{
while(rs.next()){
login = new Logins(rs.getString("USERNAME"),rs.getInt("SID"),rs.getString("USN"),rs.getString("USRROLE"));
loginList.add(login);}
}
}
catch (SQLException ex) {
JOptionPane.showMessageDialog(null, "SQLEXCEPTION"+ex.getMessage());
return null;
}
return loginList;
}
//      2 - Populate The JTable
public void Show_Logins_In_JTable(){
ArrayList<Logins> list = getLoginsList();
DefaultTableModel model = (DefaultTableModel)JTable_Logins.getModel();
// clear jtable content
//select USERNAME,PASSWORD,ENCRYPTEDPASS,SID, USN,USRROLE FROM LOGIN
model.setRowCount(0);
Object[] row = new Object[4];
for(int i = 0; i < list.size(); i++){
row[0] = list.get(i).getusername();
row[1] = list.get(i).getsid();
row[2] = list.get(i).getusn();
row[3] = list.get(i).getusrrole();
model.addRow(row);
}
}
// Show Data In Inputs
public void ShowItem(int index){
txt_username.setText(getLoginsList().get(index).getusername());
txt_sid.setText(String.valueOf(getLoginsList().get(index).getsid()));
txt_usn.setText(getLoginsList().get(index).getusn());
Jcbtxt_usrrole.setSelectedItem(getLoginsList().get(index).getusrrole());
}
private void initComponents() {
jPanel1 = new JPanel();
jPanel1.setBackground(new java.awt.Color(153, 255, 153));
jPanel1.setLayout(new GridLayout(2,4,12,12));
jlbUSERNAME = new JLabel();
jlbUSERNAME.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbUSERNAME.setText("USERNAME:");
txt_username = new JTextField(5);
txt_username.setFont(new java.awt.Font("Tahoma", 1, 14));  
txt_username.setPreferredSize(new java.awt.Dimension(24, 20));

//row 2
jlbSID = new JLabel();
jlbSID.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbSID.setText("STAFF ID:");
txt_sid = new JTextField(5);
txt_sid.setFont(new java.awt.Font("Tahoma", 1, 14)); 
txt_sid.setPreferredSize(new java.awt.Dimension(24, 20));

jlbUSN = new JLabel();
jlbUSN.setFont(new java.awt.Font("Tahoma", 1, 14)); 
jlbUSN.setText("USN:");
txt_usn = new JTextField(3); 
txt_usn.setPreferredSize(new java.awt.Dimension(24, 20));
//rows3
jlbUSRROLE = new JLabel();
jlbUSRROLE.setFont(new java.awt.Font("Tahoma", 1, 14));  
jlbUSRROLE.setText("USER ROLE:");
Jcbtxt_usrrole = new JComboBox(userrole_lov);
Jcbtxt_usrrole.setFont(new java.awt.Font("Tahoma", 1, 14));  
Jcbtxt_usrrole.setPreferredSize(new java.awt.Dimension(24, 20));

jPanel1.add(jlbUSERNAME);
jPanel1.add(txt_username);
jPanel1.add(jlbSID);
jPanel1.add(txt_sid);
jPanel1.add(jlbUSN);
jPanel1.add(txt_usn);
jPanel1.add(jlbUSRROLE);
jPanel1.add( Jcbtxt_usrrole);
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
jPnlscrtb.setLayout(new GridLayout(1,1,25,25));
jScrollPane1 = new JScrollPane();
JTable_Logins = new JTable();
DefaultTableModel MytableModel = new DefaultTableModel(
new Object [][] {},
//select USERNAME,PASSWORD,ENCRYPTEDPASS,SID, USN,USRROLE FROM LOGIN
new String [] {"USERNAME","SID","USN","USRROLE"});
JTable_Logins.setModel(MytableModel);
JTableHeader header = JTable_Logins.getTableHeader();
header.setUpdateTableInRealTime(true);
jPnlscrtb.add(jScrollPane1);
JTable_Logins.setSelectionForeground(Color.white);
JTable_Logins.setSelectionBackground(new java.awt.Color(55, 55, 55));
jScrollPane1.setViewportView(JTable_Logins);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbaptitle.setText("ADMIN PANEL FOR SETTING UP ATTENDANCE APPLICATION ACCOUNTS FOR STAFF AND STUDENTS");
jPanelptitle.add(jlbaptitle);
JPanel jPanel2N = new JPanel();
jPanel2N.setLayout(new GridLayout(3,1,12,12));
f=new JFrame();
JPanel jPanelIns = new JPanel();
jPanelIns.setBackground(new java.awt.Color(255, 255,153));
jPanel2N.add(jPanelptitle);
jPanel2N.add(jPnlButton);
jPanel2N.add(jPanelIns);
JLabel jlbIns = new JLabel();
jlbIns.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbIns.setText("IMPORTANT NOTE:STUDENT & STAFF DATA MUST BE SETUP BEFORE LOGIN ACCOUNTS CAN SETUP FOR STAFF AND STUDENTS");
jPanelIns.add(jlbIns);
f.add(jPanel2N, BorderLayout.NORTH);
//f.add(jPanel1, BorderLayout.NORTH);
f.add(jPnlscrtb, BorderLayout.CENTER);
f.add(jPanel1, BorderLayout.SOUTH);
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
f.setVisible(true);
JTable_Logins.addMouseListener(new java.awt.event.MouseAdapter() {
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

private void jbtnINSERTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnINSERTActionPerformed
int datastatus=0;
String Myoptmsg=null;
if(checkInputs()){ 
if((txt_usn.getText().trim().equals("")) && (txt_sid.getText().trim().equals(""))){
Myoptmsg="BOTH STAFFID AND USN FIELDS ARE EMPTY, ONLY EITHER STAFF OR USN MUST BE FILLED";
datastatus=1;
} 
if((txt_usn.getText().trim().length()>0) && (txt_sid.getText().trim().length()>0)){
Myoptmsg= "EITHER STAFFID OR USN MUST BE EMPTY, FILL ONLY ONE OF THEM";   
datastatus=2;
}   
s_srole  = (String)Jcbtxt_usrrole.getSelectedItem();
if((txt_usn.getText().trim().length()<=0) && (s_srole.equals("STUDENT"))){  
Myoptmsg= "You cannot select student role for admin or staff";     
datastatus=3;    
}
if((txt_usn.getText().trim().length()>0) && ((s_srole.equals("STAFF")) || (s_srole.equals("ADMIN")))){ 
Myoptmsg= "You cannot select staff role for student";     
datastatus=4;   
}
if(datastatus==0){ //No error in imput data
s_usnrname=txt_username.getText().trim();
s_epsw=null;
if(txt_sid.getText().trim().length()>0) 
i_sid=Integer.parseInt(txt_sid.getText());
else
{
s_sid=null;
i_sid=0;
}
s_usn=txt_usn.getText().trim();
try {
myconnection = getConnection();
ps1 = myconnection.prepareStatement("SELECT count(*) COUNTOFROWS FROM LOGIN WHERE (SID=? AND USN IS NULL) OR (SID IS NULL AND USN=?)");
if(i_sid==0)
ps1.setNull(1,java.sql.Types.INTEGER);
else
ps1.setInt(1,i_sid);
if(s_usn.length()==0)
ps1.setNull(2,java.sql.Types.VARCHAR);
else
ps1.setString(2,s_usn);
rs=ps1.executeQuery();
if(rs.next()){
int x= rs.getInt("COUNTOFROWS");
if(x==0){ // new user does not exist
ps = myconnection.prepareStatement("INSERT INTO LOGIN values(?,?,?,?,?,UPPER(?),0)");
//select USERNAME,PASSWORD,ENCRYPTEDPASS,SID, USN,USRROLE FROM LOGIN
s_psw=randomAlphaNumeric(8);
PswHash=new md5Hash();
String s_epsw= PswHash.md5Hash(s_psw); //MD5 algorithm one way encryption
ps.setString(1,s_usnrname);
ps.setString(2,s_psw);
ps.setString(3,s_epsw);
if(i_sid==0)
ps.setNull(4,java.sql.Types.INTEGER);
else
ps.setInt(4,i_sid);
if(s_usn.length()==0)
ps.setNull(5, java.sql.Types.VARCHAR);
else
ps.setString(5,s_usn);
ps.setString(6,s_srole);
int rowsUpdated=ps.executeUpdate();
if(rowsUpdated==0)
JOptionPane.showMessageDialog(null, "No user Data Inserted");
else
{
System.out.println("INSERT-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Logins_In_JTable();
JOptionPane.showMessageDialog(null, "Data Inserted");
SSLEmail(s_usnrname,s_psw);
}
}
else
JOptionPane.showMessageDialog(null, "user already exist");
}
else
JOptionPane.showMessageDialog(null, "EMPTY OR NO MATCHING ROWS");
}
catch (Exception ex) {
JOptionPane.showMessageDialog(null, ex.getMessage());
}
}
else //error in imput data
{
JOptionPane.showMessageDialog(null, Myoptmsg);
}
}
else
{
JOptionPane.showMessageDialog(null, "One Or More Field Are Empty");
}
System.out.println("LOGIN => "+txt_username.getText());
}

private void jbtnUPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUPDATEActionPerformed
if(checkInputs()){
String UpdateQuery = null;
myconnection = getConnection();
// update without image
UpdateQuery = "update LOGIN set USRROLE=?  where   USERNAME=?";
try{
ps = myconnection.prepareStatement(UpdateQuery);
//select USERNAME,PASSWORD,ENCRYPTEDPASS,SID, USN,USRROLE FROM LOGIN
ps.setString(1,(String)Jcbtxt_usrrole.getSelectedItem());
ps.setString(2,txt_username.getText());
int rowsUpdated=ps.executeUpdate();
if(rowsUpdated==0)
JOptionPane.showMessageDialog(null, "No user Data updated, invalid userid");
else
{
System.out.println("UPDATE-1-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Logins_In_JTable();
JOptionPane.showMessageDialog(null, "User login Data Updated");
}
}
catch (SQLException ex) {
System.err.println("SQL Exception-02 Occurred for the UPDATE OF login table");
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
if(!txt_username.getText().equals("")){
//select USERNAME,PASSWORD,ENCRYPTEDPASS,SID, USN,USRROLE FROM LOGIN
try{
myconnection = getConnection();
ps = myconnection.prepareStatement("DELETE LOGIN WHERE USERNAME=?");
ps.setString(1,txt_username.getText());
int rowsUpdated=ps.executeUpdate();
if(rowsUpdated==0)
JOptionPane.showMessageDialog(null, "No user Data Deleted");
else
{
System.out.println("DELETE-Commiting data here...."+rowsUpdated);
myconnection.commit();
Show_Logins_In_JTable();
JOptionPane.showMessageDialog(null, "Logins Deleted");
}
}
catch (SQLException ex) {
System.err.println("SQL Exception-01 Occurred for the DELETE OF login table");
ex.printStackTrace();
JOptionPane.showMessageDialog(null, "Logins Not Deleted");
}
}
else{
JOptionPane.showMessageDialog(null, "Logins Not Deleted : No Id To Delete");
}

}//GEN-LAST:event_jbtnDELETEActionPerformed
private void JTable_SectionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_SectionsMouseClicked
int index = JTable_Logins.getSelectedRow();
ShowItem(index);
}//GEN-LAST:event_JTable_SectionsMouseClicked

// Button First Show The First Record
private void jbtnFIRSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFIRSTActionPerformed
pos = 0;
ShowItem(pos);
}//GEN-LAST:event_jbtnFIRSTActionPerformed
// Button Last Show The Last Record
private void jbtnLASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLASTActionPerformed
pos = getLoginsList().size()-1;
ShowItem(pos);
}//GEN-LAST:event_jbtnLASTActionPerformed
// Button Next Show The Next Record
private void jbtnNEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNEXTActionPerformed
pos++;
if(pos >= getLoginsList().size()){
pos = getLoginsList().size()-1;
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
