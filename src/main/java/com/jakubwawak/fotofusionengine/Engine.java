/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.fotofusionengine;

import com.drew.metadata.Tag;
import com.jakubwawak.FotoFusionApplication;

import java.io.File;
import java.util.ArrayList;

/**
 * Class for running engine data
 */
public class Engine {

    public String sourcePath;
    public String destinationPath;

    public PhotoCollection collection;

    public ArrayList<String> folderNameTree;

    /**
     * Constructor
     */
    public Engine(){
        this.sourcePath = "";
        folderNameTree = new ArrayList<>();
    }

    /**
     * Function for setting path
     * @param path
     */
    public int setDestinationPath(String path){
        File file = new File(path);
        if ( file.exists() && file.isDirectory() ){
            destinationPath = path;
            FotoFusionApplication.log.add("Destination path set to: "+path);
            return 1;
        }
        else{
            FotoFusionApplication.log.add("Given path is not directory or does not exists");
            return 0;
        }
    }

    /**
     * Function for setting source path
     * @param path
     */
    public int setSourcePath(String path){
        File file = new File(path);
        if ( file.exists() && file.isDirectory() ){
            this.sourcePath = path;
            FotoFusionApplication.log.add("Source path set to: "+path);
            return 1;
        }
        else{
            FotoFusionApplication.log.add("Given path is not directory or does not exists");
            return 0;
        }
    }

    /**
     * Function for running collection generation
     */
    public void runCollectionGeneration(){
        collection = new PhotoCollection(sourcePath);
        collection.createCollection();
        collection.showCollectionStats();
    }

    /**
     * Function for running photo exif loader
     */
    public void runPhotoExifLoader(){
        collection.loadPhotoCollectionExifParser();
    }

    /**
     * Function for listing available branches (tags from photos)
     */
    public void listBranches(){
        System.out.println("\033[0;35m"+"Listing tags: ("+collection.getCommonTags().size()+")");
        int index = 1;
        for(String tagName : collection.getCommonTags()){
            System.out.println("\033[0;35m"+index+": "+tagName);
            index++;
        }
        System.out.print("\033[0m");
    }

    /**
     * Function for listing folder name tree
     * @return
     */
    public String listFolderNameTree(){
        String data = "";
        for(String folder : folderNameTree){
            data = data + folder + ",";
        }
        return data;
    }

    /**
     * Function for adding tag branch to all photos
     * @param tagName
     */
    public void addTagBranch(String tagName){
        if ( collection.getCommonTags().contains(tagName)){
            for(Photo photo : collection.collection){
                photo.addBranch(tagName);
            }
            FotoFusionApplication.log.add("Tag branch added to all photos");
        }
        else{
            FotoFusionApplication.log.add("Given tag name is not in common tags list");
        }

    }

    /**
     * Function for removing tag branch from all photos
     * @param tagName
     */
    public void removeTagBranch(String tagName){
        if ( collection.getCommonTags().contains(tagName) ){
            for(Photo photo : collection.collection){
                photo.removeBranch(tagName);
            }
            FotoFusionApplication.log.add("Tag branch added to all photos");
        }
        else{
            FotoFusionApplication.log.add("Given tag name is not in common tags list");
        }
    }

    /**
     * Function for running dummy photo copy - showing information about photos
     * will be copied
     */
    public void runDummyPhotoCopy(){
        for(Photo photo : collection.collection){
            System.out.println(photo.getPhotoJourneyData(sourcePath));
        }
    }

    /**
     * Function for running photo copy
     */
    public void runPhotoCopy(){
        for(Photo photo : collection.collection){
            FolderTreeGenerator ftg = new FolderTreeGenerator(photo.getFolderTreePath(destinationPath));
            ftg.createTree();
            photo.copyPhotoToTargetBranch(destinationPath);
        }
    }


}
