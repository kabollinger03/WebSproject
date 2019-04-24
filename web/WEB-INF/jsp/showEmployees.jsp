<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%@page import="java.util.ArrayList"%>
<%@ page import="ExcelUpload.Module" %>
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
  sql = "select email, name from employees";
  rs = stmt.executeQuery(sql);
  
  ArrayList usersList = new ArrayList();
  request.setAttribute("usersList", usersList);
  ArrayList nameList = new ArrayList();
  request.setAttribute("nameList", nameList);
  
  while (rs.next()) {
        usersList.add(rs.getString("email"));
        nameList.add(rs.getString("name"));
        //out.println("User Id = " + rs.getString("user_id") + "<BR>"); 
        } // End while 
  
  
  rs.close();
  stmt.close();

  //commit
  conn.commit();
  
  //disconnect
  conn.close();
  
%>  

<HTML>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="css/styles.css">
        <!-- Animate CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.0/animate.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
        <!-- Google Fonts (Noto Sans) --> 
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans" rel="stylesheet">
        
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>

        <title>Atos Syntel &middot; Land</title>
        <style>
            p, h1, h2, h3, h4, h5, h6, li, ul, ol{
                font-family: Verdana,Geneva,sans-serif;
            }
        </style>
    </head>
<body class="bg-light">
    
    <jsp:include page="nav.jsp"/>
    
    <div class="container-fluid">
        <div class="container mt-2 pt-4 pb-3">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb" style="background: transparent;">
                    <li class="breadcrumb-item"><a href="#">Instructor</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Email Hub</li>
                </ol>
            </nav>
        </div>
    </div>
    
    <!-- Tabs -->
    <div class="container-fluid">
        <div class="container">
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link" href="createclass.htm">Create Class</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="email.htm">Email Hub</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="showEmployees.htm">Employees</a>
                </li>
            </ul>
        </div>
    </div>
 
    <div class="container-fluid bg-white" style="height: 100vh;">
        <div class="container pb-5 pt-3">
            
            <form:form method="post" action="searchEmployees.htm" class="form-inline pt-1 pb-2 w-100"> 
                
                <button class="btn btn-primary rounded-0 px-3 mr-2 my-1" type="submit"><i class="fas fa-search pr-1"></i>Search</button>
                    <select name="col" class="custom-select my-1 mr-sm-2">
                        <option value="name">Name</option>
                        <option value="email">Email</option>
                    </select>
                    <input type="text" placeholder="Search.." name="search" class="form-control my-1 mr-sm-2">
              
              
            </form:form>
            <table class="table table-striped table-sm table-bordered">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Manager</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items = "${emplist}" var = "emp">
                        <tr>
                            <td>
                                <a href="#" id="editButton" data-toggle="modal" data-target="#editModal" 
                                   data-target="#editModal" data-id="${emp.employeeID}" data-name="${emp.employeeName}" 
                                   data-email="${emp.employeeEmail}" data-managerid="${emp.managerID}" data-modulescores="${emp.modScores}">${emp.employeeName}
                                </a>
                            </td>
                            <td>${emp.employeeEmail}</td>
                            <td>${emp.managerID}</td>
                            <td>
                                <button id="deleteButton" type="button" class="btn btn-danger" data-toggle="modal"
                                        data-target="#deleteModal" data-id="${emp.employeeID}">Delete</button>
                            </td>                        
                        </tr>
                    </c:forEach>          
                </tbody>
            </table>

            <!-- Edit Employee Modal -->
            <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editModalLabel">Edit Employee</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <form:form method="post" action="editEmployees.htm">
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="editName">Employee Name: 
                                    </label> <input type="text" id="editName" name="editName" class="form-control" pattern="[A-Za-z]+ [A-Za-z]+">
                                </div>
                                <div class="form-group">
                                    <label for="editEmail">Employee Email: </label> 
                                    <input type="text" id="editEmail" name="editEmail" class="form-control" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$">
                                </div>   
                                <div class="form-group">
                                    <label for="editManagerID">Employee Manager ID: 
                                    </label> <input type="text" id="editManagerID" name="editManagerID" class="form-control" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$">
                                </div>
                                <div class="form-group">
                                    <label for="selectModule">Module Name: </label> 
                                    <select id="selectModule" name="selectModule" class="custom-select my-1 mr-sm-2"></select>
                                    <label for="editModuleModule">Module Score: </label>
                                    <input type="number" id="editModuleScore" name="editModuleScore" class="form-control my-1 mr-sm-2" step="0.01">
                                    <input type="hidden" id="editModuleID" name="editModuleID">
                                 </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" id="editCloseModalButton" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button name="editModalButton" type="submit" id="editModalButton" class="btn btn-primary btn-primary">
                                    Confirm Update
                                </button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>                

            <!-- Delete Employee Modal -->
            <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="deleteModalLabel">Confirm Delete</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            Are you sure you want to delete this employee?
                        </div>
                        <div class="modal-footer">
                            <form:form method="post" action="deleteEmployees.htm">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button name="deleteModalButton" type="submit" id="deleteModalButton" class="btn btn-primary btn-danger">
                                    Confirm Delete
                                </button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal button listener -->
            <!-- Add data thru modal buttons -->
            <script type="text/javascript">
                $(document).ready(function(){
                    //Clear modure score drop down when modal is closed.
                    $(document).on("hidden.bs.modal", function(){
                        $("#selectModule").empty();
                         $("#editModuleScore").val("");
                         $("#editModuleID").val("");
                    });
                    //Update module score when drop box of module name is changed.
                    $(document).on("change", "#selectModule", function(){
                        $("#editModuleScore").val($("#selectModule option:selected").data("modscore"));
                        $("#editModuleID").val($("#selectModule option:selected").data("modid"));
                    });
                    //Send data to edit modal
                    $(document).on("click", "#deleteButton", function(){
                        $("#deleteModalButton").val($(this).data("id"));
                    });
                    //Send data to delete modal
                    $(document).on("click", "#editButton", function(){
                        $("#editModalButton").val($(this).data("id"));
                        $("#editName").val($(this).data("name"));
                        $("#editEmail").val($(this).data("email"));
                        $("#editManagerID").val($(this).data("managerid"));
                        
                        //Parse out arraylist of modules
                        var scoreArr = $(this).data("modulescores").toString();
                        //Remove []
                        scoreArr = scoreArr.toString().substring(1, scoreArr.length);
                        scoreArr = scoreArr.toString().substring(0, scoreArr.length-2);
                        //Spilt into single modules
                        var s = scoreArr.split("], ");
                        //Extract module name and score
                        for(i = 0; i < s.length; i++){ 
                           //Seperate module name and score for each module name
                           var str = s[i].substring(8, s[i].length).split(", ");
                           var name = str[0].substring(9, str[0].length);
                           var scoreVal = str[1].substring(12, str[1].length);
                           var scoreID = str[2].substring(9, str[2].length);
                           
                           //Create new option in select tag for drop down
                           var o = new Option(name, name);  
                           $(o).html(name);   //inner html
                           $(o).attr("data-modscore", scoreVal); //Add data attr of score.
                           $(o).attr("data-modid", scoreID); //Add data attr of id.
                           $("#selectModule").append(o);                           
                        }
                        //If scores not empty then display score of selected.
                        if(s.length>0){
                            //Set to first option.
                            $("#selectModule>option:eq(0)").attr("selected", true);
                            $("#editModuleScore").val($("#selectModule option:selected").data("modscore"));
                            $("#editModuleID").val($("#selectModule option:selected").data("modid"));
                        }
                    });
                 });
            </script>
        </div>
    </div>

    <!-- Optional JavaScript -->

    <!-- Popper.js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <!-- Bootstrap.js -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</BODY>
</HTML>
