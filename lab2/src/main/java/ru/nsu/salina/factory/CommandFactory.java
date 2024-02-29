package ru.nsu.salina.factory;

import ru.nsu.salina.commands.Command;
import ru.nsu.salina.exceptions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class CommandFactory implements Factory{
    @Override
    public Command findCommand(String commandID, Properties properties) {
        String className = properties.getProperty(commandID);
        try {
            if (className == null) {
                throw new CommandException("No such command as: " + commandID);
            }
        } catch (CommandException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            Class<Command> commandClass = (Class<Command>) Class.forName(className);
            Constructor<Command> commandConstructor = commandClass.getDeclaredConstructor();
            return commandConstructor.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new UnableToFindCommand(commandID + ex.getMessage());
        }
    }
}
