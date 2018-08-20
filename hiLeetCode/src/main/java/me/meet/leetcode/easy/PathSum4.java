package me.meet.leetcode.easy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public final class PathSum4 {

    /**
     * url: https://www.cnblogs.com/grandyang/p/7570954.html
     *
     * If the depth of a tree is smaller than 5, then this tree can be represented by a list of three-digits integers.
     * For each integer in this list:
     * The hundreds digit represents the depth D of this node, 1 <= D <= 4.
     * The tens digit represents the position P of this node in the level it belongs to, 1 <= P <= 8. The position is the same as that in a full binary tree.
     * The units digit represents the value V of this node, 0 <= V <= 9.
     *
     * Given a list of ascending three-digits integers representing a binary with the depth smaller than 5. You need to return the sum of all paths from the root towards the leaves.
     *
     * Example 1:
     * Input: [113, 215, 221]
     * Output: 12
     *
     * Explanation:
     * The tree that the list represents is:
     *     3
     *    / \
     *   5   1
     *
     * The path sum is (3 + 5) + (3 + 1) = 12.
     *
     * Example 2:
     * Input: [113, 221]
     * Output: 4
     *
     * Explanation:
     * The tree that the list represents is:
     *     3
     *      \
     *       1
     *
     * The path sum is (3 + 1) = 4.
     *
     */
    /**
     * 题意：给了一棵二叉树，问是否存在一条从跟结点到叶结点到路径，使得经过到结点值之和为一个给定的 sum 值
     * 思路：用深度优先算法 DFS 的思想来遍历每一条完整的路径，也就是利用递归不停找子结点的左右子结点，而调用递归函数的参数只有当前结点和 sum 值。
     */
    static class ResultHolder { //error
        int val;
    }

    static int pathSum(int[] nums) {
        if (0 == nums.length) {
            return 0;
        }
        HashMap<Integer, Integer> m = new HashMap<>();
        for (int num : nums) {
            m.put(num / 10, num % 10);
        }
        ResultHolder res = new ResultHolder();
        helper(m, nums[0] / 10, 0, res);
        return res.val;
    }

    private static void helper(HashMap<Integer, Integer> m, int num, int cur, ResultHolder res) {
        int level = num / 10, pos = num % 10;
        int left = (level + 1) * 10 + 2 * pos - 1, right = left + 1;
        cur += m.get(num);

        if (null == m.get(left) && null == m.get(right)) {
            res.val += cur;
            return;
        }


        if (null != m.get(left)) {
            helper(m, left, cur, res);
        }
        if (null != m.get(right)) {
            helper(m, right, cur, res);
        }
    }

    private static void testPathSum() {
        int[] nums = new int[]{113, 215, 221};
        int res = pathSum(nums);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testPathSum();
    }

}
