package com.cse203.adressbook;

import com.cse203.adressbook.datamodel.PersonData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setScene(new Scene(root, 500, 200));
        root.requestFocus();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Adress Book");
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
