package ru.nsu.salina;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int P = scanner.nextInt();
        int C = scanner.nextInt();
        Storage storage = new Storage(N);
        for (int i = 0; i < P; ++i) {
            Producer producer = new Producer(i, storage);
            producer.start();
        }
        for (int i = 0; i < C; ++i) {
            Consumer consumer = new Consumer(i, storage);
            consumer.start();
        }
    }
}