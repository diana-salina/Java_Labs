package ru.nsu.salina;

import javax.print.DocFlavor;

public interface Command {
    public void executeCommand(String[] splited);
}

class CommentCommand implements Command {
    @Override
    public void executeCommand(String[] splited) {

    };
}

class DefineCommand implements Command {
    @Override
    public void executeCommand(String[] splited) {

    }
}

class PopCommand implements Command {
    @Override
    public void executeCommand(String[] splited) {

    }
}

class PrintCommand implements Command {
    @Override
    public void executeCommand(String[] splited) {

    }
}

class PuchCommand implements Command {
    @Override
    public void executeCommand(String[] splited) {

    }
}


