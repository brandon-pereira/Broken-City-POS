/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brokers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Broker Class for export and report management. 
 * Provides database connection and returns relevant
 * information for the controller classes.
 * @author 504724/532485
 */
public class AdminBroker {
   
    private static AdminBroker instance = null;
    
    private AdminBroker()
    {
        
    }
    
    /**
     * Method to be used by the controllers
     * to create an instance of AdminBroker class
     * @return instance
     */
    public static AdminBroker getInstance()
    {
        if(instance == null)
        {
            instance = new AdminBroker();
        }
        return instance;
    }
    
    /**
     * Method that returns a list of export information
     * depending on the date range.
     * @param startDate Start date
     * @param endDate End date
     * @return entry Export list data
     */
    public ArrayList<String[]> getExportData(String startDate, String endDate)
    {
        ArrayList<String[]> entries = new ArrayList<String[]>();
        
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call getExportData(?,?)");
            cs.setString(1,startDate);
            cs.setString(2,endDate);
            ResultSet rs = cs.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();         

            while(rs.next())
            {
                String[] entry = new String[rsmd.getColumnCount()];
                //For loop each column
                for(int i =0;i<rsmd.getColumnCount();i++)
                {
                    //Get the value of each column and insert into the entry String[]
                    entry[i] = rs.getString(i+1);
                }
                
                entries.add(entry);
            }           

            cs.close();
            rs.close();
            conn.close();
            
        } catch (ClassNotFoundException | NamingException | SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entries;
    }
    
    /**
     * Method that returns employee hours depending on the employee
     * number and date range
     * @param empno Employee number
     * @param startDate Start date
     * @param endDate End date
     * @return hours Employee hours
     */
    public String getEmployeeHours(int empno, String startDate, String endDate)
    {
        String hours="";
        
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call reportOneEmployee(?,?,?)");
            cs.setInt(1,empno);
            cs.setString(2,startDate);
            cs.setString(3,endDate);
            ResultSet rs = cs.executeQuery();
            
            while(rs.next())
            {    
                hours = rs.getString(2);
            }           
            
            cs.close();
            rs.close();
            conn.close();
            
        } catch (ClassNotFoundException | NamingException | SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hours;
    }
    
    /**
     * Method that returns a list of employee information for reporting.
     * @param empNo Employee number
     * @return entry Employee report list
     */
    public ArrayList<String> getEmployeeInfo(String empNo)
    {
        Integer emp = Integer.valueOf(empNo);
        ArrayList<String> entry = new ArrayList();
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call getEmployee(?)");
            cs.setInt(1,emp);
            ResultSet rs = cs.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next())
            {
                //For loop each column
                for(int i=1;i<rsmd.getColumnCount();i++)
                {
                    //Get the value of each column and insert into the entry String[]
                    entry.add(rs.getString(i));
                }
            }           
            
            cs.close();
            rs.close();
            conn.close();
            
        } catch (ClassNotFoundException | NamingException | SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }        
                
        return entry;
    }
    
    /**
     * Method that returns a list of order depending on a given date range.
     * @param startDate Start date
     * @param endDate End date
     * @return entries List of orders
     */
    public ArrayList<String> getOrderByDate(String startDate, String endDate)
    {
        ArrayList<String> entries = new ArrayList<String>();
        
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call reportOrderByDate(?,?)");
            cs.setString(1,startDate);
            cs.setString(2,endDate);
            ResultSet rs = cs.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next())
            {
                //For loop each column
                for(int i=1;i<rsmd.getColumnCount()+1;i++)
                {
                    //Get the value of each column and insert into the entry String[]
                    //entry[i] = rs.getString(i);
                    entries.add(rs.getString(i));
                }
                
            }           
            
            cs.close();
            rs.close();
            conn.close();
            
        } catch (ClassNotFoundException | NamingException | SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entries;
    }
    
    /**
     * Method that returns all orders by payment type (Debit, Cash, Credit)
     * @param type
     * @param startDate
     * @param endDate
     * @return entries List of orders
     */
    public ArrayList<String> getOrderByPayment(String type, String startDate, String endDate)
    {
        ArrayList<String> entries = new ArrayList<String>();
        
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call reportOrderByPayment(?,?,?)");
            cs.setString(1,type);
            cs.setString(2,startDate);
            cs.setString(3,endDate);
            ResultSet rs = cs.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            //String[] entry = new String[rsmd.getColumnCount()];
            
            while(rs.next())
            {
                //For loop each column
                for(int i=1;i<rsmd.getColumnCount()+1;i++)
                {
                    //Get the value of each column and insert into the entry String[]
                    //entry[i] = rs.getString(i);
                    entries.add(rs.getString(i));
                }
                
            }           
            
            cs.close();
            rs.close();
            conn.close();
            
        } catch (ClassNotFoundException | NamingException | SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entries;
    }
    
    /**
     * Method that returns all items on an order
     * @param ordernum Order number
     * @return entries List of all items on a specific order
     */
    public ArrayList<String> getOrderItems(String ordernum)
    {
        ArrayList<String> entries = new ArrayList<String>();
        Integer onum = Integer.valueOf(ordernum);
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call reportOrderItems(?)");
            cs.setInt(1,onum);
            ResultSet rs = cs.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            //String[] entry = new String[rsmd.getColumnCount()];
            
            while(rs.next())
            {
                //For loop each column
                for(int i=1;i<rsmd.getColumnCount()+1;i++)
                {
                    //Get the value of each column and insert into the entry String[]
                    entries.add(rs.getString(i));
                }
            }           
            
            cs.close();
            rs.close();
            conn.close();
            
        } catch (ClassNotFoundException | NamingException | SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entries;
    }
}
