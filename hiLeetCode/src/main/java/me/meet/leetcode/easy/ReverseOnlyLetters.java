package me.meet.leetcode.easy;

public final class ReverseOnlyLetters {
    /**
     * Given a string S, return the "reversed" string where all characters that are not a letter stay in the same place, and all letters reverse their positions.
     *
     * Example 1:
     * Input: "ab-cd"
     * Output: "dc-ba"
     *
     * Example 2:
     * Input: "a-bC-dEf-ghIj"
     * Output: "j-Ih-gfE-dCba"
     *
     * Example 3:
     * Input: "Test1ng-Leet=code-Q!"
     * Output: "Qedo1ct-eeLg=ntse-T!"
     *
     * Note:
     * S.length <= 100
     * 33 <= S[i].ASCIIcode <= 122
     * S doesn't contain \ or "
     */

    /**
     * 题意：只翻转字母
     * 思路：这道题给了一个由字母和其他字符组成的字符串，让我们只翻转其中的字母，并不是一道难题，解题思路也比较直接。可以先反向遍历一遍字符串，只要遇到字母就直接存入到一个新的字符串 res，这样就实现了对所有字母的翻转。但目前的 res 中就只有字母，还需要将原字符串S中的所有的非字母字符加入到正确的位置，可以再正向遍历一遍原字符串S，遇到字母就跳过，否则就把非字母字符加入到 res 中对应的位置，
     */
    static String reverseOnlyLetters1(String src) {
        int length = src.length();
        StringBuilder res = new StringBuilder(length);
        for (int i = length - 1; i >= 0; i--) {
            char c = src.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                res.append(c);
            }
        }
        for (int i = 0; i < length; i++) {
            char c = src.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                continue;
            }
            res.insert(i, c);
        }
        return res.toString();
    }

    /**
     * 思路：再来看一种更加简洁的解法，使用两个指针i和j，分别指向S串的开头和结尾。当i指向非字母字符时，指针i自增1，否则若j指向非字母字符时，指针j自减1，若i和j都指向字母时，则交换 S[i] 和 S[j] 的位置，同时i自增1，j自减1，这样也可以实现只翻转字母的目的
     */
    static String reverseOnlyLetters2(String src) {
        int length = src.length(), i = 0, j = length - 1;
        StringBuilder res = new StringBuilder(src);

        for ( ; i < j; ) {
            char ic = src.charAt(i);
            char jc = src.charAt(j);

            if (!isLetter(ic)) {
                i++;
            } else if (!isLetter(jc)) {
                j--;
            } else {
                // swap
                res.setCharAt(j, ic);
                res.setCharAt(i, jc);

                i++;
                j--;
            }
        }
        return res.toString();
    }
    private static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static void main(String[] args) {
        String src = "a-bC-dEf-ghIj";
        String res = reverseOnlyLetters1(src);
        System.out.println(res);

        res = reverseOnlyLetters2(src);
        System.out.println(res);
    }
}
