/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.fotofusionengine;

import com.jakubwawak.FotoFusionApplication;
import com.jakubwawak.maintanance.FotoFusionPreset;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
     * Function for checking if engine is ready to start
     * @return
     */
    public boolean checkGoodToStart(){
        if (sourcePath.isEmpty() || destinationPath.isEmpty() || collection == null || folderNameTree.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * Constructor with preset object
     * @param preset
     */
    public Engine(FotoFusionPreset preset){
        this.sourcePath = "";

        folderNameTree = (ArrayList) Arrays.asList(preset.getValue("folderTreeName").split(","));
        setSourcePath(preset.getValue("source_path"));
        setDestinationPath(preset.getValue("destination_path"));
        addTagBranchesFromTree();
        runCollectionGeneration();
        runPhotoExifLoader();
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
     * Function for getting current values
     * @return
     */
    public String getCurrentValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("Source Path: ").append(sourcePath).append("\n");
        sb.append("Destination Path: ").append(destinationPath).append("\n");
        sb.append("Photo Collection size: "+collection.collection.size()).append("\n");
        sb.append("Common tags size: ").append(collection.getCommonTags().size()).append("\n");
        sb.append("Folder Name Tree: ").append(folderNameTree.toString()).append("\n");
        return sb.toString();
    }

    /**
     * Function for checking if source and destination path are empty
     * @return boolean
     */
    public boolean checkIfEmpty(){
        if (sourcePath.isEmpty() || destinationPath.isEmpty()){
            return true;
        }
        return false;
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
            if (!folderNameTree.contains(tagName))
                folderNameTree.add(tagName);
            FotoFusionApplication.log.add("Tag ("+tagName+")branch added to all photos");
        }
        else{
            FotoFusionApplication.log.add("Given tag name is not in common tags list");
        }
    }

    /**
     * Function for adding tag branches from tree
     */
    public void addTagBranchesFromTree(){
        for(String tag : folderNameTree){
            addTagBranch(tag);
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
            folderNameTree.remove(tagName);
            FotoFusionApplication.log.add("Tag ("+tagName+")branch removed from all photos");
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
