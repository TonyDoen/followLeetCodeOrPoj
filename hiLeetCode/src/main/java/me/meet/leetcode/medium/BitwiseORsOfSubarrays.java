package me.meet.leetcode.medium;

import java.util.HashSet;
import java.util.Set;

public final class BitwiseORsOfSubarrays {
    private BitwiseORsOfSubarrays() {}

    /**
     * We have an array A of non-negative integers.
     * For every (contiguous) subarray B = [A[i], A[i+1], ..., A[j]] (with i <= j), we take the bitwise OR of all the elements in B, obtaining a result A[i] | A[i+1] | ... | A[j].
     * Return the number of possible results.  (Results that occur more than once are only counted once in the final answer.)
     *
     * Example 1:
     * Input: [0]
     * Output: 1
     * Explanation:
     * There is only one possible result: 0.
     *
     * Example 2:
     * Input: [1,1,2]
     * Output: 3
     * Explanation:
     * The possible subarrays are [1], [1], [2], [1, 1], [1, 2], [1, 1, 2].
     * These yield the results 1, 1, 2, 1, 3, 3.
     * There are 3 unique values, so the answer is 3.
     *
     * Example 3:
     * Input: [1,2,4]
     * Output: 6
     * Explanation:
     * The possible results are 1, 2, 3, 4, 6, and 7.
     *
     * Note:
     * 1 <= A.length <= 50000
     * 0 <= A[i] <= 10^9
     */
    /**
     * 题意：子数组按位或操作
     * 一道蛮有意思的题目，说是给了我们一个数组，里面都是非负数，问我们所有连续的子数组'或'起来能产生多少个不同的值。
     *
     * 思路：
     * 虽说这只是一道 Medium 的题，但是直觉告诉博主，暴力遍历所有子数组，并且一个一个的'或'将会产生大量的重复运算，不出意外应该是会 TLE 的。
     *
     * 参考了 网上大神 zhoubowei 的帖子
     * 举个例子吧，比如数组 [0, 3, 4, 6, 5]，写成二进制的就是 [001, 011, 100, 110, 101]，生成子数组的方法跟生成子集合 Subsets 有些类似，但由于子数组必须是连续的，所以个数比子集合要少一些，生成的方法也是在现有的集合都加入当前数字，并每次新加一个只有当前数字的集合，顺序如下：
     * [001]
     * [001 011] [011]
     * [001 011 100] [011 100] [100]
     * [001 011 100 110] [011 100 110] [100 110] [110]
     * [001 011 100 110 101] [011 100 110 101] [100 110 101] [110 101] [101]
     *
     * 我们可以看到，最开始就只有一个集合 [001]，然后对于数字 011，先放到现有集合中，变成 [001 011]，然后再新建一个自己的集合 [011]，对于后面的数字都是同样的操作，最后我们就有5个不同的集合，代表了所有的子数组，我们对每个集合都计算总'或'值，可以得到：
     * 001
     * 011 011
     * 111 111 100
     * 111 111 110 110
     * 111 111 111 111 101
     *
     * 之前提到了，若对于每个集合都一个一个的'或'起来，将会十分的不高效，而其实这里面可能会有许多重复值，所以对重复值只需要保留一个，实际上就可以变成：
     * 001
     * 011
     * 111 100
     * 111 110
     * 111 101
     *
     * 这样数字就减少了很多，使得计算效率也就大大的提高了。具体的做法是，开始先建立两个 HashSet，分别是 res 和 cur，然后遍历数组A，对于每个遍历到的数字，首先生成一个自己的集合 tmp，然后遍历集合 cur 中的所有数字，将当前数字和 cur 中的每个数字相'或'，并存入 tmp 中，由于 HashSet 可以自动去重复，所以 tmp 中保存的只有不同的值，然后将 tmp 全部赋值给 cur，再将 cur 中的所有值加入结果 res 中，由于结果 res 也是 HashSet，也可以自动去重复，最后留在 res 中的就是所有不同的子数组的总'或'值，参见代码如下：
     */
    static int subarrayBitwiseORs(int[] A) {
        Set<Integer> res = new HashSet<>(), cur = new HashSet<>();
        for (int i : A) {
            Set<Integer> tmp = new HashSet<>();
            tmp.add(i);
            for (int j : cur) tmp.add(i | j);
            cur = tmp;
            res.addAll(cur);
        }
        return res.size();
    }

    public static void main(String[] args) {
        int[] arr = new int[]{0};
        arr = new int[]{1,1,2};
        arr = new int[]{0, 3, 4, 6, 5};
        int res = subarrayBitwiseORs(arr);
        System.out.println(res);
    }
}
