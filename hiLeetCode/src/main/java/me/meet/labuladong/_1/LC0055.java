package me.meet.labuladong._1;

import java.util.Arrays;

public final class LC0055 {
    private LC0055() {
    }

    /**
     *
     * 贪心算法可以理解为一种特殊的动态规划问题，拥有一些更特殊的性质，可以进一步降低动态规划算法的时间复杂度。
     *
     * LeetCode 第 55 题  跳跃游戏 I
     * 给定一个非负整数数组，最初位于数组的第一个位置。数组中的每个元素代表你在该位置可以跳跃的最大长度。判断是否能够到达最后一个位置
     * 例子1：
     * input:  [2,3,1,1,4]
     * output: true
     * explain:我们可以先跳1步，从位置0到达位置1，然后再从位置1跳3步到达最后一个位置。
     *
     * 例子2：
     * input:  [3,2,1,0,4]
     * output: false
     * explain:无论怎么做，你总会到达索引为3的位置，但是该位置的最大跳跃长度是0，所以你永远不能到达最后一个位置。
     *
     *
     * 读者有没有发现，有关动态规划的问题，大多是让你求最值的
     * 比如最长子序列，最小编辑距离，最长公共子串等等等。这就是规律，因为动态规划本身就是运筹学里的一种求最值的算法。
     *
     * 那么贪心算法作为特殊的动态规划也是一样，一般也是让你求个最值。
     * 这道题表面上不是求最值，但是可以改一改： 请问通过题目中的跳跃规则，最多能跳多远？如果能够越过最后一格，返回 true，否则返回 false。
     *
     * 所以说，这道题肯定可以用动态规划求解的。但是由于它比较简单，下一道题再用动态规划和贪心思路进行对比，现在直接上贪心的思路：
     * 每一步都计算一下从当前位置最远能够跳到哪里，然后和一个全局最优的最远位置farthest做对比，通过每一步的最优解，更新全局最优解，这就是贪心。
     *
     */
    static boolean canJump(int[] nums) {
        int n = nums.length, farthest = 0;
        for (int i = 0; i < n - 1; i++) {
            // 不断计算能跳到的最远距离
            farthest = Math.max(farthest, i + nums[i]);
            // 可能碰到了 0, 卡住跳不动了
            if (farthest <= i) {
                return false;
            }
        }
        return farthest >= n - 1;
    }

    /**
     * LeetCode 第 45 题  Jump Game II
     *
     * 给定一个非负数整数数组，你最初位于数组的第一个位置。数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
     *
     * 例子1：
     * input:  [2,3,1,1,4]
     * output: 2
     * explain:跳到最后一个位置的最小跳跃数是2；从下标为0跳到下标为1的位置，跳2步，然后跳2步到达数组的最后一个位置
     *
     * 说明：
     * 假设你总是可以到达数组的最后一个位置。
     *
     *
     * 思路：
     * 现在的问题是，保证你一定可以跳到最后一格，请问你最少要跳多少次，才能跳过去？
     *
     * 动态规划的思路，采用自顶向下的递归动态规划，定义一个dp函数
     * // 定义： 从索引 p 跳到最后一格，至少需要 dp(nums, p) 步骤
     * int dp(int[] nums, int p)
     *
     * 我们想求解结果就是 dp(nums, 0); base case 就是当p 超过最后一格时，不需要跳跃。
     * if(p >= nums.length-1) {
     *     return 0;
     * }
     *
     * 可以暴力穷举所有可能的跳法，通过备忘录 memo 消除重叠子问题，取其中的最小值为答案
     *
     * 算法的时间复杂度是 递归深度 × 每次递归需要的时间复杂度，即 O(N^2)
     *
     */
    private static int[] memo;

    static int jump(int[] nums) {
        // 备忘录初始化为 nums.length; 相当于 INT_MAX
        // 因为从0跳到n-1; 最多需要n-1步
        memo = new int[nums.length];
        Arrays.fill(memo, nums.length);

        return helpJump(nums, 0);
    }

    private static int helpJump(int[] nums, int p) {
        int n = nums.length;
        // base case
        if (p >= n - 1) {
            return 0;
        }
        // 子问题已经计算过了
        if (n != memo[p]) {
            return memo[p];
        }

        int steps = nums[p];
        // 可以选择跳1步，2步...
        for (int i = 1; i <= steps; i++) {
            // 穷举每一个选择；计算每一个子问题的结果
            int subProblem = helpJump(nums, p + i);
            // 取其中最小的作为最终结果
            memo[p] = Math.min(memo[p], subProblem + 1);
        }
        return memo[p];
    }

    /**
     * 贪心算法比动态规划多了一个性质：贪心选择性质。
     *
     * 刚才的动态规划思路，不是要穷举所有子问题，然后取其中最小的作为结果吗？核心的代码框架是这样：
     *     int steps = nums[p];
     *     // 你可以选择跳 1 步，2 步...
     *     for (int i = 1; i <= steps; i++) {
     *         // 计算每一个子问题的结果
     *         int subProblem = dp(nums, p + i);
     *         res = min(subProblem + 1, res);
     *     }
     * for 循环中会陷入递归计算子问题，这是动态规划时间复杂度高的根本原因。
     * 但是，真的需要「递归地」计算出每一个子问题的结果，然后求最值吗？
     * 直观地想一想，似乎不需要递归，只需要判断哪一个选择最具有「潜力」即可：
     *
     * index: 0  1  2  3  4  5  6  7
     * nums:  3  1  4  2  ...
     *
     * 观察上面的例子，显然从 index=0的位置，应该跳跃到index=2，即4所在的位置。这是index=0位置能跳跃到的潜力最大的位置，即index=2时可跳跃区域覆盖范围最大的位置。index=2是最优选择。
     *
     * 上面就是贪心选择性质。
     * 我们不需要递归地计算出所有选择的具体结果然后比较求最值，而只需要作出那个最有潜力，最优选择。
     *
     */
    static int jump0(int[] nums) {
        int n = nums.length, end = 0, farthest = 0, jumps = 0;
        for (int i = 0; i < n - 1; i++) {
            farthest = Math.max(nums[i] + i, farthest);
            if (i == end) {
                jumps++;
                end = farthest;
            }
        }
        return jumps;
    }

    private static void testCanJump() {
        int[] nums = new int[]{3, 2, 1, 0, 4};
        boolean res = canJump(nums);
        System.out.println(res);

        nums = new int[]{2, 3, 1, 1, 4};
        int res1 = jump(nums);
        System.out.println(res1);

        res1 = jump0(nums);
        System.out.println(res1);
    }

    public static void main(String[] args) {
        testCanJump();
    }
}
