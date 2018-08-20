package me.meet.labuladong._2.LCNOT;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public final class _00007 {
    private _00007() {
    }
    /*
     * 队列是一种先进先出的数据结构，
     * 栈是一种先进后出的数据结构，
     *
     * 形象一点就是这样：
     *    队尾(入队/input)                     栈顶(入栈/出栈)(input/output)
     *    |  |                               |  |
     *    |  |                               |  |
     *    |  |                               |  |
     *    |  |                               |  |
     *    |  |                               |  |
     *    |  |                               |__|
     *    队头(出队/output)
     *
     * 这两种数据结构底层其实都是数组或者链表实现的，
     * 只是 API 限定了它们的特性，那么今天就来看看如何使用「栈」的特性来实现一个「队列」，如何用「队列」实现一个「栈」。
     *
     *
     * 至此，就用栈结构实现了一个队列，核心思想是利用两个栈互相配合。
     * 值得一提的是，这几个操作的时间复杂度是多少呢？
     * 其他操作都是 O(1)，
     *
     * 有点意思的是peek操作，调用它时可能触发while循环，这样的话时间复杂度是 O(N)，但是大部分情况下while循环不会被触发，时间复杂度是 O(1)。
     * 由于pop操作调用了peek，它的时间复杂度和peek相同。
     *
     * 像这种情况，可以说它们的最坏时间复杂度是 O(N)，因为包含while循环，可能需要从s1往s2搬移元素。
     * 但是它们的均摊时间复杂度是 O(1)，
     *
     * 这个要这么理解：对于一个元素，最多只可能被搬运一次，也就是说peek操作平均到每个元素的时间复杂度是 O(1)。
     */

    /**
     * 一、用栈实现队列
     */
    public static class MyQueue<T> {
        private LinkedList<T> st1, st2;

        public MyQueue() {
            st1 = new LinkedList<>(); // Stack
            st2 = new LinkedList<>();
        }

        // 添加元素到队尾
        public void push(T x) {
            st1.push(x); // stack's push
        }

        // 删除队头的元素并返回
        public T pop() {
//            peek(); // 先调用 peek 保证 st2 非空

            // 保证 st2 非空
            if (st2.isEmpty()) {
                if (st1.isEmpty()) { // st1 空
                    return null;
                }

                while (!st1.isEmpty()) {
                    st2.push(st1.pop());
                }
            }

            return st2.pop(); // stack's pop
        }

//        // 返回队头元素
//        public T peek() {
//            if (st2.isEmpty()) {
//                while (!st1.isEmpty()) {
//                    st2.push(st1.pop());
//                }
//            }
//            return st2.peek();
//        }

        // 判断队列是否为空
        public boolean empty() {
            return st1.isEmpty() && st2.isEmpty();
        }
    }

    private static void testMyQueue() {
        MyQueue<Integer> queue = new MyQueue<>();
        int n = 20;
        for (int i = 0; i < n; i++) {
            queue.push(i);
        }

        Integer val = null;
        while (null != (val = queue.pop())) {
            System.out.print(val + ", ");
        }
        System.out.println();
    }

    /**
     * 二、用队列实现栈
     *
     * 如果说双栈实现队列比较巧妙，那么用队列实现栈就比较简单粗暴了，只需要一个队列作为底层数据结构。首先看下栈的 API：
     * 先说pushAPI，直接将元素加入队列，同时记录队尾元素，因为队尾元素相当于栈顶元素，如果要top查看栈顶元素的话可以直接返回：
     */
    public static class MyStack<T> {
        Queue<T> q = new LinkedList<>();
        private T topEle = null;

        // 添加元素到栈顶
        public void push(T x) {
            q.offer(x);
            topEle = x;
        }

        // 删除栈顶的元素并返回
        public T pop() {
            int size = q.size();
            while (size > 2) {
                q.offer(q.poll());
                size--;
            }
            //
            topEle = q.peek();
            q.offer(q.poll());
            return q.poll();
        }

        // 返回栈顶元素 */
        public T top() {
            return topEle;
        }

        // 判断栈是否为空 */
        public boolean empty() {
            return q.isEmpty();
        }
    }

    private static void testStack() {
        MyStack<Integer> stack = new MyStack<>();
        int n = 20;
        for (int i = 0; i < n; i++) {
            stack.push(i);
        }

        Integer val = null;
        while (null != (val = stack.pop())) {
            System.out.print(val + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        testMyQueue();
        testStack();
    }
}
