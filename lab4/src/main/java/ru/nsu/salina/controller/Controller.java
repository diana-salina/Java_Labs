package ru.nsu.salina.controller;

import ru.nsu.salina.model.CarFactory;

public class Controller {
    private CarFactory factory;
    public Controller(CarFactory factory) {
        this.factory = factory;
        factory.start();
    }
}
