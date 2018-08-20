package me.meet.leetcode.easy;

import java.util.*;

public final class Subsets {
    private Subsets() {
    }

    /**
     * Given a set of distinct integers, S, return all possible subsets.
     *
     * Note:
     * Elements in a subset must be in non-descending order.
     * The solution set must not contain duplicate subsets.
     *
     * For example,
     * If S = [1,2,3], a solution is:
     * [ [3],
     *   [1],
     *   [2],
     *   [1,2,3],
     *   [1,3],
     *   [2,3],
     *   [1,2],
     *   [] ]
     */
    /**
     * 题意：子集合
     * 这道求子集合的问题，由于其要列出所有结果，按照以往的经验，肯定要是要用递归来做。
     * <p>
     * 思路：
     * 这道题其实它的非递归解法相对来说更简单一点，下面我们先来看非递归的解法，由于题目要求子集合中数字的顺序是非降序排列的，所有我们需要预处理，先给输入数组排序，然后再进一步处理，最开始我在想的时候，是想按照子集的长度由少到多全部写出来，比如子集长度为0的就是空集，空集是任何集合的子集，满足条件，直接加入。下面长度为1的子集，直接一个循环加入所有数字，子集长度为2的话可以用两个循环，但是这种想法到后面就行不通了，因为循环的个数不能无限的增长，所以我们必须换一种思路。我们可以一位一位的网上叠加，比如对于题目中给的例子 [1,2,3] 来说，最开始是空集，那么我们现在要处理1，就在空集上加1，为 [1]，现在我们有两个自己 [] 和 [1]，下面我们来处理2，我们在之前的子集基础上，每个都加个2，可以分别得到 [2]，[1, 2]，那么现在所有的子集合为 [], [1], [2], [1, 2]，同理处理3的情况可得 [3], [1, 3], [2, 3], [1, 2, 3], 再加上之前的子集就是所有的子集合了，代码如下：
     */
    static List<List<Integer>> subset(int[] s) {
        // init
        List<List<Integer>> res = new ArrayList<>(1 << s.length);
        res.add(new LinkedList<>());

        Arrays.sort(s);
        for (int value : s) {
            int size = res.size();
            for (int i = 0; i < size; i++) {
                List<Integer> cll = new LinkedList<>(res.get(i));
                cll.add(value);
                res.add(cll);
            }
        }
        return res;
    }

    /**
     * 思路：
     * 下面来看递归的解法，相当于一种深度优先搜索，参见网友 JustDoIt的博客，由于原集合每一个数字只有两种状态，要么存在，要么不存在，那么在构造子集时就有选择和不选择两种情况，所以可以构造一棵二叉树，左子树表示选择该层处理的节点，右子树表示不选择，最终的叶节点就是所有子集合，树的结构如下：
     * 复制代码
     *                         []
     *                    /          \
     *                   /            \
     *                  /              \
     *               [1]                []
     *            /       \           /    \
     *           /         \         /      \
     *        [1 2]       [1]       [2]     []
     *       /     \     /   \     /   \    / \
     *   [1 2 3] [1 2] [1 3] [1] [2 3] [2] [3] []
     */

    /**
     * 思路：
     * 最后我们再来看一种解法，这种解法是 CareerCup 书上给的一种解法，想法也比较巧妙，把数组中所有的数分配一个状态，true 表示这个数在子集中出现，false 表示在子集中不出现，那么对于一个长度为n的数组，每个数字都有出现与不出现两种情况，所以共有 2n 中情况，那么我们把每种情况都转换出来就是子集了，我们还是用题目中的例子, [1 2 3] 这个数组共有8个子集，每个子集的序号的二进制表示，把是1的位对应原数组中的数字取出来就是一个子集，八种情况都取出来就是所有的子集了，参见代码如下：
     *          1	2	3	Subset
     * 0(001)	F	F	F	[]
     * 1(001)	F	F	T	3
     * 2(010)	F	T	F	2
     * 3(011)	F	T	T	23
     * 4(100)	T	F	F	1
     * 5(101)	T	F	T	13
     * 6(110)	T	T	F	12
     * 7(111)	T	T	T	123
     */
    static List<List<Integer>> subset2(int[] s) {
        int max = 1 << s.length;
        // init
        List<List<Integer>> res = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            res.add(convert(s, i));
        }
        return res;
    }
    private static List<Integer> convert(int[] s, int k) {
        LinkedList<Integer> res = new LinkedList<>();
        int idx = 0;
        for (int i = k; i > 0; i >>= 1) {
            if ((i & 1) == 1) {
                res.add(s[idx]);
            }
            ++idx;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3};
        List<List<Integer>> res = subset(arr);
        System.out.println(res);

        List<List<Integer>> res2 = subset2(arr);
        System.out.println(res2);
    }
}
