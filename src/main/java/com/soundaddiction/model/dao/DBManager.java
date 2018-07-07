package com.soundaddiction.model.dao;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBManager {

    //Constants
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "sound_addiction";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "123456";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    //Fields
    private static Connection connection;

    //Constructors
    private void initConnection() {
        //1. Testing if the class (driver) is loaded in the application
        try {
            Class.forName(JDBC_DRIVER);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Sorry, driver not loaded or does not exist. Aborting!");
            return;
        }
        System.out.println("Driver successfully loaded.");


        //2. Create connection
        try {
            DBManager.connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s", DB_HOST,
                                                                                                      DB_PORT,
                                                                                                      DB_NAME),
                                                                                                      DB_USER,
                                                                                                      DB_PASS);
            this.testConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void testConnection() {
        if(getConnection() != null) {
            System.out.println("Connection successful!");
        }
        else{
            System.out.println("Connection unsuccessful!");
        }
    }

    //Methods

    public Connection getConnection() {
        if(connection == null) {
            initConnection();
        }
        return connection;
    }

}
