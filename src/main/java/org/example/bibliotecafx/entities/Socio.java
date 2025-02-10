package org.example.bibliotecafx.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "socios") // Specifies the table name in the database
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private long id;

    private String nombre;
    private String direccion;
    private String telefono;

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Prestamo> prestamos = new HashSet<>();
    // Defines a one-to-many relationship with the Prestamo entity.
    // - mappedBy = "socio": Indicates that the relationship is managed by the "socio" field in Prestamo.
    // - cascade = CascadeType.ALL: All operations (persist, merge, remove, refresh, detach) are cascaded.
    // - orphanRemoval = true: If a loan is removed from this set, it is also removed from the database.
    // - fetch = FetchType.LAZY: Loans are loaded only when accessed, improving performance.

    // Default constructor (required by JPA)
    public Socio() {
    }

    // Constructor with parameters
    public Socio(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Getters & Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Set<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    @Override
    public String toString() {
        return nombre; // Returns the member's name as its string representation
    }
}
