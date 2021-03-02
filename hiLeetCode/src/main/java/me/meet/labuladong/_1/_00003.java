package me.meet.labuladong._1;

import java.util.Arrays;
import java.util.Comparator;

public final class _00003 {
    private _00003() {
    }
    /**
     * 贪心算法可以认为是动态规划算法的一个特例，相比动态规划，使用贪心算法需要满足更多的条件（贪心选择性质），但是效率比动态规划要高。
     *
     * 比如说一个算法问题使用暴力解法需要指数级时间，
     * 如果能使用动态规划消除重叠子问题，就可以降到多项式级别的时间，
     * 如果满足贪心选择性质，那么可以进一步降低时间复杂度，达到线性级别的。
     *
     * 什么是贪心选择性质呢，简单说就是：每一步都做出一个局部最优的选择，最终的结果就是全局最优。
     * 注意哦，这是一种特殊性质，其实只有一小部分问题拥有这个性质。
     *
     * 比如你面前放着 100 张人民币，你只能拿十张，怎么才能拿最多的面额？显然每次选择剩下钞票中面值最大的一张，最后你的选择一定是最优的。
     * 然而，大部分问题都明显不具有贪心选择性质。比如打斗地主，对手出对儿三，按照贪心策略，你应该出尽可能小的牌刚好压制住对方，但现实情况我们甚至可能会出王炸。
     * 这种情况就不能用贪心算法，而得使用动态规划解决，
     *
     *
     * 本文解决一个很经典的贪心算法问题 Interval Scheduling（区间调度问题）
     * 给你很多形如[start,end]的闭区间，请你设计一个算法，算出这些区间中最多有几个互不相交的区间。
     *
     * 举个例子，
     * intvs=[[1,3],[2,4],[3,6]]，这些区间最多有两个区间互不相交，即[[1,3],[3,6]]，
     * 算法应该返回 2。
     *
     * 注意边界相同并不算相交。
     *
     * 这个问题在生活中的应用广泛，比如你今天有好几个活动，每个活动都可以用区间[start,end]表示开始和结束的时间，请问你今天最多能参加几个活动
     *
     * 正确的思路其实很简单，可以分为以下三步：
     * <1> 从区间集合 intvs 中选择一个区间 x，这个 x 是在当前所有区间中结束最早的（end 最小）。
     * <2> 把所有与 x 区间相交的区间从区间集合 intvs 中删除。
     * <3> 重复步骤 1 和 2，直到 intvs 为空为止。之前选出的那些 x 就是最大不相交子集。
     *
     * 把这个思路实现成算法的话，可以按每个区间的end数值升序排序，因为这样处理之后实现步骤 1 和步骤 2 都方便很多:
     *
     * 现在来实现算法，对于步骤 1，由于我们预先按照end排了序，所以选择 x 是很容易的。关键在于，如何去除与 x 相交的区间，选择下一轮循环的 x 呢？
     * 由于我们事先排了序，不难发现所有与 x 相交的区间必然会与 x 的end相交；如果一个区间不想与 x 的end相交，它的start必须要大于（或等于）x 的end：
     *
     *
     */
    static int intervalSchedule(int[][] intvs) {
        if (0 == intvs.length) {
            return 0;
        }
        // 按照 end 升序排列
        Arrays.sort(intvs, Comparator.comparingInt(o -> o[1]));
        // 至少应该有一个区间不相交
        int count = 1;
        // 排序后，第一个区间就是 x
        int xEnd = intvs[0][1];
        for (int[] intv : intvs) {
            int start = intv[0];
            if (start >= xEnd) {
                // 更新 x
                count++;
                xEnd = intv[1];
            }
        }
        return count;
    }

    private static void testIntervalSchedule() {
        int[][] intvs = new int[][]{{1, 3}, {2, 4}, {3, 6}};
        int res = intervalSchedule(intvs);
        System.out.println(res);
    }

    /**
     * LeetCode 435 无重叠区间
     *
     * 给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互相不重叠。
     * 注意：
     * 1. 可以认为区间的终点总是大于起点。
     * 2. 区间 [1, 2] 和 [2, 3] 的边界互相接触，没有互相重叠
     *
     * 例子1：
     * input:  [[1,2],[2,3],[3,4],[1,3]]
     * output: 1
     * explain:移除 [1, 3] 后，剩下的区间没有重叠
     *
     *
     * 思路：
     * 上面已经知道 [最多有几个区间不会重叠]，那么剩下的就是 [至少需要去除的区间]
     *
     */
    static int eraseOverlapIntervals(int[][] intvs) {
        return intvs.length - intervalSchedule(intvs);
    }

    private static void testEraseOverlapIntervals() {
        int[][] intvs = new int[][]{{1, 3}, {2, 4}, {3, 6}};
        int res = eraseOverlapIntervals(intvs);
        System.out.println(res);
    }

    /**
     * LeetCode 452 最少的箭头射爆气球
     *
     * 在二维空间种有许多球形的气球。每个气球，提供的输入是水平方向上，气球直径的开始和结束坐标。
     * 由于气球是水平的，所以y坐标并部重要，因此只要知道开始和结束的x坐标就足够了。
     * 开始坐标总是小于结束坐标。平面内最多存在10^4个气球。
     *
     * 一支共建可以沿着x轴从不同点完全垂直地射出。在坐标x处射出一支箭，若有一个气球的直径的开始和结束坐标为Xstart, Xend,
     * 且 Xstart <= X <= Xend, 则该气球会被引爆。可以射出的弓箭的数量没有限制。弓箭一旦被射出，可以无限地前进。
     * 我们想找到使得所有气球全部被引爆，所需的弓箭最小数量。
     *
     * 例子1：
     * input:  [[10,16],[2,8],[1,6],[7,12]]
     * output: 2
     * explain:对于该样例，我们可以在 x = 6 (射爆[2,8],[1,6]两个气球) 和 x = 11 (射爆另外2个气球)
     *
     *
     * 思路：
     * 与上面类似，但是 区间 [1, 2] 和 [2, 3] 的边界互相接触，就会被射爆
     */
    static int findMinArrowShots(int[][] intvs) {
        if (0 == intvs.length) {
            return 0;
        }
        // 按照 end 升序排列
        Arrays.sort(intvs, Comparator.comparingInt(o -> o[1]));
        // 至少应该有一个区间不相交
        int count = 1;
        // 排序后，第一个区间就是 x
        int xEnd = intvs[0][1];
        for (int[] intv : intvs) {
            int start = intv[0];
            if (start > xEnd) { // 注意： >= 改成 >
                // 更新 x
                count++;
                xEnd = intv[1];
            }
        }
        return count;
    }

    private static void testFindMinArrowShots() {
        int[][] intvs = new int[][]{{1, 3}, {2, 4}, {3, 6}};
        int res = findMinArrowShots(intvs);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testIntervalSchedule();
        testEraseOverlapIntervals();
        testFindMinArrowShots();
    }

}
