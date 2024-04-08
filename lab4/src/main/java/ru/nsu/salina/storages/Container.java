package ru.nsu.salina.storages;

public interface Container<T> {
    void put(T item);
    void take(T item);

}
