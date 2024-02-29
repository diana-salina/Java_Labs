package ru.nsu.salina.exceptions;

public class CommandException extends RuntimeException{
    public CommandException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
