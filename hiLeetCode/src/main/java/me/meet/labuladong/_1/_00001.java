package me.meet.labuladong._1;

import java.util.Arrays;

public class _00001 {
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
        return Math.max(/*不抢劫，去下家*/doRobI(nums, start+1), /*抢劫，去下下家*/doRobI(nums, start+2));
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

        int res = Math.max(/*不抢劫，去下家*/doRobI(nums, start+1), /*抢劫，去下下家*/doRobI(nums, start+2));

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
        // dp[i] = x => 表示 从 i 房间开始抢劫，最多强盗的钱是 x
        // bad case  => dp[n] = 0
        int[] dp = new int[n+2];
        for (int i = n-1; i >= 0; i--) {
            dp[i] = Math.max(dp[i+1], nums[i]+nums[i+2]);
        }
        return dp[0];
    }



    public static void main(String[] args) {
    }
}
