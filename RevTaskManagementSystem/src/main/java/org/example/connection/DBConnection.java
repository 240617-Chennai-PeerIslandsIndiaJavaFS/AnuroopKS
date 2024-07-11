package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/revtaskmanagementsystem";
        String user = "root";
        String password = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,user,password);
            return conn;
        }catch (ClassNotFoundException ex){
            System.out.println("Error loading MySQL JDBC driver: " + ex.getMessage());
        } catch (SQLException ex){
            System.out.println("Error connecting to the database: " + ex.getMessage());
        }
        return null;
    }
}