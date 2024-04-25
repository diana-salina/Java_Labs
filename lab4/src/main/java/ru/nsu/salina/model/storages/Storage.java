package ru.nsu.salina.model.storages;

import java.util.ArrayList;

public class Storage<T> implements Container<T> {
    protected int size;
    protected ArrayList<T> items;
    public Storage(int size) {
        this.size = size;
        this.items = new ArrayList<>();
    }
    public synchronized void put(T item) {
        while (isFull()) {
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                ex.printStackTrace();
                //TODO close threads
            }
        }
        this.notifyAll();
        items.add(item);
    }
    public synchronized T take() {
        while (isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                ex.printStackTrace();
                //TODO close threads
            }
        }
        T item = items.getFirst();
        this.notifyAll();
        return item;
    }
    public synchronized boolean isFull() {
        return size == items.size();
    }
    public synchronized boolean isEmpty() {
        return items.isEmpty();
    }
}
