//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
 
public class StaffStudentAttndTemp {
private int ac_year,sem,sid,slot;   
private String branch,sec,sub_code,usn,adate,status,studname;
public StaffStudentAttndTemp(int ac_year,int sem,String branch,String sec,String sub_code,String usn,String studname, int sid,String adate,int slot,String status){
        this.ac_year = ac_year;
        this.sem = sem;
        this.branch = branch.toUpperCase();
        this.sec = sec.toUpperCase();
        this.sub_code = sub_code.toUpperCase();
        this.usn = usn.toUpperCase();
        this.sid = sid;
        this.adate = adate;
        this.slot = slot;
        this.status =status.toUpperCase();
        this.studname =studname.toUpperCase();
    }
    public int getacyear(){ return ac_year;}
    public int getsem(){ return sem;}
    public String getbranch(){return  branch.toUpperCase();}
    public String getsec(){return sec.toUpperCase();}
    public String getsubcode(){return sub_code.toUpperCase();}
    public String getusn(){ return usn.toUpperCase();}
    public String getstudname(){ return studname.toUpperCase();}
    public int getsid(){return sid;}
    public String getadate(){return adate;}
    public int getslot(){return slot;}
    public String getstatus(){return status.toUpperCase();}
    public void setstatus(String nstatus){this.status=nstatus;}
}
