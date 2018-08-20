package me.meet.labuladong._0.LCNOT;

import me.meet.labuladong.common.SingleListNode;

public final class _00009 {
    private _00009() {
    }

    /*
     * 本文就总结一下单链表的基本技巧，每个技巧都对应着至少一道算法题：
     * 1、合并两个有序链表
     * 2、合并k个有序链表
     * 3、寻找单链表的倒数第k个节点
     * 4、寻找单链表的中点
     * 5、判断单链表是否包含环并找出环起点
     * 6、判断两个单链表是否相交并找出交点
     *
     * 这些解法都用到了双指针技巧，所以说对于单链表相关的题目，双指针的运用是非常广泛的，下面我们就来一个一个看。
     *
     * 1、合并两个有序链表
     * 给你输入两个有序链表，请你把他俩合并成一个新的有序链表(链表升序)
     *
     * 1>算法的逻辑类似于「拉拉链」, 双指针在 2 个链表上移动
     * 2>使用「虚拟头节点」技巧
     *
     *
     * 2、合并k个有序链表
     * 给你输入k个有序链表，请你把他们合并成一个新的有序链表(链表升序)
     * 1>我们就要用到 优先级队列（二叉堆） 这种数据结构
     *
     *
     * 3、寻找单链表的倒数第k个节点
     * 1>双指针在 1 个链表上移动
     * 2>使用「虚拟头节点」技巧
     *
     *
     * 4、寻找单链表的中点
     * 1>双指针(快慢指针)在 1 个链表上移动（一个一次走1步，一个一次走2步）
     *
     *
     * 5、判断单链表是否包含环并找出环起点
     * 1>双指针(快慢指针)在 1 个链表上移动（一个一次走1步，一个一次走2步）
     * 2>有环情况下快慢指针相遇，再重新从 head 出发再次相遇， 即环起点
     *
     *
     * 6、判断两个单链表是否相交并找出交点
     * 1>双指针(交换扫描2个链表)
     *
     */

    /**
     * 5、判断单链表是否包含环并找出环起点
     * 判断单链表是否包含环属于经典问题了，解决方案也是用快慢指针：
     * 每当慢指针slow前进一步，快指针fast就前进两步。
     * 如果fast最终遇到空指针，说明链表中没有环；如果fast最终和slow相遇，那肯定是fast超过了slow一圈，说明链表中含有环。
     * 只需要把寻找链表中点的代码稍加修改就行了：
     *
     * 当然，这个问题还有进阶版：如果链表中含有环，如何计算这个环的起点？
     * 这里简单提一下解法：
     * 可以看到，当快慢指针相遇时，让其中任一个指针指向头节点，然后让它俩以相同速度前进，再次相遇时所在的节点位置就是环开始的位置。
     * 我们假设快慢指针相遇时，慢指针slow走了k步，那么快指针fast一定走了2k步：
     * fast一定比slow多走了k步，这多走的k步其实就是fast指针在环里转圈圈，所以k的值就是环长度的「整数倍」。
     * 假设相遇点距环的起点的距离为m，那么结合上图的 slow 指针，环的起点距头结点head的距离为k - m，也就是说如果从head前进k - m步就能到达环起点。
     * 巧的是，如果从相遇点继续前进k - m步，也恰好到达环起点。因为结合上图的 fast 指针，从相遇点开始走k步可以转回到相遇点，那走k - m步肯定就走到环起点了：
     * 所以，只要我们把快慢指针中的任一个重新指向head，然后两个指针同速前进，k - m步后一定会相遇，相遇之处就是环的起点了。
     *
     */
    static boolean hasCycle(SingleListNode head) {
        // 快慢指针初始化指向 head
        SingleListNode slow = head, fast = head;
        // 快指针走到末尾时停止
        while (fast != null && fast.next != null) {
            // 慢指针走一步，快指针走两步
            slow = slow.next;
            fast = fast.next.next;
            // 快慢指针相遇，说明含有环
            if (slow == fast) {
                return true;
            }
        }
        // 不包含环
        return false;
    }

    static SingleListNode detectCycle(SingleListNode head) {
        // 快慢指针初始化指向 head
        SingleListNode slow = head, fast = head;
        boolean hasCycle = false;
        // 快指针走到末尾时停止
        while (fast != null && fast.next != null) {
            // 慢指针走一步，快指针走两步
            slow = slow.next;
            fast = fast.next.next;
            // 快慢指针相遇，说明含有环
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        if (!hasCycle) {
            return null;
        }
        // 重新指向头结点
        slow = head;
        // 快慢指针同步前进，相交点就是环起点
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    private static void testHasCycle() {
        SingleListNode l0 = SingleListNode.prepareSingleListNode0();
        boolean ret = hasCycle(l0);
        System.out.println(ret);

        l0 = SingleListNode.prepareSingleListNode3();
        ret = hasCycle(l0);
        System.out.println(ret);
    }

    private static void testDetectCycle() {
        SingleListNode l0 = SingleListNode.prepareSingleListNode3();
        SingleListNode ret = detectCycle(l0);
        if (null != ret) {
            System.out.println(ret.val);
        }
    }

    public static void main(String[] args) {
        // 判断单链表是否包含环
        testHasCycle();
        // 判断单链表是否包含环并找出环起点
        testDetectCycle();
    }
}
