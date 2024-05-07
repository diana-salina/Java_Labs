package ru.nsu.salina.model.client;

import ru.nsu.salina.model.client.handler.ServerHandler;
import ru.nsu.salina.model.client.view.View;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String name;
    private String hashPassword;
    private Socket socket;
    private PrintWriter pw;
    private View view;
    public Client(int ping) throws Exception {
        System.out.println("Connecting to the server...");
        socket = new Socket(getServer(), ping);
    }
    private String getServer() {
        return null; //TODO
    }
//    public String getName() {
//        return name;
//    }
    public void setName() {
        this.name = view.getName();
    }

    public void printInPW(String text) {
        pw.println(text);
    }
    public void start() throws IOException {
        while (name.isEmpty()) {
            setName();
        }
        Scanner scanner = new Scanner(System.in);

        view.allowWriting();
        OutputStream outputStream = socket.getOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
        pw = new PrintWriter(streamWriter, true);
        ServerHandler handler = new ServerHandler(this);
        Thread thread = new Thread(handler);
        thread.start();

        while(true) { //TODO ending
            String text = scanner.nextLine();
            printInPW(text);
        }
    }
    private void showView(Client client) {
        SwingUtilities.invokeLater(() -> {
            this.view = new View(client);
            view.setVisible(true);
        });
    }
    public static void main(String[] args) {
        try {
            Client client = new Client(8088);
            client.showView(client);
            client.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void broadcastMassage(BufferedReader bufferedReader) {
        view.broadcastMassage(bufferedReader);
    }
}
