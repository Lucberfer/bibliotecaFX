package org.example.bibliotecafx.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;

import java.io.IOException;
import java.net.URL;

public class MainViewController {

    @FXML
    private StackPane contentArea; // Main container for switching views

    /**
     * Closes the application when the "Salir" (Exit) button is clicked.
     */
    @FXML
    private void handleSalir() {
        Platform.exit(); // Terminates the JavaFX application
    }

    /**
     * Loads the "Libros" (Books) view into the content area.
     */
    @FXML
    private void handleLibros() {
        loadContent("LibrosView.fxml");
    }

    /**
     * Loads the "Autores" (Authors) view into the content area.
     */
    @FXML
    private void handleAutores() {
        loadContent("AutoresView.fxml");
    }

    /**
     * Loads the "Socios" (Members) view into the content area.
     */
    @FXML
    private void handleSocios() {
        loadContent("SociosView.fxml");
    }

    /**
     * Loads the "Préstamos" (Loans) view into the content area.
     */
    @FXML
    private void handlePrestamos() {
        loadContent("PrestamosView.fxml");
    }

    /**
     * Loads an FXML file into the content area.
     *
     * @param fxmlFile The name of the FXML file to load.
     */
    private void loadContent(String fxmlFile) {
        // Defines the resource path for the FXML file
        String resourcePath = "/org/example/bibliotecafx/fxml/" + fxmlFile;
        URL resource = getClass().getResource(resourcePath);

        if (resource == null) {
            System.err.println("Resource not found: " + resourcePath);
            return;
        }

        try {
            // Loads the FXML file and replaces the content in the StackPane
            Node node = FXMLLoader.load(resource);
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace(); // Prints error details if FXML loading fails
        }
    }
}
