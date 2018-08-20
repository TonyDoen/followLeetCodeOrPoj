package me.meet.labuladong._0;

public final class LC0005 {
    private LC0005() {
    }

    /**
     * uri: https://leetcode.cn/problems/longest-palindromic-substring/description/
     * 
     * LeetCode 第5题 longest-palindromic-substring
     * 给定一个字符串，输出该字符串最长回文子串
     * 
     * 示例 1：
     * 
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     * 示例 2：
     * 
     * 输入：s = "cbbd"
     * 输出："bb"
     * 
     * 提示：
     * 
     * 1 <= s.length <= 1000
     * s 仅由数字和英文字母组成
     *
     *
     * 思路: 使用双指针, 中心扩展算法
     * 时间复杂度：O(n²)。
     * 空间复杂度：O(1)。
     */

    private static String palindrome(String src, int left, int right) {
        // 下面，就来说一下正确的思路，如何使用双指针。
        //寻找回文串的问题核心思想是：从中间开始向两边扩散来判断回文串。对于最长回文子串，就是这个意思：
        // for 0 <= i < len(s):
        //    找到以 s[i] 为中心的回文串
        //    更新答案
        //
        // 但是呢，我们刚才也说了，回文串的长度可能是奇数也可能是偶数，如果是abba这种情况，没有一个中心字符，上面的算法就没辙了。所以我们可以修改一下：
        // for 0 <= i < len(s):
        //    找到以 s[i] 为中心的回文串
        //    找到以 s[i] 和 s[i+1] 为中心的回文串
        //    更新答案
        //
        // 为什么要传入两个指针l和r呢？因为这样实现可以同时处理回文串长度为奇数和偶数的情况：

        // 中心展开
        while (left >= 0 && right < src.length() && src.charAt(left) == src.charAt(right)) {
            left--;
            right++;
        }
        // 返回以 src[left]...src[right] 为中心的最长回文串长度
//        return src.substring(left + 1, right - left - 1);
        return src.substring(left + 1, right);
    }

    static String longestPalindromicSubstring1(String src) {
        if (null == src || src.isEmpty()) {
            return src;
        }
        String ret = "";
        for (int i = 0, length = src.length(); i < length; i++) {
            String s0 = palindrome(src, i, i);
            String s1 = palindrome(src, i, i + 1);
            if (ret.length() < s0.length()) {
                ret = s0;
            }
            if (ret.length() < s1.length()) {
                ret = s1;
            }
        }
        return ret;
    }

    private static int lenOfPalindrome(String src, int left, int right) {
        // 中心展开
        while (left >= 0 && right < src.length() && src.charAt(left) == src.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    static String longestPalindromicSubstring0(String src) {
        int start = 0, end = 0;
        for (int i = 0, length = src.length(); i < length; i++) {
            int len0 = lenOfPalindrome(src, i, i);
            int len1 = lenOfPalindrome(src, i, i + 1);
            int tLen = Math.max(len0, len1);
            if (tLen > end - start) {
                start = i - (tLen - 1) / 2;
                end = i + tLen / 2;
            }
        }
        return src.substring(start, end + 1);
    }

    /**
     * 思路: 暴力解法为遍历所有子串，逐个判断是否是回文字串。
     *
     * 解释1:
     * 在动态规划的思想中，总是希望把问题划分成相关联的子问题；然后从最基本的子问题出发来推导较大的子问题，直到所有的子问题都解决。
     * 假设字符串s的长度为length，建立一个 length*length 的矩阵dp。
     * 令 dp[i][j] 表示 S[i] 至 S[j] 所表示的子串是否是回文子串。
     *
     * 1. 当 i == j，dp[i][j] 是回文子串（单字符都是回文子串）；
     * 2. 当 j - i < 3，只要 S[i] == S[j]，则 dp[i][j] 是回文子串（如"aa"，"aba"），否则不是；
     * 3. 当 j - i >= 3，如果 S[i] == S[j] && dp[i+1][j-1] ，则 dp[i][j] 是回文子串，否则不是 。
     *
     * 由此可以写出状态转移方程：
     *           ⎪ true,                            i == j
     * dp[i][j]= ⎨ S[i] == S[j],                    j-i < 3
     *           ⎪ S[i] == S[j] && dp[i+1][j-1],    j-i >= 3
     *
     * 时间复杂度：O(n²)。
     * 空间复杂度：O(n²)。
     */
    static String longestPalindromicSubstring2(String s) {
        int length = s.length();
        boolean[][] dp = new boolean[length][length];
        String result = s.substring(0, 1);

        //4、依次循环单个字符、两个字符、三个字符、四个字符......
        for (int gap = 0; gap < length; gap++) {
            for (int i = 0; i < length - gap; i++) {
                int j = i + gap;
                if (s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    if (j + 1 - i > result.length()) {
                        result = s.substring(i, j + 1);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Manacher（马拉车）算法
     * Manacher算法，又叫"马拉车"算法，可以在时间复杂度为O(n)的情况下求解一个字符串的最长回文子串长度的问题。
     * 1. 将初始字符串每个字符左右两边填充’#’(也可以是其它字符)，巧妙地解决对称数量奇偶的问题（如"aba"变成"#a#b#a#","bb"变成"#b#b#",处理后的回文子串都是奇数）；
     * 2. 遍历整个字符串，用一个数组来记录以该字符为中心的回文子串半径，并记录已经扩展到的右边界；
     * 3. 每一次遍历的时候，如果该字符在已知回文串最右边界的覆盖下，那么就计算其相对最右边界回文串中心对称的位置，得出已知回文串的长度；
     * 4. 判断该长度和右边界，如果达到了右边界，那么需要继续进行中心扩展探索。当然，如果第3步该字符没有在最右边界的"羽翼"下，则直接进行中心扩展探索。进行中心扩展探索的时候，同时又更新右边界；
     * 5. 最后得到最长回文子串之后，去掉其中的特殊符号即可。
     *
     * 时间复杂度：O(n)，这个算法在循环的时候，要么扩展右边界，要么直接得出结论，时间复杂度可以到O(n)。
     * 空间复杂度：O(n)。
     */
    static String longestPalindromicSubstring3(String s) {
        // 1. 将初始字符串每个字符左右两边填充’#’(也可以是其它字符)
        int len = s.length(); //
        StringBuilder sb = new StringBuilder(2 * len + 1);
        sb.append('#');
        for (int i = 0; i < len; i++) {
            sb.append(s.charAt(i));
            sb.append('#');
        }

        // 2. 遍历整个字符串，用一个数组来记录以该字符为中心的回文子串半径，并记录已经扩展到的右边界；
        len = sb.length(); // 处理后的字串长度
        int right = 0, rightCenter = 0, center = 0, longestHalf = 0;
        int[] halfLenArr = new int[len];
        for (int i = 0; i < len; i++) {
            boolean needCalc = true; //
            // 3. 每一次遍历的时候，如果该字符在已知回文串最右边界的覆盖下，那么就计算其相对最右边界回文串中心对称的位置，得出已知回文串的长度；
            if (right > i) {
                int leftCenter = 2 * rightCenter - i;
                halfLenArr[i] = halfLenArr[leftCenter];
                if (i + halfLenArr[i] > right) {
                    halfLenArr[i] = right - i;
                }
                if (i + halfLenArr[leftCenter] < right) {
                    needCalc = false;
                }
            }
            // 4. 判断该长度和右边界，如果达到了右边界，那么需要继续进行中心扩展探索。当然，如果第3步该字符没有在最右边界的"羽翼"下，则直接进行中心扩展探索。进行中心扩展探索的时候，同时又更新右边界；
            // 中心扩展
            if (needCalc) {
                while (i - 1 - halfLenArr[i] >= 0 && i + 1 + halfLenArr[i] < len) {
                    if (sb.charAt(i + 1 + halfLenArr[i]) == sb.charAt(i - 1 - halfLenArr[i])) {
                        halfLenArr[i]++;
                    } else {
                        break;
                    }
                }
                // 更新右边界及中心
                right = i + halfLenArr[i];
                rightCenter = i;
                // 记录最长回文串
                if (halfLenArr[i] > longestHalf) {
                    center = i;
                    longestHalf = halfLenArr[i];
                }
            }
        }

        // 5. 最后得到最长回文子串之后，去掉其中的特殊符号即可。去掉之前添加的#
        StringBuilder res = new StringBuilder();
        for (int i = center - longestHalf + 1; i <= center + longestHalf; i += 2) {
            res.append(sb.charAt(i));
        }
        return res.toString();
    }

    static String longestPalindromicSubstring4(String s) {
        // Transform S into T.
        // For example, S = "abba", T = "^#a#b#b#a#$".
        // ^ and $ signs are sentinels appended to each end to avoid bounds checking
        String t = preProcess(s);
        int n = t.length();
        int[] p = new int[n];
        int c = 0, r = 0;

        for (int i = 1; i < n - 1; i++) {
            int i_mirror = 2 * c - i; // equals to i' = C - (i-C)

            if (r > i) {
                p[i] = Math.min(r - i, p[i_mirror]); // Prevents overflow
            } else {
                p[i] = 0; // This initializes P[i] to 0 if i is outside the current right boundary
            }

            // Attempt to expand palindrome centered at i
            while (t.charAt(i + 1 + p[i]) == t.charAt(i - 1 - p[i])) {
                p[i]++;
            }

            // If palindrome centered at i expands past R,
            // adjust center based on expanded palindrome.
            if (i + p[i] > r) {
                c = i;
                r = i + p[i];
            }
        }

        // Find the maximum element in P
        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (p[i] > maxLen) {
                maxLen = p[i];
                centerIndex = i;
            }
        }

        // Calculate start index and length of the longest palindrome
        int start = (centerIndex - maxLen) / 2; // original start index

        return s.substring(start, start + maxLen);
    }

    // Transform S into T by inserting dummy characters
    private static String preProcess(String s) {
        if (s == null || s.length() == 0) return "^$";
        StringBuilder ret = new StringBuilder("^");
        for (int i = 0; i < s.length(); i++)
            ret.append("#").append(s.charAt(i));
        ret.append("#$");
        return ret.toString();
    }

    public static void main(String[] args) {
        // 测试回文串长度
        String ret = palindrome("aaa", 1, 1);
        System.out.println(ret);

        // 给定一个字符串，输出该字符串最长回文子串
        String res0 = longestPalindromicSubstring0("aaabbb");
        System.out.println(res0);

        String res1 = longestPalindromicSubstring1("aaabbb");
        System.out.println(res1);

        String res2 = longestPalindromicSubstring2("aaabbb");
        System.out.println(res2);

        // // 给定一个字符串，输出该字符串最长回文子串，Manacher（马拉车）算法(滑动窗口)
        String res3 = longestPalindromicSubstring3("aaabbb");
        System.out.println(res3);

        String res4 = longestPalindromicSubstring4("aaabbb");
        System.out.println(res4);
    }
}
