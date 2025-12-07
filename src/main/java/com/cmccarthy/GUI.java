package com.cmccarthy;

import com.cmccarthy.controller.ScreenController;
import com.cmccarthy.utils.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("/main.fxml"));
            ScreenController screenController = new ScreenController(new Scene(pane));
            screenController.add("main", FXMLLoader.load(getClass().getResource("/main.fxml")));
            screenController.activate("main");

            primaryStage.setScene(screenController.getScene());
            primaryStage.setTitle("JSON 2 POJO Converter");
            primaryStage.setResizable(false);  // Keep false to avoid macOS tracking rect bug
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void stop() {
        try {
            File outputDir = new File(Constants.outputDirectory + "/");
            if (outputDir.exists()) {
                FileUtils.deleteDirectory(outputDir);
            }
        } catch (IOException e) {
            System.err.println("Failed to cleanup output directory: " + e.getMessage());
        }
    }
}
