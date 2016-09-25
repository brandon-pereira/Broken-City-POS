/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminHelper;

import businessLogic.SpecialManager;
import containers.Item;
import containers.Menu;
import containers.Special;
import java.util.ArrayList;

/**
 *
 * @author 642123
 */
public class SpecialsHelper
{
    /**
     * Lists all items as an HTML string
     * @return an HTML string of all specials
     */
    public String listAllSpecials()
    {
        String html = "";
        SpecialManager sm = SpecialManager.getInstance();
        ArrayList<Special> specials = sm.getSpecials();
        for (Special s : specials) 
            html += "<li data-id="+ s.getSpecialNo() +">" + s.getName() + "</li>";
        return html;
    }
    
    /**
     * Returns a specials name
     * @param specialNo as the special number to fetch
     * @return the specials name
     */
    public String getSpecialName(String specialNo)
    {
        SpecialManager sm = SpecialManager.getInstance();
        return sm.getSpecialByNumber(Integer.parseInt(specialNo)).getName();
    }
        
    /**
     * Returns a specials  discounted price
     * @param specialNo as the special number to fetch
     * @return the specials discounted price
     */
    public String getDiscountedPrice(String specialNo)
    {
        SpecialManager sm = SpecialManager.getInstance();
        return sm.getSpecialByNumber(Integer.parseInt(specialNo)).getDiscountedPrice()+"";
    }
        
    /**
     * Returns a specials days of week as a delimited string seperated by semicolons.
     * @param specialNo as the special number to fetch
     * @return the specials delimited by semicolons.
     */
    public String getDaysForSpecial(String specialNo)
    {
        String html = "";
        SpecialManager sm = SpecialManager.getInstance();
        Special s = sm.getSpecialByNumber(Integer.parseInt(specialNo));
        ArrayList<Integer> specialDays = s.getDayOfWeek();
        for (Integer day : specialDays) 
            html += day + ";";
        
        return html.substring(0, html.length() - 1);
    }
        
    /**
     * Returns a specials start time
     * @param specialNo as the special number to fetch
     * @return the specials start time
     */
    public String getStartTime(String specialNo)
    {
        SpecialManager sm = SpecialManager.getInstance();
        return sm.getSpecialByNumber(Integer.parseInt(specialNo)).getStartTime();
    }
        
    /**
     * Returns a specials end time
     * @param specialNo as the special number to fetch
     * @return the specials end time
     */
    public String getEndTime(String specialNo)
    {
        SpecialManager sm = SpecialManager.getInstance();
        return sm.getSpecialByNumber(Integer.parseInt(specialNo)).getEndTime();
    }
        
    /**
     * Returns a specials  items as an HTML string
     * @param specialNo as the special number to fetch
     * @return the specials items as an HTML string
     */
    public String getItemsOnSpecial(String specialNo)
    {
        String html = "<ul class='foodList'>";
        SpecialManager sm = SpecialManager.getInstance();
        ArrayList<Item> items = sm.getSpecialByNumber(Integer.parseInt(specialNo)).getItems();
        if(items.isEmpty())
            return "<span class='help'>Double click items on the left to add to specials.</span>";
        for(Item i : items)
            html += "<li data-id="+i.getItemNo()+">"+i.getItemName()+"</li>";
        return html + "</ul>";
    }
        
    /**
     * Returns a specials items NOT on the order as an HTML string
     * @param specialNo as the special number to fetch
     * @return the items not on a special as HTML.
     */
    public String getItemsNotOnSpecial(String specialNo)
    {
        String html = "<ul class='foodList'>";
        SpecialManager sm = SpecialManager.getInstance();
        Menu m = Menu.getInstance();
        ArrayList<Item> allItems = m.getMenuItems();
        ArrayList<Item> items = sm.getSpecialByNumber(Integer.parseInt(specialNo)).getItems();
        if(items.isEmpty())
            return "<span class='help'>Double click items on the left to add to specials.</span>";
        // for all items
        for(Item i : allItems)
        {
            boolean found = false;
            for(Item s : items)
                if(s.getItemNo() == i.getItemNo())
                    found = true;
            if(!found)
                html += "<li data-id='"+i.getItemNo()+"'>"+i.getItemName()+"</li>";
        }
        return html + "</ul>";
    }
    
}
