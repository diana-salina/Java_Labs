package ru.nsu.salina.factory;

import java.util.Properties;

public interface Factory {
    <T>T findCommand(String ID, Properties properties);
}
