/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminHelper;

import businessLogic.EmployeeManager;
import containers.Employee;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author 632794
 */
public class EmployeesHelper {
    public EmployeesHelper() {};
    
    /**
     * Method to get a list of all employees in the system.
     * @return a list of all employees in the system as an HTML String.
     */
    public String getEmployeeList()
    {
        EmployeeManager em = EmployeeManager.getInstance();
        ArrayList<Employee> employees = em.getEmployees();
        String table = "<ul class='employeeList'>";
        for(int i = 0; i < employees.size(); i++)
        {
            Employee e =  employees.get(i);
            table += "<li ";
            if(e.isActive() == 0)
                table += "class='inactive'";
            table += " data-id="+e.getEmpNo()+">"+e.getFirstName()+" "+e.getLastName()+"</li>";
        }
        return table += "</ul>";
    }
    
    /**
     * Method to get a random available employee number for use in the JSP when creating a new employee. 
     * @return random emp no
     */
    public int getNextEmpNo()
    {
        Random random = new Random();
        int randomNumber = random.nextInt((9999-1000)+1)+1000;
        EmployeeManager em = EmployeeManager.getInstance();
        Employee e = em.getEmpById(randomNumber);
        if(e == null)
            return randomNumber; // doesnt exist, return number
        else 
            return getNextEmpNo(); // exist, restart.
    }
    
    /**
     * Returns the first name of an employee
     * @param empNo as the employee to get first name for.
     * @return  first name of that employee
     */
    public String getFirstName(String empNo)
    {
        EmployeeManager em = EmployeeManager.getInstance();
        Employee e = em.getEmpById(Integer.parseInt(empNo));
        return e.getFirstName();
    }
    
    /**
     * Returns the last name of an employee
     * @param empNo as the employee to get first name for.
     * @return  last name of that employee
     */
    public String getLastName(String empNo)
    {
        EmployeeManager em = EmployeeManager.getInstance();
        Employee e = em.getEmpById(Integer.parseInt(empNo));
        return e.getLastName();
    }
        
    /**
     * Returns the phone number of an employee
     * @param empNo as the employee to get first name for.
     * @return phone number of that employee
     */
    public String getPhone(String empNo)
    {
        EmployeeManager em = EmployeeManager.getInstance();
        Employee e = em.getEmpById(Integer.parseInt(empNo));
        return e.getPhoneNumber();
    }
          
    /**
     * Returns the address of an employee
     * @param empNo as the employee to get first name for.
     * @return  address of that employee
     */
    public String getAddress(String empNo)
    {
        EmployeeManager em = EmployeeManager.getInstance();
        Employee e = em.getEmpById(Integer.parseInt(empNo));
        return e.getAddress();
    }
    
    /**
     * Returns the SIN of an employee
     * @param empNo as the employee to get first name for.
     * @return  SIN of that employee
     */
    public String getSIN(String empNo)
    {
        EmployeeManager em = EmployeeManager.getInstance();
        Employee e = em.getEmpById(Integer.parseInt(empNo));
        return e.getSIN() + "";
    }
    
    /**
     * Returns the position of an employee
     * @param empNo as the employee to get first name for.
     * @return  position of that employee as a html string <select>
     */
    public String getPosition(String empNo)
    {
        EmployeeManager em = EmployeeManager.getInstance();
        Employee e = em.getEmpById(Integer.parseInt(empNo));
        String currentType= e.getPosition();
        String[] types = {"Server", "Bartender", "Admin", "Other"};
        String select = "<select name='position'>";
        for(String item:types)
        {
            if(currentType.equalsIgnoreCase(item))
                select += "<option selected=selected>"+item+"</option>";
            else
                select += "<option>"+item+"</option>";
        }
        select +=  "</select>";
        return select;
    }
    
    /**
     * If employee is active returns checked, else returns "". 
     * @param empNo as the employee to check
     * @return a string as described above.
     */
    public String isEnabled(String empNo)
    {
        EmployeeManager em = EmployeeManager.getInstance();
        Employee e = em.getEmpById(Integer.parseInt(empNo));
        if(e.isActive() == 1)
            return "checked";
        else
            return "";
    }
    
    /**
     * Returns the hire date of an employee
     * @param empNo as the employee to get first name for.
     * @return  hire date of that employee
     */
    public String getHireDate(String empNo)
    {
        EmployeeManager em = EmployeeManager.getInstance();
        Employee e = em.getEmpById(Integer.parseInt(empNo));
        return e.getHireDate();
    }
    
       /**
     * Returns the end date  of an employee
     * @param empNo as the employee to get first name for.
     * @return end date of that employee, if no end date returns null
     */
    public String getEndDate(String empNo)
    {
        EmployeeManager em = EmployeeManager.getInstance();
        Employee e = em.getEmpById(Integer.parseInt(empNo));
        
        if(e.getEndDate() != null)
            return e.getEndDate();
        else
            return "";
    }
    
    /**
     * Returns a HTML button if they are an admin who can change their password
     * @param empNo as the employee to get first name for.
     * @return html button string or ""
     */
    public String changeAdminPass(String empNo)
    {
        EmployeeManager em = EmployeeManager.getInstance();
        Employee e = em.getEmpById(Integer.parseInt(empNo));
        String currentType= e.getPosition();
        if(e.getPosition().equalsIgnoreCase("Admin"))
            return "<a href='#changeAdminPass' class='button'>Change Password</a>";
        return "";
    }
}