package org.example.bibliotecafx.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "libro_id") // Foreign key column referencing the Libro entity
    private Libro libro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "socio_id") // Foreign key column referencing the Socio entity
    private Socio socio;

    private LocalDate fechaPrestamo; // Date when the book was borrowed
    private LocalDate fechaDevolucion; // Expected return date

    // Default constructor (required by JPA)
    public Prestamo() {
    }

    // Constructor with parameters
    public Prestamo(Libro libro, Socio socio, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.libro = libro;
        this.socio = socio;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Getters & Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "libro=" + (libro != null ? libro.getTitulo() : "N/A") +
                ", socio=" + (socio != null ? socio.getNombre() : "N/A") +
                "}";
    }
}
