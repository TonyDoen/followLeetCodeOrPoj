package me.meet.pattern.design.action.strategy;

import java.util.logging.Logger;

public class ConcreteStrategyB implements Strategy {
//  private static final Logger LOG = LoggerFactory.getLogger(ConcreteStrategyB.class);
    private static final Logger LOG = Logger.getLogger(ConcreteStrategyB.class.getName());

    public void strategy(String input) {
        LOG.info(String.format("Strategy B for input : %s", input));
    }
}