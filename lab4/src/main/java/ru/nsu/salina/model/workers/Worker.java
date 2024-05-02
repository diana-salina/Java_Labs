package ru.nsu.salina.model.workers;

import ru.nsu.salina.model.Delay;
import ru.nsu.salina.model.car.Car;
import ru.nsu.salina.model.car.parts.Accessory;
import ru.nsu.salina.model.car.parts.Body;
import ru.nsu.salina.model.car.parts.Engine;
import ru.nsu.salina.model.storages.Storage;

public class Worker implements Runnable{
    private final Storage<Car> carStorage;
    private final Storage<Engine> engineStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Body> bodyStorage;
    private Delay delay;

    public Worker(Storage<Car> carStorage, Storage<Engine> engineStorage,
                  Storage<Accessory> accessoryStorage, Storage<Body> bodyStorage, Delay delay) {
        this.carStorage = carStorage;
        this.engineStorage = engineStorage;
        this.accessoryStorage = accessoryStorage;
        this.bodyStorage = bodyStorage;
        this.delay = delay;
    }
    public void setDelay(Delay delay) {
        this.delay = delay;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Engine engine = engineStorage.take();
            Accessory accessory = accessoryStorage.take();
            Body body = bodyStorage.take();
            Car newCar = new Car(engine, body, accessory);
            //System.out.println("worker");
            carStorage.put(newCar);

            try {
                Thread.sleep(delay.getDelay());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
