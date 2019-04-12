package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.sql.Statement;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
    
    public static void sendIdividualEmail(String email, String id) {
    	
        String host="smtp.gmail.com";
        final String user="jmcgregtemp@gmail.com";//ENTER YOUR EMAIL!!
        final String password="mcgregor1"; //ENTER YOUR PASSWORD

        String to=email;

        //imported code
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });
        
        //imported code
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Employee Performance");


            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("Attached to this email is the pdf doc of the employee's performance.");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "C:/Users/syntel/Music/" + id + ".pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);
             Transport tr = session.getTransport("smtp");
            tr.send(message);

            System.out.println("message sent!");

        }
        catch (MessagingException mex)
        {
            System.out.println("Error: unable to send message....");
            System.out.println(mex.toString());
            
        }
        
    }
    
    public static void sendBatchEmails(Map<String,String> emails) {
    	
    	for (Map.Entry<String,String> entry : emails.entrySet()) {
    		sendIdividualEmail(entry.getKey(),entry.getValue());
    		
    	}		
    }
    
    static boolean validateEmail(String username)
	{
		int count = 0;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver"); // Type 4 Driver Pure Java Driver
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","Student_Performance","Student_Performance");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select email from employees where email = " + "'" + username + "'");
									
			while(rs.next())
			{
				++count;
			}
			con.commit();
			st.close();
			con.close();
        
			if(count == 0 || count > 1)
			{
				System.out.println("Not Valid Email!!!");
				return false;
			}
			else if(count ==1)
			{
				System.out.println("Valid Email!");
				return true;
			}
		                
		}catch (Exception ex) 
		{
					System.out.println(ex);
		}
				

		return false;
	}
    
    static boolean validateClassId(String id)
	{
		int count = 0;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver"); // Type 4 Driver Pure Java Driver
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","Student_Performance","Student_Performance");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select class_id from class where class_id = " + "'" + id + "'");
									
			while(rs.next())
			{
				++count;
			}
					
			con.commit();
			st.close();
			con.close();
			        
			        
			if(count == 0 || count > 1)
			{
				System.out.println("Not Valid Class ID");
				return false;
			}
			else if(count ==1)
			{
				System.out.println("Success: Valid Class ID");
				return true;
			}
		                
		}catch (Exception ex) 
		{
					System.out.println(ex);
		}
				
		return false;
	}
    
    static String getEmpId(String username) {
    	
    	try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver"); // Type 4 Driver Pure Java Driver
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","Student_Performance","Student_Performance");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select employee_id from employees where email = " + "'" + username + "'");
			
			while(rs.next())
			{
				return rs.getString(1);
			}
			
			con.commit();
	        st.close();
	        con.close();
                
	    }
		
		catch (Exception ex) 
		{
			System.out.println(ex);
	    }
    	
    	return " ";
    }
    
    static Map<String,String> getBatchEmails(String classid) {
    	
    	Map<String,String> batchEmails = new HashMap<String,String>();
    	
    	try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver"); // Type 4 Driver Pure Java Driver
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","Student_Performance","Student_Performance");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select employee_id, email from employees where class_id = " + "'" + classid + "'");
			
			while(rs.next())
			{
				batchEmails.put(rs.getString(2),rs.getString(1));
			}
			
			con.commit();
	        st.close();
	        con.close();
                
	    }
		
		catch (Exception ex) 
		{
			System.out.println(ex);
	    }
    	
    	return batchEmails;
    }
    
    public static void main(String[] args) {
        PDF temp = new PDF();
        
        //temp.set()
    }
}