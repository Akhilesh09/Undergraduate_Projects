import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class md5Hash{
    private String Message;
    private final static String salt="AKHILESH-RNSIT Attendance Application PROJECT IN  CORE JAVA";
    public md5Hash(){
        // initialise instance variables
    }
    public static String md5Hash(String message) {
        String md5 = "";
        if(null == message) 
        return null;        
        message = message+salt;//adding a salt to the string before it gets hashed.
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");//Create MessageDigest object for MD5
            digest.update(message.getBytes(), 0, message.length());//Update input string in message digest
            md5 = new BigInteger(1, digest.digest()).toString(16);//Converts message digest value in base 16 (hex)  
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
