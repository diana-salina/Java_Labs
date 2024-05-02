package ru.nsu.salina.controller;

import ru.nsu.salina.model.CarFactory;
import ru.nsu.salina.model.Delay;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderController implements ChangeListener {
    private final int ID;
    private final CarFactory factory;

    public SliderController(CarFactory factory, int ID) {
        this.factory =factory;
        this.ID = ID;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int value = ((JSlider)e.getSource()).getValue();
        Delay delay = new Delay(value);
        switch(ID) {
            case 0:
                factory.resetDealersDelay(delay);
            case 1:
                factory.resetWorkersDelay(delay);
            case 2:
                factory.resetAccessorySuppliersDelay(delay);
            case 3:
                factory.resetBodySuppliersDelay(delay);
            case 4:
                factory.resetEngineSuppliersDelay(delay);
        }
    }
}
