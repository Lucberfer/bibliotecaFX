package org.example.bibliotecafx.view.controller;

import org.example.bibliotecafx.dao.LibroDAO;
import org.example.bibliotecafx.dao.AutorDAO;
import org.example.bibliotecafx.entities.Libro;
import org.example.bibliotecafx.entities.Autor;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LibrosController {

    @FXML private TableView<Libro> tablaLibros;
    @FXML private TableColumn<Libro, Long> colId;
    @FXML private TableColumn<Libro, String> colTitulo;
    @FXML private TableColumn<Libro, String> colISBN;
    @FXML private TableColumn<Libro, String> colAutor;
    @FXML private TableColumn<Libro, String> colEditorial;
    @FXML private TableColumn<Libro, Integer> colAnio;
    @FXML private TableColumn<Libro, Boolean> colPrestado;

    @FXML private TextField tfBuscarTitulo;
    @FXML private TextField tfBuscarAutor;
    @FXML private TextField tfBuscarISBN;

    @FXML private TextField tfTitulo;
    @FXML private TextField tfISBN;
    @FXML private TextField tfAutorId;
    @FXML private TextField tfEditorial;
    @FXML private TextField tfAnio;

    private LibroDAO libroDAO = new LibroDAO();
    private AutorDAO autorDAO = new AutorDAO();
    private ObservableList<Libro> librosList;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colISBN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));
        colAutor.setCellValueFactory(cellData -> {
            Autor autor = cellData.getValue().getAutor();
            return new SimpleStringProperty(autor != null ? autor.getNombre() : "");
        });
        colEditorial.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditorial()));
        colAnio.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAnioPublicacion()));
        colPrestado.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isPrestado()));

        tablaLibros.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                tfTitulo.setText(newSelection.getTitulo());
                tfISBN.setText(newSelection.getIsbn());
                tfAutorId.setText(newSelection.getAutor() != null ? newSelection.getAutor().getId().toString() : "");
                tfEditorial.setText(newSelection.getEditorial());
                tfAnio.setText(newSelection.getAnioPublicacion() != null ? newSelection.getAnioPublicacion().toString() : "");
            }
        });
        refreshTable();
    }

    @FXML
    public void onAgregar() {
        try {
            String titulo = tfTitulo.getText();
            String isbn = tfISBN.getText();
            Long autorId = Long.parseLong(tfAutorId.getText());
            String editorial = tfEditorial.getText();
            Integer anio = Integer.parseInt(tfAnio.getText());

            Autor autor = autorDAO.getAutorById(autorId);
            if(autor == null){
                showAlert("Error", "Autor no encontrado");
                return;
            }

            Libro libro = new Libro(titulo, isbn, autor, editorial, anio);
            libroDAO.addLibro(libro);
            refreshTable();
            clearFields();
            showAlert("Éxito", "Libro agregado");
        } catch(Exception e) {
            showAlert("Error", "Datos inválidos");
        }
    }

    @FXML
    public void onModificar() {
        Libro libro = tablaLibros.getSelectionModel().getSelectedItem();
        if(libro == null){
            showAlert("Error", "Seleccione un libro para modificar");
            return;
        }
        try {
            libro.setTitulo(tfTitulo.getText());
            libro.setIsbn(tfISBN.getText());
            Long autorId = Long.parseLong(tfAutorId.getText());
            String editorial = tfEditorial.getText();
            Integer anio = Integer.parseInt(tfAnio.getText());

            Autor autor = autorDAO.getAutorById(autorId);
            if(autor == null){
                showAlert("Error", "Autor no encontrado");
                return;
            }
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setAnioPublicacion(anio);

            libroDAO.updateLibro(libro);
            refreshTable();
            clearFields();
            showAlert("Éxito", "Libro modificado");
        } catch(Exception e) {
            showAlert("Error", "Datos inválidos");
        }
    }

    @FXML
    public void onEliminar() {
        Libro libro = tablaLibros.getSelectionModel().getSelectedItem();
        if(libro == null){
            showAlert("Error", "Seleccione un libro para eliminar");
            return;
        }
        libroDAO.deleteLibro(libro);
        refreshTable();
        clearFields();
        showAlert("Éxito", "Libro eliminado");
    }

    @FXML
    public void onBuscar() {
        String titulo = tfBuscarTitulo.getText();
        String autorNombre = tfBuscarAutor.getText();
        String isbn = tfBuscarISBN.getText();
        librosList = FXCollections.observableArrayList(libroDAO.searchLibros(titulo, autorNombre, isbn));
        tablaLibros.setItems(librosList);
    }

    @FXML
    public void onRefresh() {
        refreshTable();
    }

    @FXML
    public void onLimpiar() {
        clearFields();
    }

    public void refreshTable(){
        librosList = FXCollections.observableArrayList(libroDAO.listAllLibros());
        tablaLibros.setItems(librosList);
    }

    public void clearFields(){
        tfTitulo.clear();
        tfISBN.clear();
        tfAutorId.clear();
        tfEditorial.clear();
        tfAnio.clear();
    }

    public void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
