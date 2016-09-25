/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import brokers.OrderBroker;
import containers.Table;
import businessLogic.OrderManager;
import businessLogic.TableManager;
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
@WebServlet(name = "TableController", urlPatterns = {"/TableController"})
public class TableController extends HttpServlet {
    private static final int MAX_NUMBER_OF_TABLES = 99;
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
        String mergeTable = request.getParameter("mergeTable");
        String tableNo = request.getParameter("tableNo");
        String changeServer = request.getParameter("changeServer");
        String persist = request.getParameter("pTable");
        String from = request.getParameter("tableFrom");
        String to = request.getParameter("tableTo");

        if(tableNo != null) //user clicks a table
        {
            if(Integer.parseInt(tableNo) == 0)//if user clicks quick sale
            {               
                OrderManager om = OrderManager.getInstance();
                HttpSession session = request.getSession();
                Table t = new Table(Integer.parseInt(tableNo));//create a new table
                t.setStatus(true);//make the table active
                t.setServerNo( (Integer) session.getAttribute("serverNo"));
                
                session.setAttribute("table", t);//put table in the session
                session.setAttribute("tableNo", t.getTableNo());
                session.setAttribute("seatNo", (Integer) 1);
                response.sendRedirect("Menu.jsp?");
            }
            else
            {
            
                if(Integer.parseInt(tableNo) > MAX_NUMBER_OF_TABLES)
                {
                    response.sendRedirect("Tables.jsp?error=Table " + tableNo + " is out of range");
                }
                else
                {
                    Table t = null;
                    OrderManager om = OrderManager.getInstance();
                    TableManager tm = TableManager.getInstance();
                    ArrayList<Table> tables = new ArrayList<Table>();
                    tables = tm.getTables();
                    for(int i = 0; i < tables.size(); i++)
                    {
                        if(tables.get(i).getTableNo() == Integer.parseInt(tableNo))//if the table already exists
                        {
                            t = tables.get(i);//get a reference to it and put it into the session
                            break;
                        }
                    }

                    if(t == null)//if the table wasnt found in the above loop, create a new table object
                    {
                        HttpSession session = request.getSession();
                        t = new Table(Integer.parseInt(tableNo));//create a new table
                        t.setStatus(true);//make the table active
                        t.setServerNo( (Integer) session.getAttribute("serverNo"));
                        om.addSeatToTable(t);//add default seat (1) to this table
                        tm.getTables().add(t);//add table to the arraylist of current tables
                    }

                    HttpSession session = request.getSession();
                    session.setAttribute("table", t);//put table in the session
                    session.setAttribute("tableNo", t.getTableNo());
                    session.setAttribute("seatNo", (Integer) 1);
                    response.sendRedirect("Menu.jsp?");
                }
            }
        }
        else if(mergeTable != null && from != null && to != null)//clicks merge table button
        {
            //put the from table into the to table
            TableManager tm = TableManager.getInstance();
            Table fromTable = tm.getTable(Integer.parseInt(from));
            Table toTable = tm.getTable(Integer.parseInt(to));
            tm.mergeTables(fromTable, toTable);
            response.sendRedirect("Tables.jsp");
        }
        else if(persist != null)//persist the table
        {
            HttpSession session = request.getSession();
            OrderBroker ob = OrderBroker.getInstance();
            if((Table) session.getAttribute("table") == null)
            {
                response.sendRedirect("Tables.jsp?error=Failed to save specified table");
            }
            else
            {
                ob.persistTable( (Table) session.getAttribute("table"));
                response.sendRedirect("Tables.jsp");
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
