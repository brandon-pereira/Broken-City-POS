/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import brokers.EmployeeBroker;
import brokers.ShiftBroker;
import businessLogic.EmployeeManager;
import businessLogic.OrderManager;
import businessLogic.ShiftManager;
import businessLogic.TableManager;
import containers.Employee;
import containers.Table;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
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
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, NamingException, SQLException 
    {
        String error = "", 
               warning = "",
               success = "";
        String userID = request.getParameter("userID");
        String accessMenu = request.getParameter("accessMenu");
        String clockIn = request.getParameter("clockIn");
        String clockOut = request.getParameter("clockOut");
        String logout = request.getParameter("logout");
        String msg = request.getParameter("msg");
        
        EmployeeManager em;
        ShiftManager sm;
        
        if(logout != null)
        {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("LoginScreen.jsp?success=Logged out");
        }
        else if(logout != null && msg != null)
        {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("LoginScreen.jsp?success=" + msg);
        }
        else if(userID == null || userID.length() != 4)
        {
            error = "Please enter valid user ID";
            response.sendRedirect("LoginScreen.jsp?error=" + error);
        }
        else if(accessMenu != null)//user clicks access menu
        {
            em = EmployeeManager.getInstance();
            Employee e = em.getEmpById(Integer.parseInt(userID));
            if(em.validateEmpNo(e))//make sure the userID is valid using employee manager
            {
                sm = ShiftManager.getInstance();
                if(sm.isClockedIn(e))//check to see if employee is clocked in
                {
                    int empType = em.getPosition(e);
                    HttpSession session = request.getSession();//TODO + Update when Employee object implemented
                    session.setAttribute("empName", e.getFirstName() + " " + e.getLastName().substring(0, 1));
                    session.setAttribute("serverNo", e.getEmpNo());
                    if(empType == 1)//then it is server
                    {
                        //redirect to menu
                        response.sendRedirect("Tables.jsp");
                    }
                    else if(empType == 2)//then it is bartender
                    {
                        //redirect to speed bar
                        TableManager tm = TableManager.getInstance();
                        OrderManager om = OrderManager.getInstance();
                        Table t = tm.getSpeedBarTable(e.getEmpNo());//pass in the user id so we know what server is servicing the table
                        //put table information in the session for speedbar
                        session.setAttribute("table", t);
                        session.setAttribute("tableNo", t.getTableNo());
                        session.setAttribute("seatNo", 1);
                        //om.addSeatToTable(t, true);//add default seat (1) to this table
                        response.sendRedirect("SpeedBar.jsp");
                    }
                    else if(empType == 3)//then admin
                    {
                        session.setAttribute("admin",true);
                        response.sendRedirect("Tables.jsp");
                    }
                    else
                    {
                        warning = "You don't have priveleges to access the menu";
                        response.sendRedirect("LoginScreen.jsp?warning=" + warning);
                    }
                }  
                else
                {
                    error = "You are not clocked in";
                    response.sendRedirect("LoginScreen.jsp?error=" + error);
                }
            }
            else
            {
                error = "Invalid User ID";
                response.sendRedirect("LoginScreen.jsp?error=" + error);
            }
        }
        else if(clockIn != null)//user clicks clock in
        {
            //make sure the userID is valid using employeeBroker
            em = EmployeeManager.getInstance();
            Employee e = em.getEmpById(Integer.parseInt(userID));
            if(em.validateEmpNo(e))//make sure the userID is valid using employeeBroker
            {
                sm = ShiftManager.getInstance();
                //if it is then shiftBroker to clock them in
                if(!sm.isActive(e))//employee is not active
                {
                    error = "Invalid User ID";
                    response.sendRedirect("LoginScreen.jsp?error=" + error);
                }
                else if(sm.isClockedIn(e))
                {
                    warning = "You are already clocked in";
                    response.sendRedirect("LoginScreen.jsp?warning=" + warning);
                }
                else
                {
                    //clock user in using shiftbroker
                    sm.clockUserIn(e);
                    success = "Clocked in";
                    response.sendRedirect("LoginScreen.jsp?success=" + success);
                }
            }
            else
            {
                error = "Invalid User ID";
                response.sendRedirect("LoginScreen.jsp?error=" + error);
            }
        }
        else if(clockOut != null)//user clicks clock out
        {
            em = EmployeeManager.getInstance();
            Employee e = em.getEmpById(Integer.parseInt(userID));
            if(em.validateEmpNo(e))//make sure the userID is valid using employeeBroker
            {
                sm = ShiftManager.getInstance();
                //if it is then shiftBroker to clock them out
                if(!sm.isClockedIn(e))
                {
                    warning = "You are already clocked out";
                    response.sendRedirect("LoginScreen.jsp?warning=" + warning);
                }
                else
                {
                    //clock user in using shiftbroker
                    sm.clockUserOut(e);
                    success = "Clocked out";
                    response.sendRedirect("LoginScreen.jsp?success=" + success);
                }
            }
            else
            {
                error = "Invalid User ID";
                response.sendRedirect("LoginScreen.jsp?error=" + error);
            }
        }
        else
        {
            error = "An error occured";
            response.sendRedirect("LoginScreen.jsp?error=" + error);
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
