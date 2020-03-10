package me.meet.leetcode.easy;

import java.util.LinkedList;
import java.util.List;

public final class PathSum4 {
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
    static List<List<Integer>> hasPathSum(TreeNode root, int sum) {
        List<List<Integer>> res = new LinkedList<>();
        helper(res, new LinkedList<>(), root, sum);
        return res;
    }

    private static void helper(List<List<Integer>> res, LinkedList<Integer> path, TreeNode node, int sum) {
        //base case: leaf
        if (null == node.left && null == node.right) {
            if (node.val == sum) {
                path.add(node.val);
                res.add(new LinkedList<>(path));
                path.removeLast();
            }
            return;
        }

        //recursion rule: non-leaf
        path.add(node.val);
        if (null != node.left) {
            helper(res, path, node.left, sum - node.val);
        }
        if (null != node.right) {
            helper(res, path, node.right, sum - node.val);
        }
        path.removeLast();
    }

    /**
     * 第二种递归写法
     */
    static List<List<Integer>> hasPathSum2(TreeNode root, int sum) {
        List<List<Integer>> res = new LinkedList<>();
        if (null == root) {
            return res;
        }

        helper2(res, new LinkedList<>(), root, sum, 0);
        return res;
    }

    private static void helper2(List<List<Integer>> res, LinkedList<Integer> path, TreeNode node, int sum, int cur) {
        //base case: leaf
        if (null == node) {
            return;
        }
        path.add(node.val);
        if (null == node.left && null == node.right && cur + node.val == sum) {
            res.add(new LinkedList<>(path));
        }

        //recursion rule: non-leaf
        helper2(res, path, node.left, sum, cur + node.val);
        helper2(res, path, node.right, sum, cur + node.val);
        path.removeLast();
    }

    /**
     * 非递归写法
     */
    static List<List<Integer>> hasPathSum3(TreeNode root, int sum) {
        List<List<Integer>> res = new LinkedList<>();
        if (null == root) {
            return res;
        }

        LinkedList<TreeNode> st = new LinkedList<>();
        LinkedList<Integer> path = new LinkedList<>();
        TreeNode cur = root, pre = null;
        for (int curSum = 0; null != cur || !st.isEmpty(); ) {
            for (; null != cur;) {                              // 1. 当前节点cur只要不为空，先走到树的最左边节点(第一个while循环)；
                st.push(cur);
                curSum += cur.val;
                path.add(cur.val);
                cur = cur.left;
            }
            cur = st.peek();                                    // 2. 然后取栈顶元素，但是此时还要继续判断栈顶的右孩子的左子树，此时不能pop()，因为有孩子还有可能也是有左子树的；

//            assert cur != null;
            if (null != cur.right && pre != cur.right) {        // 3. pre节点的作用是为了回溯，记录前一个访问的节点，如果cur.right == pre，则说明右子树正在回溯，下面的已经访问完了；
                cur = cur.right;
            } else {                                            // 4. 右孩子为空　或者　已经访问过 此时先判断是否叶子 然后 开始回溯
                if (null == cur.left && null == cur.right && curSum == sum) {
                    res.add(new LinkedList<>(path));
                }

                st.pop();
                pre = cur;
                path.removeLast();
                curSum -= cur.val;
                cur = null;
            }
        }
        return res;
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
        TreeNode _5 = new TreeNode(5, null, null);
        TreeNode _1 = new TreeNode(1, null, null);

        TreeNode _11 = new TreeNode(11, _7, _2);
        TreeNode _13 = new TreeNode(13, null, null);
        TreeNode _4 = new TreeNode(4, _5, _1);

        TreeNode _4up = new TreeNode(4, _11, null);
        TreeNode _8 = new TreeNode(8, _13, _4);

        TreeNode _5up = new TreeNode(5, _4up, _8);

        List<List<Integer>> res = hasPathSum(_5up, 22);
        System.out.println(res);

        List<List<Integer>> res2 = hasPathSum2(_5up, 22);
        System.out.println(res2);

        List<List<Integer>> res3 = hasPathSum3(_5up, 22);
        System.out.println(res3);
    }

    public static void main(String[] args) {
        testHasPathSum();
    }


}
