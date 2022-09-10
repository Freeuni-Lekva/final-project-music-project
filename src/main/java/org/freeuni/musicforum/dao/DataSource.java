package org.freeuni.musicforum.dao;

import java.sql.*;

public enum DataSource {
    INSTANCE;

    private Connection con;

    public synchronized Connection getConnection() {

        if(con != null) return con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e) {
            System.out.println("Provided driver is incorrect");
        }

        final long attempts = 5;
        for (int i = 0; i < attempts; i++) {

            try {
                con = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/freeuni?characterEncoding=utf8",
                        "root", "FreeUni2022!");
                break;
            }
            catch(SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("RETRYING...");
            }

        }

        return con;
    }

    public synchronized void closeConnection() throws SQLException {
        if(con != null) {
            con.close();
        }
    }
}
