package me.meet.offer.structure;

import java.util.List;

public class StackAndQueue {

    static class Node<T> {
        T data;
        Node<T> next;

        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    static class SelfQueue<T> {
        private Node<T> head, tail;

        public boolean isEmpty() {
            return null == head;
        }

        public void push(T data) {
            if (null == head) {
                head = new Node<>(data, null);
                tail = head;
                return;
            }
            tail.next = new Node<>(data, null);
            tail = tail.next;
        }

        public T pop() {
            if (null == head) {
                return null;
            }
            T rs = head.data;
            head = head.next;
            if (null == head) {
                tail = null;
            }
            return rs;
        }
    }

    static class MaxMinStack<T extends Comparable<T>> {
        private Node<T> head;
        private Node<T> max;
        private Node<T> min;

        public void push(T data) {
            if(null == data) {
                return;
            }
            if (null == head) {
                head = new Node<>(data, null);
                max = head;
                min = head;
                return;
            }
            // head
            head = new Node<>(data, head);
            // max
            if (data.compareTo(max.data) > 0) {
                max = new Node<>(data, max);
            } else {
                max = new Node<>(max.data, max);
            }
            // min
            if (data.compareTo(min.data) < 0) {
                min = new Node<>(data, min);
            } else {
                min = new Node<>(min.data, min);
            }
        }

        public T pop() {
            if (null == head) {
                return null;
            }
            Node<T> rs = head;
            head = head.next;
            max = max.next;
            min = min.next;
            return rs.data;
        }

        public T min() {
            if (null == min) {
                return null;
            }
            return min.data;
        }

        public T max() {
            if (null == max) {
                return null;
            }
            return max.data;
        }
    }

    static boolean isPopOrder(int[] push, int[] pop) {
        if (null == push || null == pop || push.length != pop.length || push.length < 1) {
            return false;
        }
        java.util.LinkedList<Integer> st = new java.util.LinkedList<>();
        for (int i = 0, j = 0; i < push.length; i++) {
            st.push(push[i]);
            while (!st.isEmpty() && st.peek().equals(pop[j])) {
                st.pop();
                j++;
            }
        }
        return st.isEmpty();
    }

    // error
    static List<Integer> maxInWindow(int[] arr, int k) {
        List<Integer> rs = new java.util.LinkedList<>();
        if (null == arr || k < 1) {
            return rs;
        }

        // 1. k > arr.length
        if (arr.length < k) {
            int max = Integer.MIN_VALUE;
            for (int v : arr) {
                max = Math.max(v, max);
            }
            rs.add(max);
            return rs;
        }

        // 2. k < arr.length
        java.util.LinkedList<Integer> window = new java.util.LinkedList<>();
//        for (int i = 0; i < k; i++) {
//            while(!window.isEmpty() && arr[window.peekLast()] <= arr[i]) {
//                window.removeLast();
//            }
//            window.addLast(i);
//        }
//        for (int i = k; i < arr.length; i++) {
//            while (!window.isEmpty() && window.peekFirst() < i-k) {
//                window.removeFirst();
//            }
//            while (!window.isEmpty() && arr[window.peekLast()] <= arr[i]) {
//                window.removeLast();
//            }
//            window.addLast(i);
//            rs.add(arr[window.peekFirst()]);
//        }
//        return rs;

        for (int i = 0; i < arr.length; i++) {
            if (!window.isEmpty() && i > k && window.peekFirst() == i - k ) {
                window.removeFirst();
            }
            if (!window.isEmpty() && arr[window.peekLast()] < arr[i]) {
                window.removeLast();
            }
            window.addLast(i);
            if (i - k + 1 > 0) {
                rs.add(arr[window.peekFirst()]);
            }
        }
        return rs;
    }


    private static void testMaxSlideWindow() {
        int[] arr = new int[]{2, 3, 4, 2, 6, 2, 5, 1};
        int k = 3;
        List<Integer> rs = maxInWindow(arr, k);
        System.out.println(rs);
    }

    public static void main(String[] args) {
        testMaxSlideWindow();
    }
}
