package ru.nsu.salina;

import ru.nsu.salina.model.Model;
import ru.nsu.salina.view.MainWindow;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow(model);
            mainWindow.setVisible(true);
        });
    }
}