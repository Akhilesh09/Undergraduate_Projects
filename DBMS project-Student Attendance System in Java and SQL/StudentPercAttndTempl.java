//select ac_year,sem,branch,sec,sub_code,usn,sid,adate,slot,status from attendance
 
public class StudentPercAttndTempl {
private int ac_year,sem,perc;
private String sub_code,usn,studname;
public StudentPercAttndTempl(int ac_year,int sem,String sub_code,String usn,String studname,int perc){
        this.ac_year = ac_year;
        this.sem = sem;
        this.sub_code = sub_code;
        this.usn = usn;
        this.perc = perc;
        this.studname = studname;
    }
    public int getAcyear(){ return ac_year;}
    public int getSem(){ return sem;}
    public String getSubcode(){return sub_code;}
    public String getusn(){ return usn;}
    public String studname(){ return studname;}
    public int getPerc(){return perc;}
}
