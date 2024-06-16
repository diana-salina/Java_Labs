package ru.nsu.salina.model.client.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.nsu.salina.model.client.Client;
import ru.nsu.salina.model.client.ModelListener;

import javafx.scene.*;
import ru.nsu.salina.model.message.Message;
import ru.nsu.salina.model.message.MessageType;

import java.util.ArrayList;
import java.util.List;

public class View extends Stage implements ModelListener {
    private Client client;
    private Scene loginScene;
    private Scene chatScene;
    ListView<HBox> history;
    private boolean loginProcess = false;
    private boolean logoutProcess = false;
    public View(Client client) {
        super();
        this.client = client;
        setTitle("Messanger");
        setResizable(false);
        client.setListener(this);

        setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    client.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        createLoginScene();
        setLoginVisible();
        createChat();
    }

    private void createChat() {
        TextField message = new TextField("message");
        message.setStyle("-fx-background-color:Black; -fx-text-fill:white; -fx-font-size:16");
        Button send = new Button("send");
        send.setStyle("-fx-background-color:Black; -fx-text-fill:white; -fx-font-size:16");
        Button logout = new Button("log out");
        logout.setStyle("-fx-background-color:Black; -fx-text-fill:white; -fx-font-size:16");
        history = new ListView<>();

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("send tapped");
                client.setSucceed(false);
                client.message(message.getText());
                message.clear();
            }
        });
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("log out tapped");
                if (client.getSucceed()) {
                    client.setSucceed(false);
                    logoutProcess = true;
                    client.logout();
                }
            }
        });

        VBox chatParent = new VBox(history, message, send, logout);
        chatParent.setAlignment(Pos.CENTER);
        chatScene = new Scene(chatParent, 480, 640);
    }
    private void setChatVisible() {
        if (chatScene != null) {
            setScene(chatScene);
            show();
        }
    }

    private void createLoginScene() {
        TextField name = new TextField("name");
        name.setStyle("-fx-background-color:Black; -fx-text-fill:white; -fx-font-size:16");
        TextField password = new TextField("password");
        password.setStyle("-fx-background-color:Black; -fx-text-fill:white; -fx-font-size:16");

        Button login = new Button("login");
        login.setStyle("-fx-background-color:Black; -fx-text-fill:white; -fx-font-size:16");
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                client.connect();
                client.login(name.getText(), password.getText());
                loginProcess = true;
            }
        });
        VBox loginParent = new VBox(name, password, login);
        loginParent.setAlignment(Pos.CENTER);
        loginScene = new Scene(loginParent, 480, 640);
    }
    private void setLoginVisible() {
        setScene(loginScene);
        show();
    }

    @Override
    public void onModelChanged() {
        Platform.runLater(() -> {
            if (loginProcess && client.getSucceed()) {
                loginProcess = false;
                client.setSucceed(false);
                setChatVisible();
            }
            if (logoutProcess && client.getSucceed()) {
                logoutProcess = false;
                client.setSucceed(false);
                client.disconnect();
                client.getMassageHistory().clear();
                setLoginVisible();
            }
            history.getItems().clear();
            List<Message> messages = client.getMassageHistory();
            for (Message message : messages) {
                history.getItems().add(new HBox(new Label(message.getMessage() + "\n")));
            }
        });
    }
}
