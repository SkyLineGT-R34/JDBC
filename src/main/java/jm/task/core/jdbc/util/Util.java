package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Connection conn;
    private static SessionFactory sessionFactory;
    private static final String url = "jdbc:mysql://localhost:3306/data";
    private static final String user = "root";
    private static final String password = "1509507300";

        public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration cfg = new Configuration().configure();
                StandardServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
                Metadata metadata = new MetadataSources(registry)
                        .addAnnotatedClass(User.class)
                        .addAnnotatedClassName("jm.task.core.jdbc.model.User")
                        .getMetadataBuilder()
                        .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE).build();
                sessionFactory =  metadata.getSessionFactoryBuilder().applyBeanManager(metadata).build();
            //cfg.buildSessionFactory(registry);
            } catch (Exception e) {
                System.out.println("Problem creating session factory");
                e.printStackTrace();
                return null;
            }
        }
        return sessionFactory;
    }


    public static Connection getConnection() {
        try {
            Properties info = new Properties();
            info.put("user", user);
            info.put("password", password);
            conn = DriverManager.getConnection(url, info);
            if (conn != null) {
                System.out.println("Connected to the database successfully");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //универсальное закрытие соединения
    public static void close() {
        try {
            if (sessionFactory != null){
                sessionFactory.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

