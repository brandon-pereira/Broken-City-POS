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

/**
 *
 * @author 642123
 */
@WebServlet(name = "EmployeeController", urlPatterns = {"/EmployeeController"})
public class EmployeeController extends HttpServlet {

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
        String add = request.getParameter("add");
        String remove = request.getParameter("remove");
        String modify = request.getParameter("modify");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String verifyPassword = request.getParameter("verifyPassword");
        
        String warning = "", error = "", success = "";
        
        int active = 0;
        String empNo = request.getParameter("empNo");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String SIN = request.getParameter("SIN");
        String position = request.getParameter("position");
        String hireDate = request.getParameter("hireDate");
        String isActive = request.getParameter("isActive");
        String endDate = request.getParameter("endDate");
        
        EmployeeManager em;

        if(add != null)//user clicks add button
        {          
            if(empNo != null && !empNo.equals("") &&
            firstName != null && !firstName.equals("") &&
            lastName != null && !lastName.equals("") &&
            phoneNumber != null && !phoneNumber.equals("") &&
            address != null && !address.equals("") &&
            SIN != null && !SIN.equals("") &&
            position != null && !position.equals("") &&
            hireDate != null && !hireDate.equals(""))//this if statement checks to see that none of the fields are left blank
            {   
                //stored procedure (isValidEmp) to check if empNo exists
                if(phoneNumber.length() < 9 || phoneNumber.length() > 14)
                {
                    warning = "Phone number is invalid";
                    response.sendRedirect("admin/addEmployee.jsp?warning=" + warning);
                }
                else if(SIN.length() != 9)
                {
                    warning = "SIN number is invalid";
                    response.sendRedirect("admin/addEmployee.jsp?warning=" + warning);
                }
                else
                {   
                    if(position.equals("Admin"))//then its an admin being added
                    {
                        String adminPass = request.getParameter("adminPass");
                        String adminPass2 = request.getParameter("adminPass2");
                        
                        if(adminPass != null && !adminPass.equals("") && adminPass2 != null && !adminPass2.equals(""))
                        {
                            if(adminPass.equals(adminPass2))
                            {
                                em = EmployeeManager.getInstance();
                                Employee e = new Employee(Integer.parseInt(empNo), firstName, lastName, address, phoneNumber, 
                                                        Integer.parseInt(SIN), position, 1, hireDate, null);
                                em.addEmployee(e);

                                em.setAdminPassword(e, adminPass);
                                success = "Employee added";
                                response.sendRedirect("admin/listEmployees.jsp?success=" + success);
                            }
                            else
                            {
                                warning = "Passwords do not match";
                                response.sendRedirect("admin/addEmployee.jsp?warning=" + warning);
                            }
                        }
                        else
                        {
                            warning = "Both password fields required";
                            response.sendRedirect("admin/addEmployee.jsp?warning=" + warning);
                        }
                    }
                    else
                    {
                        em = EmployeeManager.getInstance();
                        Employee e = new Employee(Integer.parseInt(empNo), firstName, lastName, address, phoneNumber, 
                                                        Integer.parseInt(SIN), position, 1, hireDate, null);
                        em.addEmployee(e);
                                            
                        success = "Employee added";
                        response.sendRedirect("admin/listEmployees.jsp?success=" + success);
                    }

                }
            }
            else
            {
                warning = "Make sure all fields are filled out";
                response.sendRedirect("admin/addEmployee.jsp?warning=" + warning);
            }
        }
        else if(modify != null)//user clicks modify button
        {   
            
            if(empNo != null && !empNo.equals("") &&
            firstName != null && !firstName.equals("") &&
            lastName != null && !lastName.equals("") &&
            phoneNumber != null && !phoneNumber.equals("") &&
            address != null && !address.equals("") &&
            SIN != null && !SIN.equals("") &&
            position != null && !position.equals("") &&
            hireDate != null && !hireDate.equals(""))//this if statement checks to see that none of the fields are left blank
            {
                if(isActive != null)
                    active = 1;   
                
                if(phoneNumber.length() < 9 || phoneNumber.length() > 14)
                {
                    warning = "Phone number is invalid";
                    response.sendRedirect("admin/modifyEmployee.jsp?warning=" + warning + "&empNo=" + empNo);
                }
                else if(SIN.length() != 9)
                {
                    warning = "SIN number is invalid";
                    response.sendRedirect("admin/modifyEmployee.jsp?warning=" + warning + "&empNo=" + empNo);
                }
                else
                {
                    em = EmployeeManager.getInstance();
                    Employee e = em.getEmpById(Integer.parseInt(empNo));
                    em.modifyEmployee(e, firstName, lastName, phoneNumber, address, 
                                                Integer.parseInt(SIN), position, active, hireDate, endDate);
                    success = "Employee modified";
                    response.sendRedirect("admin/listEmployees.jsp?success=" + success);
                }
                
            }
            else
            {
                warning = "Make sure all fields are filled out";
                response.sendRedirect("admin/modifyEmployee.jsp?warning=" + warning + "&empNo=" + empNo);
            }
            
        }
        else if(currentPassword != null && !currentPassword.equals("") && 
                empNo != null && !empNo.equals(""))//changing an admin users password
        {
            if(newPassword != null && !newPassword.equals("") &&
                verifyPassword != null && !verifyPassword.equals("") && newPassword.equals(verifyPassword))
            {
            Employee e = new Employee();
            e.setEmpNo(Integer.parseInt(empNo));
            
            em = EmployeeManager.getInstance();
            em.modifyPassword(e,newPassword);
            
            success = "Password successfully changed";
            response.sendRedirect("admin/modifyEmployee.jsp?success=" + success + "&empNo=" + empNo);
            }
            else
            {
                warning = "Make sure all fields are filled out and passwords match";
                response.sendRedirect("admin/modifyEmployee.jsp?warning=" + warning + "&empNo=" + empNo);
            }
        }  
        else
        {
            error = "An error occured";
            response.sendRedirect("admin/listEmployees.jsp?error=" + error);
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
