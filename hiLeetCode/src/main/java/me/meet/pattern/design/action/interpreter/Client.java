package me.meet.pattern.design.action.interpreter;

public class Client {
    /**
     * 解释器模式（Interpreter）
     *
     * 解释器模式是我们暂时的最后一讲，一般主要应用在OOP开发中的编译器的开发中，所以适用面比较窄。
     *
     * Context类是一个上下文环境类，Plus和Minus分别是用来计算的实现，
     *
     *
     */
    public static void main(String[] args) {
        // 计算9+2-8的值
        int result = new Minus().interpret((new Context(new Plus().interpret(new Context(9, 2)), 8)));
        System.out.println(result);
    }
}
