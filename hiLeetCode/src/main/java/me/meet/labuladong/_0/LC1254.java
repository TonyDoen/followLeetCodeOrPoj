package me.meet.labuladong._0;

public class LC1254 {
    private LC1254() {
    }

    /**
     * 封闭岛屿的数量
     * 力扣第 1254 题「统计封闭岛屿的数目」和上一题有两点不同：
     * 上一题说二维矩阵四周可以认为也是被海水包围的，所以靠边的陆地也算作岛屿。
     * 1>用0表示陆地，用1表示海水。
     * 2>让你计算「封闭岛屿」的数目。所谓「封闭岛屿」就是上下左右全部被1包围的0，也就是说靠边的陆地不算作「封闭岛屿」。
     * int closedIsland(int[][] grid)
     *
     *
     * 比如题目给你输入如下这个二维矩阵：
     *
     * 1 1 1 1 1 1 1 0
     * 1 0 0 0 0 1 1 0
     * 1 0 1 0 1 1 1 0
     * 1 0 0 0 0 1 0 1
     * 1 1 1 1 1 1 1 0
     *
     * 算法返回 2，只有图中灰色部分的0是四周全都被海水包围着的「封闭岛屿」。
     * 那么如何判断「封闭岛屿」呢？其实很简单，把上一题中那些靠边的岛屿排除掉，剩下的不就是「封闭岛屿」了吗？
     * 有了这个思路，就可以直接看代码了，注意这题规定0表示陆地，用1表示海水：
     *
     * 只要提前把靠边的陆地都淹掉，然后算出来的就是封闭岛屿了。
     * PS：处理这类岛屿问题除了 DFS/BFS 算法之外，
     * Union Find 并查集算法也是一种可选的方法，前文 Union Find 算法运用 就用 Union Find 算法解决了一道类似的问题。
     *
     *
     *
     */
    // 主函数：计算封闭岛屿的数量
    static int closedIsland0(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int j = 0; j < n; j++) {
            // 把靠上边的岛屿淹掉
            dfs(grid, 0, j);
            // 把靠下边的岛屿淹掉
            dfs(grid, m - 1, j);
        }
        for (int i = 0; i < m; i++) {
            // 把靠左边的岛屿淹掉
            dfs(grid, i, 0);
            // 把靠右边的岛屿淹掉
            dfs(grid, i, n - 1);
        }
        // 遍历 grid，剩下的岛屿都是封闭岛屿
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    res++;
                    dfs(grid, i, j);
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
        if (1 == grid[i][j]) {
            // 已经是海水了
            return;
        }
        // 将 (i, j) 变成海水
        grid[i][j] = 1;
        for (int[] dir : dirs) {
            dfs(grid, i + dir[0], j + dir[1]);
        }
    }

    private static void testClosedIsland0() {
        int[][] grid = prepareGrid0();
        int ret = closedIsland0(grid);
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
        // 力扣第 1254 题「统计封闭岛屿的数目」
        testClosedIsland0();
    }

}
