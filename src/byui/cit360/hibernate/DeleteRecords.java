/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byui.cit360.hibernate;
import byui.cit360.Handler;
import static byui.cit360.hibernate.Hibernate.tearDown;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Dale
 */
public class DeleteRecords implements Handler {
    private static SessionFactory factory;
        
    @Override
    public void execute() {
        String dbQuery = "DELETE Employee WHERE empNumber > 1";
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Exception ex) {
            Logger.getLogger(Hibernate.class.getName()).log(Level.SEVERE, null, ex);
        }
        Session session;
        session = factory.openSession();
        session.beginTransaction();
        Query sql = session.createQuery(dbQuery);
        int result = sql.executeUpdate();
        System.out.println(result + " records deleted.");
        session.getTransaction().commit();
        session.close();
        try {
            factory.close();
        } catch (Exception ex) {
            Logger.getLogger(Hibernate.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
