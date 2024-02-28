package ru.nsu.salina.factory.commands;

import ru.nsu.salina.factory.Command;

import java.util.Map;
import java.util.Stack;

public class PuchCommand implements Command {
    @Override
    public void executeCommand(Map<String, Double> map, Stack<Double> stack, String parameters) {
        stack.push((map.get(parameters)));
    }
}