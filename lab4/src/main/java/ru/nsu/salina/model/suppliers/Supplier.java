package ru.nsu.salina.model.suppliers;

import ru.nsu.salina.model.Delay;
import ru.nsu.salina.model.storages.Storage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Supplier<T> extends Thread{
    private final Storage<T> storage;
    private final String productType;
    private Delay delay;
    public Supplier(Storage<T> storage, String productType) {
        this.storage = storage;
        this.productType = productType;
    }
    public void setDelay(Delay delay) {
        this.delay =delay;
    }
    @Override
    public void run() {
        while(isAlive()) {
            T item = createItem();
            storage.put(item);
            try {
                Thread.sleep(delay.getDelay());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private T createItem() {
        try {
            Class<T> tClass = (Class<T>) Class.forName(productType);
            Constructor<T> tConstructor = tClass.getDeclaredConstructor();
            return tConstructor.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            interrupt();
            ex.printStackTrace();
        }
        return null;
    }

}
