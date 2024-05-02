package ru.nsu.salina.model.storages;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Storage<T> implements Container<T> {
    protected int size;
    protected final Queue<T> items;
    public Storage(int size) {
        this.size = size;
        this.items = new LinkedList<>();
    }
    public void put(T item) {
        synchronized (items) {
            while (isFull()) {
                try {
                    items.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    //TODO close threads
                }
            }
            items.add(item);
            //System.out.println(item.toString() + " added");
            items.notifyAll();
        }
    }
    public T take() {
        synchronized (items) {
            while (isEmpty()) {
                try {
                    items.wait();
                } catch (InterruptedException ex) {
                    ex.getMessage();
                    //TODO close threads
                }
            }
            var i = items.poll();
            //System.out.println(i.toString() + " taken");
            items.notifyAll();
            return i;
        }
    }
    public synchronized int getAvailablePlaces() {
        return size - items.size();
    }
    public synchronized boolean isFull() {
        return size == items.size();
    }
    public synchronized boolean isEmpty() {
        return items.isEmpty();
    }

    public synchronized int getAmount() {
        return items.size();
    }
}
