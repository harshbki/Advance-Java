package com.shopsphere.swing.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private Database(){ }
    public static Connection open() throws SQLException {
        String url = getSetting("SHOPSPHERE_DB_URL", "jdbc:mysql://localhost:3306/shopsphere?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC");
        String user = getSetting("SHOPSPHERE_DB_USER", "root");
        String password = getSetting("SHOPSPHERE_DB_PASSWORD", "root");
        return DriverManager.getConnection(url, user, password);
    }
    private static String getSetting(String key, String defaultValue) {
        String val = System.getProperty(key);
        if (val != null && !val.isBlank()) return val;
        val = System.getenv(key);
        if (val != null && !val.isBlank()) return val;
        return defaultValue;
    }
}
