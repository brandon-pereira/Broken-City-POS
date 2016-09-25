/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuHelper;

import containers.Extra;
import containers.Item;
import containers.Menu;
import containers.Order;
import containers.Side;
import containers.Table;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.naming.NamingException;
import AdminHelper.ItemsHelper;
/**
 *
 * @author 642123
 */
public class OrderHelper 
{
    private final double GST = 1.05;
    
    /**
     * Method that takes in the total price of an order, and adds the gst to it
     * @param cost - the subtotal of the order
     * @return cost - the total cost of the order
     */
    public double addGst(double cost)
    {
        return cost * GST;
    }
    
    /**
     * Method returns the current total of all the items purchased at a table without GST
     * @param table - the table
     * @return the current total of all the items purchased at a table
     * @throws java.sql.SQLException 
     * @throws javax.naming.NamingException 
     * @throws java.lang.ClassNotFoundException 
     */
    public String getTablePrice(Table table) throws SQLException, NamingException, ClassNotFoundException
    {   
        double tablePrice = 0;
        ArrayList<Order> orders = table.getOrders();//returns an array of orders that the table has
        for(int i = 0; i < orders.size(); i++)
        {
            Order o = orders.get(i);//get a single order from the list
            tablePrice += o.getOrderTotal();
        }
        DecimalFormat dec = new DecimalFormat("##,##0.00");
        return String.valueOf(dec.format(tablePrice));
    }
    
    /**
     * This method calculates the subtotal cost of all the items a seat has without GST
     * @param seatNo
     * @param table
     * @return
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String getSeatPrice(int seatNo, Table table) throws SQLException, NamingException, ClassNotFoundException
    {   
        double seatPrice = 0;
        ArrayList<Order> orders = table.getOrders();//returns an array of orders that the table has
        for(int i = 0; i < orders.size(); i++)
        {
            Order o = orders.get(i);//get a single order from the list
            if(o.getSeatNo() == seatNo)
            {
                seatPrice = o.getOrderTotal();
                break;
            }
        }
        DecimalFormat dec = new DecimalFormat("##,##0.00");
        return String.valueOf(dec.format(seatPrice));
    }
    
    /**
     * Displays comments added to an item as HTML popup for Menu screen.
     * @param itemOnOrder as the item no on an order
     * @param seatNo as the seat no in a table
     * @param t as the current table object
     * @return comment form popup
     */
    public String displayComments(String itemOnOrder, int seatNo, Table t)
    {
        String comment = "";
        ArrayList<Order> tableOrders = t.getOrders();
        ArrayList<Item> orderItems = null;
        for(int i=0;i<tableOrders.size();i++)
        {
            if(tableOrders.get(i).getSeatNo() == seatNo)//user seatNo to get the correct order
                orderItems = tableOrders.get(i).getItems();
        }
        for(int i=0;i<orderItems.size();i++)
        {
            if(orderItems.get(i).getItemOnOrderNo() == Integer.parseInt(itemOnOrder))
                if(orderItems.get(i).getComment() !=  null)
                    comment = orderItems.get(i).getComment();
        }
                //use the itemOnOrder number to get the correct item
                //tada getComment
        int itemOnOrderNo = Integer.parseInt(itemOnOrder);
        String html = "";

        html += "<div class='popup comment visible'><div class='slide'><form method='get' action='OrderController'>"
             + "<h1>Add a comment</h1><input type=\"hidden\" class=\"itemNo\" name='itemNo' value=\""+itemOnOrder+"\">\n" 
             + "<textarea name='comment'>"+comment+"</textarea><button>Add/Modify Comment</button>"
             + "</form></div><a href='#' class='close'></a></div>";
        return html;
    }

    /**
     * Method returns HTML code that generates the left side bar of the menu containing all of the seats
     * @param table
     * @param seatNo
     * @return
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String loadSeats(Table table, int seatNo) throws SQLException, NamingException, ClassNotFoundException
    {   
        
        String html = "";
        for(int i = 0; i < table.getNumberOfSeats(); i++)
        {
            if(seatNo == (i+1))
                html += "<a href='OrderController?seatNo="+(i+1)+"' class='seat current'>"+(i+1)+"</a>";
            else
                html += "<a href='OrderController?seatNo="+(i+1)+"' class='seat'>"+(i+1)+"</a>";
        }
        return html;
    }
    
    /**
     * Returns list of seats for a table
     * @param table as a table object to read
     * @param seatNo as current seat
     * @param hideCurrent a boolean if it should hide the seatNo int.
     * @return HTML for the current seats
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String loadSeats(Table table, int seatNo, boolean hideCurrent) throws SQLException, NamingException, ClassNotFoundException
    {   
        
        String html = "";
        for(int i = 0; i < table.getNumberOfSeats(); i++)
        {
            if(seatNo != (i+1))
                html += "<a href='#' class='seat'>"+(i+1)+"</a>";
        }
        return html;
    }
    
    /**
     * Returns all seats in a table as links
     * @param table as a table object
     * @return String of HTML links.
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String loadSeats(Table table) throws SQLException, NamingException, ClassNotFoundException
    {   
        String html = "";
        for(int i = 0; i < table.getNumberOfSeats(); i++)
        {
                html += "<a href='#' class='seat'>"+(i+1)+"</a>";
        }
        return html;
    }
    
    /**
     * Method that returns HTML code for all of the items that a seat has within a table
     * @param seatNo
     * @param table
     * @return
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String getItemsForSeat(int seatNo, Table table) throws SQLException, NamingException, ClassNotFoundException
    {   
        String food = "";
        String drinks = "";
        DecimalFormat dec = new DecimalFormat("##0.00");
        ArrayList<Order> orders = table.getOrders();//returns an array of orders that the table has
        for(int i = 0; i < orders.size(); i++)
        {
            Order o = orders.get(i);//get a single order from the list
            if(o.getSeatNo() == seatNo)
            {
                ArrayList<Item> items = o.getItems();//get the list of items for that order
                for(int j = 0; j < items.size(); j++)
                {
                    Item item = items.get(j);//get the item
                    String listItem = "";
                    if(item.getBasePrice() != 0)
                        listItem = "<li data-id='"+item.getItemOnOrderNo()+"'>"+item.getItemName()+"<span>"+dec.format(item.getBasePrice())+"</span></li>";
                    else
                        listItem = "<li class='void' data-id='"+item.getItemOnOrderNo()+"'>"+item.getItemName()+"<span>"+dec.format(item.getBasePrice())+"</span></li>";
                    ArrayList<Extra> itemExtras = item.getExtras();
                    ArrayList<Side> itemSides = item.getSides();
                    for(int s = 0; s < itemSides.size();s++)
                        listItem += "<li class='side'>"+itemSides.get(s).getName()+"</li>";
                    for(int e = 0; e < itemExtras.size();e++)
                        listItem += "<li class='extra'>"+itemExtras.get(e).getName()+"<span>"+dec.format(itemExtras.get(e).getPrice())+"</span></li>";
                    if(item.getItemType().equalsIgnoreCase("drink"))
                       drinks += listItem;
                    else
                        food += listItem;
                }
                break;
            }
        }
        String html = "<ul class='food section'><h1>Food</h1>"+food+"</ul>" +
             "<ul class='drinks section'><h1>Drinks</h1>"+drinks+"</ul>";
        
        return html;//return html string
    }
    
    /**
     * Method that returns HTML code for all of the orders for a certain table.
     * @param table
     * @return
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String getItemsForTable(Table table) throws SQLException, NamingException, ClassNotFoundException
    {
        String food = "";
        String drinks = "";
        ArrayList<Order> orders = table.getOrders();//returns an array of orders that the table has
        for(int i = 0; i < orders.size(); i++)
        {
            Order o = orders.get(i);//get a single order from the list
            ArrayList<Item> items = o.getItems();//get the list of items for that order
            
            for(int j = 0; j < items.size(); j++)
            {
                Item item = items.get(j);//get the item
                 String listItem = "<li data-id='"+item.getItemOnOrderNo()+"'>"+item.getItemName()+"<span>"+item.getBasePrice()+"</span></li>";
                    if(item.getItemType().equalsIgnoreCase("drink"))
                       drinks += listItem;
                    else
                        food += listItem;
            }
        }
        String html = "<ul class='food section'><h1>Food</h1>"+food+"</ul>" +
                     "<ul class='drinks section'><h1>Drinks</h1>"+drinks+"</ul>";
        return html;//return html string
    }
    
    /**
     * Method to return a tables items options popup for Menu.
     * @param itemOnOrderNo as item on order no
     * @param seatNo as seat number of current seat
     * @param t as table object of current table
     * @return  html popup string.
     */
    public String getTableItemOptions(String itemOnOrderNo, int seatNo, Table t)
    {
        ArrayList<Order> orders = t.getOrders();
        Item itemOnOrder = new Item();
        // Cycle through orders and look for seat no
        for(int i = 0; i < orders.size(); i++)
        {
            Order o = orders.get(i);
            if(o.getSeatNo() == seatNo)
            {
                // Cycle through items in a seat
                ArrayList<Item> items = o.getItems();
                for(int j = 0; j < items.size(); j++)
                {
                    Item currItem = items.get(j);
                    if(currItem.getItemOnOrderNo() == Integer.parseInt(itemOnOrderNo))
                    {
                        itemOnOrder = currItem;
                        break;//inner for loop
                    }
                }
                break;//outer for loop
            }
        }
        
        String itemNo = itemOnOrder.getItemNo() + "";
        String html = " <div class='popup options visible'><div class='slide'><h1>";
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(Integer.parseInt(itemNo));
        html += item.getItemName();
        ArrayList<Side> sides = item.getSides();//get the sides of the item
        ArrayList<Extra> extras = item.getExtras();//get the extras of the item
        html += "</h1><form method='get' class='itemOptions' action='OrderController'><div class='itemOptionsForm'><input type='hidden' name='more' value='true'><input type='hidden' name='modifyItem' value='"+itemOnOrder.getItemOnOrderNo()+"'>";
        boolean hasSides = false;
        if(sides.isEmpty() || item.getNumberOfSides() == 0){}
        else
        {
            hasSides = true;
            html += "<h2>Select "+item.getNumberOfSides()+" side(s)</h2><div data-maxSides='"+item.getNumberOfSides()+"' class='grid flex small inline selectSides'><input type='hidden' name='sides' class='sides' value='";
            String sidesDelimited = "";
            System.out.println("It thinks there is: " + itemOnOrder.getSides().size());
            for(int i = 0;i < itemOnOrder.getSides().size();i++)
            {
                sidesDelimited += itemOnOrder.getSides().get(i).getSideNo() + ";";
            }
            sidesDelimited = sidesDelimited.substring(0, sidesDelimited.length()-1);
            html += sidesDelimited + "'>";
            // For each side in system
            for(int i = 0; i < sides.size(); i++)
            {
                boolean foundSide = false;
                Side side = sides.get(i);
                // Compare that side no with the side on order no
                
                for(int j = 0; j < itemOnOrder.getSides().size();j++)
                {
                    if(itemOnOrder.getSides().get(j).getSideNo() == side.getSideNo())
                        foundSide = true;
                }
                if(foundSide)
                    html += "<a data-id='"+side.getSideNo()+"' class='selected' href='#'>"+side.getName()+"</a>";
                else
                    html += "<a data-id='"+side.getSideNo()+"' href='#'>"+side.getName()+"</a>";
            }
            html += "</div>";
        }
        ItemsHelper h = new ItemsHelper();
        ArrayList<String> extraNames = new ArrayList<String>();
        String extra1 = h.getExtraName(itemNo,1);
        String extra2 = h.getExtraName(itemNo,2);
        String extra3 = h.getExtraName(itemNo,3);
        extraNames.add(extra1);
        extraNames.add(extra2);
        extraNames.add(extra3);
        int noExtras = item.getNumberOfExtras();
        boolean hasExtras = false;
        if(noExtras != 0)
            hasExtras = true;
        for(int i = 0;i<noExtras;i++)
        {
            String extraHtml, extraItemsHtml = "";
            int currExtra = 0;
            for(int k = 0; k < extras.size(); k++)
            {
                Extra e = extras.get(k);
                boolean foundExtra = false;

                for(int j = 0; j < itemOnOrder.getExtras().size();j++)
                {
                    if(itemOnOrder.getExtras().get(j).getExtraNo() == e.getExtraNo())
                        foundExtra = true;
                }
                if(foundExtra)
                {
                    if(e.getDescription().equalsIgnoreCase(extraNames.get(i)))
                    {
                        currExtra = e.getExtraNo();
                        extraItemsHtml += "<a data-id='"+e.getExtraNo()+"' class='selected' href='#'>"+e.getName()+"</a>";
                    }
                }
                else
                    if(e.getDescription().equalsIgnoreCase(extraNames.get(i)))
                        extraItemsHtml += "<a data-id='"+e.getExtraNo()+"' href='#'>"+e.getName()+"</a>";
            }
            html += "<h2>"+extraNames.get(i)+"</h2><div class='grid flex small inline extra'><input type='hidden' name='extra"+(i+1)+"' class='extra"+(i+1)+"' value='"+currExtra+"'>" + extraItemsHtml;
            html += "</div>";
        }
        
html+="                    </div><button class='submit'>Submit</button>\n" +
"                </form>\n" +
"            </div>\n" +
"            <a href='#' class='close'></a>\n" +
"        </div>";   
        if(hasExtras || hasSides)
            return html;
        else
            return "<div class='msg warning'>Item has no options</div>";
    }
    
    /**
     * Method to display an generic items options popup.
     * @param itemNo as itemNo to display popup for
     * @return html popup OR error message if no extras.
     */
    public String getItemOptions(String itemNo)
    {
        int itemNoInt = Integer.parseInt(itemNo);
        String html = " <div class='popup options visible'><div class='slide'><h1>";
        Menu menu = Menu.getInstance();
        Item item = menu.getMenuItem(itemNoInt);
        html += item.getItemName();
        ArrayList<Side> sides = item.getSides();//get the sides of the item
        ArrayList<Extra> extras = item.getExtras();//get the extras of the item
        html += "</h1><form method='get' class='itemOptions' action='OrderController'><div class='itemOptionsForm'><input type='hidden' name='more' value='true'><input type='hidden' name='addItem' value='"+itemNo+"'>";
        if(sides.isEmpty() || item.getNumberOfSides() == 0){}
        else
        {
            html += "<h2>Select "+item.getNumberOfSides()+" side(s)</h2><div data-maxSides='"+item.getNumberOfSides()+"' class='grid flex small inline selectSides'><input type='hidden' name='sides' class='sides'>";
            for(int i = 0; i < sides.size(); i++)
            {
                Side side = sides.get(i);
                html += "<a data-id='"+side.getSideNo()+"' href='#'>"+side.getName()+"</a>";
            }
            html += "</div>";
        }
        ItemsHelper h = new ItemsHelper();
        ArrayList<String> extraNames = new ArrayList<String>();
        String extra1 = h.getExtraName(itemNo,1);
        String extra2 = h.getExtraName(itemNo,2);
        String extra3 = h.getExtraName(itemNo,3);
        extraNames.add(extra1);
        extraNames.add(extra2);
        extraNames.add(extra3);
        int noExtras = item.getNumberOfExtras();
        for(int i = 0;i<noExtras;i++)
        {
            html += "<h2>"+extraNames.get(i)+"</h2><div class='grid flex small inline extra'><input type='hidden' name='extra"+(i+1)+"' class='extra"+(i+1)+"'>";
            for(int k = 0; k < extras.size(); k++)
            {
                Extra e = extras.get(k);
                if(e.getDescription().equalsIgnoreCase(extraNames.get(i)))
                    html += "<a data-id='"+e.getExtraNo()+"' href='#'>"+e.getName()+"</a>";
            }
            html += "</div>";
        }
        
html+="                    </div><button class='submit'>Submit</button>\n" +
"                </form>\n" +
"            </div>\n" +
"            <a href='#' class='close'></a>\n" +
"        </div>";
        return html;
    }
    
    
    /*******************************MENU METHODS*********************************/
    /**
     * Displays the tabbing used to alternate between different screens
     * @param type as the current screen
     * @return html code of all items on current type
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String getMenuSelection(String    type) throws SQLException, NamingException, ClassNotFoundException
    {
        String html = "";
        if(type.equals("0"))
            html = getMains();
        else if(type.equals("1"))
            html = getAppetizers();
        else if(type.equals("2"))
            html = getDesserts();
        else if(type.equals("3"))
            html = getSides();
        else if(type.equals("4"))
            html = getOthers();
        else if(type.equals("5"))
            html = getDrinks();
        return html;
        
    }
    
    /**
     * Display all appetizers
     * @return appetizers html.
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String getAppetizers() throws SQLException, NamingException, ClassNotFoundException
    {
        Menu menu = Menu.getInstance();
        ArrayList<Item> items = menu.getMenuItems();//get reference to all the menu items
        String html = "";
        for(int i = 0; i < items.size(); i++)
        {
            Item item = items.get(i);
            if(item.getItemType().equalsIgnoreCase("appetizer"))//if the item is an appetizer
            {
                if(item.getNumberOfExtras()== 0 && item.getNumberOfSides() == 0)
                     html += "<a href='OrderController?addItem="+item.getItemNo()+"'>"+item.getItemName()+"</a>";
                else
                   html += "<a href='Menu.jsp?displayOptions="+item.getItemNo()+"'>"+item.getItemName()+"</a>";
                
            }
        }
        return html;//return html code
    }
    
    /**
     * Get all other items
     * @return html code of all other items.
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String getOthers() throws SQLException, NamingException, ClassNotFoundException
    {
        Menu menu = Menu.getInstance();
        ArrayList<Item> items = menu.getMenuItems();//get reference to all the menu items
        String html = "";
        for(int i = 0; i < items.size(); i++)
        {
            Item item = items.get(i);
            if(item.getItemType().equalsIgnoreCase("others"))//if the item is an appetizer
            {
                    if(item.getNumberOfExtras()== 0 && item.getNumberOfSides() == 0)
                        html += "<a href='OrderController?addItem="+item.getItemNo()+"'>"+item.getItemName()+"</a>";
                    else
                        html += "<a href='Menu.jsp?displayOptions="+item.getItemNo()+"'>"+item.getItemName()+"</a>";
                
            }
        }
        return html;//return html code
    }
    
    /**
     * Get all main items
     * @return html string of all mains
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String getMains() throws SQLException, NamingException, ClassNotFoundException
    {
        Menu menu = Menu.getInstance();
        ArrayList<Item> items = menu.getMenuItems();//get reference to all the menu items
        String html = "";
        for(int i = 0; i < items.size(); i++)
        {
            Item item = items.get(i);
            if(item.getItemType().equalsIgnoreCase("main"))//if the item is an appetizer
            {
                if(item.getNumberOfExtras()== 0 && item.getNumberOfSides() == 0)
                    html += "<a href='OrderController?addItem="+item.getItemNo()+"'>"+item.getItemName()+"</a>";
                else
                    html += "<a href='Menu.jsp?displayOptions="+item.getItemNo()+"'>"+item.getItemName()+"</a>";
            }
        }
        return html;//return html code
    }
    
    /**
     * Get all desserts
     * @return html string of all desserts.
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String getDesserts() throws SQLException, NamingException, ClassNotFoundException
    {
        Menu menu = Menu.getInstance();
        ArrayList<Item> items = menu.getMenuItems();//get reference to all the menu items
        String html = "";
        for(int i = 0; i < items.size(); i++)
        {
            Item item = items.get(i);
            if(item.getItemType().equalsIgnoreCase("dessert"))//if the item is an appetizer
            {
                if(item.getNumberOfExtras()== 0 && item.getNumberOfSides() == 0)
                    html += "<a href='OrderController?addItem="+item.getItemNo()+"'>"+item.getItemName()+"</a>";
                else
                    html += "<a href='Menu.jsp?displayOptions="+item.getItemNo()+"'>"+item.getItemName()+"</a>";
            }
        }
        
        return html;//return html code
    }
    
    /**
     * Get all sides
     * @return html string of all sides.
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String getSides() throws SQLException, NamingException, ClassNotFoundException
    {
        Menu menu = Menu.getInstance();
        ArrayList<Item> items = menu.getMenuItems();//get reference to all the menu items
        String html = "";
        for(int i = 0; i < items.size(); i++)
        {
            Item item = items.get(i);
            if(item.getItemType().equalsIgnoreCase("side"))//if the item is an appetizer
            {
                if(item.getNumberOfExtras()== 0 && item.getNumberOfSides() == 0)
                    html += "<a href='OrderController?addItem="+item.getItemNo()+"'>"+item.getItemName()+"</a>";
                else
                    html += "<a href='Menu.jsp?displayOptions="+item.getItemNo()+"'>"+item.getItemName()+"</a>";
            }
        }
        
        return html;//return html code
    }

    /**
     * Gets all drinks
     * @return html string of all drinks.
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public String getDrinks() throws SQLException, NamingException, ClassNotFoundException
    {
        Menu menu = Menu.getInstance();
        ArrayList<Item> items = menu.getMenuItems();//get reference to all the menu items
        String html = "";
        for(int i = 0; i < items.size(); i++)
        {
            Item item = items.get(i);
            if(item.getItemType().equalsIgnoreCase("drink"))//if the item is an appetizer
            {
                if(item.getNumberOfExtras()== 0 && item.getNumberOfSides() == 0)
                    html += "<a href='OrderController?addItem="+item.getItemNo()+"'>"+item.getItemName()+"</a>";
                else
                    html += "<a href='Menu.jsp?displayOptions="+item.getItemNo()+"'>"+item.getItemName()+"</a>";
            }
        }
        
        return html;//return html code
    }
}
