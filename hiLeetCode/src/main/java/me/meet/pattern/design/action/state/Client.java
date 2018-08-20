package me.meet.pattern.design.action.state;

public class Client {
    /**
     *
     * 核心思想就是：当对象的状态改变时，同时改变其行为，很好理解！
     * 就拿QQ来说，有几种状态，在线、隐身、忙碌等，每个状态对应不同的操作，而且你的好友也能看到你的状态，
     * 所以，状态模式就两点：1、可以通过改变状态来获得不同的行为。2、你的好友能同时看到你的变化。
     *
     *
     *
     * State模式在实际使用中比较多，适合“状态”的切换。因为我们经常会使用If else if else 进行状态切换，如果针对状态的这样判断切换反复出现，我们就要联想到是否可以采取State模式了。
     * 这里要阐述的是"开关切换状态" 和" 一般的状态判断"是有一些区别的，" 一般的状态判断"也是有 if..elseif结构,
     *
     * if (which==1) state="hello";
     * else if (which==2) state="hi";
     * else if (which==3) state="bye"
     *
     * 这是一个 " 一般的状态判断”，state值的不同是根据which变量来决定的，which和state没有关系.如果改成:
     *
     * if (state.euqals("bye")) state="hello";
     * else if (state.euqals("hello")) state="hi";
     * else if (state.euqals("hi")) state="bye";
     *
     * 这就是 "开关切换状态”，是将state的状态从"hello"切换到”hi"，再切换到"”bye"，在切换到”hello"，好象一个旋转开关，这种状态改变就可以使用State模式了。
     *
     * 如果单纯有上面一种将"hello"-->"hi"-->"bye"-->"hello"这一个方向切换,也不一定需要使用State模式，因为State模式会建立很多子类，复杂化，但是如果又发生另外一个行为：将上面的切换方向反过来切换，或者需要任意切换，就需要State了。
     *
     * 使用策略模式重写上面例子
     * State需要两种类型实体参与:
     * 1.state manager 状态管理器 ,就是开关 ,如上面例子的Context实际就是一个state manager, 在state manager中有对状态的切换动作.
     * 2.用抽象类或接口实现的父类,不同状态就是继承这个父类的不同子类.
     *
     * 使用状态模式前，客户端外界需要介入改变状态，而状态改变的实现是琐碎或复杂的。
     * 使用状态模式后，客户端外界可以直接使用事件Event实现，根本不必关心该事件导致如何状态变化，这些是由状态机等内部实现。
     * 这是一种Event-condition-State，状态模式封装了condition-State部分。
     * 每个状态形成一个子类，每个状态只关心它的下一个可能状态，从而无形中形成了状态转换的规则。如果新的状态加入，只涉及它的前一个状态修改和定义。
     *
     * 状态模式的主要优点在于封装了转换规则，并枚举可能的状态，它将所有与某个状态有关的行为放到一个类中，并且可以方便地增加新的状态，只需要改变对象状态即可改变对象的行为，还可以让多个环境对象共享一个状态对象，从而减少系统中对象的个数；
     * 其缺点在于使用状态模式会增加系统类和对象的个数，且状态模式的结构与实现都较为复杂，如果使用不当将导致程序结构和代码的混乱，对于可以切换状态的状态模式不满足“开闭原则”的要求。
     *
     *
     *
     */

    private static void check2() {
        State state = new BlackState();
        Context context = new Context();
        context.setState(state);

        context.push();
        context.pull();
    }

    public static void main(String[] args) {
        StateCase1.check1();

        check2();
    }
}
