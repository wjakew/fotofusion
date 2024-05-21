/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.maintanance;

import java.time.LocalDateTime;
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
}
