package ru.nsu.salina.model.car.parts;

public class Accessory implements Part{
    private static int id = 0;
    private String accessoryID;
    public Accessory() {
        accessoryID = "A" + id;
        id++;
    }
    public String getID() {
        return accessoryID;
    }
}
