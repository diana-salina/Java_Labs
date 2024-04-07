package ru.nsu.salina.model.objects;

public class Meteor extends Entity implements GameUnit{
    private boolean mute;
    public Meteor(int x, int y) {
        super(x, y);
        this.height = 10;
        this.width = 10;
        this.mute = false;
    }

    public boolean doHit(Player player) {
        int playerX = player.getX();
        int playerY = player.getY();
        int playerW = player.getWidth();
        int playerH = player.getHeight();
        return this.x < playerX + playerW && this.x + this.width > playerX &&
                this.y < playerY + playerH && this.y + this.height > playerY;

    }

    public void setMute(boolean m) {
        this.mute = m;
    }
    public boolean getMute() {
        return this.mute;
    }

}
