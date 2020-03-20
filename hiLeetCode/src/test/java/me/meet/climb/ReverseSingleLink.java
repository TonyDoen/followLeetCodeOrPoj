package me.meet.climb;

public class ReverseSingleLink {
    static class Node {
        Object data;
        Node next;

        Node() {
        }

        Node(Object data) {
            this.data = data;
        }
    }

    /**
     * 逆转单链表
     */
    static Node reverse(Node node) {
        if (null == node) {
            return null;
        }
        Node result = null;
        Node cur = node;
        for (; null != cur; ) {
            Node tmp = cur;
            cur = cur.next;
            tmp.next = result;
            result = tmp;
        }
        return result;
    }

    public static void main(String[] args) {

    }
}