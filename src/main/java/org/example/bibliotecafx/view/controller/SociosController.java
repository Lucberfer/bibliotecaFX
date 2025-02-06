package org.example.bibliotecafx.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example.bibliotecafx.dao.SocioDAO;
import org.example.bibliotecafx.entities.Socio;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.persistence.Table;

public class SociosController {

    @FXML private TableView<Socio> tablaSocios;
    @FXML private TableColumn<Socio, Long> colId;
    @FXML private TableColumn<Socio, String> colNombre;
    @FXML private TableColumn<Socio, String> colDireccion;
    @FXML private TableColumn<Socio, String> colTelefono;

    @FXML private TextField tfBuscarNombre;
    @FXML private TextField tfBuscarTelefono;

    @FXML private TextField tfNombre;
    @FXML private TextField tfDireccion;
    @FXML private TextField tfTelefono;

    private SocioDAO socioDAO = new SocioDAO();
    private ObservableList<Socio> sociosList;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));

        tablaSocios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                tfNombre.setText(newSelection.getNombre());
                tfDireccion.setText(newSelection.getDireccion());
                tfTelefono.setText(newSelection.getTelefono());
            }
        });
        refreshTable();
    }



    @FXML
    public void onBuscar(ActionEvent event) {
        String nombre = tfBuscarNombre.getText();
        String telefono = tfBuscarTelefono.getText();
        sociosList = FXCollections.observableArrayList(socioDAO.searchSocios(nombre, telefono));
        tablaSocios.setItems(sociosList);
    }

    @FXML
    public void onRefresh(ActionEvent event) {
    }

    @FXML
    public void onModificar(ActionEvent event) {
        Socio socio = tablaSocios.getSelectionModel().getSelectedItem();
        if(socio == null){
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
    public void onAgregar(ActionEvent event) {
        String nombre = tfNombre.getText();
        String direccion = tfDireccion.getText();
        String telefono = tfTelefono.getText();
        if(nombre.isEmpty()){
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
    public void onEliminar(ActionEvent event) {
        Socio socio = tablaSocios.getSelectionModel().getSelectedItem();
        if(socio == null){
            showAlert("Error", "Seleccione un socio para eliminar");
            return;
        }
        socioDAO.deleteSocio(socio);
        refreshTable();
        clearFields();
        showAlert("Éxito", "Socio eliminado");
    }

    @FXML
    public void onLimpiar(ActionEvent event) {
    }

    @FXML
    public void onRefresh(){
        refreshTable();
    }

    @FXML
    public void onLimpiar(){
        clearFields();
    }

    public void refreshTable(){
        sociosList = FXCollections.observableArrayList(socioDAO.listAllSocios());
        tablaSocios.setItems(sociosList);
    }

    public void clearFields(){
        tfNombre.clear();
        tfDireccion.clear();
        tfTelefono.clear();
    }

    public void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
