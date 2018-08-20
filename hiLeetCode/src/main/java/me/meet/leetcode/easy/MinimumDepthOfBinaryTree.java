package me.meet.leetcode.easy;

public final class MinimumDepthOfBinaryTree {
    private MinimumDepthOfBinaryTree() {}

    static class Node {
        int val;
        Node left;
        Node right;
        Node(int val) {
            this.val = val;
        }
    }
    /**
     * Given a binary tree, find its minimum depth.The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
     */
    /**
     * 题意：
     * 求根节点到叶子节点的最短路径的节点个数。
     *
     * 思路：
     * 1、递归解法，求左右子树深度，两者中小的加1即为最终深度。但是，需要注意左节点/右节点为空时，需要返回根节点到右节点/左节点的深度。
     * 2、非递归解法，按层遍历，叠加level
     */
    static int getDepth(Node root) {
        if (root == null) {
            return 0;
        }

        int left = getDepth(root.left);
        int right = getDepth(root.right);

        // 需要注意左节点为空时，需要返回根节点到右叶子节点的深度
        if (left == 0 || right == 0) {
            return 1 + left + right;
        }

        // 两者中小的加1即为最终深度
        return left < right ? left + 1 : right + 1;
    }

    private static void testGetDepth() {
        Node res = createBinaryTree();
        int depth = getDepth(res);
        System.out.println(depth);
    }

    private static Node createBinaryTree() {


        Node root = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);

        root.left = node1;
        root.right = node2;

        Node node3 = new Node(3);
        node1.left = node3;

        Node node4 = new Node(4);
        node3.left = node4;

        return root;
    }

    public static void main(String[] args) {
        testGetDepth();
    }
}
