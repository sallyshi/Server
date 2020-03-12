package com.sallylshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserThread extends Thread {
    Socket socket;
    ChatServer chatServer;
    PrintWriter printWriter;

    public UserThread(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
    }

    @Override
    public void run() {
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String r;
            while ((r = reader.readLine()) != null) {
                chatServer.writeToUserThreads(r);
            }

            chatServer.removeUser(this);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String message) {
        printWriter.write(message);
    }


}
