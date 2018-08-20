package me.meet.offer.structure;

import java.util.Deque;
import java.util.List;

public final class LinkedList {
    static class Node<T extends Comparable<T>> {
        T data;
        Node<T> next;
        Node<T> random;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        public Node(T data, Node<T> next, Node<T> random) {
            this.data = data;
            this.next = next;
            this.random = random;
        }
    }

    private static Node<Integer> _4321() {
        /**
         * 4 -> 3 -> 2 -> 1
         */
        Node<Integer> _1 = new Node<>(1, null);
        Node<Integer> _2 = new Node<>(2, _1);
        Node<Integer> _3 = new Node<>(3, _2);
        Node<Integer> _4 = new Node<>(4, _3);
        return _4;
    }
    
    private static Node<Integer> random4321() {
        /**
         * 4 -> 3 -> 2 -> 1
         */
        Node<Integer> _1 = new Node<>(1, null);
        Node<Integer> _2 = new Node<>(2, _1);
        Node<Integer> _3 = new Node<>(3, _2);
        Node<Integer> _4 = new Node<>(4, _3);
        _4.random = _1;
        _3.random = _4;
        _2.random = _3;
        _1.random = _4;
        return _4;
    }
    
    private static Node<Integer>[] yLinkedList() {
        /**
         * Y型:
         * 1 -> 2 -> 3
         *            \
         *             6 -> 7 -> 8
         *            /
         *      4 -> 5
         *
         */
        Node<Integer> _8 = new Node<>(8, null);
        Node<Integer> _7 = new Node<>(7, _8);
        Node<Integer> _6 = new Node<>(6, _7);
        Node<Integer> _3 = new Node<>(3, _6);
        Node<Integer> _2 = new Node<>(2, _3);
        Node<Integer> _1 = new Node<>(1, _2);

        Node<Integer> _5 = new Node<>(5, _6);
        Node<Integer> _4 = new Node<>(4, _5);
        return (Node<Integer>[]) new Node[]{_1, _4};
    }

    private static Node<Integer> loopLinkedList() {
        /**
         * 1 -> 2 -> 3 -> 4 -> 5 -> 6
         *                |         |
         *                9 <- 8 <- 7
         */
        Node<Integer> _9 = new Node<>(9, null);
        Node<Integer> _8 = new Node<>(8, _9);
        Node<Integer> _7 = new Node<>(7, _8);
        Node<Integer> _6 = new Node<>(6, _7);
        Node<Integer> _5 = new Node<>(5, _6);
        Node<Integer> _4 = new Node<>(4, _5);
        Node<Integer> _3 = new Node<>(3, _4);
        Node<Integer> _2 = new Node<>(2, _3);
        Node<Integer> _1 = new Node<>(1, _2);

        // loop
        _9.next = _4;
        return _1;
    }
    
    private static Node<Integer> duplicatLinkedList() {
        // 1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5
        Node<Integer> _5 = new Node<>(5, null);
        Node<Integer> _4d = new Node<>(4, _5);
        Node<Integer> _4 = new Node<>(4, _4d);
        Node<Integer> _3d = new Node<>(3, _4);
        Node<Integer> _3 = new Node<>(3, _3d);
        Node<Integer> _2 = new Node<>(2, _3);
        Node<Integer> _1 = new Node<>(1, _2);
        return _1;
    }
    
    static void backPrintSingleLinkedList(Node<Integer> head) {
        if (null == head) {
            return;
        }
        java.util.LinkedList<Node<Integer>> st = new java.util.LinkedList<>();
        Node<Integer> cur = head;
        while (null != cur) {
            st.push(cur);
            cur = cur.next;
        }
        cur = st.poll();
        while (null != cur) {
            System.out.print(cur.data + ", ");
            cur = st.poll();
        }
    }

    private static void testPrint() {
        Node<Integer> head = _4321();
        backPrintSingleLinkedList(head);
    }


    static Node<Integer> findKth2Tail(Node<Integer> head, int k) {
        if (null == head || k < 0) {
            return null;
        }
        Node<Integer> first = head;
        for (int i = k; i > 0; i--) {
            if (null == first) {
                return null;
            }
            first = first.next;
        }

        Node<Integer> second = head;
        while (null != first) {
            first = first.next;
            second = second.next;
        }
        return second;
    }

    private static void testFind() {
        Node<Integer> head = _4321();
        int k = 3;
        Node<Integer> rs = findKth2Tail(head, k);
        System.out.println(rs.data);
    }

    static Node<Integer> reverse(Node<Integer> head) {
        Node<Integer> rs = null;
        for (Node<Integer> cur = head; null != cur;) {
            Node<Integer> next = cur.next;
            cur.next = rs;
            rs = cur;
            cur = next;
        }
        return rs;
    }

    private static void testReverse() {
        Node<Integer> head = _4321();
        Node<Integer> rs = reverse(head);
        backPrintSingleLinkedList(rs);
    }

    static Node<Integer> merge2Node(Node<Integer> l0, Node<Integer> l1) {
        if (null == l0 || null == l1) {
            return null == l0 ? l1 : l0;
        }
        Node<Integer> head = new Node<>(0, null);
        Node<Integer> cur = head, cr0 = l0, cr1 = l1;
        while (null != cr0 && null != cr1) {
            if (cr0.data.compareTo(cr1.data) > 0) {
                cur.next = cr1;
                cr1 = cr1.next;
            } else {
                cur.next = cr0;
                cr0 = cr0.next;
            }
            cur = cur.next;
        }
        if (null != cr0) {
            cur.next = cr0;
        }
        if (null != cr1) {
            cur.next = cr1;
        }
        return head.next;
    }

    private static void testMerge2Node() {
        Node<Integer> l0 = reverse(_4321());
        Node<Integer> l1 = reverse(_4321());
        Node<Integer> rs = merge2Node(l0, l1);
        backPrintSingleLinkedList(rs);
    }

    static Node<Integer> copyRandomList(Node<Integer> head) {
        if (null == head) {
            return null;
        }
        // 1. copy and insert next
        Node<Integer> cur = head;
        while (null != cur) {
            Node<Integer> next = cur.next;
            cur.next = new Node<>(cur.data, cur.next, cur.random);
            cur = next;
        }
        // 2. copy random
        cur = head;
        while (null != cur) {
            if (null == cur.random) {
                cur.next.random = null;
            } else {
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }
        // 3. split
        cur = head;
        Node<Integer> cHead = cur.next;
        while (null != cur) {
            Node<Integer> cCur = cur.next;
            if (null == cCur.next) {
                cur.next = null;
            } else {
                Node<Integer> next = cCur.next;
                cCur.next = cCur.next.next;
                cur.next = next;
            }
            cur = cur.next;
        }
        return cHead;
    }

    private static void testCopyRandomList() {
        Node<Integer> head = random4321();
        Node<Integer> rs = copyRandomList(head);
        System.out.println(head == rs);
    }

    static Node<Integer> findFirstCommon(Node<Integer> h0, Node<Integer> h1) {
        if (null == h0 || null == h1) {
            return null;
        }

        Node<Integer> cr0 = h0, cr1 = h1;
        java.util.LinkedList<Node<Integer>> st0 = new java.util.LinkedList<>(), st1 = new java.util.LinkedList<>();
        while (null != cr0) {
            st0.push(cr0);
            cr0 = cr0.next;
        }
        while (null != cr1) {
            st1.push(cr1);
            cr1 = cr1.next;
        }
        cr0 = st0.poll();
        cr1 = st1.poll();
        Node<Integer> rs = cr0;
        while (null != cr0 && null != cr1) {
            if (cr0 == cr1) {
                rs = cr0;
                cr0 = st0.poll();
                cr1 = st1.poll();
            } else {
                return rs;
            }
        }
        return null;
    }

    private static void testFindFirstCommon() {
        Node<Integer>[] _2n = yLinkedList();
        Node<Integer> rs = findFirstCommon(_2n[0], _2n[1]);
        System.out.println(rs.data);
    }

    static Node<Integer> findEntryOfLoop(Node<Integer> head) {
        if (null == head || null == head.next) {
            return null;
        }

        Node<Integer> meet = null, fCur = head, sCur = head;
        while (null != fCur && null != sCur) {
            fCur = fCur.next;
            sCur = sCur.next.next;
            if (fCur == sCur) {
                meet = sCur;
                break;
            }
        }
        if (null == meet) { // no loop
            return null;
        }

        Node<Integer> cur = head;
        while (cur != meet) {
            cur = cur.next;
            meet = meet.next;
        }
        return cur;
    }

    private static void testFindEntryOfLoop() {
        Node<Integer> head = loopLinkedList();
        Node<Integer> rs = findEntryOfLoop(head);
        System.out.println(rs.data);
    }

    static Node<Integer> deleteDuplicate(Node<Integer> head) {
        if (null == head) {
            return null;
        }
        Node<Integer> rHead = new Node<>(0, head);
        Node<Integer> pre = rHead, cur = head;
        while (null != cur && null != cur.next) {
            if (cur.data.compareTo(cur.next.data) == 0) {
                while (null != cur.next && cur.data.compareTo(cur.next.data) == 0) {
                    cur = cur.next;
                }
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return rHead.next;
    }

    private static void testDeleteDuplicate() {
        Node<Integer> head = duplicatLinkedList();
        Node<Integer> rs = deleteDuplicate(head);
        backPrintSingleLinkedList(rs);
    }

    public static List<Integer> maxSlideWindow(int[] nums, int k) {
        // nums.length > k
        List<Integer> rs = new java.util.LinkedList<>();
        Deque<Integer> window = new java.util.LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i >= k && window.peek() <= i - k + 1) {
                window.removeFirst();
            }
            while (!window.isEmpty() && nums[window.peekLast()] <= nums[i]) {
                window.removeLast();
            }
            window.add(i);
            if (i - k + 1 >= 0) {
                rs.add(nums[window.peek()]);
            }
        }
        return rs;
    }

    private static void testMaxSlideWindow() {
        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        List<Integer> rs = maxSlideWindow(nums, k);
        System.out.println(rs);
    }

    public static void main(String[] args) {
        testPrint();
        testFind();
        testReverse();
        testMerge2Node();
        testCopyRandomList();
        testFindFirstCommon();
        testFindEntryOfLoop();
        testDeleteDuplicate();
        testMaxSlideWindow();
    }
}
