package com.shopsphere.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Opens JDBC connections using local environment configuration. */
public final class DatabaseConnection {
    private DatabaseConnection() { }

    public static Connection open() throws SQLException {
        String url = getSetting("SHOPSPHERE_DB_URL", "jdbc:mysql://localhost:3306/shopsphere?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC");
        String user = getSetting("SHOPSPHERE_DB_USER", "root");
        String password = getSetting("SHOPSPHERE_DB_PASSWORD", "root");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found.", e);
        }

        return DriverManager.getConnection(url, user, password);
    }

    private static String getSetting(String name, String defaultValue) {
        String value = System.getProperty(name);
        if (value != null && !value.isBlank()) {
            return value;
        }
        value = System.getenv(name);
        if (value != null && !value.isBlank()) {
            return value;
        }
        return defaultValue;
    }
}

