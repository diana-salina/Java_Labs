package ru.nsu.salina;

import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    private Stack<String> TaskStack = new Stack<String>();
    public Stack<Integer> ExecuteStack = new Stack<Integer>();

    public void DoTask(Scanner scanner) {
        TaskStack = InputReader.getStack(scanner);
        for (String operator : TaskStack) {
            String[] splited = InputReader.SplitStackElement(operator);
            Command currentCommand = CommandFactory.findCommand(splited[0]);
            currentCommand.executeCommand(splited);
        }
    }
}
