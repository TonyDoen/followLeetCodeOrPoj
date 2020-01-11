package me.meet.pattern.design.creation.singleton;

public class Client {
    /**
     * url: https://blog.csdn.net/jason0539/article/details/23297037
     * java中单例模式是一种常见的设计模式，单例模式的写法有好几种，
     * 这里主要介绍三种：懒汉式单例、饿汉式单例
     *
     * 单例模式有以下特点：
     * 1、单例类只能有一个实例。
     * 2、单例类必须自己创建自己的唯一实例。
     * 3、单例类必须给所有其他对象提供这一实例。
     *
     * 单例模式的应用场景：
     * 单例模式确保某个类只有一个实例，而且自行实例化并向整个系统提供这个实例。
     * 在计算机系统中，线程池、缓存、日志对象、对话框、打印机、显卡的驱动程序对象常被设计成单例。
     * 这些应用都或多或少具有资源管理器的功能。
     * 每台计算机可以有若干个打印机，但只能有一个Printer Spooler，以避免两个打印作业同时输出到打印机中。每台计算机可以有若干通信端口，系统应当集中管理这些通信端口，以避免一个通信端口同时被两个请求同时调用。
     * 总之，选择单例模式就是为了避免不一致状态，避免政出多头。
     *
     * 单例模式的注意点：
     * 1. Singleton通过将构造方法限定为private避免了类在外部被实例化，在同一个虚拟机范围内，Singleton的唯一实例只能通过getInstance()方法访问。
     *    事实上，通过Java反射机制是能够实例化构造方法为private的类的，那基本上会使所有的Java单例实现失效。此问题在此处不做讨论，姑且掩耳盗铃地认为反射机制不存在。
     * 2. 饿汉式和懒汉式区别, 从名字上来说，饿汉和懒汉，饿汉就是类一旦加载，就把单例初始化完成，保证getInstance的时候，单例是已经存在的了，而懒汉比较懒，只有当调用getInstance的时候，才回去初始化这个单例。
     * 3. 单例模式为一个面向对象的应用程序提供了对象惟一的访问点，不管它实现何种功能，整个应用程序都会同享一个实例对象。
     *
     */
    public static void main(String[] args) {
        Singleton1 s0= Singleton1.getInstance();
        Singleton1 s1 = Singleton1.getInstance();
        System.out.println(s0 == s1);

        Singleton2 s2 = Singleton2.getInstance();

    }
}
