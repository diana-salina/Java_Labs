package ru.nsu.salina.factory;

import java.util.Map;
import java.util.Stack;

public interface Command {
    void executeCommand(Map<String, Double> map, Stack<Double> stack, String parameters);
}
