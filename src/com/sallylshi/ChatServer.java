package com.sallylshi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        for (UserThread u : users) {
            if(u != origUser) {
                u.write(message);
            }
        }
    }

    public void removeUser(UserThread user) {
        users.remove(user);
    }
}
