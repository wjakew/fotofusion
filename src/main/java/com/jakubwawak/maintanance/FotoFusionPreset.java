/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.maintanance;

import com.jakubwawak.FotoFusionApplication;
import com.jakubwawak.fotofusionengine.Engine;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Function for managing and creating properties file
 */
public class FotoFusionPreset {

    String propertiesFileTitle = "fotofusion";

    int INTEGRITY_CHECK_FLAG = 4;
    String EXPECTED_FILEVERSION = "1";

    String propertiesFile;

    public boolean error;
    public boolean integrityFlag;
    public boolean fileVersionFlag;
    public String propertiesFileVersion;

    ArrayList<PropertiesField> propertiesCollection;
    public boolean fileExists;


    /**
     * Constructor
     * @param propertiesFile
     */
    public FotoFusionPreset(String propertiesFile){
        this.propertiesFile = propertiesFile + ".ffpreset";
        propertiesCollection = new ArrayList<>();
        error = false;

        // flag for checking
        File checkFile = new File(propertiesFile);
        fileExists = checkFile.exists();
    }

    /**
     * Function for getting properties value from collection by key given
     * @param key
     * @return String
     */
    public String getValue(String key){
        for( PropertiesField pf : propertiesCollection ){
            if ( pf.propertiesKey.contains(key)){
                return pf.propertiesValue;
            }
        }
        return null;
    }

    /**
     * Function for parsing properties file
     */
    public void parsePropertiesFile(){
        File configurationFile = new File(propertiesFile);
        if ( configurationFile.exists() ){
            try{
                Scanner propertiesScanner = new Scanner(configurationFile);
                while (propertiesScanner.hasNextLine()) {
                    String line = propertiesScanner.nextLine();

                    if ( line.startsWith("$") ){
                        // getting file version
                        if ( line.contains("$fileVersion") ){
                            propertiesFileVersion = valueOfKey(line);
                            fileVersionFlag = propertiesFileVersion.equals(EXPECTED_FILEVERSION);
                        }
                        else{
                            propertiesCollection.add(new PropertiesField(line.split("=")[0].replaceAll("$",""),valueOfKey(line)));
                        }
                    }
                }
                FotoFusionApplication.log.add("Loaded "+propertiesCollection.size()+" properties from configuration file.");
                integrityFlag = propertiesCollection.size() == INTEGRITY_CHECK_FLAG;
                propertiesScanner.close();
            }catch(Exception ex){
                FotoFusionApplication.log.add("Failed to parse ("+ex.toString()+")");
            }

        }
    }

    /**
     * Function for retriving everyting after the "=" in string
     * @param content
     * @return String
     */
    String valueOfKey(String content){
        // Find the index of the "=" sign.
        int indexEquals = content.indexOf('=');

        // If the "=" sign is not found, return null.
        if (indexEquals == -1) {
            return null;
        }
        // Return the substring after the "=" sign.
        return content.substring(indexEquals + 1);
    }

    /**
     * Function for creating properties file
     */
    public void createPropertiesFile(Engine engine){
        try{
            FileWriter writer = new FileWriter(propertiesFile);
            writer.write("#"+propertiesFileTitle+" properties file\n");
            writer.write("$fileVersion="+EXPECTED_FILEVERSION+"\n");
            writer.write("$sourcePath="+engine.sourcePath+"\n");
            writer.write("$destinationPath="+engine.destinationPath+"\n");
            writer.write("$folderTreeName="+String.join(",", engine.folderNameTree)+"\n");
            writer.write("$accepted_file_types="+String.join(",", FotoFusionApplication.photoExtensions)+"\n");
            writer.write("#by Jakub Wawak all rights reserved");
            writer.close();
        }catch(Exception ex){
            error = true;
            FotoFusionApplication.log.add("Failed to create .properties file ("+ex.toString()+")");
        }
    }
}