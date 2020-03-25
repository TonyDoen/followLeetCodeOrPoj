package me.meet.pattern.design.action.observer;

import java.util.logging.Logger;

public class Architect implements ITalent {

    //  private static final Logger LOG = LoggerFactory.getLogger(Architect.class);
    private static final Logger LOG = Logger.getLogger(Architect.class.getName());

    @Override
    public void newJob(String job) {
        LOG.info("Architect get new position " + job);
    }
}