package org.example.bibliotecafx.dao;

import org.example.bibliotecafx.entities.Libro;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LibroDAO {

    public void addLibro(Libro libro) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(libro);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateLibro(Libro libro) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(libro);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteLibro(Libro libro) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(libro);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public  Libro getLibroById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Libro.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Libro> searchLibros(String titulo, String autorNombre, String isbn) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM Libro l WHERE 1=1 ";
            if(titulo != null && !titulo.isEmpty()){
                hql += "AND l.titulo LIKE :titulo ";
            }
            if(autorNombre != null && !autorNombre.isEmpty()){
                hql += "AND l.autor.nombre LIKE :autorNombre ";
            }
            if(isbn != null && !isbn.isEmpty()){
                hql += "AND l.isbn = :isbn ";
            }
            Query<Libro> query = session.createQuery(hql, Libro.class);
            if(titulo != null && !titulo.isEmpty()){
                query.setParameter("titulo", "%" + titulo + "%");
            }
            if(autorNombre != null && !autorNombre.isEmpty()){
                query.setParameter("autorNombre", "%" + autorNombre + "%");
            }
            if(isbn != null && !isbn.isEmpty()){
                query.setParameter("isbn", isbn);
            }
            return query.list();
        }
    }

    public List<Libro> listAllLibros() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM Libro";
            Query<Libro> query = session.createQuery(hql, Libro.class);
            return query.list();
        }
    }

    public List<Libro> listAvailableLibros() {

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Libro l WHERE l.prestado = false";
            Query<Libro> query = session.createQuery(hql, Libro.class);
            return query.list();
        }
    }
}
