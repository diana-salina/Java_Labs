package ru.nsu.salina.controller;

import ru.nsu.salina.model.CarFactory;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller implements ChangeListener {
    private CarFactory factory;
    public Controller(CarFactory factory) {
        this.factory = factory;
        factory.start();
    }
    @Override
    public void stateChanged(ChangeEvent event) {

    }
}
