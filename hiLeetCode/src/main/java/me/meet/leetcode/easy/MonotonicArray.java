package me.meet.leetcode.easy;

public final class MonotonicArray {
    private MonotonicArray() {
    }
    /**
     * An array is monotonic if it is either monotone increasing or monotone decreasing.
     * An array A is monotone increasing if for all i <= j, A[i] <= A[j].  An array A is monotone decreasing if for all i <= j, A[i] >= A[j].
     * Return true if and only if the given array A is monotonic.
     *
     * Example 1:
     * Input: [1,2,2,3]
     * Output: true
     *
     * Example 2:
     * Input: [6,5,4,4]
     * Output: true
     *
     * Example 3:
     * Input: [1,3,2]
     * Output: false
     *
     * Example 4:
     * Input: [1,2,4,5]
     * Output: true
     *
     * Example 5:
     * Input: [1,1,1]
     * Output: true
     *
     * Note:
     * 1 <= A.length <= 50000
     * -100000 <= A[i] <= 100000
     */
    /**
     * 单调数组
     * 这道题让我们判断一个数组是否单调，单调数组就是说这个数组的数字要么是递增的，要么是递减的，不存在一会儿递增一会儿递减的情况，即不会有山峰存在。这里不是严格的递增或递减，是允许有相同的数字的。
     *
     * 思路：
     * 那么我们直接将相邻的两个数字比较一下即可，使用两个标识符，inc 和 dec，初始化均为 true，我们开始时假设这个数组既是递增的又是递减的，当然这是不可能的，我们会在后面对其进行更新。在遍历数组的时候，只要发现某个数字大于其身后的数字了，那么 inc 就会赋值为 false，同理，只要某个数字小于其身后的数字了，dec 就会被赋值为 false，所以在既有递增又有递减的数组中，inc 和 dec 都会变为 false，而在单调数组中二者之间至少有一个还会保持为 true，参见代码如下：
     */
    static boolean isMonotonic(int[] arr) {
        boolean inc = true, dec = true;
        for (int i = 1; i < arr.length; i++) {
            inc &= (arr[i - 1] <= arr[i]);
            dec &= (arr[i - 1] >= arr[i]);
            if (!inc && !dec) {
                return false;
            }
        }
        return true;
    }

    /**
     * 思路：
     * 跟上面的解法思路很像，只不过没有用 bool 型的，而是用了整型数字来记录递增和递减的个数，若是单调数组，那么最终在 inc 和 dec 中一定会有一个值是等于数组长度的，参见代码如下：
     */
    static boolean isMonotonic2(int[] arr) {
        int inc = 1, dec = 1, n = arr.length;
        for (int i = 1; i < n; i++) {
            inc += (arr[i - 1] <= arr[i]) ? 1 : 0;
            dec += (arr[i - 1] >= arr[i]) ? 1 : 0;
        }
        return (inc == n) || (dec == n);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 2, 3};
        boolean res = isMonotonic(arr);
        System.out.println(res);

        res = isMonotonic2(arr);
        System.out.println(res);
    }
}
