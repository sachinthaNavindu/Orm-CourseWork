package edu.ijse.therapycenter.config;

import edu.ijse.therapycenter.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/TherapyCenter?createDatabaseIfNotExist=true")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.connection.password", "Ijse@1234")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting("hibernate.show_sql", "true")
                .build();

        sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Therapist.class)
                .addAnnotatedClass(Patient.class)
                .addAnnotatedClass(TherapyProgram.class)
                .addAnnotatedClass(TherapySession.class)
                .addAnnotatedClass(Payment.class)
                .buildMetadata()
                .buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        if (factoryConfiguration == null) {
            factoryConfiguration = new FactoryConfiguration();
        }
        return factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}