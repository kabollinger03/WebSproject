<%-- 
    Document   : reset-password
    Created on : Apr 17, 2019, 3:04:07 PM
    Author     : syntel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<!-- Custom CSS -->
	<!--<link rel="stylesheet" href="css/login.css">-->
        <link rel="stylesheet" href="resources/css/login.css">
	<!-- Animate CSS -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.0/animate.min.css">
	<!-- Font Awesome -->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
	<!-- Google Fonts (Noto Sans) --> 
	<link href="https://fonts.googleapis.com/css?family=Noto+Sans" rel="stylesheet">
        <title>Reset Password</title>
    </head>
    <body class="animated fadeIn">
        <div class="container-fluid h-100">
		<div class="row justify-content-center align-items-center h-100">
			<div class="col col-sm-6 col-md-6 col-lg-4 col-xl-6 card mx-2 py-5 animated fadeIn shadow-lg rounded-0">
				<div class="row justify-content-center">
					<div class="col-4">
                                               <img src="resources/img/logo-fake.png" class="img-fluid pt-3 pb-5">
					</div>
				</div>
                                <s:form commandName="forgotPass" class="px-5 noto">
                                    <div class="input-group mb-3">
					  <div class="input-group-prepend">
					    <span class="input-group-text" id="basic-addon1"><i class="far fa-envelope"></i></span>
					  </div>
                                        <input type="email" class="form-control-lg form-control" name="email" placeholder="Email" required>
                                    </div>
                                    <div class="form-group text-center pt-2">
                                        <button class="btn btn-primary rounded-0 px-4" type="submit"><i class="fas fa-user-lock pr-2"></i>Reset</button>
                                    </div>
                                </s:form>
                        </div>
                </div>
        </div>
    </body>
</html>
