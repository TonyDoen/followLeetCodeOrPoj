package me.meet.leetcode.hard;

import java.util.LinkedList;

public final class SlidingWindowMaximum {
    private SlidingWindowMaximum() {
    }
    /**
     * Sliding Window Maximum
     * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Return the max sliding window.
     *
     * Example:
     * Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
     * Output: [3,3,5,5,6,7]
     *
     * Explanation:
     *
     * Window position                Max
     * -------------------------     -----
     * [1  3  -1] -3  5  3  6  7       3
     *  1 [3  -1  -3] 5  3  6  7       3
     *  1  3 [-1  -3  5] 3  6  7       5
     *  1  3  -1 [-3  5  3] 6  7       5
     *  1  3  -1  -3 [5  3  6] 7       6
     *  1  3  -1  -3  5 [3  6  7]      7
     *
     * Note:
     * You may assume k is always valid, 1 ≤ k ≤ input array's size for non-empty array.
     *
     * Follow up:
     * Could you solve it in linear time?
     *
     * Hint:
     * How about using a data structure such as deque (double-ended queue)?
     * The queue size need not be the same as the window’s size.
     * Remove redundant elements and the queue should store only elements that need to be considered.
     *
     */
    /**
     * 题意：滑动窗口最大值
     * 给定一个数组 nums,有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
     * 你只可以看到在滑动窗口 k 内的数字。滑动窗口每次只向右移动一位。返回滑动窗口最大值。
     * 
     * 思路：
     * 1、双端队列, 最大值放在队列头部
     * 2、如果一个值比它前面的值要大,那么它前面的值就永远不可能成为最大值
     *    因为当前元素比前面的元素大,则当前的窗口最大值为当前元素,
     *    否则为窗口中目前的最大值
     * 
     * 大概思路是用双向队列保存数字的下标,
     * 遍历整个数组,
     * 如果此时队列的首元素是 i-k 的话,表示此时窗口向右移了一步,则移除队首元素。
     * 然后比较队尾元素和将要进来的值,如果小的话就都移除,然后此时我们把队首元素加入结果中即可,
     */
    static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < 1 || k < 1) {
            return new int[0];
        }
        int length = nums.length;
        int[] res = new int[length - k + 1];
        LinkedList<Integer> deque = new LinkedList<>(); // 双端队列
        for (int i = 0; i < length; i++) {
            // 添加元素到队列,保证队列递增,比当前元素小的都弹出
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                // 队列中加入元素索引
                deque.removeLast();
            }
            // 队列中加入元素索引
            deque.addLast(i);
            // 需要移除队列中过期的元素
            if (i - deque.peekFirst() >= k) {
                deque.removeFirst();
            }
            // 如果队列中i>=k-1, 记录当前队列中的最大值
            if (i >= k - 1) {
                res[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return res;
    }

    private static void testMaxSlidingWindow() {
        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        int[] res = maxSlidingWindow(nums, k);
        for (int i : res) {
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) {
        testMaxSlidingWindow();
    }
}
