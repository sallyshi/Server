package com.sallylshi;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    Socket socket;
    PrintWriter printWriter;

    public static void main(String args[]) {
        ChatClient chatClient = new ChatClient();
        chatClient.execute();

    }

    public void execute() {
        try {
            socket = new Socket("localhost", 2000);
            printWriter = new PrintWriter(socket.getOutputStream());
            Thread readThread = new Thread(this::read);
            readThread.start();
            Thread writeThread = new Thread(this::write);
            writeThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void read() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String r;
            while ((r = reader.readLine()) != null) {
                System.out.println(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        Console console = System.console();
        String c;
        while ((c = console.readLine()) != null) {
            printWriter.write(c);
        }
    }

}
