package me.meet.pattern.design.creation.factory;

import java.util.logging.Logger;

public class BenzCar implements Car {
    private static final Logger LOG = Logger.getLogger(BenzCar.class.getName());

    public void drive() {
        LOG.info("BenzCar drive now....");
    }

    public void drive(String driverName) {
        LOG.info(driverName + "is driving BenzCar now....");
    }
}
