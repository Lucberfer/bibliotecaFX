package org.example.bibliotecafx.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autores") // Specifies the table name in the database
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private long id;

    private String nombre;
    private String nacionalidad;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Libro> libros = new HashSet<>();
    // Defines a one-to-many relationship with the Libro entity.
    // - mappedBy = "autor": Indicates that the relationship is managed by the "autor" field in Libro.
    // - cascade = CascadeType.ALL: All operations (persist, merge, remove, refresh, detach) are cascaded.
    // - orphanRemoval = true: If a book is removed from this set, it is also removed from the database.
    // - fetch = FetchType.LAZY: Books are loaded only when accessed, improving performance.

    // Default constructor (required by JPA)
    public Autor() {
    }

    // Constructor with parameters
    public Autor(String nombre, String nacionalidad) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    // Getters & setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return nombre; // Returns the author's name as the string representation
    }
}
