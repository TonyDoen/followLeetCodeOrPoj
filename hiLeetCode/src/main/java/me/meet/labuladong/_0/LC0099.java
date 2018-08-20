package me.meet.labuladong._0;

import me.meet.labuladong.common.TreeNode;

public final class LC0099 {
    private LC0099() {
    }

    /**
     *
     * 【LeetCode】99. Recover Binary Search Tree 解题报告
     * 二叉搜索树中的两个节点被错误地交换。请在不改变其结构的情况下，恢复这棵树。
     *
     * LeetCode 题目 99 的名字叫做 "Recover Binary Search Tree"。
     * 这道题目要求恢复一棵因为两个节点位置错误而导致不满足二叉搜索树（BST）性质的树。
     * 在一个有效的BST中，对于任何一个节点，它的左子树上所有节点的值都小于它本身的值，同样地，它的右子树上所有节点的值都大于它本身的值。
     *
     * 一个有效的解法是通过中序遍历来找出那两个被错误交换的节点，并将它们恢复到正确的位置。下面提供一个 Java 方法作为示例：
     */

    /**
     * 思路1：中序遍历将数值取出来，然后排序，然后再插入进入，使用的空间复杂度是O(n)
     *
     * 对一个BST进行中序遍历，结果是一个递增的序列。如果一个BST两个结点交换了，那么中序遍历的结果中对应结点的值也会交换。
     */
    static TreeNode firstElement = null;
    static TreeNode secondElement = null;
    // The reason for this initialization is to avoid null pointer exception in the first comparison when prevElement has not been initialized
    static TreeNode prevElement = new TreeNode(Integer.MIN_VALUE);

    public static void recoverTree(TreeNode root) {
        // In order traversal to find the two elements
        traverse(root);

        // Swap the values of the two nodes
        int temp = firstElement.getVal();
        firstElement.setVal(secondElement.getVal());
        secondElement.setVal(temp);
    }

    private static void traverse(TreeNode root) {
        if (root == null) return;
        traverse(root.getLeft());

        // Start of "do some business",
        // If first element has not been found, assign it to prevElement (refer to 6 in the example above)
        if (firstElement == null && prevElement.getVal() >= root.getVal()) {
            firstElement = prevElement;
        }

        // If first element is found, assign the second element to the root (refer to 2 in the example above)
        if (firstElement != null && prevElement.getVal() >= root.getVal()) {
            secondElement = root;
        }
        prevElement = root;

        // End of "do some business"
        traverse(root.getRight());
    }

    /**
     * 思路2：对树进行中序遍历，在遍历过程中如果发现上一个节点的值大于当前节点的值，则说明上一个节点是被交换了的。
     * 一共会有两个节点会发生上述的问题，最后交换这两个节点的值。
     */
    private static TreeNode first = null;
    private static TreeNode second = null;
    private static TreeNode last = new TreeNode(Integer.MIN_VALUE);

    static void recoverBST2(TreeNode node) {
        if (null == node) {
            return ;
        }
        inOrder(node);

        // swap
        int tmp = first.getVal();
        first.setVal(second.getVal());
        second.setVal(tmp);
    }

    private static void inOrder(TreeNode node) {
        if (null == node) {
            return;
        }

        inOrder(node.getLeft());

        if (null == first && last.getVal() > node.getVal()) {
            first = last;
        }
        if (null != first && last.getVal() > node.getVal()) {
            second = node;
        }
        last = node;

        inOrder(node.getRight());
    }

    private static void testRecoverTree0() {
        TreeNode root = TreeNode.prepareErrorBST0();

        // Print the original tree in-order (should be an incorrect BST)
        System.out.println("Original tree inorder:");
        root.print();

        // Recover the tree
        recoverTree(root);

        // Print the recovered tree in-order (should be a correct BST)
        System.out.println("\nRecovered tree inorder:");
        root.print();
    }

    private static void testRecoverBST2() {
        TreeNode root = TreeNode.prepareErrorBST0();

        // Print the original tree in-order (should be an incorrect BST)
        System.out.println("Original tree inorder:");
        root.print();

        // Recover the tree
        recoverBST2(root);

        // Print the recovered tree in-order (should be a correct BST)
        System.out.println("\nRecovered tree inorder:");
        root.print();
    }

    public static void main(String[] args) {
        // LeetCode 题目 99 的名字叫做 "Recover Binary Search Tree"
        // 二叉搜索树中的两个节点被错误地交换。请在不改变其结构的情况下，恢复这棵树。
        testRecoverTree0();
        testRecoverBST2();
    }
}
