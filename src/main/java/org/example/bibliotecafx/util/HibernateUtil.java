package org.example.bibliotecafx.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    // Creates a single instance of SessionFactory
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Loads the Hibernate configuration file (hibernate.cfg.xml) and builds the SessionFactory
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Error creating SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex); // Throws an exception if the initialization fails
        }
    }

    // Returns the SessionFactory instance
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Closes the SessionFactory, releasing resources
    public static void shutdown() {
        getSessionFactory().close();
    }
}
