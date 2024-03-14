package ru.nsu.salina.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpritePanel  extends JPanel {
    private final int x;
    private final int y;
    private BufferedImage img;
    public SpritePanel(String path, Integer x, Integer y) {
        try {
            this.img = ImageIO.read(new File(path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.x = x;
        this.y = y;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSprite(g);
    }
    private void drawSprite(Graphics g) {
        g.drawImage(this.img, this.x, this.y, this);
    }
}
