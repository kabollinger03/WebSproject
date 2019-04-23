/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
/**
 *
 * @author syntel
 */
public class PdfViewController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
                                HttpServletResponse response){
        
        ServletContext context = request.getServletContext();
        WebApplicationContext ctx;
        ctx =  WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        
        UserServiceDAO usrDAO = (UserServiceDAO)ctx.getBean("user1");
        
        String employeeID = request.getParameter("empID");
        
        try {
            PDF pdfGenerator = new PDF(usrDAO.getDataSource());
            pdfGenerator.generate(employeeID);
        } catch (Exception ex) {
            System.err.println("There was an issue with creating the PDF generator");
        }
        PDF.id = employeeID;
        return new ModelAndView("pdfviewer", "employeeID", employeeID);
    } 
}
