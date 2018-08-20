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
     * 思路1，
     * 动态规划(使用一维数组，复杂度高)
     * 动态规划问题(一维数组表示，复杂度大)
     * dp[i] - 表示子串（0，i）的最小回文切割，则最优解在dp[s.length-1]中。
     *
     * 分几种情况：
     * 1.初始化：当字串s.substring(0,i+1)(包括i位置的字符)是回文时，dp[i] = 0(表示不需要分割)；否则，dp[i] = i（表示至多分割i次）;
     * 2.对于任意大于1的i，如果s.substring(j,i+1)(j<=i,即遍历i之前的每个子串)是回文时，dp[i] = min(dp[i], dp[j-1]+1);
     * 3.如果s.substring(j,i+1)(j<=i)不是回文时，dp[i] = min(dp[i],dp[j-1]+i+1-j);
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
        char[] arr = str.toCharArray();
        for (int left = 0, right = arr.length - 1; left < right; left++, right--) {
            if (arr[left] != arr[right]) {
                return false;
            }
        }
        return true;
    }

    private static void testMinCut() {
        String str = "aabc";
        int res = minCut(str);
        System.out.println(res);
    }

    /**
     * 动态规划的题，最主要就是写出状态转移方程
     * 状态转移，其实就是怎么把一个大的状态表示为两个或者多个已知的状态
     * 以此题为例，设f[i][j]为最小的切点数，那么有：
     * 1、s[i][j]为回文字符串，则f[i][j] = 0;
     * 2、s[i][j]不是回文字符串，增加一个切点p，将s[i][j]切割为两端s[i][p]、s[p+1][j],则f[i][j] = f[i][p]+f[p+1][j]
     * 
     * 所谓的状态转移方程就是上面的式子
     * 接着来看看怎么组织程序，先看看状态转移的思路：
     * 以"aab"为例，"aab"明显不是回文串
     * 所以 f("aab") = min( (f("a")+f("ab")) , (f("aa")+f("b")) ) + 1;
     * f("a") = 0;
     * f("ab") = f("a")+f("b") +1  = 0+0+1 = 1;
     * f("aa") = 0;
     * f("b") = 0;
     * 即f("aab") = 1;
     *
     *
     */
    static int minCut0(String src) {
        if (null == src || src.isEmpty()) {
            return 0;
        }
        int length = src.length();
        int[][] dp = new int[length][length];
        for (int gap = 0; gap <= length; gap++) {
            for (int i = 0, j = gap; j < length; i++, j++) {
                if (isPalindrome(src.substring(i, j + 1))) { // 1、s[i][j]为回文字符串，则f[i][j] = 0;
                    dp[i][j] = 0;
                } else {                                     // 2、s[i][j]不是回文字符串，增加一个切点p，将s[i][j]切割为两端s[i][p]、s[p+1][j],则f[i][j] = f[i][p]+f[p+1][j]
                    int min = length - 1;
                    for (int p = i; p < j; p++) {
                        int a = dp[i][p];
                        int b = dp[p + 1][j];
                        a = a + b + 1;
                        min = min < a ? min : a;
                    }
                    dp[i][j] = min;
                }
            }
        }
        return dp[0][length - 1];
    }

    private static void testMinCut0() {
        String str = "aabc";
        int res = minCut0(str);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testMinCut();
        testMinCut0();
    }
}
