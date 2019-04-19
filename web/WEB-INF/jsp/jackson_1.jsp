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
  Statement stmt = conn.createStatement();
  ResultSet rs;
  
  int num = Integer.parseInt(request.getParameter("num"));
  
  //Update Module
  if(num == 1)
  {
  String moduleName = request.getParameter("modName");
  int id = Integer.parseInt(request.getParameter("modId"));
  //insert 
    String sql2;
    sql2 = "select module_name from modules where module_name='" + moduleName + "' and module_id!=" + 
            id;
    rs = stmt.executeQuery(sql2);
     if(rs.next())
    {
        out.println("Module already exists and will not update");
    }
  }
  
  //Update Stream
  else if(num == 2)
  {
        String streamName = request.getParameter("streamName");
        //insert 
            String sql2;
          sql2 = "select stream_name from stream where stream_name='" + streamName + "'";
          rs = stmt.executeQuery(sql2);
           if(rs.next())
          {
              out.println("Stream already exists and will not update");
          }
  }
  
  //Update Category
  else if(num == 3)
  {
    String cat_name_new = request.getParameter("newCat");
        String sql2;
    sql2 = "select category_name from category where category_name='" + cat_name_new + "'";
    rs = stmt.executeQuery(sql2);
    if(rs.next())
    {
        out.println("Category already exists and will not update");
    }
  }
  
  //Update Course
  else if(num == 4)
  {
    String course_name_new = request.getParameter("newC");
    String course_name_original = request.getParameter("oldC");
 
    String sql2;
  
    sql2 = "select course_name from courses where course_name='" + course_name_new + "' and course_name !='" + 
         course_name_original + "'";
    
    rs = stmt.executeQuery(sql2);
    
    if(rs.next())
    {
        out.println("Course already exists and will not update");
    }
  }
  
  else if(num == 5)
  {
      String user_name = request.getParameter("oldUn");
    String newUser_name = request.getParameter("newUn");

    String sql2;
      sql2 = "select user_id from users where user_id='" + newUser_name + "' and user_id!='" + user_name + "'";

      rs = stmt.executeQuery(sql2);

      if(rs.next())
      {
        out.println("Email already exists and will not update");
      }
  }
    
//----------------------------------------

  //Insert Module
  else if(num == 6)
  {
      String mName = request.getParameter("name");
      String sql2;
      
      sql2 = "select module_name from modules where module_name='" + mName + "'";
      rs = stmt.executeQuery(sql2);
      if(rs.next())
      {
          out.println("Module already exists and will not insert");
      }
  }
  
  //Insert Stream
  else if(num == 7)
  {
      String sName = request.getParameter("name");
      String sql2;
      
      sql2 = "select stream_name from stream where stream_name='" + sName + "'";
      rs = stmt.executeQuery(sql2);
      if(rs.next())
      {
          out.println("Stream already exists and will not insert");
      }
  }
  
  //Insert Course
  else if(num == 8)
  {
      String cName = request.getParameter("name");
      String sql2;
      
      sql2 = "select course_name from courses where course_name='" + cName + "'";
      rs = stmt.executeQuery(sql2);
      if(rs.next())
      {
          out.println("Course already exists and will not insert");
      }
  }
  
  //Insert Category
  else if(num == 9)
  {
      String cName2 = request.getParameter("name");
      String sql2;
      
      sql2 = "select category_name from category where category_name='" + cName2 + "'";
      rs = stmt.executeQuery(sql2);
      if(rs.next())
      {
          out.println("Category already exists and will not insert");
      }
  }
  
  //Insert User
  else if(num == 10)
  {
      String uName = request.getParameter("name");
      String sql2;
      
      sql2 = "select user_id from users where user_id='" + uName + "'";
      rs = stmt.executeQuery(sql2);
      if(rs.next())
      {
          out.println("Email already exists and will not insert");
      }
  }
  

%>

