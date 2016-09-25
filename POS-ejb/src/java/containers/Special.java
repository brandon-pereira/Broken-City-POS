/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;

import java.util.ArrayList;

/**
 *
 * @author 642123
 */
public class Special 
{
    private int specialNo;//unique special number
    private ArrayList<Item> items;//the item number in the menu
    private String name;
    private ArrayList<Integer> dayOfWeek;
    private String startTime;
    private String endTime;
    private double discountedPrice;

    /**
     * non default constructor used for creating a special object
     * @param specialNo
     * @param items
     * @param name
     * @param dayOfWeek
     * @param startTime
     * @param endTime
     * @param discountedPrice 
     */
    public Special(int specialNo, ArrayList<Item> items, String name, ArrayList<Integer> dayOfWeek, String startTime, String endTime, double discountedPrice) {
        this.specialNo = specialNo;
        this.items = items;
        this.name = name;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountedPrice = discountedPrice;
    }

    public int getSpecialNo() {
        return specialNo;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(ArrayList<Integer> dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
    
}
