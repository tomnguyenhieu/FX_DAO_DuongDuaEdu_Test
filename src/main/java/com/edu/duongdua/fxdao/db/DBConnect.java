package com.edu.duongdua.fxdao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private String dbUrl = "jdbc:mysql://localhost/duongduaeducation";
    private String dbUser = "root";
    private String dbPassword = "";
    private Connection connection;

    public DBConnect() {
        try {
            connection = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword);
//			System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    public DBConnect(String dbUrl, String dbUser, String dbPassword) {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
//			System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
