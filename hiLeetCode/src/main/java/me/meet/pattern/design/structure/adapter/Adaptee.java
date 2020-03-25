package me.meet.pattern.design.structure.adapter;

import me.meet.pattern.design.structure.composite.Department;

import java.util.logging.Logger;

public class Adaptee { // 待适配类，其接口名为onRequest，而非目标接口request
    private static final Logger LOGGER = Logger.getLogger(Department.class.getName());

    public void onRequest() {
        LOGGER.info("Adaptee.onRequest()");
    }

}