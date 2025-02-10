package org.example.bibliotecafx.dao;

import org.example.bibliotecafx.entities.Prestamo;
import org.example.bibliotecafx.entities.Libro;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PrestamosDAO {

    // Adds a new Prestamo (loan) record to the database
    public void addPrestamo(Prestamo prestamo) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Marks the book as borrowed
            Libro libro = prestamo.getLibro();
            libro.setPrestado(true); // Sets the "prestado" flag to true
            session.save(prestamo); // Saves the loan record
            session.update(libro); // Updates the book status
            transaction.commit(); // Commits the transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rolls back in case of an error
            e.printStackTrace();
        }
    }

    // Updates an existing Prestamo record
    public void updatePrestamo(Prestamo prestamo) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(prestamo); // Updates the loan entity
            transaction.commit(); // Commits the transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rolls back in case of an error
            e.printStackTrace();
        }
    }

    // Deletes a Prestamo record and marks the book as available
    public void deletePrestamo(Prestamo prestamo) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(prestamo); // Deletes the loan record

            // Marks the book as available again
            Libro libro = prestamo.getLibro();
            libro.setPrestado(false); // Sets the "prestado" flag to false
            session.update(libro); // Updates the book status
            transaction.commit(); // Commits the transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rolls back in case of an error
            e.printStackTrace();
        }
    }

    // Retrieves a list of books that have been returned
    public List<Prestamo> listLibrosPrestados() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Prestamo p WHERE p.libro.devuelto = true"; // Fetches only returned books
            Query<Prestamo> query = session.createQuery(hql, Prestamo.class);
            return query.list(); // Returns the list of returned books
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Retrieves the loan history for a specific user (socio)
    public List<Prestamo> listHistorialPrestamosPorSocio(Long socioId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Prestamo p WHERE p.socio.id = :socioId"; // Fetches loans based on socio ID
            Query<Prestamo> query = session.createQuery(hql, Prestamo.class);
            query.setParameter("socioId", socioId); // Sets the socio ID as a parameter
            return query.list(); // Returns the list of loans for the given socio
        }
    }

    // Retrieves a list of all loans from the database
    public List<Prestamo> listAllPrestamos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Prestamo"; // HQL query to fetch all loan records
            Query<Prestamo> query = session.createQuery(hql, Prestamo.class);
            return query.list(); // Returns the list of all loans
        }
    }
}
