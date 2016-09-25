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
public class Order 
{
    private int orderNo;
    private ArrayList<Item> items;  //an ArrayList containing items for this order
    private int seatNo;             //an order also belongs to a seat number on that table
    private double orderTotal;
    
    /**
     * default constructor for an order object
     */
    public Order()
    {
        items = new ArrayList<Item>();
    }
    
    public Order(int seatNo)
    {
        this.seatNo = seatNo;
    }
    
    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
    
    
    
    

}
