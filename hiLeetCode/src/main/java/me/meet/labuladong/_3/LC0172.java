package me.meet.labuladong._3;

public class LC0172 {
    /**
     *  LeetCode 上拿下如下题目：
     *
     * 172、阶乘后的零（难度 Easy）
     * 793、阶乘后 K 个零（难度 Hard）
     *
     * 笔试题中经常看到阶乘相关的题目，今天说两个最常见的题目：
     * 1、输入一个非负整数n，请你计算阶乘n!的结果末尾有几个 0。
     * 比如说输入n = 5，算法返回 1，因为5! = 120，末尾有一个 0。
     * 函数签名如下： int trailingZeroes(int n);
     *
     * 2、输入一个非负整数K，请你计算有多少个n，满足n!的结果末尾恰好有K个 0。
     * 比如说输入K = 1，算法返回 5，因为5!,6!,7!,8!,9!这 5 个阶乘的结果最后只有一个 0，即有 5 个n满足条件。
     * 函数签名如下： int preimageSizeFZF(int K);
     * 我把这两个题放在一起，肯定是因为它们有共性，可以连环击破。下面我们来逐一分析。
     *
     *
     */
    /**
     * 题目一
     *
     * 分析：
     * 肯定不可能真去把n!的结果算出来，阶乘增长可是比指数增长都恐怖，趁早死了这条心吧。
     * 那么，结果的末尾的 0 从哪里来的？我们有没有投机取巧的方法计算出来？
     * 首先，两个数相乘结果末尾有 0，一定是因为两个数中有因子 2 和 5，因为 10 = 2 x 5。
     *
     * 也就是说，问题转化为：n!最多可以分解出多少个因子 2 和 5？
     * 比如说n = 25，那么25!最多可以分解出几个 2 和 5 相乘？
     * 这个主要取决于能分解出几个因子 5，因为每个偶数都能分解出因子 2，因子 2 肯定比因子 5 多得多。
     * 25!中 5 可以提供一个，10 可以提供一个，15 可以提供一个，20 可以提供一个，25 可以提供两个，总共有 6 个因子 5，所以25!的结果末尾就有 6 个 0。
     * 现在，问题转化为：n!最多可以分解出多少个因子 5？
     *
     *
     * 难点在于像 25，50，125 这样的数，可以提供不止一个因子 5，怎么才能不漏掉呢？
     * 这样，我们假设 n = 125，来算一算125!的结果末尾有几个 0：
     * (1) 首先，125 / 5 = 25，这一步就是计算有多少个像 5，15，20，25 这些 5 的倍数，它们一定可以提供一个因子 5。
     * (2) 但是，像 25，50，75 这些 25 的倍数，可以提供两个因子 5，那么我们再计算出125!中有 125 / 25 = 5 个 25 的倍数，它们每人可以额外再提供一个因子 5。
     * (3) 我们发现 125 = 5 x 5 x 5，像 125，250 这些 125 的倍数，可以提供 3 个因子 5，那么我们还得再计算出125!中有 125 / 125 = 1 个 125 的倍数，它还可以额外再提供一个因子 5。
     *
     * 125!最多可以分解出 25 + 5 + 1 = 26 个因子 5，也就是说阶乘结果的末尾有 31 个 0。
     * 5   = 5*1
     * 10  = 5*2
     * 15  = 5*3
     * 20  = 5*4
     * 25  = 5*5
     * 30  = 5*6
     * 35  = 5*7
     * 40  = 5*8
     * 45  = 5*9
     * 50  = 5*10
     * 55  = 5*11
     * 60  = 5*12
     * ...
     * 100 = 5*20
     * 105 = 5*21
     * 110 = 5*22
     * ...
     * 125 = 5*25
     *
     *
     */
    static int trailingZero0(int n) {
        int res = 0;
        long divisor = 5;
        while (divisor <= n) {
            res += n / divisor;
            divisor *= 5;
        }
        return res;
    }

    static int trailingZero1(int n) {
        int ret = 0;
        for (int d = n; d / 5 > 0; d = d / 5) {
            ret += (d / 5);
        }
        return ret;
    }

    private static void testTrailingZero() {
        int n = 125;
        int rs = trailingZero0(n);
        System.out.println(rs);

        rs = trailingZero1(n);
        System.out.println(rs);
    }

    /**
     * 题目二
     *
     * 分析：
     * 现在是给你一个非负整数K，问你有多少个n，使得n!结果末尾有K个 0。
     * 一个直观地暴力解法就是穷举呗，因为随着n的增加，n!肯定是递增的，trailingZeroes(n!)肯定也是递增的，伪码逻辑如下：
     *
     * int res = 0;
     * for (int n = 0; n < +inf; n++) {
     *     if (trailingZeroes(n) < K) {
     *         continue;
     *     }
     *     if (trailingZeroes(n) > K) {
     *         break;
     *     }
     *     if (trailingZeroes(n) == K) {
     *         res++;
     *     }
     * }
     * return res;
     *
     *
     * 前文 二分搜索只能用来查找元素吗？ 说过，对于这种具有单调性的函数，用 for 循环遍历，可以用二分查找进行降维打击.
     *
     * 搜索有多少个n满足trailingZeroes(n) == K，其实就是在问，
     * 满足条件的n最小是多少，最大是多少，最大值和最小值一减，就可以算出来有多少个n满足条件了.
     *
     * 那不就是二分查找「搜索左侧边界」和「搜索右侧边界」这两个事儿嘛？
     * 先不急写代码，因为二分查找需要给一个搜索区间，也就是上界和下界，上述伪码中n的下界显然是 0，但上界是+inf，这个正无穷应该如何表示出来呢？
     * 首先，数学上的正无穷肯定是无法编程表示出来的，我们一般的方法是用一个非常大的值，大到这个值一定不会被取到。
     * 比如说 int 类型的最大值INT_MAX（2^31 - 1，大约 31 亿），还不够的话就 long 类型的最大值LONG_MAX（2^63 - 1，这个值就大到离谱了）。
     *
     * 那么我怎么知道需要多大才能「一定不会被取到」呢？这就需要认真读题，看看题目给的数据范围有多大。
     * 这道题目实际上给了限制，K是在[0,10^9]区间内的整数，也就是说，trailingZeroes(n)的结果最多可能达到10^9。
     * 然后我们可以反推，当trailingZeroes(n)结果为10^9时，n为多少？
     * 这个不需要你精确计算出来，你只要找到一个数hi，使得trailingZeroes(hi)比10^9大，就可以把hi当做正无穷，作为搜索区间的上界。
     * 刚才说了，trailingZeroes函数是单调函数，那我们就可以猜，先算一下trailingZeroes(INT_MAX)的结果，比10^9小一些，
     * 那再用LONG_MAX算一下，远超10^9了，所以LONG_MAX可以作为搜索的上界。
     * 注意为了避免整型溢出的问题，trailingZeroes函数需要把所有数据类型改成 long：
     *
     * 时间复杂度主要是二分搜索，从数值上来说LONG_MAX是 2^63 - 1，大得离谱，但是二分搜索是对数级的复杂度，log(LONG_MAX) 是一个常数 63；每次二分的时候都会调用一次trailingZeroes(n)函数，它的复杂度 O(logN)。
     * 那么算法的复杂度就是 O(logN) 吗？其实你会发现 trailingZeroes 函数传入的参数 n 也是在区间[0,LONG_MAX]之内的，所以我们认为这个 O(logN) 最多也不过 63，所以说可以认为时间复杂度为 O(1)。
     * 综上，由于我们根据 K 的大小限制了数据范围，用大 O 表示法来说，整个算法的时间复杂度为 O(1)。
     *
     *
     * 果说不考虑数据范围，这个算法的时间复杂度应该是 O(logN*logN)，
     *
     */
    /* 主函数 */
    static long preimageSizeFZF(int K) {
        // 左边界和右边界之差 + 1 就是答案
        return rightBound(K) - leftBound(K) + 1;
    }

    /* 搜索 trailingZeroes(n) == K 的左侧边界 */
    private static long leftBound(int target) {
        long lo = 0, hi = Long.MAX_VALUE;
        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (trailingZeroL(mid) < target) {
                lo = mid + 1;
            } else if (trailingZeroL(mid) > target) {
                hi = mid;
            } else {
                hi = mid;
            }
        }

        return lo;
    }

    /* 搜索 trailingZeroes(n) == K 的右侧边界 */
    private static long rightBound(int target) {
        long lo = 0, hi = Long.MAX_VALUE;
        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (trailingZeroL(mid) < target) {
                lo = mid + 1;
            } else if (trailingZeroL(mid) > target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo - 1;
    }

    private static long trailingZeroL(long n) {
        int ret = 0;
        for (long d = n; d / 5 > 0; d = d / 5) {
            ret += (d / 5);
        }
        return ret;
    }

    private static void testPreimageSizeFZF() {
        int n = 25;
        for (int i = 0; i <= n; i++) {
            long ret = preimageSizeFZF(n);
            System.out.print(ret + ", ");
        }
    }

    /**
     * 其实位操作的技巧很多，有一个叫做 Bit Twiddling Hacks 的外国网站收集了几乎所有位操作的黑科技玩法，感兴趣的读者可以查看：
     * url: http://graphics.stanford.edu/~seander/bithacks.html#ReverseParallel
     *
     * 1. 利用或操作 `|` 和空格将英文字符转换为小写
     * ('a' | ' ') = 'a'
     * ('A' | ' ') = 'a'
     *
     * 2. 利用与操作 `&` 和下划线将英文字符转换为大写
     * ('b' & '_') = 'B'
     * ('B' & '_') = 'B'
     *
     * 3. 利用异或操作 `^` 和空格进行英文字符大小写互换
     * ('d' ^ ' ') = 'D'
     * ('D' ^ ' ') = 'd'
     *
     * 以上操作能够产生奇特效果的原因在于 ASCII 编码。
     * 字符其实就是数字，恰巧这些字符对应的数字通过位运算就能得到正确的结果，有兴趣的读者可以查 ASCII 码表自己算算，本文就不展开讲了。
     *
     * 4. 判断两个数是否异号
     * int x = -1, y = 2;
     * bool f = ((x ^ y) < 0); // true
     *
     * int x = 3, y = 2;
     * bool f = ((x ^ y) < 0); // false
     * 这个技巧还是很实用的，利用的是补码编码的符号位。
     * 如果不用位运算来判断是否异号，需要使用 if else 分支，还挺麻烦的。
     * 读者可能想利用乘积或者商来判断两个数是否异号，但是这种处理方式可能造成溢出，从而出现错误。
     *
     *
     * 5. 不用临时变量交换两个数
     * int a = 1, b = 2;
     * a ^= b;
     * b ^= a;
     * a ^= b;
     * // 现在 a = 2, b = 1
     *
     * 6. 加一
     * int n = 1;
     * n = -~n;
     * // 现在 n = 2
     *
     * 7. 减一
     * int n = 2;
     * n = ~-n;
     * // 现在 n = 1
     * PS：上面这三个操作就纯属装逼用的，没啥实际用处，大家了解了解乐呵一下就行。
     *
     *
     * n&(n-1) 这个操作是算法中常见的，作用是消除数字 n 的二进制表示中的最后一个 1。
     *
     * 一个数和它本身做异或运算结果为 0，即 a ^ a = 0；一个数和 0 做异或运算的结果为它本身，即 a ^ 0 = a。
     *
     */
    public static void main(String[] args) {
        testTrailingZero();
        testPreimageSizeFZF();
    }
}
