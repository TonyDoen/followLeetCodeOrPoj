package me.meet.labuladong._0;

import me.meet.labuladong.common.SingleListNode;

public class LC0021 {
    private LC0021() {
    }

    /**
     * 力扣第 21 题「合并两个有序链表」
     * 给你输入两个有序链表，请你把他俩合并成一个新的有序链表
     *
     * 最基本的链表技巧:
     * 这个算法的逻辑类似于「拉拉链」，l1, l2类似于拉链两侧的锯齿，指针p就好像拉链的拉索，将两个有序链表合并。
     * 代码中还用到一个链表的算法题中是很常见的「虚拟头节点」技巧，也就是dummy节点。
     * 你可以试试，如果不使用dummy虚拟节点，代码会复杂很多，而有了dummy节点这个占位符，可以避免处理空指针的情况，降低代码的复杂性。
     *
     */
    static SingleListNode mergeTwoLists(SingleListNode l0, SingleListNode l1) {
        // 虚拟头结点
        SingleListNode dummy = SingleListNode.build(-1), pd = dummy, p0 = l0, p1 = l1;
        while (null != p0 && null != p1) {
            // 比较 p0 和 p1 两个指针, 将值较小的的节点接到 pd 指针
            if (p0.val < p1.val) {
                pd.next = p0;
                p0 = p0.next;
            } else {
                pd.next = p1;
                p1 = p1.next;
            }
            // pd 指针不断前进
            pd = pd.next;
        }
        if (null != p0) {
            pd.next = p0;
        }
        if (null != p1) {
            pd.next = p1;
        }
        return dummy.next;
    }

    private static void testMergeTwoLists() {
        SingleListNode l0 = SingleListNode.prepareSingleListNode0();
        SingleListNode l1 = SingleListNode.prepareSingleListNode0();
        SingleListNode ret = mergeTwoLists(l0, l1);
        ret.print();
    }

    public static void main(String[] args) {
        // 力扣第 21 题「合并两个有序链表」
        testMergeTwoLists();
    }
}
