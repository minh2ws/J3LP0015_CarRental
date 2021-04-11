<%-- 
    Document   : view-cart
    Created on : Feb 24, 2021, 1:44:49 AM
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
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <title>Cart Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>

        <c:set var="cart" value="${requestScope.CUSTOMER_CART}"/>
        <c:if test="${not empty cart}">
            <div class="text-center mt-4">
                <h3>Cart</h3>
            </div>
            <div class="shopping-cart">
                <div class="px-4 px-lg-0">
                    <div class="pb-5">
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-12 p-5 bg-white rounded shadow-sm mb-5">
                                    <!--Car list-->
                                    <div class="table-responsive">
                                        <table class="table table-bordered">
                                            <thead class="thead-dark">
                                                <tr>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="p-2 px-3 text-uppercase">Car Name</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="py-2 text-uppercase">Car Type</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="py-2 text-uppercase">Amount</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="py-2 text-uppercase">Price</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="py-2 text-uppercase">Total</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="py-2 text-uppercase">Delete</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="py-2 text-uppercase">Update</div>
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="item" items="${cart.listItem}">
                                                <form action="update-quantity-car">
                                                    <tr>
                                                        <td class="align-middle">
                                                            <strong>${item.car.carName}</strong>
                                                            <img src="load-image?file=${item.car.image}" width="150" class="img-fluid rounded shadow-sm" />
                                                        </td>
                                                        <td class="align-middle">
                                                            <strong>${item.car.categoryId}</strong>
                                                        </td>
                                                        <td class="align-middle">
                                                            <input type="number" class="text-center rounded-pill border" name="txtQuantity" value="${item.quantity}" min="1" step="1"/>
                                                        </td>
                                                        <td class="align-middle">
                                                            <strong>${item.car.price}</strong>
                                                        </td>
                                                        <td class="align-middle">
                                                            <strong>${item.total}</strong>
                                                        </td>
                                                        <td class="align-middle">
                                                            <a href="#delete${item.car.carID}" class="delete" data-toggle="modal">
                                                                <button type="button" class="btn btn-primary">Delete</button>
                                                            </a>
                                                        </td>
                                                        <td class="align-middle">
                                                            <input type="hidden" name="carId" value="${item.car.carID}" />
                                                            <button type="summit" class="btn btn-success">Update</button>
                                                        </td>
                                                    </tr>
                                                </form>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!--End car table-->
                                </div>
                            </div>

                            <c:set var="error" value="${requestScope.CART_ERRORS}"/>
                            <div class="card-deck">
                                <div class="card">
                                    <div class="card-header" style="font-weight: bold">Detail</div>
                                    <div class="card-body">
                                        <c:set var="rentalDate" value="${cart.rentalDate}"/>
                                        <c:set var="returnDate" value="${cart.returnDate}"/>
                                        <c:set var="discountCode" value="${cart.discountCode}"/>
                                        <form action="calculate-total-price" method="POST" onsubmit="return validateDate()">
                                            <label><strong>Rental date</strong></label>
                                            <div class="input-group mb-4 border rounded-pill p-2">
                                                <input id="rentalDate" type="date" class="form-control border-0" name="txtRentalDate" value="${rentalDate}" />
                                            </div>
                                            <label><strong>Return date</strong></label>
                                            <div class="input-group mb-4 border rounded-pill p-2">
                                                <input id="returnDate" type="date" class="form-control border-0" name="txtReturnDate" value="${returnDate}" />
                                            </div>
                                            <label><strong>Voucher</strong</label>
                                            <div class="input-group mb-4 border rounded-pill p-2">
                                                <input type="text" name="discountCode" value="${discountCode}" placeholder="Enter Voucher" aria-describedby="button-addon3" class="form-control border-0">
                                            </div>
                                            <div class="input-group-append border-0 justify-content-center">
                                                <button type="submit" class="btn btn-dark px-4 rounded-pill">Apply</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <div class="card">
                                    <c:set var="total" value="${requestScope.TOTAL_PRICE}"/>
                                    <c:set var="totalPriceDiscounted" value="${requestScope.TOTAL_PRICE_DISCOUNTED}"/>
                                    <div class="card-header"  style="font-weight: bold">Payment</div>
                                    <div class="card-body">
                                        <ul class="list-unstyled mb-4">
                                            <li class="d-flex justify-content-between py-3 border-bottom">
                                                <strong class="text-muted">Total</strong>
                                                <strong>${total} $</strong>
                                            </li>
                                            <li class="d-flex justify-content-between py-3 border-bottom">
                                                <strong class="text-muted">Apply Discount</strong>
                                                <strong>${total - totalPriceDiscounted} $</strong>
                                            </li>
                                            <li class="d-flex justify-content-between py-3 border-bottom">
                                                <strong class="text-muted">Total Payment</strong>
                                                <strong>${totalPriceDiscounted} $</strong>
                                            </li>
                                        </ul>
                                        <c:if test="${empty error and total > 0}">
                                            <form action="checkout">
                                                <input type="submit" value="Checkout" class="btn btn-light px-4 rounded-pill"/>
                                            </form>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> 
                </div>
            </div>
            <!--End checkout page-->

            <!--Pop up-->
            <!--Delete Modal Car-->
            <c:forEach var="item" items="${cart.listItem}">
                <div class="modal fade" id="delete${item.car.carID}" role="dialog" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <form action="delete-car-item" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title">Delete Car From Cart</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <p>Do you want to delete <strong>${item.car.carName}</strong> car?</p>
                                </div>
                                <div class="modal-footer">
                                    <input type="hidden" name="carId" value="${item.car.carID}" />
                                    <input type="submit" class="btn btn-secondary" data-dismiss="modal" value="Cancel" />
                                    <input type="submit" class="btn btn-primary" value="Delete" />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <!--Check field validation Modal-->
            <c:if test="${not empty error}">
                <!--JavaScript awake check-->
                <script>
                    $(document).ready(function () {
                        $("#errorModal").modal();
                    });
                </script>

                <!--Check Modal-->
                <div class="modal fade" id="errorModal" role="dialog" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <img src="assets/img/warning.png" alt="warning" width="40" height="40"><br/>
                                <h4 class="modal-title">Warning</h4>
                                <button type="button" class="close" data-dismiss="modal">×</button>
                            </div>
                            <div class="modal-body">
                                <c:if test="${not empty errors.questionIdExistErr}">
                                    <p class="text-danger">${errors.questionIdExistErr}</p>
                                </c:if>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:if>

        <c:if test="${empty cart}">
            <h1>Your cart is empty!</h1>
        </c:if>

        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/validation.js"></script>
    </body>
</html>
