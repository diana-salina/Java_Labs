package ru.nsu.salina;

import java.io.*;
import java.util.*;

public class InputReader {
    public static void getStack(String path, List<String> commands, List<String> parameters) throws IOException {
        try (Reader reader0 = (path == null || path.isEmpty()) ? new InputStreamReader(System.in) : new FileReader(path)) {
            try (BufferedReader reader = new BufferedReader(reader0)) {
                String line = reader.readLine();
                int ind = 0;
                while (line != null & !Objects.equals(line, "")) {
                    String[] splited = line.split(" ", 2);
                    if (splited.length == 1) {
                        commands.add(ind, splited[0]);
                        parameters.add(ind, "");
                    } else {
                        commands.add(ind, splited[0]);
                        parameters.add(ind, splited[1]);
                    }
                    ++ind;
                    try {
                        line = reader.readLine();
                    } catch (NoSuchElementException ex) {
                        break;
                    }
                }
            }
        }
    }
}