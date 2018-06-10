//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
 
public class StaffStudPercAttndTempl {
private int ac_year,sem,sid,perc;
private String sec,branch,sub_code,usn,studname;
public StaffStudPercAttndTempl(int ac_year,int sem,String branch,String sec, String sub_code,int sid,String usn,String studname,int perc){
        this.ac_year = ac_year;
        this.sem = sem;
	this.branch = branch;
	this.sec = sec;
        this.sub_code = sub_code;
        this.sid = sid;
        this.usn = usn;
        this.perc = perc;
        this.studname = studname;
    }
    public int getacyear(){ return ac_year;}
    public int getsem(){ return sem;}
    public String getbranch(){return branch;}
    public String getsec(){return sec;}
    public String getsubcode(){return sub_code;}
    public int getsid(){ return sid;}
    public String getusn(){return usn;}
    public String getname(){return studname;}
    public int getperc(){return perc;}
}
