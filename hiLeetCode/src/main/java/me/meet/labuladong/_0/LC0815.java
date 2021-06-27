package me.meet.labuladong._0;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public final class LC0815 {
    private LC0815() {
    }
    /*
     * 815. 公交路线
     *
     * 给你一个数组 routes ，表示一系列公交线路，其中每个 routes[i] 表示一条公交线路，第 i 辆公交车将会在上面循环行驶。
     * 例如，路线 routes[0] = [1, 5, 7] 表示第 0 辆公交车会一直按序列 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... 这样的车站路线行驶。
     * 现在从 source 车站出发（初始时不在公交车上），要前往 target 车站。 期间仅可乘坐公交车。
     * 求出 最少乘坐的公交车数量 。如果不可能到达终点车站，返回 -1 。
     *
     *
     * 示例 1：
     * 输入：routes = [[1,2,7],[3,6,7]], source = 1, target = 6
     * 输出：2
     * 解释：最优策略是先乘坐第一辆公交车到达车站 7 , 然后换乘第二辆公交车到车站 6 。
     *
     * 示例 2：
     * 输入：routes = [[7,12],[4,5,15],[6],[15,19],[9,12,13]], source = 15, target = 12
     * 输出：-1
     *
     *
     * 提示：
     * 1 <= routes.length <= 500.
     * 1 <= routes[i].length <= 105
     * routes[i] 中的所有值 互不相同
     * sum(routes[i].length) <= 105
     * 0 <= routes[i][j] < 106
     * 0 <= source, target < 106
     *
     */

    static int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }
        // routes[i]中存第i辆公交会经过哪些station
        // <station,{bus}>-每个站都被哪些公交车经过
        HashMap<Integer, HashSet<Integer>> s2b = new HashMap<>();
        for (int i = 0; i < routes.length; ++i) {
            // i => 第i辆公交
            for (int j : routes[i]) {
                // j => 经过哪些station
                HashSet<Integer> b = s2b.computeIfAbsent(j, k -> new HashSet<>());
                b.add(i);
            }
        }
        //
        if (!s2b.containsKey(source) || !s2b.containsKey(target)) {
            return -1;
        }

        // 记录已经坐了哪些公交车
        int[] visited = new int[routes.length];
        // 收集当前station辐射到的station
        LinkedList<Integer> queue = new LinkedList<>();
        for (int b : s2b.get(source)) {
            visited[b] = 1;
            queue.add(b);
        }
        // 坐过多少公交车
        int cnt = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            cnt++;

            // while(size--)结束，没有找到target说明需要换乘一次，cnt++
            while (size-- > 0) {
                int cur = queue.removeFirst();
                // 收集cur辐射到的所有station，都是cur可以不用换乘到达的车站
                for (int s : routes[cur]) {
                    if (s == target) {
                        return cnt;
                    }

                    for (int b : s2b.get(s)) {
                        if (1 == visited[b]) {
                            continue;
                        }
                        visited[b] = 1;
                        queue.add(b);
                    }
                }
            }
        }

        return -1;
    }

    private static void testNumBusesToDestination() {
        int[][] routes = new int[][]{{7, 12}, {4, 5, 15}, {6}, {15, 19}, {9, 12, 13}};
        int source = 15, target = 12;
        int rs = numBusesToDestination(routes, source, target);
        System.out.println(rs);
    }

    public static void main(String[] args) {
        testNumBusesToDestination();
    }
}
