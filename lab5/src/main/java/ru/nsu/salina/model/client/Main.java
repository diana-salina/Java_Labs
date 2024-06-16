package ru.nsu.salina.model.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import ru.nsu.salina.model.client.view.View;

public class Main extends Application {
    Client client = new Client();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage mainView) {
        mainView = new View(client);
        mainView.show();
    }
    @Override
    public void stop() {
        try {
            client.close();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
