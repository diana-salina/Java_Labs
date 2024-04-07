package ru.nsu.salina.model.objects;

public interface GameUnit {

    int getX();
    int getY();
    int getWidth();
    int getHeight();

    void move();
}
