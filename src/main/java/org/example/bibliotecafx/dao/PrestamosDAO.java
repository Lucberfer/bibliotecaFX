package org.example.bibliotecafx.dao;

import org.example.bibliotecafx.entities.Prestamo;
import org.example.bibliotecafx.entities.Libro;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PrestamosDAO {

    public void addPrestamo(Prestamo prestamo) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Mark book as borrowed
            Libro libro = prestamo.getLibro();
            libro.setPrestado(true);
            session.save(prestamo);
            session.update(libro);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updatePrestamo(Prestamo prestamo) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(prestamo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deletePrestamo(Prestamo prestamo) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(prestamo);

            // Mark book as avaliable
            Libro libro = prestamo.getLibro();
            libro.setPrestado(false);
            session.update(libro);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Prestamo> listLibrosPrestados() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Prestamo p WHERE p.libro.devuelto = true";
            Query<Prestamo> query = session.createQuery(hql, Prestamo.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Prestamo> listHistorialPrestamosPorSocio(Long socioId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM Prestamo p WHERE p.socio.id = :socioId";
            Query<Prestamo> query = session.createQuery(hql, Prestamo.class);
            query.setParameter("socioId", socioId);
            return query.list();
        }
    }

    public List<Prestamo> listAllPrestamos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM Prestamo";
            Query<Prestamo> query = session.createQuery(hql, Prestamo.class);
            return query.list();
        }
    }
}
