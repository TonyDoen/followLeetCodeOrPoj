package me.meet.leetcode.easy;

public final class ValidPalindrome2 {
    private ValidPalindrome2() {}
    /**
     * Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.
     *
     * Example 1:
     * Input: "aba"
     * Output: True
     *
     * Example 2:
     * Input: "abca"
     * Output: True
     *
     * Explanation: You could delete the character 'c'.
     *
     * Note:
     * The string will only contain lowercase characters a-z. The maximum length of the string is 50000.
     */
    /**
     * 题意：验证一句话是不是回文串
     * 思路：验证回文字符串是比较常见的问题，所谓回文，就是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串。
     * 但是区别是这道题的字符串中只含有小写字母
     * 而且这道题允许删除一个字符，
     * 那么当遇到不匹配的时候，我们到底是删除左边的字符，还是右边的字符呢，我们的做法是两种情况都要算一遍，
     * 只要有一种能返回true，那么结果就返回true。
     *
     */
    static boolean isPalindrome(String s) {
        char[] arr = s.toCharArray();
        int left = 0, right = arr.length - 1;
        for (; left < right; ) {
            if (arr[left] != arr[right]) {
                return isValid(arr, left, right - 1) || isValid(arr, left + 1, right); // 两种情况都要算一遍
            }
            left++;
            right--;
        }
        return true;
    }

    private static boolean isValid(char[] arr, int left, int right) {
        for (; left < right;) {
            if (arr[left] != arr[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private static void testIsPalindrome() {
        String src = "abca";
        boolean res = isPalindrome(src);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testIsPalindrome();
    }
}
