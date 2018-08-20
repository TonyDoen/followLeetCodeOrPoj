package me.meet.leetcode.hard;

public final class ValidPermutations4DISequence {
    private ValidPermutations4DISequence() {}

    /**
     * We are given S, a length n string of characters from the set {'D', 'I'}. (These letters stand for "decreasing" and "increasing".)
     * A valid permutation is a permutation P[0], P[1], ..., P[n] of integers {0, 1, ..., n}, such that for all i:
     * If S[i] == 'D', then P[i] > P[i+1], and;
     * If S[i] == 'I', then P[i] < P[i+1].
     * How many valid permutations are there?  Since the answer may be large, return your answer modulo 10^9 + 7.
     *
     * Example 1:
     * Input: "DID"
     * Output: 5
     * Explanation:
     * The 5 valid permutations of (0, 1, 2, 3) are:
     * (1, 0, 3, 2)
     * (2, 0, 3, 1)
     * (2, 1, 3, 0)
     * (3, 0, 2, 1)
     * (3, 1, 2, 0)
     *
     * Note:
     * 1 <= S.length <= 200
     * S consists only of characters from the set {'D', 'I'}.
     */

    /**
     * 题意：DI序列的有效排列
     * 这道题给了我们一个长度为n的字符串S，里面只有两个字母D和I，分别表示下降 Decrease 和上升 Increase，意思是对于 [0, n] 内所有数字的排序，必须满足S的模式，比如题目中给的例子 S="DID"，就表示序列需要先降，再升，再降，于是就有那5种情况。题目中提示了结果可能会是一个超大数，让我们对 1e9+7 取余，经验丰富的刷题老司机看到这里就知道肯定不能递归遍历所有情况，编译器估计都不允许，这里动态规划 Dynamic Programming 就是不二之选，但是这道题正确的动态规划解法其实是比较难想出来的，因为存在着关键的隐藏信息 Hidden Information，若不能正确的挖掘出来（山东布鲁斯特挖掘机专业了解一下？），是不太容易解出来的。首先来定义我们的 DP 数组吧，这里大家的第一直觉可能是想着就用一个一维数组 dp，其中 dp[i] 表示范围在 [0, i] 内的字符串S的子串能有的不同序列的个数。这样定义的话，就无法写出状态转移方程，像之前说的，我们忽略了很关键的隐藏信息。先来想，序列是升是降到底跟什么关系最大，答案是最后一个数字，比如我们现在有一个数字3，当前的模式是D，说明需要下降，所以可能的数字就是 0，1，2，但如果当前的数字是1，那么还要下降的话，那么貌似就只能加0了？其实也不一定，因为这道题说了只需要保证升降模式正确就行了，数字之间的顺序关系其实并不重要，举个例子来说吧，假如我们现在已经有了一个 "DID" 模式的序列 1032，假如我们还想加一个D，变成 "DIDD"，该怎么加数字呢？多了一个模式符，就多了一个数字4，显然直接加4是不行的，实际是可以在末尾加2的，但是要先把原序列中大于等于2的数字都先加1，即 1032 -> 1043，然后再加2，变成 10432，就是 "DIDD" 了。虽然我们改变了序列的数字顺序，但是升降模式还是保持不变的。同理，也是可以加1的，1032 -> 2043 -> 20431，也是可以加0的，1032 -> 2143 -> 21430。但是无法加3和4，因为 1032 最后一个数字2很很重要，所有小于等于2的数字，都可以加在后面，从而形成降序。那么反过来也是一样，若要加个升序，比如变成 "DIDI"，猜也猜的出来，后面要加大于2的数字，然后把所有大于等于这个数字的地方都减1，比如加上3，1032 -> 1042 -> 10423，再比如加上4，1032 -> 1032 -> 10324。
     * 通过上面的分析，我们知道了最后一个位置的数字的大小非常的重要，不管是要新加升序还是降序，最后的数字的大小直接决定了能形成多少个不同的序列，这个就是本题的隐藏信息，所以我们在定义 dp 数组的时候必须要把最后一个数字考虑进去，这样就需要一个二维的 dp 数组，其中 dp[i][j] 表示由范围 [0, i] 内的数字组成且最后一个数字为j的不同序列的个数。就拿题目中的例子来说吧，由数字 [0, 1, 2, 3] 组成 "DID" 模式的序列，首先 dp[0][0] 是要初始化为1，如下所示（括号里是实际的序列）：
     * dp[0][0] = 1 (0)
     * 然后需要加第二个数字，由于需要降序，那么根据之前的分析，加的数字不能大于最后一个数字0，则只能加0，如下所示：
     * 加0:  ( dp[1][0] = 1 )
     * 0 -> 1 -> 10
     * 然后需要加第三个数字，由于需要升序，那么根据之前的分析，加的数字不能小于最后一个数字0，那么实际上可以加的数字有 1，2，如下所示：
     * 加1:  ( dp[2][1] = 1 )
     * 10 -> 20 -> 201
     * 加2:  ( dp[2][2] = 1 )
     * 10 -> 10 -> 102
     * 然后需要加第四个数字，由于需要降序，那么根据之前的分析，加的数字不能大于最后一个数字，上一轮的最后一个数字有1或2，那么实际上可以加的数字有 0，1，2，如下所示：
     * 加0:  ( dp[3][0] = 2 )
     * 201 -> 312 -> 3120
     * 102 -> 213 -> 2130
     * 加1:  ( dp[3][1] = 2 )
     * 201 -> 302 -> 3021
     * 102 -> 203 -> 2031
     * 加2:  ( dp[3][2] = 1 )
     * 102 -> 103 -> 1032
     *
     * 这种方法算出的 dp 数组为：
     * 1 0 0 0
     * 1 0 0 0
     * 0 1 1 0
     * 2 2 1 0
     *
     * 最后把 dp 数组的最后一行加起来 2+2+1 = 5 就是最终的结果，分析到这里，其实状态转移方程已经不难得到了，根据前面的分析，当是降序时，下一个数字不小于当前最后一个数字，反之是升序时，下一个数字小于当前最后一个数字，所以可以写出状态转移方程如下所示：
     * if (S[i-1] == 'D')    dp[i][j] += dp[i-1][k]    ( j <= k <= i-1 )
     * else                  dp[i][j] += dp[i-1][k]    ( 0 <= k < j )
     */

}
