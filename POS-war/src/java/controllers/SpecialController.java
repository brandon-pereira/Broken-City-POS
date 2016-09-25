/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import businessLogic.SpecialManager;
import containers.Item;
import containers.Menu;
import containers.Special;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 642123
 */
@WebServlet(name = "SpecialController", urlPatterns = {"/SpecialController"})
public class SpecialController extends HttpServlet {

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
        
        String specialNo = request.getParameter("specialNo");//int
        String name = request.getParameter("specialName");
        String dayOfWeek = request.getParameter("dow");//array list
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String discountedPrice = request.getParameter("discountPrice");//double
        String selectedItems = request.getParameter("selectedItems");
        
        String add = request.getParameter("add");
        String modify = request.getParameter("modify");
        String remove = request.getParameter("remove");
        
        if(add != null)//adding a new special
        {
            if(selectedItems != null && !selectedItems.equals("") && name != null && !name.equals("") && dayOfWeek != null && !dayOfWeek.equals("")
                    && startTime != null && !startTime.equals("") && endTime != null && !endTime.equals("") && discountedPrice != null
                    && !discountedPrice.equals(""))
            {
                try 
                {
                    if(checkSpecialName(name))
                        response.sendRedirect("admin/specialManager.jsp?error=Special name already exists");
                    else
                    {
                        StringTokenizer st = new StringTokenizer(selectedItems , ";");
                        ArrayList<Item> items = new ArrayList<Item>();
                        SpecialManager sm = SpecialManager.getInstance();
                        while(st.hasMoreTokens())
                        {
                            Item i = Menu.getInstance().getMenuItem(Integer.parseInt(st.nextToken()));
                            items.add(i);
                        }

                        st = new StringTokenizer(dayOfWeek , ";");
                        ArrayList<Integer> days = new ArrayList<Integer>();
                        while(st.hasMoreTokens())
                        {
                            days.add(Integer.parseInt(st.nextToken()));//get all the item numbers for the special
                        }

                        Special s = new Special(sm.getNextSpecialId(), items, name, days, startTime, endTime, Double.parseDouble(discountedPrice));
                        sm.addSpecial(s);
                        response.sendRedirect("admin/specialManager.jsp?success=Special added");
                    }
                } catch (NumberFormatException e)
                {
                    response.sendRedirect("admin/specialManager.jsp?warning=Please make sure all fields are filled out correctly");
                }
            }
            else
            {
                response.sendRedirect("admin/specialManager.jsp?error=Please make sure all fields are filled out");
            }
        }
        else if(modify != null)
        {
            if(specialNo != null && !specialNo.equals("") && selectedItems != null && !selectedItems.equals("") && name != null && 
                !name.equals("") && dayOfWeek != null && !dayOfWeek.equals("") && startTime != null && !startTime.equals("") && endTime != null && 
                !endTime.equals("") && discountedPrice != null && !discountedPrice.equals(""))
            {
                try 
                {
                    if(checkSpecialName(name, Integer.parseInt(specialNo)))
                        response.sendRedirect("admin/specialManager.jsp?error=Special name already exists");
                    else
                    {
                        SpecialManager sm = SpecialManager.getInstance();
                        Special s = sm.getSpecialByNumber(Integer.parseInt(specialNo));

                        StringTokenizer st = new StringTokenizer(selectedItems , ";");
                        ArrayList<Item> items = new ArrayList<Item>();
                        while(st.hasMoreTokens())
                        {
                            Item i = Menu.getInstance().getMenuItem(Integer.parseInt(st.nextToken()));
                            items.add(i);
                        }

                        st = new StringTokenizer(dayOfWeek , ";");
                        ArrayList<Integer> days = new ArrayList<Integer>();
                        while(st.hasMoreTokens())
                        {
                            days.add(Integer.parseInt(st.nextToken()));//get all the item numbers for the special
                        }

                        //modify the special
                        sm.modifySpecial(s, days, Double.parseDouble(discountedPrice), startTime, endTime, items, name);
                        response.sendRedirect("admin/specialManager.jsp?success=Special modified");
                    }
                } catch (NumberFormatException e)
                {
                    response.sendRedirect("admin/specialManager.jsp?warning=Please make sure all fields are filled out correctly");
                }
            }
            else
            {
                response.sendRedirect("admin/specialManager.jsp?error=Please make sure all fields are filled out");
            }
        }
        else if(remove != null)
        {
            if(specialNo != null & !specialNo.equals(""))
            {
                SpecialManager sm = SpecialManager.getInstance();
                Special s = sm.getSpecialByNumber(Integer.parseInt(specialNo));
                sm.removeSpecial(s);
                response.sendRedirect("admin/specialManager.jsp?success=Special removed");
            }
            else
            {
                response.sendRedirect("admin/specialManager.jsp?error=Please select a special to remove");
            }
        }
        else
        {
            response.sendRedirect("admin/specialManager.jsp?error=Oops, an error occured");
        }
        
    }
    
    /**
     * Method used for checking if a special name already exists
     * @param name - name of the special
     * @return true - if the special name already exists
     */
    private boolean checkSpecialName(String name)
    {
        SpecialManager sm = SpecialManager.getInstance();
        
        for(Special s : sm.getSpecials())
        {
            if(s.getName().equalsIgnoreCase(name))
                return true;//special name already exists
        }
        
        return false;
    }
    
    /**
     * Overloaded method to check if the special name is not taken by ANOTHER special
     * @param name - name of the special
     * @param specNo - the specialNo of the current special
     * @return true if the name already exists
     */
    private boolean checkSpecialName(String name, int specNo)
    {
        SpecialManager sm = SpecialManager.getInstance();
        
        for(Special s : sm.getSpecials())
        {
            if(s.getName().equalsIgnoreCase(name) && s.getSpecialNo() != specNo)
                return true;//special name already exists
        }
        
        return false;
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
