/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
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
import minhtn.utilities.ReCaptchaVerify;

/**
 *
 * @author minh2ws
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String INVALID_PAGE = "invalid-page";
    private final String LOAD_HOME_PAGE_SERLVET = "LoadHomePageServlet";
    private final String LOGIN_PAGE_ERROR = "login-page";
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

        String url = INVALID_PAGE;

        boolean isVerified = false;

        try {
            String txtEmail = request.getParameter("txtEmail");
            String txtPassword = request.getParameter("txtPassword");

            TblUsersDAO dao = new TblUsersDAO();
            TblUsersDTO account = dao.checkLogin(txtEmail, txtPassword);

            if (account != null) {
                String googleCaptcharResponse = request.getParameter("g-recaptcha-response");

                //verify captchar
                isVerified = ReCaptchaVerify.verifyCaptcha(googleCaptcharResponse);

                if (!isVerified) {
                    request.setAttribute("CAPTCHAR_ERROR", "Captchar invalid!");
                    url = LOGIN_PAGE_ERROR;
                } else {
                    //create session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("ACCOUNT", account);
                    //add cookie email
                    Cookie email = new Cookie("EMAIL", txtEmail);
                    email.setMaxAge(60 * 3);
                    response.addCookie(email);
                    //add cookie password
                    Cookie password = new Cookie("PASSWORD", txtPassword);
                    password.setMaxAge(60 * 3);
                    response.addCookie(password);

                    if (account.getStatus().equals("NEW")) 
                        url = SEND_ACTIVATION_CODE_SERVLET;
                    else 
                        url = LOAD_HOME_PAGE_SERLVET;
                }
            } //end if account is existed
        } catch (MalformedURLException e) {
            log("LoginServlet _ MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            log("LoginServlet _ IOException: " + e.getMessage());
        } catch (SQLException e) {
            log("LoginServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("LoginServlet _ NamingException: " + e.getMessage());
        } finally {
            if (isVerified) {
                response.sendRedirect(url);
            } else {
                ServletContext context = request.getServletContext();
                Map<String, String> functionMap = (Map<String, String>) context.getAttribute("FUNCTION_MAP");
                RequestDispatcher rd = request.getRequestDispatcher(functionMap.get(url));
                rd.forward(request, response);
            }
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
