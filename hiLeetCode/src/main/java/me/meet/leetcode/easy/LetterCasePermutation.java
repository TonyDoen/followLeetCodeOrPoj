package me.meet.leetcode.easy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class LetterCasePermutation {
    private LetterCasePermutation() {}

    /**
     * Given a string S, we can transform every letter individually to be lowercase or uppercase to create another string.  Return a list of all possible strings we could create.
     *
     * Examples:
     * Input: S = "a1b2"
     * Output: ["a1b2", "a1B2", "A1b2", "A1B2"]
     *
     * Input: S = "3z4"
     * Output: ["3z4", "3Z4"]
     *
     * Input: S = "12345"
     * Output: ["12345"]
     * Note:
     *
     * S will be a string with length at most 12.
     * S will consist only of letters or digits.
     */

    /**
     * 题意： 字母大小写全排列
     * 这道题给了我们一个只包含字母和数字的字符串，让我们将字母以大小写进行全排列，给的例子很好的说明了题意。博主认为这道题给Easy有点不合适，至少应该是Medium的水准。这题主要参考了官方解答贴的解法，我们关心的是字母，数字的处理很简单，直接加上就可以了。比如说S = "abc"，那么先让 res = [""]，然后res中的每个字符串分别加上第一个字符a和A，得到 ["a", "A"]，然后res中的每个字符串分别加上第二个字符b和B，得到 ["ab", "Ab", "aB", "AB"]，然后res中的每个字符串分别加上第三个字符c和C，得到 ["abc", "Abc", "aBc", "ABc", "abC", "AbC", "aBC", "ABC"]，参见代码如下：
     */

    static List<String> letterCasePermutation1(String S) { // error
        List<String> res = new LinkedList<String>();
        int sLen = S.length();
        for (int i = 0; i < sLen; i++) {
            char c = S.charAt(i);
            int len = res.size();
            if (c >= '0' && c <= '9') {
                for (String str : res) str += c;
            } else {
                for (int j = 0; j < len; ++j) {
                    String resJ = res.get(j);
                    res.add(resJ);
                    res.set(j, resJ + Character.toLowerCase(c));
                    res.set(len+j,  resJ + Character.toUpperCase(c));
                }
            }
        }
        return res;
    }

    static List<String> letterCasePermutation2(String S) {
        List<String> res = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        helper(S, res, sb, 0);
        return res;
    }
    private static void helper(String s, List<String> res, StringBuilder sb, int index) {
        // base case:
        if (index == s.length()) {
            res.add(sb.toString());
            return;
        }

        // recursive rule:
        int len = sb.length();
        char ch = s.charAt(index);
        if (ch >= '0' && ch <= '9') {
            sb.append(ch);
            helper(s, res, sb, index + 1);
        } else {
            char lower = Character.toLowerCase(ch);
            char upper = Character.toUpperCase(ch);
            sb.append(lower);
            helper(s, res, sb, index + 1);
            sb.setCharAt(sb.length() - 1, upper);
            helper(s, res, sb, index + 1);
        }
        sb.setLength(len);
    }


    static void helper(List<String> res, String S, int i, String item){
        if(i == S.length()){
            res.add(item);
            return;
        }
        char si = S.charAt(i);
        if(Character.isLetter(si)){
            helper(res, S, i+1, item+Character.toLowerCase(si));
            helper(res, S, i+1, item+Character.toUpperCase(si));
        } else{
            helper(res, S, i+1, item+si);
        }
    }
    static List<String> letterCasePermutation3(String S) {
        //DFS with backtracking
        //!!!Usually just one loop or multiple branches in DFS helper function
        List<String> res = new ArrayList<String>();
        helper(res, S, 0, "");
        return res;
    }

    static List<String> letterCasePermutation4(String str) {
        List<String> res = new ArrayList<String>();

        res.add(str);

        for (int i = 0, n = str.length(); i < n; ++i) {
            char c = str.charAt(i);
            if ('0' <= c && c <= '9') continue;

            c ^= 32; // 因为我们知道 'A' = 65, 'B' = 66, 和 'a' = 97, 'b' = 98, 小写字母的ASCII码比大写字母多32，刚好是(1 << 5)
            for (int k = 0, size = res.size(); k < size; ++k) {
                char[] newStr = res.get(k).toCharArray();
                newStr[i] = c;
                res.add(new String(newStr));
            }
        }

        return res;
    }

    public static void main(String[] args) {
        String input = "a1b2";
        System.out.println(letterCasePermutation1(input));
        System.out.println(letterCasePermutation2(input));
        System.out.println(letterCasePermutation3(input));
        System.out.println(letterCasePermutation4(input));
    }
}
