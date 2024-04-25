package ru.nsu.salina.model;

import ru.nsu.salina.model.car.Car;
import ru.nsu.salina.model.car.parts.Accessory;
import ru.nsu.salina.model.car.parts.Body;
import ru.nsu.salina.model.car.parts.Engine;
import ru.nsu.salina.model.dealers.Dealer;
import ru.nsu.salina.model.storages.Storage;
import ru.nsu.salina.model.suppliers.Supplier;
import ru.nsu.salina.model.workers.Worker;
import ru.nsu.salina.threadpool.ThreadPool;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;


public class CarFactory extends Thread{
    private LinkedList<Dealer> dealers;
    private ThreadPool workers;
    private CarController carController;
    private LinkedList<Supplier<Engine>> engineSuppliers;
    private LinkedList<Supplier<Accessory>> accessorySuppliers;
    private LinkedList<Supplier<Body>> bodySuppliers;
    private Storage<Car> carStorage;
    private Storage<Engine> engineStorage;
    private Storage<Body> bodyStorage;
    private Storage<Accessory> accessoryStorage;
    private static final Delay defaultDelay = new Delay(1);
    private static final Properties properties = new Properties();
    public CarFactory(String configFile) {
        dealers = new LinkedList<>();
        engineSuppliers = new LinkedList<>();
        accessorySuppliers = new LinkedList<>();
        bodySuppliers = new LinkedList<>();


        try {
            File file = new File(configFile);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            properties.load(new FileReader(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        createStorages();
        createThreads();
        carController = new CarController(carStorage, workers);
    }
    @Override
    public void run() {
        for (Dealer dealer : dealers) dealer.start();
        for (Supplier<Accessory> supplier : accessorySuppliers) supplier.start();
        for (Supplier<Body> supplier : bodySuppliers) supplier.start();
        for (Supplier<Engine> supplier : engineSuppliers) supplier.start();
        workers.start();
        carController.start();
    }
    public void closeThreads() {
        for (Dealer dealer : dealers) dealer.interrupt();
        for (Supplier<Accessory> supplier : accessorySuppliers) supplier.interrupt();
        for (Supplier<Body> supplier : bodySuppliers) supplier.interrupt();
        for (Supplier<Engine> supplier : engineSuppliers) supplier.interrupt();
        workers.interrupt();
        carController.interrupt();
    }

    private void startThreads() {
    }

    public synchronized void resetWorkersDelay(Delay delay) {
        workers.setDelay(delay);
        notifyAll();
    }
    public synchronized void resetDealersDelay(Delay delay) {
        for (Dealer dealer : dealers) {
            dealer.setDelay(delay);
        }
        notifyAll();
    }
    public synchronized void resetAccessorySuppliersDelay(Delay delay) {
        for (Supplier<Accessory> supplier : accessorySuppliers) {
            supplier.setDelay(delay);
        }
        notifyAll();
    }
    public synchronized void resetBodySuppliersDelay(Delay delay) {
        for (Supplier<Body> supplier : bodySuppliers) {
            supplier.setDelay(delay);
        }
        notifyAll();
    }
    public synchronized void resetEngineSuppliersDelay(Delay delay) {
        for (Supplier<Engine> supplier : engineSuppliers) {
            supplier.setDelay(delay);
        }
        notifyAll();
    }
    private void createThreads() {
        try {
            int accessorySuppliersCount = Integer.parseInt(properties.getProperty("AccessorySuppliers"));
            int bodySuppliersCount = Integer.parseInt(properties.getProperty("BodySuppliers"));
            int engineSuppliersCount = Integer.parseInt(properties.getProperty("EngineSuppliers"));
            int workersCount = Integer.parseInt(properties.getProperty("Workers"));
            int dealersCount = Integer.parseInt(properties.getProperty("Dealers"));

            createAccessorySuppliers(accessorySuppliersCount);
            createBodySuppliers(bodySuppliersCount);
            createEngineSuppliers(engineSuppliersCount);
            createWorkers(workersCount);
            createDealers(dealersCount);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private void createDealers(int dealersCount) {
        for (int i = 0; i < dealersCount; ++i) {
            dealers.add(new Dealer(carStorage));
            dealers.getLast().setDelay(defaultDelay);
        }
    }

    private void createWorkers(int workersCount) {
        workers = new ThreadPool(workersCount);
    }

    private void createEngineSuppliers(int engineSuppliersCount) {
        for (int i = 0; i < engineSuppliersCount; ++i) {
            engineSuppliers.add(new Supplier<>(engineStorage, "Engine"));
            engineSuppliers.getLast().setDelay(defaultDelay);
        }
    }

    private void createBodySuppliers(int bodySuppliersCount) {
        for (int i = 0; i < bodySuppliersCount; ++i) {
            bodySuppliers.add(new Supplier<>(bodyStorage, "Body"));
            bodySuppliers.getLast().setDelay(defaultDelay);
        }
    }

    private void createAccessorySuppliers(int accessorySuppliersCount) {
        for (int i = 0; i < accessorySuppliersCount; ++i) {
            accessorySuppliers.add(new Supplier<>(accessoryStorage, "Accessory"));
            accessorySuppliers.getLast().setDelay(defaultDelay);
        }
    }

    private void createStorages() {
        try {
            carStorage = new Storage<>(Integer.parseInt(properties.getProperty("CarStorageSize")));
            engineStorage = new Storage<>(Integer.parseInt(properties.getProperty("EngineStorageSize")));
            bodyStorage = new Storage<>(Integer.parseInt(properties.getProperty("BodyStorageSize")));
            accessoryStorage = new Storage<>(Integer.parseInt(properties.getProperty("AccessoryStorageSize")));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}
