package ru.nsu.salina.model.suppliers;

import ru.nsu.salina.model.Delay;
import ru.nsu.salina.model.car.Car;
import ru.nsu.salina.model.storages.Storage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Supplier<T> extends Thread{
    private final Storage<T> storage;
    private final String productType;
    private Delay delay;
    private final int ID;
    public Supplier(Storage<T> storage, String productType, int ID) {
        super("Supplier (" + productType + ") №" + ID);
        this.storage = storage;
        this.productType = productType;
        this.ID = ID;
    }
    public void setDelay(Delay delay) {
        this.delay =delay;
    }
    @Override
    public void run() {

        //System.out.println(this.isInterrupted());
        while(!Thread.currentThread().isInterrupted()) {
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
        try {
            Class<T> tClass = (Class<T>) Class.forName("ru.nsu.salina.model.car.parts." + productType);
            Constructor<T> tConstructor = tClass.getDeclaredConstructor();
            return tConstructor.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Thread.currentThread().interrupt();
            ex.printStackTrace();
        }
        return null;
    }

}
