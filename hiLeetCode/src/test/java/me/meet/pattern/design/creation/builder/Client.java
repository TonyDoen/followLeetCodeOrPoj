package me.meet.pattern.design.creation.builder;

public class Client {
    /**
     * 建造者模式：将一个复杂的对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
     *
     * 建造者模式的应用场景：
     * 1、当创建复杂对象的算法应该独立于该对象的组成部分以及它们的装配方式时。
     * 2、当构造过程必须允许被构造的对象有不同表示时。
     *
     *
     * 建造者模式的角色：
     * 1、Builder：         为创建一个产品对象的各个部件指定抽象接口。
     * 2、ConcreteBuilder： 实现Builder的接口以构造和装配该产品的各个部件，定义并明确它所创建的表示，并提供一个检索产品的接口。
     * 3、Director：        构造一个使用Builder接口的对象，指导构建过程。
     * 4、Product：         表示被构造的复杂对象。ConcreteBuilder创建该产品的内部表示并定义它的装配过程，包含定义组成部件的类，包括将这些部件装配成最终产品的接口。
     *
     *
     * 建造者模式的注意点：
     * 建造者模式在使用过程中可以演化出多种形式：
     * 如果具体的被建造对象只有一个的话，可以省略抽象的Builder和Director，让ConcreteBuilder自己扮演指导者和建造者双重角色，甚至ConcreteBuilder也可以放到Product里面实现。
     * 在《Effective Java》书中第二条，就提到“遇到多个构造器参数时要考虑用构建器”，其实这里的构建器就属于建造者模式，只是里面把四个角色都放到具体产品里面了。
     *
     */
    public static void main(String[] args) {
        PersonDirector pd = new PersonDirector();
        Person womanPerson = pd.constructPerson(new ManBuilder());
        Person manPerson = pd.constructPerson(new WomanBuilder());

    }
}
