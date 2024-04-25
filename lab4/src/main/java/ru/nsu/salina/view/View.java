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
        setDefaultCloseOperation(close());
        setSize(width, height);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        addSliders(panel);
        add(panel);
    }

    private int close() {
        factory.closeFactory();
        return JFrame.EXIT_ON_CLOSE;
    }

    private void addSliders(JPanel panel) {
        JSlider dealerSlider = createSlider();


        panel.add(dealerSlider);
    }

    private JSlider createSlider() {
        BoundedRangeModel model = new DefaultBoundedRangeModel(80, 0, 0, 80);
        JSlider slider = new JSlider(model);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(10);
        slider.addChangeListener(controller);
        return slider;
    }
}
