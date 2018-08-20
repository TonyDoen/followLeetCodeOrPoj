package me.meet.leetcode.easy;

public final class LongestContinuousIncreasingSubsequence {
    /**
     * Longest Continuous Increasing Subsequence
     * Given an unsorted array of integers, find the length of longest continuous increasing subsequence.
     *
     * Example 1:
     * Input: [1,3,5,4,7]
     * Output: 3
     * Explanation: The longest continuous increasing subsequence is [1,3,5], its length is 3.
     * Even though [1,3,5,7] is also an increasing subsequence, it's not a continuous one where 5 and 7 are separated by 4.
     *
     * Example 2:
     * Input: [2,2,2,2,2]
     * Output: 1
     * Explanation: The longest continuous increasing subsequence is [2], its length is 1.
     *
     * Note: Length of the array will not exceed 10,000.
     */
    /**
     * 题意：最长连续递增序列
     * 这道题让我们求一个数组的最长连续递增序列，由于有了连续这个条件，跟之前那道 Number of Longest Increasing Subsequence 比起来，其实难度就降低了很多。
     * <p>
     * 思路：
     * 可以使用一个计数器，如果遇到大的数字，计数器自增1；如果是一个小的数字，则计数器重置为1。用一个变量 cur 来表示前一个数字，初始化为整型最大值，当前遍历到的数字 num 就和 cur 比较就行了，每次用 cnt 来更新结果 res，
     */
    static int findLengthOfLCIS(int[] arr) {
        int res = 0, cnt = 0, cur = Integer.MAX_VALUE;
        for (int one : arr) {
            if (one > cur) {
                cnt++;
            } else {
                cnt = 1;
            }
            res = Math.max(res, cnt);
            cur = one;
        }
        return res;
    }

    private static void testFindLengthOfLCIS() {
        int[] arr = new int[]{1, 3, 5, 4, 7};
        int res = findLengthOfLCIS(arr);
        System.out.println(res);
    }

    /**
     * 思路：
     * 每次都和前面一个数字来比较，注意处理无法取到前一个数字的情况
     */
    static int findLengthOfLCIS0(int[] arr) {
        int res = 0, cnt = 0, length = arr.length;
        for (int i = 0; i < length; i++) {
            if (0 == i || arr[i - 1] < arr[i]) {
                cnt++;
                res = Math.max(res, cnt);
            } else {
                cnt = 1;
            }
        }
        return res;
    }

    private static void testFindLengthOfLCIS0() {
        int[] arr = new int[]{1, 3, 5, 4, 7};
        int res = findLengthOfLCIS0(arr);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testFindLengthOfLCIS();
        testFindLengthOfLCIS0();
    }
}
