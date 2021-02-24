package me.meet.labuladong._1;

public final class LC0053 {
    private LC0053() {
    }

    /**
     * 53. 最大子序和
     * 给定一个整数数组 nums, 找到一个具有最大和的连续子数组(子数组最少包含一个元素)，返回其最大和。
     *
     * 例子1：
     * input:  [-2, 1, -3, 4, -1, 2, 1, -5, 4]
     * output: 6
     * explain:连续子数组 [4, -1, 2, 1] 的和最大，为6
     *
     *
     * 思路：
     * 1. 滑动窗口(专门处理子串/子数组问题的)，这道题目不能用滑动窗口，因为数组中的数字可以是负数。
     *    滑动窗口算法是双指针形成的窗口扫描整个数组/子串，关键是清楚的知道什么时间移动右侧指针扩大窗口，什么时间左移指针减小窗口。
     *    针对这个题目扩大窗口的时候可能遇到负数，窗口中的值可能增加或减少，这种情况不知道什么时间去收缩左侧窗口，无法求 [最大子数组和]
     *
     * 2. 动态规划，
     *    dp数组定义比较特殊，常规思路是 dp 数组： nums[0...i] 中 [最大的子数组和] 是 dp[i]; 但是这个定义 dp[i] 不能推出 dp[i+1]。
     *    因为子数组一定是连续，按照我们当前的定义，不能保证 nums[0...i] 中的最大子数组与 nums[i+1] 是相邻，
     *
     *    所以说上面定义的 dp 数组是不正确的，无法得到合适的状态转移方程。对于这类子数组问题，需要重新定义 dp 数组的含义
     *
     *    以 nums[i] 为结尾的 [最大子数组和] 为 dp[i]
     *    这种定义下，想得到整个 nums 数组的 [最大子数组和]， 不能直接返回 dp[n-1]，而需要遍历整个 dp 数组。
     *    int res = Integer.MIN_VALUE;
     *    for (int i = 0; i < n; i++) {
     *        res = Math.max(res, dp[i]);
     *    }
     *    return res;
     *
     *    依然使用数学归纳法来找状态转移关系：假设我们已经算出了 dp[i-1]，如何推导出 dp[i]
     *    dp[i] 有2种选择，要么与前面的相邻子数组连接，形成一个和更大的子数组；要么不与前面的子数组连接，自己作为一个子数组
     *    // 要么自己单独作为子数组，要么和前面的子数组合并
     *    dp[i] = Math.max(nums[i], nums[i]+dp[i-1]);
     *
     *    综上，状态转移方程
     *
     *    解法时间复杂度是 O(N)，空间复杂度也是 O(N)
     *
     */
    static int maxSubArray(int[] nums) {
        int n = nums.length;
        if (0 == n) {
            return 0;
        }
        int[] dp = new int[n];

        // base case 第一个元素前面没有子数组
        dp[0] = nums[0];

        // 状态转移方程
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
        }
        // 得到 nums 的最大子数组
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /**
     * 以上注意到dp[i]仅仅和dp[i-1]的状态有关，那么我们可以进行「状态压缩」，将空间复杂度降低
     *
     * 解法时间复杂度是 O(N)，空间复杂度也是 O(1)
     */
    static int maxSubArrayCompressSpace(int[] nums) {
        int n = nums.length;
        if (0 == n) {
            return 0;
        }

        // base case
        int dp_0 = nums[0];
        int dp_1 = 0, res = dp_0;

        for (int i = 1; i < n; i++) {
            // dp[i] = Math.max(nums[i], nums[i]+dp[i-1]);
            dp_1 = Math.max(nums[i], nums[i] + dp_0);
            dp_0 = dp_1;
            // 顺便计算最大的结果
            res = Math.max(res, dp_1);
        }
        return res;
    }

    private static void testMaxSubArray() {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int res = maxSubArray(nums);
        System.out.println(res);

        res = maxSubArrayCompressSpace(nums);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testMaxSubArray();
    }
}
