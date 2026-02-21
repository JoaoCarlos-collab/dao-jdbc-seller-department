package databaseconnection;

import databaseconnection.exceptions.DbException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConfigurationDatabase {
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
                System.out.println("Database successfully connected.");
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
                System.out.print("Database closed successfully.");
            } catch (SQLException e) {
                throw new DbException("It was not possible to close your database.\n" + e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DbException("It was not possible to close the statement.\n" + e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DbException("It was not possible to close the ResultSet.\n" + e.getMessage());
            }
        }
    }
}