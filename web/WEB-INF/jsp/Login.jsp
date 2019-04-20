<%-- 
    Document   : Login
    Created on : Jan 3, 2017, 11:35:52 PM
    Author     : LS5028230
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!doctype html>
<html lang="en">
<head>
	<!-- Required meta tags -->
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

	<title>Home</title>
</head>

<body class="animated fadeIn">
	<div class="container-fluid h-100">
		<div class="row justify-content-center align-items-center h-100">
			<div class="col col-sm-6 col-md-6 col-lg-4 col-xl-6 card mx-2 py-5 animated fadeIn shadow-lg rounded-0">
				<div class="row justify-content-center">
					<div class="col-7">
                                               <img src="resources/img/logo.png" class="img-fluid">
					</div>
				</div>

                                <!-- these message only displays if it receives a value from Authentication.java controller -->
                                <p class="text-center text-danger">${errorMessage}</p>
                            
				<!-- Main login form -->
				<s:form commandName="user" class="px-5 noto" id="login-form">
					<div class="input-group mb-3">
					  <div class="input-group-prepend">
					    <span class="input-group-text" id="basic-addon1"><i class="far fa-envelope"></i></span>
					  </div>
					  <input type="text" class="form-control-lg form-control" name="userName" placeholder="Email" aria-label="Email" aria-describedby="basic-addon1">
					</div>
					<div class="input-group mb-2">
					  <div class="input-group-prepend">
					    <span class="input-group-text" id="basic-addon2"><i class="fas fa-lock"></i></span>
					  </div>
					  <input type="password" class="form-control-lg form-control" name="password" placeholder="Password" aria-label="Password" aria-describedby="basic-addon2">
					</div>
                                        
                                        <!--forgot password link/button-->
					<small class="text-muted">
                                            <a id="myLink" href="reset-password.htm">Forgot Password?</a>
                                        </small>
					<div class="form-group text-center pt-2">
						<button type="submit" href="#" class="btn bg-primary text-white rounded-0 px-5">
							<i class="fas fa-sign-in-alt pr-2"></i>Sign In
						</button>
					</div>
				</s:form>
			</div>
		</div>
	</div>

    <script src="<c:url value="/resources/js/form.js" />"></script>

    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</body>
</html>