<%-- 
    Document   : active-account
    Created on : Feb 20, 2021, 3:05:55 PM
    Author     : minhv
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet"  href="assets/css/bootstrap.min.css">
        <link rel="stylesheet"  href="assets/css/customize.css">
        <title>Active Account Page</title>
    </head>
    <body>
        <c:set var="activeError" value="${requestScope.CREATE_ACCOUNT_ERRORS}"/>
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
        <div class="container-fluid h-100 bg-custom">
            <div class="row h-100 justify-content-center align-items-center">
                <div class="card p-5 col-10 col-md-8 col-lg-6">
                    <h1 class="text-center">Active Account</h1>
                    <form action="active-account" method="POST">
                        <div class ="row">
                            <div class="justify-content-center">
                                <div class="form-group">
                                    <h4>Enter 6 digits send to your email - ${account.email}</h4>
                                    <input type="text" name="txtActiveCode" value="" class="form-control" placeholder="123456"/><br/>
                                    <c:if test="${not empty activeError.activeCodeError}">
                                        <p class="text-danger">Active code is not correct</p>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="text-center">
                            <input type="submit" value="Active Account" name="btAction" class="btn btn-primary px-5 my-3" /> <br/>
                            <a href="resend-code" class="btn btn-primary">Resend Activation Code</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
