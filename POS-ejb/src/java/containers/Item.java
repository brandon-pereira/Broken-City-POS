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
public class Item 
{
    private int itemOnOrderNo;//this will be used to uniquely identify an item on an order(duplicates)
    private int itemNo;
    private String itemName;
    private String itemDesc;
    private double basePrice;
    private double cost;
    private int numberOfSides;
    private int numberOfExtras;
    private int isActive;
    private String itemType;
    private String comment;
    private ArrayList<Side> sides;//included in the basePrice
    private ArrayList<Extra> extras;
    
    /**
     * default constructor for an item object
     */
    public Item()
    {
    }
    
    /**
     * Copy constructor for an item object: used to copy an item without the sides and extras
     * @param item 
     */
    public Item(Item item)
    {
        this.itemOnOrderNo = item.itemOnOrderNo;//this will be used to uniquely identify an item (duplicates)
        this.itemNo = item.itemNo;
        this.itemName = item.itemName;
        this.itemDesc = item.itemDesc;
        this.basePrice = item.basePrice;
        this.cost = item.cost;
        this.numberOfSides = item.numberOfSides;
        this.numberOfExtras = item.numberOfExtras;
        this.isActive = item.isActive;
        this.itemType = item.itemType;
        this.comment = item.comment;
    }
    
    /**
     * copy constructor for an item object: used to copy an item with sides and extras
     * @param item
     * @param withSides 
     */
    public Item(Item item, boolean withSides)//copy constructor to copy an item with the sides and extras
    {
        this.itemOnOrderNo = item.itemOnOrderNo;
        this.itemNo = item.itemNo;
        this.itemName = item.itemName;
        this.itemDesc = item.itemDesc;
        this.basePrice = item.basePrice;//add gst to the item
        this.cost = item.cost;
        this.numberOfSides = item.numberOfSides;
        this.numberOfExtras = item.numberOfExtras;
        this.isActive = item.isActive;
        this.itemType = item.itemType;
        this.comment = item.comment;
        this.sides = item.sides;//included in the basePrice
        this.extras = item.extras;
    }
    
    /**
     * non default constructor for an item object
     * @param itemNo
     * @param itemName
     * @param itemDesc
     * @param basePrice
     * @param cost
     * @param numberOfSides
     * @param numberOfExtras
     * @param itemType
     * @param isActive
     * @param sides
     * @param extras 
     */
    public Item(int itemNo, String itemName, String itemDesc, double basePrice, double cost, int numberOfSides, int numberOfExtras, String itemType, int isActive, ArrayList<Side> sides, ArrayList<Extra> extras)
    {
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.basePrice = basePrice;
        this.cost = cost;
        this.numberOfSides = numberOfSides;
        this.numberOfExtras = numberOfExtras;
        this.itemType = itemType;
        this.isActive = isActive;
        this.sides = sides;
        this.extras = extras;
    }
    
    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getItemDesc() {
        return itemDesc;
    }
    
    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double baseprice) {
        this.basePrice = baseprice;
    }

    public ArrayList<Side> getSides() {
        return sides;
    }

    public void setSides(ArrayList<Side> sides) {
        this.sides = sides;
    }

    public ArrayList<Extra> getExtras() {
        return extras;
    }

    public void setExtras(ArrayList extras) {
        this.extras = extras;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getNumberOfSides() {
        return numberOfSides;
    }

    public void setNumberOfSides(int numberOfSides) {
        this.numberOfSides = numberOfSides;
    }

    public int getNumberOfExtras() {
        return numberOfExtras;
    }

    public void setNumberOfExtras(int numberOfExtras) {
        this.numberOfExtras = numberOfExtras;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getItemOnOrderNo() {
        return itemOnOrderNo;
    }

    public void setItemOnOrderNo(int itemOnOrderNo) {
        this.itemOnOrderNo = itemOnOrderNo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    /**
     * This method will return an ArrayList<Extra> of all the extras that belong together
     * ie. have the same item Description, based on the extraNo of 1,2 or 3
     * 
     * Pre-condition: Pass in and know the number of the extras being searched for; 1, 2 or 3
     * @param extraNo
     * @return ArrayList of extras that belong to the same description
     */
    public ArrayList<Extra> getExtraNo(int extraNo)
    {
        ArrayList<Extra> ex = new ArrayList<Extra>();
        
        String extra1Desc = "";
        String extra2Desc = "";
        String extra3Desc = "";
        
        for(Extra e:extras)
        {
            //Check if we have the first extra description, if not set it to current description
            if(extra1Desc.equals(""))
                extra1Desc = e.getDescription();
            //Check if we are looking for another extra description, else check if the item belongs to this description and add it
            if(extraNo > 1)
            {
                //Check if wehave the second extra description, if not set it to the current description if it does not match the first
                if(extra2Desc.equals("") && !extra1Desc.equals(e.getDescription()))
                    extra2Desc = e.getDescription();
                //Check if we are looking for an extra after the second, else check if the item belongs to this description and add it
                if(extraNo > 2 && !extra2Desc.equals(""))
                {
                    //Check if we have the third extra description, if not set it to the current description if it does not match the first or second
                    if(extra3Desc.equals("") && !extra2Desc.equals(e.getDescription()) && !extra1Desc.equals(e.getDescription()))
                        extra3Desc = e.getDescription();
                    if(extra3Desc.equals(e.getDescription()))
                        ex.add(e);
                }
                else if(extra2Desc.equals(e.getDescription()))
                    ex.add(e);
            }
            else if(extra1Desc.equals(e.getDescription()))
                ex.add(e);
        }
        
        return ex;
    }
    
}
