package ru.nsu.salina.commands;

import ru.nsu.salina.exceptions.InvalidStackSizeException;
import ru.nsu.salina.exceptions.NullStackException;

import java.util.Map;
import java.util.Stack;

public class MultCommand implements Command {
    @Override
    public void executeCommand(Map<String, Double> map, Stack<Double> stack, String parameters) {
        if (stack == null) throw  new NullStackException("Stack do not exist");
        if (stack.size() < 2) throw new InvalidStackSizeException("Stack is too small");
        Double first = stack.pop();
        Double second = stack.pop();
        stack.push(first * second);
    }
}
