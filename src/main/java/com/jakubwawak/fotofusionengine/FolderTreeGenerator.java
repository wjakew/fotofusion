/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.fotofusionengine;

import com.jakubwawak.FotoFusionApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Object for generating tree of folder based on the path given
 */
public class FolderTreeGenerator {

    public String pathToCreate;

    /**
     * Constructor
     * @param pathToCreate
     */
    public FolderTreeGenerator(String pathToCreate){
        this.pathToCreate = pathToCreate;
        FotoFusionApplication.log.add("Loaded path to create: "+pathToCreate);

    }

    /**
     * Function for creating tree folders based on the path
     */
    public void createTree(){
        FotoFusionApplication.log.add("Creating tree for path: "+pathToCreate);
        Path path = Paths.get(pathToCreate);
        try {
            if ( !Files.exists(path) ){
                FotoFusionApplication.log.add("Path does not exists, creating");
                Files.createDirectories(path);
                FotoFusionApplication.log.add("Tree created for path: "+pathToCreate);
            }
            else{
                FotoFusionApplication.log.add("Path exists, skipping");
            }
        } catch (IOException e) {
            FotoFusionApplication.log.add("Error while creating tree for path: "+e.toString());
        }
    }
}
