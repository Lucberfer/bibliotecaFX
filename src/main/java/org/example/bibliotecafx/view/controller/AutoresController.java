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

    @FXML private TableView<Autor> tablaAutores;
    @FXML private TableColumn<Autor, Long> colId;
    @FXML private TableColumn<Autor, String> colNombre;
    @FXML private TableColumn<Autor, String> colNacionalidad;

    @FXML private TextField tfBuscarNombre;

    @FXML private TextField tfNombre;
    @FXML private TextField tfNacionalidad;

    private AutorDAO autorDAO = new AutorDAO();
    private ObservableList<Autor> autoresList;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colNacionalidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNacionalidad()));

        tablaAutores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                tfNombre.setText(newSelection.getNombre());
                tfNacionalidad.setText(newSelection.getNacionalidad());
            }
        });
        refreshTable();
    }

    @FXML
    public void onBuscar(ActionEvent event) {

        String nombre = tfBuscarNombre.getText();
        autoresList = FXCollections.observableArrayList(autorDAO.searchAutores(nombre));
        tablaAutores.setItems(autoresList);
    }

    @FXML
    public void onRefresh(ActionEvent event) {
    }
    @FXML
    public void onAgregar(ActionEvent event) {
        String nombre = tfNombre.getText();
        String nacionalidad = tfNacionalidad.getText();
        if(nombre.isEmpty()){
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

        Autor autor = tablaAutores.getSelectionModel().getSelectedItem();
        if(autor == null){
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

        Autor autor = tablaAutores.getSelectionModel().getSelectedItem();
        if(autor == null){
            showAlert("Error", "Seleccione un autor para eliminar");
            return;
        }
        autorDAO.deleteAutor(autor);
        refreshTable();
        clearFields();
        showAlert("Éxito", "Autor eliminado");
    }

    @FXML
    public void onRefresh(){
        refreshTable();
    }


    @FXML
    public void onLimpiar(ActionEvent event) {

        clearFields();
    }

    public void refreshTable(){
        autoresList = FXCollections.observableArrayList(autorDAO.listAllAutores());
        tablaAutores.setItems(autoresList);
    }

    public void clearFields(){
        tfNombre.clear();
        tfNacionalidad.clear();
    }

    public void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
