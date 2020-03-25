package me.meet.pattern.design.action.observer;

import java.util.logging.Logger;

public class SeniorEngineer implements ITalent {

    //  private static final Logger LOG = LoggerFactory.getLogger(Architect.class);
    private static final Logger LOG = Logger.getLogger(SeniorEngineer.class.getName());

    @Override
    public void newJob(String job) {
        LOG.info("SeniorEngineer get new position " + job);
    }
}