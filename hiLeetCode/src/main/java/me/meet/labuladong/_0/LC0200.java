package me.meet.labuladong._0;

public class LC0200 {
    private LC0200() {
    }

    /**
     * 岛屿数量
     *
     * 这是力扣第 200 题「岛屿数量」，最简单也是最经典的一道岛屿问题，
     * 题目会输入一个二维数组grid，其中只包含0或者1，0代表海水，1代表陆地，且假设该矩阵四周都是被海水包围着的。
     * 我们说连成片的陆地形成岛屿，那么请你写一个算法，计算这个矩阵grid中岛屿的个数，函数签名如下：
     * int numIslands(char[][] grid);
     *
     * 比如说题目给你输入下面这个grid有四片岛屿，算法应该返回 4：
     *
     * 1 1 0 1 1
     * 1 0 0 0 0
     * 0 0 0 0 1
     * 1 1 0 1 1
     *
     * 思路很简单，关键在于如何寻找并标记「岛屿」，这就要 DFS 算法发挥作用了，我们直接看解法代码：
     * // 主函数，计算岛屿数量
     * int numIslands(char[][] grid) {
     *     int res = 0;
     *     int m = grid.length, n = grid[0].length;
     *     // 遍历 grid
     *     for (int i = 0; i < m; i++) {
     *         for (int j = 0; j < n; j++) {
     *             if (grid[i][j] == '1') {
     *                 // 每发现一个岛屿，岛屿数量加一
     *                 res++;
     *                 // 然后使用 DFS 将岛屿淹了
     *                 dfs(grid, i, j);
     *             }
     *         }
     *     }
     *     return res;
     * }
     *
     * // 从 (i, j) 开始，将与之相邻的陆地都变成海水
     * void dfs(char[][] grid, int i, int j) {
     *     int m = grid.length, n = grid[0].length;
     *     if (i < 0 || j < 0 || i >= m || j >= n) {
     *         // 超出索引边界
     *         return;
     *     }
     *     if (grid[i][j] == '0') {
     *         // 已经是海水了
     *         return;
     *     }
     *     // 将 (i, j) 变成海水
     *     grid[i][j] = '0';
     *     // 淹没上下左右的陆地
     *     dfs(grid, i + 1, j);
     *     dfs(grid, i, j + 1);
     *     dfs(grid, i - 1, j);
     *     dfs(grid, i, j - 1);
     * }
     *
     * 为什么每次遇到岛屿，都要用 DFS 算法把岛屿「淹了」呢？主要是为了省事，避免维护visited数组。
     * 因为dfs函数遍历到值为0的位置会直接返回，所以只要把经过的位置都设置为0，就可以起到不走回头路的作用。
     * PS：这类 DFS 算法还有个别名叫做 FloodFill 算法，现在有没有觉得 FloodFill 这个名字还挺贴切的~
     * 这个最最基本的岛屿问题就说到这，我们来看看后面的题目有什么花样。
     *
     *
     *
     */
    static int numIslands0(char[][] grid) {
        int res = 0;
        int m = grid.length, n = grid[0].length;
        // 遍历 grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    // 每发现一个岛屿，岛屿数量加一
                    res++;
                    // 然后使用 DFS 将岛屿淹了
                    dfs0(grid, i, j);
                }
            }
        }
        return res;
    }

    // 从 (i, j) 开始，将与之相邻的陆地都变成海水
    private static void dfs0(char[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            // 超出索引边界
            return;
        }
        if (grid[i][j] == '0') {
            // 已经是海水了
            return;
        }
        // 将 (i, j) 变成海水
        grid[i][j] = '0';
        // 淹没上下左右的陆地
        dfs0(grid, i + 1, j);
        dfs0(grid, i, j + 1);
        dfs0(grid, i - 1, j);
        dfs0(grid, i, j - 1);
    }

    private static void testNumIslands() {
        char[][] grid = prepareGrid0();
        int ret = numIslands0(grid);
        System.out.println("there is " + ret + " islands.");
    }

    private static char[][] prepareGrid0() {
        /*
         * 1 1 0 1 1
         * 1 0 0 0 0
         * 0 0 0 0 1
         * 1 1 0 1 1
         */
        return new char[][]{
            {'1', '1', '0', '1', '1'},
            {'1', '0', '0', '0', '0'},
            {'0', '0', '0', '0', '1'},
            {'1', '1', '0', '1', '1'}
        };
    }

    public static void main(String[] args) {
        // 力扣第 200 题「岛屿数量」
        testNumIslands();
    }

}
