package me.meet.pattern.design.structure.flyweight;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class FlyWeightFactory {
    private static final Logger LOG = Logger.getLogger(ConcreteFlyWeight.class.getName());
    private static final ConcurrentHashMap<String, FlyWeight> allFlyWeight = new ConcurrentHashMap<>();

    public static FlyWeight getFlyWeight(String name) {
        final FlyWeight res = allFlyWeight.get(name);
        if (null == res) {
            LOG.info("Instance of name = " + name + " does not exist, creating it");
            final FlyWeight flyWeight = new ConcreteFlyWeight(name);
            LOG.info("Instance of name = " + name + " created");
            allFlyWeight.put(name, flyWeight);
            return flyWeight;
        }
        return res;
    }
}