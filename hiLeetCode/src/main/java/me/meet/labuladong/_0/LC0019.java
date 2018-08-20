package me.meet.labuladong._0;

import me.meet.labuladong.common.SingleListNode;

public class LC0019 {
    private LC0019() {
    }

    /**
     * 力扣第 19 题「删除链表的倒数第 N 个结点」medium
     * 给一个单链表，删掉链表倒数第 n 个节点，并返回链表头节点
     * 限制：只遍历一次完成上述操作
     *
     * 1> 2 个指针遍历单链表
     * 2> 使用了虚拟头结点
     *
     * 这个逻辑就很简单了，要删除倒数第n个节点，就得获得倒数第n + 1个节点的引用，可以用我们下面实现的 findNodeFromEnd 来操作。
     * 不过注意我们又使用了虚拟头结点的技巧，也是为了防止出现空指针的情况，
     * 比如说链表总共有 5 个节点，题目就让你删除倒数第 5 个节点，也就是第一个节点，那按照算法逻辑，应该首先找到倒数第 6 个节点。
     * 但第一个节点前面已经没有节点了，这就会出错。
     * 但有了我们虚拟节点dummy的存在，就避免了这个问题，能够对这种情况进行正确的删除。
     *
     */
    static SingleListNode deleteNodeFromEnd(SingleListNode head, int k) {
        // 虚拟头节点
        SingleListNode dummy = SingleListNode.build(-1);
        dummy.next = head;
        // 删除倒数第 n 个，要先找倒数第 n + 1 个节点
        SingleListNode node = findNodeFromEnd(dummy, k + 1);
        // 删掉倒数第 n 个节点
        node.next = node.next.next;
        return dummy.next;
    }

    /**
     * 单链表的倒数第 k 个节点
     * 1> 2 个指针遍历单链表
     *
     * 从前往后寻找单链表的第k个节点很简单，一个 for 循环遍历过去就找到了，但是如何寻找从后往前数的第k个节点呢？
     * 那你可能说，假设链表有n个节点，倒数第k个节点就是正数第n - k个节点，不也是一个 for 循环的事儿吗？
     * 是的，但是算法题一般只给你一个ListNode头结点代表一条单链表，你不能直接得出这条链表的长度n，而需要先遍历一遍链表算出n的值，然后再遍历链表计算第n - k个节点。
     * 也就是说，这个解法需要遍历两次链表才能得到出倒数第k个节点。
     * 那么，我们能不能只遍历一次链表，就算出倒数第k个节点？可以做到的，如果是面试问到这道题，面试官肯定也是希望你给出只需遍历一次链表的解法。
     * 这个解法就比较巧妙了，假设k = 2，思路如下：
     * 首先，我们先让一个指针p1指向链表的头节点head，然后走k步：
     * 现在的p1，只要再走n - k步，就能走到链表末尾的空指针了对吧？  趁这个时候，再用一个指针p2指向链表头节点head：
     * 接下来就很显然了，让p1和p2同时向前走，p1走到链表末尾的空指针时走了n - k步，p2也走了n - k步，也就恰好到达了链表的倒数第k个节点：
     * 这样，只遍历了一次链表，就获得了倒数第k个节点p2。
     *
     * 当然，如果用 big O 表示法来计算时间复杂度，无论遍历一次链表和遍历两次链表的时间复杂度都是O(N)，但上述这个算法更有技巧性。
     *
     */
    static SingleListNode findNodeFromEnd(SingleListNode head, int k) {
        SingleListNode p0 = head;
        // p0 先走 k 步
        for (int i = 0; i < k; i++) {
            p0 = p0.next;
        }
        SingleListNode p1 = head;
        // p0 和 p1 同时走 n - k 步
        while (null != p0) {
            p0 = p0.next;
            p1 = p1.next;
        }
        // p1 现在指向第 n - k 个节点
        return p1;
    }

    private static void testDeleteNodeFromEnd() {
        SingleListNode head = SingleListNode.prepareSingleListNode0();
        int k = 3;
        SingleListNode ret = deleteNodeFromEnd(head, k);
        ret.print();
    }

    private static void testFindNodeFromEnd() {
        SingleListNode head = SingleListNode.prepareSingleListNode0();
        int k = 3;
        SingleListNode ret = findNodeFromEnd(head, k);
        System.out.println(ret.val);
    }

    public static void main(String[] args) {
        // 找到单链表的倒数第 k 个节点
        testFindNodeFromEnd();
        // 删除链表的倒数第 N 个结点
        testDeleteNodeFromEnd();
    }

}
