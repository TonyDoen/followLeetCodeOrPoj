package me.meet.pattern.design.creation.factory;

import java.util.logging.Logger;

public class LandRoverMiniCar implements Car {
    private static final Logger LOG = Logger.getLogger(LandRoverMiniCar.class.getName());

    public void drive() {
        LOG.info("LandRoverMiniCar drive now....");
    }

    public void drive(String driverName) {
        LOG.info(driverName + "is driving LandRoverMiniCar now....");
    }
}
