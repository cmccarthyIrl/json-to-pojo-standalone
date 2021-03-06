package com.cmccarthy.controller;

import com.cmccarthy.utils.POJOHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

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
    ComboBox<?> targetLang = new ComboBox<>();
    @FXML
    ComboBox<?> sourceLang = new ComboBox<>();
    @FXML
    ComboBox<?> annotationLang = new ComboBox<>();
    @FXML
    TextArea inputTextArea = new TextArea();
    @FXML
    TextArea outputTextArea = new TextArea();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        targetLang.getSelectionModel().selectFirst();
        sourceLang.getSelectionModel().selectFirst();
        annotationLang.getSelectionModel().selectFirst();
        setLabelVisibility(false);
        setLabelVisibility(false);
    }

    @FXML
    public void generatePageObject() throws IOException {
        updateText();
        try {
            String value = pojoHelper.buildJson();
            outputTextArea.setText(value);
            setLabelVisibility(false);
            setLabelVisibility(false);
        }catch (Exception ex){
            setLabelVisibility(true);
            setLabelVisibility(true);
        }
        pojoHelper.removeFiles();
    }

    public void updateText() throws IOException {
        inputTextArea.setText(inputTextArea.getText());
        Files.write(Paths.get("target/required.json"), inputTextArea.getText().getBytes());
    }

    private void setLabelVisibility(boolean visible){
        warningLabel.setVisible(visible);
        warningLabel.setManaged(visible);
    }
}
