package me.meet.leetcode.hard;

public final class MinimumWindowSubsequence {
    private MinimumWindowSubsequence() {}
    /**
     * Given strings S and T, find the minimum (contiguous) substring W of S, so that T is a subsequence of W.
     * If there is no such window in S that covers all characters in T, return the empty string "". If there are multiple such minimum-length windows, return the one with the left-most starting index.
     *
     * Example 1:
     * Input:
     * S = "abcdebdde", T = "bde"
     * Output: "bcde"
     *
     * Explanation:
     * "bcde" is the answer because it occurs before "bdde" which has the same length.
     * "deb" is not a smaller window because the elements of T in the window must occur in order.
     *
     * Note:
     * All the strings in the input will only contain lowercase letters.
     * The length of S will be in the range [1, 20000].
     * The length of T will be in the range [1, 100].
     */
    /**
     * 题意:最小窗口序列
     * 思路:这道题给了我们两个字符串S和T,让我们找出S的一个长度最短子串W,使得T是W的子序列,如果长度相同,取起始位置靠前的。清楚子串和子序列的区别,
     *
     * 这种玩字符串且还是 Hard 的题,十有八九都是要用动态规划 Dynamic Programming 来做的,那么就直接往 DP 上去想吧。
     * DP 的第一步就是设计 dp 数组,像这种两个字符串的题,一般都是一个二维数组,想想该怎么定义。
     * 确定一个子串的两个关键要素是起始位置和长度,那么我们的 dp 值到底应该是定起始位置还是长度呢？
     * 因为我们一旦知道了起始位置,那么当前位置减去起始位置,就是长度了,所以我们 dp 值定为起始位置。
     * 那么 dp[i][j] 表示范围S中前i个字符包含范围T中前j个字符的子串的起始位置,注意这里的包含是子序列包含关系。（注意:子串和子序列的区别）
     *
     * 下面就是重中之重啦,求状态转移方程。一般来说,dp[i][j] 的值是依赖于之前已经求出的dp值的,在递归形式的解法中,dp数组也可以看作是记忆数组,从而省去了大量的重复计算,这也是 dp 解法凌驾于暴力搜索之上的主要原因。
     *
     * 建议是从最简单的例子开始分析,
     * 比如 S = "b", T = "b", 那么我们就有 dp[1][1] = 0,因为S中的起始位置为0,长度为1的子串可以包含T。
     * 若当 S = "d", T = "b",那么我们有 dp[1][1] = -1,因为我们的dp数组初始化均为 -1,表示未匹配或者无法匹配。
     * 下面来看一个稍稍复杂些的例子,S = "dbd", T = "bd",我们的dp数组是:
     *    ∅  b  d
     * ∅  ?  ?  ?
     * d  ? -1 -1
     * b  ?  1 -1
     * d  ?  1  1
     *
     */

    static String minWindow0(String s, String t) {
        char[] sArr = s.toCharArray(), tArr = t.toCharArray();
        int m = s.length(), n = t.length(), start = -1, minLen = Integer.MAX_VALUE;
        for (int i = 0, j = 0; i < m; i++) {
            if (sArr[i] == tArr[j]) {
                j++;
                if (j == n) {
                    int end = i + 1;
                    for (; --j >= 0; ) {
                        for (;sArr[i--] == tArr[j];) {

                        }
                    }
                    i++;
                    j++;

                    if (end - i < minLen) {
                        minLen = end - i;
                        start = i;
                    }
                }
            }
            i++;
        }

        return (start != -1) ? s.substring(start, minLen) : "";
    }

    /**
     * 思路:
     * 1、子串构建一个map,里面存储每个char字符出现的个数(首先采用一个大小为256的数组充当hashmap的功能,记录tartget中字母出现次数)
     * 2、遍历source数组,开始时start=0,i=0; start记录当前字串的起点,i相当于当前字串的终点。
     * 3、用found表示当前字串中包含target中字符的数目,如果found=target.length()则表明当前字串包含了target中所有字符,如果满足,进入下一步。
     * 4、删除当前start占用的字符的数目,found计数器也要减1
     * 5、将start后移,取出start前面多余的元素,已达到字串最小的目标。
     * 6、判断,如果当前字串小于历史搜到的最小字串,则将当前字串的长度,起始点,结束点都记录,更新。
     * 7、将start后移,寻找下一个字串
     */
    static String minWindow3(String s, String t) {
        if (s == null || t == null || s.isEmpty() || t.isEmpty()) {
            return "";
        }
        // 共256个字符,所以初始一个256的数组代替map
        int[] td = new int[256];
        for (char c : t.toCharArray()) {
            td[c]++;
        }

        // 找到的满足条件的匹配字符串的起止索引
        int first = -1;
        int end = 0;
        int minLen = s.length();

        // 标示当前遍历的起始位置
        int start = 0;

        // 标示找到的匹配个数
        int found = 0;

        // target的长度
        int tLen = t.length();
        // source的map
        int[] sd = new int[256];

        for (int i = 0; i < s.length(); i++) {
            char curChar = s.charAt(i);
            sd[curChar]++;

            // td中存在该元素的空缺,则计数器+1
            if (sd[curChar] <= td[curChar]) {
                found++;
            }

            if (found == tLen) {
                // 删除无用的元素
                while (start <= i && sd[s.charAt(start)] > td[s.charAt(start)]) {
                    sd[s.charAt(start)]--;
                    start++;
                }

                // 第一次发现 或者 判断当前发现的字符串是否小于之前发现的
                if (first<0 || i-start+1 < minLen) {
                    first = start;
                    end = i;
                    minLen = i - start + 1;
                }

                // 把start前移,当前sd的缓存去除当前start指向的元素,found计数器减1
                sd[s.charAt(start)]--;
                found--;
                start++;
            }
        }

        if (first >= 0) {
            return s.substring(first, end + 1);
        }

        return "";
    }

    private static void testMinWindow3() {
        String s = "abcdebdde", t = "bde";
        String res = minWindow3(s, t);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testMinWindow3();
    }
}
