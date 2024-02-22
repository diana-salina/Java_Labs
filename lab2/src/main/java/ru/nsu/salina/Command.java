package ru.nsu.salina;

import javax.print.DocFlavor;

public interface Command {
    public void executeCommand();
}

class CommentCommand implements Command {
    @Override
    public void executeCommand() {

    };
}

class DefineCommand implements Command {
    @Override
    public void executeCommand() {

    }
}

class PopCommand implements Command {
    @Override
    public void executeCommand() {

    }
}

class PrintCommand implements Command {
    @Override
    public void executeCommand() {

    }
}

class PuchCommand implements Command {
    @Override
    public void executeCommand() {

    }
}


