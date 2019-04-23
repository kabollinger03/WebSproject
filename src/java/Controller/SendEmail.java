package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.sql.Statement;
import java.util.Calendar;

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
            System.out.println("here");
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String path = "C:/Users/syntel/Music/" + id + ".pdf";
            String filename = id + ".pdf";
            DataSource source = new FileDataSource(path);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);


            
            messageBodyPart = new MimeBodyPart();
            String htmlText = "<html lang=\"en\">\n" +
            "<head>\n" +
            "<title>Performica</title>\n" +
            "<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<style>\n" +
            "* {\n" +
            "  box-sizing: border-box;\n" +
            "}\n" +
            "\n" +
            "/* Clear floats after the columns */\n" +
            "section:after {\n" +
            "  content: \"\";\n" +
            "  display: table;\n" +
            "  clear: both;\n" +
            "}\n" +
            "@media (max-width: 600px) {\n" +
            "  nav, article {\n" +
            "    width: 100%;\n" +
            "    height: auto;\n" +
            "  }\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body style=\"  font-family: Verdana, sans-serif;\n" +
            "  width: 800px;\n" +
            "  border: solid;\n" +
            "  margin: 0px 0px 0px 10px;\n" +
            "  padding-left: 0px;\">\n" +
            "\n" +
            "<header style=\"  height: 195px;\n" +
            "  position:relative;  \n" +
            "  height:12.1em;\n" +
            "  width 100%;\"><img src=\"cid:image\" width=\"799\"></header>\n" +
            "<section>\n" +
            "  \n" +
            "  <article style='float: left;width: 100%;padding-left: 20px;padding-right: 20px;font-family: Verdana;'>\n" +
            "    <p style=\"padding-left: 20px;padding-right: 20px\";><strong>Hi " + getName(email) +",</strong><br>\n" +
            "    <br>You have successfully completed your technical induction training program. We are happy to share the detailed report of your performance during the training period. We thank you for your participation in our training program and hope you had a good learning experience.<br>\n" +
            "    <br>Should you need any further information, please do not hesitate to contact us.\n" +
            "	 \n" +
            "    <br><br><strong>Wish you the best for your future!</strong><br></p>\n" +
            "    <img src=\"cid:image2\" width=\"794\";> \n" +
            "    <p style=\"padding-left: 20px;padding-right: 20px\";><br><br>Thanks and Regards,\n" +
            "    <br>Training and Development Team</p><br>\n" +
            "  </article>\n" +
            "</section>\n" +
            "<div style=\"background: rgb(0,102,161);color: #fff;font-family:Verdana; height:1.5em;\">\n" +
            "      <p align=\"center\">For internal circulation only.©" + Calendar.getInstance().get(Calendar.YEAR)  +"</p>\n" +
            "  </div> \n" +

            "\n" +
            "</body>\n" +
            "</html>";
            messageBodyPart.setContent(htmlText, "text/html;charset=iso-8859-1");
            // add it
            multipart.addBodyPart(messageBodyPart);
            //add header image
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(
            "header.jpg");

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource fds2 = new FileDataSource("welcome.jpg");
            messageBodyPart.setDataHandler(new DataHandler(fds2));
            messageBodyPart.setHeader("Content-ID", "<image2>");
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
    
        public static void sendUserNamePassword(String email, String pswd) {
    	
        String host="smtp.gmail.com";
        final String userName="jmcgregtemp@gmail.com";
        final String pass="mcgregor1"; 
        String to=email;

        //imported code
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", userName);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName,pass);
                    }
                });
        
        //imported code
        try {
            
           MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Atos Syntel - Performica Account Created");
    
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            messageBodyPart = new MimeBodyPart();
            String htmlText = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "<title>Performica</title>\n" +
            "<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<style>\n" +
            "* {\n" +
            "  box-sizing: border-box;\n" +
            "}\n" +
            "\n" +
            "/* Clear floats after the columns */\n" +
            "section:after {\n" +
            "  content: \"\";\n" +
            "  display: table;\n" +
            "  clear: both;\n" +
            "}\n" +
            "/* Responsive layout - makes the two columns/boxes stack on top of each other instead of next to each other, on small screens */\n" +
            "@media (max-width: 600px) {\n" +
            "  nav, article {\n" +
            "    width: 100%;\n" +
            "    height: auto;\n" +
            "  }\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body style=\"  font-family: Verdana, sans-serif;\n" +
            "  width: 800px;\n" +
            "  border: solid;\n" +
            "  margin: 0px 0px 0px 150px;\n" +
            "  padding-left: 0px;\">\n" +
            "\n" +
            "<header style=\"  height: 195px;\n" +
            "  position:relative;  \n" +
            "  height:12.1em;\n" +
            "  width 100%;\"><img src=\"cid:image\" width=\"794\"></header>\n" +
            "<section>\n" +
            "  \n" +
            "  <article style='padding-left: 20px;padding-right: 20px;font-family: Verdana;'>\n" +
            "    <p style=\"padding-left: 20px;padding-right:20px;\"><strong>Welcome to Atos Syntel!</strong><br>\n" +
            "    <br>Your Performica account has successfully been created! Sign in detials are below:<br>\n" +
            "    <br><strong>Email/Username: " + email + "<br>\n" +
            "    Password: " + pswd + "</strong><br>\n" +
            "\n" +
            "    <br>Once you have loggeed in, you will be able to change your default password.\n" +
            "\n" +
            "    <br><br>Thanks and Regards,\n" +
            "    <br>Training and Development Team</p>\n" +
            "  </article>\n" +
            "</section>\n" +
            "  <div style=\"background: rgb(0,102,161);color: #fff;font-family:Verdana; height:1.5em;\">\n" +
            "      <p align=\"center\">For internal circulation only.© 2019</p>\n" +
            "  </div> \n" +
            "</body>\n" +
            "</html>";
            messageBodyPart.setContent(htmlText, "text/html;charset=iso-8859-1");
            // add it
            multipart.addBodyPart(messageBodyPart);
            //add header image
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(
            "header.jpg");

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
            multipart.addBodyPart(messageBodyPart);
            
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
            String empid = emp[0];
            if(empid == null)
                return "";
            else
                return empid;
                
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
            String name = emp[1];
            if(name == null)
                return "";
            else
                return name;
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
            String RM = emp[3];
            if(RM==null)
                return "";
            else
                return RM;
	}
		
	catch (Exception ex) 
	{
            System.out.println(ex);
	}
    	
    	return "";
    }
    
    static String getStreamName(String email) {//getEmployee returns an array of attributes
    	
    	try
	{
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Type 4 Driver Pure Java Driver
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","Student_Performance","Student_Performance");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select s.stream_name from employees e ,class c ,stream s where e.email = '" + email + "' AND e.class_id=c.class_id AND c.stream_id = s.stream_id");
            
            while(rs.next()){
                if( rs.getString(1) == null)
                    return "";
                else
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