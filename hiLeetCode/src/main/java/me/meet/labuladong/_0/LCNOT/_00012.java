package me.meet.labuladong._0.LCNOT;

public final class _00012 {
    private _00012() {
    }

    /*
     * 滑动窗口技巧
     * 关于双指针的快慢指针和左右指针的用法，可以参见前文 双指针技巧汇总，本文就解决一类最难掌握的双指针技巧：滑动窗口技巧，并总结出一套框架，可以保你闭着眼直接套出答案。
     * 说起滑动窗口算法，很多读者都会头疼。这个算法技巧的思路非常简单，就是维护一个窗口，不断滑动，然后更新答案么。
     *
     * LeetCode 上有起码 10 道运用滑动窗口算法的题目，难度都是中等和困难。该算法的大致逻辑如下：
     * int left = 0, right = 0;
     *
     * while (right < s.size()) {
     *     // 增大窗口
     *     window.add(s[right]);
     *     right++;
     *
     *     while (window needs shrink) {
     *         // 缩小窗口
     *         window.remove(s[left]);
     *         left++;
     *     }
     * }
     *
     * 这个算法技巧的时间复杂度是 O(N)，比一般的字符串暴力算法要高效得多。
     *
     * 其实困扰大家的，不是算法的思路，而是各种细节问题。
     * 比如说如何向窗口中添加新元素，如何缩小窗口，在窗口滑动的哪个阶段更新结果。
     * 即便你明白了这些细节，也容易出 bug，找 bug 还不知道怎么找，真的挺让人心烦的。
     *
     * 所以今天我就写一套滑动窗口算法的代码框架，我连在哪里做输出 debug 都给你写好了，以后遇到相关的问题，你就默写出来如下框架然后改三个地方就行，还不会出边界问题：
     * // 滑动窗口算法框架
     * void slidingWindow(string s, string t) {
     *     unordered_map<char, int> need, window;
     *     for (char c : t) need[c]++;
     *
     *     int left = 0, right = 0;
     *     int valid = 0;
     *     while (right < s.size()) {
     *         // c 是将移入窗口的字符
     *         char c = s[right];
     *         // 右移窗口
     *         right++;
     *         // 进行窗口内数据的一系列更新
     *         ...
     *
     *         // debug 输出的位置
     *         printf("window: [%d, %d)\n", left, right);
     *
     *         // 判断左侧窗口是否要收缩
     *         while (window needs shrink) {
     *             // d 是将移出窗口的字符
     *             char d = s[left];
     *             // 左移窗口
     *             left++;
     *             // 进行窗口内数据的一系列更新
     *             ...
     *         }
     *     }
     * }
     *
     * 其中两处...表示的更新窗口数据的地方，到时候你直接往里面填就行了。而且，这两个...处的操作分别是右移和左移窗口更新操作，等会你会发现它们操作是完全对称的。
     * 说句题外话，其实有很多人喜欢执着于表象，不喜欢探求问题的本质。比如说有很多人评论我这个框架，说什么散列表速度慢，不如用数组代替散列表；
     * labuladong 公众号的重点在于算法思想，你把框架思维了然于心套出解法，然后随你再魔改代码好吧，你高兴就好。
     * 言归正传，下面就直接上四道 LeetCode 原题来套这个框架，其中第一道题会详细说明其原理，后面四道就直接闭眼睛秒杀了。
     *
     *
     *
     * 一、最小覆盖子串
     * LeetCode 76 题，Minimum Window Substring，难度 Hard，我带大家看看它到底有多 Hard：
     *
     * 给你一个字符串S、一个字符串T,请在字符串S里面找出:告包含T所有字母的最小子串。
     *
     * 示例:
     * 输入:S="ADOBECODEBANC",T="ABC"
     * 输出:"BANC"
     *
     * 说明:
     * 如果S中不存这样的子串,则返回空字符串""。
     * 如果S中存在这样的子串,我们保证它是唯一的答案。
     *
     * 就是说要在S(source) 中找到包含T(target) 中全部字母的一个子串，且这个子串一定是所有可能子串中最短的。如果我们使用暴力解法，代码大概是这样的：
     * for (int i = 0; i < s.size(); i++)
     *     for (int j = i + 1; j < s.size(); j++)
     *         if s[i:j] 包含 t 的所有字母:
     *             更新答案
     * 思路很直接，但是显然，这个算法的复杂度肯定大于 O(N^2) 了，不好。
     *
     * 滑动窗口算法的思路是这样：
     * 1、我们在字符串S中使用双指针中的左右指针技巧，初始化left = right = 0，把索引左闭右开区间[left, right)称为一个「窗口」。
     * 2、我们先不断地增加right指针扩大窗口[left, right)，直到窗口中的字符串符合要求（包含了T中的所有字符）。
     * 3、此时，我们停止增加right，转而不断增加left指针缩小窗口[left, right)，直到窗口中的字符串不再符合要求（不包含T中的所有字符了）。同时，每次增加left，我们都要更新一轮结果。
     * 4、重复第 2 和第 3 步，直到right到达字符串S的尽头。
     *
     * 这个思路其实也不难，第 2 步相当于在寻找一个「可行解」，然后第 3 步在优化这个「可行解」，最终找到最优解，也就是最短的覆盖子串。
     * 左右指针轮流前进，窗口大小增增减减，窗口不断向右滑动，这就是「滑动窗口」这个名字的来历。
     * 下面画图理解一下，needs和window相当于计数器，分别记录T中字符出现次数和「窗口」中的相应字符的出现次数。
     *
     *
     *
     * 二、字符串排列
     * LeetCode 567 题，Permutation in String，难度 Medium：
     * 给定两个字符串s1和s2,写一个函数来判断s2是否包含s1的的排列换句话说,第一个字符串的排列之一是第二个字符串的子串。
     *
     * 示例1:
     * 输入: s1="ab"s2="eidbaooo"
     * 输出: True
     * 解释: s2包含s1的排列之一("ba").
     *
     * 示例2:
     * 输入: s1= "ab"s2="eidboaoo"
     * 输出: False
     *
     * 注意哦，输入的s1是可以包含重复字符的，所以这个题难度不小。
     * 这种题目，是明显的滑动窗口算法，相当给你一个S和一个T，请问你S中是否存在一个子串，包含T中所有字符且不包含其他字符？
     * 首先，先复制粘贴之前的算法框架代码，然后明确刚才提出的 4 个问题，即可写出这道题的答案：
     * // 判断 s 中是否存在 t 的排列
     * bool checkInclusion(string t, string s) {
     *     unordered_map<char, int> need, window;
     *     for (char c : t) need[c]++;
     *
     *     int left = 0, right = 0;
     *     int valid = 0;
     *     while (right < s.size()) {
     *         char c = s[right];
     *         right++;
     *         // 进行窗口内数据的一系列更新
     *         if (need.count(c)) {
     *             window[c]++;
     *             if (window[c] == need[c])
     *                 valid++;
     *         }
     *
     *         // 判断左侧窗口是否要收缩
     *         while (right - left >= t.size()) {
     *             // 在这里判断是否找到了合法的子串
     *             if (valid == need.size())
     *                 return true;
     *             char d = s[left];
     *             left++;
     *             // 进行窗口内数据的一系列更新
     *             if (need.count(d)) {
     *                 if (window[d] == need[d])
     *                     valid--;
     *                 window[d]--;
     *             }
     *         }
     *     }
     *     // 未找到符合条件的子串
     *     return false;
     * }
     *
     * 对于这道题的解法代码，基本上和最小覆盖子串一模一样，只需要改变两个地方：
     * 1、本题移动left缩小窗口的时机是窗口大小大于t.size()时，因为排列嘛，显然长度应该是一样的。
     * 2、当发现valid == need.size()时，就说明窗口中就是一个合法的排列，所以立即返回true。
     * 至于如何处理窗口的扩大和缩小，和最小覆盖子串完全相同。
     *
     *
     *
     * 三、找所有字母异位词
     * 这是 LeetCode 第 438 题，Find All Anagrams in a String，难度 Medium：
     * 给定一个字符串s和一个非空字符串p,找到s中所有是p的字母母异位词的子串,返回这些子串的起始索引。字符串只包含小写英文字母,并且字符串s和p的长度都不超过20100
     * 说明: <1>字母异位词指字母相同,但排列不同的字符串。<2>不考虑答案输出的顺序。
     *
     * 示例1:
     * 输入:
     * s: "cbaebabacd" p: "abc"
     * 输出:
     * [0,6]
     *
     * 解释:
     * 起始索引等于0的子串是"cba",它是"abc"的字母异位词。
     * 起始索引等于6的子串是"bac",它是"abc"的字母异位词。
     *
     * 呵呵，这个所谓的字母异位词，不就是排列吗，搞个高端的说法就能糊弄人了吗？
     * 相当于，输入一个串S，一个串T，找到S中所有T的排列，返回它们的起始索引。
     * 直接默写一下框架，明确刚才讲的 4 个问题，即可秒杀这道题：
     * vector<int> findAnagrams(string s, string t) {
     *     unordered_map<char, int> need, window;
     *     for (char c : t) need[c]++;
     *
     *     int left = 0, right = 0;
     *     int valid = 0;
     *     vector<int> res; // 记录结果
     *     while (right < s.size()) {
     *         char c = s[right];
     *         right++;
     *         // 进行窗口内数据的一系列更新
     *         if (need.count(c)) {
     *             window[c]++;
     *             if (window[c] == need[c])
     *                 valid++;
     *         }
     *         // 判断左侧窗口是否要收缩
     *         while (right - left >= t.size()) {
     *             // 当窗口符合条件时，把起始索引加入 res
     *             if (valid == need.size())
     *                 res.push_back(left);
     *             char d = s[left];
     *             left++;
     *             // 进行窗口内数据的一系列更新
     *             if (need.count(d)) {
     *                 if (window[d] == need[d])
     *                     valid--;
     *                 window[d]--;
     *             }
     *         }
     *     }
     *     return res;
     * }
     *
     * 跟寻找字符串的排列一样，只是找到一个合法异位词（排列）之后将起始索引加入res即可。
     *
     *
     *
     * 四、最长无重复子串
     * 这是 LeetCode 第 3 题，Longest Substring Without Repeating Characters，难度 Medium：
     * 给定一个字符串,请你找出其中不含有重复字符的最长子串的长度。
     *
     * 示例1:
     * 输入:"abcabcbb"
     * 输出:3
     * 解释:因为无重复字符的最长子串是"abc",所以其长度为3。
     *
     * 示例2:
     * 输入:"bbbbb"
     * 输出:1
     * 解释:因为无重复字符的最长子串是"b",所以其长度为1。
     *
     * 示例3:
     * 输入:"pwwkew"
     * 输出:3
     * 解释:因为无重复字符的最长子串是"wke",所以其长度为3。请注意,你的答案必须是子串的长度,"pwke"是一个子序列,不不是子串
     *
     * 这个题终于有了点新意，不是一套框架就出答案，不过反而更简单了，稍微改一改框架就行了：
     * int lengthOfLongestSubstring(string s) {
     *     unordered_map<char, int> window;
     *
     *     int left = 0, right = 0;
     *     int res = 0; // 记录结果
     *     while (right < s.size()) {
     *         char c = s[right];
     *         right++;
     *         // 进行窗口内数据的一系列更新
     *         window[c]++;
     *         // 判断左侧窗口是否要收缩
     *         while (window[c] > 1) {
     *             char d = s[left];
     *             left++;
     *             // 进行窗口内数据的一系列更新
     *             window[d]--;
     *         }
     *         // 在这里更新答案
     *         res = max(res, right - left);
     *     }
     *     return res;
     * }
     *
     * 这就是变简单了，连need和valid都不需要，而且更新窗口内数据也只需要简单的更新计数器window即可。
     * 当window[c]值大于 1 时，说明窗口中存在重复字符，不符合条件，就该移动left缩小窗口了嘛。
     * 唯一需要注意的是，在哪里更新结果res呢？我们要的是最长无重复子串，哪一个阶段可以保证窗口中的字符串是没有重复的呢？
     * 这里和之前不一样，要在收缩窗口完成后更新res，因为窗口收缩的 while 条件是存在重复元素，换句话说收缩完成后一定保证窗口中没有重复嘛。
     *
     *
     *
     * 五、最后总结
     *
     *
     */

}
