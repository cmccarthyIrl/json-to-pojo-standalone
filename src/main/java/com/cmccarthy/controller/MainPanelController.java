package com.cmccarthy.controller;

import com.cmccarthy.utils.Constants;
import com.cmccarthy.utils.POJOHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.jsonschema2pojo.AnnotationStyle;
import org.jsonschema2pojo.SourceType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainPanelController implements Initializable {

    private final POJOHelper pojoHelper = new POJOHelper();
    @FXML
    Button generatePOJO = new Button();
    @FXML
    Label warningLabel = new Label();
    @FXML
    ComboBox<String> sourceLang = new ComboBox<>();
    @FXML
    ComboBox<String> annotationLang = new ComboBox<>();
    @FXML
    TextArea inputTextArea = new TextArea();
    @FXML
    TextArea outputTextArea = new TextArea();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sourceLang.getSelectionModel().selectFirst();
        annotationLang.getSelectionModel().selectFirst();
        setLabelVisibility(false, "");
        
        // Add placeholder text
        inputTextArea.setPromptText("Paste your JSON here...");
        outputTextArea.setPromptText("Generated POJO classes will appear here...");
    }

    @FXML
    public void generatePageObject() {
        // Validate input
        String input = inputTextArea.getText();
        if (input == null || input.trim().isEmpty()) {
            setLabelVisibility(true, "Please provide JSON input");
            return;
        }
        
        try {
            // Configure POJO helper based on dropdown selections
            configurePojoHelper();
            
            // Generate files
            updateText();
            String value = pojoHelper.buildJson();
            outputTextArea.setText(value);
            setLabelVisibility(false, "");
        } catch (Exception ex) {
            String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Invalid input. Please check your JSON format.";
            setLabelVisibility(true, errorMessage);
            outputTextArea.setText("Error: " + errorMessage);
            ex.printStackTrace();
        } finally {
            try {
                pojoHelper.removeFiles();
            } catch (IOException e) {
                System.err.println("Failed to cleanup temporary files: " + e.getMessage());
            }
        }
    }
    
    private void configurePojoHelper() {
        // Set source type
        String source = sourceLang.getValue();
        if ("JSON Schema".equals(source)) {
            pojoHelper.setSourceType(SourceType.JSONSCHEMA);
        } else if ("YAML Schema".equals(source)) {
            pojoHelper.setSourceType(SourceType.YAMLSCHEMA);
        } else if ("YAML".equals(source)) {
            pojoHelper.setSourceType(SourceType.YAML);
        } else {
            pojoHelper.setSourceType(SourceType.JSON);
        }
        
        // Set annotation style
        String annotation = annotationLang.getValue();
        if ("Jackson 1.x".equals(annotation)) {
            pojoHelper.setAnnotationStyle(AnnotationStyle.JACKSON);
        } else if ("Gson".equals(annotation)) {
            pojoHelper.setAnnotationStyle(AnnotationStyle.GSON);
        } else if ("None".equals(annotation)) {
            pojoHelper.setAnnotationStyle(AnnotationStyle.NONE);
        } else {
            pojoHelper.setAnnotationStyle(AnnotationStyle.JACKSON2);
        }
    }

    public void updateText() throws IOException {
        File newDirectory = new File(Constants.outputDirectory);
        if (!newDirectory.exists()) {
            newDirectory.mkdir();
        }
        Files.write(Paths.get(Constants.outputDirectory + "/required.json"), inputTextArea.getText().getBytes());
    }

    private void setLabelVisibility(boolean visible, String message) {
        warningLabel.setVisible(visible);
        warningLabel.setManaged(visible);
        if (visible && message != null && !message.isEmpty()) {
            warningLabel.setText(message);
        }
    }
}
