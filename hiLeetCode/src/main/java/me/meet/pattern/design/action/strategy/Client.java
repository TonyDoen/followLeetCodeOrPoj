package me.meet.pattern.design.action.strategy;

public class Client {

    /**
     * url: http://www.jasongj.com/design_pattern/strategy/
     *
     * 策略模式类图
     *  ___________________            ________________________
     * |       Context     |          |       <<Strategy>>     |
     * |-------------------|          |------------------------|
     * |-strategy:Strategy |<>------->|+strategy(String input) |
     * |-------------------|          |________________________|
     * |+action()          |                       /\
     * |___________________|              _  _  _ _|_ _ _ _ _
     *                                   |                   |
     *                  _________________|_____         _____|_________________
     *                 |  ConcreteStrategyA    |       | ConcreteStrategyA     |
     *                 |-----------------------|       |-----------------------|
     *                 |                       |       |                       |
     *                 |_______________________|       |_______________________|
     *                 |+strategy(String input)|       |+strategy(String input)|
     *                 |_______________________|       |_______________________|
     *
     * 策略模式角色划分
     * 1. Strategy         策略接口或者（抽象策略类），定义策略执行接口
     * 2. ConcreteStrategy 具体策略类
     * 3. Context          上下文类，持有具体策略类的实例，并负责调用相关的算法
     *
     * 策略模式分析
     *
     * 策略模式优点
     * 1. 策略模式提供了对“开闭原则”的完美支持，用户可以在不修改原有系统的基础上选择算法（策略），并且可以灵活地增加新的算法（策略）。
     * 2. 策略模式通过Context类提供了管理具体策略类（算法族）的办法。
     * 3. 结合简单工厂模式和Annotation，策略模式可以方便的在不修改客户端代码的前提下切换算法（策略）。
     *
     * 策略模式缺点
     * 1. 传统的策略模式实现方式中，客户端必须知道所有的具体策略类，并须自行显示决定使用哪一个策略类。但通过本文介绍的通过和Annotation和简单工厂模式结合，可以有效避免该问题
     * 2. 如果使用不当，策略模式可能创建很多具体策略类的实例，但可以通过使用上文《Java设计模式（十一） 享元模式》介绍的享元模式有效减少对象的数量。
     *
     */

    /**
     * SimpleContextClient
     * 简单的策略模式的应用
     */
    private static void testSimpleContextClient() {
        Strategy strategy = new ConcreteStrategyA();
        SimpleContext context = new SimpleContext(strategy);
        context.action("Hello, world");
    }

    /**
     * 使用 简单工厂 + 反射 + 简单的策略模式
     */
    private static void testSimpleFactoryContext() {
        SimpleFactoryContext context = new SimpleFactoryContext();
        context.action("Hello, world");
    }

    public static void main(String[] args) {
        // 简单的策略模式
        testSimpleContextClient();
        // 使用 简单工厂 + 反射 + 简单的策略模式
        testSimpleFactoryContext();
    }
}
