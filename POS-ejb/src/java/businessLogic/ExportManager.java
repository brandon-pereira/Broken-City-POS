/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import brokers.AdminBroker;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ExportManager handles all information regarding exporting sales history from the database.
 * 
 * CSV Format:
 *  First Row: Version#, Contry Code(1), 'Inventory'
 *  Second Row: Titles- 'Item ID', 'Name', 'Inventory'/'Service', 'Unit', 'Regular Price', 'Preferred Price', 'Asset Acct.', 'Revenue Acct.', 'Expense Acct.'
 *   itemID, name, 'Inventory' or 'Service', unit, reg selling price, preferred selling price, linked asset account, linked revenue account, linked expense account
 * 
 * OpenCSV info. @ http://opencsv.sourceforge.net/
 * @author 504724
 */
public class ExportManager {

    /**
     * exportData method handles exporting sales information from the database using the OpenCSV api package.
     * 
     * @param fileName 
     * @param dateFrom 
     * @param dateTo 
     * @throws IOException 
     */
    public void exportData(String fileName, String dateFrom, String dateTo) throws IOException
    {
        ArrayList<String[]> entries = new ArrayList<String[]>();
        
        //Retreive the data from the database
        //select item_no, item_name, base_price, item_total_log
        //based on the log order numbers between beginDate and current date

        AdminBroker ab = AdminBroker.getInstance();
        entries = ab.getExportData(dateFrom, dateTo);
        
        //Plug in the static numbers into each entry
        for(int i=0;i<entries.size();i++)
        {
            String[] entry = {"","","Inventory","Each","","","Asset Account","Revenue Account","Espense Account"};
            String[] oldEntry = new String[4];
            oldEntry = entries.get(i);
            entry[0] = oldEntry[0];
            entry[1] = oldEntry[1];
            entry[4] = oldEntry[2];
            entry[5] = oldEntry[3];
            entries.set(i,entry);
        }
        //build the second row of titles
        String[] titles = {"Item ID","Name","Inventory/Service","Unit","Regular Price","Preferred Price","Asset Acct.","Revenue Acct.","Expense Acct."};
        entries.add(0,titles);
        
        //build the first row
        String[] s = {"19001","1","Inventory"};
        entries.add(0,s);
        
        File file = new File(System.getProperty("user.home") + "/Desktop", fileName);

        writeCSV(entries, file);      
        
    }
    /**
     * method calls the CSVWriter api to write the information to file.
     * Pre-Condition: entries List contains information formatted correctly and locations of information matches the format of the import
     * Post-condition: .csv file is written tot he desktop of the computer.
     * @param entries
     * @param file
     * @throws IOException 
     */
    public void writeCSV(List<String[]> entries, File file) throws IOException
    {    
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        writer.writeAll(entries);
        
        writer.close();
    }
}