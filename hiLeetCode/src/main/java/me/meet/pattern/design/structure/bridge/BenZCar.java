package me.meet.pattern.design.structure.bridge;

import java.util.logging.Logger;

public class BenZCar extends AbstractCar {
    private static final Logger LOG = Logger.getLogger(Manual.class.getName());

    @Override
    public void run() {
        gear.gear();
        LOG.info("BenZCar is running");
    }
}