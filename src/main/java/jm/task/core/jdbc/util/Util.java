package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Connection conn = null;
    private static final String url = "jdbc:mysql://localhost:3306/data";
    private static final String user = "root";
    private static final String password = "1509507300";

    public static void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection util() {
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
}

