package ru.nsu.salina;

import java.util.*;

public class InputReader {
    public static Stack<String> getStack(Scanner scanner) {
        Stack<String> stack = new Stack<String>();
        String line = scanner.nextLine();
        while (line != null) {
            stack.push(line);
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException ex) {
                break;
            }
        }
        return stack;
    }

    public static String[] SplitStackElement(String el) {
        return el.split(" ");
    }
}
