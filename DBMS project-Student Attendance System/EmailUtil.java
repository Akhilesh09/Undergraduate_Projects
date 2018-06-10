import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
public class EmailUtil {
    public static boolean sendEmail(Session session, String toEmail, String subject, String body){
        try{
          MimeMessage msg = new MimeMessage(session);
          msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
          msg.addHeader("format", "flowed");
          msg.addHeader("Content-Transfer-Encoding", "8bit");
          msg.setFrom(new InternetAddress("vasuprada93@gmail.com", "ATTENDANCE SYSTEM"));
          msg.setReplyTo(InternetAddress.parse("vasuprada93@gmail.com", false));
          msg.setSubject(subject, "UTF-8");
          msg.setText(body, "UTF-8");
          msg.setSentDate(new Date());
          msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
          System.out.println("Message is ready");
          Transport.send(msg);  
          System.out.println("EMail Sent Successfully!!");
        //  JOptionPane.showMessageDialog(null, "EMail Sent Successfully to "+toEmail);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
       }
    }