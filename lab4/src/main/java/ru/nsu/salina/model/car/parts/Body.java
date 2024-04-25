package ru.nsu.salina.model.car.parts;

public class Body implements Part{
    private static int id = 0;
    private String bodyID;
    public Body() {
        bodyID = "B" + id;
        id++;
    }
    public String getID() {
        return bodyID;
    }
}
