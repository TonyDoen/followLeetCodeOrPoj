package me.meet.leetcode.easy;

import java.util.HashSet;

public final class UniqueMorseCodeWords {
    private UniqueMorseCodeWords() {}

    /**
     International Morse Code defines a standard encoding where each letter is mapped to a series of dots and dashes, as follows: "a" maps to ".-", "b" maps to "-...", "c" maps to "-.-.", and so on.
     For convenience, the full table for the 26 letters of the English alphabet is given below:
       a    b      c      d     e   f      g     h      i    j      k     l      m    n    o     p      q      r     s     t   u     v      w     x      y      z
     [".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."]
     Now, given a list of words, each word can be written as a concatenation of the Morse code of each letter. For example, "cab" can be written as "-.-.-....-", (which is the concatenation "-.-." + "-..." + ".-"). We'll call such a concatenation, the transformation of a word.
     Return the number of different transformations among all words we have.

     Example:
     Input: words = ["gin", "zen", "gig", "msg"]
     Output: 2
     Explanation:
     The transformation of each word is:
     "gin" -> "--...-."
     "zen" -> "--...-."
     "gig" -> "--...--."
     "msg" -> "--...--."
     There are 2 different transformations, "--...-." and "--...--.".

     Note:
     The length of words will be at most 100.
     Each words[i] will have length in range [1, 12].
     words[i] will only consist of lowercase letters.
     */

    /**
     题意：独特的摩斯码单词
     给了我们所有字母的摩斯码的写法，然后给了我们一个单词数组，问我们表示这些单词的摩斯码有多少种。因为某些单词的摩斯码表示是相同的，比如gin和zen就是相同的。最简单直接的方法就是我们求出每一个单词的摩斯码，然后将其放入一个HashSet中，利用其去重复的特性，从而实现题目的要求，最终HashSet中元素的个数即为所求
     */

    static int uniqueMorseRepresentations(String[] words) {
        String[] morse = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        HashSet<String> s = new HashSet<String>();
        for (String word : words) {
            StringBuilder t = new StringBuilder();
            for (char c : word.toCharArray()) {
                t.append(morse[c - 'a']);
            }
            s.add(t.toString());
        }
        return s.size();
    }

    public static void main(String[] args) {
        String[] words = {"gin", "zen", "gig", "msg"};

        int res = uniqueMorseRepresentations(words);
        System.out.println(res);
    }
}
