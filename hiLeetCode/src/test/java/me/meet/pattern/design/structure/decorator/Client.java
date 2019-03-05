package me.meet.pattern.design.structure.decorator;

public class Client {
    /**
     * 装饰模式类图                     _________________________________
     *                                |          ISubject              |
     *                                |--------------------------------|
     *  ___________________           |+action(): void                 |
     * |       Client      |          |                                |
     * |-------------------|          |                                |<------------------------
     * |                   |--------->|                                |                         |
     * |-------------------|          |________________________________|                         |
     * |                   |                       /\                                            |
     * |___________________|              _ _ _ _ _|_ _ _ _ _                                    |
     *                                   |                   |                                   |
     *             ______________________|_________       ___|____________________________       |
     *            |       ConcreteSubject          |     |       SubjectPostDecorator     |      |
     *            |--------------------------------|     |--------------------------------|      |
     *            |                                |     |-target: ISubject               |      |
     *            |________________________________|     |________________________________|      |
     *            |+action() :void                 |     |                                |      |
     *            |                                |     |-postAction(): void             |<>-----
     *            |                                |     |+action(): void                 |
     *            |                                |     |                                |
     *            |________________________________|     |________________________________|
     *
     */

    public static void main(String[] args) {
        ISubject target = new ConcreteSubject();
        ISubject preDecorator = new SubjectPreDecorator(target);
        ISubject postDecorator = new SubjectPostDecorator(preDecorator);
        postDecorator.action();
    }
}
