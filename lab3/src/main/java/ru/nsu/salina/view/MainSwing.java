package ru.nsu.salina.view;

import ru.nsu.salina.model.Model;

import javax.swing.*;

public class MainSwing {
    public static void main(String[] args) {
        Model model = new Model();
        SwingUtilities.invokeLater(() -> {
            JFrame mainWindow = new MainWindow(model);
            mainWindow.setVisible(true);
        });
    }
}