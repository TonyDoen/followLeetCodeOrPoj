package me.meet.leetcode.medium;

public class UniqueBinarySearchTrees {
    /**
     * Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
     *
     * Example:
     * Input: 3
     * Output: 5
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
     * 跟斐波那契数列一样，
     * 我们把 n = 0 时赋为1，因为空树也算一种二叉搜索树.
     * 那么 n = 1 时的情况可以看做是其左子树个数乘以右子树的个数，左右子树都是空树，所以1乘1还是1。
     * 那么 n = 2 时，由于1和2都可以为根，分别算出来，再把它们加起来即可。
     *     n = 2 的情况可由下面式子算出（这里的 dp[i] 表示当有i个数字能组成的 BST 的个数）：
     *     dp[2] =  dp[0] * dp[1]　　　(1为根的情况，则左子树一定不存在，右子树可以有一个数字)
     *            + dp[1] * dp[0]　　  (2为根的情况，则左子树可以有一个数字，右子树一定不存在)
     *
     * 同理
     * 写出 n = 3 的计算方法：
     *     dp[3] =  dp[0] * dp[2]　　　(1为根的情况，则左子树一定不存在，右子树可以有两个数字)
     *            + dp[1] * dp[1]　　  (2为根的情况，则左右子树都可以各有一个数字)
     *            + dp[2] * dp[0]　　  (3为根的情况，则左子树可以有两个数字，右子树一定不存在)
     *
     * 由此可以得出递推式为（卡塔兰数列）：
     * C(n+1) = C(0)*C(n) + C(1)*C(n-1) + ... + C(i)*C(n-i) + ... + C(n)*C(0)
     * 条件：C(0) = 1, C(1) = 1, C(2) = 2, n >= 0;
     */
    static int countUniqueBST1(int n) {
        int length = n + 1;
        int[] dp = new int[length];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i < length; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }
        return dp[n];
    }

    private static void testCountUniqueBST1() {
        int n = 3;
        System.out.println(countUniqueBST1(n));
    }

    /**
     * 由此可以得出递推式为（卡塔兰数列）：
     * C(n+1) = C(0)*C(n) + C(1)*C(n-1) + ... + C(i)*C(n-i) + ... + C(n)*C(0)
     * 条件：C(0) = 1, C(1) = 1, C(2) = 2, n >= 0;
     *
     * 推导出其通项公式，即 C(2n,n)/(n+1)
     * 表示在 2n 个数字中任取n个数的方法再除以 n+1
     */
    static int countUniqueBST2(int n) {
        int res = 1;
        for (int i = n + 1; i <= 2 * n; i++) {
            res = res * i / (i - n);
        }
        return res / (n + 1);
    }

    private static void testCountUniqueBST2() {
        int n = 3;
        System.out.println(countUniqueBST2(n));
    }

    public static void main(String[] args) {
        testCountUniqueBST1();
        testCountUniqueBST2();
    }
}
