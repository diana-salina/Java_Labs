package ru.nsu.salina.model.car;

import ru.nsu.salina.model.car.parts.Accessory;
import ru.nsu.salina.model.car.parts.Body;
import ru.nsu.salina.model.car.parts.Engine;

public class Car{
    private Accessory accessory;
    private Body body;
    private Engine engine;
    private String carID;
    private static int id = 0;
    public Car(Engine e, Body b, Accessory a) {
        carID = "C" + id;
        id++;
        engine = e;
        body = b;
        accessory = a;
    }
    public String getCarID() {
        return carID;
    }

    public String getDetailsID() {
        return "(Body: " + body.getID() + ", Motor: " +
                engine.getID() + ", Accessory: " + accessory.getID() + ")";
    }
}
