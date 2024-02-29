package ru.nsu.salina.factory;

import ru.nsu.salina.commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HashFactory implements Factory{
    private final Map<String, Command> hashMap = new HashMap<>();
    public Command findCommand(String ID, Properties properties) {

        CommandFactory factory = new CommandFactory();
        if (!this.hashMap.containsKey(ID)) {
            Command command = factory.findCommand(ID, properties);
            this.hashMap.put(ID, command);
            return command;
        } else {
            return this.hashMap.get(ID);
        }
    }
}
