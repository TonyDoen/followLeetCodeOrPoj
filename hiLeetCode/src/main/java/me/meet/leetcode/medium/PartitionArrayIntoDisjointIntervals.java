package me.meet.leetcode.medium;

public final class PartitionArrayIntoDisjointIntervals {
    /**
     * Given an array A, partition it into two (contiguous) subarrays left and right so that:
     * Every element in left is less than or equal to every element in right.
     * left and right are non-empty.
     * left has the smallest possible size.
     * Return the length of left after such a partitioning.  It is guaranteed that such a partitioning exists.
     *
     * Example 1:
     * Input: [5,0,3,8,6]
     * Output: 3
     * Explanation: left = [5,0,3], right = [8,6]
     *
     * Example 2:
     * Input: [1,1,1,0,6,12]
     * Output: 4
     * Explanation: left = [1,1,1,0], right = [6,12]
     *
     * Note:
     * 2 <= A.length <= 30000
     * 0 <= A[i] <= 10^6
     * It is guaranteed there is at least one way to partition A as described.
     */
    /**
     * 题意：分割数组为不相交的区间
     * 思路：这道题说是给了一个数组A，让我们分成两个相邻的子数组 left 和 right，使得 left 中的所有数字小于等于 right 中的，并限定了每个输入数组必定会有这么一个分割点，让返回数组 left 的长度。
     * 这道题并不算一道难题，当然最简单并暴力的方法就是遍历所有的分割点，然后去验证左边的数组是否都小于等于右边的数，这种写法估计会超时，这里就不去实现了。
     * 直接来想优化解法吧，由于分割成的 left 和 right 数组本身不一定是有序的，只是要求 left 中的最大值要小于等于 right 中的最小值，只要这个条件满足了，一定就是符合题意的分割。
     * left 数组的最大值很好求，在遍历数组的过程中就可以得到，
     * 而 right 数组的最小值怎么求呢？其实可以反向遍历数组，并且使用一个数组 backMin，其中 backMin[i] 表示在范围 [i, n-1] 范围内的最小值，
     * 有了这个数组后，再正向遍历一次数组，每次更新当前最大值 curMax，这就是范围 [0, i] 内的最大值，通过 backMin 数组快速得到范围 [i+1, n-1] 内的最小值，假如 left 的最大值小于等于 right 的最小值，则 i+1 就是 left 的长度，直接返回即可，
     */
    static int partitionDisjoint1(int[] arr) {
        int length = arr.length, curMax = Integer.MIN_VALUE;
        int[] backMin = new int[length];
        backMin[length - 1] = arr[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            backMin[i] = Math.min(backMin[i + 1], arr[i]);
        }
        for (int i = 0; i < length - 1; i++) {
            curMax = Math.max(curMax, arr[i]);
            if (curMax <= backMin[i + 1]) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * 思路：
     * 看论坛上的主流解法，只需要一次遍历即可，并且不需要额外的空间，
     * 这里使用三个变量，partitionIdx 表示分割点的位置，preMax 表示 left 中的最大值，curMax 表示当前的最大值。
     * 思路是遍历每个数字，更新当前最大值 curMax，并且判断若当前数字 A[i] 小于 preMax，说明这个数字也一定是属于 left 数组的，此时整个遍历到的区域应该都是属于 left 的，所以 preMax 要更新为 curMax，并且当前位置也就是潜在的分割点，所以 partitionIdx 更新为i。
     * 由于题目中限定了一定会有分割点，所以这种方法是可以得到正确结果的，
     */
    static int partitionDisjoint2(int[] arr) {
        int partitionIdx = 0, preMax = arr[0], curMax = preMax, length = arr.length;
        for (int i = 0; i < length; i++) {
            curMax = Math.max(curMax, arr[i]);
            if (arr[i] < preMax) {
                preMax = curMax;
                partitionIdx = i;
            }
        }
        return partitionIdx + 1;
    }

    public static void main(String[] args) {
        // [5,0,3,8,6]
        int[] arr = new int[]{5, 0, 3, 8, 6};
        int res = partitionDisjoint1(arr);
        System.out.println(res);

        res = partitionDisjoint2(arr);
        System.out.println(res);
    }

}
