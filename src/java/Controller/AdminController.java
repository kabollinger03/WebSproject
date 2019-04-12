/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import org.springframework.web.servlet.mvc.Controller;
/**
 *
 * @author syntel
 */

public class AdminController implements Controller{
    
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
                                HttpServletResponse response){
        
        int lengthOfExt = 4; // to account for .htm, .jsp at the end of the path
        String servletPath = request.getServletPath();
        String viewName = servletPath.substring(1, (servletPath.length() - lengthOfExt));
        
        return new ModelAndView(viewName);
    }
}
