package ru.nsu.salina.model.server;

import javafx.util.Pair;
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
    private List<ClientThread> clientThreads;
    private List<Pair<String, String>> clientsBase;
    private final List<ClientInfo> clients;
    private List<String> messageHistory;
    private final int port;
    private TimeOutChecker timeOutChecker;
    private Logger logger;
    public Server(int port) {
        this.port = port;
        clientThreads = new ArrayList<>();
        clients = new ArrayList<>();
        clientsBase = new ArrayList<>();
        messageHistory = new ArrayList<>();
        logger = Logger.getLogger(Server.class.getName());
    }
    public void start() {
        boolean isRunning = true;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server is opened on port №" + port);
            timeOutChecker = new TimeOutChecker(clientThreads);
            timeOutChecker.start();
            while(isRunning) {
                logger.info("server is waiting for clients");
                Socket socket = serverSocket.accept();
                ClientInfo clientInfo = new ClientInfo("", socket);
                synchronized (clients) {
                    clients.add(clientInfo);
                }
                ClientThread client = new ClientThread(this, socket, clientInfo);
                synchronized (clientThreads) {
                    clientThreads.add(client);
                }
                client.start();
            }
        } catch (IOException ex) {
            logger.warning("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    logger.warning("ERROR while closing server socket");
                }
            }
            for (ClientThread client : clientThreads) {
                client.close();
            }
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

    public void closeChecker() {
        timeOutChecker.stopWork();
    }

    public boolean checkNewClient(String name, String password, ClientInfo clientInfo) {
        synchronized (clients) {
            for (Pair<String, String> client : clientsBase) {
                if (!client.getKey().equals(name)) {
                    continue;
                }
                if (client.getValue().equals(password)) {
                    clientInfo.setName(name);
                    return true;
                } else {
                    return false;
                }
            }
            clientInfo.setName(name);
            clientsBase.add(new Pair<>(clientInfo.getName(), password));
            return true;
        }
    }

    public void removeClient(ClientThread clientThread) {
        synchronized (clientThread) {
            clientThreads.remove(clientThread);
        }
    }

    public void sendAll(String message) {
        synchronized (clientThreads) {
            for (ClientInfo client : clients) {
                client.send(message);
            }
        }
    }
}
