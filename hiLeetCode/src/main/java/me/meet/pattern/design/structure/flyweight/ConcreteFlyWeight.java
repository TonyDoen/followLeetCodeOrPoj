package me.meet.pattern.design.structure.flyweight;

import java.util.logging.Logger;

public class ConcreteFlyWeight implements FlyWeight {
    private static final Logger LOG = Logger.getLogger(ConcreteFlyWeight.class.getName());

    private String name;
    public ConcreteFlyWeight(String name) {
        this.name = name;
    }

    @Override
    public void action(String externalState) {
        LOG.info("name = " + this.name + ", outerState = " + externalState);
    }

}