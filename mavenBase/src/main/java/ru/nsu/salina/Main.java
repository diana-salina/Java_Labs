package ru.nsu.salina;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Data data = new Data();
        data.setName("Diana");
        data.setAge(19);

        JParser<Data> json = new JParser<>();
        json.write(data);
        Data data2 = json.read("{\"name\":\"Bob\", \"age\":13}", Data.class);
        json.write(data2);
    }
}