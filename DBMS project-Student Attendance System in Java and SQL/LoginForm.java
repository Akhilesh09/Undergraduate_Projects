import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
public class LoginForm extends JFrame implements WindowListener{
private JTextField txt_username;
private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
private JPasswordField jtfpass;
private JLabel jlbUSERNAME;
private JLabel jlbPASSWORD;
private JButton jbtnLOGIN,jbtnCHANGEPSW;
static Connection connection = null;
static Statement stmt;
static PreparedStatement pstmt,pstmt2,pstmt3;
static String dbusername ="AKHI";
static String dbpsw ="0929octdec";
private int ssid=0;
private  md5Hash PswHash;
public static void main(String[] args){
    //LOAD THE ORACLE JDBC DRIVER
try{
    Class.forName("oracle.jdbc.driver.OracleDriver");
}
catch (ClassNotFoundException e) {
    System.out.println("Where is your Oracle JDBC Driver?");
    JOptionPane.showMessageDialog(null,"Connection Failed! Where is your Oracle JDBC Driver?");   
    e.printStackTrace();
    return;
}
//System.out.println("Oracle JDBC Driver Registered!");
try {
String url = "jdbc:oracle:thin:@localhost:1521:xe";
connection = DriverManager.getConnection(url,dbusername,dbpsw);
}
catch (SQLException e) {
JOptionPane.showMessageDialog(null,"Connection Failed! Check output console");      
System.out.println("Connection Failed! Check output console");
e.printStackTrace();
return;
}
if (connection != null) {
//JOptionPane.showMessageDialog(null,"Successful login");    
System.out.println("You made it, take control your database now!");
}
else {
JOptionPane.showMessageDialog(null,"Failed to make connection to the database");     
System.out.println("Failed to make connection!");
}
//CREATE THE LOGIN FORM GUI
LoginForm f=new LoginForm();
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
f.setMinimumSize(new Dimension(800, 250));
f.setPreferredSize(new Dimension(800, 250));
f.setSize(800,250);
f.setLocationRelativeTo(null);
f.setLocation(x,y); //set the frame to the center of the screen
f.pack();
f.setVisible(true);
}
public void windowDeiconified(WindowEvent event){
}
public void windowIconified(WindowEvent event){
}
public void windowActivated(WindowEvent event){
}
public void windowDeactivated(WindowEvent event){
}
public void windowOpened(WindowEvent event){
}
public void windowClosing(WindowEvent event){
    dispose();
    System.exit(0);
}
public void windowClosed(WindowEvent event){
}
public LoginForm(){
super();
//button.setToolTipText("This is the left button");
addWindowListener(this); //Register window Listener
JPanel p=new JPanel();
p.setLayout(new GridLayout(3,2,20,20));
p.setBackground(new java.awt.Color(00, 205, 204));
jlbUSERNAME = new JLabel();
jlbUSERNAME.setFont(new java.awt.Font("Tahoma", 1, 14));
jlbUSERNAME.setText("USERNAME:");
txt_username = new JTextField(24);
txt_username.setFont(new java.awt.Font("Tahoma", 1, 14));
txt_username.setPreferredSize(new java.awt.Dimension(16, 16));
txt_username.setToolTipText("Type user Name");
p.add(jlbUSERNAME);
p.add(txt_username);
/*************************************************************/
jlbPASSWORD = new JLabel();
jlbPASSWORD.setFont(new java.awt.Font("Tahoma", 1, 14));
jlbPASSWORD.setText("PASSWORD:");
jtfpass = new JPasswordField(24);
jtfpass.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
jtfpass.setPreferredSize(new java.awt.Dimension(16, 16));
jtfpass.setToolTipText("Type password");
p.add(jlbPASSWORD);
p.add(jtfpass);
jbtnLOGIN = new JButton();
jbtnLOGIN.setFont(new java.awt.Font("Tahoma", 1, 14));
jbtnLOGIN.setText("LOGIN");
jbtnLOGIN.setIconTextGap(15);
jbtnLOGIN.setToolTipText("Click to Login");
p.add(jbtnLOGIN);
jbtnCHANGEPSW = new JButton();
jbtnCHANGEPSW.setFont(new java.awt.Font("Tahoma", 1, 14));
jbtnCHANGEPSW.setText("RESET PASSWORD");
jbtnCHANGEPSW.setIconTextGap(15);
jbtnCHANGEPSW.setToolTipText("Click to Reset Password");
p.add(jbtnCHANGEPSW);
p.add(jbtnCHANGEPSW);
JPanel jPanelptitle = new JPanel();
jPanelptitle.setBackground(new java.awt.Color(153, 255,250));
JLabel jlbaptitle = new JLabel();
jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 18));
jlbaptitle.setText("LOGIN FORM");
jPanelptitle.add(jlbaptitle);
JPanel jPanelIns = new JPanel();
jPanelIns.setBackground(new java.awt.Color(255, 255,153));
JLabel jlbIns = new JLabel();
jlbIns.setFont(new java.awt.Font("Tahoma", 1, 16));
jlbIns.setText("TO RESET PASSWORD TYPE USERNAME AND CLICK ON RESET PASSWORD");
jPanelIns.add(jlbIns);
getContentPane().add(jPanelptitle, BorderLayout.NORTH);
getContentPane().add(p, BorderLayout.CENTER);
getContentPane().add(jPanelIns, BorderLayout.SOUTH);
jbtnLOGIN.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae){
Boolean susrstatus=false;
String suname,suname1;
appmenu MyappMenu =null;
String susrname=null,susrpsw=null,susrrole=null,staffid=null,studusn=null;
String studname=null,studsec=null,studbranch=null,studstatus=null;
String staffname,staffbranch,staffsec,staffscode,subjname,staffsem;
if(ae.getSource()==jbtnLOGIN)
System.out.println(txt_username.getText()+"   "+jtfpass.getText());
String query="select username,password,usrrole,sid,usn from login where USERNAME=? and PASSWORD=? and ENCRYPTEDPASS=?";
try{
pstmt=connection.prepareStatement(query);
suname= txt_username.getText().trim();
String spsw=jtfpass.getText().trim();
PswHash = new md5Hash();
String sepsw = PswHash.md5Hash(spsw);
suname1=suname.toLowerCase();
pstmt.setString(1,suname1);
pstmt.setString(2,spsw);
pstmt.setString(3,sepsw);
ResultSet rs=pstmt.executeQuery();
if (rs.next()) {
susrname = rs.getString("username");
susrpsw =  rs.getString("password");
susrrole = rs.getString("usrrole");
ssid = rs.getInt("sid");
staffid=String.valueOf(ssid);
System.out.println("STAFFID="+ssid +" "+staffid);
studusn = rs.getString("usn");
susrstatus=true;
System.out.println(susrname);
System.out.println(susrpsw);
System.out.println(susrrole);
System.out.println(ssid);
System.out.println(studusn);
System.out.println("Login Successful");
}
else{
//System.out.println("login failed");
JOptionPane.showMessageDialog(null, "LOGIN ATTEMPT FAILED PLEASE CHECK USER NAME AND PASSWORD (CASE SENSTIVE)");
susrstatus=false;
}
}
catch(SQLException e1){
 System.out.println(e1);
}
if (susrstatus && susrrole.equals("STUDENT")){
System.out.println("EXECUTING "+studusn);
String query2="select distinct USN, NAME,BRANCH,SEC,STATUS from student where USN=? AND STATUS='AC'";
//System.out.println("EXECUTING "+query2);
try{
pstmt2=connection.prepareStatement(query2);
pstmt2.setString(1,studusn);
ResultSet rs2=pstmt2.executeQuery();
if (rs2.next()) {
studname = rs2.getString("NAME");
studsec =  rs2.getString("SEC");
studbranch = rs2.getString("BRANCH");
studstatus = rs2.getString("STATUS");
//System.out.println("STUDENT login INFO: "+studusn +" " + studname +" " + studsec+" " +studbranch+" " +studstatus);
MyappMenu = new appmenu(susrname,susrrole,true,connection,studusn,studname,studbranch,studsec,null,null,null,studstatus);
if(susrrole.equals("STUDENT")){MyappMenu.CreateStudentMenu();}
}
else{
System.out.println("YOU ARE NOT AUTHORIZED TO ACCESS THE APPLICATION AS THE YOUR STUDENT STATUS IS INACTIVE");
JOptionPane.showMessageDialog(null, "YOU ARE NOT AUTHORIZED TO ACCESS THE APPLICATION AS THE YOUR STUDENT STATUS IS INACTIVE ");
}
}
catch(SQLException e1){
System.out.println("SQL EXCEPTION ERROR FOR AFTER STUDENT LOGIN"+e1);
}
}
if(susrstatus && susrrole.equals("STAFF")){
String query3="select distinct SID, SNAME from STAFF where SID=?";
try{
pstmt3=connection.prepareStatement(query3);
pstmt3.setInt(1,ssid);
ResultSet rs3=pstmt3.executeQuery();
if (rs3.next()) {
staffname = rs3.getString("SNAME");
//System.out.println("STAFF login INFO: "+ ssid + " " +staffname +" ");
MyappMenu = new appmenu(susrname,susrrole,true,connection,staffid,staffname,null,null,null,null,null,null);
 if(susrrole.equals("STAFF")){MyappMenu.CreateStaffMenu();}
 }
 else{
 System.out.println("STAFF QUERY: failed");
JOptionPane.showMessageDialog(null, "YOU ARE NOT AUTHORIZED TO ACCESS THE APPLICATION WITH STAFF ROLE ");
 }
 }
catch(SQLException e1){
 System.out.println(e1);
}
}
if(susrstatus && susrrole.equals("ADMIN")){
System.out.println("ADMIN login INFO: "+ ssid);
JOptionPane.showMessageDialog(null, "YOU HAVE LOGGED IN AS APPLICATION ADMINISTRATOR");
MyappMenu = new appmenu(susrname,susrrole,true,connection,staffid,null,null,null,null,null,null,null);
MyappMenu.CreateAdminMenu();}
}
});

jbtnCHANGEPSW.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae){
Boolean susrstatus=false;
if(ae.getSource()==jbtnCHANGEPSW)
//System.out.println("password reset");
try{
String susrname= txt_username.getText();
String newpass=randomAlphaNumeric(8);
PswHash=new md5Hash();
String epass=PswHash.md5Hash(newpass);
String query2="update login set PASSWORD=?,ENCRYPTEDPASS=?,rstcount=rstcount+1 where username=?";
pstmt=connection.prepareStatement(query2);
pstmt.setString(1,newpass);
pstmt.setString(2,epass);
pstmt.setString(3,susrname);
pstmt.execute();
connection.commit();
SSLEmail(susrname,newpass);
}
catch(SQLException ex){
System.out.println(ex);
}
}
});
}
public String randomAlphaNumeric(int count){
//private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";    
StringBuilder builder = new StringBuilder();
while (count-- != 0) {
int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
builder.append(ALPHA_NUMERIC_STRING.charAt(character));
}
return builder.toString();
}

public void SSLEmail(String RstEmail,String RstPsw) {
boolean sucflag=false;    
final String fromEmail = "usha20171968@gmail.com"; //requires valid gmail id
final String password = "Akhi1968#"; // correct password for gmail id
final String toEmail = RstEmail; // can be any email id
System.out.println("SSLEmail Start");
Properties props = new Properties();
props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
props.put("mail.smtp.port", "465"); //SMTP Port
//try{
Authenticator auth = new Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {return new PasswordAuthentication(fromEmail, password);}};
try {
Session session = Session.getDefaultInstance(props, auth);
System.out.println("Session created");
sucflag=EmailUtil.sendEmail(session, toEmail,"You have Requested to Reset the Password", "Dear User," 
+"Your New password for Java Based Attendance Application is      "+RstPsw+"       by System Administrator,RNSIT");
}
catch (Exception e) {
System.out.println(e.getMessage());
}
if(sucflag)
JOptionPane.showMessageDialog(null, "Email Successfuly send to "+toEmail);
else
JOptionPane.showMessageDialog(null, "ERROR: Failed to send Email ");
}
}
//final String fromEmail = "principalgat@gmail.com"; //requires valid gmail id
//final String password = "Gatprincipal2016$"; // correct password for gmail id

