package ru.nsu.salina;

import ru.nsu.salina.model.CarFactory;
import ru.nsu.salina.view.View;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        CarFactory factory = new CarFactory("configFile.properties");
        SwingUtilities.invokeLater(() -> {
            JFrame view = new View(factory);
            //view.setVisible(true);
        });
    }
}