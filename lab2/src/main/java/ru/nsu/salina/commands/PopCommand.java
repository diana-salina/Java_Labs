package ru.nsu.salina.commands;

import ru.nsu.salina.exceptions.NullStackException;

import java.util.Map;
import java.util.Stack;

public class PopCommand implements Command {

    @Override
    public void executeCommand(Map<String, Double> map, Stack<Double> stack, String parameters) {
        if (stack == null) throw  new NullStackException("Stack do not exist");
        stack.pop();
    }
}
