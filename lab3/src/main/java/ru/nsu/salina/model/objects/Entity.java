package ru.nsu.salina.model.objects;

import ru.nsu.salina.model.Direction;

public abstract class Entity implements GameUnit {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Direction dir;
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.dir = Direction.STAY_X;

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
    public void move() {
        if (dir == Direction.LEFT) {
            x -= 5;
        } else if (dir == Direction.RIGHT) {
            x += 5;
        } else if (dir == Direction.UP) {
            y -= 5;
        } else if (dir == Direction.DOWN) {
            y += 5;
        }
    }
    public void setDiraction(Direction d) {
        this.dir = d;
    }

}
