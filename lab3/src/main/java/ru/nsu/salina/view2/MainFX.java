package ru.nsu.salina.view2;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.salina.model.Model;

public class MainFX extends Application {
    Model model = new Model();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage mainView) {
        mainView = new View(model);
        mainView.show();
    }
    @Override
    public void stop() {
        try {
            model.close();
        } catch (InterruptedException ex) {
            throw new RuntimeException((ex));
        }
    }

}
