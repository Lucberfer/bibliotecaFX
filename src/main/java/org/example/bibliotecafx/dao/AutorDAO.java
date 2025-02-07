package org.example.bibliotecafx.dao;

import org.example.bibliotecafx.entities.Autor;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class AutorDAO {

    public void addAutor(Autor autor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(autor);
            transaction.commit();
        } catch(Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateAutor(Autor autor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.update(autor);
            transaction.commit();
        } catch(Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteAutor(Autor autor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.delete(autor);
            transaction.commit();
        } catch(Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Autor getAutorById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Autor.class, id);
        }
    }

    public List<Autor> searchAutores(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM Autor a WHERE 1=1 ";
            if (nombre != null && !nombre.trim().isEmpty()) {
                hql += "AND a.nombre LIKE :nombre ";
            }
            Query<Autor> query = session.createQuery(hql, Autor.class);
            if (nombre != null && !nombre.trim().isEmpty()) {
                query.setParameter("nombre", "%" + nombre.trim() + "%");
            }
            return query.list();
        }
    }


    public List<Autor> listAllAutores() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM Autor";
            Query<Autor> query = session.createQuery(hql, Autor.class);
            return query.list();
        }
    }
}
