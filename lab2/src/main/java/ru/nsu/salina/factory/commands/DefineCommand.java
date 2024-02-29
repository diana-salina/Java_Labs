package ru.nsu.salina.factory.commands;

import ru.nsu.salina.exceptions.*;
import ru.nsu.salina.factory.Command;

import java.util.Stack;
import java.util.Map;

public class DefineCommand implements Command {
    @Override
    public void executeCommand(Map<String, Double> map, Stack<Double> stack, String parameters) throws IllegalArgumentException, NullMapException{
        String[] arguments = parameters.split(" ");
        if (arguments.length != 2) throw new IllegalArgumentException("invalid Define input");
        String variableName = arguments[0];
        Double variableValue = Double.parseDouble(arguments[1]);
        if (map == null) throw new NullMapException("Map do not exist");
        map.put(variableName, variableValue);
    }
}
