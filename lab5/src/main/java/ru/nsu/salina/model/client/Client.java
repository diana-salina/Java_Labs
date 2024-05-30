package ru.nsu.salina.model.client;

import com.google.gson.Gson;
//import ru.nsu.salina.model.client.view.View;
import ru.nsu.salina.model.message.Message;
import ru.nsu.salina.model.message.MessageType;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class Client {
    private String clientName;
    private String hashPassword;
    private  ObjectOutputStream out;
    private ObjectInputStream in;
    private Gson gson;
    private Socket socket;
    private final String server;
    private final int port;
    private long lastActivity;
    private boolean isExisted;
    //private View view;
    public Client(String server, int port, String clientName) {
        System.out.println("Connecting to the server...");
        this.server = server;
        this.port = port;
        this.clientName = clientName;
        isExisted = true;
    }
//    public void setName() {
//        this.clientName = view.getName();
//    }
    public void start() {
        gson = new Gson();
        try {
            socket = new Socket(server, port);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Listener listener = new Listener(this);
        listener.start();
        Pinger pinger = new Pinger(this);
        pinger.start();
    }
//    private void showView(Client client) {
//        SwingUtilities.invokeLater(() -> {
//            this.view = new View(client);
//            view.setVisible(true);
//        });
//    }
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            File file = new File("configFile.properties");
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            properties.load(new FileReader(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        int port = Integer.parseInt(properties.getProperty("port"));
        String serverAddress = properties.getProperty("ip");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your nickname: ");
        String name = scanner.nextLine();

        Client client = new Client(serverAddress, port, name);
        client.start();
        while (client.isExisted) {
            if (client.getSocket().isClosed()) {
                client.setUnexisted();
                break;
            }
            String massage = scanner.nextLine();
            try {
                client.sendMessage(new Message(client.getName(), massage, MessageType.BASIC_MASSAGE));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        scanner.close();
    }

    public void sendMessage(Message message) throws IOException{
        String json = gson.toJson(message);
        out.writeObject(json);
        out.flush();
    }
    public Message getMessage() throws IOException, ClassNotFoundException{
        if (socket.isClosed() |
                socket.isInputShutdown() |
                socket.isOutputShutdown()) return null;
        String json = (String)in.readObject();
        if (json == null) return null;
        return gson.fromJson(json, Message.class);
    }

    public String getName() {
        return clientName;
    }

    public void setUnexisted() {
        isExisted = false;
    }

    public Socket getSocket() {
        return socket;
    }
    public void stop() {
        try {
            in.close();
            out.close();
            socket.close();
            isExisted = false;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setActivity() {
        lastActivity = System.currentTimeMillis();
    }

    public long getLastActivity() {
        return lastActivity;
    }
}
