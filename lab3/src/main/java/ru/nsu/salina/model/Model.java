package ru.nsu.salina.model;

import ru.nsu.salina.model.objects.Doudle;
import ru.nsu.salina.model.objects.Plank;

import java.util.ArrayList;

public class Model {
    private Doudle doudle;
    private ArrayList<Plank> planks;
    public Model() {
        this.doudle = new Doudle();
        this.planks = new ArrayList<Plank>();
    };
}
