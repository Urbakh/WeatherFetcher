package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {

    public static Connection JDBCConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("db.url", "db.username", "db.password");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
