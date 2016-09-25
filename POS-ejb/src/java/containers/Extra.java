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
public class Extra 
{
    private int extraNo;
    private double price;
    private String name;
    private String description;
    
    /**
     * Default constructor for an extra object
     */
    public Extra()
    {
        
    }
    
    /**
     * non default constructor for an extra object
     * @param extraNo
     * @param price
     * @param name
     * @param description 
     */
    public Extra(int extraNo, double price, String name, String description)
    {
        this.extraNo = extraNo;
        this.price = price;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getExtraNo()
    {
        return extraNo;
    }
    
    public void setExtraNo(int extraNo)
    {
        this.extraNo = extraNo;
    }
}
