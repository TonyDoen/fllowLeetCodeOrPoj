package me.meet.leetcode.medium;

import java.util.LinkedList;

public final class IsGraphBipartite {
    private IsGraphBipartite() {}
    /**
     * url:http://www.cnblogs.com/grandyang/p/8519566.html
     * url:https://leetcode.com/problems/is-graph-bipartite/discuss/115487/Java-Clean-DFS-solution-with-Explanation
     *
     * Given an undirected graph, return true if and only if it is bipartite.
     * Recall that a graph is bipartite if we can split it's set of nodes into two independent subsets A and B such that every edge in the graph has one node in A and another node in B.
     * The graph is given in the following form: graph[i] is a list of indexes j for which the edge between nodes i and j exists.  Each node is an integer between 0 and graph.length - 1.  There are no self edges or parallel edges: graph[i] does not contain i, and it doesn't contain any element twice.
     *
     * Example 1:
     * Input: [[1,3], [0,2], [1,3], [0,2]]
     * Output: true
     *
     * Explanation:
     * The graph looks like this:
     * 0----1
     * |    |
     * |    |
     * 3----2
     * We can divide the vertices into two groups: {0, 2} and {1, 3}.
     *
     *
     * Example 2:
     * Input: [[1,2,3], [0,2], [0,1,3], [0,2]]
     * Output: false
     *
     * Explanation:
     * The graph looks like this:
     * 0----1
     * | \  |
     * |  \ |
     * 3----2
     * We cannot find a way to divide the set of nodes into two independent subsets.
     *
     * Note:
     * graph will have length in range [1, 100].
     * graph[i] will contain integers in range [0, graph.length - 1].
     * graph[i] will not contain i or duplicate values.
     * The graph is undirected: if any element j is in graph[i], then i will be in graph[j].
     *
     *
     * 题意: 是二分图吗
     * 输入数组中的graph[i]，表示顶点i所有相邻的顶点，比如对于例子1来说，顶点0和顶点1，3相连，顶点1和顶点0，2相连，顶点2和结点1，3相连，顶点3和顶点0，2相连。
     * 这道题让我们验证给定的图是否是二分图，所谓二分图，就是可以将图中的所有顶点分成两个不相交的集合，使得同一个集合的顶点不相连。为了验证是否有这样的两个不相交的集合存在，
     * 我们采用一种很机智的染色法，大体上的思路是要将相连的两个顶点染成不同的颜色，一旦在染的过程中发现有两连的两个顶点已经被染成相同的颜色，说明不是二分图。这里我们使用两种颜色，
     * 分别用1和-1来表示，初始时每个顶点用0表示未染色，然后遍历每一个顶点，如果该顶点未被访问过，则调用递归函数，如果返回false，那么说明不是二分图，则直接返回false。如果循环退出后没有返回false，则返回true。
     * 在递归函数中，如果当前顶点已经染色，如果该顶点的颜色和将要染的颜色相同，则返回true，否则返回false。如果没被染色，则将当前顶点染色，然后再遍历与该顶点相连的所有的顶点，调用递归函数，如果返回false了，则当前递归函数的返回false，循环结束返回true，参见代码如下：
     */
    static boolean isBipartite1(int[][] graph) {
        int xLen = graph.length;
        int[] colors = new int[xLen];
        for (int i = 0; i < xLen; ++i) {
            if (colors[i] == 0 && !valid(graph, 1, i, colors)) {
                return false;
            }
        }
        return true;
    }
    private static boolean valid(int[][] graph, int color, int cur, int[] colors) {
        if (colors[cur] != 0) return colors[cur] == color;
        colors[cur] = color;
        for (int i : graph[cur]) {
            if (!valid(graph, -1 * color, i, colors)) {
                return false;
            }
        }
        return true;
    }

    static boolean isBipartite2(int[][] graph) {
        int xLen = graph.length;
        int[] colors = new int[xLen];
        for (int i = 0; i < xLen; ++i) {
            if (colors[i] != 0) continue;
            colors[i] = 1;

            LinkedList<Integer> q = new LinkedList<Integer>();
            q.add(i);
            while (!q.isEmpty()) {
                int t = (q).poll();
                for (int a : graph[t]) {
                    if (colors[a] == 0) {
                        colors[a] = -1 * colors[t];
                        q.addFirst(a);
                    } else {
                        if (colors[a] == colors[t]) return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] src = new int[][]{{1,3}, {0,2}, {1,3}, {0,2}};
        boolean result1 = isBipartite1(src);
        boolean result2 = isBipartite2(src);
        System.out.println(result1);
        System.out.println(result2);
    }
}
