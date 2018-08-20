package me.meet.labuladong._0;

import me.meet.labuladong.common.SingleListNode;

public class LC0876 {
    private LC0876() {
    }

    /**
     * 力扣第 876 题「链表的中间结点」
     * 限制：一次遍历就得到中间节点
     *
     * 问题的关键也在于我们无法直接得到单链表的长度n，常规方法也是先遍历链表计算n，再遍历一次得到第n / 2个节点，也就是中间节点。
     * 使用「快慢指针」的技巧： 我们让两个指针slow和fast分别指向链表头结点head。
     * 每当慢指针slow前进一步，快指针fast就前进两步，这样，当fast走到链表末尾时，slow就指向了链表中点。
     *
     */
    static SingleListNode middleNode(SingleListNode head) {
        // 快慢指针初始化指向 head
        SingleListNode slow = head, fast = head;
        // 快指针走到末尾时停止
        while (null != fast && null != fast.next) {
            // 慢指针走一步，快指针走两步
            slow = slow.next;
            fast = fast.next.next;
        }
        // 慢指针指向中点
        return slow;
    }

    private static void testMiddleNode() {
        SingleListNode l0 = SingleListNode.prepareSingleListNode0();
        SingleListNode ret = middleNode(l0);
        System.out.println(ret.val);
    }

    public static void main(String[] args) {
        // 力扣第 876 题「链表的中间结点」
        testMiddleNode();
    }
}
