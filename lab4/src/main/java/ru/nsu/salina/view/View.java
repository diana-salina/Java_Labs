package ru.nsu.salina.view;

import ru.nsu.salina.controller.Controller;
import ru.nsu.salina.model.CarFactory;
import ru.nsu.salina.model.ModelListener;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame implements ModelListener {
    private CarFactory factory;
    private Controller controller;
    private final int height = 420;
    private final int width = 940;
    public View(CarFactory factory) {
        this.factory = factory;
        controller = new Controller(factory);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(controller);
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        factory.setListener(this);
        JPanel panel = new JPanel();
        panel.setSize(width / 2, height / 6 * 5);
        addSlider(panel, "Dealers Delay", 0);
        addSlider(panel, "Workers Delay", 1);
        addSlider(panel, "Accessory Suppliers", 2);
        addSlider(panel, "Body Suppliers", 3);
        addSlider(panel, "Engine Suppliers", 4);
        add(panel);
        JPanel factoryPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponents(g);
                try {
                    setFactory(g);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
            }
        };
        add(factoryPanel);
    }

    @Override
    public void onModelChanged() {
        SwingUtilities.invokeLater(this::repaint);
    }

    private void setFactory(Graphics g) {
        drawStorage(g, "Accessories", factory.getAccessoryStorageAmount(), 0);
        drawStorage(g, "Engines", factory.getEngineStorageAmount(), 1);
        drawStorage(g, "Bodies", factory.getBodyStorageAmount(), 2);
        drawStorage(g, "Cars", factory.getBodyStorageAmount(), 3);

    }

    private void drawStorage(Graphics g, String itemType, int amount, int line) {
        g.setColor(Color.BLACK);
        int rectX = width / 2;
        int rectY = 15 + (height - 40) / 4 * line;
        g.fillRect(rectX, rectY, 150, 80);
        g.setColor((Color.WHITE));
        g.setFont(new Font("Arial", Font.BOLD, 14));
        String string = itemType + ": " +amount;
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(string);
        int textHeight = fm.getHeight();
        int x = rectX + (150 - textWidth) / 2;
        int y = rectY + (80 - textHeight) / 2 + fm.getAscent();
        g.drawString(string, x, y);
    }

    private void addSlider(JPanel panel, String name, int ID) {
        JSlider slider = createSlider(ID);
        JLabel label = new JLabel(name);

        GridLayout layout = new GridLayout(5, 2, 15, 15);
        panel.setLayout(layout);
        panel.add(slider);
        panel.add(label);
    }

    private JSlider createSlider(int ID) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 20, 0);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.addChangeListener(new sliderController(ID));
        return slider;
    }
}
