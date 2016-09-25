/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import brokers.OrderBroker;
import businessLogic.OrderManager;
import businessLogic.TableManager;
import containers.Extra;
import containers.Item;
import containers.Menu;
import containers.Order;
import containers.Side;
import containers.Table;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
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
@WebServlet(name = "OrderController", urlPatterns = {"/OrderController"})
public class OrderController extends HttpServlet 
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
            throws ServletException, IOException 
    {
        String addItem = request.getParameter("addItem");
        String seatNo = request.getParameter("seatNo");
        String voidItem = request.getParameter("void");
        String addSeat = request.getParameter("addSeat");
        String itemNo = request.getParameter("itemNo");
        String itemOnOrder = request.getParameter("itemOnOrder");
        String comment = request.getParameter("comment");
        String sides = request.getParameter("sides");
        String extra1 = request.getParameter("extra1");
        String extra2 = request.getParameter("extra2");
        String extra3 = request.getParameter("extra3");
        String more = request.getParameter("more");
        String moveItem = request.getParameter("moveItem");
        String newSeat = request.getParameter("newSeat");
        String adminPin = request.getParameter("adminPin");
        String splitItem = request.getParameter("splitItem");
        String selectedSeats = request.getParameter("selectedSeats");
        String close = request.getParameter("close");
        String payAmount = request.getParameter("amountPaid");
        String discountPercent = request.getParameter("discountPercent");
        String modifyItem = request.getParameter("modifyItem");
        HttpSession session;
        
        if(addItem != null && more != null)//an item with extras/sides comes in
        {
            session = request.getSession();
            OrderManager om = OrderManager.getInstance();
            OrderBroker ob = OrderBroker.getInstance();
            
            int orderNumber = om.getOrderNo((Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"));
            int itemOnOrderNo = ob.addItemOnOrder(orderNumber, Integer.parseInt(addItem));//create a new row in the database and return that new order number
            
            Menu menu = Menu.getInstance();          
            Item item = menu.getMenuItem(Integer.parseInt(addItem));//find from memory and get reference to this item
            Item newItem = new Item(item);//copy this menu item so we can customize it within the order
            
            newItem.setExtras(new ArrayList<Extra>());
            newItem.setSides(new ArrayList<Side>());
            
            if(sides != null)
            {
                if(!sides.equals(""))            //figure out what side(s) they want
                {
                    StringTokenizer st = new StringTokenizer(sides , ";");
                    while(st.hasMoreTokens())
                    {
                        String side = st.nextToken();//get the side number
                        for(int i = 0; i < item.getSides().size(); i++)//loop trough the sides of the item
                        {
                            int otherSide = item.getSides().get(i).getSideNo();
                            if(otherSide == Integer.parseInt(side))
                            {
                                int sideNo = item.getSides().get(i).getSideNo();
                                double price = item.getSides().get(i).getPrice();
                                String sideName = item.getSides().get(i).getName();
                                Side newSide = new Side(sideNo, price, sideName);//create a new side for the new order
                                newItem.getSides().add(newSide);//add the chosen side to the new item
                                break;
                            }
                        }
                    }
                }
            }
            
            ArrayList<Extra> extras = item.getExtras();//original item extras
            
            if(extra1 != null)
            {
                if(!extra1.equals(""))
                {
                    //find the extra from memory and create a copy of it and add to newItems array list of extras
                    for(int i = 0; i < extras.size(); i++)
                    {
                        int extraNo = extras.get(i).getExtraNo();
                        if(extraNo == Integer.parseInt(extra1))
                        {
                            double price = extras.get(i).getPrice();
                            String name = extras.get(i).getName();
                            String desc = extras.get(i).getDescription();
                            Extra newExtra = new Extra(extraNo, price, name, desc);//create a copy of the extra to add to the new item
                            newItem.getExtras().add(newExtra);
                            break;
                        }
                    }
                }
            }
            
            if(extra2 != null)
            {
                if(!extra2.equals(""))
                {
                    //find the extra from memory and create a copy of it and add to newItems array list of extras
                    for(int i = 0; i < extras.size(); i++)
                    {
                        int extraNo = extras.get(i).getExtraNo();
                        if(extraNo == Integer.parseInt(extra2))
                        {
                            double price = extras.get(i).getPrice();
                            String name = extras.get(i).getName();
                            String desc = extras.get(i).getDescription();
                            Extra newExtra = new Extra(extraNo, price, name, desc);//create a copy of the extra to add to the new item
                            newItem.getExtras().add(newExtra);
                            break;
                        }
                    }
                }
            }
            
            if(extra3 != null)
            {
                if(!extra3.equals(""))
                {
                    //find the extra from memory and create a copy of it and add to newItems array list of extras
                    for(int i = 0; i < extras.size(); i++)
                    {
                        int extraNo = extras.get(i).getExtraNo();
                        if(extraNo == Integer.parseInt(extra3))
                        {
                            double price = extras.get(i).getPrice();
                            String name = extras.get(i).getName();
                            String desc = extras.get(i).getDescription();
                            Extra newExtra = new Extra(extraNo, price, name, desc);//create a copy of the extra to add to the new item
                            newItem.getExtras().add(newExtra);
                            break;
                        }
                    }
                }
            }
            
            newItem.setItemOnOrderNo(itemOnOrderNo);//set the itemOnOrder number
            
            om.addItemToSeat( (Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"), newItem);//add this item to the seat
            Order o = om.getOrderFromTable((Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"));
            om.updateOrderTotal(o);
            response.sendRedirect("Menu.jsp");
        }
        else if(addItem != null)//user clicked a single item to add to the seat with no extras/sides
        {
            session = request.getSession();
            OrderManager om = OrderManager.getInstance();
            OrderBroker ob = OrderBroker.getInstance();
            
            int orderNumber = om.getOrderNo((Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"));
            int itemOnOrderNo = ob.addItemOnOrder(orderNumber, Integer.parseInt(addItem));//create a new row in the database and return that new order number
            
            Menu menu = Menu.getInstance();          
            Item item = menu.getMenuItem(Integer.parseInt(addItem));//find from memory and get reference to this item
            Item newItem = new Item(item);//copy this menu item so we can customize it within the menu
            newItem.setItemOnOrderNo(itemOnOrderNo);//set the itemOnOrder number
            newItem.setExtras(new ArrayList<Extra>());
            newItem.setSides(new ArrayList<Side>());
            
            om.addItemToSeat( (Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"), newItem);//add this item to the seat
            Order o = om.getOrderFromTable((Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"));
            om.updateOrderTotal(o);
            response.sendRedirect("Menu.jsp");
        }
        else if(itemNo != null && voidItem != null && adminPin != null)//user clicked void item button (itemNo is the pk in the db)
        {
            if(!adminPin.equals(""))
            {
                session = request.getSession();
                OrderManager om = OrderManager.getInstance();
                if(om.getManagerApproval(Integer.parseInt(adminPin)))
                {
                    if(!om.voidItemFromSeat( (Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"), Integer.parseInt(itemNo)))
                    {
                        response.sendRedirect("Menu.jsp?error=Cannot void this item twice");
                    }
                    else
                    {
                        Order oldOrder = om.getOrderFromTable((Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"));
                        om.updateOrderTotal(oldOrder);
                        response.sendRedirect("Menu.jsp");
                    }
                }
                else
                {
                    response.sendRedirect("Menu.jsp?error=Admin PIN required");
                }
            }
            else
                response.sendRedirect("Menu.jsp?warning=Please enter admin pin");
        }
        else if(itemNo != null && comment != null)//user adds comment to an item (itemNo is the pk in the db)
        {
            session = request.getSession();
            OrderManager om = OrderManager.getInstance();
            om.addCommentToItem( (Table) session.getAttribute("table"), (Integer) session.getAttribute("seatNo"), Integer.parseInt(itemNo), comment);
            response.sendRedirect("Menu.jsp");
        }
        else if(seatNo != null)//user clicks a seat
        {
            session = request.getSession();
            session.setAttribute("seatNo", Integer.parseInt(seatNo));//store in the session
            response.sendRedirect("Menu.jsp");
        }
        else if(addSeat != null)//user adds a new seat
        {
            session = request.getSession();
            Table t = (Table) session.getAttribute("table");
            OrderManager om = OrderManager.getInstance();
            om.addSeatToTable(t);//add the seat to the table
            response.sendRedirect("Menu.jsp");
        }
        else if(moveItem != null && itemOnOrder != null && newSeat != null)//wants to move an item to another seat
        {
            if(!newSeat.equals(""))
            {
                session = request.getSession();
                Table t = (Table) session.getAttribute("table");
                int currentSeat = (Integer) session.getAttribute("seatNo");
                OrderManager om = OrderManager.getInstance();
                om.moveItem(t, Integer.parseInt(newSeat), currentSeat, Integer.parseInt(itemOnOrder));
                response.sendRedirect("Menu.jsp");
            }
            else
                response.sendRedirect("Menu.jsp");
        }
        else if(modifyItem != null)
        {
            try
            {
            //This block of code will extract the item being modified from the table Object
            session = request.getSession();
            
            Table t = (Table) session.getAttribute("table");
            int currentSeat = (Integer) session.getAttribute("seatNo");
            int itemOnOrderNumber = Integer.parseInt(modifyItem);
            
            OrderManager om = OrderManager.getInstance();
            ArrayList<Side> newSides = new ArrayList<Side>();
            
            StringTokenizer st = null;
            if(sides != null && !sides.equals(""))
            {
                //Get the required information from memory
                Menu menu = Menu.getInstance();
                st = new StringTokenizer(sides,";");
                
                int newSideNo = 0;
                Side newSide = null;
                while(st.hasMoreTokens())
                {
                    newSideNo = Integer.parseInt(st.nextToken());
                    //retreive the newSide information from the menu
                    ArrayList<Side> menuSides = menu.getMenuItem(om.getItemFromOrder(t, currentSeat, itemOnOrderNumber).getItemNo()).getSides();
                    for(Side s:menuSides)
                    {
                        if(s.getSideNo() == newSideNo)
                        {
                            newSide = new Side(s.getSideNo(),s.getPrice(),s.getName());
                            break;
                        }
                    }
                    newSides.add(newSide);                   
                }             
            }
            om.setNewSides(t, currentSeat, itemOnOrderNumber, newSides);
            
            ArrayList<Extra> newExtras = new ArrayList<Extra>();
            if(extra1 != null || extra2 != null || extra3 != null)
            {
                
                Menu menu = Menu.getInstance();
                //Put all of the extras into 1 delimited String
                String extras = "";
                if(extra1 != null)
                    extras += extra1;
                if(extra2 != null)
                    extras += ";" + extra2;
                if(extra3 != null)
                    extras += ";" + extra3;
                
                st = new StringTokenizer(extras,";");
                
                int newExtraNo = 0;
                Extra newExtra = null;
                while(st.hasMoreTokens())
                {
                    newExtraNo = Integer.parseInt(st.nextToken());
                    //Get the extra information from the menu
                    ArrayList<Extra> menuExtras = menu.getMenuItem(om.getItemFromOrder(t, currentSeat, itemOnOrderNumber).getItemNo()).getExtras();
                    for(Extra e:menuExtras)
                    {
                        if(e.getExtraNo() == newExtraNo)
                        {
                            newExtra = new Extra(e.getExtraNo(), e.getPrice(), e.getName(), e.getDescription());
                            break;
                        }                         
                    }
                    newExtras.add(newExtra);
                }
            }
            om.setNewExtras(t, currentSeat, itemOnOrderNumber, newExtras);
            response.sendRedirect("Menu.jsp");
            }catch(Exception e)
            {
                response.sendRedirect("Menu.jsp?error=Oops, an error occured!");
            }
        }
        else if(splitItem != null && selectedSeats != null && itemOnOrder != null)
        {           
            if(!selectedSeats.equals(""))
            {
                session = request.getSession();
                int seatNum = (Integer) session.getAttribute("seatNo");
                Table t = (Table) session.getAttribute("table");
                OrderManager om = OrderManager.getInstance();
                Item item = om.getItemFromOrder(t, seatNum, Integer.parseInt(itemOnOrder));//get reference to the item to be split
                ArrayList<Order> splitOrders = new ArrayList<Order>();
                
                StringTokenizer st = new StringTokenizer(selectedSeats, ";");
                ArrayList<Order> orders = t.getOrders();
                while(st.hasMoreTokens())
                {
                    String seat = st.nextToken();//extract the seat number
                    for(int i = 0; i < t.getOrders().size(); i++)//find the order of that seat number
                    {
                        Order o = orders.get(i);
                        if(o.getSeatNo() == Integer.parseInt(seat))
                            splitOrders.add(o);                            
                    }                    
                }
                
                om.splitItem(splitOrders, item);
                Order oldOrder = om.getOrderFromTable(t, seatNum);
                om.updateOrderTotal(oldOrder);
            }
            
            response.sendRedirect("Menu.jsp");
        }
        else if(close != null)
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
                response.sendRedirect("Menu.jsp?error=Table still has unpaid orders");
            }
            else
            {
                tm.closeTable(t);
                response.sendRedirect("Tables.jsp?success=Table closed");
            }
        }
        else if(payAmount != null)
        {
            String paymentType = "";
            String cash = request.getParameter("cash");
            String debit = request.getParameter("debit");
            String credit = request.getParameter("credit");
            if(cash != null)
                paymentType = "Cash";
            else if(debit != null)
                paymentType = "Debit";
            else if(credit != null)
                paymentType = "Credit";
            session = request.getSession();
            OrderManager om = OrderManager.getInstance();
            int seatNum = (Integer) session.getAttribute("seatNo");
            om.acceptPayment((Table) session.getAttribute("table"), seatNum, Double.parseDouble(payAmount), paymentType);//take the order off the table & persist information to database
            response.sendRedirect("Menu.jsp");
        }
        else if(itemOnOrder != null && discountPercent != null && adminPin != null)
        {
            if(Integer.parseInt(discountPercent) < 0 || Integer.parseInt(discountPercent) > 100)
                response.sendRedirect("Menu.jsp?error=Cannot discount that percentage");
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
                    response.sendRedirect("Menu.jsp?success=Item discounted");
                }
                else
                {
                    response.sendRedirect("Menu.jsp?error=Admin PIN required");
                }
            }
        }
        else
        {
            response.sendRedirect("Menu.jsp?error=Oops, an error occured");
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
