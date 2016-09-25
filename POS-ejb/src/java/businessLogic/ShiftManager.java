/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import brokers.ShiftBroker;
import containers.Employee;

/**
 *
 * @author 642123
 */
public class ShiftManager 
{
    private static ShiftManager instance = null;
    
    private ShiftManager()
    {
    }
    /**
     * getInstance method returns an instance of ShiftManager adhering to the singleton pattern
     * @return 
     */
    public static ShiftManager getInstance()
    {
        if(instance == null)
        {
            instance = new ShiftManager();
        }
        
        return instance;
    }
    /**
     * Passes an employee object to the broker to verify with the database if an employee is currently clocked into the system
     * @return boolean true if clocked in, false if not
    */
    public boolean isClockedIn(Employee e)
    {
        ShiftBroker sb = ShiftBroker.getInstance();
        return sb.isClockedIn(e.getEmpNo());
    }
    /**
     * Passes an employee object containing an employee number to the broker to check the database if an employee is currently active within the system
     * @param e
     * @return boolean, true if active, false if inactive
     */
    public boolean isActive(Employee e)
    {
        ShiftBroker sb = ShiftBroker.getInstance();
        return sb.isActive(e.getEmpNo());
    }
    /**
     * Passes an employee object to the broker to set the employees clock in status to clocked in
     * @param e 
     */
    public void clockUserIn(Employee e)
    {
        ShiftBroker sb = ShiftBroker.getInstance();
        sb.clockUserIn(e.getEmpNo());
    }
    /**
     * Passes an employee object to the broker to set an employees clock in status to clocked out
     * @param e 
     */
    public void clockUserOut(Employee e)
    {
        ShiftBroker sb = ShiftBroker.getInstance();
        sb.clockUserOut(e.getEmpNo());
    }
}
