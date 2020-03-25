package me.meet.pattern.design.action.observer;

import java.util.logging.Logger;

public class JuniorEngineer implements ITalent {

    //  private static final Logger LOG = LoggerFactory.getLogger(Architect.class);
    private static final Logger LOG = Logger.getLogger(JuniorEngineer.class.getName());

    @Override
    public void newJob(String job) {
        LOG.info("JuniorEngineer get new position " + job);
    }
}