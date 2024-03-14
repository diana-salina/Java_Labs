package ru.nsu.salina.model;

import ru.nsu.salina.model.objects.Doudle;
import ru.nsu.salina.model.objects.Plank;

import java.util.ArrayList;

public class Model {
    private final Doudle doudle;
    private final ArrayList<Plank> planks;
    private final Integer height;
    private final Integer weight;
    public Model() {
        this.height = 640;
        this.weight =480;
        this.doudle = new Doudle();
        this.planks = new ArrayList<Plank>();
        this.doudle.setX((weight - doudle.getWidth()) / 2);
        this.doudle.setY(height / 20);
    };
    public Doudle getDoudle() {
        return this.doudle;
    }
    public ArrayList<Plank> getPlanks() {
        return this.planks;
    }
    public Integer getHeight() {
        return this.height;
    }
    public Integer getWeight() {
        return this.weight;
    }
    private void generatePlanks() {

    }
    private void handleHighJump() {

    }
     private void isDoudleOnPlank() {

     }
}
