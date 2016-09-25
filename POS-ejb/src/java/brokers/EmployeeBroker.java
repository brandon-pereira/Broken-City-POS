package brokers;

import containers.Employee;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Broker Class for the Employee that handles database connection
 * and returns employee data.
 * @author 642123
 */
@Stateless
@LocalBean
public class EmployeeBroker 
{
    private static EmployeeBroker instance = null;
    
    protected EmployeeBroker()
    {
    }
    
    /**
     * Method that creates an instance of the EmployeeBroker class
     * to be called by the controller classes.
     * @return instance
     */
    public static EmployeeBroker getInstance()
    {
        if(instance == null)
        {
            instance = new EmployeeBroker();
        }
        return instance;
    }
    
    /**
     * Method that validates an entered employee number
     * and returns a boolean status.
     * @param empNo Employee number
     * @return boolean valid or invalid status 
     */
    public boolean validateEmpNo(int empNo)
    {
        boolean retval = false;
        try 
        {
        Class.forName("com.mysql.jdbc.Driver");
        InitialContext ic = new InitialContext();   
        DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
        Connection conn = ds.getConnection();

        CallableStatement cs = conn.prepareCall("call isValidEmp(?)");
        cs.setInt(1, empNo);
        ResultSet rs = cs.executeQuery();
        
        rs.next();
        if(rs.getInt(1) > 0)
        {
            retval = true;
        }
        else if(rs.getInt(1) == 0)
        {
            retval = false;
        }
        
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

        return retval;
    }
    
    /**
     * Method that returns the position value of an employee
     * given an employee number.
     * @param empNo Employee Number
     * @return retval position value
     */
    public int getPosition(int empNo)
    {
        int retval = 0;
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call getUserType(?)");
            cs.setInt(1, empNo);
            ResultSet rs = cs.executeQuery();

            rs.next();

            String pos = rs.getString(1);
            if(pos.equalsIgnoreCase("server"))     
                retval = 1;       
            else if(pos.equalsIgnoreCase("bartender"))
                retval = 2;
            else if(pos.equalsIgnoreCase("admin"))
                retval = 3;

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
        
        return retval;
    }

    /**
     * Method that takes in an employee object and creates that specific 
     * employee in the database.
     * @param e Employee object
     */
    public void addEmployee(Employee e) 
    {
        int empNo = e.getEmpNo();
        String firstName = e.getFirstName();
        String lastName = e.getLastName();
        String address = e.getAddress();
        String phoneNumber = e.getPhoneNumber();
        int SIN = e.getSIN();
        String position = e.getPosition();
        String hireDate = e.getHireDate();
                        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call addEmployee(?,?,?,?,?,?,?,?)");
                        
            cs.setInt(1,empNo);
            cs.setString(2,firstName);
            cs.setString(3,lastName);
            cs.setString(4,address);
            cs.setString(5,phoneNumber);
            cs.setInt(6,SIN);
            cs.setString(7,position);
            cs.setString(8,hireDate);

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
     * Method that modifies an existing employee in the database.
     * The following parameters need to be entered:
     * @param empNo Employee number
     * @param firstName First name
     * @param lastName Last name
     * @param phoneNumber Phone number
     * @param address Address
     * @param SIN Social Insurance Number
     * @param position Employee position
     * @param isActive Employee active status
     * @param hireDate Employee hire date
     * @param endDate Employee end date
     */
    public void modifyEmployee(int empNo, String firstName, String lastName, String phoneNumber, 
            String address, int SIN, String position, int isActive, String hireDate, String endDate) 
    {
        if(endDate.equals(""))
            endDate = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call modifyEmployee(?,?,?,?,?,?,?,?,?,?)");

            cs.setInt(1,empNo);
            cs.setString(2,firstName);
            cs.setString(3,lastName);
            cs.setString(4,address);
            cs.setString(5,phoneNumber);
            cs.setInt(6,SIN);
            cs.setString(7,position);
            cs.setInt(8,isActive);
            cs.setString(9,hireDate);
            cs.setString(10,endDate);

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
     * Method that validates an admin user by entering an employee number
     * and password.
     * @param empNo Employee number
     * @param password Admin password
     * @return boolean status of the validate method
     */
    public boolean validateAdminUser(int empNo, String password)
    {
        boolean valid = false;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call validateAdmin(?,?)");
            cs.setInt(1,empNo);
            cs.setString(2,password);
            ResultSet rs = cs.executeQuery();
            
            if(rs.next())
                valid = true;
            
            rs.close();
            cs.close();
            conn.close();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valid;
    }
    
    /**
     * Method that sets an Admin password by specifying an employee object
     * and password.
     * @param e Employee object
     * @param password Admin password
     */
    public void setAdminPassword(Employee e, String password)
    {
        int empNo = e.getEmpNo();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call addPassword(?,?)");
            cs.setInt(1,empNo);
            cs.setString(2,password);
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
     * Method that modifies an employee password given the employee object
     * and the new password
     * @param e Employee object
     * @param newPass New admin password
     */
    public void modifyPassword(Employee e, String newPass)
    {
        int empNo = e.getEmpNo();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call modifyPassword(?,?)");
            cs.setInt(1,empNo);
            cs.setString(2,newPass);
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
     * Method that prompts for manager validation through a user ID.
     * Returns a boolean status depending on the validation
     * @param userID Manager ID
     * @return boolean status of the validation
     */
    public boolean getManagerApproval(int userID)
    {
        boolean approved = false;
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();
            
            CallableStatement cs = conn.prepareCall("call getUserType(?)");
            cs.setInt(1, userID);
            ResultSet rs = cs.executeQuery();
            rs.next();
            String pos = rs.getString(1);
            if(pos.equalsIgnoreCase("admin"))
                approved = true;
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return approved;
    }
    
    /**
     * Method that returns an arraylist of all employees.
     * @return employee All list of employees
     */
    public ArrayList<Employee> getAllEmployees()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            InitialContext ic = new InitialContext();   
            DataSource ds = (DataSource) ic.lookup("jdbc/posdb");
            Connection conn = ds.getConnection();

            CallableStatement cs = conn.prepareCall("call getAllEmployees()");
            ResultSet rs = cs.executeQuery();
            while(rs.next())
            {
                Employee e = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                                          rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getString(10));
                employees.add(e);
            }
            
            rs.close();
            cs.close();
            conn.close();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return employees;
    }
}
