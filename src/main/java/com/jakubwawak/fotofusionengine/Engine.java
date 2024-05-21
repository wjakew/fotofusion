/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.fotofusionengine;

/**
 * Class for running engine data
 */
public class Engine {

    public String path;

    public PhotoCollection collection;

    /**
     * Constructor
     * @param path
     */
    public Engine(String path){
        this.path = path;
        collection = new PhotoCollection(path);
    }

    /**
     * Function for running collection generation
     */
    public void runCollectionGeneration(){
        collection.createCollection();
        collection.showCollectionStats();
    }

    /**
     * Function for running photo exif loader
     */
    public void runPhotoExifLoader(){

    }
}
