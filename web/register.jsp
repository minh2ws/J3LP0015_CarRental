<%-- 
    Document   : register
    Created on : Feb 18, 2021, 4:55:38 PM
    Author     : minhv
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <link rel="stylesheet"  href="assets/css/bootstrap.min.css">
        <link rel="stylesheet"  href="assets/css/customize.css">
    </head>
    <body>
        <c:set var="errors" value="${requestScope.CREATE_ACCOUNT_ERRORS}"/>
        <div class="container-fluid h-100 bg-custom">
            <div class="row h-100 justify-content-center align-items-center">
                <div class="card p-5 col-10 col-md-8 col-lg-6">
                    <h1 class="text-center">Sign up</h1>
                    <form action="register" method="POST">
                        <div class ="row">
                            <div class="col-6">
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="text" name="txtEmail" value="" class="form-control" placeholder="example@gmail.com"/>
                                    <c:if test="${not empty errors.emailExisted}">
                                        <p class="text-danger">${errors.emailExisted}</p>
                                    </c:if>
                                    <c:if test="${not empty errors.emailFormatError}">
                                        <p class="text-danger">${errors.emailFormatError}</p> 
                                    </c:if>

                                    <label>Phone</label>
                                    <input type="text" name="txtPhone" value="" class="form-control" placeholder="3-10 digits"/>
                                    <c:if test="${not empty errors.phoneLengthError}">
                                        <p class="text-danger">${errors.phoneLengthError}</p>
                                    </c:if>
                                    <c:if test="${not empty errors.phoneFormatError}">
                                        <p class="text-danger">${errors.phoneFormatError}</p>
                                    </c:if>

                                    <label>Password</label>
                                    <input type="password" name="txtPassword" value="" class="form-control" placeholder="3-50 chars"/>
                                    <c:if test="${not empty errors.passwordLengthErorr}">
                                        <p class="text-danger">${errors.passwordLengthErorr}</p>
                                    </c:if>
                                </div>
                            </div>

                            <div class="col-6">
                                <div class="form-group">
                                    <label>Full name</label>
                                    <input type="text" name="txtFullname" value="" class="form-control" placeholder="6-100 chars"/>
                                    <c:if test="${not empty errors.fullnameLengthError}">
                                        <p class="text-danger">${errors.fullnameLengthError}</p>
                                    </c:if>

                                    <label>Address</label>
                                    <input type="text" name="txtAddress" value="" class="form-control" placeholder="3-200 chars"/>
                                    <c:if test="${not empty errors.addressLengthError}">
                                        <p class="text-danger">${errors.addressLengthError}</p>
                                    </c:if>
                                    <label>Confirm Password</label>
                                    <input type="password" name="txtConfirm" value="" class="form-control"/>
                                    <c:if test="${not empty errors.passwordConfirmNotMatched}">
                                        <p class="text-danger">${errors.passwordConfirmNotMatched}</p>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="text-center">
                            <input type="submit" value="Create Account" name="btAction" class="btn btn-primary px-5 my-3"/> <br/>
                        </div>
                    </form>

                    <div class="text-center">
                        Already have account? 
                        <a href="login-page">Sign in</a>
                    </div>
                </div>
            </div>
            <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
