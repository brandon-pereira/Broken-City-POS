package businessLogic;


import brokers.EmployeeBroker;
import containers.*;
import brokers.OrderBroker;
import java.text.DecimalFormat;
import java.util.ArrayList;


/*
 * This class will be used to manage table objects, and manipulating orders that are part
 * of this table object
 */

/**
 *
 * @author 642123
 */
public class OrderManager 
{
    private static OrderManager instance = null;
    private int nextId = 0;
    
    protected OrderManager()
    {
    }
    
    public static OrderManager getInstance()
    {
        if(instance == null)
            instance = new OrderManager();
        
        return instance;
    }
    
    /**
     * Method that moves an item from one order to another
     * @param table - the table the item is at
     * @param newSeatNo - the seat to transfer the item to
     * @param currentSeatNo - the current seat the item is located
     * @param itemOnOrderNo - the item on order number represents an item object
     */
    public void moveItem(Table table, int newSeatNo, int currentSeatNo, int itemOnOrderNo)
    {
        ArrayList<Order> orders = table.getOrders();
        Order orderToAddItem = new Order();
        int itemOnOrder = 0;
        int newOrderNo = 0;
        
        for(int k = 0; k < orders.size(); k++)//loop to find the order to add the item to
        {
            Order o = orders.get(k);
            if(o.getSeatNo() == newSeatNo)
            {
                newOrderNo = o.getOrderNo();
                orderToAddItem = o;//the order to add the item to
                break;
            }
        }
        
        for(int i = 0; i < orders.size(); i++)//loop to find the order the item is on, to remove it and add it to the other order
        {
            Order order = orders.get(i);
            if(order.getSeatNo() == currentSeatNo)
            {
                ArrayList<Item> items = order.getItems();
                for(int j = 0; j < items.size(); j++)
                {
                    Item item = items.get(j);
                    if(item.getItemOnOrderNo() == itemOnOrderNo)
                    {
                        itemOnOrder = item.getItemOnOrderNo();
                        orderToAddItem.getItems().add(item);//add it to the other seats order
                        order.getItems().remove(item);//remove it from the current order
                        updateOrderTotal(order);
                        updateOrderTotal(orderToAddItem);
//                        if(o.getItems().isEmpty())
//                            o.setOrderTotal(0);
                        break;
                    }
                }
                break;
            }
        }
        
        OrderBroker ob = OrderBroker.getInstance();//call the db procedure to move the item and reflect changes
        ob.moveItem(itemOnOrder, newOrderNo);
    }
    
    /**
     * Method used to get a reference to a specific item on an order
     * @param t
     * @param seatNo
     * @param itemOnOrderNo
     * @return item
     */
    public Item getItemFromOrder(Table t, int seatNo, int itemOnOrderNo)
    {
        ArrayList<Order> orders = t.getOrders();
        for(int i = 0; i < orders.size(); i++)
        {
            Order o = orders.get(i);
            if(o.getSeatNo() == seatNo)
            {
                ArrayList<Item> items = o.getItems();
                for(int j = 0; j < items.size(); j++)
                {
                    Item item = items.get(j);
                    if(item.getItemOnOrderNo() == itemOnOrderNo)
                    {
                        return item;//return reference to the item
                    }
                }
            }
        }
        
        return null;
    }
    
    /**
     * Method that verifies order options (for example when an item is voided)
     * @param userID
     * @return 
     */
    public boolean getManagerApproval(int userID)
    {
        EmployeeBroker eb = EmployeeBroker.getInstance();
        boolean approved = eb.getManagerApproval(userID);
        return approved;
    }
    
    /**
     * Method called when an order is payed for
     * @param t
     * @param seatNo 
     * @param payAmount 
     * @param paymentType 
     */
    public void acceptPayment(Table t, int seatNo, double payAmount, String paymentType)
    {
        ArrayList<Order> orders = t.getOrders();
        for(Order o : orders)
        {
            if(o.getSeatNo() == seatNo)
            {
                o.setOrderTotal(o.getOrderTotal() - payAmount);
                if(o.getOrderTotal() <= 0)
                {
                    OrderBroker ob = OrderBroker.getInstance();//persist the order to the log table
                    ob.addToOrderLog(o, paymentType);
                }
                break;
            }
        }

    }
    
    /**
     * Method that adds an item to an order at specified table
     * @param table - the table to add item to
     * @param seatNo - the seat number in which to add the order to
     * @param item - the item to add
     */
    public void addItemToSeat(Table table, int seatNo, Item item)
    {
        ArrayList<Order> orders = table.getOrders();
        if(orders.isEmpty())
        {
            Order o = new Order();//create a new order
            o.setSeatNo(seatNo);//set the seat number for the order
            ArrayList<Item> items = o.getItems();//get reference to arraylist of items for the order
            items.add(item);//add the item to the order
            
            orders.add(o);//add the order 
            table.setOrders(orders);//add the order to the table          
        }
        else
        {
            for(int i = 0; i < orders.size();i++)
            {
                Order o = orders.get(i);
                if(o.getSeatNo() == seatNo)
                {
                    ArrayList<Item> items = o.getItems();
                    items.add(item);
                    break;
                }
            }
        }
    }
    
    /**
     * Method used for adding an additional seat to a table
     * @param table - the table in which to add a seat
     */
    public void addSeatToTable(Table table)
    {
        OrderBroker ob = OrderBroker.getInstance();
        table.setNumberOfSeats(table.getNumberOfSeats() + 1);//add to the number of seats at the table
        ArrayList<Order> orders = table.getOrders();
        
        Order o = new Order();//create a blank order for that seat
        o.setSeatNo(table.getNumberOfSeats());
        o.setOrderNo(ob.addNewOrder(table.getTableNo(), o.getSeatNo(), table.getServerNo()));//fake server number; create a row in the database for future use
        orders.add(o);//add the blank order to the table
    }
    
    /**
     * Method used for adding an additional seat to a table
     * @param table - the table in which to add a seat
     * @param isBar - true if the seat is to be added to the speed bar
     */
    public void addSeatToTable(Table table, boolean isBar)
    {
        table.setNumberOfSeats(table.getNumberOfSeats() + 1);//add to the number of seats at the table
        ArrayList<Order> orders = table.getOrders();
        
        Order o = new Order();//create a blank order for that seat
        o.setSeatNo(table.getNumberOfSeats());
        orders.add(o);//add the blank order to the table
    }
    
    /**
     * Method that updates the total price of an order
     * @param o
     * @param item 
     */
    public void updateOrderTotal(Order o)
    {
        DecimalFormat dec = new DecimalFormat("##,##0.00");
        double totalPrice = 0;
        ArrayList<Item> items = o.getItems();
        for(Item item : items)
        {
            totalPrice += item.getBasePrice();
            ArrayList<Side> sides = item.getSides();
            ArrayList<Extra> extras = item.getExtras();
            if(sides != null)
            {
                if(!sides.isEmpty())
                {
                    for(Side s : sides)                
                        totalPrice += s.getPrice();
                }
            }
            
            if(extras != null)
            {
                if(!extras.isEmpty())
                {
                    for(Extra e : extras)
                        totalPrice += e.getPrice();
                }
            }
        }
        
        o.setOrderTotal(Double.parseDouble(dec.format(totalPrice * 1.05)));
    }
    /**
     * method that modified the price of an item that is being discounted on an order
     * @param t
     * @param seatNo
     * @param itemNo
     * @param discount 
     */
    public void discountItemFromSeat(Table t, int seatNo, int itemNo, double discount)       
    {
        DecimalFormat dec = new DecimalFormat("##,##0.00");
        double disc = 1 - (discount / 100);
        dec.format(disc);
        ArrayList<Order> orders = t.getOrders();
        for (Order o : orders) 
        {
            if(o.getSeatNo() == seatNo)
            {
                ArrayList<Item> items = o.getItems();
                for (Item item : items) 
                {
                    if(item.getItemOnOrderNo() == itemNo)
                    {
                        item.setBasePrice(item.getBasePrice() * disc);
                        return;
                    }
                }
            }
        }
    }
    
    /**
     * Method that returns an order based on the seat number of the order
     * @param t
     * @param seatNo
     * @return 
     */
    public Order getOrderFromTable(Table t, int seatNo)
    {
        ArrayList<Order> orders = t.getOrders();
        for(Order o : orders)
        {
            if(o.getSeatNo() == seatNo)
                return o;
        }
        
        return null;
    }
    
    /**
     * Method that is used to void an item off of an order
     * @param table - the table to remove an item from
     * @param seatNo - the seat number of the item to remove
     * @param itemNo - the itemNo to remove
     * @return 
     */
    public boolean voidItemFromSeat(Table table, int seatNo, int itemNo)
    {
        ArrayList<Order> orders = table.getOrders();
        for(int i = 0; i < orders.size();i++)
        {
            Order o = orders.get(i);
            if(o.getSeatNo() == seatNo)
            {
                ArrayList<Item> items = o.getItems();
                for(int j = 0; j < items.size(); j++)//find the item to remove
                {
                    Item item = items.get(j);
                    if(item.getItemOnOrderNo() == itemNo)
                    {
                        if(item.getBasePrice() == 0)//then that item was already voided
                        {
                            return false;
                        }
                        else
                        {
                            item.setBasePrice(0);//set the voided items price to be 0
                            item.setExtras(new ArrayList<Extra>());
                            item.setSides(new ArrayList<Side>());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    } 
    
    /**
     * Method used for adding a comment to an item from an order
     * @param table
     * @param seatNo
     * @param itemNo
     * @param comment 
     */
    public void addCommentToItem(Table table, int seatNo, int itemNo, String comment)
    {
        ArrayList<Order> orders = table.getOrders();
        for(int i = 0; i < orders.size();i++)
        {
            Order o = orders.get(i);
            if(o.getSeatNo() == seatNo)
            {
                ArrayList<Item> items = o.getItems();
                for(int j = 0; j < items.size(); j++)//find the item to add the comment to
                {
                    Item item = items.get(j);
                    if(item.getItemOnOrderNo() == itemNo)
                    {
                        item.setComment(comment);
                        break;
                    }
                }
                break;
            }
        }
    }
    
    /**
     * Method returns the order number for a specific seat at a specific table
     * @param table
     * @param seatNo
     * @return 
     */
    public int getOrderNo(Table table, int seatNo)
    {
        int tableNo = table.getTableNo();
        TableManager tm = TableManager.getInstance();
        ArrayList<Table> tables = tm.getTables();
        
        for(int i = 0; i < tables.size(); i++)
        {
            if(tables.get(i).getTableNo() == tableNo)
            {
                ArrayList<Order> orders = tables.get(i).getOrders();//get the orders for the table
                for(int k = 0; k < orders.size(); k++)
                {
                    if(orders.get(k).getSeatNo() == seatNo)
                    {
                        return orders.get(k).getOrderNo();
                    }
                }
            }
        }

        return 0;
    }
    /**
     * assigns the next Item on Order number to an item
     * @param i 
     */
    public void assignItemOnOrderNo(Item i)
    {
        nextId += 1;
        i.setItemOnOrderNo(nextId);
    }
    /**
     * resets the item on order count
     */
    public void resetItemOnOrderCount()
    {
        nextId = 0;
    }
    
    /**
     * Method to split an item into multiple seats
     * @param orders 
     */
    public void splitItem(ArrayList<Order> orders, Item item)
    {
        int numWays = orders.size();
        double priceOfItem = item.getBasePrice();
        double priceForEachOrder = priceOfItem / (numWays+1);
        item.setBasePrice(priceForEachOrder);
        //if price is / 3 .. need to give penny to one person
        for(int i = 0; i < numWays; i++)
        {
            Order o = orders.get(i);//get the order to put the split item on
            Item splitItem = new Item(item, true);
            splitItem.setBasePrice(priceForEachOrder);
            o.getItems().add(splitItem);
            updateOrderTotal(orders.get(i));
        }
        
    }
    
    /**
     * 
     * @param t - Table object, from the session
     * @param seatNo - Seat Number, for the current seat being modified
     * @param itemOnOrderNumber - The unique item number for the item being modified
     * @param newSides  - ArrayList<Side> of new sides to be added to the item
     */
    public void setNewSides(Table t, int seatNo, int itemOnOrderNumber, ArrayList<Side> newSides)
    {
        getItemFromOrder(t, seatNo, itemOnOrderNumber).setSides(newSides);
        getItemFromOrder(t, seatNo, itemOnOrderNumber).setNumberOfSides(newSides.size());
    }
    /**
     * 
     * @param t - Table object, from the session
     * @param seatNo - Seat Number, for the current seat being modified
     * @param itemOnOrderNumber - The unique item number for the item being modified
     * @param newExtras - ArrayList<Extra> of new extras to be added to the item
     */
    public void setNewExtras(Table t, int seatNo, int itemOnOrderNumber, ArrayList<Extra> newExtras)
    {
        getItemFromOrder(t, seatNo, itemOnOrderNumber).setExtras(newExtras);
        getItemFromOrder(t, seatNo, itemOnOrderNumber).setNumberOfExtras(newExtras.size());
    }
    /***************************PRIVATE METHODS*****************************************/
    
}
