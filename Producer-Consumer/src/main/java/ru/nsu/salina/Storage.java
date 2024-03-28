package ru.nsu.salina;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class Storage {
    private final int size;
    private final Deque<String> items;
    public Storage(Integer size) {
        this.items = new ArrayDeque<>();
        this.size = size;
    }
    public void putInStorage(String message) throws InterruptedException {
        synchronized (this) {
            while(this.isFull()) { wait(); }
            this.items.add(message);
            System.out.println(this.items.getFirst());
            this.notifyAll();
        }
    }
    public String popItem() throws InterruptedException {
        synchronized (this) {
            while (this.isEmpty()) { wait(); }
            String item = this.items.pop();
            this.notifyAll();
            return item;
        }
    }
    public int getCount() {
        synchronized (this) {
            return this.items.size();
        }
    }

    private boolean isFull() {
        synchronized (this) {
            return this.getCount() >= this.size;
        }
    }
    private boolean isEmpty() {
        synchronized (this) {
            return this.items.isEmpty();
        }
    }
}
