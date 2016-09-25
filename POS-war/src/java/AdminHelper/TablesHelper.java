/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminHelper;

import businessLogic.TableManager;
import containers.Table;
import java.util.ArrayList;

/**
 *
 * @author 632794
 */
public class TablesHelper 
{
    public TablesHelper()
    {
    }
    
    /**
     * Returns all tables as either HTML divs or HTML links depending on isLink parameter.
     * @param serverNo as the server requesting the tables
     * @param isLink as a way to tell if it should return divs or links 
     * @return HTML string of tables.
     */
    public String getAllTables(int serverNo, boolean isLink)
    {
        String html = "";
        TableManager tm = TableManager.getInstance();
        ArrayList<Table> tables = tm.getTables();//returns all the current tables that are open
        String server = "";
        String other = "";
        for(int i = 0; i < tables.size(); i++)
        {
            Table t = tables.get(i);
            if(t.getTableNo() == 0 || t.getTableNo() == 100)
                break;
            if(t.getServerNo() == serverNo)
                if(isLink)
                    server += "<a class='active' href='TableController?tableNo="+t.getTableNo()+"'>"+t.getTableNo()+"</a>";
                else
                    server += "<a class='active tableTo  t"+t.getTableNo()+"' href='#'>"+t.getTableNo()+"</a>";
            else
                if(isLink)
                    other += "<a href='TableController?tableNo="+t.getTableNo()+"'>"+t.getTableNo()+"</a>";
                else
                    other += "<a href='#' class='tableTo t"+t.getTableNo()+"'>"+t.getTableNo()+"</a>";
        }
        html = server+other;
        return html;
    }
    
    /**
     * Returns only tables that are linked to a server no.
     * @param serverNo as the sever requesting
     * @return  html string of links of server tables.
     */
    public String getServerTables(int serverNo)
    {
        String html = "";
        TableManager tm = TableManager.getInstance();
        ArrayList<Table> tables = tm.getTables();//returns all the current tables that are open
        for(int i = 0; i < tables.size(); i++)
        {
            Table t = tables.get(i); 
            if(t.getServerNo() == serverNo)
               html += "<a href='#' class='tableFrom'>"+t.getTableNo()+"</a>";
        }
        return html;
    }
}

    