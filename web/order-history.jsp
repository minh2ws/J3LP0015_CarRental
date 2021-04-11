<%-- 
    Document   : order-history
    Created on : Feb 28, 2021, 11:15:20 PM
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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <title>Order History Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <div class="text-center mt-4">
                <h3>Rental History</h3>
            </div>
            <div class="container">
                <!--search form-->
                <div class="row">
                    <div class="col-lg-12 my-4">
                        <div class="card">
                            <h5 class="card-header text-center">Search</h5>
                            <div class="card-body">
                            <c:set var="searchValue" value="${param.txtSearchValue}"/>
                            <c:set var="fromDate" value="${param.txtFromDate}"/>
                            <c:set var="toDate" value="${param.txtToDate}"/>
                            <form action="search-order" class="form-group mx-2" method="POST" onsubmit="return validateSearchOrder()">
                                <div class="text-center my-2">
                                    <span style="font-weight: bold">Car Name </span>
                                </div>
                                <input id="txtSearchValue" type="text" name="txtSearchValue" value="${searchValue}" class="form-control" placeholder="Enter Car Name..." />
                                <div class="text-center my-2">
                                    <span style="font-weight: bold">Rental Date </span>
                                </div>
                                <span style="font-weight: bold">From: </span><input id="txtFromDate" type="date" class="form-control" name="txtFromDate" value="${fromDate}">
                                <span style="font-weight: bold">To: </span><input id="txtToDate" type="date" class="form-control" name="txtToDate" value="${toDate}">
                                <div class="text-center">
                                    <input type="submit" value="Search" name="btAction" class="btn btn-success my-3" />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!--end search form-->

            <c:set var="listOrder" value="${requestScope.ORDER_LIST}"/>
            <c:set var="orderDetailMap" value="${requestScope.ORDER_DETAIL_MAP}"/>
            <c:if test="${not empty listOrder}">
                <c:forEach var="order" items="${listOrder}">
                    <div class="row">
                        <div class="col-lg-10 ml-auto">
                            <div>
                                <span style="font-weight: bold">OrderID: </span>${order.orderID}
                            </div>
                            <div>
                                <span style="font-weight: bold">Email: </span>${order.email}
                            </div>
                            <div>
                                <span style="font-weight: bold">Status: </span>
                                <c:if test="${order.status eq 'ACTIVE'}">
                                    <span style="color: green">Active</span>
                                </c:if>
                                <c:if test="${order.status eq 'DEACTIVE'}">
                                    <span style="color: red">Deactive</span>
                                </c:if>
                            </div>
                        </div>
                        <div class="col-lg-2 mr-0">
                            <a href="#delete-order${order.orderID}" class="delete" data-toggle="modal">
                                <button type="button" class="btn btn-primary">Delete</button>
                            </a>
                        </div>
                    </div>

                    <table class="table table-bordered">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">No.</th>
                                <th scope="col">Car Name</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Price</th>
                                <th scope="col">Total</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="itemList" value="${orderDetailMap[order.orderID]}"/>
                            <c:forEach var="item" items="${itemList}" varStatus="counter">
                                <tr class>
                                    <td class="align-middle">
                                        ${counter.count}
                                    </td>
                                    <td class="align-middle">
                                        <img src="load-image?file=${item.car.image}" width="150" class="img-fluid rounded shadow-sm" /></br>
                                        ${item.car.carName}
                                    </td>
                                    <td class="align-middle">
                                        ${item.quantity}
                                    </td>
                                    <td class="align-middle">
                                        $ ${item.car.price}/Day
                                    </td>
                                    <td class="align-middle">
                                        $ ${item.total}
                                    </td>
                                    <td class="align-middle">
                                        <a href="#feedback${order.orderID}-${counter.count}" class="btn btn-success" data-toggle="modal">Feedback</a>
                                    </td>
                                </tr>
                                <!-- Feedback Modal HTML -->
                            <div id="feedback$${order.orderID}-${counter.count}" class="modal fade">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <form action="send-feedback" method="POST">
                                            <div class="modal-header">						
                                                <h4 class="modal-title">Add Feedback</h4>
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            </div>
                                            <div class="modal-body">					
                                                <div class="form-group">
                                                    <label>Content</label>
                                                    <input name="txtContent" type="text" class="form-control" required>
                                                </div>
                                                <div class="form-group">
                                                    <label>Point</label>
                                                    <select name="cmbPoint" class="form-select" aria-label="Default select example">
                                                        <option value="1">1</option>
                                                        <option value="2">2</option>
                                                        <option value="3">3</option>
                                                        <option value="4">4</option>
                                                        <option value="5">5</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <input type="hidden" name="orderId" value="${order.orderID}" />
                                                <input type="hidden" name="carId" value="${item.car.carID}" />
                                                <input type="hidden" name="txtSearchValue" value="${searchValue}" />
                                                <input type="hidden" name="txtFromDate" value="${fromDate}" />
                                                <input type="hidden" name="txtToDate" value="${toDate}" />
                                                <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                                                <input type="submit" class="btn btn-success" value="Add">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:forEach> 
                        <tr class="table-warning text-right" style="font-weight: bold">
                            <td scope="row" colspan="2">Rental Date:</td>
                            <td class="text-center" scope="row">${order.bookingDate}</td>
                            <td scope="row" colspan="2">Return Date:</td>
                            <td class="text-center" scope="row">${order.returnDate}</td>
                        </tr>
                        <tr class="table-info text-right" style="font-weight: bold">
                            <td scope="row" colspan="5">Cart Total:</td>
                            <td class="text-center" scope="row">$ ${order.totalAmount}</td>
                        </tr>
                        <tr class="table-success text-right" style="font-weight: bold">
                            <td scope="row" colspan="5">Total After Discount:</td>
                            <td class="text-center" scope="row">$ ${order.totalAfterDiscount}</td>
                        </tr>
                        </tbody>
                    </table>
                </c:forEach>

                <!--Delete Modal Car-->
                <c:forEach var="order" items="${listOrder}">
                    <div class="modal fade" id="delete-order${order.orderID}" role="dialog" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <form action="delete-order" method="POST">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Delete Order</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Do you want to delete <strong>OrderID: ${order.orderID}</strong>?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="orderId" value="${order.orderID}" />
                                        <input type="hidden" name="txtSearchValue" value="${searchValue}" />
                                        <input type="hidden" name="txtFromDate" value="${fromDate}" />
                                        <input type="hidden" name="txtToDate" value="${toDate}" />
                                        <input type="submit" class="btn btn-secondary" data-dismiss="modal" value="Cancel" />
                                        <input type="submit" class="btn btn-primary" value="Delete"/>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty listOrder}">
                <h4 class="text-center my-5">
                    No Rental Orders Found!!
                </h4>
            </c:if>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/validation.js"></script>
    </body>
</html>
