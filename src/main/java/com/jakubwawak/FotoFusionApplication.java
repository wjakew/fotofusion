/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak;

import com.jakubwawak.fotofusionengine.Engine;
import com.jakubwawak.fotofusionengine.terminalmenu.FotoFusionMenu;
import com.jakubwawak.maintanance.ConsoleColors;
import com.jakubwawak.maintanance.FotoFusionLog;
import com.jakubwawak.maintanance.FotoFusionPreset;
import com.jakubwawak.maintanance.Properties;

import java.io.File;
import java.util.Scanner;

/**
 * Application for creating a backup copy of photo library based on photo EXIF data
 */
public class FotoFusionApplication {

    public static String version = "v1.1.0";
    public static String build = "ff240524JWA";
    public static FotoFusionLog log = new FotoFusionLog(true);;
    public static String[] photoExtensions = {};
    public static String dateDivider = "";

    public static Engine engine = new Engine();
    public static boolean debugFlag = false;
    public static Properties properties;

    /*
     * Main method of the application
     * @param args
     */
    public static void main(String[] args) {
        show_info();
        // load properties
        properties = new Properties("fotofusion.properties");
        if (properties.fileExists){
            properties.parsePropertiesFile();
        }
        else{
            properties.createPropertiesFile();
            properties.parsePropertiesFile();
        }
        photoExtensions = properties.getValue("accepted_file_types").split(",");
        dateDivider = properties.getValue("date_format_divider");
        log.add("Loaded properites file, date divider set to ["+dateDivider+"]6, accepted file types: "+properties.getValue("accepted_file_types"));
        if ( args.length == 0 ){
            if ( debugFlag ){
                // run test
                FotoFusionTest test = new FotoFusionTest();
                test.run();
            }
            else{
                // load menu
                FotoFusionMenu ffm = new FotoFusionMenu();
                ffm.run();
            }
        }
        else{
            if ( args.length == 1){
                String pathToPreset = args[0];
                File file = new File(pathToPreset);
                if (file.exists()){
                    FotoFusionPreset ffp = new FotoFusionPreset(pathToPreset);
                    ffp.parsePropertiesFile();
                    engine = new Engine(ffp);
                    Scanner sc = new Scanner(System.in);
                    engine.runDummyPhotoCopy();
                    System.out.print("type 'run' to start the engine: ");
                    String ans = sc.nextLine();
                    if ( ans.equals("run")){
                        engine.runPhotoCopy();
                    }
                }
            }
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