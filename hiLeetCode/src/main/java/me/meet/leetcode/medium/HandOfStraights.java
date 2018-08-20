package me.meet.leetcode.medium;

import java.util.HashMap;
import java.util.Map;

public final class HandOfStraights {
    private HandOfStraights() {
    }
    /**
     * Alice has a hand of cards, given as an array of integers.
     * Now she wants to rearrange the cards into groups so that each group is size W, and consists of W consecutive cards.
     * Return true if and only if she can.
     *
     * Example 1:
     * Input: hand = [1,2,3,6,2,3,4,7,8], W = 3
     * Output: true
     *
     * Explanation:
     * Alice's hand can be rearranged as [1,2,3],[2,3,4],[6,7,8]
     *
     * Example 2:
     * Input: hand = [1,2,3,4,5], W = 4
     * Output: false
     *
     * Explanation:
     * Alice's hand can't be rearranged into groups of 4
     *
     * Note:
     * 1 <= hand.length <= 10000
     * 0 <= hand[i] <= 10^9
     * 1 <= W <= hand.length
     */


    /**
     * 一手顺子牌
     * 这里给了我们一个W，规定了顺子的最小长度，那么我们就拿例子1来模拟下打牌吧，首先摸到了牌之后，肯定要先整牌，按从小到大的顺序排列，这里我们就不考虑啥3最大，4最小啥的，就统一按原始数字排列吧：1 2 2 3 3 4 6 7 8
     * 下面要来组顺子，既然这里是3张可连，那么我们从最小的开始连呗。其实这道题还是简化了许多，真正打牌的时候，即便是3张起连，那么连4张5张都是可以的，可以这里限定了只能连W张，就使得题目变简单了。我们用贪婪算法就可以了，首先从1开始，那么一定得有2和3，才能起连，若少了任何一个，都可以直接返回false，好那么取出这三张后
     */
    static boolean isStraightHand(int[] hand, int w) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int start = hand[0];
        for (int i : hand) {
            Integer cnt = map.get(i);
            if (null == cnt) {
                cnt = 1;
            } else {
                cnt += 1;
            }
            map.put(i, cnt);

            if (start > i) {
                start = i;
            }
        }
        while (!map.isEmpty()) {
            for (int i = 0; i < w; i++) {
                int want = start + i;
                Integer v = map.get(want);
                if (null == v || v <= 0) {
                    return false;
                }
                if (--v == 0) {
                    map.remove(want);
                } else {
                    map.put(want, v);
                }
            }
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                start = entry.getKey(); // 取出当前最小的Key
                break;
            }
        }
        return true;
    }

    /**
     * 我们也可以不对TreeMap进行删除操作，而是直接修改其映射值，在建立好映射对儿之后，不用while循环，而是遍历所有的映射对儿，若某个映射值为0了，直接跳过。然后还是遍历能组成顺子的W张牌，若某张牌出现的次数小于顺子其实位置的牌的个数，则直接返回false，因为肯定会有起始牌剩余，无法全组成顺子，这样还避免了上面解法中一张一张减的操作，提高了运算效率。然后我们的映射值减去起始牌的个数，最后for循环退出后，返回true，
     */
//    static boolean isStraightHand2(int[] hand, int w) {
//        HashMap<Integer, Integer> map = new HashMap<>(hand.length);
//        int start = hand[0];
//        for (int i : hand) {
//            Integer cnt = map.computeIfAbsent(i, k -> 0);
//            map.put(i, ++cnt);
//
//            if (start > i) {
//                start = i;
//            }
//        }
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            Integer k = entry.getKey();
//            Integer v = entry.getValue();
//            if (null == v || v <= 0) {
//                continue;
//            }
//            for (int i = 0; i < w; i++) {
//                int want = k + i;
//                Integer cnt = map.computeIfAbsent(want, o -> 0);
//                Integer afterCnt = map.computeIfAbsent(want+1, o -> 0);
//                if (cnt > afterCnt) { // error
//                    return false;
//                }
//                map.put(want, --cnt);
//            }
//        }
//        return true;
//    }


    public static void main(String[] args) {
        int[] hand = new int[]{1, 2, 3, 6, 2, 3, 4, 7, 8};
        int w = 3;
        System.out.println(isStraightHand(hand, w));
//        System.out.println(isStraightHand2(hand, w));
    }
}
