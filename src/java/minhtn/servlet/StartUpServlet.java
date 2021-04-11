/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhtn.tblUsers.TblUsersDAO;
import minhtn.tblUsers.TblUsersDTO;

/**
 *
 * @author minhv
 */
@WebServlet(name = "StartUpServlet", urlPatterns = {"/StartUpServlet"})
public class StartUpServlet extends HttpServlet {

    private final String HOME_SERVLET = "home";
    private final String SEND_ACTIVATION_CODE_SERVLET = "send-activation-code-servlet";

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

        String url = HOME_SERVLET;

        try {
            //1. Get Cookies
            Cookie[] cookies = request.getCookies();
            //2. Check cookie
            if (cookies != null) {
                String email = "";
                String password = "";
                //3. Get account
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("EMAIL")) {
                        email = cookie.getValue();
                    }
                    if (cookie.getName().equals("PASSWORD")) {
                        password = cookie.getValue();
                    }
                }

                //4. Check login
                TblUsersDAO dao = new TblUsersDAO();
                TblUsersDTO dto = dao.checkLogin(email, password);

                if (dto != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("ACCOUNT", dto);
                    if (dto.getStatus().equals("NEW")) {
                        url = SEND_ACTIVATION_CODE_SERVLET;
                    } else {
                        url = HOME_SERVLET;
                    }
                }
            }
        } catch (NamingException e) {
            log("StartupServlet _ NamingException: " + e.getMessage());
        } catch (SQLException e) {
            log("StartupServlet _ SQLException: " + e.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> functionsMap = (Map<String, String>) context.getAttribute("FUNCTION_MAP");
            RequestDispatcher rd = request.getRequestDispatcher(functionsMap.get(url));
            rd.forward(request, response);
            out.close();
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
