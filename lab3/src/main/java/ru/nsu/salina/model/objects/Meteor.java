package ru.nsu.salina.model.objects;

public class Meteor extends Entity{
    public Meteor(int x, int y) {
        super(x, y);
        this.height = 10;
        this.width = 10;
    }

    public boolean is_hitted(Player player) {
        int playerX = player.getX();
        int playerY = player.getY();
        int playerW = player.getWidth();
        int playerH = player.getHeight();
        return this.x < playerX + playerW && this.x + this.width > playerX &&
                this.y < playerY + playerH && this.y + this.height > playerY;

    }

}
