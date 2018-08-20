package me.meet.leetcode.easy;

public final class SortArrayByParity {
    private SortArrayByParity() {
    }

    /**
     *
     * Given an array A of non-negative integers, return an array consisting of all the even elements of A, followed by all the odd elements of A.
     * You may return any answer array that satisfies this condition.
     *
     * Example 1:
     * Input: [3,1,2,4]
     * Output: [2,4,3,1]
     * The outputs [4,2,3,1], [2,4,1,3], and [4,2,1,3] would also be accepted.
     *
     * Note:
     * 1 <= A.length <= 5000
     * 0 <= A[i] <= 5000
     */

    /**
     * 题意：按奇偶排序数组
     * 这道题让我们给数组重新排序，使得偶数都排在奇数前面，
     * <p>
     * 思路：
     * 并不难。最直接的做法就是分别把偶数和奇数分别放到两个数组中，然后把奇数数组放在偶数数组之后，将拼接成的新数组直接返回即可
     */
    static int[] sortArrayByParity1(int[] A) {
        int length = A.length;
        int[] even = new int[length];
        int[] odd = new int[length / 2 + 1];
        int ei = 0, oi = 0;
        for (int num : A) {
            if (num % 2 == 0) {
                even[ei++] = num;
            } else {
                odd[oi++] = num;
            }
        }
        if (length - ei >= 0) System.arraycopy(odd, 0, even, ei, length - ei);
        return even;
    }

    /**
     * 思路：
     * 我们也可以优化空间复杂度，不新建额外的数组，而是采用直接交换数字的位置，使用两个指针i和j，初始化均为0。
     * 然后j往后遍历，若遇到了偶数，则将 A[j] 和 A[i] 交换位置，同时i自增1，这样操作下来，同样可以将所有的偶数都放在奇数前面，参见代码如下：
     */
    static int[] sortArrayByParity2(int[] A) {
        for (int i = 0, j = 0; j < A.length; ++j) {
            if (A[j] % 2 == 0) {
                //swap(A[i++], A[j]);
                int tmp = A[i];
                A[i] = A[j];
                A[j] = tmp;
                i++;
            }
        }
        return A;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 1, 2, 4};
//        int[] res = sortArrayByParity1(arr);
        int[] res = sortArrayByParity2(arr);

        for (int i : res) {
            System.out.print(i);
        }
    }
}
