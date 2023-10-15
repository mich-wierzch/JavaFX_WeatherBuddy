package com.example.javafx_weatherbuddy;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("application.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("application.css");

        Image icon = new Image("icon.png");

        stage.setResizable(false);
        stage.getIcons().add(icon);
        stage.setTitle("Weather Buddy");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}