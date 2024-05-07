package ru.nsu.salina.model.client.handler;

import ru.nsu.salina.model.client.Client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ServerHandler implements Runnable{
    private final Client client;
    public ServerHandler(Client client) {
        this.client = client;
    }
    public void run() {
        try {
            InputStream inputStream = client.getSocket().getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            client.broadcastMassage(bufferedReader);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
