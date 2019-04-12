<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
  
  // select
  sql = "select email from employees";
  rs = stmt.executeQuery(sql);
  
  ArrayList usersList = new ArrayList();
  request.setAttribute("usersList", usersList);
  
  while (rs.next()) {
        usersList.add(rs.getString("email"));
        //out.println("User Id = " + rs.getString("user_id") + "<BR>"); 
        } // End while 
  
   out.println("<P>");
  
  rs.close();
  stmt.close();

  //commit
  conn.commit();
  
  //disconnect
  conn.close();
  
%>  

<HTML>
<BODY>
 
    <div class="container"> 
    <s:form commandName="email"> 

  <label for="a1"><b>Choose who you want to email:</b></label>
      <div class="btn-group-toggle" data-toggle="buttons">
          <c:forEach items="${usersList}" var="user">
             <s:checkbox path="userNames" value="${user}" />
             ${user} <br>
        
          </c:forEach>
      </div>

      <button type="submit" value="Login" class="btn btn-danger">Register</button>
      <button type="reset" value="Reset" class="btn btn-danger">Reset</button>
      </s:form>
  </div>


</BODY>
</HTML>