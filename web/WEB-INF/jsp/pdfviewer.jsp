<%@page import="Controller.PDF"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <%
     String s = PDF.id+".pdf";
     
     %>
<!DOCTYPE html>

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
        <div>
            
            <embed src="${employeeID}.pdf" width="100%" height="900" 
            type="application/pdf">
        </div>
            <h1>${employeeID}</h1>
            
            <form>
            <div class="row justify-content-center">
              <button type="submit" value="Login" class="btn btn-success mx-1"><i class="fas fa-reply pr-2"></i>Send</button>
              <button type="reset" value="Reset" class="btn-info btn mx-1"><i class="fas fa-sync-alt pr-2"></i>Cancel</button>
            </div>
            </form>
    </body>
</html>
