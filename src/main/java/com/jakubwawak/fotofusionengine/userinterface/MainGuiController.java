/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.fotofusionengine.userinterface;

import com.jakubwawak.FotoFusionApplication;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class MainGuiController {

    @FXML
    private TextField field_source;

    @FXML
    private TextField field_destinaton;

    @FXML
    ListView<String> list_log;

    @FXML
    private TextArea fld_stats;

    @FXML
    private Button btn_setsource;

    @FXML
    private Button btn_setdestination;

    @FXML
    private ComboBox<String> cbb_tags;

    @FXML
    private ListView<String> list_tags;

    @FXML
    public void initialize() {
        list_tags.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                String selectedItem = list_tags.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    list_tags.getItems().remove(selectedItem);
                    FotoFusionApplication.engine.removeTagBranch(selectedItem);
                    loadTagList();
                    loadLogElements();
                }
            }
        });
    }

    /**
     * Function for handling button source
     * @param event
     */
    @FXML
    public void handleBtnSource(ActionEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Source Directory");
        File selectedDirectory = directoryChooser.showDialog(btn_setsource.getScene().getWindow());

        if (selectedDirectory != null) {
            field_source.setText(selectedDirectory.getAbsolutePath());
            File file = new File(selectedDirectory.getAbsolutePath());
            if (file.exists() && file.isDirectory()){
                FotoFusionApplication.engine.setSourcePath(selectedDirectory.getAbsolutePath());
                FotoFusionApplication.engine.runCollectionGeneration();
                FotoFusionApplication.engine.runPhotoExifLoader();
                fld_stats.setText(FotoFusionApplication.engine.getCurrentValues());
            }
            else
                field_source.setText("Invalid directory");
        }
        loadLogElements();
    }

    /**
     * Function for handling button set destination
     * @param event
     */
    @FXML
    public void handleBtnDestination(ActionEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Destination Directory");
        File selectedDirectory = directoryChooser.showDialog(btn_setdestination.getScene().getWindow());

        if (selectedDirectory != null) {
            field_destinaton.setText(selectedDirectory.getAbsolutePath());
            File file = new File(selectedDirectory.getAbsolutePath());
            if ( file.exists() && file.isDirectory() ) {
                FotoFusionApplication.engine.setDestinationPath(selectedDirectory.getAbsolutePath());
                fld_stats.setText(FotoFusionApplication.engine.getCurrentValues());
                loadTagElements();
            }
            else
                field_destinaton.setText("Invalid directory");
        }
        loadLogElements();
    }

    /**
     * Function for loading log elements to the list
     */
    public void loadLogElements() {
        list_log.getItems().clear();
        for (String logElement : FotoFusionApplication.log.logCollection) {
            list_log.getItems().add(logElement);
        }
        list_log.scrollTo(list_log.getItems().size() - 1);
    }

    /**
     * Function for loading tag elements to the combobox
     */
    public void loadTagElements(){
        cbb_tags.getItems().clear();
        for (String tag : FotoFusionApplication.engine.collection.getCommonTags()) {
            cbb_tags.getItems().add(tag);
        }
    }

    /**
     * Function for loading log elements to the list
     */
    public void loadTagList(){
        list_tags.getItems().clear();
        for (String tag : FotoFusionApplication.engine.folderNameTree) {
            list_tags.getItems().add(tag);
        }
    }

    /**
     * Function for handling button select tags
     * @param event
     */
    @FXML
    public void handleBtnSelectTags(ActionEvent event){
        String selectedTags = cbb_tags.getSelectionModel().getSelectedItem();
        if (!selectedTags.isEmpty())
        {
            if ( !FotoFusionApplication.engine.folderNameTree.contains(selectedTags)){
                FotoFusionApplication.engine.addTagBranch(selectedTags);
                FotoFusionApplication.log.add("Current tag branch full: "+FotoFusionApplication.engine.listFolderNameTree());
            }
            else{
                FotoFusionApplication.engine.removeTagBranch(selectedTags);
                FotoFusionApplication.log.add("Current tag branch full: "+FotoFusionApplication.engine.listFolderNameTree());
            }
        }
        loadLogElements();
        loadTagList();
    }

    /**
     * Function for handling button run copy
     * @param event
     */
    @FXML
    private void handleBtnRunCopy(ActionEvent event) {
        if ( FotoFusionApplication.engine.checkGoodToStart()){
            FotoFusionApplication.engine.runPhotoCopy();
            loadLogElements();
        }
        else{
            FotoFusionApplication.log.add("Cannot run copy, check the source and destination path");
            loadLogElements();
        }
    }

}
