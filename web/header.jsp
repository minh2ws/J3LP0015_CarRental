<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Navigation bar-->
<c:set var="account" value="${sessionScope.ACCOUNT}"/>
<c:set var="cart" value="${sessionScope.CUSTOMER_CART}"/>
<nav class="navbar navbar-dark navbar-expand-sm bg-warning">
    <c:if test="${not empty account}">
        <ul class="navbar-nav mr-auto py-0">
            <li class="nav-item">
                <a class="nav-link active" href="home">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link"href="order-history">Rental History</a>
            </li>
            <c:if test="${account.role ne 'ADMIN'}">
                <li class="nav-item">
                    <a class="nav-link" href="view-cart">
                        Cart
                        <c:if test="${not empty cart.listItem}">
                            <span class="badge badge-danger">${cart.totalItem}</span>
                        </c:if>
                        <c:if test="${empty cart.listItem}">
                            <span class="badge badge-danger">0</span>
                        </c:if>
                    </a>
                </li>
            </c:if>
        </ul>
        <ul class="navbar-nav ml-auto text-center">
            <!-- Logout form -->
            <li class="nav-item">
                <div class="welcome-user">Welcome, ${account.name}</div>		
            </li>
            <li class="nav-item">
                <form action="logout">
                    <input type="submit" value="Logout" class="btn btn-danger btn-sm my-2 my-sm-0 mx-3"/>
                </form>
            </li>
        </ul>
    </c:if>
    <c:if test="${empty account}">
        <ul class="navbar-nav mr-auto py-0">
            <li class="nav-item">
                <a class="nav-link active" href="home">Home</a>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto text-center">
            <li class="nav-item">
                <a class="btn btn-light btn-sm my-2 my-sm-0 mx-3" href="login-page">Login</a>
            </li>
        </ul>
    </c:if>
</nav>



