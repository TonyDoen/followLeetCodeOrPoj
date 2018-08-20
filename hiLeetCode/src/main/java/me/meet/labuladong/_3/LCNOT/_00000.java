package me.meet.labuladong._3.LCNOT;

import java.util.Arrays;

public class _00000 {
    /**
     * 素数的定义很简单，如果一个数如果只能被 1 和它本身整除，那么这个数就是素数。
     *
     * 不要觉得素数的定义简单，恐怕没多少人真的能把素数相关的算法写得高效。本文就主要聊这样一个函数：
     * // 返回区间 [2, n) 中有几个素数
     * int countPrimes(int n)
     *
     * // 比如 countPrimes(10) 返回 4
     * // 因为 2,3,5,7 是素数
     *
     * int countPrimes(int n) {
     *     int count = 0;
     *     for (int i = 2; i < n; i++)
     *         if (isPrim(i)) count++;
     *     return count;
     * }
     *
     * // 判断整数 n 是否是素数
     * boolean isPrime(int n) {
     *     for (int i = 2; i < n; i++)
     *         if (n % i == 0)
     *             // 有其他整除因子
     *             return false;
     *     return true;
     * }
     *
     * 这样写的话时间复杂度 O(n^2)，问题很大。首先你用 isPrime 函数来辅助的思路就不够高效；而且就算你要用 isPrime 函数，这样实现也是存在计算冗余的。
     *
     * 该算法的时间复杂度比较难算，显然时间跟这个嵌套 for 循环有关，其操作数应该是：
     *    n/2 + n/3 + n/5 + n/7 + …
     * = n × (1/2 + 1/3 + 1/5 + 1/7…)
     *
     * 括号中是素数的倒数和。其最终结果是 O(N * loglogN)，有兴趣的读者可以查一下该算法的时间复杂度证明。
     *
     */
    static int countPrimes(int n) {
        boolean[] isPrim = new boolean[n];
        Arrays.fill(isPrim, true);
        for (int i = 2; i * i < n; i++)
            if (isPrim[i])
                for (int j = i * i; j < n; j += i)
                    isPrim[j] = false;

        int count = 0;
        for (int i = 2; i < n; i++)
            if (isPrim[i]) count++;

        return count;
    }

    private static void testCountPrimes() {
        int n = 20;
        int ret = countPrimes(n);
        System.out.println(ret);
    }

    /**
     * 说一个关于模运算的技巧吧，毕竟模运算在算法中比较常见：
     * (a*b)%k = (a%k)(b%k)%k
     *
     * 证明很简单，假设： a=Ak+B；b=Ck+D 其中 A,B,C,D 是任意常数，那么：
     * a%k = B;
     * b%k = D
     *
     * ab = ACk^2+ADk+BCk+BD
     * ab%k = BD%k
     *
     * =>
     * (a%k)(b%k)%k = BD%k = (a*b)%k
     *
     *
     *
     *
     * 快速求幂的算法不止一个，就说一个我们应该掌握的基本思路吧。利用幂运算的性质，我们可以写出这样一个递归式：
     *        | a * a^(b-1)  b 是奇数
     * a^b = {
     *       |  a * a^(b/2)  b 是偶数
     *
     */
    private static final int base = 1337;
    int fPow(int a, int k) {
        // k >= 0
        if (k == 0) return 1;
        a %= base;

        if (k % 2 == 1) {
            // k 是奇数
            return (a * fPow(a, k - 1)) % base;
        } else {
            // k 是偶数
            int sub = fPow(a, k / 2);
            return (sub * sub) % base;
        }
    }

    static int missingNumber(int[] nums) {
        int n = nums.length;
        int res = 0;
        // 新补的索引
        res += (n - 0);
        // 剩下索引和元素的差加起来
        for (int i = 0; i < n; i++)
            res += i - nums[i];
        return res;
    }

    static int[] findErrorNums(int[] nums) {
        int n = nums.length;
        int dup = -1;
        for (int i = 0; i < n; i++) {
            // 索引应该从 0 开始
            int index = Math.abs(nums[i]) - 1;
            if (nums[index] < 0)
                dup = Math.abs(nums[i]);
            else
                nums[index] *= -1;
        }

        int missing = -1;
        for (int i = 0; i < n; i++)
            if (nums[i] > 0)
                // 将索引转换成元素
                missing = i + 1;

        return new int[]{dup, missing};
    }

    private static void testMissingNumber() {
        int ret = missingNumber(new int[]{0, 1, 2, 3, 4, 5, 6});
        System.out.println(ret);

        int[] retA = findErrorNums(new int[]{1,2,2,4});
        for (int a : retA) {
            System.out.print(a + ", ");
        }
    }

    public static void main(String[] args) {
        testCountPrimes();
        testMissingNumber();
    }
}
