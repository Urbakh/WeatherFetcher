package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {

    public static Connection JDBCConnection() {
        String USERNAME = "postgres";
        String PASSWORD = "root";
        String URL = "jdbc:postgresql://localhost:5432/weatherapi";

        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
