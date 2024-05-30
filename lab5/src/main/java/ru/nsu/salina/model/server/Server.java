package ru.nsu.salina.model.server;

import com.google.gson.Gson;
import ru.nsu.salina.model.message.Message;
import ru.nsu.salina.model.message.MessageType;
import ru.nsu.salina.model.server.handler.ClientThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class Server {
    private List<ClientThread> clients;
    private List<String> clientNames;
    private List<Message> messageHistory;
    private final int port;
    private TimeOutChecker timeOutChecker;
    private Logger logger;
    private Gson gson;

    public Server(int port) {
        this.port = port;
        clients = new ArrayList<>();
        clientNames = new ArrayList<>();
        messageHistory = new LinkedList<>();
        gson = new Gson();
        logger = Logger.getLogger(Server.class.getName());
    }
    public void start() {
        boolean isRunning = true;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            logger.info("Server is opened on port №" + port);
            timeOutChecker = new TimeOutChecker(clients);
            timeOutChecker.start();
            while(isRunning) {
                logger.info("server is waiting for clients");
                Socket socket = serverSocket.accept();
                ClientThread client = new ClientThread(this, socket, gson);
                clients.add(client);
                sendHistoryToClient(client);
                String name = client.getNick();
                if (clientNames.contains(name)) {
                    logger.info("Client " + name + " is connected");
                } else {
                    clientNames.add(name);
                    logger.info("NEW CLIENT " + name + " is connected");
                }
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
        Properties properties = new Properties();
        try {
            File file = new File("configfile.properties");
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            properties.load(new FileReader(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            int port = Integer.parseInt(properties.getProperty("port"));
            Server server = new Server(port);
            server.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Server cannot be started");
        }
    }
    public synchronized void broadcast(Message message, ClientThread owner) {
        for (ClientThread client : clients) {
            try {
                if (client != owner) {
                    client.sendMessage(message);
                }
            } catch (IOException ex) {
                logger.warning("ERROR: " + ex.getMessage());
            }
        }
        addToMessageHistory(message);
    }
    public synchronized void removeClient(ClientThread client) {
        String text = "client" + client.getSocket() + " disconnected";
        broadcast(new Message("server", text, MessageType.BASIC_MASSAGE), null);
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
