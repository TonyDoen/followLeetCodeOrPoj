package me.meet.leetcode.medium;


import java.util.*;

public final class WordLadder2 {
    static class WordNode {
        String word;
        int numSteps;
        WordNode pre;

        WordNode(String word, int numSteps, WordNode pre) {
            this.word = word;
            this.numSteps = numSteps;
            this.pre = pre;
        }
    }

    /**
     * Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation sequence(s) from beginWord to endWord, such that:
     * Only one letter can be changed at a time
     * Each transformed word must exist in the word list.
     * Note that beginWord is not a transformed word.
     *
     * Note:
     * Return an empty list if there is no such transformation sequence.
     * All words have the same length.
     * All words contain only lowercase alphabetic characters.
     * You may assume no duplicates in the word list.
     * You may assume beginWord and endWord are non-empty and are not the same.
     *
     * Example 1:
     * Input:
     * beginWord = "hit",
     * endWord = "cog",
     * wordList = ["hot","dot","dog","lot","log","cog"]
     *
     * Output:
     * [
     *   ["hit","hot","dot","dog","cog"],
     *   ["hit","hot","lot","log","cog"]
     * ]
     *
     * Example 2:
     * Input:
     * beginWord = "hit"
     * endWord = "cog"
     * wordList = ["hot","dot","dog","lot","log"]
     *
     * Output: []
     * Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
     */
    /**
     * 题意：给一个单词字典，把一个起始单词变为结束单词，每次只能变化一个字符，而且变化的中间词都在字典中，找出所有最短路径变换的组合。
     * 思路：
     * BFS
     * 因为要记录路径, 所以要为每一个结点记录一下父结点, 这样最后的时候我们就从终点开始一个个往前找其父结点, 直到找到起始点, 然后翻转一下加入结果集合中即可.
     *
     * 大概过程差不多, 但是有点不同的是当将字典中的一个字符串删除的时候在另一条路径上可能还会用到这个字符. 也就是像这样:
     * A -> C -> D, B->C->D
     * 他们都会经过C, 并且两个都是最短的路径, 在A的时候搜索到C, 并且将C从字典中删除, 当B在搜索与其距离为1的字符串时, C已经不在字典中了, 那么怎么办呢?
     * 我们设置一个hash表用来存储一个字符串的父结点集合, 这样C不在字典中再去查hash表看C是否在hash表中, 如果在的话并且C的父结点层次和B一样, 那么就将B也加入到C的父结点结合中去.
     * 可以知道, 一个字符串的父结点集合的距离起点的距离必然是相等的, 也就是说他们都是最短距离.
     * 最后遍历完所有的点之后, 再用DFS从终点往前找出所有集合即可.
     */
    static List<List<String>> findLadder(String start, String end, Set<String> dict) {
        List<List<String>> result = new ArrayList<>();
        LinkedList<WordNode> queue = new LinkedList<>();
        queue.add(new WordNode(start, 1, null));
        dict.add(end);
        Set<String> visited = new HashSet<>(), unvisited = new HashSet<>(dict);
        int minStep = 0, preNumSteps = 0;
        for (; !queue.isEmpty(); ) {
            WordNode top = queue.remove();
            String word = top.word;
            int currNumSteps = top.numSteps;
            if (word.equals(end)) {
                if (minStep == 0) {
                    minStep = top.numSteps;
                }
                if (top.numSteps == minStep && minStep != 0) {
                    //nothing
                    ArrayList<String> t = new ArrayList<>();
                    t.add(top.word);
                    for (; top.pre != null; ) {
                        t.add(0, top.pre.word);
                        top = top.pre;
                    }
                    result.add(t);
                    continue;
                }
            }
            if (preNumSteps < currNumSteps) {
                unvisited.removeAll(visited);
            }
            preNumSteps = currNumSteps;
            char[] arr = word.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    char temp = arr[i];
                    if (arr[i] != c) {
                        arr[i] = c;
                    }
                    String newWord = new String(arr);
                    if (unvisited.contains(newWord)) {
                        queue.add(new WordNode(newWord, top.numSteps + 1, top));
                        visited.add(newWord);
                    }
                    arr[i] = temp;
                }
            }
        }
        return result;
    }

    private static void testFindLadder() {
        String start = "hit";
        String end = "cog";
        String[] tmp = {"hot", "dot", "dog", "lot", "log", "cot", "hig", "hog"};
        HashSet<String> dict = new HashSet<>(Arrays.asList(tmp));
        List<List<String>> res = findLadder(start, end, dict);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testFindLadder();
    }
}



























