package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static Connection util() {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/data";
            Properties info = new Properties();
            info.put("user", "root");
            info.put("password", "1509507300");

            conn = DriverManager.getConnection(url, info);
            if (conn != null) {
                System.out.println("Connected to the database successfully");
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        return conn;
    }
}

