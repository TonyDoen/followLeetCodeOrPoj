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

    /**
     * SimpleContextClient
     * 简单的策略模式的应用
     */
    public static void main(String[] args) {
        Strategy strategy = new ConcreteStrategyA();
        SimpleContext context = new SimpleContext(strategy);
        context.action("Hello, world");
    }
}