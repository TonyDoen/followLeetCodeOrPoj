package me.meet.leetcode.medium;

import java.util.Arrays;

public final class Boats2SavePeople {
    private Boats2SavePeople() {}
    /**
     * The i-th person has weight people[i], and each boat can carry a maximum weight of limit.
     * Each boat carries at most 2 people at the same time, provided the sum of the weight of those people is at most limit.
     * Return the minimum number of boats to carry every given person.  (It is guaranteed each person can be carried by a boat.)
     *
     * Example 1:
     * Input: people = [1,2], limit = 3
     * Output: 1
     * Explanation: 1 boat (1, 2)
     *
     * Example 2:
     * Input: people = [3,2,2,1], limit = 3
     * Output: 3
     * Explanation: 3 boats (1, 2), (2) and (3)
     *
     * Example 3:
     * Input: people = [3,5,3,4], limit = 5
     * Output: 4
     * Explanation: 4 boats (3), (3), (4), (5)
     *
     * Note:
     * 1 <= people.length <= 50000
     * 1 <= people[i] <= limit <= 30000
     */
    /**
     * 题意：渡人的船
     * 这道题让我们载人过河，说是每个人的体重不同，每条船承重有个限度 limit（限定了这个载重大于等于最重人的体重），同时要求每条船不能超过两人，这尼玛是独木舟吧，也就比 kayak 大一点点吧（不过也有可能是公园湖中的双人脚蹬船，怀念小时候在公园划船的日子～），问我们将所有人载到对岸最少需要多少条船。
     * 从题目中的例子2可以看出，最肥的人有可能一人占一条船，当然如果船的载量够大的话，可能还能挤上一个瘦子，那么最瘦的人是最可能挤上去的，所以策略就是胖子加瘦子的上船组合。那么这就是典型的贪婪算法的适用场景啊，
     * 首先要给所有人按体重排个序，从瘦子到胖子，这样我们才能快速的知道当前最重和最轻的人。然后使用双指针，left 指向最瘦的人，right 指向最胖的人，当 left 小于等于 right 的时候，进行 while 循环。在循环中，胖子是一定要上船的，所以 right 自减1是肯定有的，但是还是要看能否再带上一个瘦子，能的话 left 自增1。
     * 然后结果 res 一定要自增1，因为每次都要用一条船，参见代码如下：
     *
     */
    static int numRescueBoats(int[] people, int limit) {
        int res = 0, n = people.length, left = 0, right = n - 1;    // 然后使用双指针，left 指向最瘦的人，right 指向最胖的人，
        Arrays.sort(people);                                        // 1. 首先要给所有人按体重排个序，从瘦子到胖子，这样我们才能快速的知道当前最重和最轻的人。
        while (left <= right) {
            if (people[left] + people[right] <= limit) ++left;      // 2.1. left 指向最瘦的人，看能否再带上一个瘦子，能的话 left 自增1
            --right;                                                // 2.2. right 指向最胖的人，胖子是一定要上船的，所以 right 自减1
            ++res;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] people = new int[]{1, 2};
        int limit = 3;
        int res = numRescueBoats(people, limit);
        System.out.println(res);
    }
}
