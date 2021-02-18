package me.meet.labuladong._0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class _00003 {
    private _00003() {
    }

    /**
     * 区间相关问题
     *
     * 区间问题就是线段问题，让你合并所有线段，找出线段的交集，
     * 主要2个技巧
     * <1>排序;
     * <2>画图;
     *
     *
     */
    /**
     * 区间覆盖问题
     * LeetCode 1288 删除被覆盖区间
     *
     * 给你一个区间列表，请你删除列表种被其他区间所覆盖的区间。
     * 只有当 c <= a 且 b <= d 时，我们才认为区间 [a, b) 被左闭右开区间 [c, d) 覆盖
     * 在完成所有删除操作后，请你返回列表中剩余区间的数目
     *
     *
     * 例子1：
     * input: intervals = [[1,4],[3,6],[2,8]]
     * output: 2
     * explain: 区间 [3,6] 被区间 [2,8] 覆盖，所以它被删除。
     *
     *
     * 思路：
     * 去掉被覆盖区间后，还剩下多少区间，总区间数-被覆盖区间数=剩余区间数
     * 排序区间后
     * 有3种相对位置
     * <1>
     *     |______________|
     *         |_______|
     *
     * <2>
     *     |______________|
     *               |___________|
     *
     * <3>
     *     |___________|
     *                     |____________|
     *
     * 情况1， 找到覆盖区域。
     * 情况2，2个区域可以合并，成为一个大区域
     * 情况3，2个区域完全不相交
     *
     */
    static int removeCoveredIntervals(int[][] intvs) {
        // 按照起点升序排列，起点相同同时按照终点降序排列
        // 保证长的区域在上面，这样才会被判定为覆盖。
        Arrays.sort(intvs, (a, b)->{
            if (a[0] == b[0]) {
                return b[1]-a[1];
            }
            return a[0]-b[0];
        });

        // 记录合并区域的起点和终点
        int left = intvs[0][0];
        int right = intvs[0][1];

        int res = 0;
        for (int i = 1; i < intvs.length; i++) {
            int[] intv = intvs[i];
            // 情况1， 找到覆盖区域。
            if (left <= intv[0] && right >= intv[1]) {
                res++;
            }
            // 情况2， 2个区域可以合并，成为一个大区域
            if (right >= intv[0] && right <= intv[1]) {
                right = intv[1];
            }
            // 情况3， 2个区域完全不相交
            if (right < intv[0]) {
                left = intv[0];
                right = intv[1];
            }
        }
        return intvs.length - res;
    }

    /**
     * 区间合并问题
     * LeetCode 56
     *
     * 给出一个区间的集合，请合并所有重叠的区间
     * 例子1：
     * input:  [[1, 3], [2, 6], [8, 10], [15, 18]]
     * output: [[1, 6], [8, 10], [15, 18]]
     * explain:[1, 3] 和 [2, 6] 重叠，将它们合并 [1, 6]
     *
     * 例子2：
     * input:  [[1, 4], [4, 5]]
     * output: [[1, 5]]
     * explain:[1, 4] 和 [4, 5] 重叠，将它们合并 [1, 5]
     *
     */
    static List<int[]> mergeCoveredIntervals(int[][] intvs) {
        // 按照起点升序排列，起点相同同时按照终点降序排列
        // 保证长的区域在上面，这样才会被判定为覆盖。
        Arrays.sort(intvs, (a, b)->{
            if (a[0] == b[0]) {
                return b[1]-a[1];
            }
            return a[0]-b[0];
        });

        List<int[]> res = new ArrayList<>();
        res.add(intvs[0]);
        for (int i = 1; i < intvs.length; i++) {
            int[] curr = intvs[i];
            // res 中最后一个元素引用
            int[] last = res.get(res.size()-1);
            if (curr[0] <= last[1]) {
                // 找到最大的end
                last[1] = Math.max(last[1], curr[1]);
            } else {
                // 处理下一个待合并区间
                res.add(curr);
            }
        }
        return res;
    }

    /**
     * 区间交集问题
     * LeetCode 986
     *
     * 给定2个由一些闭区间组成的列表，每个区间列表都是成对不相交，并且已经排序。
     * 返回这2个区间列表的交集。
     *
     * 形式上，闭区间 [a, b] 其中 a <= b, 表示 实数x的集合，而 a <= x <= b。2个闭区间的交集是一组实数，要么为空集，要么为闭区间。
     * 例如，[1, 3] 和 [2, 4] 的交集为 [2, 3]
     *
     * 例子1
     * A：  |_________|     |___________|        |_______________________|    |_____|
     * B：        |_________|       |________|                      |_________|     |_____________|
     * ans:       |___|     |       |___|                           |____|    |     |
     *
     * input: A = [[0, 2], [5, 10], [13, 23], [24, 25]]
     *        B = [[1, 5], [8, 12], [15, 24], [25, 26]]
     * output:[[1, 2], [5, 5], [8, 10], [15, 23], [24, 24], [25, 25]]
     *
     * 注意：输入和所需要的输出都是区间对象组成的列表，而不是数组或者列表
     *
     * 1. 不相交情况
     * <1>  |_________|                                     <2>                  |_________|
     *     a0        a1                                                          a0        a1
     *                    |_________|                           |_________|
     *                    b0        b1                          b0        b1
     *
     * 2. 取交集的情况
     *    4种情况
     *
     * 3. i, j 更新步骤
     *    |_________|     |___________|
     *     i
     *
     *          |_________|       |________|
     *           j
     */
    static List<int[]> intervalIntersection(int[][] a, int[][] b) {
        List<int[]> res = new ArrayList<>();
        for (int i = 0, j = 0; i < a.length && j < b.length; ) {
            int a0 = a[i][0], a1 = a[i][1], b0 = b[j][0], b1 = b[j][1];
            if (b1 < a0 || a1 < b0) {
                // 无交集
            } else {
                // 有交集
                res.add(new int[]{Math.max(a0,b0), Math.min(a1,b1)});
            }

            // i, j 更新步骤
            if (b1 < a1) {
                j++;
            } else {
                i++;
            }
        }
        return res;
    }

    private static void testIntervalsProblem() {
        // 区间覆盖问题
        int[][] src1 = new int[][]{{1, 4}, {3, 6}, {2, 8}};
        int res1 = removeCoveredIntervals(src1);
        System.out.println(res1);

        // 区间合并问题
        int[][] src2 = new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        List<int[]> res2 = mergeCoveredIntervals(src2);
        for (int[] arr : res2) {
            System.out.print("[" + arr[0] + ", " + arr[1] + "], ");
        }
        System.out.println();

        // 区间交集问题
        int[][] src3_0 = new int[][]{{0, 2}, {5, 10}, {13, 23}, {24, 25}};
        int[][] src3_1 = new int[][]{{1, 5}, {8, 12}, {15, 24}, {25, 26}};
        List<int[]> res3 = intervalIntersection(src3_0, src3_1);
        for (int[] arr : res3) {
            System.out.print("[" + arr[0] + ", " + arr[1] + "], ");
        }
    }

    public static void main(String[] args) {
        testIntervalsProblem();
    }
}
