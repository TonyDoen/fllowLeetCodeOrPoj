package me.meet.labuladong._0;

import me.meet.labuladong.common.TreeNode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class _00001 {
    private _00001() {
    }

    /**
     * House Robber I
     * 专业盗贼，计划打劫房屋。
     * 每间房内都有一定的现金，影响你的唯一制约因素就是相邻的房屋有相互连通的防盗系统。如果两间相邻的房屋在同一晚上被盗贼闯入，系统会自动报警。
     * 给定一个代表每个房屋存放现金的非负整数数组，计算在不触发报警的情况下，能够盗窃的最高金额。
     *
     * 例子1：
     * input: [1, 2, 3, 1]
     * output: 4
     * explain: 偷窃1号房屋(金额=1),然后偷窃3号房屋(金额=3)。偷窃最高金额=1+3=4
     *
     * 例子2：
     * input: [2, 7, 9, 3, 1]
     * output: 12
     * explain: 偷窃1号房屋(金额=2),偷窃3号房屋(金额=9)，偷窃5号房屋(金额=1)。偷窃最高金额=2+9+1=12
     *
     */
    /**
     * 思路：
     * 动态规划特征明显， 解决动态规划问题就是找「状态」和「选择」
     *
     * 「选择」
     * 假设我是强盗，从左到右走过房间，有2种选择：抢劫 或者 不抢劫
     *
     * 「状态」
     * 抢劫这个房间，就不能抢劫下个房间，只能从下下房间开始选择
     * 不抢劫这个房间，那么走到下个房间，继续上面的选择
     *
     * bad case
     * 当走过最后一个房间，就没有抢劫的，抢劫的钱是0
     *
     *                     | rob(nums[3...])           // 不抢劫 nums[2]
     * rob(nums[2...]) => {
     *                    | nums[2] + rob(nums[4...]) // 抢劫 nums[2]
     *
     * 明确了状态转移，就可以发现对于同一个 start 位置，存在重叠子问题，可以用备忘录优化
     *
     * 上面就是自顶向下的动态规划解法
     */
    static int robI(int[] nums) {
        return doRobI(nums, 0);
    }

    // 返回 nums[start...] 能抢劫的最大值
    private static int doRobI(int[] nums, int start) {
        if (start >= nums.length) {
            return 0;
        }
        return Math.max(/*不抢劫，去下家*/doRobI(nums, start + 1), /*抢劫，去下下家*/nums[start] + doRobI(nums, start + 2));
    }

    static int robIMemo(int[] nums) {
        int[] memo = new int[nums.length];
        Arrays.fill(memo, -1);

        return doRobIMemo(nums, 0, memo);
    }

    private static int doRobIMemo(int[] nums, int start, int[] memo) {
        if (start >= nums.length) {
            return 0;
        }
        // 避免重复计算
        if (-1 != memo[start]) {
            return memo[start];
        }

        int res = Math.max(/*不抢劫，去下家*/doRobI(nums, start + 1), /*抢劫，去下下家*/nums[start] + doRobI(nums, start + 2));

        // 记入备忘录
        memo[start] = res;
        return res;
    }

    /**
     *
     * 下面自底向上的动态规划解法
     *
     */
    static int robIFromBottom(int[] nums, int target) {
        int n = nums.length;
        // dp[i] = x => 表示 从 i 房间开始抢劫，最多抢劫的钱是 x
        // bad case  => dp[n] = 0
        int[] dp = new int[n+2];
        for (int i = n-1; i >= 0; i--) {
            dp[i] = Math.max(dp[i+1], nums[i]+nums[i+2]);
        }
        return dp[0];
    }

    static int robIFromBottom(int[] nums) {
        // 由上面的方法可以看出，状态转移只和 dp[i] 最近的2个状态有关，所以可以优化空间复杂度，到O(1)
        int n = nums.length;
        int dpi1 = 0, dpi2 = 0, dpi = 0; // 记录 dp[i+1], dp[i+2], dp[i]
        for (int i = n-1; i>=0; i--) {
            dpi = Math.max(dpi1, nums[i]+dpi2);
            dpi2 = dpi1;
            dpi1 = dpi;
        }
        return dpi;
    }


    /**
     * House Robber II
     * 专业盗贼，计划打劫房屋。
     * 每间房内都有一定的现金，影响你的唯一制约因素就是相邻的房屋有相互连通的防盗系统。如果两间相邻的房屋在同一晚上被盗贼闯入，系统会自动报警。
     * 给定一个代表每个房屋存放现金的非负整数数组，计算在不触发报警的情况下，能够盗窃的最高金额。
     * 且房子围城一个圈， 也就是第一个房间和最后一个房间也是相邻的，不能同时抢劫。
     *
     * 例子1：
     * input: [1, 2, 3, 1]
     * output: 4
     * explain: 偷窃1号房屋(金额=1),然后偷窃3号房屋(金额=3)。偷窃最高金额=1+3=4
     *
     * 例子2：
     * input: [2, 7, 9, 3, 1]
     * output: 12
     * explain: 偷窃1号房屋(金额=2),偷窃3号房屋(金额=9)，偷窃5号房屋(金额=1)。偷窃最高金额=2+9+1=12
     *
     */
    /**
     * 思路；
     * 1. 题意和第一题相似
     * 2. 房子围城一个圈， 也就是第一个房间和最后一个房间也是相邻的，不能同时抢劫。
     *
     * 对于首尾的房间有3种选择：<1>首尾房间都不抢劫; <2>首房间抢劫，尾房间不抢劫; <3>首房间不抢劫，尾房间抢劫
     * nums = [3, 0, 1, 2, 1, 4, 1, 2]
     * 选择<1>：   0, 1, 2, 1, 4, 1
     * 选择<2>：3, 0, 1, 2, 1, 4, 1
     * 选择<3>：   0, 1, 2, 1, 4, 1, 2
     *
     * 在求最大抢劫额，和房间内钱非负数的情况下，只需要考虑 <2>; <3> 2个选择
     */
    static int robII(int[] nums) {
        int n = nums.length;
        if (1 == n) {
            return nums[0];
        }
        return Math.max(doRobII(nums, 0, n-2), doRobII(nums, 1, n-1));
    }
    private static int doRobII(int[] nums, int start, int end) {
        int dpi1 = 0, dpi2 = 0, dpi = 0;
        for (int i = end; i >= start; i--) {
            dpi = Math.max(dpi1, nums[i]+dpi2);
            dpi2 = dpi1;
            dpi1 = dpi;
        }
        return dpi;
    }

    /**
     * House Robber III
     * 专业盗贼，计划打劫房屋。
     * 每间房内都有一定的现金，影响你的唯一制约因素就是相邻的房屋有相互连通的防盗系统。如果两间相邻的房屋在同一晚上被盗贼闯入，系统会自动报警。
     * 给定一个代表每个房屋存放现金的非负整数数组，计算在不触发报警的情况下，能够盗窃的最高金额。
     * 且房子排列在一棵二叉树节点上，相连的房子不能同时被抢劫
     *
     * 例子1：
     * input: [3, 2, 3, null, 3, null, 1]
     *          3
     *        /  \
     *      2      3
     *    /  \    /  \
     * null   3 null  1
     *
     * output: 7
     * explain: 偷窃1号房屋(金额=3),偷窃5号房屋(金额=3),偷窃7号房屋(金额=1)。偷窃最高金额= 3+3+1=7
     *
     *
     * 例子2：
     * input: [3, 4, 5, 1, 3, null, 1]
     *          3
     *        /  \
     *      4      5
     *    /  \    /  \
     *   1   3  null  1
     *
     * output: 9
     * explain: 偷窃2号房屋(金额=4),偷窃3号房屋(金额=5)。偷窃最高金额= 4+5=9
     *
     */
    /**
     * 思路：
     * 沿用上面的整体思路
     * 时间复杂度O(N); N节点个数
     */
    private static Map<TreeNode, Integer> memo = new HashMap<>();

    static int robIII(TreeNode node) {
        if (null == node) {
            return 0;
        }
        // 利用备忘录消除重叠子问题
        Integer v = memo.get(node);
        if (null != v) {
            return v;
        }

        int curVal = node.getVal();
        TreeNode curLeft = node.getLeft();
        TreeNode curRight = node.getRight();
        // 抢劫，然后去下下家
        int doIt = curVal
            + (null == curLeft ? 0 : (robIII(curLeft.getLeft()) + robIII(curLeft.getRight())))
            + (null == curRight ? 0 : (robIII(curRight.getLeft()) + robIII(curRight.getRight())));
        // 不抢劫，去下家
        int notDo = robIII(curLeft) + robIII(curRight);
        int res = Math.max(doIt, notDo);

        // 登记到备忘录
        memo.put(node, res);
        return res;
    }

    /**
     * 巧妙解法
     * 动态规划： 不同定义产生不同的解法
     *
     * 时间复杂度：O(N)
     * 空间复杂度：递归函数堆栈所需空间，不需要备忘录的额外空间
     *
     * 这个解法的实际运行时间会更快。虽然算法分析的时间复杂度相同，原因在与这个解法没有使用额外的备忘录，减少了数据操作的复杂性，所以时间会更快
     */
    static int robIII1(TreeNode node) {
        int[] res = doRobIII1(node);
        return Math.max(res[0], res[1]);
    }
    // 返回一个大小为2的数组arr
    // arr[0] 表示不抢劫node 的话，得到的最大钱数
    // arr[1] 表示抢劫node 的话，得到的最大钱数
    private static int[] doRobIII1(TreeNode node) {
        if (null == node) {
            return new int[]{0, 0};
        }
        int[] left = doRobIII1(node.getLeft());
        int[] right = doRobIII1(node.getRight());
        // 抢劫，下家就不能抢劫了
        int doIt = node.getVal()+left[0]+right[0];
        // 不抢劫，下家可以抢劫，也可以不抢劫，看收益
        int noDo = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return new int[]{noDo, doIt};
    }

    private static void testRob() {
        int[] rooms = new int[]{2, 7, 9, 3, 1};
        int res10 = robI(rooms);
        System.out.println(res10);

        int res11 = robIMemo(rooms);
        System.out.println(res11);

        int res20 = robII(rooms);
        System.out.println(res20);

        TreeNode node = TreeNode.prepareTree3();
        int res = robIII(node);
        System.out.println(res);

        int res1 = robIII1(node);
        System.out.println(res1);
    }

    public static void main(String[] args) {
        testRob();
    }
}
