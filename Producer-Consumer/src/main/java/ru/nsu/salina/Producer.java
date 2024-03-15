package ru.nsu.salina;

public class Producer extends Thread{
    private String name;
    private final Storage storage;
    Producer(Integer i, Storage storage) {
        super();
        this.name = "p" + i;
        this.storage = storage;
    }
    public void run() {
        for (int i = 0; i < 10; ++i) {
            try {
                this.storage.putInStorage(this.name);
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
