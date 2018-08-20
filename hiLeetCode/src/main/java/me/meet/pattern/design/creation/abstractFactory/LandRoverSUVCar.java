package me.meet.pattern.design.creation.abstractFactory;

import java.util.logging.Logger;

public class LandRoverSUVCar implements Car {
    private static final Logger LOG = Logger.getLogger(LandRoverSUVCar.class.getName());

    public void drive() {
        LOG.info("LandRoverSUVCar drive now....");
    }

    public void drive(String driverName) {
        LOG.info(driverName + "is driving LandRoverSUVCar now....");
    }
}
