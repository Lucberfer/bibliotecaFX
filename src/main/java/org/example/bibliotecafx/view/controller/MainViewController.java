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
    private StackPane contentArea;

    @FXML
    private void handleSalir() {
        Platform.exit();
    }

    @FXML
    private void handleLibros() {
        loadContent("LibrosView.fxml");
    }

    @FXML
    private void handleAutores() {
        loadContent("AutoresView.fxml");
    }

    @FXML
    private void handleSocios() {
        loadContent("SociosView.fxml");
    }

    @FXML
    private void handlePrestamos() {
        loadContent("PrestamosView.fxml");
    }

    private void loadContent(String fxmlFile) {

        String resourcePath = "/org/example/bibliotecafx/fxml/" + fxmlFile;
        URL resource = getClass().getResource(resourcePath);
        if (resource == null) {
            System.err.println("No se encontró el recurso: " + resourcePath);
            return;
        }
        try {
            Node node = FXMLLoader.load(resource);
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
