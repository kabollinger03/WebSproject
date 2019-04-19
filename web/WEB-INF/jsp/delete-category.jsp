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
  String category_name = request.getParameter("category_name");
  
 
  // delete
  try {
    sql = "delete from category where category_name = '" + category_name + "'";
    numRowsAffected = stmt.executeUpdate(sql);
  } catch (SQLException e) {
    out.println("Error encountered during deletion of employee: " + e.toString() + "<BR>");
  }  

  
  //rs.close();
  stmt.close();

  //commit
  conn.commit();
  
  //disconnect
  conn.close();
  
  String site = "category.htm";
  response.setStatus(response.SC_MOVED_TEMPORARILY);
  response.setHeader("Location", site);

  
%>
