package me.meet.leetcode.easy;

import java.util.LinkedList;
import java.util.List;

public final class PalindromePartitioning {
    private PalindromePartitioning() {
    }
    /**
     * url: https://blog.csdn.net/zangdaiyang1991/article/details/89338652
     * url: https://blog.csdn.net/sinat_27908213/article/details/80599460
     *
     * palindrome-partitioning
     * Given a string s, partition s such that every substring of the partition is a palindrome.
     * Return all possible palindrome partitioning of s.
     *
     * For example, given s ="aab",
     * Return
     *   [
     *     ["aa","b"],
     *     ["a","a","b"]
     *   ]
     *
     */
    /**
     * 题意：给出所有可能的回文串分词
     * 思路：回溯算法实现（BackTracking）
     *
     * 回溯法有通用解法的美称，对于很多问题，如迷宫等都有很好的效果。
     * 回溯算法实际上一个类似枚举的深度优先搜索尝试过程，主要是在搜索尝试过程中寻找问题的解，当发现已不满足求解条件时，就“回溯”返回（也就是递归返回），尝试别的路径。
     * 回溯法说白了就是穷举法。回溯法一般用递归来实现。
     *
     * 回溯法一般都用在要给出多个可以实现最终条件的解的最终形式。
     * 回溯法要求对解要添加一些约束条件。
     * 总的来说，如果要解决一个回溯法的问题，通常要确定三个元素：
     * 1、选择。对于每个特定的解，肯定是由一步步构建而来的，而每一步怎么构建，肯定都是有限个选择，要怎么选择，这个要知道；同时，在编程时候要定下，优先或合法的每一步选择的顺序，一般是通过多个if或者for循环来排列。
     * 2、条件。对于每个特定的解的某一步，他必然要符合某个解要求符合的条件，如果不符合条件，就要回溯，其实回溯也就是递归调用的返回。
     * 3、结束。当到达一个特定结束条件时候，就认为这个一步步构建的解是符合要求的解了。把解存下来或者打印出来。对于这一步来说，有时候也可以另外写一个issolution函数来进行判断。注意，当到达第三步后，有时候还需要构建一个数据结构，把符合要求的解存起来，便于当得到所有解后，把解空间输出来。这个数据结构必须是全局的，作为参数之一传递给递归函数。
     *
     *
     */
    static List<List<String>> palindromePartitioning(String src) {
        if (null == src) {
            return null;
        }
        List<List<String>> result = new LinkedList<>();
        helper(result, new LinkedList<>(), src);
        return result;
    }

    private static void helper(List<List<String>> result, LinkedList<String> one, String src) {
        if (null == src || src.isEmpty()) {
            result.add(new LinkedList<>(one));                             // 添加当前所有回文串
            return;
        }
        int length = src.length();
        for (int i = 1; i <= length; i++) {
            String sub = src.substring(0, i);
            if (sub.equals(new StringBuilder(sub).reverse().toString())) { // 是回文串 isPalindrome
                one.add(sub);

                helper(result, one, src.substring(i, length));             // 处理后面的字符串

                one.removeLast();                                          // 恢复列表，删除列表的最后一个元素（必须要恢复递归前状态）
            }
        }
    }

    private static void testPalindromePartitioning() {
        String src = "aab";
        List<List<String>> list = palindromePartitioning(src);
        System.out.println(list);
    }

    /**
     * 练习回溯法
     * 题意：给出n对括号，求括号排列的所有可能性
     * 思路：回溯算法
     */
    static List<String> allPossibleParentheses(int n) {
        List<String> result = new LinkedList<>();
        parentheses(result, "", n, n);
        return result;
    }

    private static void parentheses(List<String> result, String one, int left, int right) {
        if (0 == left && 0 == right) {                                    // 3. 结束
            result.add(one);
        }
        if (right > left) {                                               // 1., 2. 选择/条件
            parentheses(result, one + ")", left, right - 1);
        }
        if (left > 0) {
            parentheses(result, one + "(", left - 1, right);
        }
    }

    private static void testAllPossibleParentheses() {
        int n = 3;
        List<String> res = allPossibleParentheses(n);
        System.out.println(res);
    }

//    /**
//     * 练习回溯法
//     * 题意：给出一个不重复大于0数字的数组和一个目标，求数组中数的组合的和得到该目标（数字不同组合顺序当做一个解）。
//     * 思路：回溯法
//     */

    public static void main(String[] args) {
        testPalindromePartitioning();
        testAllPossibleParentheses();
    }
}
