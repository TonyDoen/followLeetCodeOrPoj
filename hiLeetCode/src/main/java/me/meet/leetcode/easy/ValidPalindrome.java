package me.meet.leetcode.easy;

public final class ValidPalindrome {
    private ValidPalindrome() {}
    /**
     * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
     *
     * For example,
     * "A man, a plan, a canal: Panama" is a palindrome.
     * "race a car" is not a palindrome.
     *
     * Note:
     * Have you consider that the string might be empty? This is a good question to ask during an interview.
     * For the purpose of this problem, we define empty string as valid palindrome.
     */
    /**
     * 题意：验证一句话是不是回文串
     * 思路：验证回文字符串是比较常见的问题，所谓回文，就是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串。
     * 这里，加入了空格和非字母数字的字符，增加了些难度，
     * 其实原理还是很简单：只需要建立两个指针，left和right, 分别从字符的开头和结尾处开始遍历整个字符串，如果遇到非字母数字的字符就跳过，继续往下找，直到找到下一个字母数字或者结束遍历，如果遇到大写字母，就将其转为小写。
     * 等左右指针都找到字母数字时，比较这两个字符，若相等，则继续比较下面两个分别找到的字母数字，若不相等，直接返回false.
     *
     * (首尾指针分别遍历，直到头指针i>=尾指针j)
     * 1、使用双指针分别从字符串首尾开始遍历(条件为i<j)
     * 2、如果是非字母、数字字符，则忽略，指针前移(i<j && xxx)
     * 3、如果是字母数字，则比较两个指针指向的字符是否相同，不相同则返回false(i<j && xxx)
     *
     * 时间复杂度为O(n)
     */
    static boolean isPalindrome(String s) {
        char[] arr = s.toCharArray();
        int left = 0, right = s.length() - 1;
        for (; left < right; ) {
            char lc = arr[left];
            char rc = arr[right];
            if (!isAlphaNum(lc)) {
                left++;
            } else if (!isAlphaNum(rc)) {
                right--;
            } else if (!isEqual(lc, rc)) {
                return false;
            } else {
                left++;
                right--;
            }
        }
        return true;
    }

    private static boolean isAlphaNum(char c) { // considering only alphanumeric(字母或数字) characters
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        if (c >= 'A' && c <= 'Z') {
            return true;
        }
        return c >= '0' && c <= '9';
    }

    private static boolean isEqual(char a, char b) {
        return a == b || (32 == Math.abs(a - b)); // ignoring cases.
    }

    private static void testIsPalindrome() {
        String src = "A man, a plan, a canal: Panama";
        boolean res = isPalindrome(src);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testIsPalindrome();
    }
}
