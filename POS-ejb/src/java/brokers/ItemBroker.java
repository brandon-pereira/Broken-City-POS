/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brokers;

import containers.Extra;
import containers.Item;
import containers.Side;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Broker class for items management. Creates an instance and
 * connects to the database for item handling.
 * @author 504724
 */
public class ItemBroker 
{
    private static ItemBroker instance = null;
    
    private ItemBroker()
    {
    }
    
    /**
     * Creates an instance of ItemBroker class for the controllers.
     * @return instance
     */
    public static ItemBroker getInstance()
    {
        if(instance == null)
        {
            instance = new ItemBroker();
        }
        
        return instance;
    }
    
    /**
     * Method that returns all items in the database.
     * @return menuItems Items in db
     */
    public ArrayList<Item> getAllItems()
    {
        ArrayList<Item> menuItems = new ArrayList<Item>();
        try
        {
            //Get the connetion to the database
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();
            //All CallableStatement objects and ResultSets needed
            CallableStatement cs = null;
            ResultSet items = null, itemOptions = null, option = null;
            //Get all of the Items from the database
            cs = conn.prepareCall("call getAllItems()");
            items = cs.executeQuery();

            while(items.next())//for each item
            {
                Item item = new Item();
                item.setItemNo(items.getInt(1));
                item.setItemName(items.getString(2));
                item.setItemDesc(items.getString(3));
                item.setBasePrice(items.getDouble(4));
                item.setCost(items.getDouble(5));
                item.setItemType(items.getString(6));
                if(!item.getItemType().equalsIgnoreCase("SPEED BAR"))//dont set the following attributes because they do not apply to speedbar items
                {   
                    item.setNumberOfSides(items.getInt(7));
                    item.setNumberOfExtras(items.getInt(8));
                    item.setSides(new ArrayList<Side>());
                    item.setExtras(new ArrayList<Extra>());
                
                    //get the item option numbers
                    cs = conn.prepareCall("call getItemOption(?)");//returns all of the option numbers 
                    cs.setInt(1, items.getInt(1));//plug in the item number
                    itemOptions = cs.executeQuery();//returns all the option numbers for that item

                    //loop and get the option type and price and name for all the option numbers
                    while(itemOptions.next())
                    {
                        cs = conn.prepareCall("call getOption(?)");
                        cs.setInt(1, itemOptions.getInt(1));
                        option = cs.executeQuery();//get an option
                        option.next();

                        if(option.getString(6).equalsIgnoreCase("side"))
                        {
                            Side side = new Side();
                            side.setSideNo(option.getInt(1));
                            side.setName(option.getString(2));
                            side.setPrice(option.getInt(4));

                            item.getSides().add(side);
                        }    
                        else if(option.getString(6).equalsIgnoreCase("extra"))
                        {
                            Extra extra = new Extra();
                            extra.setExtraNo(option.getInt(1));
                            extra.setName(option.getString(2));
                            extra.setPrice(option.getInt(4));
                            extra.setDescription(option.getString(3));

                            item.getExtras().add(extra);
                        }
                        option.close();
                    }
                    itemOptions.close();
                }
                item.setIsActive(items.getInt(9));
                menuItems.add(item);
            }
            items.close();
            cs.close();
            conn.close();
            return menuItems;
            
        } catch (ClassNotFoundException | NamingException | SQLException ex) {
            Logger.getLogger(ItemBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return menuItems;
    }

    /**
     * Method that returns all specific orders given a table and seat.
     * @param tableNo Table number
     * @param seatNo Seat number
     * @return orderItems List of all items in an orders
     */
    public ArrayList<Item> getOrderItems(int tableNo, int seatNo)
    {
        ArrayList<Item> orderItems = new ArrayList<Item>();
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();
            
            ArrayList<Side> sides = new ArrayList<Side>();
            ArrayList<Extra> extras = new ArrayList<Extra>();
            
            CallableStatement cs = conn.prepareCall("call getOrderNumber(?,?)");
            cs.setInt(1, tableNo);
            cs.setInt(2, seatNo);
            ResultSet orderNo = cs.executeQuery();            
            orderNo.next();
            
            cs = conn.prepareCall("call getItemsForSeat(?)");
            cs.setInt(1, orderNo.getInt(1));
            ResultSet seatItems = cs.executeQuery();
            
            while(seatItems.next())
            {
                Item item  = new Item();
                item.setItemNo(seatItems.getInt(1));
                item.setItemName(seatItems.getString(2));
                item.setBasePrice(seatItems.getDouble(3));
                
                
                cs = conn.prepareCall("call getSideInfo(?)");
                for(int i=4; i<7; i++)//loop for the 3 sides
                {
                    int sideNo = seatItems.getInt(i);
                    if(sideNo == 0)
                        continue;
                    else
                    {
                        
                        cs.setInt(1, sideNo);
                        ResultSet sideInfo = cs.executeQuery();
                        sideInfo.next();
                        
                        Side side = new Side();
                        side.setName(sideInfo.getString(1));
                        side.setPrice(sideInfo.getInt(2));
                        sides.add(side);
                        
                        sideInfo.close();
                    }
                }
                
                cs = conn.prepareCall("call getExtraInfo(?)");
                for(int i=7; i<10; i++)//loop for the 3 extras
                {
                    int extraNo = seatItems.getInt(1);
                    if(extraNo == 0)
                        continue;
                    else
                    {
                        cs.setInt(1, extraNo);
                        ResultSet extraInfo = cs.executeQuery();
                        extraInfo.next();
                        
                        Extra extra = new Extra();
                        extra.setName(extraInfo.getString(1));
                        extra.setPrice(extraInfo.getInt(2));
                        extras.add(extra);
                        
                        extraInfo.close();
                    }
                }
                
                item.setSides(sides);
                item.setExtras(extras);
                orderItems.add(item);
            }
            
            orderNo.close();
            seatItems.close();
            cs.close();
            conn.close();
   
        } catch (ClassNotFoundException | NamingException | SQLException ex) {
            Logger.getLogger(ItemBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return orderItems;
    }
    
/****************************************ADMIN METHODS**************************************/
    
    /**
     * item adding method for admin management
     * @param itemName
     * @param itemDescription
     * @param basePrice
     * @param cost
     * @param numberOfSides
     * @param numberOfExtras
     * @param itemType
     * @param isActive 
     */
    public int addItem(Item item)
    {
        int itemNo = 0;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("{? = call addNewItem(?,?,?,?,?,?,?,?)}");
            
            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            cs.setString(2,item.getItemName());
            cs.setString(3,item.getItemDesc());
            cs.setDouble(4, item.getBasePrice());
            cs.setDouble(5,item.getCost());
            cs.setString(6, item.getItemType());
            cs.setInt(7, item.getNumberOfSides());
            cs.setInt(8,item.getNumberOfExtras());
            cs.setInt(9,item.getIsActive());

            cs.execute();  
            
            item.setItemNo(cs.getInt(1));
            itemNo = item.getItemNo();
            
            //Add all of the extras to the table
            cs = conn.prepareCall("call addOption(?,?,?,?,?,?,?)");//optionName, optionDesc, optionPrice, optionCost, optionType, optionStatus
            for(int i=0;i<item.getExtras().size();i++)
            {
                Extra e = (Extra) item.getExtras().get(i);
                cs.setString(1, e.getName());
                cs.setString(2, e.getDescription());
                cs.setDouble(3, e.getPrice());
                cs.setDouble(4, e.getPrice()); //TODO + No implementation for Option costs in the front end *Remove column from database??
                cs.setString(5, "extra");
                cs.setInt(6, 1);
                cs.setInt(7, item.getItemNo());
                cs.execute();
            }
                    
            cs.close();
            conn.close();            
        } catch (ClassNotFoundException | NamingException | SQLException ex) {
            Logger.getLogger(ItemBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemNo;
    }
    
    /**
     * Method for the admin that modifies an item object
     * @param item Item object
     */
    public void modifyItem(Item item)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call modifyItem(?,?,?,?,?,?,?,?,?)");//itemNo, itemName, itemDesc, basePrice, cost, itemType, numSides, numOptions, isActive

            cs.setInt(1, item.getItemNo());
            cs.setString(2, item.getItemName());
            cs.setString(3, item.getItemDesc());
            cs.setDouble(4, item.getBasePrice());
            cs.setDouble(5, item.getCost());
            cs.setString(6, item.getItemType().toLowerCase());
            cs.setInt(7, item.getNumberOfSides());
            cs.setInt(8, item.getNumberOfExtras());
            cs.setInt(9, item.getIsActive());

            cs.executeQuery();    
            //Remove all extras for this item
            cs = conn.prepareCall("call removeOption(?)");
            cs.setInt(1, item.getItemNo());
            cs.executeQuery();
            //Re add all of the extras to the table
            cs = conn.prepareCall("call addOption(?,?,?,?,?,?,?)");//optionName, optionDesc, optionPrice, optionCost, optionType, optionStatus
            for(int i=0;i<item.getExtras().size();i++)
            {
                Extra e = (Extra) item.getExtras().get(i);
                cs.setString(1, e.getName());
                cs.setString(2, e.getDescription());
                cs.setDouble(3, e.getPrice());
                cs.setDouble(4, e.getPrice()); //TODO + No implementation for Option costs in the front end *Remove column from database??
                cs.setString(5, "extra");
                cs.setInt(6, 1);
                cs.setInt(7, item.getItemNo());
                cs.execute();
            }
            
            cs.close();
            conn.close();
            
        } catch (ClassNotFoundException | NamingException | SQLException ex) {
            Logger.getLogger(ItemBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method for the admin to remove an item given an item number
     * @param itemNo Item number
     */
    public void deleteItem(int itemNo)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call removeItem(?)");

            cs.setInt(1,itemNo);

            cs.executeQuery();    
            
            cs.close();
            conn.close();
            
        } catch (ClassNotFoundException | NamingException | SQLException ex) {
            Logger.getLogger(ItemBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
