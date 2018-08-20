package me.meet.test;

public class RebuildBSTFromLinkList {
    /**
     *
     *
     */
    static class LNode {
        int val;
        LNode next;

        LNode() {
        }
        LNode(int val) {
            this.val = val;
        }
        LNode(int val, LNode next) {
            this.val = val;
            this.next = next;
        }
    }
    static class TNode {
        int val;
        TNode left;
        TNode right;

        TNode() {
        }
        TNode(int val) {
            this.val = val;
        }
    }

    public static TNode rebuildBSTFromList(LNode head) {
        // base case
        if (null == head) {
            return null;
        }
        if (null == head.next) {
            return new TNode(head.val);
        }

        // find 1/2 list
        LNode slow = head, fast = head, pre = null;
        while (null != fast && null != fast.next) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // slow is 1/2
        TNode root = new TNode(slow.val);
        // cut list
        assert null != pre;
        pre.next = null;

        root.left = rebuildBSTFromList(head);
        root.right = rebuildBSTFromList(slow.next);
        return root;
    }

    private static LNode prepareList() {
        // 1 -> 3 -> 5 -> 7 -> 9
        LNode _9 = new LNode(9);
        LNode _7 = new LNode(7, _9);
        LNode _5 = new LNode(5, _7);
        LNode _3 = new LNode(3, _5);
        LNode _1 = new LNode(1, _3);
        return _1;
    }

    private static void testRebuildBSTFromList() {
        LNode head = prepareList();
        TNode root = rebuildBSTFromList(head);
        System.out.println(root);
    }

    public static void main(String[] args) {
        testRebuildBSTFromList();
    }
}
