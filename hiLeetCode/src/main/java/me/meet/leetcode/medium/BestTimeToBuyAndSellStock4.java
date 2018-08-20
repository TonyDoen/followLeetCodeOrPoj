package me.meet.leetcode.medium;

public final class BestTimeToBuyAndSellStock4 {
    private BestTimeToBuyAndSellStock4() {
    }
    /**
     * Say you have an array for which the i th element is 
     * the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit. 
     * You may complete at most k transactions.
     * (ie, buy one and sell one share of the stock k times).
     *
     * However, you may not engage in multiple transactions at the same time
     * (ie, you must sell the stock before you buy again).
     */
    /**
     * 题意：（与上面的区别是只能完成 k 笔交易）
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * 
     * 示例 1:
     * 输入: [7,1,5,3,6,4]; k=2
     * 输出: 7
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，
     * 在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     * 随后，在第 4 天（股票价格 = 3）的时候买入，
     * 在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
     * 
     * 示例 2:
     * 输入: [1,2,3,4,5]; k=2
     * 输出: 4
     * 解释: 在第 1 天（股票价格 = 1）的时候买入，
     * 在第 5 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     * 注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
     * 因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
     * 
     * 示例 3:
     * 输入: [7,6,4,3,1]; k=2
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     * 
     * 
     * 思路：动态规划
     * 
     * 状态穷举：
     * dp[i][k][1] 标识第i天 交易了k次手里持有股票的收益 ；
     * dp[i][k][0] 标识第i天 交易了k次手里没有股票的收益
     * 
     * 状态转移：(买入股票的时候k+1)
     * dp[i][k][1]怎么获得，第i-1天持有股票没有卖出 dp[i-1][k][1] || 第i-1天没有股票买入dp[i-1][k-1][0]-prices[i]
     * dp[i][k][0]怎么获得，第i-1天没有股票没有卖入 dp[i-1][k][0] || 第i-1天有股票卖出 dp[i-1][k][0]+prices[i]
     * 
     * base case：
     * dp[-1][k][0] = dp[i][0][0] = 0 (没开始 或者 没有交易，手里没有股票)
     * dp[-1][k][1] = dp[i][0][1] = -infinity (没开始，或没交易， 手里有股票，因为此种情况不可能)
     * 
     * 优化后解法如下：
     * t[i][0] 和 t[i][1]分别表示第i比交易买入和卖出时 各自的最大收益
     * 
     * 注意：
     * 当k大于等于数组长度一半时, 问题退化为贪心问题此时采用 买卖股票的最佳时机 II
     * 的贪心方法解决可以大幅提升时间性能, 对于其他的k, 可以采用 买卖股票的最佳时机 III
     * 的方法来解决, 在III中定义了两次买入和卖出时最大收益的变量, 在这里就是k租这样的
     * 变量, 即问题IV是对问题III的推广, t[i][0]和t[i][1]分别表示第i比交易买入和卖出时
     * 各自的最大收益
     */
    static int getBestTimeToBuyAndSellStock0(int[] prices, int k) {
        if (null == prices || prices.length < 2 || k < 1) {
            return 0;
        }

        if (k > prices.length / 2) { // 当k大于等于数组长度一半时, 问题退化为贪心问题
            int max = 0;
            for (int i = 1; i < prices.length; ++i) {
                if (prices[i] > prices[i - 1])
                    max += prices[i] - prices[i - 1];
            }
            return max;
        }


        int[][] t = new int[k][2];
        for (int i = 0; i < k; i++) {
            t[i][0] = Integer.MIN_VALUE;
        }
        for (int p : prices) {
            t[0][0] = Math.max(t[0][0], -p);
            t[0][1] = Math.max(t[0][1], t[0][0] + p);
            for (int i = 1; i < k; ++i) {
                t[i][0] = Math.max(t[i][0], t[i - 1][1] - p);
                t[i][1] = Math.max(t[i][1], t[i][0] + p);
            }
        }

        return t[k - 1][1];
    }

    private static void testGetBestTimeToBuyAndSellStock0() {
//        int[] arr = {7, 1, 5, 3, 6, 4};
        int[] arr = {1, 2, 5, 4, 5, 7, 9, 5};
        int res = getBestTimeToBuyAndSellStock0(arr, 3);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testGetBestTimeToBuyAndSellStock0();
    }
}
