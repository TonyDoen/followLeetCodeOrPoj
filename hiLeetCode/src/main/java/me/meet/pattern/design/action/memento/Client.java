package me.meet.pattern.design.action.memento;

public class Client {
    /**
     * 主要目的是保存一个对象的某个状态，以便在适当的时候恢复对象，个人觉得叫备份模式更形象些，
     * 通俗的讲下：假设有原始类A，A中有各种属性，A可以决定需要备份的属性，备忘录类B是用来存储A的一些内部状态，类C呢，就是一个用来存储备忘录的，且只能存储，不能修改等操作。
     *
     *
     * Original 类是原始类，里面有需要保存的属性value及创建一个备忘录类，用来保存value值。
     * Memento  类是备忘录类，
     * Storage  类是存储备忘录的类，持有Memento类的实例，该模式很好理解。
     */
    private static void testMemento() {
        // 创建原始类
        Original orig = new Original("egg");

        // 创建备忘录
        Storage storage = new Storage(orig.createMemento());

        // 修改原始类的状态
        System.out.println("初始化状态为：" + orig.getValue());
        orig.setValue("niu");
        System.out.println("修改后的状态为：" + orig.getValue());

        // 回复原始类的状态
        orig.restoreMemento(storage.getMemento());
        System.out.println("恢复后的状态为：" + orig.getValue());
    }

    public static void main(String[] args) {
        testMemento();
    }
}
