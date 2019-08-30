package me.meet.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

public final class LemonadeChange {
    private LemonadeChange() {
    }

    /**
     * At a lemonade stand, each lemonade costs $5.
     * Customers are standing in a queue to buy from you, and order one at a time (in the order specified by bills).
     * Each customer will only buy one lemonade and pay with either a $5, $10, or $20 bill.  You must provide the correct change to each customer, so that the net transaction is that the customer pays $5.
     * Note that you don't have any change in hand at first.
     * Return true if and only if you can provide every customer with correct change.
     * <p>
     * Example 1:
     * Input: [5,5,5,10,20]
     * Output: true
     * <p>
     * Explanation:
     * From the first 3 customers, we collect three $5 bills in order.
     * From the fourth customer, we collect a $10 bill and give back a $5.
     * From the fifth customer, we give a $10 bill and a $5 bill.
     * Since all customers got correct change, we output true.
     * <p>
     * Example 2:
     * Input: [5,5,10]
     * Output: true
     * <p>
     * Example 3:
     * Input: [10,10]
     * Output: false
     * <p>
     * Example 4:
     * Input: [5,5,10,10,20]
     * Output: false
     * <p>
     * Explanation:
     * From the first two customers in order, we collect two $5 bills.
     * For the next two customers in order, we collect a $10 bill and give back a $5 bill.
     * For the last customer, we can't give change of $15 back because we only have two $10 bills.
     * Since not every customer received correct change, the answer is false.
     * <p>
     * Note:
     * 0 <= bills.length <= 10000
     * bills[i] will be either 5, 10, or 20.
     */

//    static boolean lemonadeChange(int[] bills) {
//        Map<Integer,Integer> cnt = new HashMap<>();
////        unordered_map<int, int> cnt;
//        for (int bill : bills) {
//            ++cnt[bill];
//            if (cnt[5] < cnt[20] + cnt[10]) return false;
//            if (cnt[5] < 3 * cnt[20] - cnt[10]) return false;
//        }
//        return true;
//    }
    static boolean lemonadeChange2(int[] bills) {
        int five = 0, ten = 0;
        for (int bill : bills) {
            if (bill == 5) {
                ++five;
            } else if (bill == 10) {
                --five;
                ++ten;
            } else if (ten > 0) {
                --ten;
                --five;
            } else {
                five -= 3;
            }
            if (five < 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] bills = new int[]{5,5,5,10,20};
        System.out.println(lemonadeChange2(bills));
    }
}
