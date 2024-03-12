package ru.nsu.salina.model;

import ru.nsu.salina.model.objects.Doudle;
import ru.nsu.salina.model.objects.Plank;

import java.util.ArrayList;

public class Model {
    private Doudle doudle;
    private ArrayList<Plank> planks;
    private Integer height;
    private Integer weight;
    public Model() {
        this.height = 640;
        this.weight =480;
        this.doudle = new Doudle();
        this.planks = new ArrayList<Plank>();
        this.doudle.setX((weight - doudle.getWidth()) / 2);
        this.doudle.setY(height / 20);
    };
    private void generatePlanks() {

    }
    private void handleHighJump() {

    }
     private void isDoudleOnPlank() {

     }
}
