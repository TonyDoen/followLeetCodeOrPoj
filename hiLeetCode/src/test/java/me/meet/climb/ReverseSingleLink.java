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
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        n1.next = n2;
        n2.next = n3;
        Node result = reverse(n1);

        for (Node tmp = result; tmp != null; tmp = tmp.next) {
            System.out.println(tmp.data);
        }
    }
}