package ru.nsu.salina.view;

import ru.nsu.salina.controller.Controller;
import ru.nsu.salina.model.Model;
import ru.nsu.salina.model.objects.Meteor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame implements ActionListener {
    private Model model;
    private final Controller controller;
    public MainWindow(Model model) {
        super("Space Way");
        this.model = model;
        this.controller = new Controller(this.model);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(this.model.getWidth(), this.model.getHeight());
        setLocationRelativeTo(null);
        addKeyListener(this.controller);
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
        Timer timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        this.model.updateMeteors();
        if (this.model.getReset()) {
            this.model = new Model();
            this.controller.resetModel(this.model);
            this.model.setReset(false);
        }
        repaint();
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
        if (model.is_dead()) {
            int best = model.getBestScore();
            g.setColor(Color.RED);
            int score = (int) model.getScore();
            g.setFont(new Font("Arial", Font.BOLD, 30));
            if (best > score) {
                g.drawString(":( score: " + score + " BS: " + best, 70, (this.model.getHeight() - 30) / 2);
            } else {
                model.setBestScore(score);
                g.drawString(":), best score: " + score, 90, (this.model.getHeight() - 30) / 2);
            }
        }
    }

    private void setIcon() {
        Image icon = new ImageIcon("resources\\images\\icon.png").getImage();
        setIconImage(icon);
    }
}
