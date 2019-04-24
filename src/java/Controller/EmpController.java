/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import POJO.EmployeeServiceDAO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author syntel
 */
public class EmpController extends SimpleFormController {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
                                HttpServletResponse response){       
        
        int lengthOfExt = 4; // to account for .htm, .jsp at the end of the path
        String servletPath = request.getServletPath();
        String viewName = servletPath.substring(1, (servletPath.length() - lengthOfExt));
        
        //EmployeeServiceDAO
        ServletContext context = this.getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        EmployeeServiceDAO esd = (EmployeeServiceDAO) ctx.getBean("empdb");
        
        //Go to right ModelAndView
        switch (viewName) {
            case "searchEmployees":
                return searchEmployees(viewName, esd, request);
            case "editEmployees":
                return editEmployee(viewName, esd, request);
            case "deleteEmployees":
                return deleteEmployee(viewName, esd, request);
            default:
                break;
        }
 
        return showEmployees(viewName, esd);
    }
    
    public ModelAndView searchEmployees(String viewName, EmployeeServiceDAO esd, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("showEmployees");       
        mav.addObject("emplist",  esd.readAllEmployeeFromCol(request.getParameter("col"),
                request.getParameter("search").trim()));
        
        return mav;
    }
    
    public ModelAndView showEmployees(String viewName, EmployeeServiceDAO esd){
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        mav.addObject("emplist", esd.readAllEmployee());
        
        return mav;
    }
    
    public ModelAndView editEmployee(String viewName, EmployeeServiceDAO esd, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("showEmployees");
        
        esd.updateEmployee(request.getParameter("editModalButton"), 
                request.getParameter("editName").trim(), request.getParameter("editEmail").trim()
                ,request.getParameter("editManagerID").trim());
        
        esd.updateEmployeeModule(request.getParameter("editModuleScore"), 
               request.getParameter("editModalButton"), request.getParameter("selectModule"),
               request.getParameter("editModuleID"));
        
        mav.addObject("emplist", esd.readAllEmployee());
       
        return mav;
    }
    
    public ModelAndView deleteEmployee(String viewName, EmployeeServiceDAO esd, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("showEmployees");
        esd.deleteEmployee(request.getParameter("deleteModalButton"));
        mav.addObject("emplist", esd.readAllEmployee());
        
        return mav;
    }                      
}
