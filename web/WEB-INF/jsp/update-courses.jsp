<%@page import="com.sun.glass.ui.Window"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
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
    out.println(" Connection status: " + conn + "<P>");
  } catch(Exception e) {
    out.println("Connection failed: " + e.toString() + "<P>");      
  }

  String sql;
  //int numRowsAffected;
  Statement stmt = conn.createStatement();
  
  String course_name_new = request.getParameter("new_course_name");
  String module_name = request.getParameter("modulename");
  String course_name_original = request.getParameter("course_name");
 
  String sql2;
  ResultSet rs3;
  
 sql2 = "select course_name from courses where course_name='" + course_name_new + "' and course_name !='" + 
         course_name_original + "'";
  
  rs3 = stmt.executeQuery(sql2);
 

 if(rs3.next() == false)
 {
  //insert
   try {
    
        sql = "UPDATE courses SET course_name = '"+course_name_new+"', module_id = (SELECT module_id FROM modules WHERE module_name='"+module_name+"') WHERE course_name = '"+course_name_original+"'";
        stmt.executeUpdate(sql);
        //out.println(numRowsAffected + " user(s) inserted. <BR>");
  
        } catch (SQLException e) {
        out.println("Error encountered during update for stream: " + e.toString() + "<BR>");
        }
 }
  //rs.close();
  stmt.close();

  //commit
  conn.commit();
  
  //disconnect
  conn.close();
  
  String site = "courses.htm";
  response.setStatus(response.SC_MOVED_TEMPORARILY);
  response.setHeader("Location", site);
  
%>  
