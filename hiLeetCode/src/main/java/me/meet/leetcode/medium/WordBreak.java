package me.meet.leetcode.medium;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 1、什么是动态规划？
 * 算法导论这本书是这样介绍这个算法的，动态规划与分治方法类似，都是通过组合子问题的解来来求解原问题的。
 * 再来了解一下什么是分治方法，以及这两者之间的差别，分治方法将问题划分为互不相交的子问题，递归的求解子问题，再将它们的解组合起来，求出原问题的解。
 * 而动态规划与之相反，动态规划应用与子问题重叠的情况，即不同的子问题具有公共的子子问题（子问题的求解是递归进行的，将其划分为更小的子子问题）。
 * 在这种情况下，分治方法会做许多不必要的工作，他会反复求解那些公共子子问题。
 * 而动态规划对于每一个子子问题只求解一次，将其解保存在一个表格里面，从而无需每次求解一个子子问题时都重新计算，避免了不必要的计算工作。
 *
 * 2、动态规划的应用场景：
 * 动态规划方法一般用来求解最优化问题。这类问题可以有很多可行解，每个解都有一个值，我们希望找到具有最优值的解，我们称这样的解为问题的一个最优解，而不是最优解，因为可能有多个解都达到最优值。
 * 我们解决动态规划问题一般分为四步：
 *    1、定义一个状态，这是一个最优解的结构特征
 *    2、进行状态递推，得到递推公式
 *    3、进行初始化
 *    4、返回结果
 *
 * 3、求解方式：
 * 动态规划算法的核心是记住已经求过的解，记住求解的方式有两种：
 * ①自顶向下的备忘录法 。计算目标值，计算出来后保存在缓存(可用数组)中。
 * ②自底向上。 先计算子问题，再由子问题计算父问题。
 */
public final class WordBreak {
    private WordBreak() {}

    /**
     * Given a string s and a dictionary of words dict, 
     * determine if s can be segmented into a space-separated sequence of one or more dictionary words.
     *
     * Note:
     * The same word in the dictionary may be reused multiple times in the segmentation.
     * You may assume the dictionary does not contain duplicate words.
     * 
     * Example 1:
     * Input: s = "leetcode", wordDict = ["leet", "code"]
     * Output: true
     * Explanation: Return true because "leetcode" can be segmented as "leet code".
     *
     * Example 2:
     * Input: s = "applepenapple", wordDict = ["apple", "pen"]
     * Output: true
     * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
     *              Note that you are allowed to reuse a dictionary word.
     *
     */
    /**
     * 题意：
     * 把dict中的元素进行组合，可以重复使用，是否可以拼成s
     *
     * 思路：
     * 1、求f(j) = s[0,j](j>=0)是否能被dict中的元素组合
     * 2、状态递推，求出s[0,i](i>j)是否能被dict中的元素组合，状态转移方程：f(i) = f(j) && f(j+1, i)  (j<i)
     * 3、最终求出f(sLen) = s [0, sLen]
     * 
     * 注意：
     * 1、初始为true f(0) = true
     * 2、初始状态数组大小为sLen+1
     * 3、结果返回数组的最后一个元素
     *
     */
    static boolean wordBreak0(String s, Set<String> dict) {
        if (null == s || s.isEmpty() || null == dict || dict.isEmpty()) {
            return false;
        }
        // 初始化存储状态值的数组
        int length = s.length();
        boolean[] state = new boolean[length+1];
        state[0] = true;
        //
        for (int i = 1; i <= length; i++) {
            for (int j = 0; j < i; j++) {
                // f(j) && f(j+1, i) 找到一个为true即可
                if (state[j] && dict.contains(s.substring(j, i))) {
                    state[i] = true;
                    break;
                }
            }
        }
        return state[length];
    }

    private static void testWordBreak0() {
        String s = "leetcode";
        Set<String> dict = new HashSet<>(Arrays.asList("leet", "code"));
        boolean res = wordBreak0(s, dict);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testWordBreak0();
    }
}
