package ru.nsu.salina.factory.commands;

import ru.nsu.salina.exceptions.NullMapException;
import ru.nsu.salina.exceptions.NullStackException;
import ru.nsu.salina.factory.Command;

import java.util.Map;
import java.util.Stack;

public class PushCommand implements Command {
    @Override
    public void executeCommand(Map<String, Double> map, Stack<Double> stack, String parameters) {
        if (stack == null) throw  new NullStackException("Stack do not exist");
        if (map == null) throw new NullMapException("Map do not exist");

        stack.push((map.get(parameters)));
    }
}