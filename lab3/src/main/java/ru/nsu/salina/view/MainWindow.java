package ru.nsu.salina.view;

import ru.nsu.salina.model.Model;
import ru.nsu.salina.model.ModelListener;
import ru.nsu.salina.model.objects.Plank;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame implements ModelListener {
    private final Model model;
    public MainWindow(Model model) {
        super("Doudle Jump");
        this.model = model;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(this.model.getWeight(), this.model.getHeight());
        try {
            setIcon("C:\\Users\\diana\\Desktop\\Java\\lab3\\src\\main\\resources\\images\\icon.png");
            setBack("C:\\Users\\diana\\Desktop\\Java\\lab3\\src\\main\\resources\\images\\back.png");
            setDoudle("C:\\Users\\diana\\Desktop\\Java\\lab3\\src\\main\\resources\\images\\doudle.png");
            //setPlanks("C:\\Users\\diana\\Desktop\\Java\\lab3\\src\\main\\resources\\images\\panel.png");
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }

        setResizable(false);
        setLocationRelativeTo(null);

        pack();
        onModelChanged();
    };

    @Override
    public void onModelChanged() {
        SwingUtilities.invokeLater(() -> {

        });
    }
    private void setIcon(String path) {
        Image icon = new ImageIcon(path).getImage();
        setIconImage(icon);
    }
    private void setBack(String path) {
        JPanel contentPane = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Image image = new ImageIcon(path).getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        contentPane.setPreferredSize(new Dimension(this.model.getWeight(), this.model.getHeight()));
        setContentPane(contentPane);
    }
    private void setDoudle(String path) {
        SpritePanel panel = new SpritePanel(path, this.model.getDoudle().getX(), this.model.getDoudle().getY());
        add(panel);
        //repaint();
    }
    private void setPlanks(String path) {
        for (Plank i: this.model.getPlanks()) {
            SpritePanel panel = new SpritePanel(path, i.getX(), i.getY());
            add(panel);
        }
    }
}
