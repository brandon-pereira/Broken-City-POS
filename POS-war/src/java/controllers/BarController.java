/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import businessLogic.OrderManager;
import businessLogic.TableManager;
import containers.Item;
import containers.Menu;
import containers.Order;
import containers.Table;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "BarController", urlPatterns = {"/BarController"})
public class BarController extends HttpServlet {

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
        String addItem = request.getParameter("addItem");
        String itemNo = request.getParameter("itemNo");
        String voidItem = request.getParameter("void");
        String adminPin = request.getParameter("adminPin");
        String close = request.getParameter("close");
        String payAmount = request.getParameter("amountPaid");
        String discountPercent = request.getParameter("discountPercent");
        String itemOnOrder = request.getParameter("itemOnOrder");
        HttpSession session;
        
        if(addItem != null)//user adds item to order
        {
            session = request.getSession();
            OrderManager om = OrderManager.getInstance();
            
            Menu menu = Menu.getInstance();          
            Item item = menu.getMenuItem(Integer.parseInt(addItem));//find from memory and get reference to this item
            Item newItem = new Item(item);//copy this menu item so we can customize it within the speed bar
            om.assignItemOnOrderNo(newItem);//set the itemOnOrder number
            
            om.addItemToSeat( (Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"), newItem);//add this item to the seat
            Order o = om.getOrderFromTable((Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"));
            om.updateOrderTotal(o);
            response.sendRedirect("SpeedBar.jsp");
        }
        else if(itemNo != null && voidItem != null && adminPin != null)//user clicked void item button (itemNo is the pk in the db)
        {
            session = request.getSession();
            OrderManager om = OrderManager.getInstance();
            if(om.getManagerApproval(Integer.parseInt(adminPin)))//admin needs to approve void before proceeding
            {
                if(!om.voidItemFromSeat( (Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"), Integer.parseInt(itemNo)))
                {
                    response.sendRedirect("SpeedBar.jsp?error=Cannot void this item twice");
                }
                else
                {
                    Order oldOrder = om.getOrderFromTable((Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"));
                    om.updateOrderTotal(oldOrder);
                    response.sendRedirect("SpeedBar.jsp");
                }
            }
            else
            {
                response.sendRedirect("SpeedBar.jsp?error=Admin PIN required");
            }
        }
        else if(close != null)//close table
        {
            session = request.getSession();
            TableManager tm = TableManager.getInstance();
            Table t = (Table) session.getAttribute("table");
            boolean valid = true;
            ArrayList<Order> orders = t.getOrders();
            for(Order o : orders)
            {
                if(o.getOrderTotal() > 0)//as soon as a order has a total > 0 then the table still has unpaid orders
                {
                    valid = false;
                    break;
                }
            }
            
            if(!valid) //cant close the table if it still has unpaid orders
            {
                response.sendRedirect("SpeedBar.jsp?error=Table still has unpaid orders");
            }
            else
            {
                tm.clearTable(t);
                response.sendRedirect("SpeedBar.jsp?success=Table cleared");
            }
        }
        else if(payAmount != null)//pay for an item/order
        {
            String paymentType = "Cash";//speed bar only accepts cash

            session = request.getSession();
            OrderManager om = OrderManager.getInstance();
            int seatNum = (Integer) session.getAttribute("seatNo");
            om.acceptPayment((Table) session.getAttribute("table"), seatNum, Double.parseDouble(payAmount), paymentType);//take the order off the table & persist information to database
            response.sendRedirect("SpeedBar.jsp");
        }
        else if(itemOnOrder != null && discountPercent != null && adminPin != null)//discount an item
        {
            if(Integer.parseInt(discountPercent) < 0 || Integer.parseInt(discountPercent) > 100)
                response.sendRedirect("SpeedBar.jsp?error=Cannot discount that percentage");
            else
            {
                OrderManager om = OrderManager.getInstance();
                session = request.getSession();
                if(om.getManagerApproval(Integer.parseInt(adminPin)))
                {
                    Order o = om.getOrderFromTable((Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"));
                    om.discountItemFromSeat( (Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"), 
                            Integer.parseInt(itemOnOrder), Double.parseDouble(discountPercent));
                    om.updateOrderTotal(o);
                    response.sendRedirect("SpeedBar.jsp?success=Item discounted");
                }
                else
                {
                    response.sendRedirect("SpeedBar.jsp?error=Admin PIN required");
                }
            }
        }
        else
        {
            response.sendRedirect("SpeedBar.jsp?error=Oops, an error occured");
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
