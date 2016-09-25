/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import businessLogic.PrintManager;
import containers.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 504724
 */
@WebServlet(name = "ReceiptController", urlPatterns = {"/ReceiptController"})
public class ReceiptController extends HttpServlet
{
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
            throws ServletException, IOException, FileNotFoundException, PrintException, PrinterException 
    {
        HttpSession session = request.getSession();
        String receiptOption = request.getParameter("receipt");
        String selectedSeats = request.getParameter("selectedSeats");
        String sentFrom = request.getParameter("sentFrom");
        String sendToPrinter = request.getParameter("sendToPrinter");
        String empName = (String) session.getAttribute("empName");
        
        PrintManager printer = PrintManager.getInstance();
        if(receiptOption!=null && receiptOption.equalsIgnoreCase("Together"))
        {
            //Together option, prints all seats on one receipt
            //Table is in the session
            Receipt receipt = new Receipt();
            Menu menu = Menu.getInstance();
            Table table = (Table) session.getAttribute("table");
            ArrayList<Order> orders = table.getOrders();
            receipt.setServer(empName);
            receipt.togetherHeader(Integer.toString(table.getTableNo()));
            for(Order order:orders)
            {
                receipt.addSeat(order);
            }
            receipt.printReceiptTogether();
            
            // **Remove comment to print receipt to default printer**
            //PrinterJob printJob = PrinterJob.getPrinterJob();
            //String path = System.getProperty("user.home") + File.separator + "Documents";
            //printer.printPDF(path+"\\receiptTogether.pdf", printJob.getPrintService());
            // ******************************************************
            receipt = null;
            if(sentFrom.equalsIgnoreCase("BAR"))
            {
                //response.sendRedirect("SpeedBar.jsp?success=Receipt printed");
                response.sendRedirect("SpeedBar.jsp?error=No printer found");
            }
            else if(sentFrom.equalsIgnoreCase("MENU"))
            {
                //response.sendRedirect("Menu.jsp?success=Receipt printed");
                response.sendRedirect("Menu.jsp?error=No printer found");
            }
        }
        else if(receiptOption!=null && receiptOption.equalsIgnoreCase("Seperate"))
        {
            //Seperate option, prints all seats on seperate receipts
            //Table is in the session
            Receipt receipt = new Receipt();
            Menu menu = Menu.getInstance();
            Table table = (Table) session.getAttribute("table");
            ArrayList<Order> orders = table.getOrders();
            receipt.setServer(empName);
            for(Order order:orders)
            {
                String orderNumber = Integer.toString(order.getOrderNo());
                receipt.addSeatSeparate(order);
                receipt.printReceiptSeparate();
                // **Remove comment to print receipt to default printer**
                //PrinterJob printJob = PrinterJob.getPrinterJob();
                //String path = System.getProperty("user.home") + File.separator + "Documents";
                //printer.printPDF(path+"\\receipt"+orderNumber+".pdf", printJob.getPrintService());
                // ******************************************************
            }
            receipt = null;
            if(sentFrom.equalsIgnoreCase("BAR"))
            {
                //response.sendRedirect("SpeedBar.jsp?success=Receipt printed");
                response.sendRedirect("SpeedBar.jsp?error=No printer found");
            }
            else if(sentFrom.equalsIgnoreCase("MENU"))
            {
                //response.sendRedirect("Menu.jsp?success=Receipt printed");
                response.sendRedirect("Menu.jsp?error=No printer found");
            }
        }
        else if(sendToPrinter != null)
        {
            Receipt receipt = new Receipt();
            Menu menu = Menu.getInstance();
            Table table = (Table) session.getAttribute("table");
            ArrayList<Order> orders = table.getOrders();
            receipt.setServer(empName);
            for(Order order:orders)
            {
                String orderNumber = Integer.toString(order.getOrderNo());
                receipt.printSentOrder(order);
                // **Remove comment to print receipt to default printer**
                /*PrinterJob printJob = PrinterJob.getPrinterJob();
                String path = System.getProperty("user.home") + File.separator + "Documents";
                
                if(receipt.canPrintBar()==true)
                {
                    printer.printPDF(path+"\\barOrder"+orderNumber+".pdf", printJob.getPrintService());
                }
                if(receipt.canPrintKitchen()==true)
                {
                    printer.printPDF(path+"\\kitchenOrder"+orderNumber+".pdf", printJob.getPrintService());
                }
                receipt.setCount();*/
                // ******************************************************
            }
            receipt = null;
            response.sendRedirect("Menu.jsp?success=Order sent");
        }
        else
        {
            if(selectedSeats != null && !selectedSeats.equals(""))
            {
                //selectedSeats is a delimited String of seat Numbers, delimited by ;
                Receipt receipt = new Receipt();
                Menu menu = Menu.getInstance();
                Table table = (Table) session.getAttribute("table");
                ArrayList<Order> orders = table.getOrders();
                receipt.setServer(empName);
                StringTokenizer st = new StringTokenizer(selectedSeats,";");
                while(st.hasMoreTokens())
                {   
                    int seatNo = Integer.parseInt(st.nextToken());
                    for(Order order:orders)
                    {
                        if(seatNo == order.getSeatNo())
                        {
                            String orderNumber = Integer.toString(order.getOrderNo());
                            receipt.addSeatSeparate(order);
                            receipt.printReceiptSeparate();
                            // **Remove comment to print receipt to default printer**
                            //PrinterJob printJob = PrinterJob.getPrinterJob();
                            //String path = System.getProperty("user.home") + File.separator + "Documents";
                            //printer.printPDF(path+"\\receipt"+orderNumber+".pdf", printJob.getPrintService());
                            // ******************************************************
                        }
                    }
                }
                receipt = null;
                if(sentFrom.equalsIgnoreCase("BAR"))
                {
                    //response.sendRedirect("SpeedBar.jsp?success=Receipt printed");
                    response.sendRedirect("SpeedBar.jsp?error=No printer found");
                }
                else if(sentFrom.equalsIgnoreCase("MENU"))
                {
                    //response.sendRedirect("Menu.jsp?success=Receipt printed");
                    response.sendRedirect("Menu.jsp?error=No printer found");
                }
            }
            else
            {
                if(sentFrom.equalsIgnoreCase("BAR"))
                {
                    response.sendRedirect("SpeedBar.jsp?error=An error occured when printing");
                }
                else if(sentFrom.equalsIgnoreCase("MENU"))
                {
                    response.sendRedirect("Menu.jsp?error=An error occured when printing");
                }
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
            throws ServletException, IOException, FileNotFoundException {
        try {
            processRequest(request, response);
        } catch (PrintException ex) {
            Logger.getLogger(ReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PrinterException ex) {
            Logger.getLogger(ReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PrintException ex) {
            Logger.getLogger(ReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PrinterException ex) {
            Logger.getLogger(ReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        }
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