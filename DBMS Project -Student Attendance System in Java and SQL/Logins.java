//select * from login
 /*
USERNAME,PASSWORD,ENCRYPTEDPASS,SID, USN,USRROLE
*/
public class Logins {
private String username;
private int sid;
//private String encryptedpass;
private String usn;
private String usrrole;
public Logins(String username,int sid,String usn,String usrrole){
this.username=username;
//this.encryptedpass=encryptedpass;
this.sid=sid;
this.usn=usn;
this.usrrole=usrrole;
}
public String getusername(){return username;}
//public String getepass(){return encryptedpass;}
public int getsid(){return sid;}
public String getusn(){return usn;}
public String getusrrole(){return usrrole;}
}
