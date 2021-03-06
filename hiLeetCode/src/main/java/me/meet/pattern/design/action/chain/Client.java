package me.meet.pattern.design.action.chain;

public class Client {
    /**
     * 什么是链
     * 1. 链是一系列节点的集合。
     * 2. 链的各节点可灵活拆分再重组。
     *
     * 职责链模式
     * 1. 使多个对象都有机会处理请求，从而避免请求的发送者和接受者之间的耦合关系，
     * 2. 将这个对象连成一条链，并沿着这条链传递该请求，直到有一个对象处理他为止。
     *
     * 职责链模式的角色
     * 1. 抽象处理者角色(Handler)：定义出一个处理请求的接口。如果需要，接口可以定义 出一个方法以设定和返回对下家的引用。这个角色通常由一个Java抽象类或者Java接口实现。
     * 2. 具体处理者角色(ConcreteHandler)：具体处理者接到请求后，可以选择将请求处理掉，或者将请求传给下家。由于具体处理者持有对下家的引用，因此，如果需要，具体处理者可以访问下家。
     *
     *
     * 职责链灵活在哪
     * 1. 改变内部的传递规则
     * 在内部，项目经理完全可以跳过人事部到那一关直接找到总经理。
     * 每个人都可以去动态地指定他的继任者。
     *
     * 2. 可以从职责链任何一关开始。
     * 如果项目经理不在，可以直接去找部门经理，责任链还会继续，没有影响。
     *
     * 3.用与不用的区别
     * 不用职责链的结构，我们需要和公司中的每一个层级都发生耦合关系。
     * 如果反映在代码上即使我们需要在一个类中去写上很多丑陋的if….else语句。
     * 如果用了职责链，相当于我们面对的是一个黑箱，我们只需要认识其中的一个部门，然后让黑箱内部去负责传递就好了
     *
     * 纯的与不纯的责任链模式
     * 一个纯的责任链模式要求一个具体的处理者对象只能在两个行为中选择一个：一是承担责任，二是把责任推给下家。不允许出现某一个具体处理者对象在承担了一部分责任后又 把责任向下传的情况。
     *
     * 在一个纯的责任链模式里面，一个请求必须被某一个处理者对象所接收；
     * 在一个不纯的责任链模式里面，一个请求可以最终不被任何接收端对象所接收。
     *
     * 纯的责任链模式的实际例子很难找到，一般看到的例子均是不纯的责任链模式的实现。
     *
     */

    private static void testHandler() {
        //组装责任链
        Handler handler1 = new ConcreteHandler();
        Handler handler2 = new ConcreteHandler();
        handler1.setSuccessor(handler2);
        //提交请求
        handler1.handleRequest();
    }
    public static void main(String[] args) {
        testHandler();
    }
}
