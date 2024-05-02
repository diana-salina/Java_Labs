package ru.nsu.salina.model.dealers;

import ru.nsu.salina.model.Delay;
import ru.nsu.salina.model.car.Car;
import ru.nsu.salina.model.storages.Storage;

public class Dealer extends Thread{
    private final Storage<Car> carStorage;
    private Delay delay;
    private int ID;

    public Dealer(Storage<Car> storage, int ID) {
        super("Dealer №" + ID);
        this.carStorage = storage;
        this.ID = ID;
    }
    public void setDelay(Delay delay) {
        this.delay = delay;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Car car = carStorage.take();
            System.out.println((System.currentTimeMillis() / 1000) + ": Dealer " + ID +
                    ": Auto " + car.getCarID() + car.getDetailsID());

            try {
                Thread.sleep(delay.getDelay());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
