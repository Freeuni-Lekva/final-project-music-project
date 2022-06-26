package org.freeuni.musicforum.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public enum DataSource {
    INSTANCE;

    private Connection con;

    public synchronized Connection getConnection() {

        if(con != null) return con;
        try {
            Class.forName("com.mysql.cj.jdbc.MysqlDataSource");
        }
        catch(ClassNotFoundException e) {
            System.out.println("Provided driver is incorrect");
        }

        final long interval = 3000;
        final long attempts = 5;
        Timer timer = new Timer();
        for (int i = 0; i < attempts; i++) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        con = DriverManager.getConnection(
                                "jdbc:mysql://127.0.0.1:3306/freeuni",
                                "root", "root");
                        timer.cancel();
                    }
                    catch(SQLException e) {
                        System.out.println(e.getMessage());
                        System.out.println("RETRYING...");
                    }
                }
            }, interval);
        }

        return con;
    }

    public synchronized void closeConnection() throws SQLException {
        if(con != null) {
            con.close();
        }
    }
}
