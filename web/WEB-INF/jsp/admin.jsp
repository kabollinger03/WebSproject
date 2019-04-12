
<jsp:include page="head-tag.jsp"/>

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
   
  } catch(Exception e) {
    out.println("Connection failed: " + e.toString() + "<P>");      
  }
  String sql;
  int numRowsAffected;
  Statement stmt = conn.createStatement();
  ResultSet rs;
  
  // insert
  /*try {
    
    sql = "insert into users values ('chris@syntelinc.com', 'password', 'N')";
    numRowsAffected = stmt.executeUpdate(sql);
    out.println(numRowsAffected + " user(s) inserted. <BR>");
  
  } catch (SQLException e) {
    
    out.println("Error encountered during row insertion for employee: " + e.toString() + "<BR>");
  
  }*/
  
  
  // select
  sql = "select user_id, isadmin from users";
  rs = stmt.executeQuery(sql);
  
  ArrayList usersList = new ArrayList();
  request.setAttribute("usersList", usersList);
  
  ArrayList isadminList = new ArrayList();
  request.setAttribute("isadminList", isadminList);
  
 
  
  while (rs.next()) {
        usersList.add(rs.getString("user_id"));
        isadminList.add(rs.getString("isadmin"));
        //out.println("User Id = " + rs.getString("user_id") + "<BR>"); 
        } // End while 
  
   
 
  // delete
  /* try {
    sql = "delete from users";
    numRowsAffected = stmt.executeUpdate(sql);
    out.println(numRowsAffected + " user(s) deleted. <BR>");
  } catch (SQLException e) {
    out.println("Error encountered during deletion of employee: " + e.toString() + "<BR>");
  
  }  
  out.println("<P>"); */
  
  rs.close();
  stmt.close();
  //commit
  conn.commit();
  
  //disconnect
  conn.close();
  
%>  

<body class="bg-light">

  <jsp:include page="nav.jsp"/>
  <div class="container-fluid">
    <div class="container mt-2 pt-4 pb-3">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb" style="background: transparent;">
          <li class="breadcrumb-item"><a href="#">Admin</a></li>
          <li class="breadcrumb-item active" aria-current="page">Users</li>
        </ol>
      </nav>
    </div>
  </div>

  <!-- Tabs -->
  <div class="container-fluid">
    <div class="container">
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a class="nav-link active" href="admin.htm">Users</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="streams.htm">Streams</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="courses.htm">Courses</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="modules.htm">Modules</a>
        </li>
      </ul>
    </div>
  </div>

  <div class="container-fluid bg-white" style="min-height: 100vh;">
    <div class="container pb-5">

      <div class="row py-3">
        <div class="col-lg-12">
            <form action="create-user.htm">
          <div class="form">
            <div class="form-row">
              <div class="col-2">
                <button class="btn btn-small btn-success no-border" type="submit"><i class="fas fa-plus pr-2"></i>Insert User</button>
              </div>
              <div class="col-3">
                <input type="email" class="form-control" id="username" name="username"  placeholder="Email" required>
              </div>
              <div class="col-3">
                <input type="password" class="form-control" id="password" name="password"  placeholder="Password" required>
              </div>
              <div class="col-4">
                <div class="form-group pt-lg-2 pl-lg-2">
                  <div class="custom-control custom-radio custom-control-inline">
                    <input type="radio" class="custom-control-input" id="customRadio" name="example" value="Y" >
                    <label class="custom-control-label" for="customRadio"><small>Admin</small></label>
                  </div>
                  <div class="custom-control custom-radio custom-control-inline">
                    <input type="radio" class="custom-control-input" id="customRadio2" name="example" value="N" checked>
                    <label class="custom-control-label" for="customRadio2"><small>Instructor</small></label>
                  </div> 
                </div>
              </div>
            </div>
          </div>
            </form>
        </div>
      </div>

      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th scope="col" style="width: 10%;">#</th>
            <th scope="col">Email</th>
            <th scope="col">Admin</th>
          </tr>
        </thead>
        <tbody>
            <c:set var="count" value="1"/>
         <c:forEach items="${usersList}" var="user">
           <tr value="${user}">
               <th scope="row">${count}</th>
               <td>
                   <a href="manage-user.htm?id=${user}">${user}</a>
               </td>
               <td>
               ${isadminList.get(count-1)}
               </td>
           </tr>
           <c:set var="count" value="${count + 1}"/>
       </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
  <!-- /Tabs -->

  <!-- Optional JavaScript -->

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <!-- Popper.js -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <!-- Bootstrap.js -->
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</body>
</html>