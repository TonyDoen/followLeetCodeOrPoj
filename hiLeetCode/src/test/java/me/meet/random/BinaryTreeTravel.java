package me.meet.random;

import java.util.LinkedList;
import java.util.List;

public class BinaryTreeTravel {
    static class Node {
        int val;
        Node left;
        Node right;

        public Node() {}
        public Node(int v) {
            this.val = v;
        }
        public Node(int v, Node left, Node right) {
            this.val = v;
            this.left = left;
            this.right = right;
        }
    }

    public static int levelVisit(Node root) {
        if (null == root) {
            return -1;
        }
        int rs = root.val;

        List<Node> queue = new LinkedList<>();
        queue.add(root);
        // int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size(); // 每层的个数
            for (int i = 0; i < size; i++) {
                Node cur = queue.remove(0);
                if (null != cur.left) {
                    queue.add(cur.left);
                    rs = cur.left.val;
                }
                if (null != cur.right) {
                    queue.add(cur.right);
                }
            }
        }
        return rs;
    }

    public static Node prepare() {
        //     1
        //   /   \
        //  2      3
        //        /  \
        //       4    5
        Node _5 = new Node(5, null, null);
        Node _4 = new Node(4, null, null);
        Node _3 = new Node(3, _4, _5);
        Node _2 = new Node(2, null, null);
        Node _1 = new Node(1, _2, _3);

        return _1;
    }
    /*
     *        5
     *       / \
     *     3     7
     *   /  \   /  \
     *  1    4 6    8
     */
    private static Node prepareBST1() {
        Node _1 = new Node(1, null, null);
        Node _4 = new Node(4, null, null);
        Node _6 = new Node(6, null, null);
        Node _8 = new Node(8, null, null);
        Node _3 = new Node(3, _1, _4);
        Node _7 = new Node(7, _6, _8);
        Node _5 = new Node(5, _3, _7);

        return _5;
    }

    // 二叉树，最后一行最左边的值，
    // 尽可能最优 时间复杂度，空间复杂度
    // input:
    //     1
    //   /   \
    //  2      3
    //        /  \
    //       4    5
    //             \
    //               6
    //             /
    //           7
    // output:
    // 7
    /**
     * 返回二叉树最后一行最左边的值
     */
    public static void testFindLastLineTopLeft() {
        Node root = prepare();
        int rs = levelVisit(root);
        System.out.println(rs);
    }


    /**
     * 构建二叉树
     */
    public static void insert(Node root, int val) {
        if (null == root) {
            root = new Node();
            root.val = val;
            return;
        }
        if (val > root.val) {
            insert(root.right, val);
        } else {
            insert(root.left, val);
        }
    }

    /**
     * 遍历二叉树
     */
    public static void visit(Node root) {
        if (null == root) {
            return;
        }
        // 前序遍历
        System.out.println(root.val);
        visit(root.left);
        // 中序遍历
        visit(root.right);
        // 后序遍历
    }

    /**
     * 二叉树 转 链表
     */
    private static Node last = null;
    public static void convert0(Node root) {
        if (null == root) {
            return;
        }
        convert0(root.left);
        convert0(root.right);

        root.right = last;
        root.left = null;
        last = root;
    }

    private static void testConvert0() {
        Node root = prepare();
        convert0(root);
        System.out.print(root);
    }

    public static void convert1(Node root) {
        if (null == root) {
            return;
        }
        Node cur = root;
        while (null != cur) {
            if (null != cur.left) {
                Node p = cur.left;
                while (null != p.right) p = p.right;
                p.right = cur.right;

                cur.right = cur.left;
                cur.left = null;
            }
            cur = cur.right;
        }
    }

    private static void testConvert1() {
        Node root = prepare();
        convert1(root);
        System.out.println(root);
    }

    /**
     * 二叉搜索树 转 有序双向链表
     */
    private static Node lHead = null;
    private static Node rHead = null;
    public static Node convertB2D(Node root) {
        if (null == root) {
            return null;
        }

        convertB2D(root.left);
        if (null == rHead) {
            lHead = root;
        } else {
            rHead.right = root;
            root.left = rHead;
        }
        rHead = root;
        convertB2D(root.right);
        return lHead;
    }

    public static Node convertB2Dno(Node root) {
        if (null == root) {
            return null;
        }
        LinkedList<Node> st = new LinkedList<>();

        Node cur = root, pre = null; // 保存中序遍历的上一节点
        boolean flag = true;         // 标记
        while (null != cur || !st.isEmpty()) {
            while (null != cur) {
                st.push(cur);
                cur = cur.left;
            }
            cur = st.pop();
            if (flag) {
                root = cur;
                pre = root;
                flag = false;
            } else {
                pre.right = cur;
                cur.left = pre;
                pre = cur;
            }
            cur = cur.right;
        }
        return root;
    }

    private static void testConvertB2D() {
        Node root = prepareBST1();
        Node rs = convertB2D(root);
        System.out.println(rs);
    }

    private static void testConvertB2Dno() {
        Node root = prepareBST1();
        Node rs = convertB2Dno(root);
        System.out.println(rs);
    }


    public static void main(String[] args) {
        // 返回二叉树最后一行最左边的值
        testFindLastLineTopLeft();

        // 二叉树 转 链表
        testConvert0();
        testConvert1();

        // 二叉搜索树 转 有序双向链表
        testConvertB2D();
        testConvertB2Dno();
    }
}
