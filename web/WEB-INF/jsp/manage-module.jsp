
<jsp:include page="head-tag.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.*" %>


<%
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
// select
  sql = "select stream_Name from stream";
  rs = stmt.executeQuery(sql);
    
 ArrayList streamName = new ArrayList();
  request.setAttribute("streamName", streamName);
  
  while (rs.next()) {
        streamName.add(rs.getString("stream_Name"));
        //out.println("User Id = " + rs.getString("user_id") + "<BR>"); 
        } // End while 
  sql = "select Category_Name from Category";
  rs = stmt.executeQuery(sql);
  
  ArrayList allCategoryName = new ArrayList();
  request.setAttribute("allCategoryName",allCategoryName);
  
 while (rs.next()) {
        allCategoryName.add(rs.getString("Category_Name"));
        //out.println("User Id = " + rs.getString("user_id") + "<BR>"); 
        } // End while 
 
 
%>



<jsp:include page="head-tag.jsp"/>


<body class="bg-light">

  <jsp:include page="nav.jsp"/>
  <div class="container-fluid">
    <div class="container mt-2 pt-4 pb-3">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb" style="background: transparent;">
          <li class="breadcrumb-item"><a href="admin.htm">Admin</a></li>
          <li class="breadcrumb-item active">Modules</li>
          <li class="breadcrumb-item active" aria-current="page">Manage Module</li>
        </ol>
      </nav>
    </div>
  </div>

  <!-- Tabs -->
  <div class="container-fluid">
    <div class="container">
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a class="nav-link" href="admin.htm">Users</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="streams.htm">Streams</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="courses.htm">Courses</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="modules.htm">Modules</a>
        </li>
      </ul>
    </div>
  </div>

  <div class="container-fluid bg-white" style="min-height: 100vh;">
    <div class="container pb-5">

      <div class="row justify-content-center">
        <div class="card col-md-6 col-sm-12 col-lg-5 mt-4 py-3 shadow">
          <div class="card-header text-muted noto bg-white">
            <i class="fas fa-layer-group pr-2"></i> Manage Module 
            <span style="float: right;">
              <form action="deleteModule.htm">
                  <input type="hidden" name='moduleId' id ="moduleId" value='${param.id}' />
                  <button class="btn btn-sm btn-danger" type ="submit">
                <!--a href="" class="btn btn-sm btn-danger"-->
                  <span style="white-space: nowrap;"><i class="fas fa-user-minus"></i> Delete </span>
                <!--/a-->
                  </button>
              </form>
            </span>
          </div>
          <form action="updateModule.htm">
              <input type="hidden" name='moduleId' id ="moduleId" value='${param.id}' />
            <div class="form-group row mt-3">
              <label for="new_module" class="col-sm-3 col-form-label">Module</label>
              <div class="col-sm-9">
                <input type="text" class="form-control" id="modName" name ="modName" placeholder="" value="${param.okay}" required>
              </div>
            </div>
              
              <div class="form-group row mt-3">
              <label for="new_module" class="col-sm-3 col-form-label">Category</label>
              <div class="col-sm-9">
                  <select id="inputState" class="form-control" required="" name ="cat">
                        <option selected hidden value="${param.name}">${param.name}</option>
                        <c:forEach items="${allCategoryName}" var="catNam">
                            <option value="${catNam}">
                                ${catNam}
                            </option>
                        </c:forEach>
                    </select>
              </div>
            </div>

            <div class="form-group row mt-3">
              <label for="new_stream_id" class="col-sm-3 col-form-label">Stream Name</label>
              <div class="col-sm-9">
                  <select id="inputState" class="form-control" required="" name ="streamName" id ="streamName">
                      <option selected hidden value="${param.stream}">${param.stream}</option>
                      <c:forEach items="${streamName}" var="stream">
                          <option value="${stream}">
                              ${stream}
                          </option>
                      </c:forEach>
                  </select>
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
            </div><!-- /row -->
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
