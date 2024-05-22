/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak;

import com.drew.metadata.Tag;
import com.jakubwawak.fotofusionengine.Engine;
import com.jakubwawak.fotofusionengine.FolderTreeGenerator;
import com.jakubwawak.fotofusionengine.Photo;
import com.jakubwawak.fotofusionengine.PhotoCollection;

import java.util.ArrayList;

/**
 * Function for testing FotoFusion engine
 */
public class FotoFusionTest {

    /**
     * Constructor
     */
    public FotoFusionTest(){
        FotoFusionApplication.log.add("Running tests...");
    }

    /**
     * Function for running tests
     */
    public void run(){
        String rootPath = "/home/jakubwawak/Downloads/fotofusion";

        Engine engine = new Engine();
        engine.setSourcePath("/home/jakubwawak/Downloads/testphotos");
        engine.setDestinationPath(rootPath);
        engine.runCollectionGeneration();
        engine.runPhotoExifLoader();
        engine.addTagBranch("ffdate");
        engine.addTagBranch("Model");
        engine.addTagBranch("Date/Time Original");
        engine.runPhotoCopy();
    }
}
