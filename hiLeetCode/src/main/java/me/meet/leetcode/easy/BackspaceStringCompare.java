package me.meet.leetcode.easy;

public final class BackspaceStringCompare {
    private BackspaceStringCompare() {}

    /**
     * Given two strings S and T, return if they are equal when both are typed into empty text editors. # means a backspace character.
     *
     * Example 1:
     * Input: S = "ab#c", T = "ad#c"
     * Output: true
     *
     * Explanation: Both S and T become "ac".
     *
     * Example 2:
     * Input: S = "ab##", T = "c#d#"
     * Output: true
     *
     * Explanation: Both S and T become "".
     *
     * Example 3:
     * Input: S = "a##c", T = "#a#c"
     * Output: true
     *
     * Explanation: Both S and T become "c".
     *
     * Example 4:
     * Input: S = "a#c", T = "b"
     * Output: false
     *
     * Explanation: S becomes "c" while T becomes "b".
     *
     * Note:
     * 1 <= S.length <= 200
     * 1 <= T.length <= 200
     * S and T only contain lowercase letters and '#' characters.
     * Follow up:
     *
     * Can you solve it in O(N) time and O(1)space?
     */

    /**
     * 退格字符串比较
     * 这道题给了我们两个字符串，里面可能会有井号符#，这个表示退格符，键盘上的退格键我们应该都很熟悉吧，当字打错了的时候，肯定要点退格键来删除的。当然也可以连续点好几下退格键，这样就可以连续删除了，在例子2和3中，也确实能看到连续的井号符。
     */
    static boolean backspaceCompare(String s, String t) {
        return helper(s).equals(helper(t));
    }
    static String helper(String str) {
        StringBuilder res = new StringBuilder();
        char[] cs = str.toCharArray();
        for (char c : cs) {
            if (c == '#') {
                int len = res.length();
                if (len > 0) res.deleteCharAt(len-1);
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }

    /**
     * 这道题的follow up让我们使用常数级的空间复杂度，就是说不能新建空的字符串来保存处理之后的结果，那么我们只能在遍历的过程中同时进行比较，这样只能使用双指针同时遍历S和T串了。我们采用从后往前遍历，因为退格是要删除前面的字符，所以倒序遍历要好一些。用变量i和j分别指向S和T串的最后一个字符的位置，然后还需要两个变量cnt1和cnt2来分别记录S和T串遍历过程中连续出现的井号的个数，因为在连续井号后，要连续删除前面的字母，如何知道当前的字母是否是需要删除，就要知道当前还没处理的退格符的个数。好，现在进行while循环，条件是i和j至少有一个要大于等于0，然后对S串进行另一个while循环，条件是当i大于等于0，且当前字符是井号，或者cnt1大于0，那么若当前字符是退格符，则cnt1自增1，否则cnt1自减1，然后i自减1，这样就相当于跳过了当前的字符，不用进行比较。对T串也是做同样的while循环处理。之后若i和j有一个小于0了，那么可以根据i和j是否相等的情况进行返回。否则再看若S和T串当前的字母不相等，则返回false，因为当前位置的退格符已经处理完了，剩下的字母是需要比较相等的，若不相等就可以直接返回false了。最后当外层的while循环推出后，返回i和j是否相等
     */
    static boolean backspaceCompare2(String S, String T) {
        int i = S.length() - 1, j = T.length() - 1, cnt1 = 0, cnt2 = 0;
        while (i >= 0 || j >= 0) {
            while (i >= 0 && (S.charAt(i) == '#' || cnt1 > 0)) {
                if (S.charAt(i--) == '#') {
                    ++cnt1;
                } else {
                    --cnt1;
                }
            }
            while (j >= 0 && (T.charAt(j) == '#' || cnt2 > 0)) {
                if (T.charAt(j--) == '#') {
                    ++cnt2;
                } else {
                    --cnt2;
                }
            }
            if (i < 0 || j < 0) return i == j;
            if (S.charAt(i--) != T.charAt(j--)) return false;
        }
        return i == j;
    }

    public static void main(String[] args) {
        String s = "ab#c";
        String t = "ad#c";
        System.out.println(backspaceCompare(s, t));
        System.out.println(backspaceCompare2(s, t));
    }
}
