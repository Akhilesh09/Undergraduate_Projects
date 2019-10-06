//select sec from StaffAlloc
 
public class Students {
private String usn;
private String name;
private String sec;
private String branch;
private String status;
public Students(String usn,String name,String sec,String branch,String status){
	this.usn = usn;
	this.name = name;
	this.sec = sec;
	this.branch = branch;
	this.status = status;
    }
public String getusn(){return usn;}
public String getname(){return name;}
public String getsec(){return sec;}
public String getbranch(){return branch;}
public String getstatus(){return status;}
}
