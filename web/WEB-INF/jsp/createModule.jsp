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
  int id = 1;
  String stream = "";
  String name = request.getParameter("modName");
  String modCategory = request.getParameter("modCategory"); 
  String streamName= request.getParameter("streamName");
  String modCategoryId= " ";
  
  String sql2;
  
  sql2 = "select module_name from modules where module_name='" + name + "'";
  
  rs = stmt.executeQuery(sql2);
  
  if(rs.next() == false)
  {
  //insert
   try {
       sql = "Select Category_Id from Category where Category_name = '" + modCategory + "'";
       rs=stmt.executeQuery(sql);
       if(rs.next())
       {
           modCategoryId= rs.getString(1);
       }
       sql = "Select Stream_Id from Stream where stream_name = '" + streamName + "'";
       rs=stmt.executeQuery(sql);
       if(rs.next())
       {
           stream= rs.getString(1);
       }
       String maxIDSql = "SELECT MAX(module_id) FROM modules";
			rs = stmt.executeQuery(maxIDSql);
			if(rs.next()) // the max ID was retrieved
			{
                id = (rs.getInt(1) + 1); // increment the max ID to create a new one
			}
        sql = ("INSERT INTO modules VALUES("+id+",'"+name+"', '" +modCategoryId+"', '"+stream+"')");
        numRowsAffected = stmt.executeUpdate(sql);
  
        } catch (SQLException e) {
        out.println("Error encountered during row insertion for users: " + e.toString() + "<BR>");
        }
  }
  //out.println(stream);
  
  
  //rs.close();
  stmt.close();
  //commit
  conn.commit();
  
  //disconnect
  conn.close();
  String site= "modules.htm";
  response.setStatus(response.SC_MOVED_TEMPORARILY);
  response.setHeader("Location",site);
  
%>  
