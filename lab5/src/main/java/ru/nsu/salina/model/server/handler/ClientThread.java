package ru.nsu.salina.model.server.handler;

import org.w3c.dom.Node;
import ru.nsu.salina.model.message.Message;
import ru.nsu.salina.model.message.MessageType;
import ru.nsu.salina.model.server.ClientInfo;
import ru.nsu.salina.model.server.Server;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientThread extends Thread{
    private final Socket socket;
    private final Server server;
    private final ClientInfo clientInfo;
    private long lastActivity;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private boolean isConnected;
    private final Logger logger;
    public ClientThread(Server server, Socket socket, ClientInfo clientInfo) {
        this.socket = socket;
        this.server = server;
        this.clientInfo = clientInfo;
        isConnected = true;
        lastActivity = 0;
        logger = Logger.getLogger(ClientThread.class.getName());
    }
    @Override
    public void run() {
        DataInputStream in = null;
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            logger.warning("ERROR in creating builder");
        }
        int messageLen = 0;

        while (isConnected) {
            try {
                messageLen = in.readInt();
                byte[] message;
                try {
                    message = new byte[messageLen];
                    int readRes = in.read(message, 0, messageLen);
                    if (readRes == -1) {
                        logger.warning("ERROR reading message");
                        continue;
                    }
                } catch (Exception ex) {
                    logger.warning("ERROR creating byte array with len" + messageLen);
                    continue;
                }

                try {
                    Document doc = builder.parse(new ByteArrayInputStream(message, 0, messageLen));
                    execute(doc);
                } catch (Exception ex) {
                    logger.warning("ERROR creating doc: " + ex.getMessage());
                }
            } catch (EOFException ex) {
                continue;
            } catch (IOException ex) {
                logger.warning("Error while running Client Thread: " + ex.getClass());
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.close();
                break;
            }
        }
    }
    private void execute(Document doc) {
        try {
            String type = doc.getDocumentElement().getAttribute("name");
            switch (type) {
                case "login" -> {
                    login(doc);
                }
                case "message" -> {
                    message(doc);
                }
                case "logout" -> {
                    logout(doc);
                }
            }
        } catch (Exception ex) {
            logger.warning("ERROR executing doc" + ex.getMessage());
        }
    }
    private Node getNode(Document doc, String name) {
        return doc.getElementsByTagName(name).item(0);
    }

    private void logout(Document doc) {
        try {
            clientInfo.send("<success></success>");
            clientInfo.close();
            server.removeClient(clientInfo);
            logger.info("Client " + clientInfo.getName() + " log out");
            server.sendAll("<event name=\"logout\"><name>"+clientInfo.getName()+"</name></event>");
        } catch (Exception ex) {
            logger.warning("ERROR logging out");
        }
    }

    private void message(Document doc) {
        try {
            String text = null;
            Node node = getNode(doc, "message");
            if (node != null) {
                text = node.getTextContent();
            }
            server.sendAll("<event name=\"message\"><from>"+clientInfo.getName()+"</from><message>"+text+"</message></event>");
            clientInfo.send("<success></success>");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void login(Document doc) {
        try {
            String name = null, password = null;
            Node node = getNode(doc, "name");
            if (node != null) {
                name = node.getTextContent();
            }
            node = getNode(doc, "password");
            if (node != null) {
                password = node.getTextContent();
            }
            boolean isChecked = server.checkNewClient(name, password, clientInfo);
            if (isChecked) {
                logger.info("client " + name + " connected");
                clientInfo.send("<success></success>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public long getLastActivity() {
        return lastActivity;
    }

    public void sendSignalToClose() {
        server.removeClient(clientInfo);
    }
    public void close() {
        try {
            isConnected = false;
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
