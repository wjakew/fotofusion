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

import java.io.File;

/**
 * Object for storing photo data
 */
public class Photo {
    public String photoPath;
    public File photoFile;

    public String photoTargetPath;
    public Metadata metadata;

    /**
     * Constructor
     * @param photoPath
     */
    public Photo(String photoPath){
        this.photoPath = photoPath;
        photoFile = new File(photoPath);
        photoTargetPath = "";
    }

    /**
     * Function for parsing exif data
     */
    public void parseExifData(){
        try{
            metadata = ImageMetadataReader.readMetadata(photoFile);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.println(tag);
                }
            }
        }catch (Exception e){
            FotoFusionApplication.log.add("Error while parsing EXIF: "+e.toString());
        }

    }
}
