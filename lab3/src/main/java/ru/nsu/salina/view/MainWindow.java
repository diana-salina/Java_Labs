package ru.nsu.salina.view;

import ru.nsu.salina.controller.Controller;
import ru.nsu.salina.model.Model;
import ru.nsu.salina.model.ModelListener;
import ru.nsu.salina.model.objects.Meteor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame implements ModelListener {
    private final Model model;
    private final int height = 420;
    private final int width= 420;
    public MainWindow(Model m) {
        super("Space Way");
        model = m;
        model.setHeight(420);
        model.setWidth(420);
        Controller controller = new Controller(this.model);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        addKeyListener(controller);
        model.setListener(this);
        try {
            setIcon();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        setResizable(false);
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponents(g);
                try {
                    setBack(g);
                    setPlayer(g);
                    setMeteors(g);
                    setDeath(g);
                } catch (IOException | IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
                setScore(g);
            }
        };
        add(panel);

    }
    @Override
    public void onModelChanged() {
        SwingUtilities.invokeLater(this::repaint);
    }
    private void setBack(Graphics g) throws IOException, IllegalArgumentException {
        Image m = ImageIO.read(new File("resources\\images\\back.jpg"));
        if (m != null) {
            g.drawImage(m, 0, 0, this);
        }
    }
    private void setPlayer(Graphics g) throws IOException, IllegalArgumentException {
        Image m = ImageIO.read(new File("resources\\images\\player.png"));
        if (m != null) {
            g.drawImage(m, model.getPlayerX(), model.getPlayerY(), this);
        }
    }

    private void setMeteors(Graphics g) throws IOException, IllegalArgumentException {
        Image m = ImageIO.read(new File("resources\\images\\meteor.png"));
        if (m != null) {
            for (Meteor meteor : model.getMeteors()) {
                g.drawImage(m, meteor.getX(), meteor.getY(), this);
            }
        }

    }
    private void setScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("" + (int) model.getScore(), 10, 25);
    }
    private void setDeath(Graphics g) {
        if (model.isDead()) {
            int best = model.getBestScore();
            g.setColor(Color.RED);
            int score = (int) model.getScore();
            String text;
            g.setFont(new Font("Arial", Font.BOLD, 30));
            if (best > score) {
                text = ":( score: " + score + " BS: " + best;
            } else {
                model.setBestScore(score);
                text = ":), best score: " + score;
            }

            int textWidth = g.getFont().getSize() / 2 * text.length();
            int x = (this.model.getWidth() - textWidth) / 2;
            int y = this.getHeight() / 2;
            g.drawString(text, x, y);

        }
    }
    private void setIcon() {
        Image icon = new ImageIcon("resources\\images\\icon.png").getImage();
        setIconImage(icon);
    }
}
