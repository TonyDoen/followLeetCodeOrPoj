package me.meet.labuladong._0;

import me.meet.labuladong.common.TreeNode;

public final class LC0124 {
    private LC0124() {
    }

    /**
     * LeetCode 124 题,难度 Hard,让你求二叉树中最大路径和,
     */
    private static int ans = Integer.MIN_VALUE;

    static int oneSideMax(TreeNode root) {
        if (null == root) {
            return 0;
        }

        int left = Math.max(0, oneSideMax(root.getLeft()));
        int right = Math.max(0, oneSideMax(root.getRight()));
        ans = Math.max(ans, left + right + root.getVal());
        return Math.max(left, right) + root.getVal();
    }

    public static void main(String[] args) {
        /**
         *         -1
         *       /   \
         *     2      -3
         *   /  \    /  \
         * 2     4  5   -8
         */
        TreeNode src = TreeNode.prepareTree1();
        src.println();

        int res = oneSideMax(src);
        System.out.println(res);
    }
}
