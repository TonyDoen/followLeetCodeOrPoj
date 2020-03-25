package me.meet.pattern.design.creation.factory;

import java.util.logging.Logger;

public class BMWCar implements Car {
    private static final Logger LOG = Logger.getLogger(BMWCar.class.getName());

    public void drive() {
        LOG.info("BMWCar drive now....");
    }

    public void drive(String driverName) {
        LOG.info(driverName + "is driving BMWCar now....");
    }
}
