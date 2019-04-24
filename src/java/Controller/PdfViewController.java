
package Controller;

import POJO.UserServiceDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;


public class PdfViewController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
                                HttpServletResponse response){
        
        ServletContext context = request.getServletContext();
        WebApplicationContext ctx = 
            WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        
        UserServiceDAO userDAO = (UserServiceDAO)ctx.getBean("user1");
        
        String employeeID = request.getParameter("empID");
        
        try {
            PDF pdfGenerator = new PDF(userDAO.getDataSource());
            pdfGenerator.generate(employeeID);
        } catch (Exception ex) {
            System.err.println("PdfViewController: There was an issue with generating the PDF");
            ex.printStackTrace();
        }
                
        RedirectView emailRedirect = new RedirectView("email.htm"); // go back to the same email page        
        ModelAndView emailHub = new ModelAndView(emailRedirect);
        
        emailHub.addObject("pdfSaved", "true");
        
        return emailHub;
    } 
}