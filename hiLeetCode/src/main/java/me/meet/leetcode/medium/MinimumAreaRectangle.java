package me.meet.leetcode.medium;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class MinimumAreaRectangle {
    private MinimumAreaRectangle() {
    }

    /**
     * 939. Minimum Area Rectangle
     * 
     * Given a set of points in the xy-plane, determine the minimum area of a rectangle formed from these points, with sides parallel to the x and y axes.
     * If there isn't any rectangle, return 0.
     * 
     * Example 1:
     * Input: [[1,1],[1,3],[3,1],[3,3],[2,2]]
     * Output: 4
     * 
     * Example 2:
     * Input: [[1,1],[1,3],[3,1],[3,3],[4,1],[4,3]]
     * Output: 2
     * 
     * Note:
     * 1 <= points.length <= 500
     * 0 <= points[i][0] <= 40000
     * 0 <= points[i][1] <= 40000
     * All points are distinct.
     * 
     * 
     * 题意：面积最小的矩形
     * 思路：这道题给了我们一堆点的坐标，问能组成的最小的矩形面积是多少，题目中限定了矩形的边一定是平行于主轴的，不会出现旋转矩形的形状。如果知道了矩形的两个对角顶点坐标，求面积就非常的简单了，但是随便取四个点并不能保证一定是矩形，不过这四个点坐标之间是有联系的，相邻的两个顶点要么横坐标，要么纵坐标，一定有一个是相等的，这个特点先记下。策略是，先找出两个对角线的顶点，一但两个对角顶点确定了，其实这个矩形的大小也就确定了，另外的两个点其实就是分别在跟这两个点具有相同的横坐标或纵坐标的点中寻找即可，为了优化查找的时间，可以事先把所有具有相同横坐标的点的纵坐标放入到一个 HashSet 中，使用一个 HashMap，建立横坐标和所有具有该横坐标的点的纵坐标的集合之间的映射。然后开始遍历任意两个点的组合，由于这两个点必须是对角顶点，所以其横纵坐标均不能相等，若有一个相等了，则跳过该组合。否则看其中任意一个点的横坐标对应的集合中是否均包含另一个点的纵坐标，均包含的话，说明另两个顶点也是存在的，就可以计算矩形的面积了，更新结果 res，若最终 res 还是初始值，说明并没有能组成矩形，返回0即可，
     */
    static int minAreaRect(int[][] points) {
        int res = Integer.MAX_VALUE, n = points.length;
        Map<Integer, Set<Integer>> mp = new HashMap<>();
        for (int[] point : points) {
            Set<Integer> st = mp.computeIfAbsent(point[0], k -> new HashSet<>());
            st.add(point[1]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (points[i][0] == points[j][0] || points[i][1] == points[j][1]) {
                    continue;
                }
                Set<Integer> i0 = mp.get(points[i][0]), j0 = mp.get(points[j][0]);
                if (null != i0 && null != j0 && i0.contains(points[j][1]) && j0.contains(points[i][1])) {
                    res = Math.min(res, Math.abs(points[i][0] - points[j][0]) * Math.abs(points[i][1] - points[j][1]));
                }
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    private static void testMinAreaRect() {
        int[][] points = new int[][]{{1, 1}, {1, 3}, {3, 1}, {3, 3}, {2, 2}};
        int res = minAreaRect(points);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testMinAreaRect();
    }
}
