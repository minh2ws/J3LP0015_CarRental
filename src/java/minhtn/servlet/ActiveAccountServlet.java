/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
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
import minhtn.tblUsers.TblUsersDAO;
import minhtn.tblUsers.TblUsersDTO;
import minhtn.tblUsers.TblUsersRegisterErrors;

/**
 *
 * @author minhv
 */
@WebServlet(name = "ActiveAccountServlet", urlPatterns = {"/ActiveAccountServlet"})
public class ActiveAccountServlet extends HttpServlet {

    private final String ACTIVE_ERROR_PAGE = "active-account-page";
    private final String HOME_SERVLET = "home";

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

        String url = ACTIVE_ERROR_PAGE;

        try {
            String txtActiveCode = request.getParameter("txtActiveCode");
            TblUsersRegisterErrors userError = new TblUsersRegisterErrors();

            HttpSession session = request.getSession(false);
            if (session != null) {
                String activeCode = (String) session.getAttribute("CODE_VALIDATION");
                if (activeCode.equals(txtActiveCode)) {
                    TblUsersDTO dto = (TblUsersDTO) session.getAttribute("ACCOUNT");

                    TblUsersDAO dao = new TblUsersDAO();
                    dao.updateStatusAccount(dto.getEmail());

                    url = HOME_SERVLET;
                } else {
                    userError.setActiveCodeError("Wrong code error!");
                    request.setAttribute("CREATE_ACCOUNT_ERRORS", userError);
                }
            }
        } catch (SQLException e) {
            log("ActiveAccountServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("ActiveAccountServlet _ NamingException: " + e.getMessage());
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
