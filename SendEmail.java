package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.sql.Statement;
import javafx.util.Pair;

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
        final String user="jmcgregtemp@gmail.com";
        final String password="mcgregor1"; 

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
            String cc = getRM(email);
            if(!cc.equals(""))
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            
            String[] startEndDates = getStartEnd(email);
            message.setSubject("Performica Report["+ getStreamName(email) + "]["+ startEndDates[0] + "]-["+startEndDates[1]+"]");


            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("Congratulations, " + getName(email) + "\nHere is your Performica Report! Inside is the score of each module you completed and a small graph"+
            " to show where you stand compared to the class average. We hope you enjoyed your experience in our " +
            " training program and wish you the best for your future!");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String path = "C:/Users/syntel/Music/" + id + ".pdf";
            String filename = id + ".pdf";
            DataSource source = new FileDataSource(path);
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
    
    public static void sendBatchEmails(String classid) {
        Map<String,String> emails = getBatchEmails(classid);
        
    	
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
    
    static String[] getEmployee(String email) {//getEmployee returns an array of attributes
    	
    	try
	{
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Type 4 Driver Pure Java Driver
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","Student_Performance","Student_Performance");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select employee_id, name, class_id, manager_id from employees where email = '" + email + "'");
            String emp[] = new String[4];
            while(rs.next()){
                emp[0] = rs.getString("employee_id");
                emp[1] = rs.getString("name");
                emp[2] = rs.getString("class_id");
                emp[3] = rs.getString("manager_id");
                
            }
			
            con.commit();
	    st.close();
	    con.close();
            return emp;
                
	}
		
	catch (Exception ex) 
	{
            System.out.println(ex);
	}
    	
    	return null;
    }
    
    static String getEmpId(String email) {
    	
    	try
	{
            String[] emp = getEmployee(email);
            return emp[0];
                
	}
        catch (Exception ex) 
        {
            System.out.println(ex);
	}
    	
    	return " ";
    }
    
    static String getName(String email) {
    	
    	try
	{
            String[] emp = getEmployee(email);
            return emp[1];
	}	
        catch (Exception ex) 
        {
            System.out.println(ex);
	}
    	
    	return " ";
    }
        
    static String getRM(String email) {
    	
    	try
	{
            String[] emp = getEmployee(email);
            return emp[3];
	}
		
	catch (Exception ex) 
	{
            System.out.println(ex);
	}
    	
    	return " ";
    }
    
    static String getStreamName(String email) {//getEmployee returns an array of attributes
    	
    	try
	{
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Type 4 Driver Pure Java Driver
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","Student_Performance","Student_Performance");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select s.stream_name from employees e ,class c ,stream s where e.email = '" + email + "' AND e.class_id=c.class_id AND c.stream_id = s.stream_id");
            
            while(rs.next()){
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
        static String[] getStartEnd(String email) {//getEmployee returns an array of attributes
    	
    	try
	{
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Type 4 Driver Pure Java Driver
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","Student_Performance","Student_Performance");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select c.start_date,c.end_date from employees e ,class c where e.email = '" + email + "' AND e.class_id=c.class_id");
            String duration[] = new String[2];
            while(rs.next()){
                duration[0] = rs.getString(1).substring(0,10);
                duration[1] = rs.getString(2).substring(0,10);
                
            }
			
            con.commit();
	    st.close();
	    con.close();
           
            return duration;
	}
		
	catch (Exception ex) 
	{
            System.out.println(ex);
	}
    	
    	return null;
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
    
    
}