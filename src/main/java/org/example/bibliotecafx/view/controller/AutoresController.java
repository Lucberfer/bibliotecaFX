package org.example.bibliotecafx.view.controller;

import javafx.event.ActionEvent;
import org.example.bibliotecafx.dao.AutorDAO;
import org.example.bibliotecafx.entities.Autor;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AutoresController {

    // TableView for displaying authors
    @FXML
    private TableView<Autor> tablaAutores;
    @FXML
    private TableColumn<Autor, Long> colId;
    @FXML
    private TableColumn<Autor, String> colNombre;
    @FXML
    private TableColumn<Autor, String> colNacionalidad;

    // TextField for searching authors
    @FXML
    private TextField tfBuscarNombre;

    // TextFields for author input
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNacionalidad;

    private AutorDAO autorDAO = new AutorDAO(); // DAO for author operations
    private ObservableList<Autor> autoresList; // Observable list for TableView

    @FXML
    public void initialize() {
        // Setting up table column bindings
        colId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colNacionalidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNacionalidad()));

        // Listener to update text fields when an author is selected
        tablaAutores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tfNombre.setText(newSelection.getNombre());
                tfNacionalidad.setText(newSelection.getNacionalidad());
            }
        });

        tablaAutores.setRowFactory(tv -> {
            TableRow<Autor> row = new TableRow<>();
            Tooltip tooltip = new Tooltip();
            tooltip.setStyle("-fx-font-size: 16px;");
            row.itemProperty().addListener((obs, oldAutor, newAutor) -> {
                if (newAutor == null) {
                    row.setTooltip(null);
                } else {
                    StringBuilder details = new StringBuilder();
                    details.append("ID: ").append(newAutor.getId()).append("\n")
                            .append("Nombre: ").append(newAutor.getNombre()).append("\n")
                            .append("Nacionalidad: ").append(newAutor.getNacionalidad());
                    tooltip.setText(details.toString());
                    row.setTooltip(tooltip);
                }
            });
            return row;
        });

        refreshTable(); // Load authors into the table
    }

    @FXML
    public void onBuscar(ActionEvent event) {
        // Searches for authors by name
        String nombre = tfBuscarNombre.getText();
        autoresList = FXCollections.observableArrayList(autorDAO.searchAutores(nombre));
        tablaAutores.setItems(autoresList);
    }

    @FXML
    public void onRefresh(ActionEvent event) {
        refreshTable(); // Refreshes the table
    }

    @FXML
    public void onAgregar(ActionEvent event) {
        // Adds a new author
        String nombre = tfNombre.getText();
        String nacionalidad = tfNacionalidad.getText();

        if (nombre.isEmpty()) {
            showAlert("Error", "El nombre es requerido");
            return;
        }

        Autor autor = new Autor(nombre, nacionalidad);
        autorDAO.addAutor(autor);
        refreshTable();
        clearFields();
        showAlert("Éxito", "Autor agregado");
    }

    @FXML
    public void onModificar(ActionEvent event) {
        // Updates the selected author
        Autor autor = tablaAutores.getSelectionModel().getSelectedItem();
        if (autor == null) {
            showAlert("Error", "Seleccione un autor para modificar");
            return;
        }

        autor.setNombre(tfNombre.getText());
        autor.setNacionalidad(tfNacionalidad.getText());
        autorDAO.updateAutor(autor);
        refreshTable();
        clearFields();
        showAlert("Éxito", "Autor modificado");
    }

    @FXML
    public void onEliminar(ActionEvent event) {
        // Deletes the selected author
        Autor autor = tablaAutores.getSelectionModel().getSelectedItem();
        if (autor == null) {
            showAlert("Error", "Seleccione un autor para eliminar");
            return;
        }

        autorDAO.deleteAutor(autor);
        refreshTable();
        clearFields();
        showAlert("Éxito", "Autor eliminado");
    }

    @FXML
    public void onRefresh() {
        refreshTable(); // Refreshes the table
    }

    @FXML
    public void onLimpiar(ActionEvent event) {
        clearFields(); // Clears input fields
    }

    public void refreshTable() {
        // Reloads the list of authors into the table
        autoresList = FXCollections.observableArrayList(autorDAO.listAllAutores());
        tablaAutores.setItems(autoresList);
    }

    public void clearFields() {
        // Clears the text fields
        tfNombre.clear();
        tfNacionalidad.clear();
    }

    public void showAlert(String title, String message) {
        // Displays an alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
