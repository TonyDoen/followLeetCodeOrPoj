package me.meet.leetcode.medium;

import java.util.LinkedList;
import java.util.List;

public class FindAllDuplicatesInArray {
    /**
     * Find All Duplicates in an Array
     * Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
     * Find all the elements that appear twice in this array.
     * Could you do it without extra space and in O(n) runtime?
     *
     * Example:
     * Input:
     * [4,3,2,7,8,2,3,1]
     * Output:
     * [2,3]
     *
     * 题意：找出数组中所有重复项
     * 这道题给了我们一个数组，数组中的数字可能出现一次或两次，让我们找出所有出现两次的数字，由于之前做过一道类似的题目Find the Duplicate Number，所以不是完全无从下手。这类问题的一个重要条件就是1 ≤ a[i] ≤ n (n = size of array)，不然很难在O(1)空间和O(n)时间内完成。首先来看一种正负替换的方法，这类问题的核心是就是找nums[i]和nums[nums[i] - 1]的关系，我们的做法是，对于每个nums[i]，我们将其对应的nums[nums[i] - 1]取相反数，如果其已经是负数了，说明之前存在过，我们将其加入结果res中即可，参见代码如下
     */
    static List<Integer> findDuplicates(int[] nums) {
        LinkedList<Integer> res = new LinkedList<>();
        for (int i = 0; i < nums.length; ++i) {
            int idx = Math.abs(nums[i]) - 1;
            if (nums[idx] < 0) res.push(idx + 1);
            nums[idx] = -nums[idx];
        }
        return res;
    }

    static List<Integer> findDup(int[] nums) {           // 这类问题的一个重要条件就是1 ≤ a[i] ≤ n (n = size of array)，不然很难在O(1)空间和O(n)时间内完成。
        LinkedList<Integer> res = new LinkedList<>();    // 首先来看一种正负替换的方法，这类问题的核心是就是找nums[i]和nums[nums[i] - 1]的关系，
        for (int i = 0; i < nums.length; i++) {
            int idx = Math.abs(nums[i]) - 1;
            if (nums[idx] < 0) {
                res.push(idx+1);
            }
            nums[idx] = -nums[idx];
        }
        return res;
    }

    private static void testFindDup() {
        int[] nums = new int[]{4, 3, 2, 7, 8, 2, 3, 1};
        List<Integer> res3 = findDup(nums);
        System.out.println(res3);
    }

    /**
     * 思路：
     * 下面这种方法是将nums[i]置换到其对应的位置nums[nums[i]-1]上去，比如对于没有重复项的正确的顺序应该是[1, 2, 3, 4, 5, 6, 7, 8]，而我们现在却是[4,3,2,7,8,2,3,1]，我们需要把数字移动到正确的位置上去，比如第一个4就应该和7先交换个位置，以此类推，最后得到的顺序应该是[1, 2, 3, 4, 3, 2, 7, 8]，我们最后在对应位置检验，如果nums[i]和i+1不等，那么我们将nums[i]存入结果res中即可，参见代码如下：
     */
//    static List<Integer> findDuplicates2(int[] nums) {
//        LinkedList<Integer> res = new LinkedList<>();
//        for (int i = 0; i < nums.length; ++i) {
//            if (nums[i] != nums[nums[i] - 1]) {
//                // swap(nums[i], nums[nums[i] - 1]);
//                int tmp = nums[i];
//                nums[i] = nums[nums[i] - 1];
//                nums[nums[i] - 1] = tmp;
//                --i;
//            }
//        }
//        for (int i = 0; i < nums.length; ++i) {
//            if (nums[i] != i + 1) res.push(nums[i]);
//        }
//        return res;
//    }

    /**
     * 思路：
     * 下面这种方法是在nums[nums[i]-1]位置累加数组长度n，注意nums[i]-1有可能越界，所以我们需要对n取余，最后要找出现两次的数只需要看nums[i]的值是否大于2n即可，最后遍历完nums[i]数组为[12, 19, 18, 15, 8, 2, 11, 9]，我们发现有两个数字19和18大于2n，那么就可以通过i+1来得到正确的结果2和3了，参见代码如下
     */
    static List<Integer> findDuplicates3(int[] nums) {
        LinkedList<Integer> res = new LinkedList<>();
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            nums[(nums[i] - 1) % n] += n;
        }
        for (int i = 0; i < n; ++i) {
            if (nums[i] > 2 * n) res.push(i + 1);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4, 3, 2, 7, 8, 2, 3, 1};
        List<Integer> res = findDuplicates(nums);
        System.out.println(res);

//        nums = new int[]{4, 3, 2, 7, 8, 2, 3, 1};
//        List<Integer> res2 = findDuplicates2(nums);
//        System.out.println(res2);

        nums = new int[]{4, 3, 2, 7, 8, 2, 3, 1};
        List<Integer> res3 = findDuplicates3(nums);
        System.out.println(res3);

        testFindDup();
    }
}
