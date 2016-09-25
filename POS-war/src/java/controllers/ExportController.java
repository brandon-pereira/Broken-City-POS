/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import businessLogic.ExportManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 504724
 */
@WebServlet(name = "ExportController", urlPatterns = {"/ExportController"})
public class ExportController extends HttpServlet {

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
        
        String fileName = request.getParameter("fileName");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");
        
        if(fileName != null)
        {
            if(fileName.equals(""))
            {
                fileName = "export.csv";
            }
            else if(fileName.length() > 4 && !fileName.substring(fileName.length()-4, fileName.length()-1).equalsIgnoreCase(".csv"))
            {
                fileName += ".csv";
            }
            else if(fileName.length() < 4)
            {
                fileName += ".csv";    
            }
            
            ExportManager em = new ExportManager();          
            try
            {
                em.exportData(fileName, dateFrom, dateTo);
                response.sendRedirect("admin/index.jsp?success=File saved as '"+fileName+"'.");
            } catch(IOException e)
            {
                e.printStackTrace();
                response.sendRedirect("admin/index.jsp?error=An error has occured. Please make sure that a previous export is not open.");
            }
        }
        else
        {
            response.sendRedirect("admin/index.jsp?error=An error has occured.");
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
