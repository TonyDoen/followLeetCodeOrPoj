package me.meet.leetcode.medium;

import java.util.HashMap;
import java.util.Map;

public final class FruitIntoBaskets {
    private FruitIntoBaskets() {
    }
    /**
     * In a row of trees, the i-th tree produces fruit with type tree[i].
     * You start at any tree of your choice, then repeatedly perform the following steps:
     * Add one piece of fruit from this tree to your baskets.  If you cannot, stop.
     * Move to the next tree to the right of the current tree.  If there is no tree to the right, stop.
     * Note that you do not have any choice after the initial choice of starting tree: you must perform step 1, then step 2, then back to step 1, then step 2, and so on until you stop.
     * You have two baskets, and each basket can carry any quantity of fruit, but you want each basket to only carry one type of fruit each.
     * What is the total amount of fruit you can collect with this procedure?
     *
     * Example 1:
     * Input: [1,2,1]
     * Output: 3
     * Explanation: We can collect [1,2,1].
     *
     * Example 2:
     * Input: [0,1,2,2]
     * Output: 3 Explanation: We can collect [1,2,2].
     * If we started at the first tree, we would only collect [0, 1].
     *
     * Example 3:
     * Input: [1,2,3,2,2]
     * Output: 4 Explanation: We can collect [2,3,2,2].
     * If we started at the first tree, we would only collect [1, 2].
     *
     * Example 4:
     * Input: [3,3,3,1,2,1,1,2,3,3,4]
     * Output: 5  Explanation: We can collect [1,2,1,1,2].
     * If we started at the first tree or the eighth tree, we would only collect 4 fruits.
     *
     * Note:
     * 1 <= tree.length <= 40000
     * 0 <= tree[i] < tree.length
     *
     *
     * similar with LongestSubstringAtMostTwoDistinctCharacters
     */

    /**
     * 题意：水果装入果篮
     * 这道题说是给了我们一排树，每棵树产的水果种类是 tree[i]，说是现在有两种操作，第一种是将当前树的水果加入果篮中，若不能加则停止；第二种是移动到下一个树，若没有下一棵树，则停止。现在我们有两个果篮，可以从任意一个树的位置开始，但是必须按顺序执行操作一和二，问我们最多能收集多少个水果。说实话这道题的题目描述确实不太清晰，博主看了很多遍才明白意思，论坛上也有很多吐槽的帖子，但实际上这道题的本质就是从任意位置开始，若最多只能收集两种水果，问最多能收集多少个水果。那么再进一步提取，其实就是最多有两个不同字符的最长子串的长度，跟之前那道 Longest Substring with At Most Two Distinct Characters 一模一样，只不过换了一个背景，代码基本都可以直接使用的，博主感觉这样出题有点不太好吧，完全重复了。之前那题的四种解法这里完全都可以使用，先来看第一种，使用一个 HashMap 来记录每个水果出现次数，当 HashMap 中当映射数量超过两个的时候，我们需要删掉一个映射，做法是滑动窗口的左边界 start 的水果映射值减1，若此时减到0了，则删除这个映射，否则左边界右移一位。当映射数量回到两个的时候，用当前窗口的大小来更新结果 res 即可，
     */
    static int totalFruit0(int[] tree) {
        int res = 0, start = 0, n = tree.length;
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < n; i++) {
            m.put(tree[i], i);
            for (; m.size() > 2; ) {
                if (m.get(tree[start]) == start) {
                    m.remove(tree[start]);
                }
                start++;
            }
            res = Math.max(res, i - start + 1);
        }
        return res;
    }

    /**
     * 思路:
     * 还有一种不使用 HashMap 的解法，这里我们使用若干个变量，其中 cur 为当前最长子数组的长度，a和b为当前候选的两个不同的水果种类，cntB 为水果b的连续个数。我们遍历所有数字，假如遇到的水果种类是a和b中的任意一个，那么 cur 可以自增1，否则 cntB 自增1，因为若是新水果种类的话，默认已经将a种类淘汰了，此时候选水果由类型b和这个新类型水果构成，所以当前长度是 cntB+1。然后再来更新 cntB，假如当前水果种类是b的话，cntB 自增1，否则重置为1，因为 cntB 统计的就是水果种类b的连续个数。然后再来判断，若当前种类不是b，则此时a赋值为b， b赋值为新种类。最后不要忘了用 cur 来更新结果 res，
     */
    static int totalFruit2(int[] tree) {
        int res = 0, cur = 0, cntB = 0, a = 0, b = 0;
        for (int fruit : tree) {
            cur = (fruit == a || fruit == b) ? cur + 1 : cntB + 1;
            cntB = (fruit == b) ? cntB + 1 : 1;
            if (b != fruit) {
                a = b;
                b = fruit;
            }
            res = Math.max(res, cur);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] src = new int[]{0, 1, 2, 2};
        int res0 = totalFruit0(src);
        System.out.println(res0);

        int res = totalFruit2(src);
        System.out.println(res);
    }
}
