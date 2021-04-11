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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import minhtn.tblUsers.TblUsersDAO;
import minhtn.tblUsers.TblUsersRegisterErrors;

/**
 *
 * @author minhv
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {
    private final String REGISTER_ERROR_PAGE = "register-page-error";
    private final String LOGIN_PAGE = "login-page";

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
        
        String url = REGISTER_ERROR_PAGE;
        
        TblUsersRegisterErrors registerError = new TblUsersRegisterErrors();

        try {
            String txtEmail = request.getParameter("txtEmail");
            String txtPassword = request.getParameter("txtPassword");
            String txtPhone = request.getParameter("txtPhone");
            String txtFullname = request.getParameter("txtFullname");
            String txtAddress = request.getParameter("txtAddress");
            String txtConfirm = request.getParameter("txtConfirm");

            boolean isErrored = false;

            
            //check user input error
            //check email format
            if (!txtEmail.matches("(\\w*\\d*)+@(\\w+\\.\\w+){1}(\\.\\w+)?")) {
                isErrored = true;
                registerError.setEmailFormatError("Wrong email format!");
            }

            //check password length
            if (txtPassword.length() < 3 || txtPassword.length() > 50) {
                isErrored = true;
                registerError.setPasswordLengthErorr("Password must from 3 to 50 characters!");
            }

            //check confirm password is match or not
            if (!txtConfirm.equals(txtPassword)) {
                isErrored = true;
                registerError.setPasswordConfirmNotMatched("Confirm password not matched!");
            }

            //check name length 
            if (txtFullname.length() < 6 || txtFullname.length() > 100) {
                isErrored = true;
                registerError.setFullnameLengthError("Full name must from 6 to 100 characters!");
            }

            //check phone format
            if (!txtPhone.matches("^[0-9]{1,10}$")) {
                isErrored = true;
                registerError.setPhoneFormatError("Phone number must be digits only!");
            }

            //check phone length
            if (txtPhone.length() < 3 || txtPassword.length() > 10) {
                isErrored = true;
                registerError.setPhoneLengthError("Phone number must from 3 to 10 digits!");
            }

            //check address length error
            if (txtAddress.length() < 3 || txtAddress.length() > 200) {
                isErrored = true;
                registerError.setAddressLengthError("Address must from 3 to 200 characters");
            }

            if (isErrored) {
                request.setAttribute("CREATE_ACCOUNT_ERRORS", registerError);
            } else {
                TblUsersDAO dao = new TblUsersDAO();
                boolean isSuccess = dao.registerAccount(txtEmail, txtPassword, txtPhone, txtFullname, txtAddress);
                
                if (isSuccess) {
                    url = LOGIN_PAGE;
                }
            }

        } catch (SQLException e) {
            log("CreateAccountServlet _ SQLException: " + e.getMessage());
            
            //check if email is existed or not
            if (e.getMessage().contains("duplicate")) {
                registerError.setEmailExisted("Email is already existed");
                request.setAttribute("CREATE_ACCOUNT_ERRORS", registerError);
            }
        } catch (NamingException e) {
            log("CreateAccountServlet _ NamingException: " + e.getMessage());
        } finally {
            ServletContext context = (ServletContext) request.getServletContext();
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
