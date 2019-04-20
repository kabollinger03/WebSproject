<%@page import="POJO.Stream"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%> 

<!--database access-->
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
    
    String sql = "SELECT * FROM stream";
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(sql);
    
    ArrayList<String> streamIDs = new ArrayList();
    request.setAttribute("streamIDs", streamIDs);
        
    ArrayList<String> streamNames = new ArrayList();
    request.setAttribute("streamNames", streamNames);
    
    ArrayList<Stream> allStreams = new ArrayList<Stream>();
    while(rs.next()) {
        allStreams.add(new Stream(rs));
    }
    request.setAttribute("allStreams", allStreams);
    
    // fill in names and IDs, two separate arrays
//    while(rs.next()) {
//        streamIDs.add(rs.getString("stream_id"));
//        streamNames.add(rs.getString("stream_name"));
//    }

    rs.close(); // close resources
    stmt.close();
    conn.commit();
    conn.close();
%>


<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <!-- Navbar -->
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
                            <a class="nav-link" href="createclass.htm">Instructor</a>
                        </li>
                    </ul>
                    <ul class="navbar-nav d-md-none d-lg-block">
                        <div class="btn-group mr-3">
                            <button style="color: #fff;" class="btn btn-sm dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <small> <i class="fas fa-user pr-1"></i></small>
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
        </nav>
        <!-- End Navbar -->
        
        <div class="container-fluid">
            <div class="container mt-2 pt-4 pb-3">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb" style="background: transparent;">
                        <li class="breadcrumb-item"><a href="#">Instructor</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Create Class</li>
                    </ol>
                </nav>
            </div>
        </div>
        
        <!-- Tabs -->
        <div class="container-fluid">
            <div class="container">
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a class="nav-link active" href="createclass.htm">Create Class</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="email.htm">Email Hub</a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- End Tabs -->
        
        <div class="container-fluid bg-white" style="height: 100vh;">
            <div class="container pb-5 pt-4">
                <div class="row mx-1">
                    <div class="col-8 px-3 py-3">
                        <s:form commandName="excel">
                            <Table class="table">
                                <tr class="my-2">
                                   
                                    <td> Stream:</td> <!-- stream dropdown -->
                                    <td>
                                        
                                            <select id="selectedStream" name="streamName" class="form-control">
                                                <c:forEach items="${allStreams}" var="stream">
                                                    <option value="${stream.ID}"> 
                                                        ${stream.name}
                                                    </option>
                                                </c:forEach>
                                            </select>      
                                        
                                    </td>
                                </tr>
                                <tr class="my-2">
                                    <td> Location:</td>
                                    <td><input type="text" name="location" class="form-control"/></td>
                                </tr>
                                <tr>
                                    <td>Site</td>
                                    <td><select name="site" class="form-control">
                                        <option value="">Select</option>
                                        <option  id="offSite" value="CHI">CHI</option>
                                        <option  id="onSite" value="OPA">OPA</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Instructor Email:</td>
                                    <td><input type="email" name="insEmail" class="form-control"/></td>
                                </tr>
                                <tr>
                                 <tr>
                                    <td>Start Date:</td>
                                    <td><input type="date" name="startDate" class="form-control"/></td>
                                </tr>
                                 <tr>
                                    <td>End Date:</td>
                                    <td><input type="date" name="endDate" class="form-control"/></td>
                                </tr>
                                <tr>
                                <tr>   
                                    <td>Upload Excel</td>
                                    <td>  
                                            <input type="file" name="file" style=""/>
                                            <label class="custom-file-input">Choose file...</label>
                                    </td>
                                    <td></td>
                                </tr>
                            </Table>
                            <div class="row justify-content-center">
                                <input type="submit" value="Submit" class="btn btn-success px-3"/>
                            </div>
                        </s:form>
                    </div><!-- End col 8 -->
                    <div class="col-4 mt-4">
                        Download template<br>
                        <c:forEach items="${allStreams}" var="stream"> 
                            <a href="download.htm?streamID=${stream.ID}">${stream.name}</a><br>
                        </c:forEach>
                    </div>
                </div><!-- End row --> 
            </div><!-- End container --> 
        </div><!-- End fluid container -->
        
        
        
        <!-- Optional JavaScript -->
  
        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <!-- Popper.js -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <!-- Bootstrap.js -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>

<style>
    html{
        height: 100%;
    }

    p, h1, h2, h3, h4, h5, h6, li, ul, ol, a, button, tr, td, table, input, label{
        font-family: 'Noto Sans', sans-serif;
    }

    .atos-blue{
        color: rgb(0,102,161) !important;
    }

    .noto{
        font-family: 'Noto Sans', sans-serif;
    }

    .font-dark{
        color: #393E41;
    }

    .bg-white{
        background: #fff;
    }

    .bg-gray{
        background: #e5e5e5 !important;
    }

    /* adds little triangle on dropdown menu */

    .triangle:before {
        position: absolute;
        top: -7px;
        left: 9px;
        display: inline-block;
        border-right: 7px solid transparent;
        border-bottom: 7px solid #CCC;
        border-left: 7px solid transparent;
        border-bottom-color: rgba(0, 0, 0, 0.2);
        content: '';
    }

    .triangle:after {
        position: absolute;
        top: -6px;
        left: 10px;
        display: inline-block;
        border-right: 6px solid transparent;
        border-bottom: 6px solid white;
        border-left: 6px solid transparent;
        content: '';
    }

    /* End Triange Dropdown */

    .card-header{
        background: rgb(241,248,255);
    }

    .btn-ghost{
        background: rgb(248,249,251);
        border: 1px solid rgba(27,31,35,.2);
        transition: background;
        transition-timing-function: ease-in-out;
        transition-duration: .2s;
        font-size: .8em;
    }

    .btn-ghost:hover{
        background: #e6ebf1;
    }

    .ghost{
        background: rgb(248,249,251);
        border: 1px solid rgba(27,31,35,.2);
        transition: background;
        transition-timing-function: ease-in-out;
        transition-duration: .2s;
    }
    .ghost:hover{
        background: #e6ebf1;
    }
</style>
