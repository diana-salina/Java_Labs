package ru.nsu.salina.view;

import ru.nsu.salina.model.Model;
import ru.nsu.salina.model.ModelListener;

import javax.swing.*;
import java.awt.*;
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
            //TODO draw Doudle and planks correctly
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }

        setResizable(false);
        setLocationRelativeTo(null);

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

}
