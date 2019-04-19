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

  Statement stmt = conn.createStatement();
  
  String cat_name_new = request.getParameter("new_category_name");
  
  String cat_name_original = request.getParameter("category_name");
 

  ResultSet rs;
  String sql2;
  
    sql2 = "select category_name from category where category_name='" + cat_name_new + "'";
  
  rs = stmt.executeQuery(sql2);
  
  
  //insert
  if(rs.next() == false)
  {
  //insert
   try {
    
        sql = "UPDATE category SET category_name = '"+ cat_name_new +"' WHERE category_name = '"+ cat_name_original +"'";
        stmt.executeUpdate(sql);
        
  
        } catch (SQLException e) {
        out.println("Error encountered during update for stream: " + e.toString() + "<BR>");
        }
  }
  
  stmt.close();

  //commit
  conn.commit();
  
  //disconnect
  conn.close();
  
  String site = "category.htm";
  response.setStatus(response.SC_MOVED_TEMPORARILY);
  response.setHeader("Location", site);
  
%>  
