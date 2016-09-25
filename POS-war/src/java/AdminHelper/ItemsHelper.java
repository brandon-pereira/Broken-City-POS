/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminHelper;

import containers.Extra;
import containers.Item;
import containers.Menu;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author 632794
 */
public class ItemsHelper {
    public ItemsHelper(){};
    
    /**
     * Gets list of all items as HTML string
     * @return html string for items
     */
    public String getItemsList()
    {
        Menu menu = Menu.getInstance();
        ArrayList<Item> items = menu.getMenuItems();
        String table = "<ul class='foodList'>";
        for(int i = 0; i < items.size(); i++)
        {
            Item item = items.get(i);
            table += "<li ";
            if(item.getIsActive() == 0)
                table += "class='inactive'";
            table += " data-id="+item.getItemNo()+">"+item.getItemName()+"</li>";
        }
        return table += "</ul>";
    }
    
    /**
     * Get an item name
     * @param itemNo as the item number
     * @return items name
     */
    public String getItemName(String itemNo){
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(Integer.parseInt(itemNo));
        return item.getItemName();
    }
    
    /**
     * Get an item description
     * @param itemNo as the item number
     * @return items description
     */
    public String getItemDesc(String itemNo){
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(Integer.parseInt(itemNo));
        return item.getItemDesc();
    }
    
    /**
     * Get an item base price
     * @param itemNo as the item number
     * @return items base price
     */
    public String getBasePrice(String itemNo){
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(Integer.parseInt(itemNo));
        DecimalFormat dec = new DecimalFormat("#0.00");
        return dec.format(item.getBasePrice());
    }
    
    /**
     * Get an item cost
     * @param itemNo as the item number
     * @return items cost
     */
    public String getCost(String itemNo){
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(Integer.parseInt(itemNo));
        DecimalFormat dec = new DecimalFormat("#0.00");
        return dec.format(item.getCost());
    }
    
    /**
     * Get an items number of sides
     * @param itemNo as the item number
     * @return items number of sides
     */
    public String getNoSides(String itemNo){
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(Integer.parseInt(itemNo));
        return item.getNumberOfSides() + "";
    }
    
    /**
     * Get an item number of extras
     * @param itemNo as the item number
     * @return items number of extras
     */
    public String getNoExtras(String itemNo){
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(Integer.parseInt(itemNo));
        return item.getNumberOfExtras() + "";
    }
    
    /**
     * Get an item active status
     * @param itemNo as the item number
     * @return items active status
     */
    public String isEnabled(String itemNo){
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(Integer.parseInt(itemNo));
        if(item.getIsActive() == 1)
            return "checked";
        else
            return "";
    }
    
    /**
     * Get an item type as html string
     * @param itemNo as the item number
     * @return html string with current type being selected
     */
    public String getItemType(String itemNo){
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(Integer.parseInt(itemNo));
        String currentType = item.getItemType();
        String[] types = {"Main", "Side", "Drink", "Appetizer", "Dessert", "Speed Bar"};
        String select = "<select name='itemType'>";
        for(String i:types)
        {
            if(currentType.equalsIgnoreCase(i))
                select += "<option selected=selected>"+i+"</option>";
            else
                select += "<option>"+i+"</option>";
        }
        select +=  "</select>";
        return select;
    }
    
    /**
     * Get an items extra cost
     * @param itemNo as the item number
     * @param extraNo as the extra number
     * @return items extra cost
     */
    public String getExtraPrice(String itemNo, int extraNo)
    {
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(Integer.parseInt(itemNo));
        ArrayList<Extra> extras  = item.getExtraNo(extraNo);
        DecimalFormat dec = new DecimalFormat("#0.00");
        if(extras.isEmpty())
            return "";
        Extra e = extras.get(0);   
        return dec.format(e.getPrice());
    }
    
        
    /**
     * Get an items extra name
     * @param itemNo as the item number
     * @param extraNo as the extra number
     * @return items extra name
     */
    public String getExtraName(String itemNo, int extraNo)
    {
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(Integer.parseInt(itemNo));
        ArrayList<Extra> extras  = item.getExtraNo(extraNo);
        if(extras.isEmpty())
            return "";
        Extra e = extras.get(0);   
        return e.getDescription();
    }
    
    /**
     * Get an items extra items
     * @param itemNo as the item number
     * @param extraNo as the extra number
     * @return items extra items
     */
    public String getExtra(String itemNo, int extraNo)
    {
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(Integer.parseInt(itemNo));
        ArrayList<Extra> extras  = item.getExtraNo(extraNo);
        String delimiter = "";
        for(int i = 0; i < extras.size(); i++)
            delimiter += extras.get(i).getName() + ";";
        if(delimiter.length() != 0)
            delimiter = delimiter.substring(0, delimiter.length() - 1);
        return delimiter;
    }
}
