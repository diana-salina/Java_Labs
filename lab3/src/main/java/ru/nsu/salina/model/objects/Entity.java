package ru.nsu.salina.model.objects;

import ru.nsu.salina.model.MovementState;

public abstract class Entity {
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    public Entity() {};
    public Entity(Integer x, Integer y, Integer width, Integer height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }
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

    public void move(Integer shift, MovementState state) {
        switch(state) {
            case UP:
                this.y += shift;
            case DOWN:
                this.y -= shift;
            case LEFT:
                this.x -= shift;
            case RIGHT:
                this.x += shift;
        }
    }

}
