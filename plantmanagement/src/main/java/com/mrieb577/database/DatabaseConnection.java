package com.mrieb577.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnection {
    private static Logger log = LoggerFactory.getLogger(DatabaseConnection.class);
    private Connection connection;

    public DatabaseConnection(){
        Credentials creds = new Credentials();
        try {
            connection = DriverManager.getConnection(
                creds.getURL(),
                creds.getUsername(),
                creds.getPassword()
            );
        } catch (SQLException e) {
            log.error("Unable to connect to database - {}", e);
            return;
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Statement createStatement() throws SQLException {
        if(connection == null){
            throw new SQLException("Database connection is not established");
        }
        return connection.createStatement();
    }

    public void close(){
        if(connection == null){
            log.warn("Attempted to close a database connection that was not established");
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("Could not close database connection - {}", e);
        }
    }
}
