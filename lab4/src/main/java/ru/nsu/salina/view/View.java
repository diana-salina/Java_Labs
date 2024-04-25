package ru.nsu.salina.view;

import ru.nsu.salina.controller.Controller;
import ru.nsu.salina.model.CarFactory;

import javax.swing.*;

public class View extends JFrame {
    private CarFactory factory;
    private Controller controller;
    private final int height = 420;
    private final int width = 640;
    public View(CarFactory factory) {
        this.factory = factory;
        controller = new Controller(factory);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        //addKeyListener(controller); TODO controller listen to JSlider

    }
}
