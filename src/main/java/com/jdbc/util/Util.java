package com.jdbc.util;

import com.jdbc.model.User;
import org.hibernate.SessionFactory;
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

    //getting session for Hibernate implementation
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/data?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "1509507300");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");
                settings.put(Environment.HBM2DDL_DEFAULT_CONSTRAINT_MODE, "false");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("***Connected to the database successfully***");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return sessionFactory;
    }

    //getting connection for JDBC implementation
    public static Connection getConnection() {
        try {
            Properties info = new Properties();
            info.put("user", user);
            info.put("password", password);
            conn = DriverManager.getConnection(url, info);
            if (conn != null) {
                System.out.println("***Connected to the database successfully***");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //универсальное закрытие
    public static void close() {
        try {
            if (sessionFactory != null) {
                sessionFactory.close();
                System.out.println("***Session closed***");
            }
            if (conn != null) {
                conn.close();
                System.out.println("***Connection closed***");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

