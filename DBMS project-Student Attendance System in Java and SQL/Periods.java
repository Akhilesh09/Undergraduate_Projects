//select ac_year,sem,st_date,end_date from period
public class Periods {
private int ac_year,sem;   
private String startdate,enddate;
public Periods(int ac_year,int sem,String startdate,String enddate){
        this.ac_year = ac_year;
	this.sem = sem;
	this.startdate = startdate;
	this.enddate =enddate;
    }
    public int getacyear(){return ac_year;}
    public int getsem(){return sem;}
    public String getstartdate(){return startdate;}
    public String getenddate(){return enddate;}
}
