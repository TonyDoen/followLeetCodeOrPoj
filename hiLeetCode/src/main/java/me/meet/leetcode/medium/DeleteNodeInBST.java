package me.meet.leetcode.medium;

public final class DeleteNodeInBST {
    private DeleteNodeInBST() {}

    /**
     url: http://www.cnblogs.com/grandyang/p/6228252.html
     url: https://leetcode.com/problems/delete-node-in-a-bst/discuss/93296/recursive-easy-to-understand-java-solution

     Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.
     Basically, the deletion can be divided into two stages:
     Search for a node to remove.
     If the node is found, delete the node.
     Note: Time complexity should be O(height of tree).

     Example:
     root = [5,3,6,2,4,null,7]
     key = 3

         5
        / \
       3   6
      / \   \
     2   4   7

     Given key to delete is 3. So we find the node with value 3 and delete it.
     One valid answer is [5,4,6,2,null,null,7], shown in the following BST.

         5
        / \
       4   6
      /     \
     2       7

     Another valid answer is [5,2,6,null,4,null,7].

       5
      / \
     2   6
      \   \
       4   7
     */

    /**
     题意：删除二叉搜索树中的节点
     这道题让我们删除二叉搜索树中的一个节点，这道题的难点在于删除完节点并补上那个节点的位置后还应该是一棵二叉搜索树。被删除掉的节点位置，不一定是由其的左右子节点补上，比如下面这棵树：
           7                                                              7
         /   \                                                          /  \
        4     8             如果我们要删除节点4，                        5     8
      /   \                 那么应该将节点5补到4的位置，                /  \
     2     6                这样才能保证还是BST                      2     6
      \    /               ------------------------->               \
       3  5                                                          3

     先来看一种递归的解法，首先判断根节点是否为空。由于BST的左<根<右的性质，使得我们可以快速定位到要删除的节点，我们对于当前节点值不等于key的情况，根据大小关系对其左右子节点分别调用递归函数。若当前节点就是要删除的节点，我们首先判断是否有一个子节点不存在，那么我们就将root指向另一个节点，如果左右子节点都不存在，那么root就赋值为空了，也正确。难点就在于处理左右子节点都存在的情况，我们需要在右子树找到最小值，即右子树中最左下方的节点，然后将该最小值赋值给root，然后再在右子树中调用递归函数来删除这个值最小的节点
     */


    // BST的左<根<右的性质
    static TreeNode deleteNode(TreeNode root, int key) {
        if (null == root) return null;
        if (root.data > key) {
            root.left = deleteNode(root.left, key);
        } else if (root.data < key) {
            root.right = deleteNode(root.right, key);
        } else {
            boolean leftNull = (null == root.left);
            boolean rightNull = (null == root.right);

            if (leftNull || rightNull) {
                root = leftNull ? root.right: root.left;
            } else {
                TreeNode cur = root.right;
                while (null != cur.left) cur = cur.left;
                root.data = cur.data;
                root.right = deleteNode(root.right, cur.data);
            }
        }
        return root;
    }

    // 还是通过BST的性质来快速定位要删除的节点，如果没找到直接返回空。遍历的过程要记录上一个位置的节点pre，如果pre不存在，说明要删除的是根节点，如果要删除的节点在pre的左子树中，那么pre的左子节点连上删除后的节点，反之pre的右子节点连上删除后的节点。在删除函数中，如果左右子节点都不存在，那么返回空；如果有一个不存在，那么我们返回那个存在的；难点还是在于处理左右子节点都存在的情况，还是要找到需要删除节点的右子树中的最小值，然后把最小值赋值给要删除节点，然后就是要处理最小值可能存在的右子树的连接问题，如果要删除节点的右子节点没有左子节点了的话，那么最小值的右子树直接连到要删除节点的右子节点上即可(因为此时原本要删除的节点的值已经被最小值替换了，所以现在其实是要删掉最小值节点)。否则我们就把最小值节点的右子树连到其父节点的左子节点上。
    static TreeNode deleteNode2(TreeNode root, int key) {
        TreeNode cur = root, pre = null;
        while (null != cur) {
            if (cur.data == key) break;
            pre = cur;
            if (cur.data > key) cur = cur.left;
            else cur = cur.right;
        }
        if (null == cur) return root;
        if (null == pre) return del(cur);
        if (null != pre.left && pre.left.data == key) pre.left = del(cur);
        else pre.right = del(cur);
        return root;
    }
    private static TreeNode del(TreeNode node) {
        boolean leftNull = (null == node.left);
        boolean rightNull = (null == node.right);

        if (leftNull && rightNull) return null;
        if (leftNull || rightNull) {
            return rightNull ? node.left : node.right;
        }
        TreeNode pre = node, cur = node.right;
        while (null != cur.left) {
            pre = cur;
            cur = cur.left;
        }
        node.data = cur.data;
        if (pre == node) {
            node.right = cur.right;
        } else {
            pre.left = cur.right;
        }
        return node;
    }

    // 面来看一种对于二叉树通用的解法，适用于所有二叉树，所以并没有利用BST的性质，而是遍历了所有的节点，然后删掉和key值相同的节点
    static TreeNode deleteNode3(TreeNode root, int key) {
        if (null == root) return null;
        if (root.data == key) {
            if (null == root.right) return root.left;
            else {
                TreeNode cur = root.right;
                while (null != cur.left) cur = cur.left;

                // swap(root->val, cur->val);
                Integer val = root.data;
                root.data = cur.data;
                cur.data = val;
            }
        }
        root.left = deleteNode3(root.left, key);
        root.right = deleteNode3(root.right, key);
        return root;
    }

    public static void main(String[] args) {
        TreeNode res = deleteNode(prepareTree(), 4);
        TreeNode res2 = deleteNode2(prepareTree(), 4);
        TreeNode res3 = deleteNode3(prepareTree(), 4);

        System.out.println(res);
        System.out.println(res2);
        System.out.println(res3);
    }

    private static TreeNode prepareTree() {
        /**
               7
             /   \
            4     8
          /   \
         2     6
          \    /
           3  5
         */
        TreeNode _3 = new TreeNode(3);
        TreeNode _5 = new TreeNode(5);
        TreeNode _2 = new TreeNode(2, null, _3);
        TreeNode _6 = new TreeNode(6, _5, null);
        TreeNode _4 = new TreeNode(4, _2, _6);
        TreeNode _8 = new TreeNode(8, null, null);
        TreeNode _7 = new TreeNode(7, _4, _8);

        return _7;
    }

    private static class TreeNode {
        Integer data;
        TreeNode left;
        TreeNode right;
        TreeNode(Integer data) {
            this(data, null, null);
        }
        TreeNode(Integer data, TreeNode left, TreeNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
}
