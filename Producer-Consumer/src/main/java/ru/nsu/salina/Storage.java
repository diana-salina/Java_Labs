package ru.nsu.salina;

import java.util.Stack;

public class Storage {
    private final Integer size;
    private final Stack<String> items;
    private static Integer amount;
    Storage(Integer size) {
        this.items = new Stack<>();
        this.size = size;
        amount = 0;
    }
    public void putInStorage(String producer) throws InterruptedException {
        synchronized (this) {
            if (!this.isFull()) {
                amount++;
                this.items.add(producer + "-" + amount);
                this.notifyAll();
                //System.out.println(producer + " added: " + producer + "-" + amount);
            } else {
                this.wait(50);
            }
        }
    }
    public void popItem(String consumer) throws InterruptedException {
        synchronized (this) {
            if (!this.isEmpty()) {
                String item = this.items.pop();
                this.notifyAll();
                amount--;
                System.out.println(consumer + " consumes " + item);
            } else {
                this.wait(50);
            }
        }
    }
    public boolean isFull() {
        return amount >= this.size;
    }
    public boolean isEmpty() {
        return amount == 0;
    }
}
