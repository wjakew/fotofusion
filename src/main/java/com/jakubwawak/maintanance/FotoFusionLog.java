/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.maintanance;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Class for storing log
 */
public class FotoFusionLog {

    public ArrayList<String> logCollection;
    public boolean showLogFlag;
    /**
     * Constructor
     */
    public FotoFusionLog(boolean showLogFlag){
        logCollection = new ArrayList<>();
        this.showLogFlag = showLogFlag;
        add("Running the app, log started!");
    }

    /**
     * Function for adding log data
     * @param log
     */
    public void add(String log){
        String logElement = LocalDateTime.now()+" - "+log;
        logCollection.add(logElement);
        if ( showLogFlag ){
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+logElement+ConsoleColors.RESET);
        }
    }

    /**
     * Function for generating log file name
     * @return String
     */
     String generateLogFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return "log_" + now.format(formatter) + ".log";
    }

    /**
     * Function for saving log to file
     */
    public void saveLogToFile() {
        try (PrintWriter writer = new PrintWriter(generateLogFileName())) {
            for (String logEntry : logCollection) {
                writer.println(logEntry);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the log file: " + e.getMessage());
        }
    }
}
