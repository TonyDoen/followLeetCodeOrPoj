package me.meet.labuladong._0;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public final class LC1514 {
    private LC1514() {
    }

    /**
     * LeetCode 1514 题「概率最大的路径」，题目如下：
     *
     * 示例1:
     *             0
     *          /   |
     *    0.5  /    | 0.2
     *        / 0.5 |
     *      1 ----- 2
     * 输入: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.2], start = 0, end = 2
     * 输出: 0.25000
     * 解释: 从起点到终点有两条路径,其中一条的成功概率为 0.2,而另一条为 0.5*0.5=0.25
     *
     * 函数签名如下：
     * // 输入一幅无向图，边上的权重代表概率，返回从 start 到达 end 最大的概率
     * double maxProbability(int n, int[][] edges, double[] succProb, int start, int end)
     *
     * 我说这题一看就是 Dijkstra 算法，但聪明的你肯定会反驳我：
     * 1、这题给的是无向图，也可以用 Dijkstra 算法吗？
     * 2、更重要的是，Dijkstra 算法计算的是最短路径，计算的是最小值，这题让你计算最大概率是一个最大值，怎么可能用 Dijkstra 算法呢？
     *
     *
     */
    static double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        List<double[]>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList<>();
        }
        // 构造邻接表结构表示图
        for (int i = 0; i < edges.length; i++) {
            int from = edges[i][0];
            int to = edges[i][1];
            double weight = succProb[i];
            // 无向图就是双向图；先把 int 统一转成 double，待会再转回来
            graph[from].add(new double[]{(double) to, weight});
            graph[to].add(new double[]{(double) from, weight});
        }

        return dijkstra(start, end, graph);
    }

    static class State {
        // 图节点的 id
        int id;
        // 从 start 节点到达当前节点的概率
        double probFromStart;

        State(int id, double probFromStart) {
            this.id = id;
            this.probFromStart = probFromStart;
        }
    }

    static double dijkstra(int start, int end, List<double[]>[] graph) {
        // 定义：probTo[i] 的值就是节点 start 到达节点 i 的最大概率
        double[] probTo = new double[graph.length];
        // dp table 初始化为一个取不到的最小值
        Arrays.fill(probTo, -1);
        // base case，start 到 start 的概率就是 1
        probTo[start] = 1;

        // 优先级队列，probFromStart 较大的排在前面
        Queue<State> pq = new PriorityQueue<>((a, b) -> {
            return Double.compare(b.probFromStart, a.probFromStart);
        });
        // 从起点 start 开始进行 BFS
        pq.offer(new State(start, 1));

        while (!pq.isEmpty()) {
            State curState = pq.poll();
            int curNodeID = curState.id;
            double curProbFromStart = curState.probFromStart;

            // 遇到终点提前返回
            if (curNodeID == end) {
                return curProbFromStart;
            }

            if (curProbFromStart < probTo[curNodeID]) {
                // 已经有一条概率更大的路径到达 curNode 节点了
                continue;
            }
            // 将 curNode 的相邻节点装入队列
            for (double[] neighbor : graph[curNodeID]) {
                int nextNodeID = (int) neighbor[0];
                // 看看从 curNode 达到 nextNode 的概率是否会更大
                double probToNextNode = probTo[curNodeID] * neighbor[1];
                if (probTo[nextNodeID] < probToNextNode) {
                    probTo[nextNodeID] = probToNextNode;
                    pq.offer(new State(nextNodeID, probToNextNode));
                }
            }
        }
        // 如果到达这里，说明从 start 开始无法到达 end，返回 0
        return 0.0;
    }

    private static void testMaxProbability0() {
        int[][] edges = new int[][]{{0, 1}, {1, 2}, {0, 2}};
        double[] succProb = new double[]{0.5, 0.5, 0.2};
        int n = 3, start = 0, end = 2;
        double ret = maxProbability(n, edges, succProb, start, end);
        System.out.println(ret);
    }

    public static void main(String[] args) {
        // LeetCode 1514 题「概率最大的路径」
        testMaxProbability0();
    }
}

