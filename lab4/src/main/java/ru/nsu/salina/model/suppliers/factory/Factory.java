package ru.nsu.salina.model.suppliers.factory;

import ru.nsu.salina.model.car.parts.Accessory;
import ru.nsu.salina.model.car.parts.Body;
import ru.nsu.salina.model.car.parts.Engine;

public class Factory<T> {
    String productType;
    public Factory(String productType) {
        this.productType = productType;
    }
    public T create() {
        try {
            switch (productType) {
                case "Accessory":
                    return (T) new Accessory();
                case "Body":
                    return (T) new Body();
                case "Engine":
                    return (T) new Engine();
            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
        return null
    }
}
