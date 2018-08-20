package me.meet.leetcode.hard;

import java.util.Arrays;

public final class SumOfSubsequenceWidths {
    private SumOfSubsequenceWidths() {
    }

    /**
     * Given an array of integers A, consider all non-empty subsequences of A.
     * For any sequence S, let the width of S be the difference between the maximum and minimum element of S.
     * Return the sum of the widths of all subsequences of A.
     * As the answer may be very large, return the answer modulo 10^9 + 7.
     *
     * Example 1:
     * Input: [2,1,3]
     * Output: 6
     * Explanation: Subsequences are [1], [2], [3], [2,1], [2,3], [1,3], [2,1,3].
     * The corresponding widths are 0, 0, 0, 1, 1, 2, 2.
     * The sum of these widths is 6.
     *
     * Note:
     * 1 <= A.length <= 20000
     * 1 <= A[i] <= 20000
     */
    /**
     * 题意：子序列宽度之和
     * 思路：
     * 这道题给了我们一个数组，并且定义了一种子序列的宽度，就是非空子序列中最大值和最小值的差值，让我们算出所有的子序列的宽度之和，而且提示了结果可能是个超大数，要对 1e9+7 取余。由于要求是子序列，所以不必像子数组那样必须要连续，并且我们只关心最大值和最小值，其他的数字并不 care。由于子序列并不存在顺序之分，所以我们可以开始就对输入数组进行排序，并不会影响最终的结果。想到这里博主的思路就断了，难道要生成所有的子序列，然后一个一个的计算差值么，这种思路对得起 Hard 标签么？但一时半会又想不出什么其他好思路，其实这道题的最优解法相当的 tricky，基本有点脑筋急转弯的感觉了。在解题之前，我们首先要知道的是一个长度为n的数组，共有多少个子序列，如果算上空集的话，共有 2^n 个。那么在给数组排序之后，对于其中任意一个数字 A[i]，其前面共有i个数是小于等于 A[i] 的，这i个数字共有 2^i 个子序列，它们加上 A[i] 都可以组成一个新的非空子序列，并且 A[i] 是这里面最大的数字，那么在宽度计算的时候，就要加上 A[i] x (2^i)，同理，A[i] 后面还有 n-1-i 个数字是大于等于它的，后面可以形成 2^(n-1-i) 个子序列，每个加上 A[i] 就都是一个新的非空子序列，同时 A[i] 是这些子序列中最小的一个，那么结果中就要减去 A[i] x (2 ^ (n-1-i))。对于每个数字都这么计算一下，就是最终要求的所有子序列的宽度之和了。可能你会怀疑虽然加上了 A[i] 前面 2^i 个子序列的最大值，那些子序列的最小值减去了么？其实是减去了的，虽然不是在遍历 A[i] 的时候减去，在遍历之前的数字时已经将所有该数字是子序列最小值的情况减去了，同理，A[i] 后面的那些 2^(n-1-i) 个子序列的最大值也是在遍历到的时候才加上的，所以不会漏掉任何一个数字。在写代码的时候有几点需要注意的地方，首先，结果 res 要定义为 long 型，因为虽然每次会对 1e9+7 取余，但是不能保证不会在取余之前就已经整型溢出，所以要定义为长整型。其次，不能直接算 2^i 和 2^(n-1-i)，很容易溢出，即便是长整型，也有可能溢出。那么解决方案就是，在累加i的同时，每次都乘以个2，那么遍历到i的时候，也就乘到 2^i 了，防止溢出的诀窍就是每次乘以2之后就立马对 1e9+7 取余，这样就避免了指数溢出，同时又不影响结果。最后，由于这种机制下的 2^i 和 2^(n-1-i) 不方便同时计算，这里又用了一个 trick，就是将 A[i] x (2^(n-1-i)) 转换为了 A[n-1-i] x 2^i，其实二者最终的累加和是相等的：
     * sum(A[i] * 2^(n-1-i)) = A[0]*2^(n-1) + A[1]*2^(n-2) + A[2]*2^(n-3) + ... + A[n-1]*2^0
     * sum(A[n-1-i] * 2^i) = A[n-1]*2^0 + A[n-2]*2^1 + ... + A[1]*2^(n-2) + A[0]*2^(n-1)
     * 可以发现两个等式的值都是相等的，只不过顺序颠倒了一下，参见代码如下：
     */
    static int sumSubseqWidths1(int[] arr) {
        int res = 0, n = arr.length, m = (int) (1e9 + 7), c = 1;
        Arrays.sort(arr);
        for (int i = 0; i < n; ++i) {
            res = (res + arr[i] * c - arr[n - i - 1] * c) % m;
            c = (c << 1) % m;
        }
        return res;
    }

    /**
     * 思路：
     * 我们也可以换一种写法，使用两个累加和 leftSum 和 rightSum，其中 leftSum[i] 表示数组范围 [0, i] 内的数字之和，rightSum[i] 表示数组范围 [i, n-1] 内的数字之和，然后每次在i位置，累加 (rightSum - leftSum) x 2^i 到结果 res，也能得到同样正确的结果，这是为啥呢？我们只要将 (rightSum - leftSum) x 2^i 展开，就能明白了：
     * sum((rightSum - leftSum) * 2^i) =
     * (A[n-1] - A[0]) * 2^0 +
     * (A[n-1] + A[n-2] - A[1] - A[0]) * 2^1 +
     * (A[n-1] + A[n-2] + A[n-3] - A[2] - A[1] - A[0]) * 2^2 +
     * ... +
     * (A[n-1] + A[n-2] - A[1] - A[0]) * 2^(n-3)
     * (A[n-1] - A[0]) * 2^(n-2)
     * =
     * A[n-1] * (2^(n-1) - 2^0) + A[n-2] * (2^(n-2) - 2^1) + ... + A[0] * (2^0 - 2^(n-1))
     * =
     * sum(A[i] * 2^i - A[i] * 2^(n-1-i))
     * 我们发现，最终还是转化成了跟解法一中一样的规律，参见代码如下：
     */
    static int sumSubseqWidths2(int[] arr) {
        int res = 0, n = arr.length, m = (int) (1e9 + 7), c = 1;
        int leftSum = 0, rightSum = 0, left = 0, right = n - 1;
        Arrays.sort(arr);
        while (left < n) {
            leftSum += arr[left++];
            rightSum += arr[right--];
            res = (res + (rightSum - leftSum) * c) % m;
            c = (c << 1) % m;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] src = new int[]{2, 1, 3};
        int res = sumSubseqWidths1(src);
        System.out.println(res);

        res = sumSubseqWidths2(src);
        System.out.println(res);
    }

}
