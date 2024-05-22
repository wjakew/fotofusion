/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.fotofusionengine;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.jakubwawak.FotoFusionApplication;
import com.jakubwawak.maintanance.ConsoleColors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Object for storing photo data
 */
public class Photo {
    public String photoPath;
    public File photoFile;

    public String photoTargetPath;
    private Metadata metadata;
    public ArrayList<Tag> tags;

    public ArrayList<Tag> folderTree;

    /**
     * Constructor
     * @param photoPath
     */
    public Photo(String photoPath){
        this.photoPath = photoPath;
        photoFile = new File(photoPath);
        photoTargetPath = "";
        tags = new ArrayList<>();
        folderTree = new ArrayList<>();
    }

    /**
     * Function for parsing exif data
     */
    public void parseExifData(){
        try{
            metadata = ImageMetadataReader.readMetadata(photoFile);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    tags.add(tag);
                }
            }
            FotoFusionApplication.log.add("Loaded "+tags.size()+" tags from photo: "+photoFile.getName());
        }catch (Exception e){
            FotoFusionApplication.log.add("Error while parsing EXIF: "+e.toString());
        }
    }

    /**
     * Function for adding branch to folder tree
     * @param tag
     */
    void addBranch(Tag tag){
        folderTree.add(tag);
        FotoFusionApplication.log.add("Current state of the branch: "+getFolderTreePath());
    }


    /**
     * Function for adding branch to folder tree
     * @param tagName
     */
    public void addBranch(String tagName){
        boolean foundFlag = false;
        for( Tag tag : tags ){
            if(tag.getTagName().equals(tagName)){
                addBranch(tag);
                foundFlag = true;
                break;
            }
        }

        if ( foundFlag ){
            FotoFusionApplication.log.add("Added to branch: "+tagName);
            FotoFusionApplication.log.add("Current state of the branch: "+getFolderTreePath());
        }
        else{
            FotoFusionApplication.log.add("Tag not found: "+tagName);
        }
    }

    /**
     * Function for removing branch from folder tree
     * @param tagName
     */
    public void removeBranch(String tagName){
        boolean foundFlag = false;
        for( Tag tag : tags ){
            if(tag.getTagName().equals(tagName)){
                removeBranch(tag);
                foundFlag = true;
                break;
            }
        }

        if ( foundFlag ){
            FotoFusionApplication.log.add("Removed from branch: "+tagName);
            FotoFusionApplication.log.add("Current state of the branch: "+getFolderTreePath());
        }
        else{
            FotoFusionApplication.log.add("Tag not found: "+tagName);
        }
    }

    /**
     * Function for removing branch from folder tree
     * @param tag
     */
    void removeBranch(Tag tag){
        folderTree.remove(tag);
    }

    /**
     * Function for swapping branches
     * @param tag1
     * @param tag2
     */
    public void swapBranch(Tag tag1, Tag tag2){
        Collections.swap(folderTree, folderTree.indexOf(tag1), folderTree.indexOf(tag2));
    }

    /**
     * Function for showing folder tree
     */
    public String getFolderTreePath(String rootPath){
        Pattern pattern = Pattern.compile("\\d{4}:\\d{2}:\\d{2} \\d{2}:\\d{2}:\\d{2}");
        for(Tag tag : folderTree){
            Matcher matcher = pattern.matcher(tag.getDescription());
            if ( matcher.find()){
                String date = tag.getDescription().split(" ")[0];
                date = date.replaceAll(":","-");

                date = date.replaceAll(" ","");
                rootPath = rootPath + "/" + date;
            }
            else{
                String branchName = tag.getDescription();
                branchName = branchName.replaceAll(" ","");
                rootPath = rootPath + "/" + branchName;
            }

        }
        rootPath = rootPath.replaceAll(" ","");
        rootPath = rootPath.stripLeading().stripTrailing();
        FotoFusionApplication.log.add("Current state of the branch: "+rootPath);
        return rootPath;
    }

    /**
     * Function for showing folder tree
     */
    public String getFolderTreePath(){
        String rootPath = "...";
        for(Tag tag : folderTree){
            String branchName = tag.getDescription();
            branchName = branchName.replaceAll(" ","");
            rootPath = rootPath + "/" + branchName;
        }
        rootPath = rootPath.replaceAll(" ","");
        rootPath = rootPath.stripLeading().stripTrailing();
        FotoFusionApplication.log.add("Current state of the branch: "+rootPath);
        return rootPath;
    }

    /**
     * Function for copying photo from target path
     * @return
     */
    public int copyPhotoToTargetBranch(String rootPath){
        try {
            Path srcPath = Paths.get(photoPath);
            Path treePath = Paths.get(getFolderTreePath(rootPath)+"/"+photoFile.getName());
            Files.copy(srcPath, treePath, StandardCopyOption.REPLACE_EXISTING);
            FotoFusionApplication.log.add("Photo ("+photoFile.getName()+") copied to: "+treePath);
            return 1;
        } catch (IOException e) {
            FotoFusionApplication.log.add("Error while copying photo: "+e.toString());
            return 0;
        }
    }

    /**
     * Function for getting photo journey data ( information about how photo will be copied )
     * @param rootPath
     * @return String
     */
    public String getPhotoJourneyData(String rootPath){
        String data = ConsoleColors.YELLOW_BOLD_BRIGHT+"\n------------------------\nObject: "+photoFile.getName()
                +" will be copied to: "+getFolderTreePath(rootPath)+"\nbased on given tags:\n";

        for(Tag tag : folderTree){
            data = data + tag.getTagName() + " : " + tag.getDescription() + "\n";
        }
        data = data+"------------------------\n"+ConsoleColors.RESET;
        return data;
    }
}
