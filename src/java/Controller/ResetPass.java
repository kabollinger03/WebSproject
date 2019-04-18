/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import POJO.ResetPassForm;
import POJO.User;
import POJO.UserServiceDAO;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class ResetPass extends SimpleFormController{
    
    public ResetPass(){
        setCommandClass(POJO.ResetPassForm.class);
        setCommandName("forgotPass");
    }
    
    public void SendResetEmail(String email, String pass_message) {
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
            message.setSubject("Password reset - Attos Syntel");


            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("Your password has been reset, the new password is:" + pass_message);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);
            Transport tr = session.getTransport("smtp");
            tr.send(message);

            System.out.println("message sent!");

        }
        catch (MessagingException mex)
        {
            System.out.println("********RESETPASS.JAVA ********\n"
                    + "Error: unable to send message....");
            System.out.println(mex.toString());
            
        }
        
    }
    
    @Override
    protected ModelAndView onSubmit(Object command) throws Exception{
        ResetPassForm userEmail = (ResetPassForm) command;
        
        System.out.println("ResetPass: " + userEmail);
        
        ServletContext context = this.getServletContext();
        WebApplicationContext ctx;
        ctx =  WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        UserServiceDAO usrDAO = (UserServiceDAO)ctx.getBean("user1");
        
        User match;
        
        try {
            //try to find username in database
            match = usrDAO.getUser(userEmail.getEmail());
            //create random password
            PasswordGenerator passwordGenerator = new PasswordGenerator();
            String newPass = passwordGenerator.generatePassword();
            //set the new password in the database
            usrDAO.setUserPassword(userEmail.getEmail(), newPass);
            //send email to user with the new password
            SendResetEmail(userEmail.getEmail(), newPass);
            System.out.println("Password Changed");
            
        }catch(Exception e){
            System.out.println("********EXCEPTION IN RESETPASS.JAVA**********");
            System.out.println("Invalid Email");
            System.out.println(e);
            return new ModelAndView("Login");
        }        
        return new ModelAndView("Login");
    }
}
