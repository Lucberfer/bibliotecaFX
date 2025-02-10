package org.example.bibliotecafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class MainApp extends Application {
    private Stage primaryStage; // Main application window

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        // Load the main view from the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/org/example/bibliotecafx/fxml/MainView.fxml"));
        Parent root = fxmlLoader.load();

        // Create the scene and set it on the primary stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Gestión de Biblioteca"); // Set window title
        primaryStage.setScene(scene);
        primaryStage.show(); // Display the application window
    }

    public static void main(String[] args) {
        launch(args); // Launches the JavaFX application
    }
}
