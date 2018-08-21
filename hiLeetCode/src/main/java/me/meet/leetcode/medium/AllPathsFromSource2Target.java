package me.meet.leetcode.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AllPathsFromSource2Target {
    private AllPathsFromSource2Target() {}

    /**
     url: https://leetcode.com/problems/all-paths-from-source-to-target/solution/
     url: http://www.cnblogs.com/grandyang/p/9262159.html

     Given a directed, acyclic graph of N nodes.  Find all possible paths from node 0 to node N-1, and return them in any order.
     The graph is given as follows:  the nodes are 0, 1, ..., graph.length - 1.  graph[i] is a list of all nodes j for which the edge (i, j) exists.

     Example:
     Input: [[1,2], [3], [3], []]
     Output: [[0,1,3],[0,2,3]]
     Explanation: The graph looks like this:
     0--->1
     |    |
     v    v
     2--->3
     There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.

     Note:
     The number of nodes in the graph will be in the range [2, 15].
     You can print different paths in any order, but you should keep the order of nodes inside one path.
     */

    /**
     题意：从起点到目标点到所有路径

     */

    static List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        return solve(graph, 0);
    }
    private static List<List<Integer>> solve(int[][] graph, int node) {
        int N = graph.length;
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (node == N - 1) {
            List<Integer> path = new ArrayList<Integer>();
            path.add(N-1);
            ans.add(path);
            return ans;
        }

        for (int nei: graph[node]) {
            for (List<Integer> path: solve(graph, nei)) {
                path.add(0, node);
                ans.add(path);
            }
        }
        return ans;
    }

    static List<List<Integer>> allPathsSourceTarget2(int[][] graph) {
        int n = graph.length;
        Map<Integer, List<Integer>> g = new HashMap<Integer, List<Integer>>();

        for (int i = 0; i < n; i++) {
            if (!g.containsKey(i))
                g.put(i, new ArrayList<Integer>());

            for (int j = 0; j < graph[i].length; j++)
                g.get(i).add(graph[i][j]);
        }

        List<List<Integer>> res = new ArrayList<List<Integer>>();
        boolean[] used = new boolean[n];
        List<Integer> path = new ArrayList<Integer>();

        path.add(0);
        used[0] = true;

        dfs(0, n - 1, used, g, path, res);

        return res;
    }
    private static void dfs(int v, int end, boolean[] used, Map<Integer, List<Integer>> g, List<Integer> path, List<List<Integer>> res) {
        if (v == end) {
            res.add(new ArrayList<Integer>(path));
            return;
        }

        List<Integer> next = g.get(v);
        if (next == null)
            return;

        for (int x : next) {
            if (!used[x]) {
                used[x] = true;
                path.add(x);
                dfs(x, end, used, g, path, res);
                path.remove(path.size() - 1);
                used[x] = false;
            }
        }
    }

    public static void main(String[] args) {
        int[][] graph = {{1,2}, {3}, {3}, {}};

        List<List<Integer>> res = allPathsSourceTarget(graph);
        List<List<Integer>> res2 = allPathsSourceTarget2(graph);
        System.out.println(res);
        System.out.println(res2);
    }
}
