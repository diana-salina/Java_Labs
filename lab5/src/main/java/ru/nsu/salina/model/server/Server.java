package ru.nsu.salina.model.server;

import ru.nsu.salina.model.client.Client;
import ru.nsu.salina.model.server.handler.ClientHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket server;
    private List<PrintWriter> out;
    public Server(int port) throws Exception {
        server = new ServerSocket(port);
        out = new ArrayList<>();
    }
    private void addOut(PrintWriter out) {
        this.out.add(out);
    }
    private void removeOut(PrintWriter out) {
        this.out.remove(out);
    }
    private void sendMessage(String message) {
        for (var out : this.out) {
            out.println(message);
        }
    }
    public void start() {
        try {
            while(true) {
                System.out.println("Waiting for clients...");
                Socket socket = server.accept();
                System.out.println("Somebody is connected...");
                ClientHandler handler = new ClientHandler(socket);
                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            Server server = new Server(8088);
            server.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Server cannot be started");
        }
    }
}
