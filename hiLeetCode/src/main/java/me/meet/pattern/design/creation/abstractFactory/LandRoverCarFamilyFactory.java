package me.meet.pattern.design.creation.abstractFactory;


import java.util.logging.Logger;

public class LandRoverCarFamilyFactory implements CarFamilyFactory {
    private static final Logger LOG = Logger.getLogger(LandRoverCarFamilyFactory.class.getName());

    /**
     *
     * 在工厂方法模式中一种工厂只能创建一种具体产品。而在抽象工厂模式中一种具体工厂可以创建多个种类的具体产品。
     *
     *
     * 抽象工厂模式介绍
     * 抽象工厂模式（Abstract Factory Method Pattern）中，抽象工厂提供一系列创建多个抽象产品的接口，而具体的工厂负责实现具体的产品实例。抽象工厂模式与工厂方法模式最大的区别在于抽象工厂中每个工厂可以创建多个种类的产品。
     *
     *
     * 抽象工厂模式角色划分
     * 抽象产品（或者产品接口），如上文类图中的IUserDao，IRoleDao，IProductDao
     * 具体产品，如PostgreSQLProductDao
     * 抽象工厂（或者工厂接口），如IFactory
     * 具体工厂，如果MySQLFactory
     * 产品族，如Oracle产品族，包含OracleUserDao，OracleRoleDao，OracleProductDao
     *
     * 抽象工厂模式角色划分
     * 抽象产品（或者产品接口），如 Car
     * 具体产品，              如 LandRoverBusCar, LandRoverMiniCar, LandRoverSUVCar
     * 产品族，                如 LandRoverBusCar, LandRoverMiniCar, LandRoverSUVCar
     *
     * 抽象工厂（或者工厂接口），如 CarFamilyFactory
     * 具体工厂，              如 LandRoverCarFamilyFactory
     *
     *
     *
     *
     **/

    //    public static void main(String[] args) {
    //        CarFamilyFactory carFamilyFactory = new LandRoverCarFamilyFactory();
    //        Car suv = carFamilyFactory.newSUVCar();
    //        Car bus = carFamilyFactory.newBusCar();
    //        Car mini = carFamilyFactory.newMiniCar();
    //
    //        suv.drive();
    //        bus.drive();
    //        mini.drive();
    //    }

    public Car newSUVCar() {
        return new LandRoverSUVCar();
    }

    public Car newBusCar() {
        return new LandRoverBusCar();
    }

    public Car newMiniCar() {
        return new LandRoverMiniCar();
    }
}
