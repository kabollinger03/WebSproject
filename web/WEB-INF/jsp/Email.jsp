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
  
   out.println("<P>");
  
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

        <title>Atos Syntel &middot; Land</title>
    </head>
<body class="bg-light">
    
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">
                <i class="fas fa-rocket"></i>
                <!--<img width="30" height="30" position="inline-block" src="img/rocket-ship.png"/>-->
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="admin.htm">Admin <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="instructor.jsp">Instructor</a>
                    </li>
                </ul>
                <ul class="navbar-nav d-md-none d-lg-block">
                    <div class="btn-group mr-3">
                        <button style="color: #fff;" class="btn btn-sm dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <small> <i class="fas fa-user pr-1"></i> </small>
                        </button>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="#"><small><i class="fas fa-lock pr-2"></i>Change Password</small></a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="login.htm"><small>Logout</small></a>
                        </div>
                    </div>
                </ul>

            </div>
        </div><!-- /Container -->
    </nav><!-- /Nav -->
    
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
                    <a class="nav-link active" href="email.htm">Email Hub</a>
                </li>
            </ul>
        </div>
    </div>
 
    <div class="container-fluid bg-white" style="height: 100vh;">
        <div class="container pb-5 pt-3">
            <s:form commandName="email" class="mt-2"> 

            <h5 class="text-muted pb-2">Choose who you want to email:</h5>
            <table class="table table-bordered table-striped table-sm">
                <thead>
                    <tr>
                        <th scope="col" style="width: 50%;">Name</th>
                        <th scope="col" style="width: 40%;">Email</th>
                        <th scope="col" style="width: 10%;">Send?</th> 
                    </tr>
                </thead>
                <tbody>
              <!--<div class="btn-group-toggle" data-toggle="buttons">-->
                  <c:forEach items="${usersList}" var="user" varStatus="loop">
                      <tr>
                        <td class="noto">${nameList[loop.index]}</td>
                        <td class="noto">${user}</td>
                        <td class="text-center noto"><s:checkbox path="userNames" value="${user}" /></td>
                      </tr>

                  </c:forEach>
              <!--</div>-->
                </tbody>
            </table>
            <div class="row justify-content-center">
              <button type="submit" value="Login" class="btn btn-success mx-1"><i class="fas fa-reply pr-2"></i>Send</button>
              <button type="reset" value="Reset" class="btn-info btn mx-1"><i class="fas fa-sync-alt pr-2"></i>Reset</button>
            </div>
              </s:form>
        </div>
    </div>

    <!-- Optional JavaScript -->

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <!-- Popper.js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <!-- Bootstrap.js -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</BODY>
</HTML>