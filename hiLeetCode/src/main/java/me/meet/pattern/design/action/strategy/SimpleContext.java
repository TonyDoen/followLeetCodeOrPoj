package me.meet.pattern.design.action.strategy;

import java.util.logging.Logger;

public class SimpleContext {
    private static final Logger LOG = Logger.getLogger(ConcreteStrategyA.class.getName());

    private Strategy strategy;

    public SimpleContext(Strategy strategy) {
        this.strategy = strategy;
    }

    public void action(String input) {
        strategy.strategy(input);
    }

}