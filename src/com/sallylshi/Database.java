package com.sallylshi;

import java.sql.*;

public class Database {

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from chatlog;");
            while (rs.next()) {
                System.out.println("userId = " + rs.getString("user"));
                System.out.println("message = " + rs.getString("message"));
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
