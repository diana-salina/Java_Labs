package ru.nsu.salina.model.client;

import ru.nsu.salina.model.message.Message;
import ru.nsu.salina.model.message.MessageType;

import java.io.IOException;

public class Pinger extends Thread{
    private Client client;
    private final long timeToDisconnect = 30 * 1000;
    public Pinger(Client client) {
        super("Pinger");
        this.client = client;
    }
    @Override
    public void run() {
        Message name = new Message(client.getName(), client.getName(), MessageType.PING_MASSAGE);
        try {
            client.sendMessage(name);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        while (!Thread.interrupted()) {
            try {
                client.sendMessage(new Message(client.getName(), "ping", MessageType.PING_MASSAGE));
                sleep(timeToDisconnect / 5);
                if (System.currentTimeMillis() - client.getLastActivity() >= timeToDisconnect) {
                    System.out.println(("Server not available"));
                    client.stop();
                    break;
                }
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
