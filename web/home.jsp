<%-- 
    Document   : home
    Created on : Feb 18, 2021, 3:27:33 PM
    Author     : minhv
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link rel="stylesheet"  href="assets/css/bootstrap.min.css">
        <link rel="stylesheet"  href="assets/css/customize.css">
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>

        <!--Body-->
        <div class="container-fluid">
            <div class="row">
                <!--search form-->
                <div class="col-sm-12 col-md-3 col-lg-3">
                    <div class="card">
                        <div class="card-header bg-info text-white text-uppercase">
                            <span style="font-weight: bold">Search Car</span>
                        </div>

                        <c:set var="searchValue" value="${param.txtSearch}" />
                        <c:set var="searchCategory" value="${param.cmbCategory}" />
                        <c:set var="rentalDateValue" value="${param.txtRentalDate}" />
                        <c:set var="returnDateValue" value="${param.txtReturnDate}" />
                        <c:set var="amountValue" value="${param.txtAmount}" />
                        <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}" />

                        <div class="card-body">
                            <form action="search-car" class="form-group" method="POST" onsubmit="return validateSearch()">
                                <label>Name</label>
                                <input id="searchValue" type="text" name="txtSearch" class="form-control" value="${searchValue}" placeholder="enter car name" />
                                <label>Category</label>
                                <select id="categoryValue" name="cmbCategory" class="form-control">
                                    <option></option>
                                    <c:forEach var="category" items="${categoryList}">
                                        <option value="${category.categoryID}"
                                                <c:if test="${category.categoryID eq searchCategory}">
                                                    selected="true"
                                                </c:if>>
                                            ${category.categoryName}
                                        </option>
                                    </c:forEach>
                                </select>
                                <label>Rental Date</label>
                                <input id="rentalDate" type="date" class="form-control" name="txtRentalDate" value="${rentalDateValue}" />
                                <label>Return Date</label>
                                <input id="returnDate" type="date" class="form-control" name="txtReturnDate" value="${returnDateValue}" />
                                <label>Amount</label>
                                <input id="amount" type="number" class="form-control text-center" name="txtAmount" value="${amountValue}" min="1" step="1" />
                                <div class="text-center">
                                    <input type="submit" value="Search" name="btAction" class="btn btn-success my-3"/>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <!--End of search form-->

                <!--Display search result-->
                <c:set var="result" value="${requestScope.CAR_LIST}"/>
                <c:set var="totalPage" value="${requestScope.TOTAL_PAGES}"/>
                <c:if test="${not empty result}">
                    <div class="col-sm-12 col-md-9 col-lg-9">
                        <h3 class="text-center">Car</h3>
                        <!--Display car-->
                        <div class="row justify-content-center">
                            <c:forEach var="car" items="${result}">
                                <div class="col-12 col-md-6 col-lg-4 my-3   ">
                                    <div class="card h-100 border">
                                        <img src="load-image?file=${car.image}" class="card-img-top img" alt="Card image cap"/>
                                        <div class="card-body">
                                            <h5 class="card-title">${car.carName}</h5>
                                        </div>
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item">Created date:  ${car.year}</li>
                                            <li class="list-group-item">Color: ${car.color}</li>
                                            <li class="list-group-item">Price: ${car.price}</li>
                                        </ul>
                                        <div class="card-footer">
                                            <div class="text-center">
                                                <form action="view-detail" method="POST">
                                                    <input type="hidden" name="carId" value="${car.carID}" />
                                                    <input type="hidden" name="txtRentalDate" value="${rentalDateValue}" />
                                                    <input type="hidden" name="txtReturnDate" value="${returnDateValue}" />
                                                    <input type="submit" value="View Detail" name="btAction" class="btn btn-primary" />
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <!--get current page-->
                        <c:set var="curPage" value="${requestScope.DEFAULT_PAGES}"/>
                        <c:if test="${empty curPage}">
                            <c:set var="curPage" value="${param.page}"/>
                        </c:if>

                        <!-- Page indicator -->
                        <nav>
                            <ul class="pagination justify-content-center mt-4">
                                <c:forEach var="page" begin="1" end="${totalPage}" step="1">
                                    <c:url var="urlPaging" value="search-car">
                                        <c:param name="txtSearch" value="${searchValue}"/>
                                        <c:param name="cmbCategory" value="${searchCategory}"/>
                                        <c:param name="txtRentalDate" value="${rentalDateValue}"/>
                                        <c:param name="txtReturnDate" value="${returnDateValue}"/>
                                        <c:param name="txtAmount" value="${amountValue}"/>
                                        <c:param name="page" value="${page}"/>
                                    </c:url>
                                    <c:if test="${curPage eq page}">
                                        <li class="page-item active">
                                            <a class="page-link" href="${urlPaging}">${page}</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${curPage ne page}">
                                        <li class="page-item">
                                            <a class="page-link" href="${urlPaging}">${page}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </nav>
                    </div>
                </c:if>

                <c:if test="${empty result}">
                    <h2 class="text-center">No Car Founded!!!</h2>
                </c:if>

            </div>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/validation.js"></script>
    </body>
</html>