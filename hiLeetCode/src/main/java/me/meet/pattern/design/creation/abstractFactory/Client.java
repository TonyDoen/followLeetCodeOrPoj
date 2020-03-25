package me.meet.pattern.design.creation.abstractFactory;

public class Client {
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
     * 1. 抽象产品（或者产品接口），如上文类图中的IUserDao，IRoleDao，IProductDao
     * 2. 具体产品，如PostgreSQLProductDao
     * 3. 抽象工厂（或者工厂接口），如IFactory
     * 4. 具体工厂，如果MySQLFactory
     * 5. 产品族，如Oracle产品族，包含OracleUserDao，OracleRoleDao，OracleProductDao
     *
     *
     * 抽象工厂模式角色划分
     * 1. 抽象产品（或者产品接口） 如                  Car
     * 2. 具体产品，            如                  BenzCar, BMWCar, LandRoverBusCar, LandRoverMiniCar, LandRoverSUVCar
     * 3. 产品族，              如(LandRover产品族)  LandRoverBusCar, LandRoverMiniCar, LandRoverSUVCar
     *
     * 4. 抽象工厂（或者工厂接口），   如 CarFamilyFactory
     * 5. 具体工厂，                如 LandRoverCarFamilyFactory
     *
     *
     * 抽象工厂模式优点
     * 1. 因为每个具体工厂类只负责创建产品，没有简单工厂中的逻辑判断，因此符合单一职责原则。
     * 2. 与简单工厂模式不同，抽象工厂并不使用静态工厂方法，可以形成基于继承的等级结构。
     * 3. 新增一个产品族（如上文类图中的MySQLUserDao，MySQLRoleDao，MySQLProductDao）时，只需要增加相应的具体产品和对应的具体工厂类即可。相比于简单工厂模式需要修改判断逻辑而言，抽象工厂模式更符合开-闭原则。
     *
     * 抽象工厂模式缺点
     * 1. 新增产品种类（如上文类图中的UserDao，RoleDao，ProductDao）时，需要修改工厂接口（或者抽象工厂）及所有具体工厂，此时不符合开-闭原则。抽象工厂模式对于新的产品族符合开-闭原则而对于新的产品种类不符合开-闭原则，这一特性也被称为开-闭原则的倾斜性。
     *
     * 已遵循的原则
     * 1. 依赖倒置原则（工厂构建产品的方法均返回产品接口而非具体产品，从而使客户端依赖于产品抽象而非具体）
     * 2. 迪米特法则
     * 3. 里氏替换原则
     * 4. 接口隔离原则
     * 5. 单一职责原则（每个工厂只负责创建自己的具体产品族，没有简单工厂中的逻辑判断）
     * 6. 开闭原则（增加新的产品族，不像简单工厂那样需要修改已有的工厂，而只需增加相应的具体工厂类）
     *
     * 未遵循的原则
     * 1. 开闭原则（虽然对新增产品族符合开-闭原则，但对新增产品种类不符合开-闭原则）
     *
     **/

    public static void main(String[] args) {
        CarFamilyFactory carFamilyFactory = new LandRoverCarFamilyFactory();
        Car suv = carFamilyFactory.newSUVCar();
        Car bus = carFamilyFactory.newBusCar();
        Car mini = carFamilyFactory.newMiniCar();

        suv.drive();
        bus.drive();
        mini.drive();
    }

}
