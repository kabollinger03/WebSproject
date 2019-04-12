/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import POJO.CheckboxForm;
import POJO.UserServiceDAO;
import javax.servlet.ServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author LS5028230
 */
public class AuthEmails extends SimpleFormController{

    public AuthEmails() {
        setCommandClass(CheckboxForm.class);
        setCommandName("email");
    }

    @Override
    protected ModelAndView onSubmit(Object command) throws Exception {
       CheckboxForm user=(CheckboxForm)command;
       
       String [] userNames = user.getUserNames();
    
       createPDFs(user.getUserNames());

        for(int i = 0; i<userNames.length;++i){
            SendEmail.sendIdividualEmail(userNames[i], SendEmail.getEmpId(userNames[i]));
        }
       //SendEmail.sendIdividualEmail("phillipphoong@gmail.com", "mn");
       
           
        return new ModelAndView("instructor");
    }
    
    void createPDFs(String[] ids){
        try{
            ServletContext context = this.getServletContext();
            WebApplicationContext ctx;
            ctx =  WebApplicationContextUtils.getRequiredWebApplicationContext(context);
            UserServiceDAO usrDAO = (UserServiceDAO)ctx.getBean("user1");

            PDF temp = new PDF();
            temp.setDataSource(usrDAO.getDataSource());
            for(int i=0; i<ids.length; ++i)
                temp.generate(SendEmail.getEmpId(ids[i]));            
            
        }catch(Exception ex){
            System.out.println("invalid");
            System.out.println(ex);
        }
        
    }

      
}
