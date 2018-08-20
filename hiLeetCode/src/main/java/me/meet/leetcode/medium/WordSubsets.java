package me.meet.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

public final class WordSubsets {
    /**
     * url: https://www.cnblogs.com/grandyang/p/11623684.html
     *
     * Word Subsets
     * We are given two arrays A and B of words.  Each word is a string of lowercase letters.
     * Now, say that word b is a subset of word a if every letter in b occurs in a, including multiplicity.  For example, "wrr" is a subset of "warrior", but is not a subset of "world".
     * Now say a word a from A is universal if for every b in B, b is a subset of a.
     * Return a list of all universal words in A.  You can return the words in any order.
     *
     * Example 1:
     * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["e","o"]
     * Output: ["facebook","google","leetcode"]
     *
     * Example 2:
     * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["l","e"]
     * Output: ["apple","google","leetcode"]
     *
     * Example 3:
     * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["e","oo"]
     * Output: ["facebook","google"]
     *
     * Example 4:
     * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["lo","eo"]
     * Output: ["google","leetcode"]
     *
     * Example 5:
     * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["ec","oc","ceo"]
     * Output: ["facebook","leetcode"]
     *
     * Note:
     * 1 <= A.length, B.length <= 10000
     * 1 <= A[i].length, B[i].length <= 10
     * A[i] and B[i] consist only of lowercase letters.
     * All words in A[i] are unique: there isn't i != j with A[i] == A[j].
     */

    /**
     * 题意：单词子集合
     * 思路：这道题定义了两个单词之间的一种子集合关系，就是说假如单词b中的每个字母都在单词a中出现了（包括重复字母），就说单词b是单词a的子集合。现在给了两个单词集合A和B，让找出集合A中的所有满足要求的单词，使得集合B中的所有单词都是其子集合。
     * 配合上题目中给的一堆例子，意思并不难理解，根据子集合的定义关系，其实就是说若单词a中的每个字母的出现次数都大于等于单词b中每个字母的出现次数，单词b就一定是a的子集合。现在由于集合B中的所有单词都必须是A中某个单词的子集合，那么其实只要对于每个字母，都统计出集合B中某个单词中出现的最大次数，
     * 比如对于这个例子，B=["eo","oo"]，其中e最多出现1次，而o最多出现2次，那么只要集合A中有单词的e出现不少1次，o出现不少于2次，则集合B中的所有单词一定都是其子集合。
     * 这就是本题的解题思路，这里使用一个大小为 26 的一维数组 charCnt 来统计集合B中每个字母的最大出现次数，而将统计每个单词的字母次数的操作放到一个子函数 helper 中，当 charCnt 数组更新完毕后，下面就开始检验集合A中的所有单词了。对于每个遍历到的单词，还是要先统计其每个字母的出现次数，然后跟 charCnt 中每个位置上的数字比较，只要均大于等于 charCnt 中的数字，就可以加入到结果 res 中了
     */
    static List<String> wordSubsets(String[] a, String[] b) {
        List<String> res = new ArrayList<>(a.length);
        int[] charCnt = new int[26];
        for (String c : b) {                                      // 1. 统计模式串 b 中，字母分布
            int[] t = count(c);
            for (int i = 0; i < 26; i++) {                        // 1.1 给出 模式串 b 中，字母统计值
                charCnt[i] = Math.max(charCnt[i], t[i]);
            }
        }
        for (String c : a) {                                      // 2. 统计目标串 a 中，字母分布
            int[] t = count(c);
            int i = 0;
            for (; i < 26; i++) {                                 // 2.1 判断 目标串 a 中，符合条件的
                if (t[i] < charCnt[i]) {
                    break;
                }
            }
            if (i == 26) {                                        // 2.2 符合条件的字符一定标记到最后（共26个字母）
                res.add(c);
            }
        }
        return res;
    }

    private static int[] count(String c) {
        int[] res = new int[26];
        int length = c.length();
        for (int i = 0; i < length; i++) {
            int index = c.charAt(i) - 'a';
            res[index]++;
        }
        return res;
    }

    public static void main(String[] args) {
        // A = ["amazon","apple","facebook","google","leetcode"], B = ["e","o"]
        String[] a = new String[]{"amazon", "apple", "facebook", "google", "leetcode"};
        String[] b = new String[]{"e", "o"};
        List<String> res = wordSubsets(a, b);
        System.out.println(res);
    }
}
