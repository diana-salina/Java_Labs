package ru.nsu.salina.model.storages;

import java.awt.desktop.AboutEvent;

public interface Container<T> {
    void put(T item);
    T take();
    boolean isFull();
    boolean isEmpty();

}
