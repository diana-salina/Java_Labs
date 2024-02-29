package ru.nsu.salina.factory.commands;

import ru.nsu.salina.exceptions.*;
import ru.nsu.salina.factory.Command;

import java.util.Map;
import java.util.Stack;

public class DivCommand implements Command {
    @Override
    public void executeCommand(Map<String, Double> map, Stack<Double> stack, String parameters) {
        if (stack == null) throw  new NullStackException("Stack do not exist");
        if (stack.size() < 2) throw new InvalidStackSizeException("Stack is too small");
        Double first = stack.pop();
        Double second = stack.pop();
        if (second == 0) throw new DivisionByZeroException("Division by zero");
        stack.push(first / second);
    }
}
