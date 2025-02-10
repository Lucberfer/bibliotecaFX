package org.example.bibliotecafx.dao;

import org.example.bibliotecafx.entities.Libro;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LibroDAO {

    // Adds a new Libro record to the database
    public void addLibro(Libro libro) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(libro); // Saves the book entity
            transaction.commit(); // Commits the transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rolls back in case of an error
            e.printStackTrace();
        }
    }

    // Updates an existing Libro record
    public void updateLibro(Libro libro) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(libro); // Updates the book entity
            transaction.commit(); // Commits the transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rolls back in case of an error
            e.printStackTrace();
        }
    }

    // Deletes an existing Libro record
    public void deleteLibro(Libro libro) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(libro); // Deletes the book entity
            transaction.commit(); // Commits the transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rolls back in case of an error
            e.printStackTrace();
        }
    }

    // Retrieves a Libro entity by its ID
    public Libro getLibroById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Libro.class, id); // Fetches the book by ID
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Searches for books based on title, author's name, or ISBN
    public List<Libro> searchLibros(String titulo, String autorNombre, String isbn) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Libro l WHERE 1=1 "; // Base query
            if (titulo != null && !titulo.trim().isEmpty()) {
                hql += "AND l.titulo LIKE :titulo "; // Filters by title if provided
            }
            if (autorNombre != null && !autorNombre.trim().isEmpty()) {
                hql += "AND l.autor.nombre LIKE :autorNombre "; // Filters by author's name if provided
            }
            if (isbn != null && !isbn.trim().isEmpty()) {
                hql += "AND l.isbn LIKE :isbn "; // Filters by ISBN if provided
            }
            Query<Libro> query = session.createQuery(hql, Libro.class);
            if (titulo != null && !titulo.trim().isEmpty()) {
                query.setParameter("titulo", "%" + titulo.trim() + "%"); // Sets the title parameter for partial match
            }
            if (autorNombre != null && !autorNombre.trim().isEmpty()) {
                query.setParameter("autorNombre", "%" + autorNombre.trim() + "%"); // Sets the author's name parameter for partial match
            }
            if (isbn != null && !isbn.trim().isEmpty()) {
                query.setParameter("isbn", "%" + isbn.trim() + "%"); // Sets the ISBN parameter for partial match
            }
            return query.list(); // Returns the list of matching books
        }
    }

    // Retrieves a list of all books from the database
    public List<Libro> listAllLibros() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Libro"; // HQL query to fetch all books
            Query<Libro> query = session.createQuery(hql, Libro.class);
            return query.list(); // Returns the list of books
        }
    }

    // Retrieves a list of all available (not loaned) books
    public List<Libro> listAvailableLibros() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Libro l WHERE l.prestado = false"; // Fetches only books that are not loaned
            Query<Libro> query = session.createQuery(hql, Libro.class);
            return query.list(); // Returns the list of available books
        }
    }
}
