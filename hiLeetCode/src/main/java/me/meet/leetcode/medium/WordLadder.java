package me.meet.leetcode.medium;


import java.util.*;

public final class WordLadder {
    /**
     * Given two words (start and end), and a dictionary, 
     * find the length of shortest transformation sequence from start to end, 
     * such that:
     * Only one letter can be changed at a time
     * Each intermediate word must exist in the dictionary
     * 
     * For example,
     * Given:
     * start ="hit"
     * end ="cog"
     * dict =["hot","dot","dog","lot","log"]
     * As one shortest transformation is"hit" -> "hot" -> "dot" -> "dog" -> "cog",
     * return its length 5.
     * 
     * Note:
     * Return 0 if there is no such transformation sequence.
     * All words have the same length.
     * All words contain only lowercase alphabetic characters.
     */
    /**
     * 题意：这道词句阶梯的问题给了我们一个单词字典，里面有一系列很相似的单词，然后给了一个起始单词和一个结束单词，每次变换只能改变一个单词，并且中间过程的单词都必须是单词字典中的单词，让我们求出最短的变化序列的长度。
     * 思路：
     * 如果让我们肉身解题该怎么做呢？让你将 'hit' 变为 'cog'，那么我们发现这两个单词没有一个相同的字母，
     * 先将第一个 'h' 换成 'c'，看看 'cit' 在不在字典中，发现不在，
     * 那么把第二个 'i' 换成 'o'，看看 'hot' 在不在，发现在，完美！
     * 然后尝试 'cot' 或者 'hog'，发现都不在，那么就比较麻烦了，我们没法快速的达到目标单词，需要一些中间状态，但我们怎么知道中间状态是什么。
     *
     * 简单粗暴的方法就是brute force，遍历所有的情况，我们将起始单词的每一个字母都用26个字母来替换，比如起始单词 'hit' 就要替换为 'ait', 'bit', 'cit', .... 'yit', 'zit'，将每个替换成的单词都在字典中查找一下，如果有的话，那么说明可能是潜在的路径，要保存下来。
     * 那么现在就有个问题，比如我们换到了 'hot' 的时候，此时发现在字典中存在，那么下一步我们是继续试接下来的 'hpt', 'hqt', 'hrt'... 还是直接从 'hot' 的首字母开始换 'aot', 'bot', 'cot' ... 这实际上就是BFS和DFS的区别，到底是广度优先，还是深度优先。
     * 不知道你有没有觉得这个跟什么很像？对了，跟迷宫遍历很像啊，你想啊，迷宫中每个点有上下左右四个方向可以走，而这里有26个字母，就是二十六个方向可以走，本质上没有啥区别啊！
     * 熟悉迷宫遍历的童鞋们应该知道，应该用BFS来求最短路径的长度，这也不难理解啊，DFS相当于一条路走到黑啊，你走的那条道不一定是最短的啊。而BFS相当于一个小圈慢慢的一层一层扩大，相当于往湖里扔个石头，一圈一圈扩大的水波纹那种感觉，当水波纹碰到湖上的树叶时，那么此时水圈的半径就是圆心到树叶的最短距离。
     *
     *
     * 1、广度优先搜索思想，上图中每一列中的元素为每次循环队列处理的元素
     * 2、新建一个队列，队列第一个元素初始化为start 
     * 3、判断当前队列中的元素是否与end只差别一个字母，是则返回
     * 3、否则放入dict中与start相差一个的所有元素为队列的下一组元素
     * 3、队列中没有元素了，还没找到，则返回0
     */
    static int ladderLength(String start, String end, Set<String> dict) {
        if (null == start || null == end || null == dict) {
            return 0;
        }

        LinkedList<String> queue = new LinkedList<>();
        queue.add(start);

        int result = 0;
        for (; !queue.isEmpty(); ) {
            result++;

            for (int size = queue.size(); size > 0;) {
                String str = queue.poll();
                size--;
                if (isDiff1Char(str, end)) {
                    return result + 1;
                } else {
                    Iterator<String> it = dict.iterator();
                    for (; it.hasNext();) {
                        String cur = it.next();
                        if (isDiff1Char(str, cur)) {
                            queue.add(cur);
                            it.remove();
                        }
                    }
                }
            }
        }
        return 0;
    }

    private static boolean isDiff1Char(String a, String b) {
        if (null == a || null == b || a.length() != b.length()) {
            return false;
        }
        int count = 0;
        char[] aArr = a.toCharArray(), bArr = b.toCharArray();
        for (int i = aArr.length - 1; i >= 0; i--) {
            if (aArr[i] != bArr[i]) {
                count++;
            }
        }
        return 1 == count;
    }

    private static void testLadderLength() {
        String start = "hit";
        String end = "cog";
        String[] tmp = {"hot","dot","dog","lot","log"};
        HashSet<String> dict = new HashSet<>(Arrays.asList(tmp));
        int res = ladderLength(start, end, dict);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testLadderLength();
    }
}



























