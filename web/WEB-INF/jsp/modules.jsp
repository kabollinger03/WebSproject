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
  
  
  // select
  sql = "select module_name from modules";
  rs = stmt.executeQuery(sql);
  
  ArrayList moduleList = new ArrayList();
  request.setAttribute("moduleList", moduleList);
  
  ArrayList cleanedModuleList = new ArrayList();
  request.setAttribute("cleanedModuleList", cleanedModuleList);
  String cleanedString;
  while (rs.next()) {
        moduleList.add(rs.getString("module_name"));
        cleanedString = rs.getString("module_name");
        cleanedString = cleanedString.trim();
        //replacing + with %2B
        cleanedString = cleanedString.replaceAll("\\+", "%2B");
        //replacing spaces with %20
        cleanedString = cleanedString.replaceAll("\\s", "+");
        //replacing # with %23
        cleanedString = cleanedString.replaceAll("\\#", "%23");
        
        cleanedModuleList.add(cleanedString);  
        } // End while 
  

   
    
    // select
  sql = "select module_id from modules";
  rs = stmt.executeQuery(sql);
    
 ArrayList moduleIdList = new ArrayList();
  request.setAttribute("moduleIdList", moduleIdList);
  
  while (rs.next()) {
        moduleIdList.add(rs.getString("module_id"));
        //out.println("User Id = " + rs.getString("user_id") + "<BR>"); 
        } // End while 
  

   
    
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
  
  // select
  sql = "select c.category_NAME, m.Category_Id from category c, modules m where m.category_id = c.category_id";
  rs = stmt.executeQuery(sql);
  
  ArrayList catagoryName = new ArrayList();
  request.setAttribute("catagoryName",catagoryName);
  
 while (rs.next()) {
        catagoryName.add(rs.getString("Category_Name"));
        //out.println("User Id = " + rs.getString("user_id") + "<BR>"); 
        } // End while 
  
  
  sql = "select s.stream_name, m.module_name from stream s, modules m where m.stream_id = s.stream_id";
  rs = stmt.executeQuery(sql);
  ArrayList streamNameList = new ArrayList();
  request.setAttribute("streamNameList", streamNameList);
  
  ArrayList cleanedStreamNameList = new ArrayList();
  request.setAttribute("cleanedStreamNameList", cleanedStreamNameList);
  
  String cleanedString2;
  while (rs.next()) {
        streamNameList.add(rs.getString("stream_name"));
        
        cleanedString2 = rs.getString("stream_name");
        cleanedString2 = cleanedString2.trim();
        //replacing + with %2B
        cleanedString2 = cleanedString2.replaceAll("\\+", "%2B");
        //replacing spaces with %20
        cleanedString2 = cleanedString2.replaceAll("\\s", "+");
        //replacing # with %23
        cleanedString2 = cleanedString2.replaceAll("\\#", "%23");
        
        cleanedStreamNameList.add(cleanedString2);  
        } // End while 
  

    
    
    // select
  sql = "select stream_Name from stream";
  rs = stmt.executeQuery(sql);
  
  
  
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
          <li class="breadcrumb-item active" aria-current="page">Modules</li>
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
          <a class="nav-link active" href="modules.htm">Modules</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="courses.htm">Courses</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="admin.htm">Users</a>
        </li>
      </ul>
    </div>
  </div>

  <div class="container-fluid bg-white" style="min-height: 100vh;">
    <div class="container pb-5">

      <div class="row py-3">
        <div class="col-lg-12">
            <form action="createModule.htm">
          <div class="form">
            <div class="form-row">
              <div class="col-2">
                  <button class="btn btn-sm btn-primary mt-1 no-border" type="submit"><i class="fas fa-plus pr-2"></i>Insert Module</button>
              </div>
              <div class="col-4">
                <input type="text" class="form-control" id="modName" onchange="myFunction()" name="modName" placeholder="Module Name" pattern="[a-zA-Z][a-zA-Z0-9-_.+#* ]{2,45}" title="Name must start with a letter and can only contain letters, numbers, hyphens, underscores, periods, hashtag, plus, star and be between 2 and 45 characters." required>
              <div><small id="jackson_1" class="text-danger"></small></div>
              </div>
              <div class="col-3">
                <select id="inputState" class="form-control" required="" name ="modCategory">
                        <c:forEach items="${allCategoryName}" var="catNam">
                            <option value="${catNam}">
                                ${catNam}
                            </option>
                        </c:forEach>
                    </select>
              </div>
                <div class="col-3">
                    <select id="inputState" class="form-control" required="" name ="streamName">
                        <c:forEach items="${streamName}" var="stream">
                            <option value="${stream}">
                                ${stream}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
          </div>
            </form>
        </div>
      </div>

      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th scope="col" style="width: 10%;">Module ID</th>
            <th scope="col">Name</th>
            <th scope="col">Category</th>
            <th scope="col">Stream Name</th>
          </tr>
        </thead>
        <tbody>
            <c:set var="count" value="1"/>
            <c:forEach items="${moduleList}" var="module">
            <tr value="${module}">
                <th scope="row"><a href="manage-module.htm?id=${moduleIdList.get(count-1)}&name=${catagoryName.get(count-1)}&stream=${cleanedStreamNameList.get(count-1)}&okay=${cleanedModuleList.get(count-1)}">${moduleIdList.get(count-1)}</a></th>
                <td>
                   ${module}
                </td>
                <td>
                    ${catagoryName.get(count-1)}
                </td>
                <td>
                    ${streamNameList.get(count-1)}
                </td>
            </tr>
            <c:set var="count" value="${count+1}"/>
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

<script>
function myFunction()
{
  var modName = document.getElementById("modName").value;
  
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("jackson_1").innerHTML = this.responseText;
    }
  };
  
  xhttp.open("GET", "jackson_1.htm?name="+modName+"&num=6", true);
  xhttp.send();
}
</script>