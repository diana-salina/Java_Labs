package ru.nsu.salina;

public class Producer extends Thread{
    private final String name;
    private final Storage storage;
    public Producer(int i, Storage storage) {
        super();
        this.name = "p" + i;
        this.storage = storage;
    }
    private String getMessage() {
        return this.name + "-" + this.storage.getCount();
    }
    public void run() {
        for (int i = 0; i < 10; ++i) {
            try {
                this.storage.putInStorage(this.getMessage());
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
