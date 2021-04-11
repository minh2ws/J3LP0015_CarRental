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
import minhtn.feedback.FeedBackDAO;
import minhtn.feedback.FeedBackDTO;
import minhtn.tblCar.TblCarDAO;
import minhtn.tblCar.TblCarDTO;

/**
 *
 * @author minhv
 */
@WebServlet(name = "ViewCarDetailServlet", urlPatterns = {"/ViewCarDetailServlet"})
public class ViewCarDetailServlet extends HttpServlet {

    private final String DETAIL_PAGE = "car-detail-page";

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

        String url = DETAIL_PAGE;

        try {
            String carId = request.getParameter("carId");
            String txtRentalDate = request.getParameter("txtRentalDate");
            String txtReturnDate = request.getParameter("txtReturnDate");
            
            TblCarDAO dao = new TblCarDAO();
            TblCarDTO car = dao.getCarDetail(carId);

            //get car rented for minus quantity to display
            dao.getCarRented(txtRentalDate, txtReturnDate);
            Map<String, Integer> mapCarRented = dao.getMapCarRented();
            if (mapCarRented != null) {
                if (mapCarRented.containsKey(car.getCarID())) {
                    //calculate salary
                    int currentQuantity = car.getQuantity() - mapCarRented.get(car.getCarID());
                    //set quantity
                    car.setQuantity(currentQuantity);
                }
            }

            request.setAttribute("CAR", car);

            //load all feedback of car
            FeedBackDAO feedbackDAO = new FeedBackDAO();
            feedbackDAO.getAllFeedback(carId);
            List<FeedBackDTO> listFeedback = feedbackDAO.getListFeedback();
            if (listFeedback != null) {
                request.setAttribute("FEEDBACK_LIST", listFeedback);
            }

        } catch (SQLException e) {
            log("ViewCarDetailServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("ViewCarDetailServlet _ NamingException: " + e.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> functionsMap = (Map<String, String>) context.getAttribute("FUNCTION_MAP");
            RequestDispatcher rd = request.getRequestDispatcher(functionsMap.get(url));
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
