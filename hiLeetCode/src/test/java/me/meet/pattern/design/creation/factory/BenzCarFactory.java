package me.meet.pattern.design.creation.factory;

import java.util.logging.Logger;

public class BenzCarFactory implements CarFactory {
    private static final Logger LOG = Logger.getLogger(BenzCarFactory.class.getName());

    /**
     * 简单工厂模式（Simple Factory Pattern）又叫静态工厂方法模式（Static FactoryMethod Pattern）有如下缺点，而工厂方法模式可以解决这些问题
     *
     * 由于工厂类集中了所有实例的创建逻辑，这就直接导致一旦这个工厂出了问题，所有的客户端都会受到牵连。
     * 由于简单工厂模式的产品是基于一个共同的抽象类或者接口，这样一来，产品的种类增加的时候，即有不同的产品接口或者抽象类的时候，工厂类就需要判断何时创建何种接口的产品，这就和创建何种种类的产品相互混淆在了一起，违背了单一职责原则，导致系统丧失灵活性和可维护性。
     * 简单工厂模式违背了“开放-关闭原则”，因为当我们新增加一个产品的时候必须修改工厂类，相应的工厂类就需要重新编译一遍。
     * 简单工厂模式由于使用了静态工厂方法，造成工厂角色无法形成基于继承的等级结构。
     *
     *
     * 工厂方法模式（Factory Method Pattern）又称为工厂模式，也叫多态工厂模式或者虚拟构造器模式。在工厂方法模式中，工厂父类定义创建产品对象的公共接口，具体的工厂子类负责创建具体的产品对象。每一个工厂子类负责创建一种具体产品。
     *
     * 工厂方法模式角色划分
     * 抽象产品（或者产品接口），如 Car
     * 具体产品，              如 BMWCar, BenzCar
     * 抽象工厂（或者工厂接口），如 CarFactory
     * 具体工厂，              如 BMWCarFactory, BenzCarFactory
     *
     **/


    public static void main(String[] args) {
        CarFactory carFactory = new BenzCarFactory();
        Car car = carFactory.newCar();
        car.drive();
    }

    public Car newCar() {
        return new BenzCar();
    }
}
