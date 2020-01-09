package me.meet.pattern.design.structure.bridge;


import me.meet.pattern.design.structure.adapter.Adapter;
import me.meet.pattern.design.structure.adapter.ConcreteTarget;
import me.meet.pattern.design.structure.adapter.ITarget;

public class Client {
    /**
     * 不必要的继承导致类爆炸
     * 汽车可按品牌分（本例中只考虑BMT，BenZ），也可按手动档、自动档、手自一体来分。
     * 如果对于每一种车都实现一个具体类，则一共要实现3*3=9个类。
     *
     *
     *
     * 桥接模式类图
     *
     *               ___________________                     ________________________
     *              |   AbstractCar     |                   |       Transmission     |
     *              |-------------------|                   |------------------------|
     *              |-strategy:Strategy |<>---------------->|+strategy(String input) |
     *              |-------------------|                   |------------------------|
     *              |+run(): void       |                   |+gear(): void           |
     *              |___________________|                   |________________________|
     *                       /\                                               /\
     *                       |                                                |
     *        _______________|____________                     _______________|_______
     *   ____|______                 ____|______         _____|________          _____|________
     *  |  BMWCar   |               |  BenZCar  |       | Auto         |        | Auto         |
     *  |-----------|               |-----------|       |--------------|        |--------------|
     *  |           |               |           |       |              |        |              |
     *  |-----------|               |-----------|       |--------------|        |--------------|
     *  |+run():void|               |+run():void|       |+gear(): void |        |+gear(): void |
     *  |___________|               |___________|       |______________|        |______________|
     *
     *
     * 桥接模式（Bridge Pattern），将抽象部分与它的实现部分分离，使它们都可以独立地变化。
     * 更容易理解的表述是：实现系统可从多种维度分类，桥接模式将各维度抽象出来，各维度独立变化，之后可通过聚合，将各维度组合起来，减少了各维度间的耦合。
     *
     * // 从上图可以看到，对于每种组合都需要创建一个具体类，如果有N个维度，每个维度有M种变化，则需要MN个具体类，类非常多，并且非常多的重复功能。
     * // 如果某一维度，如Transmission多一种可能，比如手自一体档（AMT），则需要增加3个类，BMWAMT，BenZAMT，LandRoverAMT。
     *
     * 从上图可知，当把每个维度拆分开来，只需要M*N个类，并且由于每个维度独立变化，基本不会出现重复代码。
     * 此时如果增加手自一体档，只需要增加一个AMT类即可
     *
     *
     * 适配器模式角色划分:
     * 目标接口，如上图中的ITarget
     * 具体目标实现，如ConcreteTarget
     * 适配器，Adapter
     * 待适配类，Adaptee
     *
     *
     * 适配器模式适用场景:
     * 1. 调用双方接口不一致且都不容易修改时，可以使用适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作
     * 2. 多个组件功能类似，但接口不统一且可能会经常切换时，可使用适配器模式，使得客户端可以以统一的接口使用它们
     *
     *
     * 适配器模式优点:
     * 1. 客户端可以以统一的方式使用ConcreteTarget和Adaptee
     * 2. 适配器负责适配过程，而不需要修改待适配类，其它直接依赖于待适配类的调用方不受适配过程的影响
     * 3. 可以为不同的目标接口实现不同的适配器，而不需要修改待适配类，符合开放-关闭原则
     *
     * 组合模式缺点
     *
     *
     * 已遵循的原则:
     * 依赖倒置原则
     * 迪米特法则
     * 里氏替换原则
     * 接口隔离原则
     * 单一职责原则
     * 开闭原则
     *
     *
     * 适配器模式可将一个类的接口转换成调用方希望的另一个接口。这种需求往往发生在后期维护阶段，因此有观点认为适配器模式只是前期系统接口设计缺乏的一种弥补。从实际工程来看，并不完全这样，有时不同产商的功能类似但接口很难完全一样，而为了系统使用方式的一致性，也会用到适配器模式。
     */

    public static void main(String[] args) {
        Transmission auto = new Auto();
        AbstractCar bmw = new BMWCar();
        bmw.setTransmission(auto);
        bmw.run();


        Transmission manual = new Manual();
        AbstractCar benz = new BenZCar();
        benz.setTransmission(manual);
        benz.run();
    }
}
