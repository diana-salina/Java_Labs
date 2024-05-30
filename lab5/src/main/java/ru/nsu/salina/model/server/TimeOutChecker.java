package ru.nsu.salina.model.server;

import ru.nsu.salina.model.server.handler.ClientThread;

import java.util.Iterator;
import java.util.List;

public class TimeOutChecker extends Thread{
    private boolean isWorking;
    private List<ClientThread> clients;
    private final int timeToDisconnect = 30 * 1000;
    public TimeOutChecker(List<ClientThread> clients) {
        isWorking = true;
        this.clients = clients;
    }
    public void stopWork() { isWorking = false; }
    @Override
    public void run() {
        while (isWorking) {
            for (Iterator<ClientThread> iterator = clients.iterator(); iterator.hasNext();) {
                ClientThread client = iterator.next();
                if (System.currentTimeMillis() - client.getLastActivity() >= timeToDisconnect) {
                    iterator.remove();
                    client.sendSignalToClose();
                }
            }
            try {
                sleep(timeToDisconnect / 5);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
