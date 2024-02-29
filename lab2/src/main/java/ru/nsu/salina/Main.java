package ru.nsu.salina;

import ru.nsu.salina.factory.*;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Factory factory = new CommandFactory();
        Factory hashfactory = new HashFactory();
        String path;
        try {
            if (!(args == null | args[0].isEmpty())){
                path = args[0];
            } else {
                path = null;
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            path = null;
        }
        calculator.getData(path);
        calculator.doTask("src\\main\\resources\\commands.properties", hashfactory);
    }
}