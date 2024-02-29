package ru.nsu.salina;

import ru.nsu.salina.commands.Command;
import ru.nsu.salina.factory.Factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Calculator {
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();
    private final List<String> commands = new ArrayList<>();
    private final List<String> parameters = new ArrayList<>();

    public void getData(String path) {
        try {
            InputReader.getStack(path, this.commands, this.parameters);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void doTask(String commandsListPath, Factory factory) {
        Properties properties = new Properties();
        try {
            File file = new File(commandsListPath);
            if (!file.exists()) {
                throw new FileNotFoundException("File do not exist");
            }
            properties.load(new FileReader(file));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        for (int i = 0; i < this.commands.size(); ++i) {
            Command currentCommand = factory.findCommand(this.commands.get(i), properties);
            try {
                currentCommand.executeCommand(this.map, this.stack, this.parameters.get(i));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                break;
            }

        }
    }
}
