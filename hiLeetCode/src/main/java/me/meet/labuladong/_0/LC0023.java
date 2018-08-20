package me.meet.labuladong._0;

import me.meet.labuladong.common.SingleListNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class LC0023 {
    private LC0023() {
    }

    /**
     * 力扣第 23 题「合并K个升序链表」
     * 给你输入两个有序链表(升序)数组，请你把他们合并成一个新的有序链表
     * 
     * 合并k个有序链表的逻辑类似合并两个有序链表，难点在于，如何快速得到k个节点中的最小节点，接到结果链表上？
     * 
     * 链表技巧:
     * 这里我们就要用到 优先级队列（二叉堆） 这种数据结构，把链表节点放入一个最小堆，就可以每次获得k个节点中的最小节点：
     * 这个算法是面试常考题，它的时间复杂度是多少呢？
     * 优先队列pq中的元素个数最多是k，所以一次poll或者add方法的时间复杂度是O(logk)；所有的链表节点都会被加入和弹出pq，
     * 
     * 所以算法整体的时间复杂度是O(Nlogk)
     * 其中k是链表的条数，N是这些链表的节点总数。
     */
    static SingleListNode mergeKLists(List<SingleListNode> lists) {
        if (null == lists || lists.isEmpty()) {
            return null;
        }
        // 虚拟头结点
        SingleListNode dummy = SingleListNode.build(-1), pd = dummy;
        // 优先级队列，最小堆
        PriorityQueue<SingleListNode> pq = new PriorityQueue<>(lists.size(), Comparator.comparingInt(a -> a.val));
        // 将 k 个链表的头结点加入最小堆
        for (SingleListNode head : lists) {
            if (null != head) {
                pq.add(head);
            }
        }
        while (!pq.isEmpty()) {
            // 获取最小节点，接到结果链表中
            SingleListNode node = pq.poll();
            pd.next = node;
            if (null != node.next) {
                pq.add(node.next);
            }
            // p 指针不断前进
            pd = pd.next;
        }
        return dummy.next;
    }

    private static void testMergeKLists() {
        List<SingleListNode> lists = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            lists.add(SingleListNode.prepareSingleListNode0());
        }

        SingleListNode ret = mergeKLists(lists);
        ret.print();
    }

    public static void main(String[] args) {
        // 力扣第 23 题「合并K个升序链表」
        testMergeKLists();
    }
}
