package org.zhd.test;

import java.util.concurrent.locks.LockSupport;

public final class MultiThreadTest1 {
    private MultiThreadTest1() {}

    static class LockSupportOuch extends Thread {
        private static Object flag = new Object();
        public LockSupportOuch(String name) {
            super.setName(name);
        }

        public void run() {
            synchronized (flag) {
                System.out.println(getName());
                LockSupport.park();
            }
        }
    }

    private static void testLockSupportOuch() {
        final LockSupportOuch lo1 = new LockSupportOuch("work-1");
        final LockSupportOuch lo2 = new LockSupportOuch("work-2");

        lo1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lo2.start();

        LockSupport.unpark(lo1);
        LockSupport.unpark(lo2);

        try {
            lo1.join();
            lo2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testLockSupportOuch();
        System.out.println("end.");
    }

    /**
     * LockSupport
     *
     */
}
