package me.meet.pattern.design.structure.bridge;

import java.util.logging.Logger;

public class Auto extends Transmission {
    private static final Logger LOG = Logger.getLogger(Manual.class.getName());

    @Override
    public void gear() {
        LOG.info("Auto transmission");
    }
}