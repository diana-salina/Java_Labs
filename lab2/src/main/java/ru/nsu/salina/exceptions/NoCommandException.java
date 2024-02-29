package ru.nsu.salina.exceptions;

public class NoCommandException extends RuntimeException{
    public NoCommandException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
