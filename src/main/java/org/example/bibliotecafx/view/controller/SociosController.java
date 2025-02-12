package org.example.bibliotecafx.view.controller;

import org.example.bibliotecafx.dao.SocioDAO;
import org.example.bibliotecafx.entities.Socio;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SociosController {

    // TableView and TableColumns for displaying members
    @FXML
    private TableView<Socio> tablaSocios;
    @FXML
    private TableColumn<Socio, Long> colId;
    @FXML
    private TableColumn<Socio, String> colNombre;
    @FXML
    private TableColumn<Socio, String> colDireccion;
    @FXML
    private TableColumn<Socio, String> colTelefono;

    // TextFields for searching members
    @FXML
    private TextField tfBuscarNombre;
    @FXML
    private TextField tfBuscarTelefono;

    // TextFields for member input
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfTelefono;

    private SocioDAO socioDAO = new SocioDAO(); // DAO for member operations
    private ObservableList<Socio> sociosList; // Observable list for TableView

    @FXML
    public void initialize() {
        // Setting up table column bindings
        colId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));

        // Listener to update text fields when a member is selected
        tablaSocios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tfNombre.setText(newSelection.getNombre());
                tfDireccion.setText(newSelection.getDireccion());
                tfTelefono.setText(newSelection.getTelefono());
            }
        });

        tablaSocios.setRowFactory(tv -> {
            TableRow<Socio> row = new TableRow<>();
            Tooltip tooltip = new Tooltip();
            tooltip.setStyle("-fx-font-size: 16px;");
            row.itemProperty().addListener((obs, oldSocio, newSocio) -> {
                if (newSocio == null) {
                    row.setTooltip(null);
                } else {
                    StringBuilder details = new StringBuilder();
                    details.append("ID: ").append(newSocio.getId()).append("\n")
                            .append("Nombre: ").append(newSocio.getNombre()).append("\n")
                            .append("Dirección: ").append(newSocio.getDireccion()).append("\n")
                            .append("Teléfono: ").append(newSocio.getTelefono());
                    tooltip.setText(details.toString());
                    row.setTooltip(tooltip);
                }
            });
            return row;
        });


        refreshTable(); // Load members into the table
    }

    @FXML
    private void onAgregar() {
        // Adds a new member
        String nombre = tfNombre.getText();
        String direccion = tfDireccion.getText();
        String telefono = tfTelefono.getText();

        if (nombre.isEmpty()) {
            showAlert("Error", "El nombre es requerido");
            return;
        }

        Socio socio = new Socio(nombre, direccion, telefono);
        socioDAO.addSocio(socio);
        refreshTable();
        clearFields();
        showAlert("Éxito", "Socio agregado");
    }

    @FXML
    private void onModificar() {
        // Updates the selected member
        Socio socio = tablaSocios.getSelectionModel().getSelectedItem();
        if (socio == null) {
            showAlert("Error", "Seleccione un socio para modificar");
            return;
        }

        socio.setNombre(tfNombre.getText());
        socio.setDireccion(tfDireccion.getText());
        socio.setTelefono(tfTelefono.getText());
        socioDAO.updateSocio(socio);
        refreshTable();
        clearFields();
        showAlert("Éxito", "Socio modificado");
    }

    @FXML
    private void onEliminar() {
        // Deletes the selected member
        Socio socio = tablaSocios.getSelectionModel().getSelectedItem();
        if (socio == null) {
            showAlert("Error", "Seleccione un socio para eliminar");
            return;
        }
        socioDAO.deleteSocio(socio);
        refreshTable();
        clearFields();
        showAlert("Éxito", "Socio eliminado");
    }

    @FXML
    private void onBuscar() {
        // Searches for members by name and/or phone number
        String nombre = tfBuscarNombre.getText();
        String telefono = tfBuscarTelefono.getText();
        sociosList = FXCollections.observableArrayList(socioDAO.searchSocios(nombre, telefono));
        tablaSocios.setItems(sociosList);
    }

    @FXML
    private void onRefresh() {
        refreshTable(); // Refreshes the table
    }

    @FXML
    private void onLimpiar() {
        clearFields(); // Clears input fields
    }

    private void refreshTable() {
        // Reloads the list of members into the table
        sociosList = FXCollections.observableArrayList(socioDAO.listAllSocios());
        tablaSocios.setItems(sociosList);
    }

    private void clearFields() {
        // Clears the text fields
        tfNombre.clear();
        tfDireccion.clear();
        tfTelefono.clear();
    }

    private void showAlert(String title, String message) {
        // Displays an alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
