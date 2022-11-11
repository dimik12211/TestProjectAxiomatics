package Application.utils;

import Application.models.Client;
import Application.models.CreditAgreement;
import Application.models.Request;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    /*Создает SessionFactory и возвращает Session
     *Created SessionFactory and return Session*/

    @Bean
    public static Session getSessionFactory() {
        try {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration().addAnnotatedClass(Client.class).addAnnotatedClass(Request.class)
                        .addAnnotatedClass(CreditAgreement.class)
                        .setProperty(Environment.DRIVER, "org.postgresql.Driver")
                        .setProperty(Environment.URL, "jdbc:postgresql://localhost:5432/TestProgectMaven")
                        .setProperty(Environment.USER, "postgres")
                        .setProperty(Environment.PASS, "123321")
                        .setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect")
                        .setProperty(Environment.SHOW_SQL, "false")
                        .setProperty(Environment.HBM2DDL_AUTO, "update");
                sessionFactory = configuration.buildSessionFactory();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory.openSession();
    }
}
