package me.meet.leetcode.easy;

public final class MaximumSubarray {
    /**
     * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
     *
     * Example:
     * Input: [-2,1,-3,4,-1,2,1,-5,4],
     * Output: 6
     * Explanation: [4,-1,2,1] has the largest sum = 6.
     *
     * Follow up:
     * If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
     */

    /**
     * 题意：最大子数组
     * 思路：这道题让求最大子数组之和，并且要用两种方法来解，分别是 O(n) 的解法，还有用分治法 Divide and Conquer Approach，这个解法的时间复杂度是 O(nlgn)，那就先来看 O(n) 的解法，定义两个变量 res 和 curSum，其中 res 保存最终要返回的结果，即最大的子数组之和，curSum 初始值为0，每遍历一个数字 num，比较 curSum + num 和 num 中的较大值存入 curSum，然后再把 res 和 curSum 中的较大值存入 res，以此类推直到遍历完整个数组，可得到最大子数组的值存在 res 中
     */
    static int maxSubArray1(int[] nums) {
        int res = Integer.MIN_VALUE, curSum = 0;
        for (int num : nums) {
            curSum = Math.max(curSum + num, num);
            res = Math.max(res, curSum);
        }
        return res;
    }

    /**
     * 思路：题目还要求我们用分治法 Divide and Conquer Approach 来解，这个分治法的思想就类似于二分搜索法，需要把数组一分为二，分别找出左边和右边的最大子数组之和，然后还要从中间开始向左右分别扫描，求出的最大值分别和左右两边得出的最大值相比较取最大的那一个，代码如下：
     */
    static int maxSubArray2(int[] nums) {
        if (nums.length == 0) return 0;
        return helper(nums, 0, nums.length - 1);
    }

    private static int helper(int[] nums, int left, int right) {
        if (left >= right) return nums[left];
        int mid = left + (right - left) / 2;
        int lmax = helper(nums, left, mid - 1);
        int rmax = helper(nums, mid + 1, right);

        int mmax = nums[mid], t = mmax;
        for (int i = mid - 1; i >= left; --i) {
            t += nums[i];
            mmax = Math.max(mmax, t);
        }
        t = mmax;
        for (int i = mid + 1; i <= right; ++i) {
            t += nums[i];
            mmax = Math.max(mmax, t);
        }
        return Math.max(mmax, Math.max(lmax, rmax));
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int res = maxSubArray1(nums);
        System.out.println(res);

        res = maxSubArray2(nums);
        System.out.println(res);
    }
}
