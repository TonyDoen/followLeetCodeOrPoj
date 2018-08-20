package me.meet.test;

public class Test03 {

    /**
     *
     *
     *
     */
    static class LNode {
        int val;
        LNode next;
        LNode() {}
        LNode(int val) { this.val = val; }
        LNode(int val, LNode next) {
            this.val = val;
            this.next = next;
        }
    }
    static class TNode {
        int val;
        TNode left;
        TNode right;
        TNode() {}
        TNode(int val) { this.val = val; }
    }

    public static TNode cBSTFromList(LNode head) {
        if (null == head) {
            return null;
        }
        if (null == head.next) {
            return new TNode(head.val);
        }

        // find 1/2 and cut
        LNode pre = null, slow = head, fast = head;
        while (null != fast && null != fast.next) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // cut
        assert pre != null;
        pre.next = null;

        TNode root = new TNode(slow.val);
        root.left = cBSTFromList(head);
        root.right = cBSTFromList(slow.next);
        return root;
    }

    private static void testCBSTFromList() {
        Tuple2<LNode, LNode> t = prepareList();
        TNode rs = cBSTFromList(t._0);
        System.out.println(rs);
    }

    static class Tuple2<A, B> {
        A _0;
        B _1;
        Tuple2(A v0, B v1) {
            this._0 = v0;
            this._1 = v1;
        }
    }
    public static TNode constructBSTFromList(LNode head, LNode tail) {
        if (null == head || null == tail) {
            return null;
        }
        if (tail == head || null == head.next) {
            return new TNode(head.val);
        }

        // find 1/2
        LNode slow = head.next, fast = head.next.next;
        while (null != fast && null != fast.next && tail != fast.next) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // slow is 1/2 position
        TNode root = new TNode(slow.val);
        root.left = constructBSTFromList(head, slow);
        root.right = constructBSTFromList(slow.next, tail);
        return root;
    }

    // 1 -> 2 -> 4 -> 5 -> 7
    private static Tuple2<LNode, LNode> prepareList() {
        LNode _7 = new LNode(7);
        LNode _5 = new LNode(5, _7);
        LNode _4 = new LNode(4, _5);
        LNode _2 = new LNode(2, _4);
        LNode _1 = new LNode(1, _2);
        return new Tuple2<>(_1, _7);
    }

    private static void testConstructBSTFromList() {
        Tuple2<LNode, LNode> tuple = prepareList();
        TNode root = constructBSTFromList(tuple._0, tuple._1);
        System.out.println(root);
    }

    // 1 -> 2 -> 3 -> 4
    //      pre  tCur cur
    // length = 4
    // k = 2 , k = 5
    // 2%4 = 2
    // 5%4 = 1
    private static LNode rightN(LNode head, int k) {
        // head is single link
        // k
        // length
        if (null == head || null == head.next) {
            return head;
        }

        LNode tHead = new LNode(-1, head), cur = head, pre = head;
        int length = 0;
        while (null != cur) {
            cur = cur.next;
            length++;
        }

        int diff = k % length, i = 0;
        if (0 == diff) {
            return head;
        }

        cur = head;
        while (i <= diff) {
            cur = cur.next;
            i++;
        }
        while (null != cur && null != cur.next) {
            pre = cur;
            cur = cur.next;
        }
        LNode tCur = pre.next;
        pre.next = null;

        LNode tmp = tHead.next;
        tHead.next = tCur;
        cur.next = tmp;

        return tHead.next;
    }



    public static void main(String[] args) {
//        testConstructBSTFromList();
//        testCBSTFromList();
    }
}
