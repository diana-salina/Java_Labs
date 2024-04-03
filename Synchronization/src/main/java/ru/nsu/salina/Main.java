package ru.nsu.salina;

import java.util.concurrent.Phaser;

public class Main {
    public static void main(String[] args) {
        int N = 2;
        MyThread[] threads = new MyThread[N];
        Phaser phaser = new Phaser(0);
        for (int i = 0; i < N; ++i) {
            threads[i] = new MyThread(phaser, i + 1);
            threads[i].start();
        }
        for (int i = 0; i < N; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
