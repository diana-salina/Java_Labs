package ru.nsu.salina.model;

public class Delay {
    private int sec;

    public Delay(int sec) {
        this.sec = sec;
    }

    public synchronized int getDelay() {
        return sec * 1000;
    }
    public synchronized void setDelay(int sec) {
        this.sec = sec;
    }
}
