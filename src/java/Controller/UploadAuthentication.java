/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import POJO.ExcelUploadObject;
import ExcelUpload.*;
import static ExcelUpload.Runner.ExcelUpload;
import java.util.ArrayList;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;


public class UploadAuthentication extends SimpleFormController{

    public UploadAuthentication() {
        setCommandClass(ExcelUploadObject.class);
        setCommandName("excel");
    }
    
    

    @Override
    protected ModelAndView onSubmit(Object command) throws Exception {
       
       ExcelUploadObject excel =(ExcelUploadObject)command;
       ExcelUpload(excel.getFile(), excel.getLocation(),excel.getSite(), excel.getStreamName());
        
        return new ModelAndView("instructor");
    }

      
}
