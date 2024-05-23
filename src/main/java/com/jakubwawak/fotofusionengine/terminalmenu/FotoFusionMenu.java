/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.fotofusionengine.terminalmenu;

import com.jakubwawak.FotoFusionApplication;
import com.jakubwawak.fotofusionengine.Engine;
import com.jakubwawak.maintanance.ConsoleColors;
import com.jakubwawak.maintanance.FotoFusionPreset;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

/**
 * Object for creating menu for FotoFusion engine
 */
public class FotoFusionMenu {

    boolean runFlag;

    ArrayList<String> commandHistory;
    EngineStatsTComponent estc;
    public FotoFusionMenu(){
        runFlag = true;
        commandHistory = new ArrayList<>();
        estc = new EngineStatsTComponent();
    }

    /**
     * Function for running the menu
     */
    public void run(){
        Scanner sc = new Scanner(System.in);
        while(runFlag){
            estc.show(FotoFusionApplication.engine);
            System.out.print(ConsoleColors.BLACK_BOLD_BRIGHT+"fotofusion :3 > "+ConsoleColors.RESET);
            mind(sc.nextLine());
        }
    }

    /**
     * Function for creating the menu logic
     * @param userInput
     */
    public void mind(String userInput){
        String[] words = userInput.split (" ");
        List<String> keyWords = Arrays.asList(words);
        if ( keyWords.contains("exit") ){
            // contains exit - stopping the program
            FotoFusionApplication.log.saveLogToFile();
            runFlag = false;
        }
        else if ( keyWords.contains("clear") ){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            estc.show(FotoFusionApplication.engine);
        }
        else{
            // run all application functions
            if ( keyWords.contains("setsource") ){
                if ( keyWords.size() == 2 ){
                    FotoFusionApplication.engine.setSourcePath(words[1]);
                }
                else{
                    System.out.println("Wrong number of arguments");
                }
            }
            else if (keyWords.contains("setdestination")){
                if ( keyWords.size() == 2 ){
                    FotoFusionApplication.engine.setDestinationPath(words[1]);
                }
                else{
                    System.out.println("Wrong number of arguments");
                }
            }
            else if (keyWords.contains("addbranch")){
                if ( keyWords.size() == 2 ){
                    FotoFusionApplication.engine.addTagBranch(words[1]);
                }
                else{
                    System.out.println("Wrong number of arguments");
                }
            }
            else if (keyWords.contains("removebranch")){
                if ( keyWords.size() == 2 ){
                    FotoFusionApplication.engine.removeTagBranch(words[1]);
                }
                else{
                    System.out.println("Wrong number of arguments");
                }
            }
            else if (keyWords.contains("reset")){
                FotoFusionApplication.engine = new Engine();
            }
            else if (keyWords.contains("runcopy")){
                System.out.println("Showing list of changes: ");
                FotoFusionApplication.engine.runDummyPhotoCopy();
                Scanner sc = new Scanner(System.in);
                System.out.print("to confirm the copying type 'yes' : ");
                String confirmation = sc.nextLine();
                if ( confirmation.equals("yes") ){
                    FotoFusionApplication.engine.runPhotoCopy();
                }
                else{
                    System.out.println("Aborting the operation");
                }
            }
            else if (keyWords.contains("help")){
                estc.showHelp();
            }
            else if (keyWords.contains("showstats")){
                estc.show(FotoFusionApplication.engine);
            }
            else if ( keyWords.contains("runexifloader") ){
                FotoFusionApplication.engine.runPhotoExifLoader();
            }
            else if (keyWords.contains("listbranches")){
                FotoFusionApplication.engine.listBranches();
            }
            else if (keyWords.contains("runcollection")){
                FotoFusionApplication.engine.runCollectionGeneration();
            }
            else if (keyWords.contains("showhistory")){
                for(String line : commandHistory){
                    System.out.println(line);
                }
            }
            else if (keyWords.contains("save")){
                if ( keyWords.size() == 2){
                    FotoFusionPreset preset = new FotoFusionPreset(words[1]);
                    preset.createPropertiesFile(FotoFusionApplication.engine);
                }
                else{
                    System.out.println("Wrong number of arguments");

                }
            }
            else{
                System.out.println("Unknown command");
            }
        }
    }

}
