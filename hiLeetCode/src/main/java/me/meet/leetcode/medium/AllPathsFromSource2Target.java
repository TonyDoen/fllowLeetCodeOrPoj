package me.meet.leetcode.medium;

import java.util.*;

public final class AllPathsFromSource2Target {
    private AllPathsFromSource2Target() {
    }

    /**
     * All Paths From Source to Target
     *
     * url: https://leetcode.com/problems/all-paths-from-source-to-target/solution/
     * url: http://www.cnblogs.com/grandyang/p/9262159.html
     *
     * Given a directed, acyclic graph of N nodes.  Find all possible paths from node 0 to node N-1, and return them in any order.
     * The graph is given as follows:  the nodes are 0, 1, ..., graph.length - 1.  graph[i] is a list of all nodes j for which the edge (i, j) exists.
     *
     * Example:
     * Input: [[1,2], [3], [3], []]
     * Output: [[0,1,3],[0,2,3]]
     * Explanation: The graph looks like this:
     * 0--->1
     * |    |
     * v    v
     * 2--->3
     * There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.
     *
     * Note:
     * The number of nodes in the graph will be in the range [2, 15].
     * You can print different paths in any order, but you should keep the order of nodes inside one path.
     *
     *
     * 题意：从起点到目标点到所有路径
     * 给一个有向无环图，二维数组代表图中存在等路径，例如：graph[i]=[j,k] 表示(i,j),(i,k)这两条路径存在。 求从起点到目标点到所有路径
     *
     * 思路：
     * 这道题的本质就是遍历邻接链表，由于要列出所有路径情况，那么递归就是不二之选了。
     * 我们用cur来表示当前遍历到的结点，初始化为0，然后在递归函数中，先将其加入路径path，如果cur等于N-1了，那么说明到达结点N-1了，将path加入结果res。否则我们再遍历cur的邻接结点，调用递归函数即可
     */
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

    static List<List<Integer>> allPathsSourceTarget1(int[][] graph) {
        if (null == graph) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new LinkedList<>();
        helpAllPathsSourceTarget1(result, new LinkedList<>(), 0, graph);
        return result;
    }
    private static void helpAllPathsSourceTarget1(List<List<Integer>> result, LinkedList<Integer> path, int cur, int[][] graph) {
        path.add(cur);
        if (graph.length-1 == cur) {
            result.add(new LinkedList<>(path));
        } else {
            for (int neighbor : graph[cur]) {
                helpAllPathsSourceTarget1(result, path, neighbor, graph);
            }
        }
        path.removeLast();
    }

    /**
     * 思路：这道题的本质就是遍历邻接链表，由于要列出所有路径情况，那么递归就是不二之选了。我们用cur来表示当前遍历到的结点，初始化为0，然后在递归函数中，先将其加入路径path，如果cur等于N-1了，那么说明到达结点N-1了，将path加入结果res。否则我们再遍历cur的邻接结点，调用递归函数即可
     */
    static List<List<Integer>> allPathsSourceTarget3(int[][] graph) {
        return helper(graph, 0);
    }

    private static List<List<Integer>> helper(int[][] graph, int cur) {
        List<List<Integer>> res = new LinkedList<>();

        if (cur == graph.length - 1) {                           // 1. 如果cur等于N-1了，直接将cur先装入数组，再装入结果res中返回。
            LinkedList<Integer> l1 = new LinkedList<>();
            l1.add(cur);
            res.add(l1);
            return res;
        }

        for (int neighbor : graph[cur]) {                        // 2. 否则就遍历cur的邻接结点
            for (List<Integer> path : helper(graph, neighbor)) { // 2.1 对于每个邻接结点，先调用递归函数，然后遍历其返回的结果
                path.add(0, cur);                          // 2.2 对于每个遍历到的path，将cur加到数组首位置，然后将path加入结果res中
                res.add(path);
            }
        }
        return res;
    }


    private static void testAllPathsSourceTarget() {
        int[][] graph = {{1, 2}, {3}, {3}, {}};

        List<List<Integer>> res1 = allPathsSourceTarget1(graph);
        System.out.println(res1);

        List<List<Integer>> res2 = allPathsSourceTarget2(graph);
        System.out.println(res2);

        List<List<Integer>> res3 = allPathsSourceTarget3(graph);
        System.out.println(res3);

    }

    public static void main(String[] args) {
        testAllPathsSourceTarget();
    }
}
