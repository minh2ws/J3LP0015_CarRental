<%-- 
    Document   : login
    Created on : Jan 25, 2021, 10:52:44 PM
    Author     : minhv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet"  href="assets/css/bootstrap.min.css">
        <link rel="stylesheet"  href="assets/css/customize.css">
        <!-- reCAPTCHA script with English language -->
	<script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
    </head>
    <body>
        <div class="container-fluid h-100 bg-custom">
			<div class="row h-100 justify-content-center align-items-center">
                <div class="card col-10 col-md-8 col-lg-6 p-5">
                    <h1 class="text-center">Sign in</h1>
                    <form action="login" method="POST" class="form-group">
                        <label>Username</label> 
                        <input type="text" name="txtEmail" value="${param.txtEmail}" class="form-control"/> <br/>
                        <label>Password</label> 
                        <input type="password" name="txtPassword" value="" class="form-control"/> <br/>
                        <div class="text-center">
                            <!--reCAPTCHA-->
                            <div class="g-recaptcha" data-sitekey="6Lek0zoaAAAAAH2zF2qBWBke9DYwfyfadOKYNAXg"></div>
                            <div class="text-danger text-bold">${requestScope.CAPTCHAR_ERROR}</div>
                        </div>
                        <!--Login button-->
                        <div class="text-center">
                            <input type="submit" value="Login" class="btn btn-primary px-5 my-3"/>
                        </div>
                    </form>
                    <div class="text-center">
                        Don't have account? <a href="register-page">Sign up</a> </br>
                        Search for car only? <a href="home">Home page</a> </br>                            
                    </div>
                </div>
            </div>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
