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

//    private

//    public static void main(String[] args) {
//        Node n1 = new Node(1);
//        Node n2 = new Node(2);
//        Node n3 = new Node(3);
//        n1.next = n2;
//        n2.next = n3;
//        Node result = reverse(n1);
//
//        for (Node tmp = result; tmp != null; tmp = tmp.next) {
//            System.out.println(tmp.data);
//        }
//    }

    private final static String NUM = "零一二三四五六七八九";
    private final static String SOC = "十百千万";

    static String getNum(Integer num) {
        if (null == num || num < 0 || num > 100000) {
            return "";
        }
        StringBuilder sr = new StringBuilder();
        int i = 0;
        for (; ; ) {
            int res = num % 10;
            if (num <= 0) {
                break;
            }
            sr.insert(0, "" + NUM.charAt(res) + (i > 0 ? SOC.charAt(i - 1) : ""));
            i++;
            num = num / 10;
        }
        return sr.toString();
    }

    public static void main(String[] args) {
        System.out.print(getNum(123456));
        ;
    }
}