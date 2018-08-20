package me.meet.leetcode.medium;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringAtMostTwoDistinctCharacters {
    private LongestSubstringAtMostTwoDistinctCharacters() {}
    /**
     * Given a string s , find the length of the longest substring t  that contains at most 2 distinct characters.
     *
     * Example 1:
     * Input: "eceba"
     * Output: 3
     * Explanation: tis "ece" which its length is 3.
     *
     * Example 2:
     * Input: "ccaabbb"
     * Output: 5
     * Explanation: tis "aabbb" which its length is 5.
     *
     *
     * similar with FruitIntoBaskets
     */

    /**
     * 题意：最多有两个不同字符的最长子串
     * 这道题给我们一个字符串，让我们求最多有两个不同字符的最长子串。
     * 用 HashMap 来映射每个字符最新的坐标，比如题目中的例子 "eceba"，遇到第一个e，映射其坐标0，遇到c，映射其坐标1，遇到第二个e时，映射其坐标2，当遇到b时，映射其坐标3，每次我们都判断当前 HashMap 中的映射数，如果大于2的时候，那么需要删掉一个映射，我们还是从 left=0 时开始向右找，看每个字符在 HashMap 中的映射值是否等于当前坐标 left，比如第一个e，HashMap 此时映射值为2，不等于 left 的0，那么 left 自增1，遇到c的时候，HashMap 中c的映射值是1，和此时的 left 相同，那么我们把c删掉，left 自增1，再更新结果，以此类推直至遍历完整个字符串
     */
    static int lengthOfLongestSubstringTwoDistinct1(String s) {
        int res = 0, left = 0;
        Map<Character, Integer> m = new HashMap<>();
        for (int i = 0; i < s.length(); ++i) {
            Character c = s.charAt(i);
            m.put(c, i);
            while (m.size() > 2) {
                Character lc = s.charAt(left);
                if (m.get(lc) == left) {
                    m.remove(lc);
                }
                ++left;
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }

    static int lengthOfLongestSubstringTwoDistinct2(String s) {
        int res = 0, cur = 0, cntLast = 0;
        char first = 0, second = 0;
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c == first || c == second) {
                cur = cur + 1;
            } else {
                cur = cntLast + 1;
            }
            if (c == second) {
                cntLast = cntLast + 1;
            } else {
                cntLast = 1;
            }
            if (c != second) {
                first = second;
                second = c;
            }
            res = Math.max(res, cur);
        }
        return res;
    }

    public static void main(String[] args) {
//        String src = "eceba";
        String src = "ccaabbbc";
//        int res = lengthOfLongestSubstringTwoDistinct1(src);
        int res = lengthOfLongestSubstringTwoDistinct2(src);
        System.out.println(res);
    }
}
