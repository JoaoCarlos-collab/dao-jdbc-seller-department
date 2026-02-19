package Db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    private static Properties loadProperties() {
        try (FileInputStream fileInputStream = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new DbException("The database password could not be read. \n"+e.getMessage());
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties properties = loadProperties();
                String dbUrl = properties.getProperty("dbUrl");
                conn = DriverManager.getConnection(dbUrl, properties);
            } catch (SQLException e) {
                throw new DbException("We were unable to connect to the database.\n" + e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException("It was not possible to close your database.\n" + e.getMessage());
            }
        }
    }
}