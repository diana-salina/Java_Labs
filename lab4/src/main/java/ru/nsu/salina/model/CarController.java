package ru.nsu.salina.model;

import ru.nsu.salina.model.car.Car;
import ru.nsu.salina.model.storages.Storage;
import ru.nsu.salina.threadpool.ThreadPool;

public class CarController extends Thread{
    private Storage<Car> carStorage;
    private ThreadPool workersPool;
    public CarController (Storage<Car> carStorage, ThreadPool workersPool) {
        this.carStorage = carStorage;
        this.workersPool = workersPool;
    }
    @Override
    public void run() {

    }
}
