package ru.nsu.salina.commands;

import ru.nsu.salina.exceptions.NullStackException;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Stack;

public class PrintCommand implements Command {
    @Override
    public void executeCommand(Map<String, Double> map, Stack<Double> stack, String parameters) {
        if (stack == null) throw  new NullStackException("Stack do not exist");
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(stack.lastElement());
        System.out.println(formattedNumber);
    }
}
