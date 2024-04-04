package ru.nsu.salina.view2;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.salina.model.Model;

public class MainFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage mainView) {
        Model model = new Model();
        mainView = new View(model);
        mainView.show();
    }
}
