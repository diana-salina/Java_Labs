package ru.nsu.salina.model.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientInfo {
    private String name;
    private final Socket socket;
    private final DataOutputStream out;
    public ClientInfo(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public String getName() {
        return name;
    }
    public void send(String message) {
        int len = message.getBytes().length;
        try {
            if (out != null) {
                out.writeInt(len);
                out.write(message.getBytes(), 0, len);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setName(String name) {
        this.name = name;
    }
}
