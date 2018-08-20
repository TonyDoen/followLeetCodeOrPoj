package me.meet.leetcode.easy;

public final class PalindromeNumber {
    private PalindromeNumber() {
    }
    /**
     * Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.
     *
     * Example 1:
     * Input: 121
     * Output: true
     *
     * Example 2:
     * Input: -121
     * Output: false
     * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
     *
     * Example 3:
     * Input: 10
     * Output: false
     * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
     *
     * Follow up:
     * Coud you solve it without converting the integer to a string?
     */
    /**
     * 题意：验证回文数字
     */
//    static boolean isPalindrome(int x) {
//        if (x < 0) return false;
//        int div = 1;
//        while (x / div >= 10) div *= 10;
//        while (x > 0) {
//            int left = x / div;
//            int right = x % 10;
//            if (left != right) return false;
//            x = (x % div) / 10;
//            div /= 100;
//        }
//        return true;
//    }
    static boolean isPalindrome0(int x) {
        if (x < 0) {                        // 1. 负数不是回文
            return false;
        }
        int div = 1;                        // 2. 算出数字 x 的最高位
        for (; x / div >= 10; ) {
            div = div * 10;
        }
        for (; x > 0; ) {                   // 3. 比较数字是不是回文
            int left = x / div;             // 3.1 得到当前数字的最左位
            int right = x % 10;             // 3.2 得到当前数字的最右位
            if (left != right) {            // 3.3 比较最左位与最右位
                return false;
            }
            x = (x % div) / 10;             // 4. 去掉当前数字 x 的 最左位，最右位
            div = div / 100;                // 5. 算出当前数字 x 的最高位
        }
        return true;
    }

    private static void testIsPalindrome() {
        boolean res = isPalindrome0(123321);
        System.out.print(res);
    }

    public static void main(String[] args) {
        testIsPalindrome();
    }
}
