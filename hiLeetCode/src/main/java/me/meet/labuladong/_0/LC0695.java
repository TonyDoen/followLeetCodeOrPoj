package me.meet.labuladong._0;

public class LC0695 {
    private LC0695() {
    }

    /**
     * 岛屿的最大面积
     * 这是力扣第 695 题「岛屿的最大面积」，0表示海水，1表示陆地，现在不让你计算岛屿的个数了，而是让你计算最大的那个岛屿的面积，函数签名如下：
     * int maxAreaOfIsland(int[][] grid)
     *
     * 这题的大体思路和之前完全一样，只不过dfs函数淹没岛屿的同时，还应该想办法记录这个岛屿的面积。
     *
     * 1 1 1 1 1 1 1 0
     * 1 0 0 0 0 1 1 0
     * 1 0 1 0 1 1 1 0
     * 1 0 0 0 0 1 0 1
     * 1 1 1 1 1 1 1 0
     *
     * 我们可以给dfs函数设置返回值，记录每次淹没的陆地的个数，直接看解法吧：
     *
     */
    // 主函数：求封闭岛屿的面积总和
    static int maxAreaOfIsland0(int[][] grid) {
        // 记录岛屿的最大面积
        int res = 0;
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 淹没岛屿，并更新最大岛屿面积
                    res = Math.max(res, dfs(grid, i, j));
                }
            }
        }
        return res;
    }

    private static final int[][] dirs = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};

    private static int dfs(int[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            // 超出索引边界
            return 0;
        }
        if (0 == grid[i][j]) {
            // 已经是海水了
            return 0;
        }
        // 将 (i, j) 变成海水
        grid[i][j] = 0;
        int area = 1;
        for (int[] dir : dirs) {
            area += dfs(grid, i + dir[0], j + dir[1]);
        }
        return area;
    }

    private static void testClosedIsland0() {
        int[][] grid = prepareGrid0();
        int ret = maxAreaOfIsland0(grid);
        System.out.println("island's max area is " + ret + ".");
    }

    private static int[][] prepareGrid0() {
        /*
         * 1 1 1 1 1 1 1 0
         * 1 0 0 0 0 1 1 0
         * 1 0 1 0 1 1 1 0
         * 1 0 0 0 0 1 0 1
         * 1 1 1 1 1 1 1 0
         */
        return new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 0},
            {1, 0, 0, 0, 0, 1, 1, 0},
            {1, 0, 1, 0, 1, 1, 1, 0},
            {1, 0, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 0},
        };
    }

    public static void main(String[] args) {
        // 力扣第 695 题「岛屿的最大面积」
        testClosedIsland0();
    }

}
