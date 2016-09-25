/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import brokers.ItemBroker;
import containers.Item;
import containers.Menu;
import java.util.ArrayList;

/**
 * ItemManager class handles all apllication logic for everything involving Items and item information.
 * @author 504724
 */
public class ItemManager {
    
    private static ItemManager instance = null;
    
    private ItemManager()
    {
        
    }
    /**
     * getInstance method returns an instance of ItemManager. Follows the singleton pattern, allowing only one instance of ItemManager to exist.
     * @return 
     */
    public static ItemManager getInstance()
    {
        if(instance == null)
        {
            instance = new ItemManager();
        }
        return instance;
    }
    /**
     * addItem method passes the information from the controller to the broker to persist a new item into the database
     * Memory is sync'd with the database after the changes are persisted
     * @param item
     * @return 
     */
    public int addItem(Item item)
    {
        ItemBroker ib = ItemBroker.getInstance();
        int itemNumber = ib.addItem(item);
        syncMenu();
        return itemNumber;
    }
    /**
     * modifyItem method passes the information from the controller to the broker to persist item changes into the database
     * Memory is sync'd with the database after the changes are persisted
     * @param item 
     */
    public void modifyItem(Item item)
    {
        ItemBroker ib = ItemBroker.getInstance();
        ib.modifyItem(item);
        syncMenu();
    }
    /**
     * removeItem method passes the itemnumber of the item being removed to the broker to remove the information from the database
     * Memory is sync'd with the database after the changes are persisted
     * @param itemNumber 
     */
    public void removeItem(int itemNumber)
    {
        ItemBroker ib = ItemBroker.getInstance();
        ib.deleteItem(itemNumber);
        syncMenu();
    }
    /**
     * syncMenu method syncs the menu information stored in memory with the information in the database
     */
    private void syncMenu()
    {
        Menu.loadMenuIntoMemory();
    }
}
