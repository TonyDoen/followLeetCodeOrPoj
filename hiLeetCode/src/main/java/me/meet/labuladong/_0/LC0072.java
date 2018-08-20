package me.meet.labuladong._0;

public final class LC0072 {
    private LC0072() {
    }

    /**
     * uri: https://leetcode.cn/problems/Edit-Distance/
     *
     * LeetCode: 72. Edit Distance
     * 编辑距离(动态规划, 1965年, 俄罗斯数学家Vladimir Levenshtein提出的)
     *
     * 给定两个字符串s1和s2,计算出将s1转换成s2所使用的最少操作数。你可以对一个字符串进行如下三种操作:
     * 1.插入一个字符
     * 2.删除一个字符
     * 3.替换一个字符
     *
     * 示例1:
     * 输入: s1="horse",s2="ros"
     * 输出: 3
     * 解释: horse -> rorse (将'h'替换为'r')
     *      rorse -> rose  (删除'r')
     *      rose  -> ros   (删除'e')
     *
     * 示例2:
     * 输入: s1="intention",s2="execution"
     * 输出: 5
     * 解释: intention -> inention (删除 't')
     *      inention  -> enention (将'i'替换为'e')
     *      enention  -> exention (将'n'替换为'x')
     *      exention  -> exection (将'n'替换为'c')
     *      exection  -> execution(插入'u')
     *
     *
     * 思路: 动态规划思想
     * (1) dp[i][j]表示将字符串A[0:i-1]转变为B[0:j-1]的最小步骤数。
     * (2) 边界情况:
     *         当i=0,即A串为空时,那么转变为B串就是不断添加字符,dp[0][i] = j
     *         当j=0,即B串为空时,那么转变为B串就是不断删除字符,dp[i][0] = i
     * (3) 对应三种字符操作方式:
     *         插入操作:dp[i][j-1]+1相当于为B串的最后插入了A串的最后一个字符;
     *         删除操作:dp[i-1][j]+1相当于将B串的最后字符删除;
     *         替换操作:dp[i-1][-1]+0(A[i-1]!=B[-1])相当于通过将B串的最后一个字符替换为A串的最后一个字符。
     * (4) 所以dp方程式为:
     *         1<=i<=len(A), 1<=j<=len(B)
     *         dp[i][j] = dp[i-1][j-1],                                    A[i-1] == B[j-1]
     *         dp[i][j] = min(dp[i-1][j-1]+1, dp[i][j=1]+1, dp[i=1][j]+1), A[i-1] != B[j-1]
     *
     * 优化dp空间:
     * 很多两维的dp空间是可以压缩成一维的,用一个例子说明,word1,word2='horse','ros',所以得到的dp空间为:
     *
     * 现在要求i=3,j=1的值?必须知道这个方格的左边、左上角、上边,三个方格的值,那么思路来了,我们一列一列的计算,
     * 一个cur[i]数据保存当前列数值,且,再开辟两个变量,pre、temp,用来保存当前方格的左上角、和左边方格的值,
     * cur[i]每更新一次对应的pre、temp也向下滑动一次,
     * 就这样只保留一列的信息,依次向右计算直至结束。
     * 所以现在可以把空间复杂度降到O(m)或者O(n),时间复杂度不变。
     */
    static int minDistance0(String src, String dst) {
        if (null == src || null == dst) {
            throw new IllegalArgumentException();
        }
        // 定义 dp 数组
        // 1<=i<=len(src), 1<=j<=len(dst)
        // dp[i][j]表示将字符串 src[0:i-1] 转变为 dst[0:j-1] 的最小步骤数。
        int srcLen = src.length(), dstLen = dst.length(), dp[][] = new int[srcLen+1][dstLen+1];
        // 状态初始化
        for (int i = 0; i <= srcLen; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= dstLen; j++) {
            dp[0][j] = j;
        }
        // 状态转移
        for (int i = 1; i <= srcLen; i++) {
            for (int j = 1; j <= dstLen; j++) {
                int flag = src.charAt(i - 1) == dst.charAt(j - 1) ? 0 : 1;
                dp[i][j] = min(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + flag);
            }
        }
        return dp[srcLen][dstLen];
    }

    // 动态规划
    // 1 状态定义
    // 2 状态存储(dp 数组)
    // 3 状态转移方程(递归, 迭代, 多情况的下需要 max,min)
    private static int optTime1 = 0;
    static void minDistance1(String src, String dst) {
        if (null == src || null == dst) {
            throw new IllegalArgumentException();
        }
        if (0 == src.length()) {
            // 即 src 串为空时,那么转变为 dst 串就是不断添加字符
            optTime1 = dst.length();
        } else if (0 == dst.length()) {
            optTime1 = src.length();
        } else {
            // 相同, 则去掉, 继续递归
            if (src.charAt(0) == dst.charAt(0)) {
                minDistance1(src.substring(1), dst.substring(1));
            } else {
                // 错误的地方
                // 不同, 挑选插入, 删除, 替换 其中一个操作选取操作数最小的(所以这里用全局变量不能表达)
                minDistance1(src.substring(1), dst);
            }
        }
    }

    @SafeVarargs
    private static <T extends Comparable<T>> T min(T... args) {
        if (null == args) {
            return null;
        }
        T ret = args[0];
        for (int i = args.length - 1; i > 0; i--) {
            if (ret.compareTo(args[i]) > 0) {
                ret = args[i];
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        String src = "horse", dst = "ros";
        int ret = minDistance0(src, dst);
        System.out.println(ret);

        // 写法错误，用来理解上面 dp 数组
        minDistance1(src, dst);
        ret = optTime1;
        System.out.println(ret);
    }
}
