package org.example.bibliotecafx.entities;

import javax.persistence.*;

@Entity
@Table(name = "libros") // Specifies the table name in the database
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private long id;

    private String titulo;
    private String isbn;

    @ManyToOne(fetch = FetchType.EAGER) // Many books can be associated with one author
    @JoinColumn(name = "autor_id") // Foreign key column referencing the Autor entity
    private Autor autor;

    private String editorial;
    private Integer anioPublicacion;

    // Indicates if the book is currently borrowed
    private boolean prestado;

    // Default constructor (required by JPA)
    public Libro() {
    }

    // Constructor with parameters
    public Libro(String titulo, String isbn, Autor autor, String editorial, Integer anioPublicacion) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.prestado = false; // Default value: the book is available upon creation
    }

    // Getters & Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }

    @Override
    public String toString() {
        return titulo; // Returns the book's title as its string representation
    }
}
