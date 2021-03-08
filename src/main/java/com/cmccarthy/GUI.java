package com.cmccarthy;

import com.cmccarthy.controller.ScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        primaryStage.setResizable(false);
        primaryStage.setTitle("JSON 2 POJO");
        primaryStage.show();
    }
}
