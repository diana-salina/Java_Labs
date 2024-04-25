package ru.nsu.salina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {

        try (
                ServerSocket server = new ServerSocket(8080);
                Socket client = server.accept();
                PrintWriter out =
                        new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(client.getInputStream()));
        ) {
            String str;
            while((str = in.readLine()) != null) {
                System.out.print(str);
            }
        }
    }
}