package org.example.bibliotecafx.dao;

import org.example.bibliotecafx.entities.Socio;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SocioDAO {

    public void addSocio(Socio socio) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(socio);
            transaction.commit();
        } catch(Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateSocio(Socio socio) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.update(socio);
            transaction.commit();
        } catch(Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteSocio(Socio socio) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.delete(socio);
            transaction.commit();
        } catch(Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Socio getSocioById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Socio.class, id);
        }
    }

    public List<Socio> searchSocios(String nombre, String telefono) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM Socio s WHERE 1=1 ";
            if(nombre != null && !nombre.isEmpty()){
                hql += "AND s.nombre LIKE :nombre ";
            }
            if(telefono != null && !telefono.isEmpty()){
                hql += "AND s.telefono = :telefono ";
            }
            Query<Socio> query = session.createQuery(hql, Socio.class);
            if(nombre != null && !nombre.isEmpty()){
                query.setParameter("nombre", "%" + nombre + "%");
            }
            if(telefono != null && !telefono.isEmpty()){
                query.setParameter("telefono", telefono);
            }
            return query.list();
        }
    }

    public List<Socio> listAllSocios() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM Socio";
            Query<Socio> query = session.createQuery(hql, Socio.class);
            return query.list();
        }
    }
}