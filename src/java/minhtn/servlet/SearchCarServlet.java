/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import minhtn.tblCar.TblCarDAO;
import minhtn.tblCar.TblCarDTO;
import minhtn.tblCategory.TblCategoryDAO;
import minhtn.tblCategory.TblCategoryDTO;
import minhtn.utilities.PageUtils;

/**
 *
 * @author minhv
 */
@WebServlet(name = "SearchCarServlet", urlPatterns = {"/SearchCarServlet"})
public class SearchCarServlet extends HttpServlet {

    private final String HOME_PAGE = "home-page";

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

        String url = HOME_PAGE;

        try {
            String searchValue = request.getParameter("txtSearch");
            String searchCategory = request.getParameter("cmbCategory");
            String rentalDate = request.getParameter("txtRentalDate");
            String returnDate = request.getParameter("txtReturnDate");
            String amountValue = request.getParameter("txtAmount");
            String pageNumber = request.getParameter("page");
            //default page
            int page = 1;
            //default amount;
            int amount = 1;

            if (pageNumber != null) {
                page = Integer.parseInt(pageNumber);
            }
            if (!amountValue.trim().isEmpty()) {
                amount = Integer.parseInt(amountValue);
            }

            doSearch(request, searchValue, searchCategory, rentalDate, returnDate, page, amount);
        } catch (SQLException e) {
            log("SearchCarServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("SearchCarServlet _ NamingException: " + e.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> functionsMap = (Map<String, String>) context.getAttribute("FUNCTION_MAP");
            RequestDispatcher rd = request.getRequestDispatcher(functionsMap.get(url));
            rd.forward(request, response);
        }
    }

    private void doSearch(HttpServletRequest req, String searchValue,
            String searchCategory, String rentalDate, String returnDate, int page,
            int amount)
            throws SQLException, NamingException {
        TblCarDAO dao = new TblCarDAO();
        //search car
        dao.searchCar(searchValue, searchCategory, page, amount);
        Map<String, TblCarDTO> mapCar = dao.getMapCar();
        if (mapCar != null) {
            //search car rental from rental date to return date
            dao.getCarRented(rentalDate, returnDate);
            Map<String, Integer> mapCarRented = dao.getMapCarRented();
            if (mapCarRented != null) {
                Set<String> carID = mapCarRented.keySet();
                for (String car : carID) {
                    if (mapCar.get(car) != null) {
                        TblCarDTO dto = mapCar.get(car);
                        int currentQuantity = dto.getQuantity() - mapCarRented.get(car);

                        if (currentQuantity < amount) {
                            mapCar.remove(car);
                        } else {
                            mapCar.get(car).setQuantity(currentQuantity);
                        }
                    } //end if car is existed
                } //end for of update quantity
            } //end if list car rented is existed

            //load car in map from list
            List<TblCarDTO> listCar = new ArrayList<>();
            Iterator<String> iter = mapCar.keySet().iterator();
            while (iter.hasNext()) {
                listCar.add(mapCar.get(iter.next()));
            }
            
            //sort list descending by year
            Collections.sort(listCar, (TblCarDTO car1, TblCarDTO car2) -> car2.getYear().compareTo(car1.getYear()));
            req.setAttribute("CAR_LIST", listCar);
        } //end if map car is existed         

        int totalCar = dao.getTotalCarSearched(searchValue, searchCategory, amount);
        int totalPage = PageUtils.caculatePageRequired(totalCar, 20);
        req.setAttribute("TOTAL_PAGES", totalPage);

        //load category list
        TblCategoryDAO category = new TblCategoryDAO();
        category.getAllCategory();
        List<TblCategoryDTO> listCategory = category.getListCategory();
        req.setAttribute("CATEGORY_LIST", listCategory);
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
