/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byui.cit360.hibernate;

import byui.cit360.collections.model.Employee;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dale
 */
public class Hibernate {
    private static SessionFactory factory;
    
    protected static void setUp() throws Exception {
        factory = new Configuration().configure().buildSessionFactory();
    }
    
    protected static void tearDown() throws Exception {
        factory.close();
    }
    
    public static void saveRecords(Set<Employee> es) {
        try {
            setUp();
        } catch (Exception ex) {
            Logger.getLogger(Hibernate.class.getName()).log(Level.SEVERE, null, ex);
        }
        Session session = factory.openSession();
        session.beginTransaction();
        es.stream().forEach((e) -> {
            session.save(e);
        });
        session.getTransaction().commit();
        session.close();
        System.out.println("Records stored successfully.");
        try {
            tearDown();
        } catch (Exception ex) {
            Logger.getLogger(Hibernate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void getRecords() {
        try {
            setUp();
        } catch (Exception ex) {
            Logger.getLogger(Hibernate.class.getName()).log(Level.SEVERE, null, ex);
        }
        Session session = factory.openSession();
        session.beginTransaction();
        List<Employee> el = session.createQuery("FROM Employee E ORDER BY E.firstName").list();
        System.out.println("DB Query Results:");
        for (Employee e : el) {
            System.out.println(e.getEmpNumber() + ", " + e.getFirstName() + " " + e.getLastName());
        }
        try {
            tearDown();
        } catch (Exception ex) {
            Logger.getLogger(Hibernate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
