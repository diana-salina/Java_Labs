package ru.nsu.salina.factory;

import ru.nsu.salina.commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CacheFactory implements Factory{
    private final Map<String, Command> cacheFactory = new HashMap<>();
    private final Factory factory;

    public CacheFactory(Factory factory) {
        this.factory = factory;
    }
    public Command findCommand(String ID, Properties properties) {
        if (!this.cacheFactory.containsKey(ID)) {
            Command command = this.factory.findCommand(ID, properties);
            this.cacheFactory.put(ID, command);
            return command;
        } else {
            return this.cacheFactory.get(ID);
        }
    }
}
