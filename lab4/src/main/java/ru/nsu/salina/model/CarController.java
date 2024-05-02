package ru.nsu.salina.model;

import ru.nsu.salina.model.car.Car;
import ru.nsu.salina.model.car.parts.Accessory;
import ru.nsu.salina.model.car.parts.Body;
import ru.nsu.salina.model.car.parts.Engine;
import ru.nsu.salina.model.storages.Storage;
import ru.nsu.salina.model.workers.Worker;
import ru.nsu.salina.threadpool.ThreadPool;

public class CarController extends Thread{
    private final Storage<Car> carStorage;
    private final ThreadPool workersPool;

    private final Storage<Engine> engineStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Delay workerDelay;
    public CarController (Storage<Car> carStorage, ThreadPool workersPool,
                          Storage<Engine> engineStorage, Storage<Body> bodyStorage,
                          Storage<Accessory> accessoryStorage, Delay workerDelay) {
        super("CarController");
        this.carStorage = carStorage;
        this.workersPool = workersPool;
        this.engineStorage = engineStorage;
        this.bodyStorage = bodyStorage;
        this.accessoryStorage = accessoryStorage;
        this.workerDelay = workerDelay;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (carStorage) {
                while (workersPool.countTasksInQueue() >= carStorage.getAvailablePlaces()) {
                    try {
                        carStorage.wait();
                    } catch (InterruptedException ex) {
                        workersPool.shutdown();
                        ex.printStackTrace();
                    }
                }
                int necessityCount = carStorage.getAvailablePlaces() - workersPool.countTasksInQueue();
                for (int i = 0; i < necessityCount; ++i) {
                    workersPool.execute(new Worker(carStorage, engineStorage, accessoryStorage, bodyStorage, workerDelay));
                }
            }
        }
    }
}
