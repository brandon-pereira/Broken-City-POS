/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuHelper;

import containers.Item;
import containers.Menu;
import containers.Order;
import containers.Table;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author 632794
 */
public class SpeedBarHelper {
    /**
     * Fetches all Speed Bar items
     * @return html String of speed bar items
     */
    public String getAllItems()
    {
        Menu menu = Menu.getInstance();
        ArrayList<Item> items = menu.getMenuItems();
        String html = "";
        for(int i = 0; i < items.size(); i++)
        {
            Item item = items.get(i);
            if(item.getItemType().equalsIgnoreCase("Speed Bar"))
                html += "<a href='BarController?addItem="+item.getItemNo()+"'><h1>"+item.getItemName()+"</h1><h2>"+item.getItemDesc()+"</h2></a>";
        }
        return html;
    }
    
    /**
     * Gets a tables receipt in a Speed Bar format. 
     * @param t as current table object
     * @return html String representing receipt.
     */
    public String getReceipt(Table t)
    {
        String html = "";
        ArrayList<Order> orders = t.getOrders();
        DecimalFormat dec = new DecimalFormat("##,##0.00");
        for(int i = 0; i < orders.size(); i++)
        {
            Order o = orders.get(i);
            ArrayList<Item> items = o.getItems();
            for(int j = 0; j < items.size(); j++)
            {
                Item item = items.get(j);
                html += "<li data-itemNo='"+item.getItemOnOrderNo()+"'>"+item.getItemName()+"<span>"+dec.format(item.getBasePrice())+"</span></li>";
            }
        }
        return html;
    }
    
    /**
     * Gets the total of all items on Speed Bar receipt.
     * @param t as current table object
     * @return total cost of all items
     */
    public String getTotal(Table t)
    {
        double tablePrice = 0;
        ArrayList<Order> orders = t.getOrders();//returns an array of orders that the table has
        for(int i = 0; i < orders.size(); i++)
        {
            Order o = orders.get(i);//get a single order from the list
            tablePrice += o.getOrderTotal();
        }
        DecimalFormat dec = new DecimalFormat("##,##0.00");
        return String.valueOf(dec.format(tablePrice));
    }
}
