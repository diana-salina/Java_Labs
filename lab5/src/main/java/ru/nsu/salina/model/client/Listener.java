package ru.nsu.salina.model.client;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ru.nsu.salina.model.message.Message;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import static ru.nsu.salina.model.message.MessageType.*;

public class Listener extends Thread{
    private Client client;
    private Socket socket;
    public Listener(Client client, Socket socket) {
        super("Listener");
        this.client = client;
        this.socket = socket;
    }
    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                ex.printStackTrace();
            }
            int messageLen;
            Document doc;
            while (!socket.isClosed() && !Thread.interrupted()) {
                try {
                    messageLen = in.readInt();
                    if (messageLen == -1) continue;
                    byte[] byteMessage = new byte[messageLen];
                    int isRead = in.read(byteMessage, 0, messageLen);
                    if (isRead == -1) {
                        break;
                    }
                    doc = builder.parse(new ByteArrayInputStream(byteMessage, 0, messageLen));
                    String type = doc.getDocumentElement().getTagName();
                    String message = "", name = "";
                    switch (type) {
                        case "success" -> {
                            message = executeSuccess(doc);
                            client.setSucceed(true);
                        }
                        case "event" -> {
                            try {
                                String eventType = doc.getDocumentElement().getAttribute("name");
                                Node node;
                                switch (eventType) {
                                    case "message" -> {
                                        node = getNode(doc, "from");
                                        if (node != null) {
                                            name = node.getTextContent();
                                        }
                                        node = getNode(doc, "message");
                                        if (node != null) {
                                            message = node.getTextContent();
                                        }
                                        client.addToMassageHistory(new Message(name, message, MASSAGE));
                                    }
                                    case "login" -> {
                                        node = getNode(doc, "name");
                                        if (node != null) {
                                            message = node.getTextContent();
                                        }
                                        client.addToMassageHistory(new Message(message, message, LOGIN));
                                    }
                                    case "logout" -> {
                                        node = getNode(doc, "name");
                                        if (node != null) {
                                            message = node.getTextContent();
                                        }
                                        client.addToMassageHistory(new Message(message, message, LOGOUT));
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    client.onModelChanged();
                    System.out.println("message: " + message);
                } catch (IOException | SAXException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private Node getNode(Document doc, String name) {
        return doc.getElementsByTagName(name).item(0);
    }
    private String executeSuccess(Document doc) {
        return "success";
    }
}
