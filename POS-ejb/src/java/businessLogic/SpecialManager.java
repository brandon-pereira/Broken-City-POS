/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import brokers.SpecialBroker;
import containers.Item;
import containers.Special;
import java.util.ArrayList;

/**
 *
 * @author 642123
 */
public class SpecialManager 
{
    private static SpecialManager instance = null;
    private static ArrayList<Special> specials = null;
    
    private SpecialManager()
    {
        
    }
    /**
     * getInstance method returns an instance of SpecialManager adhering to the singleton pattern
     * @return 
     */
    public static SpecialManager getInstance()
    {
        if(instance == null)
        {
            instance = new SpecialManager();
            loadSpecials();
        }
        
        return instance;
    }
    /**
     * Method to return a special object given the special number
     * @param num
     * @return 
     */
    public Special getSpecialByNumber(int num)
    {
        for(Special s : specials)
            if(s.getSpecialNo() == num) { return s; }
        return null;
    }
    /**
     * Method to return all specials
     * @return ArrayList<Special>
     */
    public ArrayList<Special> getSpecials()
    {
        return specials;
    }
    /**
     * Method to call the broker to load the specials from the database into memory
     */
    private static void loadSpecials()
    {
        SpecialBroker sb = SpecialBroker.getInstance();
        specials = sb.getAllSpecials();
    }
    /**
     * Method to pass a new special to the broker to be added into the database
     * @param s 
     */
    public void addSpecial(Special s)
    {
        specials.add(s);
        SpecialBroker sb = SpecialBroker.getInstance();
        sb.addSpecial(s);//persist special to database
    }
    /**
     * Passes a special with new information to be modified for the special into the database
     * @param s
     * @param days
     * @param discountedPrice
     * @param startTime
     * @param endTime
     * @param items
     * @param name 
     */
    public void modifySpecial(Special s, ArrayList<Integer> days, double discountedPrice, String startTime, String endTime, ArrayList<Item> items, 
            String name)
    {
        s.setDayOfWeek(days);
        s.setDiscountedPrice(discountedPrice);
        s.setStartTime(startTime);
        s.setEndTime(endTime);
        s.setItems(items);
        s.setName(name);
        SpecialBroker sb = SpecialBroker.getInstance();
        sb.modifySpecial(s);
    }
    /**
     * Passes a special to the broker to be removed from the database.
     * @param s 
     */
    public void removeSpecial(Special s)
    {
        SpecialBroker sb = SpecialBroker.getInstance();
        sb.removeSpecial(s);
        specials.remove(s);
    }
    /**
     * Gets the highest special id from memory and returns the next id number
     */ 
    public int getNextSpecialId()
    {
        int highId=0;
        for (Special special : specials) 
        {
            int specialNo = special.getSpecialNo();
            if(specialNo > highId)
                highId = specialNo;
        }
        
        return highId + 1;
    }
}
