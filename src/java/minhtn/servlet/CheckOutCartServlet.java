/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhtn.cart.CartError;
import minhtn.cart.CartItem;
import minhtn.cart.CartObject;
import minhtn.tblCar.TblCarDAO;
import minhtn.tblCar.TblCarDTO;
import minhtn.tblOrder.TblOrderDAO;
import minhtn.tblOrderDetail.TblOrderDetailDAO;
import minhtn.tblUsers.TblUsersDTO;
import minhtn.utilities.GenerateCode;

/**
 *
 * @author minhv
 */
@WebServlet(name = "CheckOutCartServlet", urlPatterns = {"/CheckOutCartServlet"})
public class CheckOutCartServlet extends HttpServlet {

    private final String HOME_PAGE = "home";
    private final String VIEW_CART_SERVLET = "view-cart";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = VIEW_CART_SERVLET;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblUsersDTO user = (TblUsersDTO) session.getAttribute("ACCOUNT");
                String email = "";
                if (user != null) 
                    email = user.getEmail();

                CartObject cart = (CartObject) session.getAttribute("CUSTOMER_CART");
                if (cart != null) {
                    if (!cart.getListItem().isEmpty()) {
                        CartError error = new CartError();
                        boolean isErrored = false;

                        String errorString = checkCarQuantity(cart);
                        if (!errorString.isEmpty()) {
                            error.setQuantityError(errorString + "\nPlease change quantity and update!");
                            isErrored = true;
                        }

                        if (isErrored) 
                            request.setAttribute("CART_ERRORS", error);
                        else {
                            String orderId = GenerateCode.generateCartID();

                            TblOrderDAO dao = new TblOrderDAO();
                            boolean isInsertSuccess = dao.createOrderRentalCar(orderId, email, cart);
                            if (isInsertSuccess) {
                                TblOrderDetailDAO orderDetail = new TblOrderDetailDAO();
                                boolean isBatchSuccess = orderDetail.createOrderDetail(orderId, cart.getListItem());
                                if (isBatchSuccess) {
                                    //remove cart
                                    session.setAttribute("CUSTOMER_CART", null);

                                    url = HOME_PAGE;
                                } //end if order detail insert success
                            } //end if order insert success
                        } //end if don't have error
                    } //end if cart has items
                } //end if cart is existed
            } //end if session is existed
        } catch (SQLException e) {
            log("CheckOutCartServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("CheckOutCartServlet _ NamingException: " + e.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> functionMap = (Map<String, String>) context.getAttribute("FUNCTION_MAP");
            RequestDispatcher rd = request.getRequestDispatcher(functionMap.get(url));
            rd.forward(request, response);
        }
    }

    private String checkCarQuantity(CartObject cart) throws SQLException, NamingException {
        String errorString = "";

        //load map car rented between rental date and return date
        TblCarDAO dao = new TblCarDAO();
        dao.getCarRented(cart.getRentalDate().toString(), cart.getReturnDate().toString());
        Map<String, Integer> mapCarRented = dao.getMapCarRented();

        //if map car rented not null and carId is existed in map car => check quantity
        if (mapCarRented != null) {
            List<CartItem> listCartItem = cart.getListItem();
            for (CartItem cartItem : listCartItem) {
                TblCarDTO car = cartItem.getCar();
                if (mapCarRented.containsKey(car.getCarID())) {
                    int quantityCarInDB = car.getQuantity();
                    int quantityCarRented = mapCarRented.get(car.getCarID());
                    int quantityAvailable = quantityCarInDB - quantityCarRented - cartItem.getQuantity();
                    if (quantityAvailable < 0) {
                        int quantityLeft = quantityAvailable + cartItem.getQuantity();
                        errorString = car.getCarName() + " only have " + quantityLeft + " car left!\n";
                        return errorString;
                    }
                }
            }
        }
        return errorString;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
