package ru.nsu.salina.model.objects;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    };

    public Integer getX() {
        return this.x;
    }
    public Integer getY() {
        return this.y;
    }
    public Integer getWidth() {
        return this.width;
    }
    public Integer getHeight() {
        return this.height;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

}
