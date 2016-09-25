/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brokers;

import containers.Item;
import containers.Menu;
import containers.Special;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author 504724
 */
public class SpecialBroker 
{
    private static SpecialBroker instance = null;
    
    /**
     * private constructor for special broker
     */
    private SpecialBroker()
    {
    }
    
    /**
     * public static getInstance method used for singleton pattern used in this class
     * @return 
     */
    public static SpecialBroker getInstance()
    {
        if(instance == null)
        {
            instance = new SpecialBroker();
        }
        
        return instance;
    }
    
    /**
     * Method used to persist a new special object  into the database
     * @param s 
     */
    public void addSpecial(Special s)
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call addSpecial(?,?,?)");
            //cs.setInt(1, s);
            //add to the special table
            cs.setString(1, s.getName());
            cs.setString(2, s.getStartTime());
            cs.setString(3, s.getEndTime());
            cs.executeQuery();
            cs.close();
            //add to the special_days table
            //special no, day of week
            CallableStatement cs1 = conn.prepareCall("call addSpecialDay(?,?)");
            ArrayList<Integer> days = s.getDayOfWeek();
            for (Integer day : days) 
            {
                cs1.setInt(1, s.getSpecialNo());
                cs1.setInt(2, day);
                cs1.executeQuery();
            }
            cs1.close();
            //special_item
            //special no, itemno, discount price, original price
            CallableStatement cs2 = conn.prepareCall("call addSpecialItem(?,?,?)");
            ArrayList<Item> items = s.getItems();
            for(Item i : items)
            {
                cs2.setInt(1, s.getSpecialNo());
                cs2.setInt(2, i.getItemNo());
                cs2.setDouble(3, s.getDiscountedPrice());
                cs2.executeQuery();
            }
            cs2.close();
            
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * method used to modify a special within the database
     * @param s 
     */
    public void modifySpecial(Special s)
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call modifySpecial(?,?,?,?)");
            //update special set name = name, starttime = time, end time = endtime where specialno = no;
            cs.setInt(1, s.getSpecialNo());
            cs.setString(2, s.getName());
            cs.setString(3, s.getStartTime());
            cs.setString(4, s.getEndTime());           
            cs.close();
            
            CallableStatement cs1 = conn.prepareCall("call removeSpecialDay(?)");//remove the special days
            cs1.setInt(1, s.getSpecialNo());
            cs1.executeQuery();
            cs1.close();
            //add the new special days
            CallableStatement cs2 = conn.prepareCall("call addSpecialDay(?,?)");
            ArrayList<Integer> days = s.getDayOfWeek();
            for (Integer day : days) 
            {
                cs2.setInt(1, s.getSpecialNo());
                cs2.setInt(2, day);
                cs2.executeQuery();
            }
            cs2.close();
            
            CallableStatement cs3 = conn.prepareCall("call removeSpecialItem(?)");
            cs3.setInt(1, s.getSpecialNo());
            cs3.executeQuery();
            cs3.close();
            //add the new special items
            CallableStatement cs4 = conn.prepareCall("call addSpecialItem(?,?,?)");
            ArrayList<Item> items = s.getItems();
            for(Item i : items)
            {
                cs4.setInt(1, s.getSpecialNo());
                cs4.setInt(2, i.getItemNo());
                cs4.setDouble(3, s.getDiscountedPrice());
                cs4.executeQuery();
            }
            cs4.close();
            
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * method used to remove a special  from the database
     * @param s 
     */
    public void removeSpecial(Special s)
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call removeSpecial(?)");
            cs.setInt(1, s.getSpecialNo());
            cs.executeQuery();
            
            cs.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method used for retrieving all of the specials on application startup
     * @return 
     */
    public ArrayList<Special> getAllSpecials()
    {
        ArrayList<Special> specials = new ArrayList<Special>();
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call getAllSpecials()");
            CallableStatement cs1 = conn.prepareCall("call getDaysForSpecial(?)");
            CallableStatement cs2 = conn.prepareCall("call getItemsForSpecial(?)");
            ResultSet rs = cs.executeQuery();
            ResultSet rs1 = null;

            while(rs.next())
            {
                ArrayList<Integer> days = new ArrayList<Integer>();
                cs1.setInt(1, rs.getInt(1));
                rs1 = cs1.executeQuery();
                
                while(rs1.next())//result set filled with all of the days for the special
                {
                    days.add(rs1.getInt(1));//add the day to the array list
                }
                
                ArrayList<Item> items = new ArrayList<Item>();
                cs2.setInt(1, rs.getInt(1));
                ResultSet rs2 = cs2.executeQuery();
                while(rs2.next())//result set filled with all the item numbers for the special
                {
                    Item i = Menu.getInstance().getMenuItem(rs2.getInt(1));//these will be used to get a reference to the baseprice of the item only
                    items.add(i);
                }
                
                PreparedStatement ps = conn.prepareCall("select distinct discounted from special_item where fk_special_item=" + rs.getInt(1));
                ResultSet rs3 = ps.executeQuery();
                rs3.next();
                double discountedPrice = rs3.getDouble(1);
                
                Special s = new Special(rs.getInt(1), items, rs.getString(2), days, rs.getString(3), rs.getString(4), 
                                        discountedPrice);

                specials.add(s);
                rs1.close();
                rs2.close();
                rs3.close();
            }
            cs1.close();
            cs2.close();
            cs.close();
            rs.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return specials;
    }
}
