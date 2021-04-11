<%-- 
    Document   : car-detail
    Created on : Feb 23, 2021, 5:44:19 PM
    Author     : minhv
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Car Detail Page</title>
        <link rel="stylesheet"  href="assets/css/bootstrap.min.css">
        <link rel="stylesheet"  href="assets/css/customize.css">
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>

        <!--Body-->
        <div class="container-fluid my-4">
            <div class="col-sm-12">
                <div class="container">
                    <c:set var="car" value="${requestScope.CAR}"/>
                    <div class="card">
                        <div class="row">
                            <aside class="col-sm-2 col-md-3 col-lg-4">
                                <img src="load-image?file=${car.image}" class="img-fluid rounded shadow-sm" />
                            </aside>
                            <aside class="col-sm-7">
                                <article class="card-body p-5">
                                    <h3 class="tilte mb-3">${car.carName}</h3>

                                    <p class="price-detail-wrap">
                                        <span class="price h3 text-danger"> 
                                            <span class="currency">Price: $</span><span class="num">${car.price}/Day</span>
                                        </span>
                                    </p>

                                    <div>
                                        <span style="font-weight: bold">Category: </span> ${car.categoryId}
                                    </div>
                                    <div>
                                        <span style="font-weight: bold">Color: </span> ${car.color}
                                    </div>
                                    <div>
                                        <span style="font-weight: bold">Quantity: </span> ${car.quantity}
                                    </div>
                                    <div>
                                        <span style="font-weight: bold">Year: </span> ${car.year}
                                    </div>
                                    <dl class="item-property">
                                        <dt>Description:</dt>
                                        <dd>
                                            <p>${car.description} </p>
                                        </dd>
                                    </dl>
                                    <a href="rente-car?carId=${car.carID}" class="btn btn-lg btn-outline-primary text-uppercase"> <i class="fa fa-shopping-cart"></i> Add to cart </a>
                                </article>
                            </aside>
                        </div>
                    </div>
                    <h3>Feedback</h3>
                    <c:set var="feedbackList" value="${requestScope.FEEDBACK_LIST}"/>
                    <c:if test="${not empty feedbackList}">
                        <c:forEach var="feedback" items="${feedbackList}">
                            <c:if test="${not empty feedback.content and feedback.point ne 0}">
                                <div class="my-4">
                                    <div class="card-header">
                                        <span style="font-weight: bold">Customer: </span> ${feedback.name}</br>
                                    </div>
                                    <div class="card-body">
                                        <span style="font-weight: bold">Content: </span>${feedback.content}</br>
                                        <span style="font-weight: bold">Point: </span> ${feedback.point}/5
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
