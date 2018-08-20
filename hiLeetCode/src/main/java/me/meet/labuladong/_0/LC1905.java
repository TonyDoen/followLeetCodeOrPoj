package me.meet.labuladong._0;

public class LC1905 {
    private LC1905() {
    }

    /**
     * 子岛屿数量
     * 如果说前面的题目都是模板题，那么力扣第 1905 题「统计子岛屿」可能得动动脑子了：
     * 这道题的关键在于，如何快速判断子岛屿？
     * 肯定可以借助 Union Find 并查集算法 来判断，不过本文重点在 DFS 算法，就不展开并查集算法了。
     *
     * 什么情况下grid2中的一个岛屿B是grid1中的一个岛屿A的子岛？
     * 当岛屿B中所有陆地在岛屿A中也是陆地的时候，岛屿B是岛屿A的子岛。
     * 反过来说，如果岛屿B中存在一片陆地，在岛屿A的对应位置是海水，那么岛屿B就不是岛屿A的子岛。
     * 那么，我们只要遍历grid2中的所有岛屿，把那些不可能是子岛的岛屿排除掉，剩下的就是子岛。
     * 依据这个思路，可以直接写出下面的代码：
     *
     * 这道题的思路和计算「封闭岛屿」数量的思路有些类似，只不过后者排除那些靠边的岛屿，前者排除那些不可能是子岛的岛屿。
     *
     */
    // 主函数：「统计子岛屿」
    static int countSubIslands0(int[][] grid1, int[][] grid2) {
        int m = grid1.length, n = grid1[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid1[i][j] == 0 && grid2[i][j] == 1) {
                    // 这个岛屿肯定不是子岛，淹掉
                    dfs(grid2, i, j);
                }
            }
        }
        // 现在 grid2 中剩下的岛屿都是子岛，计算岛屿数量
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) {
                    res++;
                    dfs(grid2, i, j);
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

    private static void testCountSubIslands0() {
        int[][] grid = prepareGrid0();
        int ret = countSubIslands0(grid, grid);
        System.out.println("there is " + ret + " sub islands.");
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
        // 力扣第 1905 题「统计子岛屿」
        testCountSubIslands0();
    }

}
