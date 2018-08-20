package me.meet.pattern.design.structure.decorator;

import java.util.logging.Logger;

public class ConcreteSubject implements ISubject {
    //  private static final Logger LOG = LoggerFactory.getLogger(ConcreteSubject.class);
    private static final Logger LOG = Logger.getLogger(ConcreteSubject.class.getName());

    @Override
    public void action() {
        LOG.info("ConcreteSubject action()");
    }

}