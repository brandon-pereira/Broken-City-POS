/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brokers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Broker class for employee shift management. Provides
 * methods for admin management.
 * @author 642123
 */
@Stateless
@LocalBean
public class ShiftBroker 
{
    private static ShiftBroker instance = null;
    
    protected ShiftBroker() 
    {
    }
    
    /**
     * Creates an instance of the shift broker to be used by the controllers.
     * @return instance
     */
    public static ShiftBroker getInstance()
    {
        if(instance == null)
        {
            instance = new ShiftBroker();
        }
        
        return instance;
    }

    /**
     * Method that checks if an employee is currently
     * clocked in.
     * @param userID user ID
     * @return boolean status
     */
    public boolean isClockedIn(int userID)
    {
        boolean retval = false;
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call isClockedIn(?)");
            cs.setInt(1, userID);
            ResultSet rs = cs.executeQuery();

            rs.next();
            if(rs.getString(1) != null && rs.getString(1).equalsIgnoreCase("y"))
                retval = true;

            cs.close();
            rs.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShiftBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ShiftBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ShiftBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retval;
    }
    
    /**
     * Method that checks if an employee is active.
     * @param userID User ID
     * @return boolean active status
     */
    public boolean isActive(int userID)
    {
        boolean active = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();
            
            CallableStatement cs = conn.prepareCall("call isActive(?)");
            cs.setInt(1, userID);
            ResultSet rs = cs.executeQuery();
            
            rs.next();
            if(rs.getInt(1) == 1)
                active = true;
            
            rs.close();
            cs.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShiftBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ShiftBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ShiftBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return active;
    }

    /**
     * Method that clocks in an employee given the employee number.
     * @param userID User ID
     */
    public void clockUserIn(int userID)
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call startShift(?)");
            cs.setInt(1, userID);
            cs.executeQuery();

            cs.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShiftBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ShiftBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ShiftBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method that clocks out an employee given the employee number.
     * @param userID User ID
     */
    public void clockUserOut(int userID)
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call endShift(?)");
            cs.setInt(1, userID);
            cs.executeQuery();

            cs.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShiftBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ShiftBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ShiftBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
