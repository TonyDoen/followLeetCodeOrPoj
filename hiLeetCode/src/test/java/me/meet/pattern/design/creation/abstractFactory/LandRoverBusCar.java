package me.meet.pattern.design.creation.abstractFactory;

import java.util.logging.Logger;

public class LandRoverBusCar implements Car {
    private static final Logger LOG = Logger.getLogger(LandRoverBusCar.class.getName());

    public void drive() {
        LOG.info("LandRoverBusCar drive now....");
    }

    public void drive(String driverName) {
        LOG.info(driverName + "is driving LandRoverBusCar now....");
    }
}
