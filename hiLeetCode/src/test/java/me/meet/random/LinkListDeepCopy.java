package me.meet.random;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LinkListDeepCopy {
    static class LNode {
        int val;
        LNode next;
        LNode random;

        public LNode() {}
        public LNode(int v) {
            this.val = v;
        }

        public LNode(int v, LNode next) {
            this.val = v;
            this.next = next;
        }
    }

    public static LNode prepareLNode() {
        // linkList next, random deepCopy, 最少扫描次数
        //  1 -> 2 -> 3 -> 4
        // 1: next = 2, random = null
        // 2: next = 3, random = 4
        // 3: next = 4, random = 2

        LNode _4 = new LNode(4, null);
        LNode _3 = new LNode(3, _4);
        LNode _2 = new LNode(2, _3);
        LNode _1 = new LNode(1, _2);

        _2.random = _4;
        _3.random = _2;

        return _1;
    }

    /**
     * 深度拷贝带有随机指针的单链表
     * 尽量优化时间复杂度，空间复杂度
     *
     * 思路1：
     * 递归，利用系统栈进行深度拷贝
     *
     *
     * 时间复杂度： O(N)
     * 空间复杂度： O(N)
     */
    private static final Map<LNode, LNode> mp = new HashMap<>();

    public static LNode deepCopy0(LNode head) {
        if (null == head) {
            return null;
        }

        LNode cp = mp.get(head);
        if (null == cp) {
            cp = new LNode(head.val);
            mp.put(head, cp);

            deepCopy0(head.next);
        }

        cp.next = mp.get(head.next);
        if (null != head.random) {
            cp.random = mp.get(head.random);
        }
        return cp;
    }

    /**
     * 深度拷贝带有随机指针的单链表
     * 尽量优化时间复杂度，空间复杂度
     *
     * 思路1：
     * 非递归写法，利用栈进行深度拷贝
     *
     *
     * 时间复杂度： O(2N)
     * 空间复杂度： O(2N)
     */
    public static LNode deepCopy1(LNode head) {
        if (null == head) {
            return null;
        }

        Map<LNode, LNode> nodeMp = new HashMap<>();
        LinkedList<LNode> stack = new LinkedList<>();
        LNode cur = head;
        while (null != cur) {
            nodeMp.put(cur, new LNode(cur.val));
            stack.push(cur);
            cur = cur.next;
        }
        while (!stack.isEmpty()) {
            LNode oldNode = stack.pop();
            LNode newNode = nodeMp.get(oldNode);

            newNode.next = nodeMp.get(oldNode.next);
            if (null != oldNode.random) {
                newNode.random = nodeMp.get(oldNode.random);
            }
        }
        return nodeMp.get(head);
    }

    /**
     * 深度拷贝带有随机指针的单链表
     * 尽量优化时间复杂度，空间复杂度
     *
     * 思路2： （不使用 HashMap）
     * 1. 复制值（把深度拷贝的链表链接到本链表，保存前后关系）
     * 2. 复制 random 引用
     * 3. 拆开链表
     *
     *
     * 时间复杂度： O(3N)
     * 空间复杂度： O(1)
     */
    public static LNode deepCopy2(LNode head) {
        if (null == head) {
            return null;
        }

        // 1. 将新节点链接到老链表里
        LNode cur = head;
        while (null != cur) {
            LNode newNode = new LNode(cur.val, cur.next);
            LNode tmp = cur.next;
            cur.next = newNode;
            cur = tmp;
        }

        // 2. 复制 random 引用
        cur = head;
        while (null != cur) {
            LNode random = null;
            if (null != cur.random) {
                random = cur.random.next;
            }
            cur.next.random = random;
            cur = cur.next.next;
        }

        // 3. 将新链表拆分出来
        cur = head;
        LNode newHead = cur.next;
        while (null != cur.next) {
            LNode tmp = cur.next;
            cur.next = tmp.next;
            cur = tmp;
        }
        return newHead;
    }

    private static void testDeepCopy() {
        LNode head = prepareLNode();
        LNode cp0 = deepCopy0(head);
        System.out.println(cp0);

        LNode cp1 = deepCopy1(head);
        System.out.println(cp1);

        LNode cp2 = deepCopy2(head);
        System.out.println(cp2);
    }


    public static void main(String[] args) {
        // 深度拷贝带有随机指针的单链表
        testDeepCopy();
    }
}
