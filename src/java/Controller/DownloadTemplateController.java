package Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;



public class DownloadTemplateController implements Controller {
  
    private String createFilename(String streamID) {
        String fileTag = "-Template";
        String ext = ".xlsx";
        
        return (streamID + fileTag + ext);
    }
    
    private String buildFullPath(String streamID) {
        // DANGER: THIS NEEDS TO CHANGE DEPENDING ON THE MACHINE / SERVER
        String directoryPath = "C:/Users/syntel/Downloads/WebSproject-master/WebSproject-master/";

        StringBuilder pathBuilder = new StringBuilder(directoryPath); // file starts with stream ID
        
        String filename = createFilename(streamID);
        pathBuilder.append(filename);
    
        return pathBuilder.toString();
    }
   
    
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
        
        ServletContext templateContext = request.getServletContext();
        WebApplicationContext appContext = 
                WebApplicationContextUtils.getRequiredWebApplicationContext(templateContext);

        // the stream ID used to retrieve its template file
        String streamID = request.getParameter("streamID");
        ModuleServiceDAO moduleDAO = (ModuleServiceDAO) appContext.getBean("moduleDAO");
        
        ArrayList<String> moduleNames = moduleDAO.getModuleNamesForStreamID(streamID);
        
        String directoryPath = "C:/Users/syntel/Downloads/WebSproject-master/WebSproject-master/";
        String fullPath = buildFullPath(streamID);
        String filename = createFilename(streamID);
        
        // TODO: validate that the stream exists
        
        // generate a file (regardless so we don't have to check a 'last modified' value
        ExcelWriter excelWriter = new ExcelWriter(directoryPath);
        excelWriter.createExcelTemplateFile(streamID, moduleNames);
        
        
        
        
        
        
                
        PrintWriter out = response.getWriter(); // produces outstream to the network response
        
        // NOTE: relative path doesn't work here, this is machine specific
        // we could try a resources folder set in web.xml


        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\""
                        + filename + "\"");

        FileInputStream fileInputStream = new FileInputStream(fullPath);

        int i;
        while ((i = fileInputStream.read()) != -1) {
                out.write(i);
        }
        fileInputStream.close();
        out.close();
        
        
        // return to the Login page to test redirecting to a view works
        //        return new ModelAndView("Login","user",null);
        return new ModelAndView("Login");
    }
    // some code from: https://www.guru99.com/jsp-file-upload-download.html
    
//    @Override
//    public ModelAndView handleRequest(HttpServletRequest request,
//                                     HttpServletResponse response) {
//        
//        ServletContext templateContext = request.getServletContext();
//        WebApplicationContext appContext = 
//                WebApplicationContextUtils.getRequiredWebApplicationContext(templateContext);
//  
//        // get module names for a specific stream
//        ModuleServiceDAO moduleDAO = (ModuleServiceDAO) appContext.getBean("moduleDAO");
//        ArrayList<String> moduleNames = moduleDAO.getModuleNamesForStreamID("FSD123");
//
//        // get all streams
//        StreamServiceDAO streamDAO = (StreamServiceDAO) appContext.getBean("streamDAO");
//        ArrayList<Stream> allStreams = streamDAO.getAllStreams();
//        
//        // send information to DownloadTemplate view
//        ModelAndView modelAndView = new ModelAndView("DownloadTemplate");
//        modelAndView.addObject("moduleNames", moduleNames); // module names
//        modelAndView.addObject("allStreams", allStreams);
//        
//        return modelAndView;
//    }
}
