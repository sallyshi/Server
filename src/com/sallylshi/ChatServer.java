package com.sallylshi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ChatServer {

    HashSet<UserThread> users = new HashSet<>();

    public static void main(String args[]) throws IOException, InterruptedException {
        ChatServer chatServer = new ChatServer();
        chatServer.execute();


    }

    public void execute() {
        System.out.println("Listening for connection on port 2000 ....");
        try {
            ServerSocket server = new ServerSocket(2000);
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

    public void writeToUserThreads(String message) {
        for (UserThread u : users) {
            u.write(message);
        }
    }

    public void removeUser(UserThread user) {
        users.remove(user);
    }
}
