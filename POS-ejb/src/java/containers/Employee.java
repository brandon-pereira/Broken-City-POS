/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;

/**
 *
 * @author 504724
 */
public class Employee 
{
    private int empNo;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private int SIN;
    private String position;
    private int isActive;
    private String hireDate;
    private String endDate;
    
    /**
     * Default constructor for an Employee Object
     */
    public Employee()
    {
    }

    /**
     * Non default constructor for an employee object
     * @param empNo
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
    public Employee(int empNo, String firstName, String lastName, String address, String phoneNumber, 
                    int SIN, String position, int isActive, String hireDate, String endDate) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.SIN = SIN;
        this.position = position;
        this.isActive = isActive;
        this.hireDate = hireDate;
        this.endDate = endDate;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSIN() {
        return SIN;
    }

    public void setSIN(int SIN) {
        this.SIN = SIN;
    }

    public int isActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    
    
}
