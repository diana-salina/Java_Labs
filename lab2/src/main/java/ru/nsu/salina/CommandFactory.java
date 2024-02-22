package ru.nsu.salina;

public class CommandFactory {
    public static Command findCommand(String command) {
        return switch (command) {
            case "#" -> new CommentCommand();
            case "POP" -> new PopCommand();
            case "PUSH" -> new PuchCommand();
            case "PRINT" -> new PrintCommand();
            case "DEFAULT" -> new DefineCommand();
            default -> throw new IllegalArgumentException("Command iis not supported");
        };
    }
}
