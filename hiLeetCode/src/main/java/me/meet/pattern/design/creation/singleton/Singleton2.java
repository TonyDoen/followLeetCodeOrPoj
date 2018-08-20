package me.meet.pattern.design.creation.singleton;

public class Singleton2 {

    // 利用了classloader的机制来保证初始化instance时只有一个线程，所以也是线程安全的，同时没有性能损耗。
    private static class LazyHolder {
        private static final Singleton2 INSTANCE = new Singleton2();
    }

    private Singleton2() {
    }

    public static final Singleton2 getInstance() {
        return LazyHolder.INSTANCE;
    }
}