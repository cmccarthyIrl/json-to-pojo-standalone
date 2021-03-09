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

        Parent pane = FXMLLoader.load(getClass().getResource("/main.fxml"));
        ScreenController screenController = new ScreenController(new Scene(pane));
        screenController.add("main", FXMLLoader.load(getClass().getResource("/main.fxml")));
        screenController.activate("main");

        primaryStage.setScene(screenController.getScene());
        primaryStage.setTitle("JSON 2 POJO");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws IOException {
        FileUtils.deleteDirectory(new File(Constants.outputDirectory + "/"));
    }
}
