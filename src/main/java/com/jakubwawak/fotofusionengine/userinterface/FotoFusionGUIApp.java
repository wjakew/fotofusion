/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.fotofusionengine.userinterface;// Import the necessary JavaFX classes
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Object for creating GUI for FotoFusion engine
 */
public class FotoFusionGUIApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/main_gui.fxml"));

            // Create a Scene containing the root
            Scene scene = new Scene(root);

            // Set the Scene to the Stage
            primaryStage.setScene(scene);

            // Set the title of the Stage
            primaryStage.setTitle("FotoFusion");

            // Show the Stage (the window)
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Launch the JavaFX application
    public static void run(String[] args) {
        launch(args);
    }
}