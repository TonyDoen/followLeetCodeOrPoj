package me.meet.labuladong._0;

import java.util.HashSet;

public class LC0694 {
    private LC0694() {
    }

    /**
     * 不同的岛屿数量
     * 力扣第 694 题「不同的岛屿数量」，题目还是输入一个二维矩阵，0表示海水，1表示陆地，这次让你计算 不同的 (distinct) 岛屿数量，函数签名如下：
     * int numDistinctIslands(int[][] grid)
     *
     * 比如题目输入下面这个二维矩阵：
     *
     * 1 1 0 1 1
     * 1 0 0 0 0
     * 0 0 0 0 1
     * 1 1 0 1 1
     *
     * 其中有四个岛屿，但是左下角和右上角的岛屿形状相同，所以不同的岛屿共有三个，算法返回 3。
     * 很显然我们得想办法把二维矩阵中的「岛屿」进行转化，变成比如字符串这样的类型，然后利用 HashSet 这样的数据结构去重，最终得到不同的岛屿的个数。
     * 如果想把岛屿转化成字符串，说白了就是序列化，序列化说白了遍历嘛，前文 二叉树的序列化和反序列化 讲了二叉树和字符串互转，这里也是类似的。
     *
     * 首先，对于形状相同的岛屿，如果从同一起点出发，dfs函数遍历的顺序肯定是一样的。
     * 因为遍历顺序是写死在你的递归函数里面的，不会动态改变：
     *
     * void dfs(int[][] grid, int i, int j) {
     *     // 递归顺序：
     *     dfs(grid, i - 1, j); // 上
     *     dfs(grid, i + 1, j); // 下
     *     dfs(grid, i, j - 1); // 左
     *     dfs(grid, i, j + 1); // 右
     * }
     *
     * 所以，遍历顺序从某种意义上说就可以用来描述岛屿的形状，比如下图这两个岛屿：
     * 假设它们的遍历顺序是：下，右，上，撤销上，撤销右，撤销下
     * 如果我用分别用1, 2, 3, 4代表上下左右，用-1, -2, -3, -4代表上下左右的撤销，
     * 那么可以这样表示它们的遍历顺序：2, 4, 1, -1, -4, -2
     *
     * 你看，这就相当于是岛屿序列化的结果，只要每次使用dfs遍历岛屿的时候生成这串数字进行比较，就可以计算到底有多少个不同的岛屿了。
     * 要想生成这段数字，需要稍微改造dfs函数，添加一些函数参数以便记录遍历顺序：
     *
     * void dfs(int[][] grid, int i, int j, StringBuilder sb, int dir) {
     *     int m = grid.length, n = grid[0].length;
     *     if (i < 0 || j < 0 || i >= m || j >= n
     *         || grid[i][j] == 0) {
     *         return;
     *     }
     *     // 前序遍历位置：进入 (i, j)
     *     grid[i][j] = 0;
     *     sb.append(dir).append(',');
     *
     *     dfs(grid, i - 1, j, sb, 1); // 上
     *     dfs(grid, i + 1, j, sb, 2); // 下
     *     dfs(grid, i, j - 1, sb, 3); // 左
     *     dfs(grid, i, j + 1, sb, 4); // 右
     *
     *     // 后序遍历位置：离开 (i, j)
     *     sb.append(-dir).append(',');
     * }
     *
     * dir记录方向，dfs函数递归结束后，sb记录着整个遍历顺序，其实这就是前文 回溯算法核心套路 说到的回溯算法框架，你看到头来这些算法都是相通的。
     * 有了这个dfs函数就好办了，我们可以直接写出最后的解法代码：
     *
     * int numDistinctIslands(int[][] grid) {
     *     int m = grid.length, n = grid[0].length;
     *     // 记录所有岛屿的序列化结果
     *     HashSet<String> islands = new HashSet<>();
     *     for (int i = 0; i < m; i++) {
     *         for (int j = 0; j < n; j++) {
     *             if (grid[i][j] == 1) {
     *                 // 淹掉这个岛屿，同时存储岛屿的序列化结果
     *                 StringBuilder sb = new StringBuilder();
     *                 // 初始的方向可以随便写，不影响正确性
     *                 dfs(grid, i, j, sb, 666);
     *                 islands.add(sb.toString());
     *             }
     *         }
     *     }
     *     // 不相同的岛屿数量
     *     return islands.size();
     * }
     *
     * 这样，这道题就解决了，至于为什么初始调用dfs函数时的dir参数可以随意写，
     * 这里涉及 DFS 和回溯算法的一个细微差别，前文 图算法基础 有写，这里就不展开了。
     * 以上就是全部岛屿系列问题的解题思路，也许前面的题目大部分人会做，但是最后两题还是比较巧妙的，希望本文对你有帮助。
     *
     *
     */
    static int numDistinctIslands0(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        // 记录所有岛屿的序列化结果
        HashSet<String> islands = new HashSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 淹掉这个岛屿，同时存储岛屿的序列化结果
                    StringBuilder sb = new StringBuilder();
                    // 初始的方向可以随便写，不影响正确性
                    dfs(grid, i, j, sb, 666);
                    islands.add(sb.toString());
                }
            }
        }
        // 不相同的岛屿数量
        return islands.size();
    }

    private static final int[][] dirs = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};

    private static void dfs(int[][] grid, int i, int j, StringBuilder route, int initDir) {
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
        // 前序遍历位置：进入 (i, j)
        route.append(initDir).append(',');

        int curI = 1;
        for (int[] dir : dirs) {
            dfs(grid, i + dir[0], j + dir[1], route, curI++);
        }

        // 后序遍历位置：离开 (i, j)
        route.append(-initDir).append(',');
    }

    private static void testNumDistinctIslands0() {
        int[][] grid = prepareGrid0();
        int ret = numDistinctIslands0(grid);
        System.out.println("there is " + ret + " distinct islands.");
    }

    private static int[][] prepareGrid0() {
        /*
         * 1 1 0 1 1
         * 1 0 0 0 0
         * 0 0 0 0 1
         * 1 1 0 1 1
         */
        return new int[][]{
            {1, 1, 0, 1, 1},
            {1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1},
            {1, 1, 0, 1, 1}
        };
    }

    public static void main(String[] args) {
        // 力扣第 694 题「不同的岛屿数量」
        testNumDistinctIslands0();
    }

}
