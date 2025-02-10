package org.example.bibliotecafx.dao;

import org.example.bibliotecafx.entities.Autor;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AutorDAO {

    // Adds a new Autor record to the database
    public void addAutor(Autor autor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(autor); // Saves the author entity
            transaction.commit(); // Commits the transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rolls back in case of an error
            e.printStackTrace();
        }
    }

    // Updates an existing Autor record
    public void updateAutor(Autor autor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(autor); // Updates the author entity
            transaction.commit(); // Commits the transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rolls back in case of an error
            e.printStackTrace();
        }
    }

    // Deletes an existing Autor record
    public void deleteAutor(Autor autor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(autor); // Deletes the author entity
            transaction.commit(); // Commits the transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rolls back in case of an error
            e.printStackTrace();
        }
    }

    // Retrieves an Autor entity by its ID
    public Autor getAutorById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Autor.class, id); // Fetches the author by ID
        }
    }

    // Searches for authors based on the given name (partial match)
    public List<Autor> searchAutores(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Autor a WHERE 1=1 "; // Base query
            if (nombre != null && !nombre.trim().isEmpty()) {
                hql += "AND a.nombre LIKE :nombre "; // Adds a filter if name is provided
            }
            Query<Autor> query = session.createQuery(hql, Autor.class);
            if (nombre != null && !nombre.trim().isEmpty()) {
                query.setParameter("nombre", "%" + nombre.trim() + "%"); // Sets the parameter with wildcards for partial match
            }
            return query.list(); // Returns the list of matching authors
        }
    }

    // Retrieves a list of all authors from the database
    public List<Autor> listAllAutores() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Autor"; // HQL query to fetch all authors
            Query<Autor> query = session.createQuery(hql, Autor.class);
            return query.list(); // Returns the list of authors
        }
    }
}
