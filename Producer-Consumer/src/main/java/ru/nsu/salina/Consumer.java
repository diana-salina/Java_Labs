package ru.nsu.salina;

public class Consumer extends Thread{
    private final String name;
    private final Storage storage;
    Consumer(Integer i, Storage storage) {
        super();
        this.name = "c" + i;
        this.storage = storage;
    }
    public void run() {
        for (int i = 0; i < 10; ++i) {
            try {
                sleep(1000);
                this.storage.popItem(this.name);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
