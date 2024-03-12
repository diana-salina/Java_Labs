package ru.nsu.salina.model.objects;

import ru.nsu.salina.model.MovementState;

public abstract class Entity {
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    public Entity() {};
    public void setX(Integer x) {
        this.x = x;
    }
    public void setY(Integer y) {
        this.y = y;
    }
    public void setWidth(Integer width) {
        this.width = width;
    }
    public void setHeight(Integer height) {
        this.height = height;
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
