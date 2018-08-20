package me.meet.labuladong._0;

public class LC1020 {
    private LC1020() {
    }

    /**
     * 封闭岛屿的数量
     * 力扣第 1020 题「飞地的数量」，这题不让你求封闭岛屿的数量，而是求封闭岛屿的面积总和。
     * 其实思路都是一样的，先把靠边的陆地淹掉，然后去数剩下的陆地数量就行了，注意第 1020 题中1代表陆地，0代表海水：
     *
     * 比如题目给你输入如下这个二维矩阵：
     *
     * 1 1 1 1 1 1 1 0
     * 1 0 0 0 0 1 1 0
     * 1 0 1 0 1 1 1 0
     * 1 0 0 0 0 1 0 1
     * 1 1 1 1 1 1 1 0
     *
     */
    // 主函数：求封闭岛屿的面积总和
    static int numEnclaves0(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        // 淹掉靠边的陆地
        for (int i = 0; i < m; i++) {
            dfs(grid, i, 0);
            dfs(grid, i, n - 1);
        }
        for (int j = 0; j < n; j++) {
            dfs(grid, 0, j);
            dfs(grid, m - 1, j);
        }

        // 数一数剩下的陆地
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    res += 1;
                }
            }
        }

        return res;
    }

    private static final int[][] dirs = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};

    private static void dfs(int[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            // 超出索引边界
            return;
        }
        if (0 == grid[i][j]) {
            // 已经是海水了
            return;
        }
        // 将 (i, j) 变成海水
        grid[i][j] = 0;
        for (int[] dir : dirs) {
            dfs(grid, i + dir[0], j + dir[1]);
        }
    }

    private static void testClosedIsland0() {
        int[][] grid = prepareGrid0();
        int ret = numEnclaves0(grid);
        System.out.println("there is " + ret + " islands.");
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
        // 力扣第 1020 题「飞地的数量」
        testClosedIsland0();
    }

}
