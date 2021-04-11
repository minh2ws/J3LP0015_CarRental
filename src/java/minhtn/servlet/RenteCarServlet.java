/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhtn.cart.CartObject;
import minhtn.tblUsers.TblUsersDTO;

/**
 *
 * @author minhv
 */
@WebServlet(name = "RenteCarServlet", urlPatterns = {"/RenteCarServlet"})
public class RenteCarServlet extends HttpServlet {

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
        
        String url = null;
        
        try {
            String carID = request.getParameter("carId");
            String role = "GUEST";
            
            //check role
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblUsersDTO user = (TblUsersDTO) session.getAttribute("ACCOUNT");
                if (user != null) 
                    role = user.getRole();
                
                if (role.equals("GUEST"))
                    url = "login-page";
                else {
                    CartObject cartObject = (CartObject) session.getAttribute("CUSTOMER_CART");
                    if (cartObject == null) {
                        cartObject = new CartObject();
                    }
                    
                    cartObject.addItemToCart(carID);
                    session.setAttribute("CUSTOMER_CART", cartObject);
                    
                    //create url Rewriting to keep current page car detail
                    url = "view-detail?carId=" + carID;
                }
            }
        } catch (SQLException e) {
            log("RenteCarServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("RenteCarServlet _ NamingException: " + e.getMessage());
        } finally {
            response.sendRedirect(url);
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
