/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import POJO.User;
import POJO.UserServiceDAO;
import java.io.Console;
import static java.lang.System.console;
import javax.servlet.ServletContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author LS5028230
 */
public class Authentication extends SimpleFormController{

    public Authentication() {
        setCommandClass(User.class);
        setCommandName("user");
    }

    @Override
    protected ModelAndView onSubmit(Object command) throws Exception {
       User user=(User)command;
       ServletContext context = this.getServletContext();
       WebApplicationContext ctx;
       ctx =  WebApplicationContextUtils.getRequiredWebApplicationContext(context);
       UserServiceDAO usrDAO = (UserServiceDAO)ctx.getBean("user1");
        //System.out.println(user.getPassword());
        
        System.out.println("User Form: " + user);
        
        User userMatcher;
        
        try {
            userMatcher = usrDAO.getUser(user.getUserName());
            if (userMatcher.getPassword().trim().equals(user.getPassword().trim())) {
                if (userMatcher.isAdmin()) {
                return new ModelAndView("admin", "user", user);
                }
                else
                    return new ModelAndView("instructor");
            }
            else {
                return new ModelAndView("Login");
            }
            
            
            //return new ModelAndView("Success","user",user);
        }catch(Exception e){
            return new ModelAndView("Login");
            //System.out.println(e);
            //System.out.println("Invalid username or password");
        }
    }
      
}
