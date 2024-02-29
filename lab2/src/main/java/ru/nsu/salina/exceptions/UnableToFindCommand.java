package ru.nsu.salina.exceptions;

public class UnableToFindCommand extends RuntimeException{
    public UnableToFindCommand(String message) {
        super(message);
    }
}
