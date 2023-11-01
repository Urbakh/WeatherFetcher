package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {

    public static Connection JDBCConnection() {
        try {
            Class.forName(PropertiesUtil.get("db.driver"));
            return DriverManager.getConnection(PropertiesUtil.get("db.url"),
                    PropertiesUtil.get("db.username"),
                    PropertiesUtil.get("db.password"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
