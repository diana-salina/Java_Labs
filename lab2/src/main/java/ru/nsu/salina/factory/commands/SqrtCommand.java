package ru.nsu.salina.factory.commands;

import ru.nsu.salina.factory.Command;

import java.util.Map;
import java.util.Stack;

public class SqrtCommand implements Command {
    @Override
    public void executeCommand(Map<String, Double> map, Stack<Double> stack, String parameters) {
        Double el = stack.pop();
        stack.push(Math.sqrt(el));
    }
}
