/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.fotofusionengine.terminalmenu;

import com.jakubwawak.fotofusionengine.Engine;
import com.jakubwawak.maintanance.ConsoleColors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Object for showing current Engine object information
 */
public class EngineStatsTComponent {

    /**
    * Constructor
    */
    public EngineStatsTComponent(){}

    /**
    * Function for showing the stats
    */
    public void show(Engine engine){

        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT);
        System.out.println("-----------------------------------------");
        System.out.println("Foto Fusion Engine Statistics:");
        // source path
        if (engine.sourcePath.isEmpty()){
            System.out.println("source path: not set");
        }
        else{
            System.out.println("source path: "+engine.sourcePath);
        }
        // destination path
        if (engine.destinationPath == null){
            System.out.println("destination path: not set");
        }
        else{
            System.out.println("destination path: "+engine.destinationPath);
        }
        // collection data
        if (engine.collection == null){
            System.out.println("Collection: not set");
        }
        else{
            System.out.println("Collection: set, "+engine.collection.collection.size()+" photos");
        }
        // folder name tree
        if (engine.folderNameTree.isEmpty()){
            System.out.println("folder branches: not set");
        }
        else{
            System.out.println("Folder name tree: "+engine.listFolderNameTree());
        }
        System.out.println("-----------------------------------------");
        System.out.print(ConsoleColors.RESET);
    }

    /**
     * Function for showing the help
     */
    public void showHelp() {
        String helpFilePath = "./CONSOLE.md"; // Replace with the actual path to CONSOLE.md

        try {
            String helpInfo = new String(Files.readAllBytes(Paths.get(helpFilePath)));
            System.out.println(helpInfo);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the help file: " + e.getMessage());
        }
    }
}
