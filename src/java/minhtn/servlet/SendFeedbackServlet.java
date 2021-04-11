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
import minhtn.feedback.FeedBackDAO;

/**
 *
 * @author minhv
 */
@WebServlet(name = "SendFeedbackServlet", urlPatterns = {"/SendFeedbackServlet"})
public class SendFeedbackServlet extends HttpServlet {

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

        String url = "";

        try {
            //properties for url rewriting to call function search order again
            String txtSearchValue = request.getParameter("txtSearchValue");
            String txtFromDate = request.getParameter("txtFromDate");
            String txtToDate = request.getParameter("txtToDate");
            //properties for insert feedback
            String orderId = request.getParameter("orderId");
            String carId = request.getParameter("carId");
            String txtContent = request.getParameter("txtContent");
            String cmbPoint = request.getParameter("cmbPoint");
            
            int point = Integer.parseInt(cmbPoint.trim());
            
            FeedBackDAO dao = new FeedBackDAO();
            boolean isSuccess = dao.insertFeedback(orderId, carId, txtContent, point);
            if (isSuccess) {
                url = "search-order?txtSearchValue=" + txtSearchValue +
                        "&txtFromDate=" + txtFromDate +
                        "&txtToDate=" + txtToDate;
            }
        } catch (SQLException e) {
            log("SendFeedbackServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("SendFeedbackServlet _ NamingException: " + e.getMessage());
        } catch (NumberFormatException e) {
            log("SendFeedbackServlet _ NumberFormatException: " + e.getMessage());
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
