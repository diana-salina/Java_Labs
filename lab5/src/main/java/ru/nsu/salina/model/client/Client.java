package ru.nsu.salina.model.client;

//import ru.nsu.salina.model.client.view.View;
import ru.nsu.salina.model.client.view.View;
import ru.nsu.salina.model.message.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client implements AutoCloseable{
    private String clientName;
    private String password;
    private  DataOutputStream out;
    private ExecutorService executor;
    private List<Message> messages;
    private Socket socket;
    private final String server;
    private final int port;
    private long lastActivity;
    private boolean isExisted;
    private boolean isSucceed = false;
    private View view;
    private ModelListener listener;
    public Client() {
        Properties properties = new Properties();
        try {
            File file = new File("configFile.properties");
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            properties.load(new FileReader(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        int port = Integer.parseInt(properties.getProperty("port"));
        String serverAddress = properties.getProperty("ip");

        this.server = serverAddress;
        this.port = port;
        this.messages = new ArrayList<>();
        isExisted = true;
    }
    public void connect() {
        try {
            socket = new Socket(server, port);
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Listener listener = new Listener(this, socket);
        executor = Executors.newCachedThreadPool();
        executor.execute(listener);
    }
    public void disconnect() {
        try {
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        executor.shutdownNow();
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void login(String name, String password) {
        String message = "<command name=\"login\"><name>" + name + "</name><password>"
                + password + "</password></command>";
        try {
            int len = message.getBytes().length;
            out.writeInt(len);
            out.write(message.getBytes(), 0, len);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void logout() {
        String message = "<command name=\"logout\"></command>";
        try {
            int len = message.getBytes().length;
            out.writeInt(len);
            out.write(message.getBytes(), 0, len);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void message(String mes) {
        String message = "<command name=\"message\"><message>" + mes + "</message></command>";
        try {
            int len = message.getBytes().length;
            out.writeInt(len);
            out.write(message.getBytes(), 0, len);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void setName(String name) {
        this.clientName = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return clientName;
    }

    public void setUnexisted() {
        isExisted = false;
    }

    public Socket getSocket() {
        return socket;
    }
    @Override
    public void close() throws InterruptedException{
        disconnect();
    }

    public void setActivity() {
        lastActivity = System.currentTimeMillis();
    }

    public long getLastActivity() {
        return lastActivity;
    }
    public void addToMassageHistory(Message message) {
        synchronized (messages) {
            messages.add(message);
        }
    }
    public List<Message> getMassageHistory() {
        synchronized (messages) {
            return new ArrayList<>(messages);
        }
    }
    public void setSucceed(boolean flag) {
        isSucceed = flag;
    }
    public boolean getSucceed() {
        return isSucceed;
    }

    public void setListener(ModelListener listener) {
        this.listener = listener;
    }
    public void onModelChanged() {
        listener.onModelChanged();
    }
}
