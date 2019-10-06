//select sec from Subjects
 
public class Subjects {
private String subname;
private String subcode;
private int sem;
public Subjects( String subcode,String subname,int sem){
	this.subname = subname;
	this.subcode = subcode;
	this.sem = sem;
    }
	public String getsubname(){return subname;}
	public String getsubcode(){return subcode;}
	public int getsem(){return sem;}
}
