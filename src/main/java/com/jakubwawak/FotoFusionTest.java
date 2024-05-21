/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak;

import com.jakubwawak.fotofusionengine.Photo;

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
        Photo photo = new Photo("testdata/_DSF2865.JPG");
        photo.parseExifData();
    }
}
