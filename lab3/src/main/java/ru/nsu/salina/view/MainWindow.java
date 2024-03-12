package ru.nsu.salina.view;

import ru.nsu.salina.model.Model;
import ru.nsu.salina.model.ModelListener;

import javax.swing.*;

public class MainWindow extends JFrame implements ModelListener {
    private final Model model;
    public MainWindow(Model model) {
        setResizable(false);
        this.model = model;
    };

    @Override
    public void onModelChanged() {
        SwingUtilities.invokeLater(() -> {

        });
    }
}
