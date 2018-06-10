//select sec from Staffs
 
public class Staffs {
private String sname;
private String branch;
private int sid;
private byte[] picture;
public Staffs(int sid,String sname,String branch,byte[] pimg){
	this.branch = branch;
	this.sname = sname;
	this.sid = sid;
	this.picture = pimg;
    }
	public String getbranch(){return branch;}
	public String getsname(){return sname;}
	public int getsid(){return sid;}
	public byte[] getImage(){return picture;}
}
