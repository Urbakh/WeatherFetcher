package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public final class Util {

    private static final String URL_KEY = "db.url";

    private static final String USERNAME_KEY = "db.username";
    
    private static final String PASSWORD_KEY = "db.password";

    private static final String DRIVER_KEY = "db.driver";

    public static Connection JDBCConnection() {
        try {
            Class.forName(PropertiesUtil.get(DRIVER_KEY));
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
