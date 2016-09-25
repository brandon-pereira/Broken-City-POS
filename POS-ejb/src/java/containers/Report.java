/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;

import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;
import java.util.Date;

/**
 *
 * @author 532485
 */
public class Report {
    
    private String body;
    private String body2;
    private String body3;
    private String body4;
    private final String link;
    private String empno;
    private String fname;
    private String lname;
    private String address;
    private String phone;
    private String position;
    private String ehours;
    
    public Report()
    {
        link = "http://brokencity.ca/wp/wp-content/themes/brokencity/images/logo.png";
        body="";
        body2="";
        body3="";
        body4="";
        empno="";
        fname="";
        lname="";
        address="";
        phone="";
        position="";
        ehours="";
    }
    
    public void generateHours(String hours,String from,String to)
    {
        try {     
            String path = System.getProperty("user.home") + File.separator + "Documents";
            OutputStream file = new FileOutputStream(new File(path+"\\EmployeeReport.pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            
            Paragraph p = new Paragraph("",FontFactory.getFont(FontFactory.COURIER));
            
            Image img = Image.getInstance(new URL(link));
            img.scalePercent(56f);
            p.add(img);
            
            ehours=hours;
            
            body=String.format("%-15s - %2s\n", "Employee Number", empno);
            body+=String.format("%-15s - %5s\n", "First Name", fname);
            body+=String.format("%-15s - %1s\n", "Last Name", lname);
            body+=String.format("%-15s - %9s\n", "Address", address);
            body+=String.format("%-15s - %9s\n", "Phone Number", phone);
            body+=String.format("%-15s - %3s\n", "Position", position);
            body+=String.format("%-15s - %8s\n", "Total Hours", ehours);
            body+="\n";
            
            from=from.substring(0, 10);
            to=to.substring(0, 10);
            
            p.add(new Date().toString());
            p.add("\n\nEmployee Report\n\n");
            p.add("Reporting Date: \n"+from+" - "+to+"\n\n");
            p.add(body);
            document.add(p);
            
            document.close();
            file.close();
            
            body="";
 
        } catch (Exception e) {
 
            e.printStackTrace();
        }
    }
    
    public void generateOrderByDate(ArrayList<String> entry,String from,String to)
    {
        try {     
            String path = System.getProperty("user.home") + File.separator + "Documents";
            OutputStream file = new FileOutputStream(new File(path+"\\DateOrderReport.pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            
            Paragraph p = new Paragraph("",FontFactory.getFont(FontFactory.COURIER));
            
            Image img = Image.getInstance(new URL(link));
            img.scalePercent(56f);
            p.add(img);
            
                      
            body2 = String.format("%5s - %5s - %5s - %5s\n", "Order No.", "Order Total", "Payment Type", "Order Date");
            
            double counter=0;
            for(int i=0;i<entry.size();i+=4)
            {
                body2 += String.format("%-10s %8s %15s %24s\n",entry.get(i),entry.get(i+1),entry.get(i+2),entry.get(i+3).substring(0, 19));
                counter+=Double.parseDouble(entry.get(i+1));
            }
            
            body2+="\n\nTotal for all orders: $"+counter;
            
            from=from.substring(0, 10);
            to=to.substring(0, 10);
            
            p.add(new Date().toString());
            p.add("\n\nOrder by Date Report\n");
            p.add("Reporting Date: \n"+from+" - "+to+"\n\n");
            p.add(body2);
            document.add(p);
            
            document.close();
            file.close();
            
            body2="";
 
        } catch (Exception e) {
 
            e.printStackTrace();
        }
    }
    
    public void generateOrderByPayment(String type, ArrayList<String> entry,String from,String to)
    {
        try {     
            String path = System.getProperty("user.home") + File.separator + "Documents";
            OutputStream file = new FileOutputStream(new File(path+"\\PayTypeReport.pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            
            Paragraph p = new Paragraph("",FontFactory.getFont(FontFactory.COURIER));
            
            Image img = Image.getInstance(new URL(link));
            img.scalePercent(56f);
            p.add(img);
            
                      
            body3 = String.format("%5s - %5s - %5s\n", "Order No.", "Order Total", "Order Date");
            
            double counter=0;
            for(int i=0;i<entry.size();i+=3)
            {
                body3 += String.format("%-10s %8s %25s\n",entry.get(i),entry.get(i+1),entry.get(i+2).substring(0, 19));
                counter+=Double.parseDouble(entry.get(i+1));
            }
            
            body3+="\n\nTotal for all orders: $"+counter;
            
            from=from.substring(0, 10);
            to=to.substring(0, 10);
            
            p.add(new Date().toString());
            p.add("\n\nOrder by Payment Report\n");
            p.add("Payment Type: "+type+"\n");
            p.add("Reporting Date: \n"+from+" - "+to+"\n\n");
            p.add(body3);
            document.add(p);
            
            document.close();
            file.close();
            
            body3="";
 
        } catch (Exception e) {
 
            e.printStackTrace();
        }
    }
    
    public void generateOrderItem(String ordernum,ArrayList<String> entry)
    {
        try {     
            String path = System.getProperty("user.home") + File.separator + "Documents";
            OutputStream file = new FileOutputStream(new File(path+"\\OrderReport.pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            
            Paragraph p = new Paragraph("",FontFactory.getFont(FontFactory.COURIER));
            
            Image img = Image.getInstance(new URL(link));
            img.scalePercent(56f);
            p.add(img);
            
                      
            body4 = String.format("%5s  %27s  %10s\n", "Item Name", "Base Price", "Log Total");
            
            double counter=0;
            for(int i=0;i<entry.size();i+=3)
            {
                body4 += String.format("%-10s %15s %12s\n",entry.get(i),entry.get(i+1),entry.get(i+2));
                counter+=Double.parseDouble(entry.get(i+2));
            }
            
            body4+="\n\nTotal for all orders: $"+counter;
            
            
            p.add(new Date().toString());
            p.add("\n\nItems on Order Report\n");
            p.add("Order Number: "+ordernum+"\n\n");

            p.add(body4);
            document.add(p);
            
            document.close();
            file.close();
            
            body4="";
 
        } catch (Exception e) {
 
            e.printStackTrace();
        }
    }
    
    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
