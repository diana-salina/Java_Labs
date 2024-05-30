package ru.nsu.salina.model.server.handler;

import com.google.gson.Gson;
import ru.nsu.salina.model.message.Message;
import ru.nsu.salina.model.message.MessageType;
import ru.nsu.salina.model.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientThread extends Thread{
    private final Socket socket;
    private final Server server;
    private long lastActivity;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean isConnected;
    private final Gson gson;
    private final Logger logger;
    //private String name;
    public ClientThread(Server server, Socket socket, Gson gson) {
        this.socket = socket;
        this.server = server;
        this.gson = gson;
        isConnected = true;
        lastActivity = 0;
        logger = Logger.getLogger(ClientThread.class.getName());
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            logger.warning("ERROR: Client cannot open streams with exception " + ex.getMessage());
        }
    }
    @Override
    public void run() {
        getNick();
        while (isConnected) {
            try {
                String json = (String) in.readObject();
                Message message = gson.fromJson(json, Message.class);
                if (message == null) {
                    logger.info("NULL message error");
                    this.close();
                    break;
                }
                MessageType type = message.getType();
                switch (type) {
                    case BASIC_MASSAGE:
                        lastActivity = System.currentTimeMillis();
                        server.broadcast(message, this);
                        logger.info("Basic massage is broadcast");
                    case PING_MASSAGE:
                        lastActivity = System.currentTimeMillis();
                        sendMessage(message);
                        logger.info("Ping massage is sent");

                }
            } catch (IOException | ClassNotFoundException ex) {
                logger.warning("Error while running Client Thread: " + ex.getClass());
                this.close();
                break;
            }
        }
    }
    public String getNick() {
        Message message = null;
        try {
            while (message == null || message.getContent() == null) {
                String json = (String) in.readObject();
                message = gson.fromJson(json, Message.class);
            }
            return message.getContent();
        } catch (IOException | ClassNotFoundException ex) {
            logger.warning("Get nick error");
        }
        return null;
    }

    public void sendMessage(Message message) throws IOException{
        if (socket.isClosed()) return;
        String json = gson.toJson(message);
        try {
            out.writeObject(json);
            out.flush();
        } catch (IOException ex){
            logger.warning("Error while sending message in Client thread");
            ex.printStackTrace();
        }
    }

    public long getLastActivity() {
        return lastActivity;
    }

    public void sendSignalToClose() {
        server.removeClient(this);
    }
    public void close() {
        try {
            isConnected = false;
            in.close();
            out.close();
            socket.close();
            server.closeChecker();
            logger.info("Client thread closed successfully");
        } catch (IOException ex) {
            logger.warning("Error while closing Client Thread");
            ex.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
