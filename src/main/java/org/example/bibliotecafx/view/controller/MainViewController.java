package org.example.bibliotecafx.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;
import java.io.IOException;

public class MainViewController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void handleLibros(ActionEvent event) {
        loadContent("/org/example/bibliotecafx/fxml/LibrosView.fxml");
    }

    @FXML
    public void handleAutores(ActionEvent event) {
        loadContent("/org/example/bibliotecafx/fxml/AutoresView.fxml");
    }
    @FXML
    public void handleSocios(ActionEvent event) {
        loadContent("/org/example/bibliotecafx/fxml/SociosView.fxml");
    }

    @FXML
    public void handleSalir(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void handlePrestamos(ActionEvent event) {
        loadContent("/org/example/bibliotecafx/fxml/PrestamosView.fxml");
    }

    private void loadContent(String fxmlFile) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/org/example/bibliotecafx/fxml/" + fxmlFile));
            contentArea.getChildren().clear();
            contentArea.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
