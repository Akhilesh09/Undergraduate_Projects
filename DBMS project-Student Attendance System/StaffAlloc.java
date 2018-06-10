//select sec from StaffAlloc
 
public class StaffAlloc {
private int sid;
private String subcode;
private String sec;
private String branch;
public StaffAlloc(int sid,String subcode,String sec,String branch){
	this.sid = sid;
	this.subcode = subcode;
	this.sec = sec;
	this.branch = branch;
    }
public int getsid(){return sid;}
public String getsubcode(){return subcode;}
public String getsec(){return sec;}
public String getbranch(){return branch;}
}
