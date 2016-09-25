/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import businessLogic.ItemManager;
import containers.Extra;
import containers.Item;
import containers.Menu;
import containers.Side;
import java.io.IOException;
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
@WebServlet(name = "ItemController", urlPatterns = {"/ItemController"})
public class ItemController extends HttpServlet {

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
        String add = request.getParameter("add");//add button
        String modify = request.getParameter("modify");//modify button
        String remove = request.getParameter("remove");//remove button
        
        //messages to pass around
        String warning = "";
        String error = "";
        String success = "";
        
        String itemNo = "";        //item number
        String itemName = "";      //name of the item
        String itemDesc = "";      //description of the item
        String basePrice = "";     //price of the item thats sold to customers
        String cost = "";          //what the company pays for this item
        String numberOfSides = ""; //# of sides that go with the item
        String itemType = "";      //main, side, beverage, appetizer
        String isActive = "";      //if the item is active or deactive
        String specials = "";       //List of special numbers for the item
        String numberOfExtras = ""; //Number of extra choices for the item
        String extra1Name = "";     //String holding the name(category) for extra 1
        String extras1 = "";         //String holding the extras for choice 1
        String extra1Price = "";    //Price of the extra 1
        String extra2Name = "";     //String holding the name(category) for extra 2
        String extras2 = "";        //String holding the extras for choice 2
        String extra2Price = "";    //Price of the extra 2
        String extra3Name = "";     //String holding the name(category) for extra 3
        String extras3 = "";        //String holding the extras for choice 3
        String extra3Price = "";    //Price of the extra 3
        
        Boolean valid = false;      //Used to pass the validity of extries
        
        if(add != null)//user clicks add item button
        {
            itemName = request.getParameter("itemName");
            itemDesc = request.getParameter("itemDesc");
            basePrice = request.getParameter("basePrice");
            cost = request.getParameter("cost");
            numberOfSides = request.getParameter("numberOfSides");
            itemType = request.getParameter("itemType");
            isActive = request.getParameter("isActive");
            specials = request.getParameter("specials");
            numberOfExtras = request.getParameter("extras");
            extra1Name = request.getParameter("extra1Name");
            extras1 = request.getParameter("extras1");
            extra1Price = request.getParameter("extra1Price");
            extra2Name = request.getParameter("extra2Name");
            extras2 = request.getParameter("extras2");
            extra2Price = request.getParameter("extra2Price");
            extra3Name = request.getParameter("extra3Name");
            extras3 = request.getParameter("extras3");
            extra3Price = request.getParameter("extra3Price");
            int itemNumber = 0;
            
            if(itemName != null && !itemName.equals("") &&
            itemDesc != null && !itemDesc.equals("") &&
            basePrice != null && !basePrice.equals("") &&
            cost != null && !cost.equals("") &&
            numberOfSides != null && !numberOfSides.equals("") &&
            itemType != null && !itemType.equals("") &&
            numberOfExtras != null && !numberOfExtras.equals(""))//this if statement checks to see that none of the fields are left blank
            {   
                try{
                    Item item = new Item(0, itemName, itemDesc, Double.parseDouble(basePrice), Double.parseDouble(cost), 
                                        Integer.parseInt(numberOfSides), Integer.parseInt(numberOfExtras), itemType,
                                        1, new ArrayList<Side>(), new ArrayList<Extra>());
                    if(isActive == null)
                        item.setIsActive(0);
                    //Set the extras for the Item into the array for extras
                    ArrayList<Extra> extras = new ArrayList<Extra>();
                    //Delimite first array of extras, if not empty string
                    if(!extras1.equals(""))
                    {
                        ArrayList extraList = delimiteToArray(extras1);
                        for(int i=0;i<extraList.size();i++)
                        {
                            extras.add(new Extra(0,Double.parseDouble(extra1Price), (String) extraList.get(i), extra1Name));
                        }
                    }
                    if(!extras2.equals(""))
                    {
                        ArrayList extraList = delimiteToArray(extras2);
                        for(int i=0;i<extraList.size();i++)
                        {
                            extras.add(new Extra(0,Double.parseDouble(extra2Price), (String) extraList.get(i), extra2Name));
                        }
                    }
                    if(!extras3.equals(""))
                    {
                        ArrayList extraList = delimiteToArray(extras3);
                        for(int i=0;i<extraList.size();i++)
                        {
                            extras.add(new Extra(0,Double.parseDouble(extra3Price), (String) extraList.get(i), extra3Name));
                        }
                    }
                    item.setExtras(extras);
                    ItemManager im = ItemManager.getInstance();
                    itemNumber = im.addItem(item);
                //Changes need to be logged!!!
                }
                catch(NumberFormatException e)
                {

                    error = "Please make sure all fields are valid entries";
                    response.sendRedirect("admin/addItem.jsp?error=" + error);
                    return;
                }
                //redirect
                success = "Item added";
                response.sendRedirect("admin/modifyItem.jsp?success=" + success + "&itemNo=" + itemNumber);
            }
            else
            {
                warning = "Make sure all fields are filled out";
                response.sendRedirect("admin/addItem.jsp?warning=" + warning);
            }
        }
        else if(modify != null)//user clicks modify button
        {
            itemNo = request.getParameter("itemNo");
            itemName = request.getParameter("itemName");
            itemDesc = request.getParameter("itemDesc");
            basePrice = request.getParameter("basePrice");
            cost = request.getParameter("cost");
            numberOfSides = request.getParameter("numberOfSides");
            itemType = request.getParameter("itemType");
            isActive = request.getParameter("isActive");
            specials = request.getParameter("specials");
            numberOfExtras = request.getParameter("extras");
            extra1Name = request.getParameter("extra1Name");
            extras1 = request.getParameter("extras1");
            extra1Price = request.getParameter("extra1Price");
            extra2Name = request.getParameter("extra2Name");
            extras2 = request.getParameter("extras2");
            extra2Price = request.getParameter("extra2Price");
            extra3Name = request.getParameter("extra3Name");
            extras3 = request.getParameter("extras3");
            extra3Price = request.getParameter("extra3Price");
            
            if(itemNo != null && !itemNo.equals("") &&
            itemName != null && !itemName.equals("") &&
            itemDesc != null && !itemDesc.equals("") &&
            basePrice != null && !basePrice.equals("") &&
            cost != null && !cost.equals("") &&
            numberOfSides != null && !numberOfSides.equals("") &&
            itemType != null && !itemType.equals("") &&
            isActive != null && !isActive.equals("") &&
            numberOfExtras != null && !numberOfExtras.equals(""))//this if statement checks to see that none of the fields are left blank
            {   
                //Get instance of the Menu
                Menu menu = Menu.getInstance();
                //Get the Item being modified from the Menu
                try{
                    Item item = menu.getMenuItem(Integer.parseInt(itemNo));
                    //Make any changes to the Item
                    item.setItemName(itemName);
                    item.setItemDesc(itemDesc);
                    item.setBasePrice(Double.parseDouble(basePrice));
                    item.setCost(Double.parseDouble(cost));
                    item.setNumberOfSides(Integer.parseInt(numberOfSides));
                    item.setNumberOfExtras(Integer.parseInt(numberOfExtras));
                    item.setItemType(itemType);
                    if(isActive.equals("on"))
                        item.setIsActive(1);
                    else
                        item.setIsActive(0);
                    //Set the extras for the Item into the array for extras
                    ArrayList<Extra> extras = new ArrayList<Extra>();
                    //Delimite first array of extras, if not empty string
                    if(!extras1.equals(""))
                    {
                        ArrayList extraList = delimiteToArray(extras1);
                        for(int i=0;i<extraList.size();i++)
                        {
                            extras.add(new Extra(0,Double.parseDouble(extra1Price), (String) extraList.get(i), extra1Name));
                        }
                    }
                    if(!extras2.equals(""))
                    {
                        ArrayList extraList = delimiteToArray(extras2);
                        for(int i=0;i<extraList.size();i++)
                        {
                            extras.add(new Extra(0,Double.parseDouble(extra2Price), (String) extraList.get(i), extra2Name));
                        }
                    }
                    if(!extras3.equals(""))
                    {
                        ArrayList extraList = delimiteToArray(extras3);
                        for(int i=0;i<extraList.size();i++)
                        {
                            extras.add(new Extra(0,Double.parseDouble(extra3Price), (String) extraList.get(i), extra3Name));
                        }
                    }
                    item.setExtras(extras);
                    
                    ItemManager im = ItemManager.getInstance();
                    im.modifyItem(item);
                //Changes need to be logged!!!
                }
                catch(NumberFormatException e)
                {
                    e.printStackTrace();
                    error = "Please make sure all fields are valid entries";
                    response.sendRedirect("admin/modifyItem.jsp?error=" + error + "&itemNo=" + itemNo);
                    return;
                }
                //redirect
                success = "Item modified";
                response.sendRedirect("admin/modifyItem.jsp?success=" + success + "&itemNo=" + itemNo);
            }
            else
            {
                warning = "Make sure all fields are filled out";
                response.sendRedirect("admin/modifyItem.jsp?warning=" + warning + "&itemNo=" + itemNo);
            }
        }
        else if(remove != null)//user clicks remove button
        {
            try
            {
                itemNo = request.getParameter("itemNo");
                ItemManager im = ItemManager.getInstance();
                im.removeItem(Integer.parseInt(itemNo));
                //redirect
                success = "Item deleted";
                response.sendRedirect("admin/itemManager.jsp?success=" + success);
            }
            catch(Exception e)
            {
                error = "An error occured while deleting";
                response.sendRedirect("admin/itemManager.jsp?error=" + error);
            }
        }
    }
    /**
     * Method which will accept a delimited String in and return an arraylist
     * Pre-condition: String is delimited with a semi-colon ;
     * @param s
     * @return ArrayList Objects delimited from String
     */
    private ArrayList delimiteToArray(String s)
    {
        ArrayList list = new ArrayList();
        
        StringTokenizer st = new StringTokenizer(s, ";");
        
        while(st.hasMoreTokens())
            list.add(st.nextToken());
        
        return list;
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
