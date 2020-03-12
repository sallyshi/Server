package com.sallylshi;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    Socket socket;
    PrintWriter printWriter;

    public static void main(String args[]) {
       if( args.length < 2 ) {
           System.out.println("Not enough arguments.");
           System.exit(0);
       }
           ChatClient chatClient = new ChatClient();
           chatClient.execute(args[0], Integer.parseInt(args[1]));
    }

    public void execute(String hostName, int port) {
        try {
            socket = new Socket(hostName, port);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
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
            printWriter.println(c);
        }
    }

}
