/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import brokers.OrderBroker;
import containers.Order;
import containers.Table;
import java.util.ArrayList;

/**
 *
 * @author 504724
 */
public class TableManager {
    
    private static TableManager instance = null;
    private static ArrayList<Table> tables;
    private static Table speedBarTable = null;//singleton used for the speedbar table
    
    /**
     * default private constructor for table manager
     */
    private TableManager()
    {       
    }
    
    /**
     * public static getInstance method for singleton pattern; only one instance of this class should exist
     * @return instance
     */
    public static TableManager getInstance()
    {
        if(instance ==null)
        {
            instance = new TableManager();
            tables = new ArrayList<Table>();
        }
        return instance;
    }
    
    /**
     * returns a reference to the speed bar table being used.
     * There will only ever be one speed bar table being used so a singleton like pattern
     * will be used for the table reference
     * @param empNo
     * @return speedBarTable
     */
    public Table getSpeedBarTable(int empNo)
    {
        if(speedBarTable == null)
        {
            speedBarTable = new Table();
            speedBarTable.setTableNo(100);
            speedBarTable.setOrders(new ArrayList<Order>());
            speedBarTable.setStatus(true);
            speedBarTable.setNumberOfSeats(1);
            speedBarTable.setServerNo(empNo);
            tables.add(speedBarTable);
            
            ArrayList<Order> orders = speedBarTable.getOrders();

            Order o = new Order();//create a blank order for that seat
            o.setSeatNo(speedBarTable.getNumberOfSeats());
            orders.add(o);//add the blank order to the table
        }
        
        return speedBarTable;
    }
    
    /**
     * Method that takes in two tables, the table to merge from and the table to merge to.
     * Will transfer all orders to the "to" table
     */
    public void mergeTables(Table from, Table to)
    {
        OrderBroker ob = OrderBroker.getInstance();
        
        // Get 'To' Table information
        ArrayList<Order> ordersTo = to.getOrders();
        int numberOfSeatsTo = to.getNumberOfSeats();
        
        // Get 'From' Table information
        ArrayList<Order> ordersFrom = from.getOrders();
        int numberOfSeatsFrom = from.getNumberOfSeats();
        int tableNumberFrom = from.getTableNo();
        
        // Add 'From' orders to 'To' orders
        int newSeatNo = numberOfSeatsTo;
        System.out.println("new seat number: " + newSeatNo);
        for(int i = 0; i < ordersFrom.size(); i++)//loop through the old orders and set the new seat numbers for them
        {
            newSeatNo++;
            ob.moveSeat(tableNumberFrom, ordersFrom.get(i).getSeatNo(), newSeatNo);
            ordersFrom.get(i).setSeatNo(newSeatNo);
        }
        ordersTo.addAll(ordersFrom);
        
        OrderManager om = OrderManager.getInstance();
        for (Order order : ordersTo) 
        {
            om.updateOrderTotal(order);
        }
        
        // Increase number of seats of 'To' / Set 'From' to 0
        to.setNumberOfSeats(numberOfSeatsTo+numberOfSeatsFrom);
        from.setNumberOfSeats(0);
        
        // Remove orders from 'From'
        for(int i=0; i<ordersFrom.size(); i++)
        {
            ordersFrom.remove(i);
        }
        ordersFrom = null;//gc the orders ArrayList
        // Disable Table
        from.setStatus(false);
        tables.remove(from);//remove the old table from the tables
        
        ob.mergeTables(tableNumberFrom, to.getTableNo());//need to pass in seat numbers
    }
    
    /**
     * Method used to close a table when the table has no balance left on it
     * @param t 
     */
    public void closeTable(Table t)
    {
        for(int i = 0; i < tables.size(); i++)
        {
            Table table = tables.get(i);
            if(table.getTableNo() == t.getTableNo())
            {
                table = null;
                tables.remove(i);//remove the table from current tables
                break;
            }
        }
        
        OrderBroker ob = OrderBroker.getInstance();
        ob.clearTable(t);//call broker to close table
    }
    
    /**
     * Method used to clear the speed bar table orders
     * @param t 
     */
    public void clearTable(Table t)
    {
        OrderManager om = OrderManager.getInstance();
        om.resetItemOnOrderCount();
        speedBarTable.setOrders(new ArrayList<Order>());//reset the orders
    }
    
    
    /**
     * returns a reference to all of the current tables that are open
     * @return tables
     */
    public ArrayList<Table> getTables()
    {
        return tables;
    }
    
    /**
     * Method that returns a reference to a specific open table
     * @param tableNo
     * @return t - if the table exists
     */
    public Table getTable(int tableNo)
    {
        for(int i = 0; i < tables.size(); i++)
        {
            Table t = tables.get(i);
            if(t.getTableNo() == tableNo)
                return t;
        }
        
        return null;
    }
}
