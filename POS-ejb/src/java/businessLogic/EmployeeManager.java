/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import brokers.EmployeeBroker;
import containers.Employee;
import java.util.ArrayList;

/**
 * EmployeeManager class is the application logic layer for all Employee related functionality within the system.
 * @author 642123
 */
public class EmployeeManager 
{
    private static EmployeeManager instance = null;
    private static ArrayList<Employee> employees;

    private EmployeeManager()
    {
    }
    /**
     * getInstance method is used to return an instance of the EmployeeManager.
     * This method is used for the Singleton Pattern
     * @return EmployeeManager instance
     */
    public static EmployeeManager getInstance()
    {
        if(instance == null)
        {
            instance = new EmployeeManager();
            loadEmployees();
        }
        return instance;
    }
    /**
     * loadEmployees method is used to load all employees from the database into memory of the system.
     * This method is run from the getInstance method when a new instance of EmployeeManager is created.
     */
    private static void loadEmployees()
    {
        EmployeeBroker eb = EmployeeBroker.getInstance();
        employees = eb.getAllEmployees();
    }
    /**
     * addEmployee will send the employee object passed from the front end controller to the broker to be added into the database.
     * Pre-condition: An employee object is passed with all information correctly added to the Employee object.
     * @param Employee e
     */
    public void addEmployee(Employee e)
    {
        employees.add(e);
        EmployeeBroker eb = EmployeeBroker.getInstance();
        eb.addEmployee(e);
    }
    /**
     * setAdminPassword is used to pass the password for a newly added Admin user to the broker to be added into the database.
     * Pre-condition: Employee object with the admin's user ID passed as a parameter
     * @param Employee e
     * @param password 
     */
    public void setAdminPassword(Employee e, String password)
    {
        EmployeeBroker eb = EmployeeBroker.getInstance();
        eb.setAdminPassword(e, password);
    }
    /**
     * modifyEmployee method is used to modify the employee information in the database. 
     * The method will replace the current employee information stored in memory with the new information
     * and pass the new employee object to the broker to be persisted into the database.
     * @param Employee e
     * @param firstName
     * @param lastName
     * @param address
     * @param phoneNumber
     * @param SIN
     * @param position
     * @param isActive
     * @param hireDate
     * @param endDate 
     */
    public void modifyEmployee(Employee e, String firstName, String lastName, String address, String phoneNumber, 
                    int SIN, String position, int isActive, String hireDate, String endDate)
    {
        e.setFirstName(firstName);
        e.setLastName(lastName);
        e.setAddress(address);
        e.setPhoneNumber(phoneNumber);
        e.setSIN(SIN);
        e.setPosition(position);
        e.setIsActive(isActive);
        e.setHireDate(hireDate);
        e.setEndDate(endDate);
        
        EmployeeBroker eb = EmployeeBroker.getInstance();
        eb.modifyEmployee(e.getEmpNo(), firstName, lastName, phoneNumber, address, SIN, position, isActive, hireDate, endDate);
    }
    /**
     * modifyPassword method is used to modify the password for an admin user within the database. Passes the information to the broker.
     * Pre-condition: Checking is done in the controller to verify password information entered
     * @param e
     * @param newPass 
     */
    public void modifyPassword(Employee e, String newPass)
    {
        EmployeeBroker eb = EmployeeBroker.getInstance();
        eb.modifyPassword(e,newPass);
    }
    /**
     * getEmpById method is used to retrieve the employee object from memory given an employee ID number.
     * Post-condition: Returns null if no Employee is found for the given employee ID
     * @param empNo for Employee being searched
     * @return Employee of given employee ID
     */
    public Employee getEmpById(int empNo)
    {
        for (Employee e : employees) 
        {
            if(e.getEmpNo() == empNo)
                return e;
        }
        
        return null;
    }
    /**
     * validateEmpNo will pass the employee with the required employee number to the broker to check the database if it exists.
     * 
     * @param Employee e
     * @return boolean, true if exists, false if does not exist
     */
    public boolean validateEmpNo(Employee e)
    {
        EmployeeBroker eb = EmployeeBroker.getInstance();
        return eb.validateEmpNo(e.getEmpNo());
    }
    /**
     * validateAdminUser passes the employee information and password to the broker to be verified with the database information
     * @param e Employee object
     * @param password
     * @return boolean, true if valid admin password, false if invalid
     */
    public boolean validateAdminUser(Employee e, String password)
    {
        EmployeeBroker eb = EmployeeBroker.getInstance();
        return eb.validateAdminUser(e.getEmpNo(), password);
    }
    /**
     * getPosition will pass the employee to the broker to get the employees position fromt he database.
     * @param e Employee
     * @return 1 for server, 2 for bartender, 3 for admin
     */
    public int getPosition(Employee e)
    {
        EmployeeBroker eb = EmployeeBroker.getInstance();
        return eb.getPosition(e.getEmpNo());
    }
    /**
     * returns the list of all employees from memory
     * @return ArrayList of Employees
     */
    public ArrayList<Employee> getEmployees()
    {
        return employees;
    }
    
    
}
