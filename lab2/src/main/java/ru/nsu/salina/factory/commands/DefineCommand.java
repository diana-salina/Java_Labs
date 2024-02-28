package ru.nsu.salina.factory.commands;

import ru.nsu.salina.factory.Command;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.Map;

public class DefineCommand implements Command {
    @Override
    public void executeCommand(Map<String, Double> map, Stack<Double> stack, String parameters) {
        String[] arguments = parameters.split(" ");
        if (arguments.length != 2) throw new IllegalArgumentException("invalid Define input");
        String variableName = arguments[0];
        Double variableValue = Double.parseDouble(arguments[1]);
        map.put(variableName, variableValue);
    }
}
