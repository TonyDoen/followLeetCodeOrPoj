package me.meet.pattern.design.structure.adapter;


import java.util.logging.Logger;

public class ConcreteTarget implements ITarget { // 目标接口实现
    private static final java.util.logging.Logger LOG = Logger.getLogger(ConcreteTarget.class.getName());

    @Override
    public void request() {
        LOG.info("ConcreteTarget.request()");
    }
}