package me.meet.pattern.design.action.iterator.intrinsic;

public abstract class Aggregate {
    /**
     * 工厂方法，创建相应迭代子对象的接口
     */
    public abstract Iterator createIterator();
}