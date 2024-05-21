/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.fotofusionengine;

import com.jakubwawak.FotoFusionApplication;

import java.io.File;
import java.util.ArrayList;

/**
 * Class for storing photo collection, based on EXIF data and given path
 */
public class PhotoCollection {

    public String path;

    public ArrayList<Photo> collection;

    public File rootObject;
    public boolean pathExistsFlag;


    // fields for storing statistis
    int loopedFiles, loopedFolders;

    /**
     * Constructor
     *
     * @param path
     */
    public PhotoCollection(String path) {
        this.path = path;
        loopedFiles = 0;
        loopedFolders = 0;
        collection = new ArrayList<>();
        FotoFusionApplication.log.logCollection.add("PhotoCollection object created for path: " + path);
        rootObject = new File(path);
        pathExistsFlag = rootObject.exists();
    }

    /**
     * Function for creating collection of photos
     */
    public void createCollection() {
        if (pathExistsFlag) {
            FotoFusionApplication.log.add("Path exists, creating collection");
            if (rootObject.isDirectory()) {
                FotoFusionApplication.log.add("Directory found: " + rootObject.getName());
                addPhotosFromDirectory(rootObject);
            } else {
                FotoFusionApplication.log.add("Path is not directory, skipping: " + rootObject.getName());
            }

        }
    }

    /**
     * Function for showing collection statistics
     */
    public void showCollectionStats(){
        FotoFusionApplication.log.add("Photo collection stats:");
        FotoFusionApplication.log.add("Photos in collection: "+collection.size());
        FotoFusionApplication.log.add("Looped files: "+loopedFiles);
        FotoFusionApplication.log.add("Looped folders: "+loopedFolders);
    }

    /**
     * Function for parse EXIF data from collection
     */
    public void loadPhotoCollectionExifParser(){

    }


    /**
     * Function for adding photos from directory
     * @param directory
     */
    private void addPhotosFromDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    loopedFolders++;
                    addPhotosFromDirectory(file);
                } else {
                    if (isPhoto(file)) {
                        collection.add(new Photo(file.getAbsolutePath()));
                        FotoFusionApplication.log.add("Photo added: " + file.getName());
                    }
                }
            }
        }
    }

    /**
     * Function for checking if file is photo and adding it to the collection
     * @param file
     * @return
     */
    private boolean isPhoto(File file) {
        loopedFiles++;
        String fileName = file.getName().toLowerCase();
        for (String extension : FotoFusionApplication.photoExtensions) {
            if (fileName.endsWith("." + extension)) {
                return true;
            }
        }
        return false;
    }
}