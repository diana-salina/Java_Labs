package ru.nsu.salina.model.suppliers;

import ru.nsu.salina.model.Delay;
import ru.nsu.salina.model.storages.Storage;

public class Supplier<T> extends Thread{
    private final Storage<T> storage;
    private Delay delay;
    private volatile boolean isRunning;
    private java.util.function.Supplier<T> supp;
    public Supplier(Storage<T> storage, String productType, int ID, java.util.function.Supplier<T> sup) {
        super("Supplier (" + productType + ") №" + ID);
        this.storage = storage;
        this.supp = sup;
    }
    public void setDelay(Delay delay) {
        this.delay = delay;
    }
    public void shutdown() {isRunning = false;}
    @Override
    public void run() {
        isRunning = true;
        //System.out.println(this.isInterrupted());
        while(!Thread.currentThread().isInterrupted() && isRunning) {
            T item = createItem();
            storage.put(item);
            //System.out.println(" - Supplier (" + productType + ") №" + ID);

            try {
                Thread.sleep(delay.getDelay());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private T createItem() {
        return supp.get();
    }

}
