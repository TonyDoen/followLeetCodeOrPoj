package me.meet.random;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StringOps {
    /**
     * short chars s1
     * long chars s2
     * 子串
     *
     * 1. s1, s2: ANSI
     * 2. not lib
     * 3.
     *
     * eg:
     * s1: ac
     * s2: abc x
     *     acb *
     *
     */
    public static boolean isSubStr(String s1, String s2) {
        // s1: ac
        //     j
        // s2: eacb
        //      i
        char[] sc1 = s1.toCharArray();
        char[] sc2 = s2.toCharArray();
        int sc1Len = sc1.length, sc2Len = sc2.length;
        for (int i = 0; i < sc2Len;) {
            // s2: eacb
            int j = 0;
            while (j < sc1Len) {
                if (sc1[j] == sc2[i]) {
                    i++;
                    j++;
                } else {
                    i++;
                    //j = 0;
                    break;
                }
            }
            if (j == sc1Len) {
                return true;
            }
        }
        return false;
    }

    private static void testIsSubStr() {
        String s1 = "ac";
        String s2 = "eacb";
        boolean tf = isSubStr(s1, s2);
        System.out.println(tf);
    }

    /**
     * 寻找字符串数组中
     * ["apple","iOS","dog","nana","man","good","goodman"]
     * 能够被其他字符串组合出现的最长的字符串，多个相同长度的字符串，返回字典序最小的
     */
    public static String longestWord (String[] words) {
        int minLen = Integer.MAX_VALUE, maxLen = 0, idx = 0;
        HashMap<Integer, List<String>> lenMp = new HashMap<>();
        HashMap<String, Integer> mp = new HashMap<>();

        for (String w : words) {
            int wLen = w.length();
            List<String> wLt = lenMp.computeIfAbsent(wLen, k -> new LinkedList<>());
            wLt.add(w);

            if (wLen < minLen) {
                minLen = wLen;
            }
            if (wLen > maxLen) {
                maxLen = wLen;
            }
            mp.put(w, idx++);
        }

        List<String> wLt = lenMp.get(maxLen);
        List<String> rsLt = new LinkedList<>();
        for (String rs : wLt) {
            for (int i = minLen; i < maxLen; i++) {
                String left = rs.substring(0, i);
                String right = rs.substring(i);
                if (null != mp.get(left) && null != mp.get(right)) {
                    rsLt.add(rs);
                }
            }
        }
        if (!rsLt.isEmpty()) {
            rsLt.sort((String::compareTo));
            return rsLt.get(0);
        }
        return "";
    }

    private static void testLongestWord() {
        // ["apple","iOS","dog","nana","man","good","goodman"]
        String[] words = new String[]{"apple","iOS","dog","nana","man","good","nanaman","goodman","mangood"};
        String rs = longestWord(words);
        System.out.println(rs);
    }

    public static void main(String[] args) {
        // s1 是不是 s2 子串
        testIsSubStr();
        //
        testLongestWord();
    }
}
