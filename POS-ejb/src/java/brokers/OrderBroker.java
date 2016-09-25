/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brokers;

import businessLogic.TableManager;
import containers.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Broker class for the order management. Creates an instance and provides
 * methods for order and table management.
 * @author 504724
 */
public class OrderBroker 
{
    private static OrderBroker instance = null;
    
    private OrderBroker()
    {       
    }
    
    /**
     * Creates an instance of the order broker class to be used
     * by the controllers.
     * @return 
     */
    public static OrderBroker getInstance()
    {
        if(instance == null)
        {
            instance = new OrderBroker();
        }
        
        return instance;
    }
    
    /**
     * This method will persist changes made to an order into the database.
     * Pre-Condition: 
     * @param table
     */
    public void persistTable(Table table)
    {
        if(table.getTableNo() == 0 || table.getTableNo() == 100)//dont persist table 0 (quick sale) or speed bar table
        {
            //table = null;//get rid of table 0, 100
            return; 
        }
        
        try {
            //Database connection
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();
            DecimalFormat df = new DecimalFormat("##,##0.00");
            //persisting all orders for the table
            ArrayList<Order> orders = table.getOrders();
            Order order = null;
            CallableStatement cs = conn.prepareCall("call modifyItemOnOrder(?,?,?,?,?,?,?,?,?)");
            CallableStatement cs1 = conn.prepareCall("call modifyOrder(?,?)");
            CallableStatement cs2 = conn.prepareCall("call modifyTable(?,?,?)");
            //update table information
            cs2.setInt(1, table.getTableNo());
            cs2.setInt(2, 1);
            cs2.setInt(3, table.getServerNo());
            cs2.executeQuery();
            cs2.close();
            
            for(int i=0;i<orders.size();i++)
            {
                order = orders.get(i);
                ArrayList<Item> items = order.getItems();
                for(int x=0;x<items.size();x++)//For each item on the order
                {
                    cs.setInt(1, items.get(x).getItemOnOrderNo());
                    //Get the arraylist of sides
                    ArrayList<Side> sides = items.get(x).getSides();
                    if(!sides.isEmpty())
                    {
                        if(sides.size() >= 1)
                            cs.setInt(2, sides.get(0).getSideNo());
                        else
                            cs.setNull(2, java.sql.Types.INTEGER);
                        if(sides.size() >= 2)
                            cs.setInt(3, sides.get(1).getSideNo());
                        else
                            cs.setNull(3, java.sql.Types.INTEGER);
                        if(sides.size() >= 3)
                            cs.setInt(4, sides.get(2).getSideNo());
                        else
                            cs.setNull(4, java.sql.Types.INTEGER);
                    }
                    else
                    {
                        for(int k = 2; k < 5; k++)
                        {
                            cs.setNull(k, java.sql.Types.INTEGER);
                        }
                    }
                    //Get the arraylist of extras
                    ArrayList<Extra> extras = items.get(x).getExtras();
                    if(!extras.isEmpty())
                    {
                        if(extras.size() >= 1)
                            cs.setInt(5, extras.get(0).getExtraNo());
                        else
                            cs.setNull(5, java.sql.Types.INTEGER);
                        if(extras.size() >= 2)
                            cs.setInt(6, extras.get(1).getExtraNo());
                        else
                            cs.setNull(6, java.sql.Types.INTEGER);
                        if(extras.size() >= 3)
                            cs.setInt(7, extras.get(2).getExtraNo());
                        else
                            cs.setNull(7, java.sql.Types.INTEGER);
                    }
                    else
                    {
                        for(int k = 5; k < 8; k++)
                        {
                            cs.setNull(k, java.sql.Types.INTEGER);
                        }
                    }
                    
                    cs.setString(8, items.get(x).getComment());
                    cs.setDouble(9, Double.parseDouble(df.format(items.get(x).getBasePrice())));
                    cs.executeQuery();
                }
                
                cs1.setInt(1, orders.get(i).getOrderNo());
                cs1.setDouble(2, Double.parseDouble(df.format(order.getOrderTotal())));
                cs1.executeQuery();
            }
            //close database connections
            cs.close();
            cs1.close();
            conn.close();
        } catch (ClassNotFoundException | NamingException | SQLException ex) {
            Logger.getLogger(OrderBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method will create a new row in the database for a new item being added to the order
     * Pre-Condition: Must have the OrderNumber, SeatNumber and ItemNumber
     * @param orderNumber, itemNumber 
     * @param itemNumber 
     * @return Unique itemOnOrderNumber
     */
    public int addItemOnOrder(int orderNumber, int itemNumber)
    {
        int itemOnOrderNumber = 0;
        try {
            //Database Connection
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();
            //Call function that creates a new row in the Order table and returns the OrderNumber
            CallableStatement cs = conn.prepareCall("{? = call addNewItemOnOrder(?,?)}");
            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            //Pass in Table Number, Seat Number, Server Number
            cs.setInt(2, orderNumber);
            cs.setInt(3, itemNumber);
            cs.execute();
            //retreive the order number
            System.out.println(cs.getInt(1));
            itemOnOrderNumber = cs.getInt(1);
            //Close database Connections
            cs.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("An error has occured. addNewOrder, ClassNotFound");
        } catch(NamingException ex) {
            System.out.println("An error has occured. addNewOrder, Naming");
        } catch(SQLException ex) {
            System.out.println("An error has occured. addNewOrder, SQL");
            ex.printStackTrace();
        }
        return itemOnOrderNumber;
    }
    
    /**
     * Method that moves an item in the database to the new order
     * @param itemOnOrderNo
     * @param newOrderNo 
     */
    public void moveItem(int itemOnOrderNo, int newOrderNo)
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();
            CallableStatement cs = conn.prepareCall("call moveItem(?,?)");
            cs.setInt(1, itemOnOrderNo);
            cs.setInt(2, newOrderNo);
            cs.executeQuery();
            cs.close();
            conn.close();
        }
        catch (ClassNotFoundException ex) {
            System.out.println("An error has occured. addNewOrder, ClassNotFound");
        } catch(NamingException ex) {
            System.out.println("An error has occured. addNewOrder, Naming");
        } catch(SQLException ex) {
            System.out.println("An error has occured. addNewOrder, SQL");
            ex.printStackTrace();
        }
    }
    
    /**
     * This method will call the function in the database to create a new row in the database Order table when a new Order/seat is created
     * or a new table is opened
     * pre-condition: Require a 
     * post_condition:
     * @param tableNumber, seatNumber, serverNumber
     * @param seatNumber
     * @param serverNumber
     * @return Unique orderNumber
     */
    public int addNewOrder(int tableNumber,int seatNumber, int serverNumber)
    {
        int orderNumber = 0;
        try {
            //Database Connection
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();
            //Call function that creates a new row in the Order table and returns the OrderNumber
            CallableStatement cs = conn.prepareCall("{? = call addNewOrder(?,?)}");
            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            //Pass in Table Number, Seat Number, Server Number
            cs.setInt(2, tableNumber);
            cs.setInt(3, seatNumber);
            cs.execute();
            //retreive the order number
            System.out.println(cs.getInt(1));
            orderNumber = cs.getInt(1);
            //Close database Connections
            cs.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("An error has occured. addNewOrder, ClassNotFound");
        } catch(NamingException ex) {
            System.out.println("An error has occured. addNewOrder, Naming");
        } catch(SQLException ex) {
            System.out.println("An error has occured. addNewOrder, SQL");
            ex.printStackTrace();
        }
        return orderNumber;
    }
    
    /**
     * Method that is used to clear a table of its server and set boolean to 0
     * @param table 
     */
    public void clearTable(Table table)
    {
        try 
        {
            int tableNo = table.getTableNo();
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();
            CallableStatement cs = conn.prepareCall("call modifyTable(?,?,?)");
            CallableStatement cs1 = conn.prepareCall("call closeOrder(?)");
            cs.setInt(1, tableNo);
            cs.setInt(2, 0);
            cs.setNull(3, java.sql.Types.INTEGER);
            cs.executeQuery();
            cs.close();
            
            ArrayList<Order> orders = table.getOrders();
            for(Order o : orders)
            {
                cs1.setInt(1, o.getOrderNo());
                cs1.executeQuery();
            }
            cs1.close();
            conn.close();
        }
        catch (ClassNotFoundException ex) {
            System.out.println("An error has occured. addNewOrder, ClassNotFound");
        } catch(NamingException ex) {
            System.out.println("An error has occured. addNewOrder, Naming");
        } catch(SQLException ex) {
            System.out.println("An error has occured. addNewOrder, SQL");
            ex.printStackTrace();
        }
    }
    
    public void moveSeat(int tableNo, int oldSeatNo, int newSeatNo)
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();
            CallableStatement cs = conn.prepareCall("call moveSeat(?,?,?)");
            cs.setInt(1, tableNo);
            cs.setInt(2, oldSeatNo);
            cs.setInt(3, newSeatNo);
            cs.executeQuery();
            cs.close();
            conn.close();
        }
        catch (ClassNotFoundException ex) {
            System.out.println("An error has occured. addNewOrder, ClassNotFound");
        } catch(NamingException ex) {
            System.out.println("An error has occured. addNewOrder, Naming");
        } catch(SQLException ex) {
            System.out.println("An error has occured. addNewOrder, SQL");
            ex.printStackTrace();
        }
    }
    
    public void addToOrderLog(Order o, String paymentType)
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();
            CallableStatement cs = conn.prepareCall("call pay(?,?)");
            cs.setInt(1, o.getOrderNo());
            cs.setString(2, paymentType);
            cs.executeQuery();
            cs.close();
            conn.close();
        }
        catch (ClassNotFoundException ex) {
            System.out.println("An error has occured. addNewOrder, ClassNotFound");
        } catch(NamingException ex) {
            System.out.println("An error has occured. addNewOrder, Naming");
        } catch(SQLException ex) {
            System.out.println("An error has occured. addNewOrder, SQL");
            ex.printStackTrace();
        }
    }
    
    public void mergeTables(int from, int to)
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();
            CallableStatement cs = conn.prepareCall("call moveTable(?,?)");
            cs.setInt(1, from);
            cs.setInt(2, to);
            cs.executeQuery();
            cs.close();
            conn.close();
        }
        catch (ClassNotFoundException ex) {
            System.out.println("An error has occured. addNewOrder, ClassNotFound");
        } catch(NamingException ex) {
            System.out.println("An error has occured. addNewOrder, Naming");
        } catch(SQLException ex) {
            System.out.println("An error has occured. addNewOrder, SQL");
            ex.printStackTrace();
        }
    }
    
    /**
     * Method that loads orders from the database on system startup, if the application
     * was not shut down correctly or unexpectedly
     */
    public void loadOrdersFromDatabase()
    {
        System.out.println("loadOrdersFromDatabase()");
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call getAllOrders()");
            CallableStatement cs1 = conn.prepareCall("call getItemsForOrder(?)");
            CallableStatement getNumberOfSeats = conn.prepareCall("call getNumberOfSeats(?)");
            CallableStatement getServer = conn.prepareCall("call getServer(?)");
            ResultSet rs = cs.executeQuery();
            
            TableManager tm = TableManager.getInstance();
            Menu menu = Menu.getInstance();
            while(rs.next())//for each current order
            {
                boolean found = false;

                ArrayList<Table> tables = tm.getTables();
                if(!tables.isEmpty())
                { 
                    for(int i = 0; i < tables.size(); i++)//loop to check if the table has already been created
                    {
                        if(tables.get(i).getTableNo() == rs.getInt(2)) //then the table exists
                        {
                            found = true;
                             /* ORDER CREATION */
                            Order o = new Order();//create a new order for the table
                            o.setOrderNo(rs.getInt(1));//orderNo represents a seat
                            o.setSeatNo(rs.getInt(3));
                            o.setOrderTotal(rs.getDouble(4));

                            cs1.setInt(1, o.getOrderNo());//get all the items for that order
                            ResultSet items = cs1.executeQuery();//store the items in result set
                            while(items.next())//for each item in the order
                            {
                                Item item = menu.getMenuItem(items.getInt(2));
                                                               
                                Item orderItem = new Item(item);//copy the item without the sides and extras
                               
                                orderItem.setItemOnOrderNo(items.getInt(1));     
                                orderItem.setBasePrice(items.getDouble(10));
                                orderItem.setComment(items.getString(9));//if theres a comment, set the comment
                                
                                ArrayList<Side> sides = new ArrayList<Side>();
                                ArrayList<Extra> extras = new ArrayList<Extra>();

                                ArrayList<Side> ogSides = item.getSides();//get the original items sides
                                ArrayList<Extra> ogExtras = item.getExtras();//get the original items extras
                                                     
                                /* SIDES */
                                if(items.getInt(3) != 0)//if there is a side
                                {
                                    for(int x=0; x<ogSides.size();x++)//loop to find the side
                                    {
                                        if(items.getInt(3) == ogSides.get(x).getSideNo())
                                        {
                                            Side side = new Side();//create a copy of the side
                                            side.setName(ogSides.get(x).getName());
                                            side.setSideNo(ogSides.get(x).getSideNo());
                                            side.setPrice(ogSides.get(x).getPrice());
                                            sides.add(side);
                                            break;
                                        }
                                    }
                                }
                                if(items.getInt(4) != 0)//if side 2 is part of the order
                                {
                                    for(int x=0; x<ogSides.size();x++)//loop to find the side
                                    {
                                        if(items.getInt(4) == ogSides.get(x).getSideNo())
                                        {
                                            Side side = new Side();//create a copy of the side
                                            side.setName(ogSides.get(x).getName());
                                            side.setSideNo(ogSides.get(x).getSideNo());
                                            side.setPrice(ogSides.get(x).getPrice());
                                            sides.add(side);
                                            break;
                                        }
                                    }
                                }
                                if(items.getInt(5) != 0)//if side 3 is part of the order
                                {
                                    for(int x=0; x<ogSides.size();x++)//loop to find the side
                                    {
                                        if(items.getInt(5) == ogSides.get(x).getSideNo())
                                        {
                                            Side side = new Side();//create a copy of the side
                                            side.setName(ogSides.get(x).getName());
                                            side.setSideNo(ogSides.get(x).getSideNo());
                                            side.setPrice(ogSides.get(x).getPrice());
                                            sides.add(side);
                                            break;
                                        }
                                    }
                                }
                                
                                /* EXTRAS */
                                if(items.getInt(6) != 0)//if extra 1 is part of the order
                                {
                                    for(int x=0; x<ogExtras.size();x++)
                                    {
                                        if(items.getInt(6) == ogExtras.get(x).getExtraNo())
                                        {
                                            Extra extra = new Extra();//create a copy of the extra
                                            extra.setName(ogExtras.get(x).getName());
                                            extra.setExtraNo(ogExtras.get(x).getExtraNo());
                                            extra.setPrice(ogExtras.get(x).getPrice());
                                            extra.setDescription(ogExtras.get(x).getDescription());
                                            extras.add(extra);
                                            break;
                                        }
                                    }
                                }
                                if(items.getInt(7) != 0)//if extra 2 is part of the order
                                {
                                    for(int x=0; x<ogExtras.size();x++)
                                    {
                                        if(items.getInt(7) == ogExtras.get(x).getExtraNo())
                                        {
                                            Extra extra = new Extra();//create a copy of the extra
                                            extra.setName(ogExtras.get(x).getName());
                                            extra.setExtraNo(ogExtras.get(x).getExtraNo());
                                            extra.setPrice(ogExtras.get(x).getPrice());
                                            extra.setDescription(ogExtras.get(x).getDescription());
                                            extras.add(extra);
                                            break;
                                        }
                                    }
                                }
                                if(items.getInt(8) != 0)//if extra 3 is part of the order
                                {
                                    for(int x=0; x<ogExtras.size();x++)
                                    {
                                        if(items.getInt(8) == ogExtras.get(x).getExtraNo())
                                        {
                                            Extra extra = new Extra();//create a copy of the extra
                                            extra.setName(ogExtras.get(x).getName());
                                            extra.setExtraNo(ogExtras.get(x).getExtraNo());
                                            extra.setPrice(ogExtras.get(x).getPrice());
                                            extra.setDescription(ogExtras.get(x).getDescription());
                                            extras.add(extra);
                                            break;
                                        }
                                    }
                                }
                                
                                orderItem.setSides(sides);
                                orderItem.setExtras(extras);
                                o.getItems().add(orderItem);
                            }
                            tables.get(i).getOrders().add(o);//add order to table
                            break;//the for loop
                        }   
                    }
                }
                
                if(!found)//if the table wasn't already made, create it and put the first order (seat) on it
                {
                    /* TABLE CREATION */
                    int tableNo = rs.getInt(2);
                    getNumberOfSeats.setInt(1, tableNo);
                    ResultSet rs1 = getNumberOfSeats.executeQuery();
                    rs1.next();
                    int numSeats = rs1.getInt(1);
                    Table table = new Table(tableNo);
                    table.setNumberOfSeats(numSeats);
                    table.setStatus(true);
                    getServer.setInt(1, tableNo);
                    ResultSet serverRs = getServer.executeQuery();
                    serverRs.next();
                    table.setServerNo(serverRs.getInt(1));
                    /* ORDER CREATION */
                    Order o = new Order();//create a new order for the table
                    o.setOrderNo(rs.getInt(1));//orderNo represents a seat
                    o.setSeatNo(rs.getInt(3));
                    o.setOrderTotal(rs.getDouble(4));

                    cs1.setInt(1, o.getOrderNo());//get all the items for that order number
                    ResultSet items = cs1.executeQuery();//store the items in result set
                    while(items.next())//for each item in the order
                    {
                        Item item = menu.getMenuItem(items.getInt(2));

                        Item orderItem = new Item(item);//copy the item without the sides and extras
                        orderItem.setItemOnOrderNo(items.getInt(1));
                        orderItem.setBasePrice(items.getDouble(10));
                        orderItem.setComment(items.getString(9));//if theres a comment, set the comment

                        ArrayList<Side> sides = new ArrayList<Side>();
                        ArrayList<Extra> extras = new ArrayList<Extra>();

                        ArrayList<Side> ogSides = item.getSides();//get the original items sides
                        ArrayList<Extra> ogExtras = item.getExtras();//get the original items extras

                        /* SIDES */
                        if(items.getInt(3) != 0)//if there is a side
                        {
                            for(int x=0; x<ogSides.size();x++)//loop to find the side
                            {
                                if(items.getInt(3) == ogSides.get(x).getSideNo())
                                {
                                    Side side = new Side();//create a copy of the side
                                    side.setName(ogSides.get(x).getName());
                                    side.setSideNo(ogSides.get(x).getSideNo());
                                    side.setPrice(ogSides.get(x).getPrice());
                                    sides.add(side);
                                    break;
                                }
                            }
                        }
                        if(items.getInt(4) != 0)//if side 2 is part of the order
                        {
                            for(int x=0; x<ogSides.size();x++)//loop to find the side
                            {
                                if(items.getInt(4) == ogSides.get(x).getSideNo())
                                {
                                    Side side = new Side();//create a copy of the side
                                    side.setName(ogSides.get(x).getName());
                                    side.setSideNo(ogSides.get(x).getSideNo());
                                    side.setPrice(ogSides.get(x).getPrice());
                                    sides.add(side);
                                    break;
                                }
                            }
                        }
                        if(items.getInt(5) != 0)//if side 3 is part of the order
                        {
                            for(int x=0; x<ogSides.size();x++)//loop to find the side
                            {
                                if(items.getInt(5) == ogSides.get(x).getSideNo())
                                {
                                    Side side = new Side();//create a copy of the side
                                    side.setName(ogSides.get(x).getName());
                                    side.setSideNo(ogSides.get(x).getSideNo());
                                    side.setPrice(ogSides.get(x).getPrice());
                                    sides.add(side);
                                    break;
                                }
                            }
                        }

                        /* EXTRAS */
                        if(items.getInt(6) != 0)//if extra 1 is part of the order
                        {
                            for(int x=0; x<ogExtras.size();x++)
                            {
                                if(items.getInt(6) == ogExtras.get(x).getExtraNo())
                                {
                                    Extra extra = new Extra();//create a copy of the extra
                                    extra.setName(ogExtras.get(x).getName());
                                    extra.setExtraNo(ogExtras.get(x).getExtraNo());
                                    extra.setPrice(ogExtras.get(x).getPrice());
                                    extra.setDescription(ogExtras.get(x).getDescription());
                                    extras.add(extra);
                                    break;
                                }
                            }
                        }
                        if(items.getInt(7) != 0)//if extra 2 is part of the order
                        {
                            for(int x=0; x<ogExtras.size();x++)
                            {
                                if(items.getInt(7) == ogExtras.get(x).getExtraNo())
                                {
                                    Extra extra = new Extra();//create a copy of the extra
                                    extra.setName(ogExtras.get(x).getName());
                                    extra.setExtraNo(ogExtras.get(x).getExtraNo());
                                    extra.setPrice(ogExtras.get(x).getPrice());
                                    extra.setDescription(ogExtras.get(x).getDescription());
                                    extras.add(extra);
                                    break;
                                }
                            }
                        }
                        if(items.getInt(8) != 0)//if extra 3 is part of the order
                        {
                            for(int x=0; x<ogExtras.size();x++)
                            {
                                if(items.getInt(8) == ogExtras.get(x).getExtraNo())
                                {
                                    Extra extra = new Extra();//create a copy of the extra
                                    extra.setName(ogExtras.get(x).getName());
                                    extra.setExtraNo(ogExtras.get(x).getExtraNo());
                                    extra.setPrice(ogExtras.get(x).getPrice());
                                    extra.setDescription(ogExtras.get(x).getDescription());
                                    extras.add(extra);
                                    break;
                                }
                            }
                        }

                        orderItem.setSides(sides);
                        orderItem.setExtras(extras);
                        o.getItems().add(orderItem);
                    }
                    //add table to the tablemanager array list of tables
                    table.getOrders().add(o);
                    tm.getTables().add(table);
                    rs1.close();
                    serverRs.close();
                    items.close();
                }
            }
            
            getServer.close();
            getNumberOfSeats.close();
            cs.close();
            cs1.close();
            rs.close();
            conn.close();
        } catch (ClassNotFoundException | NamingException ex) {
            ex.printStackTrace();
        } catch(SQLException ex) {
            ex.printStackTrace();
            ex.printStackTrace();
        }
    }
}
