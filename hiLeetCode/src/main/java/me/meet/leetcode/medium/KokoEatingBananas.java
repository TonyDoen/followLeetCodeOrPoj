package me.meet.leetcode.medium;

public final class KokoEatingBananas {
    private KokoEatingBananas() {
    }
    /**
     * Koko loves to eat bananas.  There are N piles of bananas, the i-th pile has piles[i] bananas.  The guards have gone and will come back in H hours.
     * Koko can decide her bananas-per-hour eating speed of K.  Each hour, she chooses some pile of bananas, and eats K bananas from that pile.  If the pile has less than K bananas, she eats all of them instead, and won't eat any more bananas during this hour.
     * Koko likes to eat slowly, but still wants to finish eating all the bananas before the guards come back.
     * Return the minimum integer K such that she can eat all the bananas within Hhours.
     *
     * Example 1:
     * Input: piles = [3,6,7,11], H = 8
     * Output: 4
     *
     * Example 2:
     * Input: piles = [30,11,23,4,20], H = 5
     * Output: 30
     *
     * Example 3:
     * Input: piles = [30,11,23,4,20], H = 6
     * Output: 23
     *
     * Note:
     * 1 <= piles.length <= 10^4
     * piles.length <= H <= 10^9
     * 1 <= piles[i] <= 10^9
     */
    /**
     * 题意：科科吃香蕉
     * <p>
     * 这道题说有一只叫科科的猩猩，非常的喜欢吃香蕉，现在有N堆香蕉，每堆的个数可能不同，科科有H小时的时间来吃。要求是，每个小时内，科科只能选某一堆香蕉开始吃，若科科的吃速固定为K，即便在一小时内科科已经吃完了该堆的香蕉，也不能换堆，直到下一个小时才可以去另一堆吃。为了健康，科科想尽可能的吃慢一些，但同时也想在H小时内吃完所有的N堆香蕉，让我们找出一个最小的吃速K值。那么首先来想，既然每个小时只能吃一堆，总共要在H小时内吃完N堆，那么H一定要大于等于N，不然一定没法吃完N堆，这个条件题目中给了，所以就不用再 check 了。我们想一下K的可能的取值范围，当H无穷大的时候，科科有充足的时间去吃，那么就可以每小时只吃一根，也可以吃完，所以K的最小取值是1。那么当H最小，等于N时，那么一个小时内必须吃完任意一堆，那么K值就应该是香蕉最多的那一堆的个数，题目中限定了不超过 1e9，这就是最大值。所以要求的K值的范围就是 [1, 1e9]，固定的范围内查找数字，当然，最暴力的方法就是一个一个的试，凭博主多年与 OJ 抗衡的经验来说，基本可以不用考虑的。那么二分查找法就是不二之选了，我们知道经典的二分查找法，是要求数组有序的，而这里香蕉个数数组又不一定是有序的。这是一个很好的观察，但是要弄清楚到底是什么应该是有序的，要查找的K是吃速，跟香蕉堆的个数并没有直接的关系，而K所在的数组其实应该是 [1, 1e9] 这个数组，其本身就是有序的，所以二分查找没有问题。实际上这是博主之前那篇总结帖 LeetCode Binary Search Summary 二分搜索法小结 中的第四类 - 用子函数当作判断关系。当求出了 mid 之后，需要统计用该速度吃完所有的香蕉堆所需要的时间，统计的方法就是遍历每堆的香蕉个数，然后算吃完该堆要的时间。比如 K=4，那么假如有3个香蕉，需要1个小时，有4香蕉，还是1个小时，有5个香蕉，就需要两个小时，如果将三种情况融合为一个式子呢，就是用吃速加上香蕉个数减去1，再除以吃速即可，即 (pile+mid-1)/mid，大家可以自行带数字检验，是没有问题的。算出需要的总时间后去跟H比较，若小于H，说明吃的速度慢了，需要加快速度，所以 left 更新为 mid+1，否则 right 更新为 mid，最后返回 right 即可，参见代码如下：
     */
    static int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = (int) Math.pow(10.0, 9.0);
        while (left < right) {
            int mid = left + (right - left) / 2, cnt = 0;
            for (int pile : piles) cnt += (pile + mid - 1) / mid;
            if (cnt > h) left = mid + 1;
            else right = mid;
        }
        return right;
    }

    public static void main(String[] args) {
        int[] piles = new int[]{3, 6, 7, 11};
        int h = 4;
        int res = minEatingSpeed(piles, h);
        System.out.println(res);
    }
}
