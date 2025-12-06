package com.telecom.app;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://<RDS-ENDPOINT>:3306/<DB_NAME>";
    private static final String USER = "<USERNAME>";
    private static final String PASS = "<PASSWORD>";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException("DB connection failed: " + e.getMessage());
        }
    }
}
