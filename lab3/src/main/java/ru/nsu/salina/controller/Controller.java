package ru.nsu.salina.controller;

import ru.nsu.salina.model.Direction;
import ru.nsu.salina.model.Model;

import java.awt.event.*;

public class Controller extends WindowAdapter implements KeyListener {
    private final Model model;
    public Controller(Model model) {
        this.model = model;
    }
    @Override
    public void windowClosing(WindowEvent event) {
        try {
            model.close();
        } catch (InterruptedException ex) {
            throw new RuntimeException((ex));
        }
        event.getWindow().setVisible(false);
        event.getWindow().dispose();
    }
    @Override
    public void keyTyped(KeyEvent ev) {
    }
    @Override
    public void keyPressed(KeyEvent ev) {
        int keyCode = ev.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            model.setPlayerDirection(Direction.LEFT);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            model.setPlayerDirection(Direction.RIGHT);
        } else if (keyCode == KeyEvent.VK_UP) {
            model.setPlayerDirection(Direction.UP);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            model.setPlayerDirection(Direction.DOWN);
        } else if (this.model.isDead() && keyCode == KeyEvent.VK_ENTER) {
            this.model.setReset(true);
        }
    }
    @Override
    public void keyReleased(KeyEvent ev) {
        int keyCode = ev.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            model.setPlayerDirection(Direction.STAY_X);
        } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            model.setPlayerDirection(Direction.STAY_Y);
        }
    }
}
