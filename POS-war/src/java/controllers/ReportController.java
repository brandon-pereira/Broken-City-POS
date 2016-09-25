/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import brokers.AdminBroker;
import containers.Report;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 532485
 */
@WebServlet(name = "ReportController", urlPatterns = {"/ReportController"})
public class ReportController extends HttpServlet {
    
    
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
        
        String empNo = request.getParameter("empNo");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String empHours = request.getParameter("empHours");
        String getOrders = request.getParameter("getOrders");
        String payments = request.getParameter("payments");
        String paymentType = request.getParameter("paymentType");
        String orderViewer = request.getParameter("orderViewer");
        String orderNo = request.getParameter("orderNo");
        String sent = request.getParameter("sent");
        String thisHour="";
        
        if(empHours!=null && empHours.equalsIgnoreCase("") && empNo!=null && !empNo.equalsIgnoreCase("")
           && fromDate!=null && !fromDate.equalsIgnoreCase("")
           && toDate!=null && !toDate.equalsIgnoreCase(""))
        {
            AdminBroker am = AdminBroker.getInstance();
            Report rep = new Report();
            thisHour = am.getEmployeeHours(Integer.valueOf(empNo), fromDate, toDate);
            ArrayList<String> entry = am.getEmployeeInfo(empNo);
            
            for(int i=0;i<entry.size();i++)
            {
                rep.setEmpno(entry.get(0));
                rep.setFname(entry.get(1));
                rep.setLname(entry.get(2));
                rep.setAddress(entry.get(3));
                rep.setPhone(entry.get(4));
                rep.setPosition(entry.get(6));
            }
            rep.generateHours(thisHour,fromDate,toDate);
            String path = System.getProperty("user.home") + File.separator + "Documents";
            response.sendRedirect("admin/reports/emphours.jsp?success=Report generated - "
                    + "EmployeeReport can be found under "+path);
        }
        else if(getOrders!=null && getOrders.equalsIgnoreCase("")
           && fromDate!=null && !fromDate.equalsIgnoreCase("")
           && toDate!=null && !toDate.equalsIgnoreCase(""))
        {
            AdminBroker am = AdminBroker.getInstance();
            Report rep = new Report();
            ArrayList<String> entry = am.getOrderByDate(fromDate, toDate);

            rep.generateOrderByDate(entry,fromDate,toDate);
            String path = System.getProperty("user.home") + File.separator + "Documents";
            response.sendRedirect("admin/reports/getOrders.jsp?success=Report generated - "
                    + "DateOrderReport can be found under "+path);
        }
        else if(payments!=null && payments.equalsIgnoreCase("") && paymentType!=null && !paymentType.equalsIgnoreCase("")
           && fromDate!=null && !fromDate.equalsIgnoreCase("")
           && toDate!=null && !toDate.equalsIgnoreCase(""))
        {
            AdminBroker am = AdminBroker.getInstance();
            Report rep = new Report();
            ArrayList<String> entry = am.getOrderByPayment(paymentType, fromDate, toDate);
            
            rep.generateOrderByPayment(paymentType, entry, fromDate, toDate);
            
            String path = System.getProperty("user.home") + File.separator + "Documents";
            response.sendRedirect("admin/reports/payments.jsp?success=Report generated - "
                    + "PayTypeReport can be found under "+path);
        }
        else if(orderViewer!=null && orderViewer.equalsIgnoreCase("") && orderNo!=null && !orderNo.equalsIgnoreCase(""))
        {
            AdminBroker am = AdminBroker.getInstance();
            Report rep = new Report();
            ArrayList<String> entry = am.getOrderItems(orderNo);
            
            rep.generateOrderItem(orderNo, entry);
            
            String path = System.getProperty("user.home") + File.separator + "Documents";
            response.sendRedirect("admin/reports/orderViewer.jsp?success=Report generated - "
                    + "OrderReport can be found under "+path);
        }
        else
        {
            if(sent.equalsIgnoreCase("e1"))
            {
                response.sendRedirect("admin/reports/emphours.jsp?error=Report generation failed");
            }
            else if(sent.equalsIgnoreCase("e2"))
            {
                response.sendRedirect("admin/reports/getOrders.jsp?error=Report generation failed");
            }
            else if(sent.equalsIgnoreCase("e3"))
            {
                response.sendRedirect("admin/reports/payments.jsp?error=Report generation failed");
            }
            else
            {
                response.sendRedirect("admin/reports/orderViewer.jsp?error=Report generation failed");
            }
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
