package ru.nsu.salina.factory.commands;

import ru.nsu.salina.factory.Command;

import java.util.EmptyStackException;
import java.util.Map;
import java.util.Stack;

public class PopCommand implements Command {

    @Override
    public void executeCommand(Map<String, Double> map, Stack<Double> stack, String parameters) {
        stack.pop();
    }
}
