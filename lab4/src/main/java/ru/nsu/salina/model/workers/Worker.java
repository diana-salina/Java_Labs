package ru.nsu.salina.model.workers;

import ru.nsu.salina.model.Delay;
import ru.nsu.salina.model.car.Car;
import ru.nsu.salina.model.car.parts.Accessory;
import ru.nsu.salina.model.car.parts.Body;
import ru.nsu.salina.model.car.parts.Engine;
import ru.nsu.salina.model.storages.Storage;

public class Worker extends Thread{
    private final Storage<Car> carStorage;
    private final Storage<Engine> engineStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Body> bodyStorage;
    private Delay delay;

    public Worker(Storage<Car> carStorage, Storage<Engine> engineStorage,
                  Storage<Accessory> accessoryStorage, Storage<Body> bodyStorage) {
        this.carStorage = carStorage;
        this.engineStorage = engineStorage;
        this.accessoryStorage = accessoryStorage;
        this.bodyStorage = bodyStorage;
    }
    public void setDelay(Delay delay) {
        this.delay = delay;
    }
    @Override
    public void run() {
        while (this.isAlive()) {
            Engine engine = engineStorage.take();
            Accessory accessory = accessoryStorage.take();
            Body body = bodyStorage.take();
            Car newCar = new Car(engine, body, accessory);
            carStorage.put(newCar);

            try {
                Thread.sleep(delay.getDelay());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
