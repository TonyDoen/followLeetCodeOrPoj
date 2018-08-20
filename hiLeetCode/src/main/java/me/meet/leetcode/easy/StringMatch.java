package me.meet.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

public class StringMatch {
    /**
     * 字符串匹配问题
     * 假设现在我们面临这样一个问题：有一个文本串S，和一个模式串P，现在要查找P在S中的位置，怎么查找呢？
     * <p>
     * 如果用暴力匹配的思路，
     * 并假设现在文本串S匹配到 i 位置，模式串P匹配到 j 位置，则有：
     * 如果当前字符匹配成功（即S[i] == P[j]），则i++，j++，继续匹配下一个字符；
     * 如果失配（即S[i]! = P[j]），令i = i - (j - 1)，j = 0。相当于每次匹配失败时，i 回溯，j 被置为0。
     * <p>
     * 理清楚了暴力匹配算法的流程及内在的逻辑，咱们可以写出暴力匹配的代码，
     */
    static int violentMatch(String s, String p) {
        char[] source = s.toCharArray(), pattern = p.toCharArray();
        int sLen = source.length, pLen = pattern.length, si = 0, pi = 0;
        for (; si < sLen && pi < pLen; ) {
            if (source[si] == pattern[pi]) {
                // 当前字符匹配成功（即S[i] == P[j]），则i++，j++，
                si++;
                pi++;
            } else {
                // 失配（即S[i]! = P[j]），令i = i - (j - 1)，j = 0。相当于每次匹配失败时，i 回溯，j 被置为0
                si = si - pi + 1;
                pi = 0;
            }
        }
        if (pi == pLen) {
            return si - pi;
        }
        return -1;
    }

    /**
     * url: https://blog.csdn.net/v_july_v/article/details/7041827
     * url: https://www.cnblogs.com/zhangtianq/p/5839909.html
     * <p>
     * Knuth-Morris-Pratt 字符串查找算法，简称为 “KMP算法”，常用于在一个文本串S内查找一个模式串P 的出现位置，这个算法由Donald Knuth、Vaughan Pratt、James H. Morris三人于1977年联合发表，故取这3人的姓氏命名此算法。
     * 下面先直接给出KMP的算法流程（如果感到一点点不适，没关系，坚持下，稍后会有具体步骤及解释，越往后看越会柳暗花明☺）：
     * <p>
     * 假设现在文本串S匹配到 i 位置，模式串P匹配到 j 位置
     * 1. 如果j = -1，或者当前字符匹配成功（即S[i] == P[j]），都令i++，j++，继续匹配下一个字符；
     * 2. 如果j != -1，且当前字符匹配失败（即S[i] != P[j]），则令 i 不变，j = next[j]。
     * 此举意味着失配时，模式串P相对于文本串S向右移动了j - next [j] 位。
     * 换言之，当匹配失败时，模式串向右移动的位数为：失配字符所在位置 - 失配字符对应的next值, 即移动的实际位数为：j - next[j]，且此值大于等于1。
     * <p>
     * 很快，你也会意识到next 数组各值的含义：代表当前字符之前的字符串中，有多大长度的相同前缀后缀。例如如果next [j] = k，代表j 之前的字符串中有最大长度为k 的相同前缀后缀。
     * 也意味着在某个字符失配时，该字符对应的next 值会告诉你下一步匹配中，模式串应该跳到哪个位置（跳到next [j] 的位置）。如果next [j] 等于0或-1，则跳到模式串的开头字符，若next [j] = k 且 k > 0，代表下次匹配跳到j 之前的某个字符，而不是跳到开头，且具体跳过了k 个字符。
     */
    static int kmpMatch(String s, String p) {
        char[] source = s.toCharArray(), pattern = p.toCharArray();
        int sLen = source.length, pLen = pattern.length, si = 0, pi = 0, next[] = getNext(pattern);

        for (; si < sLen && pi < pLen; ) {
            if (-1 == pi || source[si] == pattern[pi]) {
                si++;
                pi++;
            } else {
                pi = next[pi];
            }
        }
        if (pi == pLen) {
            return si - pi;
        }
        return -1;
    }

    private static int[] getNext(char[] pattern) {
        int[] next = new int[pattern.length];
        next[0] = -1;
        for (int i = -1, j = 0; j < pattern.length - 1; ) {
            //p[i]表示前缀，p[j]表示后缀
            if (-1 == i || pattern[i] == pattern[j]) {
                // 若p[i] == p[j]，则next[j+1] = next[j] + 1 = i + 1；
                i++;
                j++;
                next[j] = i;
            } else {
                // 若p[i] ≠ p[j]，如果此时p[next[i]] == p[j]，则next[j+1] = next[i] + 1，否则继续递归前缀索引 i = next[i]，而后重复此过程。
                i = next[i];
            }
        }
        return next;
    }

    // 优化
    private static int[] getNext0(char[] pattern) {
        int[] next = new int[pattern.length];
        next[0] = -1;
        for (int i = -1, j = 0; j < pattern.length - 1; ) {
            //p[i]表示前缀，p[j]表示后缀
            if (-1 == i || pattern[i] == pattern[j]) {
                // 若p[i] == p[j]，则next[j+1] = next[j] + 1 = i + 1；
                i++;
                j++;
//                next[j] = i;
                // 优化
                if (pattern[i] != pattern[j]) {
                    next[j] = i;
                } else {
                    next[j] = next[i];
                }
            } else {
                // 若p[i] ≠ p[j]，如果此时p[next[i]] == p[j]，则next[j+1] = next[i] + 1，否则继续递归前缀索引 i = next[i]，而后重复此过程。
                i = next[i];
            }
        }
        return next;
    }

    /**
     * BM算法
     * KMP的匹配是从模式串的开头开始匹配的，而1977年，德克萨斯大学的Robert S. Boyer教授和J Strother Moore教授发明了一种新的字符串匹配算法：Boyer-Moore算法，简称BM算法。该算法从模式串的尾部开始匹配，且拥有在最坏情况下O(N)的时间复杂度。
     * 在实践中，比KMP算法的实际效能高。
     * <p>
     * BM算法定义了两个规则：
     * 1. 坏字符规则：当文本串中的某个字符跟模式串的某个字符不匹配时，我们称文本串中的这个失配字符为坏字符，此时模式串需要向右移动，移动的位数 = 坏字符在模式串中的位置 - 坏字符在模式串中最右出现的位置。此外，如果"坏字符"不包含在模式串之中，则最右出现位置为-1。
     * 2. 好后缀规则：当字符失配时，后移位数 = 好后缀在模式串中的位置 - 好后缀在模式串上一次出现的位置，且如果好后缀在模式串中没有再次出现，则为-1。
     */
    static int bmMatch(String s, String p) {
        char[] source = s.toCharArray(), pattern = p.toCharArray();
        int sLen = source.length, pLen = pattern.length, si = pattern.length, pi;
        for (; si <= sLen; ) {
            for (pi = pLen; pi > 0 && source[si - 1] == pattern[pi - 1]; ) {
                si--;
                pi--;
            }
            if (0 == pi) { // 表示完美匹配，返回其开始匹配的位置
                return si;
            } else {
                si = si + dist(pattern, source[si - 1], pi - 1);
            }
        }

        return -1;
    }

    private static int dist(char[] pattern, char target, int pos) {
        for (int i = pos; i > 0; i--) {
            if (pattern[i - 1] == target) { // 坏字符规则
                return pattern.length - i;
            }
        }
        return pattern.length;
    }


    /**
     * 我们已经介绍了KMP算法和BM算法，这两个算法在最坏情况下均具有线性的查找时间。
     * 但实际上，KMP算法并不比最简单的c库函数strstr()快多少，而BM算法虽然通常比KMP算法快，但BM算法也还不是现有字符串查找算法中最快的算法，
     * <p>
     * 本文最后再介绍一种比BM算法更快的查找算法即Sunday算法。Sunday算法由Daniel M.Sunday在1990年提出，它的思想跟BM算法很相似：
     * 只不过Sunday算法是从前往后匹配，在匹配失败时关注的是文本串中参加匹配的最末位字符的下一位字符。
     * 1. 如果该字符没有在模式串中出现则直接跳过，即移动位数 = 匹配串长度 + 1；
     * 2. 否则，其移动位数 = 模式串中最右端的该字符到末尾的距离+1。
     *    
     * 下面举个例子说明下Sunday算法。假定现在要在文本串"substring searching algorithm"中查找模式串"search"。
     */
    static int sundayMatch(String s, String p) {
        char[] source = s.toCharArray(), pattern = p.toCharArray();
        int sLen = source.length, pLen = pattern.length, sj = 0, si = 0, pi = 0;
        Map<Character, Integer> map = mapPattern(pattern);
        for (; si < sLen && pi < pLen; ) {
            if (source[si] == pattern[pi]) {
                si++;
                pi++;
            } else {
                int last1 = sj + pLen;
                if (sLen <= last1) {
                    break;
                }
                Integer last1Val = map.get(source[last1]);
                if (null == last1Val) {
                    sj = sj + pLen + 1;
                    si = sj;
                    pi = 0;
                } else {
                    sj = sj + last1Val;
                    si = sj;
                    pi = 0;
                }
            }
        }
        if (pi == pLen) {
            return si - pi;
        }
        return -1;
    }

    private static Map<Character, Integer> mapPattern(char[] pattern) {
        Map<Character, Integer> result = new HashMap<>();
        int length = pattern.length;
        for (int i = length - 1; i > -1; i--) {
            result.putIfAbsent(pattern[i], length - i);
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "BBC ABCDAB ABCDABCDABDE", p = "ABCDABD";
        int r1 = violentMatch(s, p);
        int r2 = kmpMatch(s, p);
        int r3 = sundayMatch(s, p);
        int r4 = bmMatch(s, p);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(r4);
    }
}
