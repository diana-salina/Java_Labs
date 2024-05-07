package ru.nsu.salina.model.server.handler;

import java.net.InetAddress;
import java.net.Socket;

public class ClientHandler  implements Runnable{
    private final Socket socket;
    private final String host;
    public ClientHandler(Socket socket) {
        this.socket = socket;
        InetAddress address = socket.getInetAddress();
        host = address.getHostAddress();
    }
    public void run() {
        //TODO

    }
}
