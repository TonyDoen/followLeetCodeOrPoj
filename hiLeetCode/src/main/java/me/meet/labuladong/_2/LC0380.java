package me.meet.labuladong._2;

import me.meet.labuladong.common.SingleListNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public final class LC0380 {
    private LC0380() {
    }
    /*
     * 380. 常数时间插入，删除和获取随机元素
     * 难度：中等
     *
     * 设计一个支持在平均时间复杂度 O(1) , 执行一下操作的数据结构
     * 1> insert(val); 当元素 val 不存在时，向集合中插入该项
     * 2> remove(val); 当元素 val 存在时，从集合中移除该项
     * 3> getRandom(); 随机返回现有集合中一项，每个元素有相同概率被返回
     *
     * 例子：
     * // 初始化一个空集合
     * RandomizedSet randSet = new RandomizedSet();
     *
     * // 向集合中插入1。 返回 true 表示1被成功地插入。
     * randSet.insert(1);
     *
     * // 返回 false, 表示集合中不存在2
     * randSet.remove(2);
     *
     * // 向集合中插入2。 返回 true 。集合中现在包含 [1,2]
     * randSet.remove(2);
     *
     * // getRandom 应该随机返回 1, 2
     * randSet.getRandom();
     *
     * // 从集合中移除1, 返回 true 。集合现在包含 [2]
     * randSet.remove(1);
     *
     *
     *
     *
     * 思路：
     * 就是让我们实现如下一个类：
     * class RandomizedSet {
     *     // 如果 val 不存在集合中，则插入并返回 true，否则直接返回 false
     *     public boolean insert(int val) {}
     *     // 如果 val 在集合中，则删除并返回 true，否则直接返回 false
     *     public boolean remove(int val) {}
     *     // 从集合中等概率地随机获得一个元素
     *     public int getRandom() {}
     * }
     *
     * 本题的难点在于两点：
     * 1、插入，删除，获取随机元素这三个操作的时间复杂度必须都是 O(1)。
     * 2、getRandom方法返回的元素必须等概率返回随机元素，也就是说，如果集合里面有n个元素，每个元素被返回的概率必须是1/n。
     *
     *
     * 我们先来分析一下：对于插入，删除，查找这几个操作，哪种数据结构的时间复杂度是 O(1)？
     * HashSet肯定算一个对吧。哈希集合的底层原理就是一个大数组，我们把元素通过哈希函数映射到一个索引上；如果用拉链法解决哈希冲突，那么这个索引可能连着一个链表或者红黑树。
     *
     * 那么请问对于这样一个标准的HashSet，你能否在 O(1) 的时间内实现getRandom函数？
     * 其实是不能的，因为根据刚才说到的底层实现，元素是被哈希函数「分散」到整个数组里面的，更别说还有拉链法等等解决哈希冲突的机制，基本做不到 O(1) 时间等概率随机获取元素。
     *
     * 除了HashSet，还有一些类似的数据结构，比如哈希链表LinkedHashSet，我们前文 手把手实现LRU算法 和 手把手实现LFU算法 讲过这类数据结构的实现原理，本质上就是哈希表配合双链表，元素存储在双链表中。
     * 但是，LinkedHashSet只是给HashSet增加了有序性，依然无法按要求实现我们的getRandom函数，因为底层用链表结构存储元素的话，是无法在 O(1) 的时间内访问某一个元素的。
     *
     * 根据上面的分析，对于getRandom方法，如果想「等概率」且「在 O(1) 的时间」取出元素，
     * 一定要满足：底层用数组实现，且数组必须是紧凑的。
     * 这样我们就可以直接生成随机数作为索引，从数组中取出该随机索引对应的元素，作为随机元素。
     * 但如果用数组存储元素的话，插入，删除的时间复杂度怎么可能是 O(1) 呢？
     * 可以做到！对数组尾部进行插入和删除操作不会涉及数据搬移，时间复杂度是 O(1)。
     * 所以，如果我们想在 O(1) 的时间删除数组中的某一个元素val，可以先把这个元素交换到数组的尾部，然后再pop掉。
     *
     * 交换两个元素必须通过索引进行交换对吧，那么我们需要一个哈希表valToIndex来记录每个元素值对应的索引。
     * 有了思路铺垫，我们直接看代码：
     *
     * 注意remove(val)函数，对nums进行插入、删除、交换时，都要记得修改哈希表valToIndex，否则会出现错误。
     * 至此，这道题就解决了，每个操作的复杂度都是 O(1)，且随机抽取的元素概率是相等的。
     */
    public static class RandomizedSet {
        // 存储元素的值
        private final ArrayList<Integer> nums = new ArrayList<>();
        // 记录每个元素对应在 nums 中的索引
        private final HashMap<Integer, Integer> valToIndex = new HashMap<>();

        public boolean insert(int val) {
            // 若 val 已存在，不用再插入
            if (valToIndex.containsValue(val)) {
                return false;
            }
            // 若 val 不存在，插入到 nums 尾部，
            // 并记录 val 对应的索引值
            valToIndex.put(val, nums.size());
            nums.add(val);
            return true;
        }

        public boolean remove(int val) {
            // 若 val 不存在，不用再删除
            if (!valToIndex.containsValue(val)) {
                return false;
            }
            // 先拿到 val 的索引
            int index = valToIndex.get(val);
            // 将最后一个元素对应的索引修改为 index
            int lastIdx = nums.size() - 1;
            int lastVal = nums.get(lastIdx);
            valToIndex.put(lastVal, index);
            // 交换 val 和最后一个元素
            nums.set(index, lastVal);
            // 在数组中删除元素 val
            nums.remove(lastIdx);
            // 删除元素 val 对应的索引
            valToIndex.remove(val);
            return true;
        }

        private final Random rd = new Random();

        public int getRandom() {
            // 随机获取 nums 中的一个元素
            return nums.get(rd.nextInt(nums.size()));
        }
    }

    private static void testRandomizedSet() {
        RandomizedSet rst = new RandomizedSet();
        rst.insert(0);
        rst.insert(1);
        rst.insert(2);
        rst.insert(3);

        rst.remove(2);
        System.out.println(rst.getRandom());
    }

    /*
     * 避开黑名单的随机数
     * 有了上面一道题的铺垫，我们来看一道更难一些的题目，力扣第 710 题，我来描述一下题目：
     *
     * 给你输入一个正整数N，代表左闭右开区间[0,N)，再给你输入一个数组blacklist，其中包含一些「黑名单数字」，且blacklist中的数字都是区间[0,N)中的数字。
     * 现在要求你设计如下数据结构：
     * class Solution {
     * public:
     *     // 构造函数，输入参数
     *     Solution(int N, vector<int>& blacklist) {}
     *
     *     // 在区间 [0,N) 中等概率随机选取一个元素并返回
     *     // 这个元素不能是 blacklist 中的元素
     *     int pick() {}
     * };
     *
     * pick函数会被多次调用，每次调用都要在区间[0,N)中「等概率随机」返回一个「不在blacklist中」的整数。
     * 这应该不难理解吧，比如给你输入N = 5, blacklist = [1,3]，那么多次调用pick函数，会等概率随机返回 0, 2, 4 中的某一个数字。
     *
     * 而且题目要求，在pick函数中应该尽可能少调用随机数生成函数rand()。
     * 这句话什么意思呢，比如说我们可能想出如下拍脑袋的解法：
     * int pick() {
     *     int res = rand() % N;
     *     while (res exists in blacklist) {
     *         // 重新随机一个结果
     *         res = rand() % N;
     *     }
     *     return res;
     * }
     *
     * 这个函数会多次调用rand()函数，执行效率竟然和随机数相关，不是一个漂亮的解法。
     * 聪明的解法类似上一道题，我们可以将区间[0,N)看做一个数组，然后将blacklist中的元素移到数组的最末尾，同时用一个哈希表进行映射：
     * class Solution {
     * public:
     *     int sz;
     *     unordered_map<int, int> mapping;
     *
     *     Solution(int N, vector<int>& blacklist) {
     *         // 最终数组中的元素个数
     *         sz = N - blacklist.size();
     *         // 最后一个元素的索引
     *         int last = N - 1;
     *         // 将黑名单中的索引换到最后去
     *         for (int b : blacklist) {
     *             mapping[b] = last;
     *             last--;
     *         }
     *     }
     * };
     *
     * eg:
     * n = 5, blackList = [1, 0]
     *               sz = n - blackList.length = 5 - 2 = 3
     * value: 0, 1, 2, 3, 4
     * index: 0, 1, 2, 3, 4
     * mp   : 3, 4,
     * 如上，相当于把黑名单中的数字都交换到了区间[sz, N)中，同时把[0, sz)中的黑名单数字映射到了正常数字。
     *
     * 这个pick函数已经没有问题了，但是构造函数还有两个问题。
     *
     * 我们将黑名单中的b映射到last，但是我们能确定last不在blacklist中吗？
     * 比如下图这种情况，我们的预期应该是 1 映射到 3，但是错误地映射到 4：
     * 在对mapping[b]赋值时，要保证last一定不在blacklist中，可以如下操作：
     *
     * 第二个问题，如果blacklist中的黑名单数字本身就存在区间[sz, N)中，那么就没必要在mapping中建立映射，比如这种情况：
     * 我们根本不用管 4，只希望把 1 映射到 3，但是按照blacklist的顺序，会把 4 映射到 3，显然是错误的。
     *
     *
     *
     *
     *
     * 至此，这道题也解决了，总结一下本文的核心思想：
     *
     * 1、如果想高效地，等概率地随机获取元素，就要使用数组作为底层容器。
     * 2、如果要保持数组元素的紧凑性，可以把待删除元素换到最后，然后pop掉末尾的元素，这样时间复杂度就是 O(1) 了。当然，我们需要额外的哈希表记录值到索引的映射。
     * 3、对于第二题，数组中含有「空洞」（黑名单数字），也可以利用哈希表巧妙处理映射关系，让数组在逻辑上是紧凑的，方便随机取元素。
     */
    public static class PickExceptBlackList {
        private final int sz;
        private final HashMap<Integer, Integer> mp;

        public PickExceptBlackList(int n, int[] blackList) {
            mp = new HashMap<>();
            sz = n - blackList.length;

            for (int b : blackList) {
                mp.put(b, Integer.MAX_VALUE);
            }

            int last = n - 1;
            for (int b : blackList) {
                // 如果 b 已经在区间 [sz, N)
                // 可以直接忽略
                if (b >= sz) {
                    continue;
                }
                while (mp.containsKey(last)) {
                    last--;
                }
                mp.put(b, last);
                last--;
            }
        }


        private final Random rd = new Random();

        public int pick() {
            // 随机选取一个索引
            int index = rd.nextInt(sz);
            // 这个索引命中了黑名单，
            // 需要被映射到其他位置
            if (mp.containsKey(index)) { // containsKey then return mp.get()...不高效，仅仅做算法演示用
                return mp.get(index);
            }
            // 若没命中黑名单，则直接返回
            return index;
        }
    }

    private static void testPickExceptBlackList() {
        int n = 50;
        int[] blackList = new int[]{0, 4, 8, 12, 15, 48};

        PickExceptBlackList pebl = new PickExceptBlackList(n, blackList);
        int rs = pebl.pick();
        System.out.println(rs);
    }

    /*
     * 我最近在 LeetCode 上做到两道非常有意思的题目，382 和 398 题，
     * 关于水塘抽样算法（Reservoir Sampling），本质上是一种随机概率算法，解法应该说会者不难，难者不会。
     *
     * 我第一次见到这个算法问题是谷歌的一道算法题：给你一个未知长度的链表，请你设计一个算法，只能遍历一次，随机地返回链表中的一个节点。
     * 这里说的随机是均匀随机（uniform random），也就是说，如果有n个元素，每个元素被选中的概率都是1/n，不可以有统计意义上的偏差。
     * 一般的想法就是，我先遍历一遍链表，得到链表的总长度n，再生成一个[1,n]之间的随机数为索引，然后找到索引对应的节点，不就是一个随机的节点了吗？
     * 但题目说了，只能遍历一次，意味着这种思路不可行。题目还可以再泛化，给一个未知长度的序列，如何在其中随机地选择k个元素？
     *
     * 想要解决这个问题，就需要著名的水塘抽样算法了。
     *
     *
     *
     *
     * 算法实现
     * 先解决只抽取一个元素的问题，这个问题的难点在于，随机选择是「动态」的，比如说你现在你有 5 个元素，你已经随机选取了其中的某个元素a作为结果，但是现在再给你一个新元素b，你应该留着a还是将b作为结果呢，以什么逻辑选择a和b呢，怎么证明你的选择方法在概率上是公平的呢？
     *
     * 先说结论，当你遇到第i个元素时，应该有1/i的概率选择该元素，1 - 1/i的概率保持原有的选择。
     * // 返回链表中一个随机节点的值
     * int getRandom(ListNode head) {
     *     Random r = new Random();
     *     int i = 0, res = 0;
     *     ListNode p = head;
     *     // while 循环遍历链表
     *     while (p != null) {
     *         // 生成一个 [0, i) 之间的整数
     *         // 这个整数等于 0 的概率就是 1/i
     *         if (r.nextInt(++i) == 0) {
     *             res = p.val;
     *         }
     *         p = p.next;
     *     }
     *     return res;
     * }
     *
     * 对于概率算法，代码往往都是很浅显的，这种问题的关键在于证明，你的算法为什么是对的？为什么每次以1/i的概率更新结果就可以保证结果是平均随机（uniform random）？
     *
     * 证明：假设总共有n个元素，我们要的随机性无非就是每个元素被选择的概率都是1/n对吧，那么对于第i个元素，它被选择的概率就是：
     *   1/i * (1-1/(i+1)) * (1-1/(i+2)) *...* (1-1/n)
     * = 1/i * (i)/(i+1) * (i+1)/(i+2) *...* (n-1)/n
     * = 1/n
     *
     * 第i个元素被选择的概率是1/i，第i+1次不被替换的概率是1 - 1/(i+1)，以此类推，相乘就是第i个元素最终被选中的概率，就是1/n。
     *
     * 因为虽然每次更新选择的概率增大了k倍，但是选到具体第i个元素的概率还是要乘1/k，也就回到了上一个推导。
     *
     *
     * 拓展延伸
     * 以上的抽样算法时间复杂度是 O(n)，但不是最优的方法，
     * 更优化的算法基于几何分布（geometric distribution），时间复杂度为 O(k + klog(n/k))。
     *
     * 由于涉及的数学知识比较多，这里就不列出了，有兴趣的读者可以自行搜索一下。
     *
     *
     * 还有一种思路是基于 Fisher–Yates 洗牌算法 的。随机抽取k个元素，等价于对所有元素洗牌，然后选取前k个。
     * 只不过，洗牌算法需要对元素的随机访问，所以只能对数组这类支持随机存储的数据结构有效。
     *
     *
     * 另外有一种思路也比较有启发意义：给每一个元素关联一个随机数，然后把每个元素插入一个容量为k的二叉堆（优先级队列）按照配对的随机数进行排序，最后剩下的k个元素也是随机的。
     *
     * 这个方案看起来似乎有点多此一举，因为插入二叉堆需要 O(logk) 的时间复杂度，所以整个抽样算法就需要 O(nlogk) 的复杂度，还不如我们最开始的算法。
     * 但是，这种思路可以指导我们解决加权随机抽样算法，权重越高，被随机选中的概率相应增大，这种情况在现实生活中是很常见的，比如你不往游戏里充钱，就永远抽不到皮肤。
     *
     *
     *
     *
     * 最后我想说，随机算法虽然不多，但其实很有技巧的，读者不妨思考两个常见且看起来很简单的问题：
     * 1、如何对带有权重的样本进行加权随机抽取？比如给你一个数组w，每个元素w[i]代表权重，请你写一个算法，按照权重随机抽取索引。比如w = [1,99]，算法抽到索引 0 的概率是 1%，抽到索引 1 的概率是 99%。
     * 2、实现一个生成器类，构造函数传入一个很长的数组，请你实现randomGet方法，每次调用随机返回数组中的一个元素，多次调用不能重复返回相同索引的元素。要求不能对该数组进行任何形式的修改，且操作的时间复杂度是 O(1)。
     *
     * 这两个问题都是比较困难的，大家感兴趣的话我会写一写相关的文章。如果本文对你有帮助，希望在看分享支持一下。
     *
     */

    // 382. 返回链表中一个随机节点的值
    public static int getRandom(SingleListNode head) {
        Random r = new Random();
        int i = 0, res = 0;
        SingleListNode p = head;
        // while 循环遍历链表
        while (p != null) {
            // 生成一个 [0, i) 之间的整数
            // 这个整数等于 0 的概率就是 1/i
            if (r.nextInt(++i) == 0) {
                res = p.val;
            }
            p = p.next;
        }
        return res;
    }

    // 398. 返回链表中 k 个随机节点的值
    public static int[] getRandom(SingleListNode head, int k) {
        Random r = new Random();
        int[] res = new int[k];
        SingleListNode p = head;

        // 前 k 个元素先默认选上
        for (int j = 0; j < k && p != null; j++) {
            res[j] = p.val;
            p = p.next;
        }

        int i = k;
        // while 循环遍历链表
        while (p != null) {
            // 生成一个 [0, i) 之间的整数
            int j = r.nextInt(++i);
            // 这个整数小于 k 的概率就是 k/i
            if (j < k) {
                res[j] = p.val;
            }
            p = p.next;
        }
        return res;
    }

    private static void testGetRandom() {
        SingleListNode hd = SingleListNode.prepareSingleListNode0();
        int rs0 = getRandom(hd);
        System.out.println(rs0);

        int k = 3;
        int[] rs1 = getRandom(hd, k);
        for (int i : rs1) {
            System.out.print(i + ", ");
        }
        System.out.println();
    }

    /*
     * 如何高效对有序数组/链表去重？
     *
     * 我们知道对于数组来说，在尾部插入、删除元素是比较高效的，时间复杂度是 O(1)，但是如果在中间或者开头插入、删除元素，就会涉及数据的搬移，时间复杂度为 O(N)，效率较低。
     * 所以对于一般处理数组的算法问题，我们要尽可能只对数组尾部的元素进行操作，以避免额外的时间复杂度。
     *
     *
     * 这篇文章讲讲如何对一个有序数组去重，先看下题目：
     * 给定排序数组，需要在原地删除重复元素，使每个元素只出现一次，返回移除后数组的新长度。
     * 不使用额外的数组空间，必须在原地修改输入数组并在使用O(1)额外空间的条件下完成。
     *
     * 例子1：
     * 给定数组 nums = [1,1,2]
     * 函数应该返回新的长度 2，并且原数组 nums 前2个元素修改为 [1,2]
     * 不需要考虑数组中超出新长度后面的元素
     *
     * 例子2：
     * 给定数组 nums = [0,0,1,1,1,2,2,2,3,3,4]
     * 函数应该返回新的长度 5，并且原数组 nums 前2个元素修改为 [0,1,2,3,4]
     * 不需要考虑数组中超出新长度后面的元素
     *
     *
     * 显然，由于数组已经排序，所以重复的元素一定连在一起，找出它们并不难，但如果毎找到一个重复元素就立即删除它，就是在数组中间进行删除操作，整个时间复杂度是会达到 O(N^2)。
     * 而且题目要求我们原地修改，也就是说不能用辅助数组，空间复杂度得是 O(1)。
     * 其实，对于数组相关的算法问题，有一个通用的技巧：要尽量避免在中间删除元素，那我就先想办法把这个元素换到最后去。
     *
     * 这样的话，最终待删除的元素都拖在数组尾部，一个一个 pop 掉就行了，每次操作的时间复杂度也就降低到 O(1) 了。
     * 按照这个思路呢，又可以衍生出解决类似需求的通用方式：双指针技巧。具体一点说，应该是快慢指针。
     *
     * 我们让慢指针slow走左后面，快指针fast走在前面探路，找到一个不重复的元素就告诉slow并让slow前进一步。
     * 这样当fast指针遍历完整个数组nums后，nums[0..slow]就是不重复元素，之后的所有元素都是重复元素。
     *
     * nums :  [0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 4]
     * index:  slow   fast
     */
    public static int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (0 == n) {
            return 0;
        }

        int slow = 0, fast = 1;
        while (fast < n) {
            if (nums[fast] != nums[slow]) {
                slow++;
                // 维护 nums[0...slow] 无重复
                nums[slow] = nums[fast];
            }
            fast++;
        }
        // 长度为索引+1
        return slow + 1;
    }

    /**
     * 再简单扩展一下，如果给你一个有序链表，如何去重呢？
     * 其实和数组是一模一样的，唯一的区别是把数组赋值操作变成操作指针而已：
     *
     *
     * 最后，近期准备写写一些简单实用的数组/链表技巧。那些稍困难的技巧（比如动态规划）虽然秀，但毕竟在现实生活中不容易遇到。
     * 恰恰是一些简单常用的技巧，能够在不经意间，让人发现你是个高手 ^_^。
     */
    public static SingleListNode removeDuplicates(SingleListNode head) {
        if (null == head) {
            return null;
        }
        SingleListNode slow = head, fast = head.next;
        while (null != fast) {
            if (fast.val != slow.val) {
                slow.next = fast;
                slow = slow.next;
            }
            fast = fast.next;
        }
        // 断开与后面重复元素的链接
        slow.next = null;
        return head;
    }

    private static void testRemoveDuplicates() {
        int[] nums = new int[]{0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 4};
        int rs0 = removeDuplicates(nums);
        System.out.println(rs0);

        SingleListNode hd = SingleListNode.prepareSingleListNode0();
        SingleListNode rs1 = removeDuplicates(hd);
        rs1.print();
    }

    public static void main(String[] args) {
        // 380. 常数时间插入，删除和获取随机元素
        testRandomizedSet();
        // 710. 避开黑名单的随机数
        testPickExceptBlackList();
        // 382. 返回链表中一个随机节点的值
        // 398. 返回链表中 k 个随机节点的值
        testGetRandom();
        // 如何高效对有序数组/链表去重？
        testRemoveDuplicates();
    }
}
