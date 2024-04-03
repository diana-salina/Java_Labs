package ru.nsu.salina.model.objects;

public abstract class Entity implements GameUnit {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    };
    @Override
    public int getX() {
        return this.x;
    }
    @Override
    public int getY() {
        return this.y;
    }
    @Override
    public int getWidth() {
        return this.width;
    }
    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

}
