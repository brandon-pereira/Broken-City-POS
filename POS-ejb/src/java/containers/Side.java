/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;

/**
 *
 * @author 642123
 */
public class Side 
{
    private int sideNo;
    private String name;
    private double price;
    
    /**
     * default constructor for a side object
     */
    public Side()
    {
        
    }
    
    /**
     * non default constructor for a side object
     * @param sideNo
     * @param price
     * @param name 
     */
    public Side(int sideNo, double price, String name)
    {
        this.sideNo = sideNo;
        this.price = price;
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getSideNo()
    {
        return sideNo;
    }
    
    public void setSideNo(int sideNo)
    {
        this.sideNo = sideNo;
    }
}
