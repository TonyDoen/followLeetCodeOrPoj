package me.meet.leetcode.medium;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class CompleteBinaryTreeInserter {
    /**
     * Complete Binary Tree Inserter 完全二叉树插入器
     * A complete binary tree is a binary tree in which every level, except possibly the last, is completely filled, and all nodes are as far left as possible.
     * Write a data structure CBTInserter that is initialized with a complete binary tree and supports the following operations:
     * CBTInserter(TreeNode root) initializes the data structure on a given tree with head node root;
     * CBTInserter.insert(int v) will insert a TreeNode into the tree with value node.val = v so that the tree remains complete, and returns the value of the parent of the inserted TreeNode;
     * CBTInserter.get_root() will return the head node of the tree.
     *
     * Example 1:
     * Input: inputs = ["CBTInserter","insert","get_root"], inputs = [[[1]],[2],[]]
     * Output: [null,1,[1,2]]
     *
     * Example 2:
     * Input: inputs = ["CBTInserter","insert","insert","get_root"], inputs = [[[1,2,3,4,5,6]],[7],[8],[]]
     * Output: [null,3,4,[1,2,3,4,5,6,7,8]]
     *
     * Note:
     * The initial given tree is complete and contains between 1 and 1000 nodes.
     * CBTInserter.insert is called at most 10000 times per test case.
     * Every value of a given or inserted node is between 0 and 5000.
     *
     *
     * 题意：完全二叉树插入器
     */
    static class CBTInserter {
        TreeNode root;
        Deque<TreeNode> deque;
        public CBTInserter(TreeNode root) {
            this.root = root;
            deque = new LinkedList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            // BFS to populate deque
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node.left == null || node.right == null)
                    deque.offerLast(node);
                if (node.left != null)
                    queue.offer(node.left);
                if (node.right != null)
                    queue.offer(node.right);
            }
        }

        public int insert(int v) {
            TreeNode node = deque.peekFirst();
            deque.offerLast(new TreeNode(v));
            if (node.left == null)
                node.left = deque.peekLast();
            else {
                node.right = deque.peekLast();
                deque.pollFirst();
            }

            return node.val;
        }

        public TreeNode getRoot() {
            return root;
        }
    }

    static class TreeNode {
        Integer val;
        TreeNode left;
        TreeNode right;
        TreeNode(Integer val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        CBTInserter cbti1 = new CBTInserter(new TreeNode(1));
        cbti1.insert(2);
        TreeNode res1 = cbti1.getRoot();
        System.out.println(res1);

        int[] arr = new int[]{2,3,4,5,6};
        CBTInserter cbti2 = new CBTInserter(new TreeNode(1));
        for (int i : arr) {
            cbti2.insert(i);
        }
        cbti2.insert(7);
        cbti2.insert(8);
        TreeNode res2 = cbti2.getRoot();
        System.out.println(res2);

    }
}
