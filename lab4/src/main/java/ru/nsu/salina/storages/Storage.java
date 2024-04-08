package ru.nsu.salina.storages;

import java.util.ArrayList;

public abstract class Storage<T> implements Container<T>{
    protected int size;
    protected ArrayList<T> items;
    public Storage(int size) {
        this.size = size;
        this.items = new ArrayList<>();
    }
    public void put(T item) {

    }
    public void take(T item) {

    }
}
