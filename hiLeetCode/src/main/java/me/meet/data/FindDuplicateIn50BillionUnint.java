package me.meet.data;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class FindDuplicateIn50BillionUnint {
    /**
     * 题意: 现在有五十亿个int类型的正整数，要从中找出重复的数并返回。
     * 思路: 判断50亿个数有哪些是重复和刚才上面那个判断是否存在，其实是一样的。
     * 我们采用bitmap算法来做。不过这里50亿个数，别人肯定是以文件流的形式给你的。这样我们为了方便，我们就假设这些数是以存在int型数组的形式给我们的。
     */
    static Set<Integer> findIn50Billion(int[] arr) {
        Set<Integer> result = new HashSet<>();
        BitSet bSet = new BitSet(Integer.MAX_VALUE);
        for (int val : arr) {
            if (bSet.get(val)) {
                result.add(val);
            } else {
                bSet.set(val, true);
            }
        }
        return result;
    }

    private static void testFindIn50Billion() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 3, 4};
        Set<Integer> result = findIn50Billion(arr);
        System.out.println(result);
    }

    /**
     * 题意: 一个整型数组里除了两个数字之外，其他的数字都出现了偶数次。如何快速找出数组中只出现一次的两个数
     * 思路:
     * 1、对于出现两次的元素，使用“异或”操作后结果肯定为0，那么我们就可以遍历一遍数组，对所有元素使用异或操作，那么得到的结果就是两个出现一次的元素的异或结果。
     * 2、因为这两个元素不相等，所以异或的结果中肯定有一位是1，找到为1的那一位在什么位置。
     * 3、根据那一位的位置将数组分为2组，一组该位都是0，一组都是1，每组分别异或就是最后的结果。
     * 4、再次遍历原数组，将每个元素右移到该位置与1相与，得出最后的结果
     */
    static int[] find2NumberInDuplicate(int[] arr) {
        int result = 0;
        // 1、对于出现两次的元素，使用“异或”操作后结果肯定为0，那么我们就可以遍历一遍数组，对所有元素使用异或操作，那么得到的结果就是两个出现一次的元素的异或结果。
        for (int value : arr) {
            result = result ^ value;
        }
        // 2、因为这两个元素不相等，所以异或的结果中肯定有一位是1，找到为1的那一位在什么位置。
        int index = 0;
        for (int i = 0; i <= 32; i++) {
            //将两个不同数异或的结果result右移N位，如果与1相与结果为1，就证明该N位是1
            if ((result >> i & 1) == 1) {
                index = i;
                break;
            }
        }
        // 3、根据那一位的位置将数组分为2组，一组该位都是0，一组都是1，每组分别异或就是最后的结果。
        int[] result2 = new int[2];
        for (int value : arr) {
            //将原数组右移上面的N位与1相与分成两组，得出结果
            if ((value >> index & 1) == 0) {
                result2[0] = result2[0] ^ value;
            } else {
                result2[1] = result2[1] ^ value;
            }
        }
        return result2;
    }

    private static void testFind2NumberInDuplicate() {
        int[] arr = {1, 2, 2, 4, 4, 6, 6, 8, 8, 5, 5, 7};
        int[] res = find2NumberInDuplicate(arr);
        for (int val : res) {
            System.out.println(val);
        }
    }

    /**
     * 题意: 一个整型数组里除了一个数字之外，其他的数字都出现了偶数次。如何快速找出数组中只出现一次的两个数
     * 思路:
     * 1、对于出现两次的元素，使用“异或”操作后结果肯定为0，那么我们就可以遍历一遍数组，对所有元素使用异或操作，那么得到的结果就是两个出现一次的元素的异或结果。
     */
    static int find1NumberInDuplicate(int[] arr) {
        if (arr.length % 2 == 0) {
            throw new IllegalArgumentException();
        }
        int result = 0;
        // 1、对于出现两次的元素，使用“异或”操作后结果肯定为0，那么我们就可以遍历一遍数组，对所有元素使用异或操作，那么得到的结果就是两个出现一次的元素的异或结果。
        for (int value : arr) {
            result = result ^ value;
        }
        return result;
    }

    private static void testFind1NumberInDuplicate() {
        int[] arr = {1, 2, 2, 4, 4, 6, 6, 8, 8, 5, 5, 3};
        int res = find1NumberInDuplicate(arr);
        System.out.println(res);
    }

    public static void main(String[] args) {
//        testFindIn50Billion();
//        testFind2NumberInDuplicate();
        testFind1NumberInDuplicate();
    }
}
