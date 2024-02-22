package ru.nsu.salina;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner =
                     new Scanner(
                             new FileInputStream(args[0]))
        ) {
            Calculator calculator = new Calculator();
            calculator.DoTask(scanner);
        }
    }
}