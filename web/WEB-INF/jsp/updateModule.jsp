<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.*" %>

<%  
    
  //initialize driver class
  try {    
    Class.forName("oracle.jdbc.driver.OracleDriver");
  } catch (Exception e) {
    out.println("Fail to initialize Oracle JDBC driver: " + e.toString() + "<P>");
  }
  
  String dbUser = "Student_Performance";
  String dbPasswd = "Student_Performance";
  String dbURL = "jdbc:oracle:thin:@localhost:1521:XE";
  //connect
  Connection conn = null;
  try {
    conn = DriverManager.getConnection(dbURL,dbUser,dbPasswd);
    //out.println(" Connection status: " + conn + "<P>");
  } catch(Exception e) {
    out.println("Connection failed: " + e.toString() + "<P>");      
  }
  String sql;
  int numRowsAffected;
  Statement stmt = conn.createStatement();
  ResultSet rs;
  
  String moduleName = request.getParameter("modName");
  String modCategory = request.getParameter("cat"); 
  String streamName= request.getParameter("streamName");
  int id = Integer.parseInt(request.getParameter("moduleId"));
  String streamId="";
  String modCategoryId = " ";
  
  //insert
  
  String sql2;
  sql2 = "select module_name from modules where module_name='" + moduleName + "' and module_id!=" + 
          id;
  rs = stmt.executeQuery(sql2);
 
  if(rs.next() == false)
  {
   try {
            sql = "Select Category_Id from Category where Category_name = '" + modCategory + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                modCategoryId = rs.getString("Category_Id");
            }
            
           sql = "select stream_id from stream where stream_name = '" + streamName + "'";
           rs = stmt.executeQuery(sql);
           while (rs.next()) {
               streamId = rs.getString("stream_id"); 
           } // End while 
           
           
       } catch (SQLException e) {
           out.println("Error encountered during row select for users: " + e.toString() + "<BR>");
       }
   
        try{sql = ("Update Modules set module_name= '" + moduleName + "', category_ID= '" + modCategoryId + "', stream_id= '" + streamId + "' WHERE Module_Id=" + id);
           numRowsAffected = stmt.executeUpdate(sql);
           
        } catch (SQLException e) {
           out.println("Error encountered during row update for users: " + e.toString() + "<BR>");
       }
  }
  
  stmt.close();
  conn.commit();
  
  //disconnect
  conn.close();
  String site= "modules.htm";
  response.setStatus(response.SC_MOVED_TEMPORARILY);
  response.setHeader("Location",site);
  
%>    
