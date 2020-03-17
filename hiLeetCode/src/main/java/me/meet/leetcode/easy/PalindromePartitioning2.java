package me.meet.leetcode.easy;

public final class PalindromePartitioning2 {
    /**
     * Given a string s, partition s such that 
     * every substring of the partition is a palindrome.
     * 
     * Return the minimum cuts needed for a palindrome partitioning of s.
     * 
     * For example, given s ="aab",
     * Return 1 since the palindrome partitioning ["aa","b"] 
     * could be produced using 1 cut.
     */
    /**
     * 思路1，动态规划(使用一维数组，复杂度高)
     * 动态规划问题(一维数组表示，复杂度大)
     * dp[i] - 表示子串（0，i）的最小回文切割，则最优解在dp[s.length-1]中。
     * 
     * 分几种情况：
     * 1.初始化：当字串s.substring(0,i+1)(包括i位置的字符)是回文时，dp[i] = 0(表示不需要分割)；
     *   否则，dp[i] = i（表示至多分割i次）;
     * 2.对于任意大于1的i，如果s.substring(j,i+1)(j<=i,即遍历i之前的每个子串)是回文时，
     *   dp[i] = min(dp[i], dp[j-1]+1);
     * 3.如果s.substring(j,i+1)(j<=i)不是回文时，dp[i] = min(dp[i],dp[j-1]+i+1-j);
     *
     */
    static int minCut(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int len = s.length();
        int[] dp = new int[len];
        for (int i = 0; i < len; i++) {
            String str = s.substring(0, i + 1);
            // 是回文则需要cut 0次，否则初始化为字符串长度
            dp[i] = isPalindrome(str) ? 0 : i;
            if (dp[i] == 0) {
                continue;
            }

            // 不是回文
            for (int j = 1; j <= i; j++) {
                // j到i+1的子串
                String subStr = s.substring(j, i + 1);
                // 是回文 则dp[j-1]+1即可，不是则dp[j-1]+后面的元素个数
                if (isPalindrome(subStr)) {
                    dp[i] = Math.min(dp[i], dp[j - 1] + 1);
                } else {
                    dp[i] = Math.min(dp[i], dp[j - 1] + i - j + 1);
                }
            }
        }
        return dp[len - 1];
    }

    private static boolean isPalindrome(String str) {
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    private static void testMinCut() {
        String str = "aab";
        int res = minCut(str);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testMinCut();
    }
}
