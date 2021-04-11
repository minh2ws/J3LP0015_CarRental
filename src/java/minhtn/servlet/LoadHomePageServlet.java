/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import minhtn.tblCar.TblCarDAO;
import minhtn.tblCar.TblCarDTO;
import minhtn.tblCategory.TblCategoryDAO;
import minhtn.tblCategory.TblCategoryDTO;
import minhtn.tblUsers.TblUsersDTO;
import minhtn.utilities.PageUtils;

/**
 *
 * @author minhv
 */
@WebServlet(name = "LoadHomePageServlet", urlPatterns = {"/LoadHomePageServlet"})
public class LoadHomePageServlet extends HttpServlet {
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
        PrintWriter out = response.getWriter();
        
        String url = HOME_PAGE;
        String role = "GUEST";
        
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblUsersDTO user = (TblUsersDTO) session.getAttribute("ACCOUNT");
                if (user != null) 
                    role = user.getRole();
            }
            
            //load car
            loadAllCar(request, role);
            request.setAttribute("DEFAULT_PAGES", 1);
            
            //load category list
            TblCategoryDAO dao = new TblCategoryDAO();
            dao.getAllCategory();
            List<TblCategoryDTO> listCategory = dao.getListCategory();
            request.setAttribute("CATEGORY_LIST", listCategory);
        } catch (SQLException e) {
            log("LoadHomePageServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("LoadHomePageServlet _ NamingException: " + e.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> functionMap = (Map<String, String>) context.getAttribute("FUNCTION_MAP");
            RequestDispatcher rd = request.getRequestDispatcher(functionMap.get(url));
            rd.forward(request, response);
            
            out.close();
        }
    }
    
    public void loadAllCar(HttpServletRequest req, String role)
        throws SQLException, NamingException {
        TblCarDAO dao = new TblCarDAO();
        dao.getAllCar(role);
        List<TblCarDTO> carList = dao.getListCar();
        req.setAttribute("CAR_LIST", carList);
        
        int totalCar = dao.getTotalCar(role);
        int totalPage = PageUtils.caculatePageRequired(totalCar, 20);
        req.setAttribute("TOTAL_PAGES", totalPage);
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
