package com.example.lab_9.Daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDao {

    public Connection getConnection() throws SQLException {
        String user = "root";
        String pass = "root";
        String url = "jdbc:mysql://127.0.0.1:3306/hr";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(url, user, pass);
    }

}
