package me.meet.leetcode.easy;

import java.util.ArrayList;

public final class MagicSquaresInGrid {
    private MagicSquaresInGrid() {}

    /**
     * Magic Squares In Grid
     * A 3 x 3 magic square is a 3 x 3 grid filled with distinct numbers from 1 to 9 such that each row, column, and both diagonals all have the same sum.
     * Given an grid of integers, how many 3 x 3 "magic square" sub-grids are there?  (Each sub-grid is contiguous).
     *
     * Example 1:
     * Input: [[4,3,8,4],
     *         [9,5,1,9],
     *         [2,7,6,2]]
     * Output: 1
     *
     * Explanation:
     * The following sub-grid is a 3 x 3 magic square:
     * 438
     * 951
     * 276
     *
     * while this one is not:
     * 384
     * 519
     * 762
     * In total, there is only one magic square inside the given grid.
     *
     * Note:
     * 1 <= grid.length <= 10
     * 1 <= grid[0].length <= 10
     * 0 <= grid[i][j] <= 15
     */

    /**
     * 网格中的神奇正方形
     * 定义了一种神奇正方形，是一个3x3大小，且由1到9中到数字组成，各行各列即对角线和都必须相等。那么其实这个神奇正方形的各行各列及对角线之和就已经被限定了，必须是15才行，而且最中间的位置必须是5，否则根本无法组成满足要求的正方形。博主也没想出啥特别巧妙的方法，就老老实实的遍历所有的3x3大小的正方形呗，我们写一个子函数来检测各行各列及对角线的和是否为15，在调用子函数之前，先检测一下中间的数字是否为5，是的话再进入子函数。在子函数中，先验证下该正方形中的数字是否只有1到9中的数字，且不能由重复出现，使用一个一维数组来标记出现过的数字，若当前数字已经出现了，直接返回true。之后便是一次计算各行各列及对角线之和是否为15了，若全部为15，则返回true
     */

    static int numMagicSquaresInside(int[][] grid) {
        int R = grid.length, C = grid[0].length;
        int ans = 0;
        for (int r = 0; r < R-2; ++r)
            for (int c = 0; c < C-2; ++c) {
                if (grid[r+1][c+1] != 5) continue;  // optional skip 中心点必然是5
                if (magic(grid[r][c], grid[r][c+1], grid[r][c+2],
                        grid[r+1][c], grid[r+1][c+1], grid[r+1][c+2],
                        grid[r+2][c], grid[r+2][c+1], grid[r+2][c+2]))
                    ans++;
            }

        return ans;
    }

    static boolean magic(int... vals) {

        int[] count = new int[16];
        for (int v: vals) count[v]++;
        for (int v = 1; v <= 9; ++v)
            if (count[v] != 1)
                return false;

        return (vals[0] + vals[1] + vals[2] == 15 &&
                vals[3] + vals[4] + vals[5] == 15 &&
                vals[6] + vals[7] + vals[8] == 15 &&
                vals[0] + vals[3] + vals[6] == 15 &&
                vals[1] + vals[4] + vals[7] == 15 &&
                vals[2] + vals[5] + vals[8] == 15 &&
                vals[0] + vals[4] + vals[8] == 15 &&
                vals[2] + vals[4] + vals[6] == 15);
    }

    public static void main(String[] args) {
//        ArrayList array = new ArrayList();
//        array.add(1,"hello world");
//        System.out.println();

        Integer i = null;
        test(i);
    }

    static void test(int i) {

    }
}
