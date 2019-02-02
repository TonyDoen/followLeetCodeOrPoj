package me.meet.pattern.design.action.strategy;

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
 * Strategy 策略接口或者（抽象策略类），定义策略执行接口
 * ConcreteStrategy 具体策略类
 * Context 上下文类，持有具体策略类的实例，并负责调用相关的算法
 *
 */
public interface Strategy {
    void strategy(String input);
}