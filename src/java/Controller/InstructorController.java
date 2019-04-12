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

/**
 *
 * @author syntel
 */
public class InstructorController extends SimpleControllerHandlerAdapter{
    
    @Override
    public ModelAndView handle(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler) {
        return new ModelAndView("instructor");
    }
    
}
