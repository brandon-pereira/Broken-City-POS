/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;

import brokers.ItemBroker;
import brokers.OrderBroker;
import businessLogic.TableManager;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;


/**
 *
 * @author 642123
 */
@Stateful
@LocalBean

public class Menu
{
    private static ArrayList<Item> menuItems;
    private static Menu instance = null;
    static OrderBroker ob;
    
    /**
     * default, protected constructor for menu object
     */
    protected Menu()
    {
    }
    
    /**
     * public static getInstance method used for Singleton pattern; only one instance of this class 
     * should exist. Calling this method initially will load the menu items in from the database along with
     * all of the current orders, if there are any. The menu will sync with the database every 15 minutes to be consistent
     * with the data in the database.
     * @return 
     */
    public static Menu getInstance()
    {
        if(instance == null)
        {
            instance = new Menu();
            TableManager.getInstance();
            ob = OrderBroker.getInstance();
            
            long millis = TimeUnit.MINUTES.toMillis(15);
            Timer timer = new Timer();
            timer.schedule(new MenuSyncer(), 0, millis);
            try {
                Thread.sleep(2000);//wait 2 seconds for menu to load before loading orders
            } catch (InterruptedException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            ob.loadOrdersFromDatabase();
            System.out.println("--------------------ORDERS LOADED--------------------------");
        }
        
        return instance;
    }
    
    
    /**
     * Method pulls all the menu items from the database and loads them into memory for faster access
     */
    public static void loadMenuIntoMemory()
    {  
        System.out.println("loading menu items...");
        ItemBroker ib = ItemBroker.getInstance();
        menuItems = ib.getAllItems();   
    }
    
    /**
     * Method that returns a reference to an Item object given the item number
     * @param itemNo
     * @return 
     */
    public Item getMenuItem(int itemNo)
    {
        for (Item menuItem : menuItems) 
        {
            if (menuItem.getItemNo() == itemNo) {
                return menuItem;
            }
        }
       
        return null;
    }
    
    /**
     * method returns reference to all of the current menu items
     * @return menuItems
     */
    public ArrayList<Item> getMenuItems() {
        return menuItems;
    }
    
    /**
     * Inner class that is used as a TimerTask to schedule the syncing of the menu every 15 minutes
     */
    private static class MenuSyncer extends TimerTask
    {       
        @Override
        public void run()
        {
            Menu.loadMenuIntoMemory();
        }
        
    }

        
    
}
