package com.sallylshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClient {

    public static void main(String args[]) throws IOException {

        Socket socket = new Socket("localhost", 2000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());

    }
}
