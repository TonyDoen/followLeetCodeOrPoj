package me.meet.leetcode.medium;

import java.util.HashMap;
import java.util.Map;

public final class LongestSubstringWithAtMostKDistinctCharacters {
    private LongestSubstringWithAtMostKDistinctCharacters() {}
    /**
     * Given a string, find the length of the longest substring T that contains at most k distinct characters.
     *
     * For example, Given s = “eceba” and k = 2,
     * T is "ece" which its length is 3.
     */
    /**
     * 题意：最多有K个不同字符的最长子串
     */
    static int lengthOfLongestSubstringKDistinct1(String s, int k) {
        int res = 0, left = 0;
        Map<Character, Integer> m = new HashMap<>();
        for (int i = 0; i < s.length(); ++i) {
            Character ci = s.charAt(i);
            Integer cnt = m.get(ci);
            if (null == cnt) {
                m.put(ci, 1);
            } else {
                m.put(ci, ++cnt);
            }
            while (m.size() > k) {
                Character cl = s.charAt(left);
                Integer clt = m.get(cl);
                if (null == clt || 0 == --clt) {
                    m.remove(cl);
                }
                ++left;
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }

    static int lengthOfLongestSubstringKDistinct2(String s, int k) {
        int res = 0, left = 0;
        Map<Character, Integer> m = new HashMap<>();
        for (int i = 0; i < s.length(); ++i) {
            Character ci = s.charAt(i);
            m.put(ci, i);
            while (m.size() > k) {
                Character cl = s.charAt(left);
                if (left == m.get(cl)) {
                    m.remove(cl);
                }
                ++left;
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "eceba";
        int k = 2;
        int res1 = lengthOfLongestSubstringKDistinct1(s, k);
        int res2 = lengthOfLongestSubstringKDistinct2(s, k);
        System.out.println(res1);
        System.out.println(res2);
    }
}
