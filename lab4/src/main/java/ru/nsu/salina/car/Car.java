package ru.nsu.salina.car;

public class Car{
    private Accessory accessory;
    private Body body;
    private Engine engine;
    public Car(Engine e, Body b, Accessory a) {
        engine = e;
        body = b;
        accessory = a;
    }
}
