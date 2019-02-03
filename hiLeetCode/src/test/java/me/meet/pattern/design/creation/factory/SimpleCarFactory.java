package me.meet.pattern.design.creation.factory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class SimpleCarFactory {
    private static final Logger LOG = Logger.getLogger(SimpleCarFactory.class.getName());

    /**
     * 简单工厂模式（Simple Factory Pattern）又叫静态工厂方法模式（Static FactoryMethod Pattern）。专门定义一个类（如上文中的CarFactory1、CarFactory2、CarFactory3）来负责创建其它类的实例，由它来决定实例化哪个具体类，从而避免了在客户端代码中显式指定，实现了解耦。该类由于可以创建同一抽象类（或接口）下的不同子类对象，就像一个工厂一样，因此被称为工厂类。
     *
     * 简单工厂模式角色划分
     * 工厂角色：这是简单工厂模式的核心，由它负责创建所有的类的内部逻辑。当然工厂类必须能够被外界调用，创建所需要的产品对象。一般而言，工厂类提供一个静态方法，外部程序通过该方法创建所需对象。
     * 抽象产品角色(如上文中的Car)：简单工厂模式所创建的是所有对象的父类。注意，这里的父类可以是接口也可以是抽象类，它负责描述所创建实例共有的公共接口。
     * 具体产品角色（如上文中的BMWCar，BenzCar，LandRoverCar）：简单工厂所创建的具体实例对象，这些具体的产品往往都拥有共同的父类。
     *
     *
     *
     *
     *
     * 简单工厂模式与OOP原则
     * 已遵循的原则
     * 依赖倒置原则
     * 迪米特法则
     * 里氏替换原则
     * 接口隔离原则
     *
     * 未遵循的原则
     * 开闭原则（如上文所述，利用配置文件+反射或者注解可以避免这一点）
     * 单一职责原则（工厂类即要负责逻辑判断又要负责实例创建）
     *
     *
     *
     * 简单工厂模式优点
     * 工厂类是整个简单工厂模式的关键所在。它包含必要的判断逻辑，能够根据外界给定的信息（配置，或者参数），决定究竟应该创建哪个具体类的对象。用户在使用时可以直接根据工厂类去创建所需的实例，而无需了解这些对象是如何创建以及如何组织的。有利于整个软件体系结构的优化。
     * 通过引入配置文件和反射，可以在不修改任何客户端代码的情况下更换和增加新的具体产品类，在一定程度上提高了系统的灵活性（如CarFactory2）。
     * 客户端无须知道所创建的具体产品类的类名，只需要知道具体产品类所对应的参数即可，对于一些复杂的类名，通过简单工厂模式可以减少使用者的记忆量（如CarFactory3）。
     *
     * 简单工厂模式缺点
     * 由于工厂类集中了所有实例的创建逻辑，这就直接导致一旦这个工厂出了问题，所有的客户端都会受到牵连。
     * 由于简单工厂模式的产品是基于一个共同的抽象类或者接口，这样一来，产品的种类增加的时候，即有不同的产品接口或者抽象类的时候，工厂类就需要判断何时创建何种接口的产品，这就和创建何种种类的产品相互混淆在了一起，违背了单一职责原则，导致系统丧失灵活性和可维护性。
     * 正如上文提到的，一般情况下（如CarFactory1），简单工厂模式违背了“开放-关闭原则”，因为当我们新增加一个产品的时候必须修改工厂类，相应的工厂类就需要重新编译一遍。但这一点可以利用反射（CarFactory3在本质上也是利用反射）在一定程度上解决（如CarFactory2）。
     * 使用反射可以使简单工厂在一定条件下满足“开放-关闭原则”，但这仅限于产品类的构造及初始化相同的场景。对于各产品实例化或者初始化不同的场景，很难利用反射满足“开放-关闭”原则。
     * 简单工厂模式由于使用了静态工厂方法，造成工厂角色无法形成基于继承的等级结构。这一点笔者持保留态度，因为继承不是目的，如果没有这样的需求，这一点完全不算缺点，例如JDBC的DriverManager。
     */
    public static Car newCar() {
        String filePath = (ClassLoader.getSystemResource("") + "/settings.properties").substring(5); // file:
        String key = "car.name";
        Properties props = new Properties();

        InputStream in = null;
        String value = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            in.close();

            value = props.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ("BMW".equals(value)) {
            return new BMWCar();
        } else if ("Benz".equals(value)) {
            return new BenzCar();
        } else {
            class DefaultCar implements Car {
                public void drive() {
                    LOG.info("DefaultCar drive now....");
                }

                public void drive(String driverName) {
                    LOG.info(driverName + "is driving DefaultCar now....");
                }
            }
            return new DefaultCar();
        }
    }

    //    public static void main(String[] args) {
    //        Car car = SimpleCarFactory.newCar();
    //        car.drive();
    //    }
}
