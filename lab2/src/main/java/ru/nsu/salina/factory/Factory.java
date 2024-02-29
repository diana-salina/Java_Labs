package ru.nsu.salina.factory;

import ru.nsu.salina.commands.Command;

import java.util.Properties;

public interface Factory {
    Command findCommand(String ID, Properties properties);
}
