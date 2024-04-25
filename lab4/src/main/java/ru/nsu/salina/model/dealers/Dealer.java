package ru.nsu.salina.model.dealers;

import ru.nsu.salina.model.Delay;
import ru.nsu.salina.model.car.Car;
import ru.nsu.salina.model.storages.Storage;

public class Dealer extends Thread{
    private Storage<Car> carStorage;
    private Delay delay;

    public Dealer(Storage<Car> storage) {
        this.carStorage = storage;
    }
    public void setDelay(Delay delay) {
        this.delay = delay;
    }
    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            Car car = carStorage.take();

            synchronized (carStorage) {
                carStorage.notifyAll();
            }

            try {
                Thread.sleep(delay.getDelay());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
