/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import POJO.CheckboxForm;
import POJO.UserServiceDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.BindException;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author LS5028230
 */
public class AuthEmails extends SimpleFormController{
    HttpServletResponse r;
    public AuthEmails() {
        setCommandClass(CheckboxForm.class);
        setCommandName("email");
      
         
      
    }
   
    @Override
    protected ModelAndView onSubmit(Object command) throws Exception {
        System.out.println("INSIDE AUTHEMAILS");
       CheckboxForm user=(CheckboxForm)command;
       String [] userNames = user.getUserNames();
       for(String x :userNames)
           System.out.println(x);
       createPDFs(user.getUserNames());
       
       System.out.println("*****************" + user.isPreview()+ "********************");
       if(!user.isPreview()){
        for(int i = 0; i<userNames.length;++i){
            SendEmail.sendIdividualEmail(userNames[i], SendEmail.getEmpId(userNames[i]));
        }
       }
      
       
           
        return new ModelAndView("pdfviewer");
    }
    
    void createPDFs(String[] ids){
        try{
            ServletContext context = this.getServletContext();
            WebApplicationContext ctx;
            ctx =  WebApplicationContextUtils.getRequiredWebApplicationContext(context);
            UserServiceDAO usrDAO = (UserServiceDAO)ctx.getBean("user1");

           
            
            PDF temp = new PDF(usrDAO.getDataSource());
            //temp.setDataSource(usrDAO.getDataSource());

            for(int i=0; i<ids.length; ++i)
                temp.generate(SendEmail.getEmpId(ids[i]));            
      
        }catch(Exception ex){
            System.out.println("invalid error in create PDFs");
            System.out.println(ex);
        }
        
    }

      
}
