package me.meet.pattern.design.structure.proxy;

public class Client {
    /**
     * 代理模式类图                     _________________________________
     *                                |          ISubject              |
     *                                |--------------------------------|
     *  ___________________           |+action(): void                 |
     * |       Client      |          |                                |
     * |-------------------|          |                                |
     * |                   |--------->|                                |
     * |-------------------|          |________________________________|
     * |                   |                       /\
     * |___________________|              _ _ _ _ _|_ _ _ _ _
     *                                   |                   |
     *             ______________________|_________       ___|____________________________
     *            |       ConcreteSubject          |     |       SubjectProxy             |
     *            |--------------------------------|     |--------------------------------|
     *            |                                |     |-target: ISubject               |
     *            |________________________________|     |________________________________|
     *            |+action() :void                 |     |-preAction(): void              |
     *            |                                |<--<>|-postAction(): void             |
     *            |                                |     |+action(): void                 |
     *            |                                |     |                                |
     *            |________________________________|     |________________________________|
     *
     * 代理模式（Proxy Pattern），为其它对象提供一种代理以控制对这个对象的访问。
     * 静态代理
     * 动态代理
     *
     * 从上述代码中可以看到，被代理对象由代理对象在编译时确定，并且代理对象可能限制对被代理对象的访问。
     * 调用方直接调用代理而不需要直接操作被代理对象甚至都不需要知道被代理对象的存在。同时，代理类可代理的具体被代理类是确定的，如本例中ProxySubject只可代理ConcreteSubject。
     */

    public static void main(String[] args) {
//        ISubject subject = new ProxySubject();

        // 静态代理
        ISubject target = new ConcreteSubject();

        // 代理对象,把目标对象传给代理对象,建立代理关系
        ISubject proxy = new ProxySubject(target);
        proxy.action();


        System.out.println("===================================================");
        // 动态代理
        ISubject dTarget = new ConcreteSubject();
        // 给目标对象，创建代理对象
        ISubject dProxy = (ISubject) new ProxyFactory(dTarget).getProxyInstance();
        dProxy.action();
    }
}
