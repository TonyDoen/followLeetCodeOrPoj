package me.meet.pattern.design.action.strategy;

import java.util.logging.Logger;

public class ConcreteStrategyA implements Strategy {
//  private static final Logger LOG = LoggerFactory.getLogger(ConcreteStrategyB.class);
    private static final Logger LOG = Logger.getLogger(ConcreteStrategyA.class.getName());

    public void strategy(String input) {
        LOG.info(String.format("Strategy A for input : %s", input));
    }
}