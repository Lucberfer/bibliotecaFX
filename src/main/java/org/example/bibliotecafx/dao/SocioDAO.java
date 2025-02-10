package org.example.bibliotecafx.dao;

import org.example.bibliotecafx.entities.Socio;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SocioDAO {

    // Adds a new Socio (member) record to the database
    public void addSocio(Socio socio) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(socio); // Saves the Socio entity
            transaction.commit(); // Commits the transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rolls back in case of an error
            e.printStackTrace();
        }
    }

    // Updates an existing Socio record
    public void updateSocio(Socio socio) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(socio); // Updates the Socio entity
            transaction.commit(); // Commits the transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rolls back in case of an error
            e.printStackTrace();
        }
    }

    // Deletes a Socio record
    public void deleteSocio(Socio socio) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(socio); // Deletes the Socio entity
            transaction.commit(); // Commits the transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rolls back in case of an error
            e.printStackTrace();
        }
    }

    // Retrieves a Socio entity by its ID
    public Socio getSocioById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Socio.class, id); // Fetches the member by ID
        }
    }

    // Searches for members based on name and/or phone number
    public List<Socio> searchSocios(String nombre, String telefono) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Socio s WHERE 1=1 "; // Base query
            if (nombre != null && !nombre.trim().isEmpty()) {
                hql += "AND s.nombre LIKE :nombre "; // Adds name filter if provided
            }
            if (telefono != null && !telefono.trim().isEmpty()) {
                hql += "AND s.telefono LIKE :telefono "; // Adds phone filter if provided
            }
            Query<Socio> query = session.createQuery(hql, Socio.class);
            if (nombre != null && !nombre.trim().isEmpty()) {
                query.setParameter("nombre", "%" + nombre.trim() + "%"); // Sets name parameter for partial match
            }
            if (telefono != null && !telefono.trim().isEmpty()) {
                query.setParameter("telefono", "%" + telefono.trim() + "%"); // Sets phone parameter for partial match
            }
            return query.list(); // Returns the list of matching members
        }
    }

    // Retrieves a list of all members from the database
    public List<Socio> listAllSocios() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Socio"; // HQL query to fetch all members
            Query<Socio> query = session.createQuery(hql, Socio.class);
            return query.list(); // Returns the list of members
        }
    }
}
