package me.meet.leetcode.easy;

import java.util.LinkedList;

public final class PathSum {
    static class TreeNode {
        Integer val;
        TreeNode left;
        TreeNode right;
        TreeNode(Integer val, TreeNode l, TreeNode r) {
            this.val = val;
            this.left = l;
            this.right = r;
        }
    }
    /**
     * url: https://www.cnblogs.com/grandyang/p/4036961.html
     *
     * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
     * Note: A leaf is a node with no children.
     *
     * Example:
     * Given the below binary tree and sum = 22,
     *
     *       5
     *      / \
     *     4   8
     *    /   / \
     *   11  13  4
     *  /  \      \
     * 7    2      1
     *
     * return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
     */
    /**
     * 题意：给了一棵二叉树，问是否存在一条从跟结点到叶结点到路径，使得经过到结点值之和为一个给定的 sum 值
     * 思路：用深度优先算法 DFS 的思想来遍历每一条完整的路径，也就是利用递归不停找子结点的左右子结点，而调用递归函数的参数只有当前结点和 sum 值。
     */
    static boolean hasPathSum(TreeNode root, int sum) {
        if (null == root) {                                        // 1. 如果输入的是一个空结点，则直接返回 false，
            return false;
        }
        sum = sum - root.val;
        if (null == root.left && null == root.right && 0 == sum) { // 2. 如果如果输入的只有一个根结点，则比较当前根结点的值和参数 sum 值是否相同，若相同，返回 true，
            return true;
        }
        return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);

//        // bingo
//        if (null == root.left && null == root.right && root.val == sum) {
//            return true;
//        }
//        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }

    static boolean hasPathSum2(TreeNode root, int sum) {
        if (null == root) {
            return false;
        }
        LinkedList<TreeNode> st = new LinkedList<>();
        st.push(root);

        for (; !st.isEmpty();) { // 是先序遍历的迭代写法
            TreeNode top = st.pop();
            if (null == top.left && null == top.right) {
                if (top.val.equals(sum)) {
                    return true;
                }
            }
            if (null != top.right) {
                top.right.val += top.val;
                st.push(top.right);
            }
            if (null != top.left) {
                top.left.val += top.val;
                st.push(top.left);
            }
        }
        return false;
    }

    private static void testHasPathSum() {
        /**
         *       5
         *      / \
         *     4   8
         *    /   / \
         *   11  13  4
         *  /  \      \
         * 7    2      1
         *
         */
        TreeNode _7 = new TreeNode(7, null, null);
        TreeNode _2 = new TreeNode(2, null, null);
        TreeNode _1 = new TreeNode(1, null, null);

        TreeNode _11 = new TreeNode(11, _7, _2);
        TreeNode _13 = new TreeNode(13, null, null);
        TreeNode _4 = new TreeNode(4, null, _1);

        TreeNode _4up = new TreeNode(4, _11, null);
        TreeNode _8 = new TreeNode(8, _13, _4);

        TreeNode _5 = new TreeNode(5, _4up, _8);

//        boolean res = hasPathSum(_5, 22);
//        System.out.println(res);

        boolean res = hasPathSum2(_5, 22);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testHasPathSum();
    }


}
