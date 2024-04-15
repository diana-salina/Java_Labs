package ru.nsu.salina;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Data {
    private String name;
    private int age;
    private ObjectMapper mapper;
    public Data() {
        mapper = new ObjectMapper();
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
}
