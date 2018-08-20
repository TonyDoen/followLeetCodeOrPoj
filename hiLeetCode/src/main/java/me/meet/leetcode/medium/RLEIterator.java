package me.meet.leetcode.medium;

import java.util.LinkedList;

public final class RLEIterator {
    private RLEIterator() {
    }

    /**
     * Write an iterator that iterates through a run-length encoded sequence.
     * The iterator is initialized by RLEIterator(int[] A), where A is a run-length encoding of some sequence.  More specifically, for all even i, A[i] tells us the number of times that the non-negative integer value A[i+1] is repeated in the sequence.
     * The iterator supports one function: next(int n), which exhausts the next n elements (n >= 1) and returns the last element exhausted in this way.  If there is no element left to exhaust, next returns -1instead.
     * For example, we start with A = [3,8,0,9,2,5], which is a run-length encoding of the sequence [8,8,8,5,5].  This is because the sequence can be read as "three eights, zero nines, two fives".
     *
     * Example 1:
     * Input: ["RLEIterator","next","next","next","next"], [[[3,8,0,9,2,5]],[2],[1],[1],[2]]
     * Output: [null,8,8,5,-1]
     *
     * Explanation:
     * RLEIterator is initialized with RLEIterator([3,8,0,9,2,5]).
     * This maps to the sequence [8,8,8,5,5].
     * RLEIterator.next is then called 4 times:
     * .next(2) exhausts 2 terms of the sequence, returning 8.  The remaining sequence is now [8, 5, 5].
     * .next(1) exhausts 1 term of the sequence, returning 8.  The remaining sequence is now [5, 5].
     * .next(1) exhausts 1 term of the sequence, returning 5.  The remaining sequence is now [5].
     * .next(2) exhausts 2 terms, returning -1.  This is because the first term exhausted was 5,
     * but the second term did not exist.  Since the last term exhausted does not exist, we return -1.
     *
     * Note:
     * 0 <= A.length <= 1000
     * A.length is an even integer.
     * 0 <= A[i] <= 10^9
     * There are at most 1000 calls to RLEIterator.next(int n) per test case.
     * Each call to RLEIterator.next(int n) will have 1 <= n <= 10^9.
     */
    /**
     * 题意： RLE迭代器
     * 这道题给了我们一种 Run-Length Encoded 的数组，就是每两个数字组成一个数字对儿，前一个数字表示后面的一个数字重复出现的次数。然后有一个 next 函数，让我们返回数组的第n个数字，题目中给的例子也很好的说明了题意。那么最暴力的方法肯定是直接还原整个数组，然后直接用坐标n去取数，但是直觉告诉我这种方法会跪，而且估计是 Memory Limit Exceeded 之类的。所以博主最先想到的是将每个数字对儿抽离出来，放到一个新的数组中。这样我们就只要遍历这个只有数字对儿的数组，当出现次数是0的时候，直接跳过当前数字对儿。若出现次数大于等于n，那么现将次数减去n，然后再返回该数字。否则用n减去次数，并将次数赋值为0，继续遍历下一个数字对儿。若循环退出了，直接返回 -1 即可，参见代码如下：
     */

    static class RLEIteratorOne {
        static class Pair<K, V> {
            public K k;
            public V v;

            Pair(K k, V v) {
                this.k = k;
                this.v = v;
            }
        }

        private LinkedList<Pair<Integer, Integer>> seq = new LinkedList<>();

        RLEIteratorOne(int[] arr) {
            for (int i = 0; i < arr.length; i += 2) {
                if (0 != arr[i]) {
                    seq.push(new Pair<>(arr[i + 1], arr[i]));
                }
            }
        }

        int next(int n) {
            for (Pair<Integer, Integer> p : seq) { // error
                if (0 == p.v) {
                    continue;
                }
                if (n <= p.v) {
                    p.v -= n;
                    return p.k;
                }
                n -= p.v;
                p.v = 0;
            }
            return -1;
        }
    }

    /**
     * 思路：
     * 其实我们根本不用将数字对儿抽离出来，直接用输入数组的形式就可以，再用一个指针 cur，指向当前数字对儿的次数即可。那么在 next 函数中，我们首先来个 while 循环，判读假如 cur 没有越界，且当n大于当前当次数了，则n减去当前次数，cur 自增2，移动到下一个数字对儿的次数上。当 while 循环结束后，判断若此时 cur 已经越界了，则返回 -1，否则当前次数减去n，并且返回当前数字即可，参见代码如下：
     */
    static class RLEIterator2 {
        int cur;
        int[] arr;

        public RLEIterator2(int[] arr) {
            this.cur = 0;
            this.arr = arr;
        }

        public int next(int n) {
            while (cur < arr.length && n > arr[cur]) {
                n -= arr[cur];
                cur += 2;
            }
            if (cur >= arr.length) return -1;
            arr[cur] -= n;
            return arr[cur + 1];
        }
    }


    public static void main(String[] args) {
        int[] arr = new int[]{3, 8, 0, 9, 2, 5};
        RLEIteratorOne ro = new RLEIteratorOne(arr);

        int[] input = new int[]{2, 1, 1, 2};
        for (int i : input) {
            int res = ro.next(i);
            System.out.println(res);
        }

        RLEIterator2 rr = new RLEIterator2(arr);
        for (int i : input) {
            int res = rr.next(i);
            System.out.println(res);
        }
    }

}
