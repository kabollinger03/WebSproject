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
    //out.println(" Connection status: " + conn + "<P>");
  } catch(Exception e) {
    out.println("Connection failed: " + e.toString() + "<P>");      
  }

String sql;
int numRowsAffected;
Statement stmt = conn.createStatement();
ResultSet rs;
String user_id = request.getParameter("id");

sql = "select  user_id from users where user_id='" + user_id + "'";
rs = stmt.executeQuery(sql);

boolean userCantBeUpdated = rs.next();

%>

<body class="bg-light">

  <jsp:include page="nav.jsp"/>
  <div class="container-fluid">
    <div class="container mt-2 pt-4 pb-3">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb" style="background: transparent;">
          <li class="breadcrumb-item"><a href="admin.htm">Admin</a></li>
          <li class="breadcrumb-item active">Users</li>
          <li class="breadcrumb-item active" aria-current="page">Manage User</li>
        </ol>
      </nav>
    </div>
  </div>

  <!-- Tabs -->
  <div class="container-fluid">
    <div class="container">
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a class="nav-link" href="streams.htm">Streams</a>
        </li>  
        <li class="nav-item">
          <a class="nav-link" href="category.htm">Category</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="modules.htm">Modules</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="courses.htm">Courses</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="admin.htm">Users</a>
        </li>
      </ul>
    </div>
  </div>

  <div class="container-fluid bg-white" style="min-height: 100vh;">
    <div class="container pb-5">
      <div class="row justify-content-center">
        <div class="card col-md-6 col-sm-12 col-lg-5 mt-4 py-3 shadow">
          <div class="card-header text-muted noto bg-white">
            <i class="fas fa-user pr-2"></i> Edit User
            <span style="float: right;">
              <form action="delete-user.htm">
                  <div class="col-sm-9">
                <input type="hidden" class="form-control" id="inputEmail4" name="oldusername" value="${param.id}">
                </div>
                <button type="submit" class="btn btn-sm btn-danger">
                  <span style="white-space: nowrap;"><i class="fas fa-user-minus"></i> Delete </span>
                </button>
              </form>
            </span>
          </div>
          <form action="update-user.htm">
            <div class="form-group row mt-3">
              <label for="inputEmail3" class="col-sm-3 col-form-label">Email</label>
              <div class="col-sm-9">
                <input type="email" class="form-control" id="inputEmail3" onchange="myFunction()" name="newusername" value="${param.id}" required>
              <div><small id="jackson_1" class="text-danger"></small></div>
              </div>
              <div class="col-sm-9">
                <input type="hidden" class="form-control" id="inputEmail4" name="oldusername" value="${param.id}" required>
              </div>
            </div>

            <div class="form-group row mt-1">
              <label for="inputEmail3" class="col-sm-4 col-form-label">User Level</label>
              <div class="col-sm-8 pt-2">
                  <div class="custom-control custom-radio custom-control-inline">
                      <input type="radio" class="custom-control-input" id="customRadio" name="example" value="N" checked>
                      <label class="custom-control-label noto text-muted" for="customRadio"><small>Instructor</small></label>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                      <input type="radio" class="custom-control-input" id="customRadio2" name="example" value="Y">
                      <label class="custom-control-label noto text-muted" for="customRadio2"><small>Admin</small></label>
                    </div> 
              </div>
            </div>
            <div class="row justify-content-center mt-1">
              <div class="row pt-3">
                <div class="col-6">
                    <button class="btn btn-sm ghost" type="submit">
                      <span style="white-space: nowrap;"><i class="fas fa-user-edit"></i> Update </span>
                    </button>
                </div>
              </div>
            </div>
          </form>
          </div>
        </div>
      </div>
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

<script>
function myFunction()
{
  var oName = document.getElementById("inputEmail4").value;
  var nName = document.getElementById("inputEmail3").value;
  
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("jackson_1").innerHTML = this.responseText;
    }
  };
  
  xhttp.open("GET", "jackson_1.htm?oldUn="+encodeURIComponent(oName)+"&newUn="+encodeURIComponent(nName)+"&num=5", true);
  xhttp.send();
}
</script>