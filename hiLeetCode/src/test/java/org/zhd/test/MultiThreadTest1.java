package org.zhd.test;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public final class MultiThreadTest1 {
    private MultiThreadTest1() {
    }

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

    /**
     * LockSupport
     */
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

    /**
     * JAVA 中断实现
     */
    private static void testInterrupt() {

        // 填充数组
        int len = 80000;
        final int[] array = new int[len];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(i + 1);
        }

        final Thread t = new Thread() {
            public void run() {
                try {
                    // 冒泡排序
                    for (int i = 0; i < array.length - 1; i++) {
                        for (int j = 0; j < array.length - i - 1; j++) {
                            if (array[j] < array[j + 1]) {
                                int temp = array[j];
                                array[j] = array[j + 1];
                                array[j + 1] = temp;
                            }
                        }
                    }

                    System.out.println((array));
                } catch (Error err) {
                    err.printStackTrace();
                }
                System.out.println("in thread t");
            }
        };

        t.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();

    }

    private static void testInterrupt1() {
        class MyThread extends Thread {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 500000; i++) {
                    if (this.interrupted()) {
                        System.out.println("should be stopped and exit");
                        break;
                    }
                    System.out.println("i=" + (i + 1));
                }
                System.out.println("this line is also executed. thread does not stopped"); // 尽管线程被中断,但并没有结束运行。这行代码还是会被执行
            }
        }

        MyThread thread = new MyThread();
        thread.start();
        try {
            Thread.sleep(20);//modify 2000 to 20
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();//请求中断MyThread线程
    }

    public static void main(String[] args) {
//        testLockSupportOuch();
//        testInterrupt();
        testInterrupt1();
        System.out.println("end.");
//        ThreadLocal
    }
}
