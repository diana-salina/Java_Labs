package ru.nsu.salina.controller;

import ru.nsu.salina.model.Model;
import ru.nsu.salina.view.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Controller implements KeyListener, ActionListener {
    private Model model;
    private int dx, dy;
    private final Timer timer;
    public Controller(Model model) {
        this.model = model;
        this.timer = new Timer(10, this);
        this.timer.start();
    }
    @Override
    public void keyTyped(KeyEvent ev) {
    }
    @Override
    public void keyPressed(KeyEvent ev) {
        int keyCode = ev.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            dx = -4;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            dx = 4;
        } else if (keyCode == KeyEvent.VK_UP) {
            dy = -4;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            dy = 4;
        } else if (this.model.is_dead() && keyCode == KeyEvent.VK_ENTER) {
            this.model.setReset(true);
            timer.start();
        }
    }
    public void resetModel(Model model) {this.model = model;}
    @Override
    public void keyReleased(KeyEvent ev) {
        int keyCode = ev.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            dx = 0;
        } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
    @Override
    public void actionPerformed(ActionEvent ev) {
        this.model.movePlayer(dx, dy);
        if (model.is_dead()) {
            this.timer.stop();
        } else model.increaseScore();
    }
    public Model getModel() { return this.model; }
}
