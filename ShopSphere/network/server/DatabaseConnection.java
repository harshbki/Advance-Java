package com.shopsphere.network.server;

import java.sql.Connection;import java.sql.DriverManager;import java.sql.SQLException;

final class DatabaseConnection {
    private DatabaseConnection(){}
    static Connection open()throws SQLException{
        String url = getSetting("SHOPSPHERE_DB_URL", "jdbc:mysql://localhost:3306/shopsphere?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC");
        String user = getSetting("SHOPSPHERE_DB_USER", "root");
        String password = getSetting("SHOPSPHERE_DB_PASSWORD", "root");
        return DriverManager.getConnection(url, user, password);
    }
    private static String getSetting(String key, String defaultValue){
        String v=System.getProperty(key);
        if(v!=null&&!v.isBlank())return v;
        v=System.getenv(key);
        if(v!=null&&!v.isBlank())return v;
        return defaultValue;
    }
}
