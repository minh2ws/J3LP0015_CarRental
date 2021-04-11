/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
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
import minhtn.cart.CartObject;
import minhtn.tblDiscount.TblDiscountDAO;
import minhtn.tblDiscount.TblDiscountDTO;
import minhtn.utilities.DateUtils;

/**
 *
 * @author minhv
 */
@WebServlet(name = "CalculateTotalPriceServlet", urlPatterns = {"/CalculateTotalPriceServlet"})
public class CalculateTotalPriceServlet extends HttpServlet {
    
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
            String txtRentalDate = request.getParameter("txtRentalDate");
            String txtReturnDate = request.getParameter("txtReturnDate");
            String discountCode = request.getParameter("discountCode");
            int totalDaysRental = 0;
            int discountValue = 0;
            
            CartError error = new CartError();
            boolean isErrored = false;
            
            Date today = DateUtils.getCurrentDate();
            
            Date rentalDate = Date.valueOf(txtRentalDate);
            Date returnDate = Date.valueOf(txtReturnDate);
            
            //check if user apply voucher or not
            if (!discountCode.trim().isEmpty()) {
                TblDiscountDAO dao = new TblDiscountDAO();
                TblDiscountDTO discount = dao.getDiscount(discountCode);
                
                if (discount == null) {
                    isErrored = true;
                    error.setInvalidDiscountError("Discount is not existed!");
                } else {
                    Date expiredDate = discount.getExpiredDate();
                    int compareDay = DateUtils.calculateDay(today, expiredDate);
                    if (compareDay < 0) {
                        isErrored = true;
                        error.setExpiredDiscountError("Discount code is expired!");
                    } else
                        discountValue = discount.getDiscount();
                    //check if discount code is expired or not
   
                } //check if discount is existed or not
            } //end if discount is existed
            
            if (isErrored) 
                request.setAttribute("CART_ERRORS", error);
            else {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    CartObject cart = (CartObject) session.getAttribute("CUSTOMER_CART");
                    
                    //calculate total day rente
                    totalDaysRental = DateUtils.calculateDay(rentalDate, returnDate);
                    if (cart !=  null) {
                        cart.setRentalDate(rentalDate);
                        cart.setReturnDate(returnDate);
                        cart.setTotalDay(totalDaysRental);
                        cart.setDiscountCode(discountCode);
                        cart.setDiscount(discountValue);
                    }
                }
            }
        } catch (SQLException e) {
            log("CalculateTotalPriceServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("CalculateTotalPriceServlet _ NamingException: " + e.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> functionMap = (Map<String, String>) context.getAttribute("FUNCTION_MAP");
            RequestDispatcher rd = request.getRequestDispatcher(functionMap.get(url));
            rd.forward(request, response);
        }
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
