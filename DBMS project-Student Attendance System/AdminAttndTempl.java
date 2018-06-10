//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
 
public class AdminAttndTempl {
private int ac_year,sem,sid,slot;   
private String branch,sec,sub_code,usn,adate,status,studname,staffname;
public AdminAttndTempl(int ac_year,int sem,String branch,String sec,String sub_code,String usn,String studname, int sid,String staffname, String adate,int slot,String status){
        this.ac_year = ac_year;
        this.sem = sem;
        this.branch = branch;
        this.sec = sec;
        this.sub_code = sub_code;
        this.usn = usn;
        this.sid = sid;
        this.adate = adate;
        this.slot = slot;
        this.status =status;
        this.studname =studname;
        this.staffname =staffname;
    }
    public int getacyear(){ return ac_year;}
    public int getsem(){ return sem;}
    public String getbranch(){return  branch;}
    public String getsec(){return sec;}
    public String getsubcode(){return sub_code;}
    public String getusn(){ return usn;}
    public String getstudname(){ return studname;}
    public String getstaffname(){ return staffname;}
    public int getsid(){return sid;}
    public String getadate(){return adate;}
    public int getslot(){return slot;}
    public String getstatus(){return status;}
    public void setstatus(String nstatus){this.status=nstatus;}
}
