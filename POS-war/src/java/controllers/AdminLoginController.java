/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import brokers.EmployeeBroker;
import businessLogic.EmployeeManager;
import containers.Employee;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 642123
 */
@WebServlet(name = "AdminLoginController", urlPatterns = {"/AdminLoginController"})
public class AdminLoginController extends HttpServlet {

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
            throws ServletException, IOException 
    {
        String userID = request.getParameter("userID");
        String password = request.getParameter("password");
        String logout = request.getParameter("logout");
        
        HttpSession session;
        String message = "";
        
        if(logout != null)//user clicks logout
        {
            //destroy the session and redirect to login page
            session = request.getSession();
            session.invalidate();
            response.sendRedirect("admin/login.jsp?success=Logged out");
        }
        else if(userID != null && !userID.equals("") && password != null && !password.equals(""))//user logs in
        {
            EmployeeManager em = EmployeeManager.getInstance();
            Employee emp = em.getEmpById(Integer.parseInt(userID));
            //validate username
            try 
            {
                if(em.validateAdminUser(emp, password))//return true if the password matches the userID
                {
                    session = request.getSession();
                    session.setAttribute("isValid", true);
                    response.sendRedirect("admin/index.jsp");
                }
                else
                {
                    message = "Invalid username or password";
                    response.sendRedirect("admin/login.jsp?error=" + message);
                } 
            }
            catch (NumberFormatException e)
            {
                message = "Invalid Username";
                response.sendRedirect("admin/login.jsp?warning=" + message);
            }         
        }
        else
        {
            message = "Both fields are required!";
            response.sendRedirect("admin/login.jsp?warning=" + message);
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
