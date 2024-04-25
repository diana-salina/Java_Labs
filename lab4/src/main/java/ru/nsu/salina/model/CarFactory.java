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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class CarFactory {
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
            properties.load(CarFactory.class.getClassLoader()
                    .getResourceAsStream("configFile.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        createStorages();
        createThreads();
        carController = new CarController(carStorage, workers);
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
