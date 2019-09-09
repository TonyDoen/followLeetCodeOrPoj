package me.meet.leetcode.easy;

public final class IncreasingOrderSearchTree {
    private IncreasingOrderSearchTree() {}

    /**
     * Given a tree, rearrange the tree in in-order so that the leftmost node in the tree is now the root of the tree, and every node has no left child and only 1 right child.
     *
     * Example 1:
     * Input: [5,3,6,2,4,null,8,1,null,null,null,7,9]
     *       5
     *      / \
     *     3   6
     *    / \   \
     *   2   4   8
     *  /       / \
     * 1       7   9
     *
     * Output: [1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]
     *  1
     *   \
     *    2
     *     \
     *      3
     *       \
     *        4
     *         \
     *          5
     *           \
     *            6
     *             \
     *              7
     *               \
     *                8
     *                 \
     *                  9
     * Note:
     * The number of nodes in the given tree will be between 1 and 100.
     * Each node will have a unique integer value from 0 to 1000.
     */

    /**
     * 题意：递增顺序查找树
     * 这道题给了一棵二叉树，让我们对其进行重排序，使得最左结点变为根结点，而且整个树不能有左子结点，如题目中的例子所示，排序后的结果是一条向右下方延伸的直线。如果我们仔细观察题目中的例子，可以发现遍历顺序其实是 左->根->右，就是中序遍历的顺序，虽然题目中没说是二叉搜索树，但这并不影响我们进行中序遍历。我们先从最简单的例子开始分析，当 root 为空时，直接返回空，当 root 没有左右子结点时，也是直接返回 root。当 root 只有一个左子结点时，我们此时要把其左子结点变为根结点，将原来的根结点变成其原来的左子结点的右子结点。但是如果 root 只有一个右子结点，还是保持原来的顺序不变，而若 root 同时具有左右子结点的话，还是要将左子结点变为根结点，然后把之前的根结点连到右子结点上，之前的右子结点还连在之前的根结点上，这个不用改变。我们可以发现，最麻烦的就是左子结点了，需要和其根结点交换位置，所以对于每个结点，我们需要知道其父结点的位置，那么就在递归函数的参数中传入一个 pre 结点，再对左右子结点调用递归函数时，都将其下一个要连接的结点传入，这个 pre 结点可能是当前结点或者当前结点的父结点。
     * 在递归函数中，首先判空，若当前结点为空的话，直接返回 pre 结点，因为到空结点的时候，说明已经遍历到叶结点的下方了，那么 pre 就是这个叶结点了。由于是中序遍历，所以要先对左子结点调用递归函数，将返回值保存到一个新的结点 res 中，表示的意义是此时 node 的左子树已经全部捋直了，而且根结点就是 res，而且 node 结点本身也被连到了捋直后的左子树下，即此时左子结点和根结点已经完成了交换位子，当然要断开原来的连接，所以将 node->left 赋值为 nullptr。然后再对 node 的右子结点调用递归函数，注意此时的 pre 不能传入 node 本身，而是要传 node 结点的 pre 结点，这是因为右子结点后面要连接的是 node 的父结点，比如兑入下面这例子：
     */
    static class Node {
        private Integer data;
        private Node left;
        private Node right;

        public Node(Integer data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    static Node increasingBST(Node root) {
        return helper(root, null);
    }
    private static Node helper(Node node, Node pre) {
        if (null == node) {
            return pre;
        }
        Node res = helper(node.left, node);
        node.left = null;
        node.right = helper(node.right, pre);
        return res;
    }

    static Node prepareTree() {
        /**
               5
              / \
             3   6
            / \   \
           2   4   8
          /       / \
         1       7   9
         */
        Node _1 = new Node(1, null, null);
        Node _7 = new Node(7, null, null);
        Node _9 = new Node(9, null, null);
        Node _2 = new Node(2, _1, null);
        Node _4 = new Node(4, null, null);
        Node _8 = new Node(8, _7, _9);
        Node _3 = new Node(3, _2, _4);
        Node _6 = new Node(6, null, _8);
        return new Node(5, _3, _6);
    }
    static void inOrder(Node root) {
        if (null == root) {
            return;
        }
        System.out.println(root.data);
        inOrder(root.left);
        inOrder(root.right);
    }

    public static void main(String[] args) {
        Node root = prepareTree();
        Node res = increasingBST(root);
        inOrder(res);
    }

}
