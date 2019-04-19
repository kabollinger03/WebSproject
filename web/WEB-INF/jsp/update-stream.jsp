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
  //ResultSet rs;
  //LocalDateTime myDate = LocalDateTime.now();
  //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddYY:HHMMss");
  String stream_name_new = request.getParameter("new_stream_name");
  
  String stream_name_original = request.getParameter("stream_name");
 // String stream_id = (stream_name.toUpperCase().replaceAll("\\s+","").substring(0, 3) + dtf.format(myDate));
  //String passWord = request.getParameter("password");
  //String admin = request.getParameter("isadmin");

  ResultSet rs;
  String sql2;
  
    sql2 = "select stream_name from stream where stream_name='" + stream_name_new + "'";
  
  rs = stmt.executeQuery(sql2);
  
  
  //insert
  if(rs.next() == false)
  {
   try {
    
        sql = "UPDATE stream SET stream_name = '"+stream_name_new+"' WHERE stream_name = '"+stream_name_original+"'";
        stmt.executeUpdate(sql);
        //out.println(numRowsAffected + " user(s) inserted. <BR>");
  
        } catch (SQLException e) {
        out.println("Error encountered during update for stream: " + e.toString() + "<BR>");
        }
  }
  
  // select
  /*sql = "select user_id from users";
  rs = stmt.executeQuery(sql);
  
  ArrayList usersList = new ArrayList();
  request.setAttribute("usersList", usersList);
  
  while (rs.next()) {
        usersList.add(rs.getString("user_id"));
        //out.println("User Id = " + rs.getString("user_id") + "<BR>"); 
        } // End while 
  
   out.println("<P>");*/
 
  // delete
  /* try {
    sql = "delete from users";
    numRowsAffected = stmt.executeUpdate(sql);
    out.println(numRowsAffected + " user(s) deleted. <BR>");
  } catch (SQLException e) {

    out.println("Error encountered during deletion of employee: " + e.toString() + "<BR>");
  
  }  

  out.println("<P>"); */
  
  //rs.close();
  stmt.close();

  //commit
  conn.commit();
  
  //disconnect
  conn.close();
  
  String site = "streams.htm";
  response.setStatus(response.SC_MOVED_TEMPORARILY);
  response.setHeader("Location", site);
  
%>  




