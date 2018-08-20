package me.meet.labuladong._0;

import me.meet.labuladong.common.SingleListNode;

public class LC0160 {
    private LC0160() {
    }

    /**
     * 力扣第 160 题「相交链表」
     * 限制：空间复杂度为O(1)，时间复杂度为O(N)
     *
     * 给你输入两个链表的头结点headA和headB，这两个链表可能存在相交。
     * 如果相交，你的算法应该返回相交的那个节点；如果没相交，则返回 null。
     * 比如题目给我们举的例子，如果输入的两个链表如下图：
     *
     * 那么我们的算法应该返回c1这个节点。
     * 这个题直接的想法可能是用HashSet记录一个链表的所有节点，然后和另一条链表对比，但这就需要额外的空间。
     * 如果不用额外的空间，只使用两个指针，你如何做呢？
     *
     * 难点在于，由于两条链表的长度可能不同，两条链表之间的节点无法对应：
     * 如果用两个指针p1和p2分别在两条链表上前进，并不能同时走到公共节点，也就无法得到相交节点c1。
     * 所以，解决这个问题的关键是，通过某些方式，让p1和p2能够同时到达相交节点c1。
     * 所以，我们可以让p1遍历完链表A之后开始遍历链表B，让p2遍历完链表B之后开始遍历链表A，这样相当于「逻辑上」两条链表接在了一起。
     * 如果这样进行拼接，就可以让p1和p2同时进入公共部分，也就是同时到达相交节点c1：
     *
     *
     * 这样，这道题就解决了，空间复杂度为O(1)，时间复杂度为O(N)。
     *
     */
    static SingleListNode getIntersectionNode(SingleListNode l0, SingleListNode l1) {
        // p1 指向 A 链表头结点，p2 指向 B 链表头结点
        SingleListNode p0 = l0, p1 = l1;
        while (p1 != p0) {
            // p0 走一步，如果走到 l0 链表末尾，转到 l1 链表
            if (null == p0) {
                p0 = l1;
            } else {
                p0 = p0.next;
            }
            // p1 走一步，如果走到 l1 链表末尾，转到 l0 链表
            if (null == p1) {
                p1 = l0;
            } else {
                p1 = p1.next;
            }
        }
        return p1;
    }

    private static void testGetIntersectionNode() {
        SingleListNode[] inputs = SingleListNode.prepareSingleListNode2();
        SingleListNode ret = getIntersectionNode(inputs[0], inputs[1]);
        System.out.println(ret.val);
    }

    public static void main(String[] args) {
        // 力扣第 160 题「相交链表」
        testGetIntersectionNode();
    }
}
