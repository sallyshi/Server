package com.sallylshi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.HashSet;

public class ChatServer {

    HashSet<UserThread> users = new HashSet<>();

    public static void main(String args[]) {
        if( args.length < 1 ) {
            System.out.println("Not enough arguments.");
            System.exit(0);
        }
        ChatServer chatServer = new ChatServer();
        chatServer.execute(Integer.parseInt(args[0]));
    }

    public void execute(int port) {
        System.out.println("Listening for connection on port " + port);
        try {
            ServerSocket server = new ServerSocket(port);
            openDatabase();
            while (true) {
                Socket socket = server.accept();
                UserThread userThread = new UserThread(socket, this);
                userThread.start();
                users.add(userThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToUserThreads(String message, UserThread origUser) {
        writeToDatabase(origUser.getUserId(), message);
        for (UserThread u : users) {
            if(u != origUser) {
                u.write(message);
            }
        }
    }

    public void removeUser(UserThread user) {
        users.remove(user);
    }

    public void writeToDatabase(String userId, String message) {
        try {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        PreparedStatement prep = conn.prepareStatement(
                    "insert into chatlog values (?, ?);");
            prep.setString(1, userId);
            prep.setString(2, message);
            prep.executeUpdate();
            System.out.println("executed update chatlog");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from chatlog;");
            while (rs.next()) {
                System.out.println("userId = " + rs.getString("user"));
                System.out.println("message = " + rs.getString("message"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void openDatabase() {
        try {
            //Class.forName("org.sqlite.JDBC.Driver");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stat = conn.createStatement();
            stat.executeUpdate("drop table if exists chatlog;");
            stat.executeUpdate("create table chatlog (user, message);");
            System.out.println("Created chatlog");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
