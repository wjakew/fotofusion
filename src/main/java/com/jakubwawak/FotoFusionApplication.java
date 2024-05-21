/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak;

import com.jakubwawak.maintanance.ConsoleColors;
import com.jakubwawak.maintanance.FotoFusionLog;

/**
 * Application for creating a backup copy of photo library based on photo EXIF data
 */
public class FotoFusionApplication {

    public static String version = "v1.0.0";
    public static String build = "ff220524JWA";
    public static FotoFusionLog log = new FotoFusionLog(true);;
    public static String[] photoExtensions = {"jpg", "png", "jpeg", "gif", "bmp"};

    public static boolean debugFlag = true;
    /**
     * Main method of the application
     * @param args
     */
    public static void main(String[] args) {
        show_info();
        if ( debugFlag ){
            FotoFusionTest test = new FotoFusionTest();
            test.run();
        }
        else{
            //TODO run application normally
        }
    }

    /**
     * Method for showing the info about the application
     */
    static void show_info() {
        String header = "  __       _        _____          _\n" +
                " / _| ___ | |_ ___ |  ___|   _ ___(_) ___  _ __\n" +
                "| |_ / _ \\| __/ _ \\| |_ | | | / __| |/ _ \\| '_ \\\n" +
                "|  _| (_) | || (_) |  _|| |_| \\__ \\ | (_) | | | |\n" +
                "|_|  \\___/ \\__\\___/|_|   \\__,_|___/_|\\___/|_| |_|";

        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT+header + "\n" + version + " " + build+ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED_BACKGROUND_BRIGHT+"by Jakub Wawak"+ConsoleColors.RESET);
    }
}