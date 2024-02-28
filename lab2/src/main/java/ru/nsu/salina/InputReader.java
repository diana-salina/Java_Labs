package ru.nsu.salina;

import java.util.*;

public class InputReader {
    public static void getStack(Scanner scanner, Stack<String> commands, Stack<String> parametres) {
        String line = scanner.nextLine();
        while (line != null) {
            String[] splited = line.split(" ", 1);
            if (splited.length == 1) {
                commands.push(splited[0]);
                parametres.push("");
            } else {
                commands.push(splited[0]);
                parametres.push(splited[1]);
            }
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException ex) {
                break;
            }
        }
    }
}