/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import javax.print.PrintService;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * PrintManager class handles printing data passed from various areas of the system
 * @author 504724
 */
public class PrintManager 
{
    private static PrintManager instance = null;
    
    private PrintManager()
    {
    }
    /**
     * getInstanc method returns an instance of PrintManager adhering to the singleton pattern
     * @return 
     */
    public static PrintManager getInstance()
    {
        if(instance == null)
        {
            instance = new PrintManager();
        }
        
        return instance;
    }
    /**
     * printPDF creates the print job and sends it to the printer to be printed
     * @param fileName
     * @param printer
     * @throws IOException
     * @throws PrinterException 
     */
    public void printPDF(String fileName, PrintService printer)
            throws IOException, PrinterException 
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintService(printer);
        PDDocument doc = PDDocument.load(fileName);
        doc.silentPrint(job);
    }
}
