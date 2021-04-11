/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhtn.tblUsers.TblUsersDTO;
import minhtn.utilities.GenerateCode;
import minhtn.utilities.MailUtils;

/**
 *
 * @author minhv
 */
@WebServlet(name = "SendActivationCodeServlet", urlPatterns = {"/SendActivationCodeServlet"})
public class SendActivationCodeServlet extends HttpServlet {

    private final String ACTIVE_ACCOUNT_PAGE = "active-account-page";

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

        String url = ACTIVE_ACCOUNT_PAGE;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblUsersDTO currentUser = (TblUsersDTO) session.getAttribute("ACCOUNT");
                String email = currentUser.getEmail();

                String codeValidation = GenerateCode.generateCodeValidation(6);

                //set code for validation
                session.setAttribute("CODE_VALIDATION", codeValidation);

                //send mail active
                MailUtils.sendMailActivation(email, codeValidation);
            }
        } catch (AddressException e) {
            log("SendActivationCodeServlet _ AddressException: " + e.getMessage());
        } catch (MessagingException e) {
            log("SendActivationCodeServlet _ MessagingException: " + e.getMessage());
        } finally {
            response.sendRedirect(url);
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
