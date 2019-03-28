package org.zhd.test;

public class InShuffle {
    /**
     * 给定一个字符串，要求把字符串前面的若干个字符移动到字符串的尾部，如把字符串“abcdef”前面的2个字符'a'和'b'移动到字符串的尾部，使得原字符串变成字符串“cdefab”。
     * 请写一个函数完成此功能，要求对长度为n的字符串操作的时间复杂度为 O(n)，空间复杂度为 O(1)。
     *
     */
    static void reverseStr(char[] s, int from, int to) {
        while (from < to) {
            char t = s[from];
            s[from++] = s[to];
            s[to--] = t;
        }
    }

    static String leftRotateStr(String s, int k) {
        if (null == s || k > s.length()) {
            throw new IllegalArgumentException();
        }

        char[] arr = s.toCharArray();
        reverseStr(arr, 0, k - 1);
        reverseStr(arr, k, arr.length - 1);
        reverseStr(arr, 0, arr.length - 1);
        return new String(arr);
    }

    public static void main(String[] args) {
        String res = leftRotateStr("abcdefj", 3);
        System.out.println(res);
    }
}
