package ru.nsu.salina;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
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
        calculator.DoTask("src\\main\\resources\\commands.properties");
    }
}