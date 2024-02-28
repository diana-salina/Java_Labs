package ru.nsu.salina.factory;

import java.lang.reflect.Constructor;
import java.util.Properties;

public class CommandFactory {
    public static Command findCommand(String commandID, Properties propertiesIn) {
        Properties properties = propertiesIn;
        String className = properties.getProperty(commandID);

        try {
            Class<Command> commandClass = (Class<Command>) Class.forName(className);
            Constructor<Command> commandConstructor = commandClass.getDeclaredConstructor();
            Command command = commandConstructor.newInstance();
            return command;
        } catch (Exception ex) {
            throw new UnableToFindCommand(commandID + ex.getMessage());
        }
    }
}
