package ru.nsu.salina.model.server;

import com.google.gson.Gson;
import ru.nsu.salina.model.message.Message;
import ru.nsu.salina.model.message.MessageType;
import ru.nsu.salina.model.server.handler.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class Server {
    private List<ClientThread> clients;
    private List<Message> messageHistory;
    private final int port;
    private TimeOutChecker timeOutChecker;
    private Logger logger;
    private Gson gson;

    public Server(int port) {
        this.port = port;
        clients = new ArrayList<>();
        messageHistory = new LinkedList<>();
        gson = new Gson();
        logger = Logger.getLogger(Server.class.getName());
    }
    public void start() {
        boolean isRunning = true;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            logger.info("Server is opened on port №" + port);
            //System.out.println("Server is opened on port №" + port);
            timeOutChecker = new TimeOutChecker(clients);
            timeOutChecker.start();
            while(isRunning) {
                logger.info("server is waiting for clients");
                //System.out.println("Waiting for clients...");
                Socket socket = serverSocket.accept();
                logger.info("Client <name> is connected");//TODO name
                //System.out.println("Somebody is connected");
                ClientThread client = new ClientThread(this, socket, gson);
                clients.add(client);
                sendHistoryToClient(client);
                client.start();
            }
            serverSocket.close();
            for (ClientThread client : clients) {
                client.close();
            }
        } catch (IOException ex) {
            logger.warning("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            int port = 8080;
            Server server = new Server(port);
            server.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Server cannot be started");
        }
    }
    public synchronized void broadcast(Message message) {
        for (ClientThread client : clients) {
            try {
                client.sendMessage(message);
            } catch (IOException ex) {
                logger.warning("ERROR: " + ex.getMessage());
            }
        }
        addToMessageHistory(message);
    }
    public synchronized void removeClient(ClientThread client) {
        String text = "client" + client.getSocket() + " disconnected";
        broadcast(new Message("server", text, MessageType.BASIC_MASSAGE));
        System.out.println("Client disconnected: " + client.getSocket());
        clients.remove(client);
        client.close();
    }
    private synchronized void addToMessageHistory(Message message) {
        messageHistory.addLast(message);
        if (messageHistory.size() > 15) {
            messageHistory.removeFirst();
        }
    }
    private synchronized void sendHistoryToClient(ClientThread client) {
        for (Message message : messageHistory) {
            try {
                client.sendMessage(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void closeChecker() {
        timeOutChecker.stopWork();
    }
}
