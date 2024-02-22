package ru.nsu.salina;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Counter counter = new Counter();
        System.out.print("Print filename: ");
        Scanner in = new Scanner(System.in);
        String file = in.next();
        try (Scanner scanner =
                new Scanner(
                        new FileInputStream(file))
        ) {
            counter.readInput(scanner);
            final int count = counter.getWordCount();
            final Map<String, Integer> map = counter.getWorldMap();

            try (BufferedOutputStream bos =
                    new BufferedOutputStream(
                         new FileOutputStream("out.csv"))
            ) {
                Recorder.buildCSV(bos, count, map);
            }
        }
    }
}