package ru.nsu.salina.model.car.parts;

public class Engine implements Part{
    private static int id = 0;
    private String engineID;
    public Engine() {
        engineID = "A" + id;
        id++;
    }
    public String getID() {
        return engineID;
    }
}
