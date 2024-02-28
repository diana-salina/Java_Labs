package ru.nsu.salina;

import ru.nsu.salina.factory.Command;
import ru.nsu.salina.factory.CommandFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Calculator {
    public Stack<Double> stack = new Stack<Double>();
    public Map<String, Double> map = new HashMap<String, Double>();
    public Stack<String> commands = new Stack<String>();
    public Stack<String> parametres = new Stack<String>();

    public void DoTask(Scanner scanner, File commandsListPath) {
        Properties properties = new Properties();
        try {
                properties.load(new FileReader(commandsListPath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        InputReader.getStack(scanner, this.commands, this.parametres);
        for (String operator : this.commands) {
            Command currentCommand = CommandFactory.findCommand(this.commands.pop(), properties);
            currentCommand.executeCommand(this.map, this.stack, this.parametres.pop());
        }
    }
}
