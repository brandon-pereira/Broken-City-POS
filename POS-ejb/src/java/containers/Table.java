/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;

import java.util.ArrayList;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author 642123
 */
@Stateful
@LocalBean
public class Table 
{
    private int tableNo;//a table has a number associated with it
    private ArrayList<Order> tableOrders;
    private boolean status;
    private int serverNo;
    private int numberOfSeats;//determines how many different orders a table has
    
    public Table()
    {
    }
    
    /**
     * non default constructor used for creating a new table object with specified table number 
     * @param tableNo 
     */
    public Table(int tableNo)
    {
        this.tableNo = tableNo;
        tableOrders = new ArrayList<Order>();
        numberOfSeats = 0;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    
    public ArrayList<Order> getOrders() 
    {   
        return tableOrders;
    }

    public void setOrders(ArrayList<Order> tableOrders) {
        this.tableOrders = tableOrders;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getServerNo() {
        return serverNo;
    }

    public void setServerNo(int serverNo) {
        this.serverNo = serverNo;
    }
    
    

}
