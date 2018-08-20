package me.meet.leetcode.medium;

import java.util.*;

public class UniqueBinarySearchTrees2 {
    static class TreeNode {
        Integer val;
        TreeNode left;
        TreeNode right;

        TreeNode(Integer val) {
            this.val = val;
        }

        TreeNode(Integer val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
     *
     * Example:
     * Input: 3
     * Output:
     * [
     *   [1,null,3,2],
     *   [3,2,null,1],
     *   [3,1,null,null,2],
     *   [2,1,3],
     *   [1,null,2,null,3]
     * ]
     *
     * Explanation:
     * Given n = 3, there are a total of 5 unique BST's:
     *
     *    1         3     3      2      1
     *     \       /     /      / \      \
     *      3     2     1      1   3      2
     *     /     /       \                 \
     *    2     1         2                 3
     *
     */

    /**
     * 题意：独一无二的二叉搜索树
     * 思路：我们先来看当 n = 1 的情况，只能形成唯一的一棵二叉搜索树，n分别为 1,2,3 的情况如下所示：
     *
     * n = 0        空树
     *
     * n = 1         1
     *
     * n = 2      2      1
     *           /        \
     *          1          2
     *
     * n = 3      1        1          2           3        3
     *             \        \        / \         /        /
     *              3        2      1   3       2        1
     *             /          \                /          \
     *            2            3              1            2
     *
     * 这道题是之前的 Unique Binary Search Trees 的延伸，之前那个只要求算出所有不同的二叉搜索树的个数，
     * 道题让把那些二叉树都建立出来。
     * 用到了分治法 Divide and Conquer，划分左右子树，递归构造。
     * 1. 刚开始时，将区间 [1, n] 当作一个整体，
     * 2. 然后需要将其中的每个数字都当作根结点，其划分开了左右两个子区间，分别调用递归函数，得到的两个数组
     * 3. 从得到的两个数组中每次各取一个结点，当作当前根结点的左右子结点，
     */
    static List<TreeNode> generateUniqueBST1(int n) {
        if (n < 1) {
            return Collections.emptyList();
        }
        return helper(1, n);
    }

    private static List<TreeNode> helper(int start, int end) {
        LinkedList<TreeNode> res = new LinkedList<>();
        if (start > end) {
            res.push(null);
            return res;
        }
        for (int i = start; i <= end; i++) {            // 1. 刚开始时，将区间 [1, n] 当作一个整体，
            List<TreeNode> left = helper(start, i - 1); // 2. 然后需要将其中的每个数字都当作根结点，其划分开了左右两个子区间，分别调用递归函数
            List<TreeNode> right = helper(i + 1, end);
            for (TreeNode l : left) {
                for (TreeNode r : right) {              // 3. 从得到的两个数组中每次各取一个结点，当作当前根结点的左右子结点，
                    TreeNode cur = new TreeNode(i, l, r);
                    res.push(cur);
                }
            }
        }
        return res;
    }

    private static void testGenerateUniqueBST1() {
        List<TreeNode> res = generateUniqueBST1(3);
        System.out.print(res);
    }

    static List<TreeNode> generateUniqueBST2(int n) {
        if (n < 1) {
            return Collections.emptyList();
        }
        return helper2(1, n);
    }

    private static final Map<String, List<TreeNode>> orderMap = new LinkedHashMap<>();

    private static List<TreeNode> helper2(int start, int end) {
        LinkedList<TreeNode> res = new LinkedList<>();
        if (start > end) {
            res.push(null);
            return res;
        }
        // 优化1.1 避免递归中重复计算
        List<TreeNode> tmp = orderMap.get(start + "->" + end);
        if (null != tmp) {
            return tmp;
        }

        for (int i = start; i <= end; i++) {            // 1. 刚开始时，将区间 [1, n] 当作一个整体，
            List<TreeNode> left = helper2(start, i - 1); // 2. 然后需要将其中的每个数字都当作根结点，其划分开了左右两个子区间，分别调用递归函数
            List<TreeNode> right = helper2(i + 1, end);
            for (TreeNode l : left) {
                for (TreeNode r : right) {              // 3. 从得到的两个数组中每次各取一个结点，当作当前根结点的左右子结点，
                    TreeNode cur = new TreeNode(i, l, r);
                    res.push(cur);
                }
            }
        }

        // 优化1.2
        orderMap.put(start + "->" + end, res);
        return res;
    }

    private static void testGenerateUniqueBST2() {
        List<TreeNode> res = generateUniqueBST2(4);
        System.out.print(res);
    }


    public static void main(String[] args) {
//        testGenerateUniqueBST1();
        testGenerateUniqueBST2();
    }
}
