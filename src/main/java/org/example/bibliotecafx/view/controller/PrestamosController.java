package org.example.bibliotecafx.view.controller;

import org.example.bibliotecafx.dao.PrestamosDAO;
import org.example.bibliotecafx.dao.LibroDAO;
import org.example.bibliotecafx.dao.SocioDAO;
import org.example.bibliotecafx.entities.Prestamo;
import org.example.bibliotecafx.entities.Libro;
import org.example.bibliotecafx.entities.Socio;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class PrestamosController {

    // TableView and TableColumns for displaying loan records
    @FXML
    private TableView<Prestamo> tablaPrestamos;
    @FXML
    private TableColumn<Prestamo, Long> colId;
    @FXML
    private TableColumn<Prestamo, String> colLibro;
    @FXML
    private TableColumn<Prestamo, String> colSocio;
    @FXML
    private TableColumn<Prestamo, LocalDate> colFechaPrestamo;
    @FXML
    private TableColumn<Prestamo, LocalDate> colFechaDevolucion;

    // TextFields for searching and registering loans
    @FXML
    private TextField tfSocioId; // For searching loan history
    @FXML
    private TextField tfLibroId;
    @FXML
    private TextField tfSocioIdPrestamo;
    @FXML
    private DatePicker dpPrestamo;
    @FXML
    private DatePicker dpDevolucion;

    private PrestamosDAO prestamoDAO = new PrestamosDAO(); // DAO for loan operations
    private ObservableList<Prestamo> prestamosList; // Observable list for TableView

    @FXML
    public void initialize() {
        // Setting up table column bindings
        colId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colLibro.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getLibro() != null ? cellData.getValue().getLibro().getTitulo() : ""));
        colSocio.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getSocio() != null ? cellData.getValue().getSocio().getNombre() : ""));
        colFechaPrestamo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaPrestamo()));
        colFechaDevolucion.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaDevolucion()));

        refreshPrestamosTable(); // Load loans into the table
    }

    @FXML
    public void onRegistrarPrestamo() {
        // Registers a new loan
        try {
            Long libroId = Long.parseLong(tfLibroId.getText());
            Long socioId = Long.parseLong(tfSocioIdPrestamo.getText());
            LocalDate fechaPrestamo = dpPrestamo.getValue();
            LocalDate fechaDevolucion = dpDevolucion.getValue();

            if (fechaPrestamo == null || fechaDevolucion == null) {
                showAlert("Error", "Las fechas son requeridas");
                return;
            }

            // Retrieve book and member using their DAOs
            LibroDAO libroDAO = new LibroDAO();
            SocioDAO socioDAO = new SocioDAO();
            Libro libro = libroDAO.getLibroById(libroId);
            Socio socio = socioDAO.getSocioById(socioId);

            if (libro == null || socio == null) {
                showAlert("Error", "Libro o Socio no encontrado");
                return;
            }
            if (libro.isPrestado()) {
                showAlert("Error", "El libro ya está prestado");
                return;
            }

            // Creates and saves the loan record
            Prestamo prestamo = new Prestamo(libro, socio, fechaPrestamo, fechaDevolucion);
            prestamoDAO.addPrestamo(prestamo);
            refreshPrestamosTable();
            clearFields();
            showAlert("Éxito", "Préstamo registrado");
        } catch (Exception e) {
            showAlert("Error", "Datos inválidos");
        }
    }

    @FXML
    public void onEliminarPrestamo() {
        // Deletes the selected loan
        Prestamo prestamo = tablaPrestamos.getSelectionModel().getSelectedItem();
        if (prestamo == null) {
            showAlert("Error", "Seleccione un préstamo para eliminar");
            return;
        }
        prestamoDAO.deletePrestamo(prestamo);
        refreshPrestamosTable();
        showAlert("Éxito", "Préstamo eliminado");
    }

    @FXML
    public void onBuscarHistorial() {
        // Searches for a member's loan history
        try {
            Long socioId = Long.parseLong(tfSocioId.getText());
            prestamosList = FXCollections.observableArrayList(prestamoDAO.listHistorialPrestamosPorSocio(socioId));
            tablaPrestamos.setItems(prestamosList);
        } catch (Exception e) {
            showAlert("Error", "ID de socio inválido");
        }
    }

    @FXML
    public void onRefreshPrestamos() {
        refreshPrestamosTable(); // Refreshes the table
    }

    @FXML
    public void onLimpiar() {
        clearFields(); // Clears input fields
    }

    public void refreshPrestamosTable() {
        // Reloads the list of loans into the table
        prestamosList = FXCollections.observableArrayList(prestamoDAO.listAllPrestamos());
        tablaPrestamos.setItems(prestamosList);
    }

    public void showAlert(String title, String message) {
        // Displays an alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void clearFields() {
        // Clears input fields
        tfLibroId.clear();
        tfSocioIdPrestamo.clear();
        dpPrestamo.setValue(null);
        dpDevolucion.setValue(null);
    }
}
