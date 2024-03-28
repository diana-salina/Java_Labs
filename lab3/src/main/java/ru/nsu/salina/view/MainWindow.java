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
    private final JPanel panel;
    private final Timer timer;
    public MainWindow(Model model) {
        super("Space Way");
        this.model = model;
        this.controller = new Controller(this.model);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(this.model.getWidth(), this.model.getHeight());
        setLocationRelativeTo(null);
        addKeyListener(this.controller);
        setBackground(Color.WHITE);
        try {
            setIcon("resources\\images\\icon.png");
            //setBack("resources\\images\\back.jpg");
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        setResizable(false);
        this.panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponents(g);
                try {
                    setPlayer(g);
                    setMeteors(g);
                } catch (IOException | IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
                setScore(g);
                setDeath(g);
            }
        };

        add(this.panel);

        this.timer = new Timer(10, this);
        timer.start();
    };

    @Override
    public void actionPerformed(ActionEvent ev) {
        this.model.updateMeteors();
        if (this.model.getReset()) {
            this.model = this.controller.getModel();
            this.model.setReset(false);
        }
        repaint();
    }
    private void setPlayer(Graphics g) throws IOException, IllegalArgumentException{
        Image m = ImageIO.read(new File("resources\\images\\player.png"));
        if (m != null) {
            g.drawImage(m, model.getPlayerX(), model.getPlayerY(), this);
        }
        //g.setColor(Color.BLACK);
        //g.fillRect(model.getPlayerX(), model.getPlayerY(), model.getPlayerWidth(), model.getPlayerHeight());
    }

    private void setMeteors(Graphics g) throws IOException, IllegalArgumentException {
        Image m = ImageIO.read(new File("resources\\images\\meteor.png"));
        if (m != null) {
            for (Meteor meteor : model.getMeteors()) {
                g.drawImage(m, meteor.getX(), meteor.getY(), this);
                //g.fillRect(meteor.getX(), meteor.getY(), meteor.getWidth(), meteor.getHeight());
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
                g.drawString(":(, score: " + score + " BS: " + best, 90, 200);
            } else {
                model.setBestScore(score);
                g.drawString(":), best score: " + score, 90, 200);
            }
        }
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

        contentPane.setPreferredSize(new Dimension(this.model.getWidth(), this.model.getHeight()));
        setContentPane(contentPane);
    }
}
