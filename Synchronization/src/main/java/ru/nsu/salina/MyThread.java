package ru.nsu.salina;

import java.util.concurrent.Phaser;

public class MyThread extends Thread{
    private Phaser phaser;
    private static final Object lock = new Object();
    private final int number;
    private int myStr;
    private static int Allstr = 1;
    public MyThread(Phaser phaser, int in) {
        super();
        this.phaser = phaser;
        phaser.register();
        this.number = in;
        this.myStr = 1;
    }
    public void run() {
        synchronized (lock) {
            while (myStr < 5) {
                System.out.println(number + " " + myStr);
                myStr++;
                phaser.arriveAndAwaitAdvance();
                lock.notifyAll();
            }
        }
    }
}
