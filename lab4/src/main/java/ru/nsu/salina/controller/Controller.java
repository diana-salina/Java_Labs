package ru.nsu.salina.controller;

import ru.nsu.salina.model.CarFactory;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Controller extends WindowAdapter {
    private CarFactory factory;
    private int ID;
    public Controller(CarFactory factory, int ID) {
        this.factory = factory;
        factory.start();
        this.ID = ID;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        factory.closeFactory();
    }
}
