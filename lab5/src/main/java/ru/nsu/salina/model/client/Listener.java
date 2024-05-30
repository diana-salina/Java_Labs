package ru.nsu.salina.model.client;

import ru.nsu.salina.model.message.Message;
import ru.nsu.salina.model.message.MessageType;

import java.io.IOException;

import static ru.nsu.salina.model.message.MessageType.*;

public class Listener extends Thread{
    private Client client;
    public Listener(Client client) {
        super("Listener");
        this.client = client;
    }
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Message message = client.getMessage();
                if (message == null) break;
                if (message.getType() == BASIC_MASSAGE) {
                    System.out.println(message.getName() + ": " + message.getContent());
                    client.setActivity();
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
}
