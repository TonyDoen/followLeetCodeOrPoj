package me.meet.labuladong._0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public final class LC1631 {
    private LC1631() {
    }

    /**
     * LeetCode 1631 题「最小体力消耗路径」，题目如下：
     *
     * 示例1:
     * 1    2    2
     * 3    8    2
     * 5    3    5
     * 输入: heights=[[1,2,2],[3,8,2],[5,3,5]]
     * 输出: 2
     * 解释: 路径[1,3,5,3,5]连续格子的差值绝对值最大为2。这条路径比路径[1,2,2,2,5]更优,因为另一条路径差值最最大值为3
     *
     * 函数签名如下：
     * // 输入一个二维矩阵，计算从左上角到右下角的最小体力消耗
     * int minimumEffortPath(int[][] heights);
     *
     * 我们常见的二维矩阵题目，如果让你从左上角走到右下角，比较简单的题一般都会限制你只能向右或向下走，但这道题可没有限制哦，你可以上下左右随便走，只要路径的「体力消耗」最小就行。
     * 如果你把二维数组中每个(x, y)坐标看做一个节点，它的上下左右坐标就是相邻节点，它对应的值和相邻坐标对应的值之差的绝对值就是题目说的「体力消耗」，你就可以理解为边的权重。
     * 这样一想，是不是就在让你以左上角坐标为起点，以右下角坐标为终点，计算起点到终点的最短路径？Dijkstra 算法是不是可以做到？
     * // 输入起点 start 和终点 end，计算起点到终点的最短距离
     * int dijkstra(int start, int end, List<Integer>[] graph)
     *
     * 只不过，这道题中评判一条路径是长还是短的标准不再是路径经过的权重总和，而是路径经过的权重最大值。
     * 明白这一点，再想一下使用 Dijkstra 算法的前提，加权有向图，没有负权重边，求最短路径，OK，可以使用，咱们来套框架。
     * 二维矩阵抽象成图，我们先实现一下图的adj方法，之后的主要逻辑会清晰一些：
     * 类似的，我们现在认为一个二维坐标(x, y)是图中的一个节点，所以这个State类也需要修改一下：
     * 接下来，就可以套用 Dijkstra 算法的代码模板了：
     *
     *
     *
     */
    // 方向数组，上下左右的坐标偏移量
    private static final int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    // 返回坐标 (x, y) 的上下左右相邻坐标
    static List<int[]> adj(int[][] matrix, int x, int y) {
        int m = matrix.length, n = matrix[0].length;
        // 存储相邻节点
        List<int[]> neighbors = new ArrayList<>();
        for (int[] dir : dirs) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx >= m || nx < 0 || ny >= n || ny < 0) {
                // 索引越界
                continue;
            }
            neighbors.add(new int[]{nx, ny});
        }
        return neighbors;
    }

    static class State {
        // 矩阵中的一个位置
        int x, y;
        // 从起点 (0, 0) 到当前位置的最小体力消耗（距离）
        int effortFromStart;

        State(int x, int y, int effortFromStart) {
            this.x = x;
            this.y = y;
            this.effortFromStart = effortFromStart;
        }
    }

    // Dijkstra 算法，计算 (0, 0) 到 (m - 1, n - 1) 的最小体力消耗
    static int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        // 定义：从 (0, 0) 到 (i, j) 的最小体力消耗是 effortTo[i][j]
        int[][] effortTo = new int[m][n];
        // dp table 初始化为正无穷
        for (int i = 0; i < m; i++) {
            Arrays.fill(effortTo[i], Integer.MAX_VALUE);
        }
        // base case，起点到起点的最小消耗就是 0
        effortTo[0][0] = 0;

        // 优先级队列，effortFromStart 较小的排在前面
        Queue<State> pq = new PriorityQueue<>((a, b) -> {
            return a.effortFromStart - b.effortFromStart;
        });

        // 从起点 (0, 0) 开始进行 BFS
        pq.offer(new State(0, 0, 0));

        while (!pq.isEmpty()) {
            State curState = pq.poll();
            int curX = curState.x;
            int curY = curState.y;
            int curEffortFromStart = curState.effortFromStart;

            // 到达终点提前结束
            if (curX == m - 1 && curY == n - 1) {
                return curEffortFromStart;
            }

            if (curEffortFromStart > effortTo[curX][curY]) {
                continue;
            }
            // 将 (curX, curY) 的相邻坐标装入队列
            for (int[] neighbor : adj(heights, curX, curY)) {
                int nextX = neighbor[0];
                int nextY = neighbor[1];
                // 计算从 (curX, curY) 达到 (nextX, nextY) 的消耗
                int effortToNextNode = Math.max(
                    effortTo[curX][curY],
                    Math.abs(heights[curX][curY] - heights[nextX][nextY])
                );
                // 更新 dp table
                if (effortTo[nextX][nextY] > effortToNextNode) {
                    effortTo[nextX][nextY] = effortToNextNode;
                    pq.offer(new State(nextX, nextY, effortToNextNode));
                }
            }
        }
        // 正常情况不会达到这个 return
        return -1;
    }

    private static void testMinimumEffortPath0() {
        /*
         * 1|   2    2
         * 3|___8____2
         * 5    3    5
         */
        int[][] heights = new int[][]{{1, 2, 2}, {3, 8, 2}, {5, 3, 5}};
        int ret = minimumEffortPath(heights);
        System.out.println(ret);
    }

    public static void main(String[] args) {
        // LeetCode 1631 题「最小体力消耗路径」
        testMinimumEffortPath0();
    }
}

