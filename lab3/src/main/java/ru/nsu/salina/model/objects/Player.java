package ru.nsu.salina.model.objects;

public class Player extends Entity implements GameUnit{
    public Player(int x, int y) {
        super(x, y);
        this.height = 20;
        this.width = 20;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
