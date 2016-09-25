/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;

import businessLogic.PrintManager;
import com.itextpdf.text.BaseColor;
import java.util.ArrayList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.net.URL;

/**
 * This class will be used to create receipts to be printed for customers, and also receipts printed for the kitchen and bar staff.
 * @author 504724
 */
public class Receipt{
    //Attributes
    private String header; //TODO + Should this be retreived from a file that the customer can customize? Or customizable in the admin section?
    private String footer; //TODO + Should this be retreived from a file that the customer can customize? Or customizable in the admin section?
    private String body;
    private String drink;
    private String food;
    private String empName;
    private String orderNumber;
    private double tableTotal;
    private double orderTotal;
    private final String link;
    private int drinkCount;
    private int foodCount;
    
    public Receipt()
    {
        header = "613 11th AVENUE SW\n" +
                "Social Club & Live Music Venue\n" +
                "INFO@BROKENCITY.CA | 403.262.9976\n\n";
        link = "http://brokencity.ca/wp/wp-content/themes/brokencity/images/logo.png";
        footer = "";
        body = "";
        empName = "";
        orderNumber = "";
        tableTotal = 0;
        drinkCount = 0;
        foodCount = 0;
    }
    
    public void togetherHeader(String table)
    {
        body="\nTable #: "+table+"\n";
        body += "Server : "+empName+"\n\n";
        body += String.format("%-15s %16s\n", "Item", "Price");
        body += String.format("%-15s %16s\n", "----", "-----");
    }

    /**
     * //This will add an entire order to the receipt body String, for printing customer receipts
     * @param order 
     */
    public void addSeat(Order order)
    {
        ArrayList<Item> items = order.getItems();
        orderNumber = Integer.toString(order.getOrderNo());
        orderTotal = order.getOrderTotal();

        for(Item item:items)
        {
            body += formatReceipt(item.getItemName(), item.getBasePrice());
            
            body += "\n";
        }
        tableTotal += order.getOrderTotal();
    }
    public void addSeatSeparate(Order order)
    {
        ArrayList<Item> items = order.getItems();
        orderNumber = Integer.toString(order.getOrderNo());
        orderTotal = order.getOrderTotal();
        body="\nOrder #: "+orderNumber+"\n";
        body += "Server : "+empName+"\n\n";
        body += String.format("%-15s %16s\n", "Item", "Price");
        body += String.format("%-15s %16s\n", "----", "-----");
        
        for(Item item:items)
        {
            body += formatReceipt(item.getItemName(), item.getBasePrice());
            
            body += "\n";
        }
        tableTotal += order.getOrderTotal();
    }
    
    public void footerTogether()
    {
        body += String.format("\n%-15s %10.2f\n", "Total (Including GST)", tableTotal);
        footer = String.format("\n\n\n%20s", "THANK YOU");
        footer += "\nVisit us online at BROKENCITY.CA";
    }
    
    public void footerSeparate()
    {
        body += String.format("\n%-15s %10.2f\n", "Total (Including GST)", orderTotal);
        footer = String.format("\n\n\n%20s", "THANK YOU");
        footer += "\nVisit us online at BROKENCITY.CA";
    }
    
    private String formatReceipt(String item, double price)
    {
        return String.format("%-15.15s %16.2f", item, price);
    }
    
    public void setServer(String name)
    {
        empName = name;
    }
    
    public String getServer()
    {
        return empName;
    }
    
    /**
     * //This will add a single item to the receipt body String, for printing bartender/kitchen receipts
     * @param item 
     */
    public void addItem(Item item)
    {
        body += "";
    }

    public void printReceiptTogether()
    {
        try {     
            String path = System.getProperty("user.home") + File.separator + "Documents";
            OutputStream file = new FileOutputStream(new File(path+"\\receiptTogether.pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            
            Paragraph p = new Paragraph("",FontFactory.getFont(FontFactory.COURIER));
            
            Image img = Image.getInstance(new URL(link));
            img.scalePercent(56f);
            p.add(img);
            
            p.add(header);
            p.add(new Date().toString());
            footerTogether();
            p.add(body);
            p.add(footer);
            document.add(p);
            
            document.close();
            file.close();
            
            header="";
            body="";
            footer="";
 
        } catch (Exception e) {
 
            e.printStackTrace();
        }
    }
    
    public void printReceiptSeparate()
    {
        try {     
            String path = System.getProperty("user.home") + File.separator + "Documents";
            OutputStream file = new FileOutputStream(new File(path+"\\receipt"+orderNumber+".pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            
            Paragraph p = new Paragraph("",FontFactory.getFont(FontFactory.COURIER));
            
            
            
            Image img = Image.getInstance(new URL(link));
            img.scalePercent(56f);
            p.add(img);
            
            p.add(header);
            p.add(new Date().toString());
            footerSeparate();
            p.add(body);
            p.add(footer);
            document.add(p);
            
            document.close();
            file.close();
 
        } catch (Exception e) {
 
            e.printStackTrace();
        }
    }
    
    
    
    public void printSentOrder(Order order)
    {
        ArrayList<Item> items = order.getItems();
        orderNumber = Integer.toString(order.getOrderNo());
        
        
        drink = "Bar Order\n";
        drink += "\nOrder #: "+orderNumber+"\n";
        drink += "Server : "+empName+"\n\n";
        drink += String.format("%-15s\n", "Item");
        drink += String.format("%-15s\n", "----");

        food = "Kitchen Order\n";
        food += "\nOrder #: "+orderNumber+"\n";
        food += "Server : "+empName+"\n\n";
        food += String.format("%-15s\n", "Item");
        food += String.format("%-15s\n", "----");
        
        for(Item item:items)
        {
            if(item.getItemType().equalsIgnoreCase("drink"))
            {
                drink += String.format("%-15s", item.getItemName());
                drink += "\n";
                drinkCount++;
            }
            else
            {
                food += String.format("%-15s", item.getItemName());
                food += "\n";
                foodCount++;
            }
        }
        
        if(drinkCount>0)
        {
            try {     
            String path = System.getProperty("user.home") + File.separator + "Documents";
            OutputStream file = new FileOutputStream(new File(path+"\\barOrder"+orderNumber+".pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            Paragraph p = new Paragraph("",FontFactory.getFont(FontFactory.COURIER));
            
            p.add(new Date().toString()+"\n");
            p.add(drink);
            document.add(p);
            
            document.close();
            file.close();
 
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        
        if(foodCount>0)
        {
            try {     
            String path = System.getProperty("user.home") + File.separator + "Documents";
            OutputStream file = new FileOutputStream(new File(path+"\\kitchenOrder"+orderNumber+".pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            Paragraph p = new Paragraph("",FontFactory.getFont(FontFactory.COURIER));
            
            p.add(new Date().toString()+"\n");
            p.add(food);
            document.add(p);
            
            document.close();
            file.close();
 
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        
        drink="";
        food="";
    }
    
    public boolean canPrintBar()
    {
        return drinkCount>0;
    }
    
    public boolean canPrintKitchen()
    {
        return foodCount>0;
    }
    
    public void setCount()
    {
        drinkCount=0;
        foodCount=0;
    }
}
