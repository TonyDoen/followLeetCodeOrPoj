package me.meet.labuladong._0;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public final class LC0752 {
    private LC0752() {
    }

    /**
     * 解开密码锁的最少次数
     *
     * LeetCode 第 752 题: 打开转盘锁
     * 有个4个圆盘的转盘锁，每个转盘有10个数字：0->9;每个拨轮可以自由转动，每次旋转只能转动一个拨轮的一位数字，初始'0000'，
     * 列表包含 deadends 死亡数字，一旦拨轮和列表中任何一个元素相同，锁被锁死，无法转动。
     * 字符窜target 代表可以解锁的数字，给出需要转动的最小次数，如果不能解锁，返回-1
     * 
     * 例子1
     * 输入： deadends=["0201","0101","0102","1212","2002"], target="0202"
     * 输出： 6
     * 
     * 思路：
     * 第一步,我们不管所有的限制条件,不管 deadends 和 target 的限制,就思考一个问题:如果让你设计一个算法,穷举所有可能的密码组合,你怎么做?
     * 穷举呗,再简单一点,如果你只转一下锁,有几种可能?总共有 4 个位置, 每个位置可以向上转,也可以向下转,也就是有 8 种可能对吧。
     * 比如说从 "0000" 开始,转一次,可以穷举出 "0900"... 共 8 种密码。然后,再以这 8 种密码作为基础,对每个密码再转 "1000", "9000", "0100", 一下,穷举出所有可能...
     * 
     * 仔细想想,这就可以抽象成一幅图,每个节点有 8 个相邻的节点,又让你求最短距离,
     * 这不就是典型的 BFS 嘛,框架就可以派上用场了,先写出一个 「简陋」的 BFS 框架代码再说
     * 
     * 
     * 
     * 这段 BFS 代码已经能够穷举所有可能的密码组合了,但是显然不能完成题目,有如下问题需要解决:
     * 1、会走回头路。
     * 比如说我们从 "1000" 时,还会拨出一个 "0000"
     * "0000" 拨到 "1000" ,但是等从队列拿出 ,这样的话会产生死循环。
     * 
     * 2、没有终止条件,
     * 按照题目要求,我们找到 target 就应该结束并返回拨动的次数。
     * 
     * 3、没有对 deadends 的处理,
     * 按道理这些「死亡密码」是不能出现的,也就是说你遇到这些密码的时候需要跳过。
     * 
     * 如果你能够看懂上面那段代码,真得给你鼓掌,只要按照 BFS 框架在对应的位置稍作修改即可修复这些问题:
     */
    private static String plus1(String s, int j) {
        char[] ch = s.toCharArray();
        if ('9' == ch[j]) {
            ch[j] = '0';
        } else {
            ch[j] += 1;
        }
        return new String(ch);
    }

    private static String minus1(String s, int j) {
        char[] ch = s.toCharArray();
        if ('0' == ch[j]) {
            ch[j] = '9';
        } else {
            ch[j] += -1;
        }
        return new String(ch);
    }

    static int unlock(Set<String> deadends, String target) {
        // 记录已经穷举过的密码,防止走回头路
        Set<String> visited = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        visited.add("0000");
        q.offer("0000");

        int step = 0;
        while (!q.isEmpty()) {
            int sz = q.size();
            // 将当前队列中的所有节点向周围扩散
            for (int i = 0; i < sz; i++) {
                String cur = q.poll();
                assert cur != null;

                // 判断是否到达终点
                if (deadends.contains(cur)) {
                    continue;
                }
                if (cur.equals(target)) {
                    return step;
                }

                // 将一个节点的未遍历相邻节点加入队列
                for (int j = 0; j < 4; j++) {
                    String up = plus1(cur, j);
                    if (!visited.contains(up)) {
                        visited.add(up);
                        q.offer(up);
                    }

                    String down = minus1(cur, j);
                    if (!visited.contains(down)) {
                        visited.add(down);
                        q.offer(down);
                    }
                }
            }
            // 在这里增加步数
            step++;
        }
        return -1;
    }

    private static void testUnlock0() {
        String[] deadends = new String[]{"0201", "0101", "0102", "1212", "2002"};
        Set<String> deads = new HashSet<>();
        for (String s : deadends) {
            deads.add(s);
        }

        String target = "0202";
        int result = unlock(deads, target);
        System.out.println("result: " + result);
    }

    /**
     * url: https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247485134&idx=1&sn=fd345f8a93dc4444bcc65c57bb46fc35&scene=21#wechat_redirect
     *
     * 不过,双向 BFS 也有局限,因为你必须知道终点在哪里。
     *
     * 比如
     * <1>我们刚才讨论的二叉树最小高度的问题,你一开始根本就不知道终点在哪里,也就无法使用双向 BFS;
     * <2>但是第二个密码锁的问题,是可以使用双向 BFS 算法来提高效率的,
     */
    static int unlock1(String src, String dst, Set<String> deadends) {
        if (null == src || null == dst) {
            throw new IllegalArgumentException();
        }
        if (null == deadends) {
            deadends = new HashSet<>();
        }
        int step = 0;
        Set<String> visited = new HashSet<>();
        // 开始位置
        Set<String> q0 = new LinkedHashSet<String>() {{
            add(src);
        }};
        // 结束位置
        Set<String> q1 = new LinkedHashSet<String>() {{
            add(dst);
        }};

        while (!q0.isEmpty() && !q1.isEmpty()) {
            // 在遍历的过程中不能修改，用 tmpQ 存储扩散结果(替 q0 暂存未遍历相邻节点)
            Set<String> tmpQ = new LinkedHashSet<>();
            // 双向 BFS 这里一个优化(为什么这是一个优化呢？)
            // 因为按照 BFS 的逻辑，队列（集合）中的元素越多，扩散之后新的队列（集合）中的元素就越多；
            // 在双向 BFS 算法中，如果我们每次都选择一个较小的集合进行扩散，那么占用的空间增长速度就会慢一些，效率就会高一些。

            for (String cur : q0) {
                if (null == cur) {
                    continue;
                }
                if (deadends.contains(cur)) {
                    continue;
                }
                if (q1.contains(cur)) {
                    return step;
                }
                visited.add(cur);

                // 将一个节点的未遍历相邻节点加入队列
                for (int curI = 0; curI < 4; curI++) {
                    String up = plus1(cur, curI);
                    if (!visited.contains(up)) {
                        tmpQ.add(up);
                    }
                    String down = minus1(cur, curI);
                    if (!visited.contains(down)) {
                        tmpQ.add(down);
                    }
                }
            }
            // 在这里增加步数
            step++;
            // 一个技巧点就是 while 循环的最后交换q0和q1的内容，所以只要默认扩散q0就相当于轮流扩散q0和q1。
            q0 = q1;
            q1 = tmpQ;
        }
        return -1;
    }

    private static void testUnlock1() {
        Set<String> deads = new HashSet<String>() {{
            addAll(Arrays.asList("0201", "0101", "0102", "1212", "2002"));
        }};

        String source = "0000";
        String target = "0202";
        int result = unlock1(source, target, deads);
        System.out.println("result: " + result);
    }

    public static void main(String[] args) {
        testUnlock0();
        testUnlock1();
    }
}
