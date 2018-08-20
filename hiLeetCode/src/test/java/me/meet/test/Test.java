package me.meet.test;

import me.meet.labuladong.common.SingleListNode;

public class Test {
    // 123+456
    // 321 654
    // cms
    // G1
    // ZGC
    // memcallc

    // DAG / HA
    // koblier master/worker
    // mysql  task/DAG
    //

    public static String compute(String i0, String i1) {
        // lvs
        // ip
        // spark
        // kafka
        //

        // default i0.length > i1.length
        StringBuilder sr = new StringBuilder();
        int d = 0; //进位
        for (int i = 0; i < i1.length(); i++) {
            int cur = (int) (i0.charAt(i)-'0') + (int) (i1.charAt(i)-'0') + d;
            if (cur > 9) {
                d = 1;
            } else {
                d = 0;
            }
            sr.insert(0, (cur%10));
        }
        for (int i = i1.length(); i < i0.length(); i++) {
            int cur = i0.charAt(i)-'0' + d;
            if (cur > 9) {
                d = 1;
            } else {
                d = 0;
            }
            sr.insert(0, cur%10);
        }
        return sr.toString();
    }

//    public static String precompute(String i0, String i1) {
//        if (i0.length() > i1.length()) {
//            return compute(i0.revese(), i1.reverse());
//        } else {
//            reutrn compute(i1.reverse(), i0.reverse())
//        }
//
//        // Spark shuffle(sort base shuffle ) bypass C
//        // text 1,2,3
//        // distinct  map shuffle reduce
//        // broadcast
//        // kafka
//        // Spark Streaming
//        //
//    }

    /**
     * A table (userId,loginDate)
     *                 20210101
     * 每个用户最长连续登录天数
     *
     * select userId, udf(row partion by userId) as cnt from A
     *
     *
     * array[] int sorted -10->99 abs
     * logN
     *
     *
     * map detla
     * 4:1
     *
     * 24
     *
     *
     *
     */
    //   l       m           r
    // [-10, -7, 3, 5, 10, 11]
    //
    public static int findAbsMin(int[] arr) {
        // arr: sorted
        int rs = Integer.MAX_VALUE;
        int left = 0, right = arr.length-1;
        for (; left < right;) {
            int mid = left+(right-left)/2;
            if (arr[mid] > 0) {
                right = mid;
            } else {
                left = mid;
            }


            if (Math.abs(arr[left]) > Math.abs(arr[right])) {
                left = mid - 1;
            } else if (Math.abs(arr[left]) < Math.abs(arr[right])) {
                right = mid + 1;
            } else {
                // abs(arr[left]) == abs(arr[right])
                left++;
                right--;

            }
            if (Math.abs(arr[mid]) < Math.abs(rs)) {
                rs = arr[mid];
            }
        }
        return rs;
    }

//    public static void main(String[] args) {
//        int[] arr = new int[]{-10, -7, 3, 5, 10, 11};
//        int rs = findAbsMin(arr);
//        System.out.println(rs);
//
////        String i0 = "123";
////        String i1 = "456";
////        String i0 = "321";
////        String i1 = "654";
////        String rs = compute(i0, i1);
////        System.out.println(rs);
//    }

    public static SingleListNode sortN(SingleListNode head) {
        if (null == head || null == head.next) {
            return head;
        }
        SingleListNode prev = null, slow = head, fast = head;
        while (null != fast && null != fast.next) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // break
        prev.next = null;
        SingleListNode half1 = reverse(slow);
        SingleListNode half0 = head;

        oMerge(half0, half1);
        return head;
    }

    public static void oMerge(SingleListNode n0, SingleListNode n1) {
        // 1 -> 2 -> 3
        // 4 -> 5 -> 6
        while (null != n0) {
            SingleListNode next0 = n0.next, next1 = n1.next;
            n0.next = n1;
            if (null == next0) {
                break;
            }
            n1.next = next0;
            n0 = next0;
            n1 = next1;
        }
    }

    public static SingleListNode reverseN(SingleListNode head, int n) {
        if (null == head || null == head.next || n < 1) {
            return head;
        }

        // 1 -> 2 -> 3 -> 4
        SingleListNode prev = null, curr = head, next = null;
        while (null != curr) {
            if (n-- < 1) {
                break;
            }
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head.next = next;
        return prev;
    }

    public static SingleListNode reverse(SingleListNode head) {
        if (null == head || null == head.next) {
            return head;
        }

        // 1 -> 2 -> 3 -> 4
        SingleListNode prev = null, curr = head, next = null;
        while (null != curr) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    static SingleListNode reverse0(SingleListNode head) {
        if (null == head || null == head.next) {
            return head;
        }

        SingleListNode last = reverse0(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }

    private static SingleListNode successor = null; // 后驱节点
    // 反转以 head 为起点的 n 个节点，返回新的头结点
    static SingleListNode reverseN0(SingleListNode head, int n) {
        if (1 == n) {
            // 记录第 n + 1 个节点
            successor = head.next;
            return head;
        }

        // 以 head.next 为起点，需要反转前 n - 1 个节点
        SingleListNode last = reverseN0(head.next, n - 1);
        head.next.next = head;
        // 让反转之后的 head 节点和后面的节点连起来
        head.next = successor;
        return last;
    }

    public static int longestOnes(int[] arr, int k) {
        int left = 0, right;
        for (right = 0; right < arr.length; right++) {
            // If we included a zero in the window we reduce the value of K.
            // Since K is the maximum zeros allowed in a window.
            if (arr[right] == 0) {
                k--;
            }
            // A negative K denotes we have consumed all allowed flips and window has
            // more than allowed zeros, thus increment left pointer by 1 to keep the window size same.
            if (k < 0) {
                // If the left element to be thrown out is zero we increase K.
                if (arr[left] == 0) {
                    k++;
                }
                left++;
            }
        }
        // The size of the window is right - left, and we know it's the maximum
        // since we moved the end pointer as far right as possible.
        return right - left;
    }

    public static void main(String[] args) {
        SingleListNode src0 = SingleListNode.prepareSingleListNode0();
        SingleListNode ret0 = reverseN(src0, 3);
        SingleListNode.printSingleListNode(ret0);

        src0 = SingleListNode.prepareSingleListNode0();
        ret0 = sortN(src0);
        SingleListNode.printSingleListNode(ret0);

        int[] arr = {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        int k = 2;
        System.out.println(longestOnes(arr, k));
    }
}
