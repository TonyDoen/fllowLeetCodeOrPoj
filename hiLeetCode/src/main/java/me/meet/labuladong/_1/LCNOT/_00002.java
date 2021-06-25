package me.meet.labuladong._1.LCNOT;

import java.util.Arrays;

public final class _00002 {
    private _00002() {
    }
    /**
     * [动态规划详解]，了解了动态规划的套路，也不会写状态转移方程，没有思路，怎么办？
     * 本文就借助「最长递增子序列」来讲一种设计动态规划的通用技巧：数学归纳思想。
     *
     * 最长递增子序列（Longest Increasing Subsequence，简写 LIS）是比较经典的一个问题，
     * 比较容易想到的是动态规划解法，时间复杂度 O(N^2)，我们借这个问题来由浅入深讲解如何写动态规划。
     *
     * 比较难想到的是利用二分查找，时间复杂度是 O(NlogN)，我们通过一种简单的纸牌游戏来辅助理解这种巧妙的解法。
     *
     *
     * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
     * 例子1：
     * input:  [10, 9, 2, 5, 3, 7, 101, 18]
     * output: 4
     * explain:最长的上升子序列是 [2, 3, 7, 101], 它的长度是4
     *
     * 说明：
     * 1> 可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
     * 2> 你算法的时间复杂度应该为 O(N^2)
     *
     * 进阶：
     * 你能将算法的时间复杂度降低到 O(N*logN)
     *
     * 注意：
     * [子序列] 和 [子串] 这两个名词的区别，子串(substring)一定是连续的，而子序列(subsequence)不一定是连续的。
     *
     *
     * 思路：
     * 动态规划的核心设计思想是数学归纳法。
     * [数学归纳法] 比如我们想证明一个数学结论，那么我们先假设这个结论在 k<n 时成立，然后想办法证明 k=n 的时候此结论也成立。如果能够证明出来，那么就说明这个结论对于 k 等于任何数都成立。
     *
     * 类似的，我们设计动态规划算法，需要一个 dp 数组，可以假设 dp[0...i−1] 都已经被算出来了，然后问自己：怎么通过这些结果算出dp[i] ?
     *
     * 拿最长递增子序列这个问题举例，
     * 首先要定义清楚 dp 数组的含义，即 dp[i] 的值到底代表着什么？
     *
     * 我们的定义是这样的：dp[i] 表示以 nums[i] 这个数结尾的最长递增子序列的长度。
     * index => 0  1  2  3  4
     * nums  => 1, 4, 3, 4, 2
     * dp    => ?
     *          1  2  2  3  2
     *
     * 根据这个定义，我们的最终结果（子序列的最大长度）应该是 dp 数组中的最大值。
     * 也许会问，刚才这个过程中每个 dp[i] 的结果是我们肉眼看出来的，我们应该怎么设计算法逻辑来正确计算每个 dp[i] 呢？
     *
     * 这就是动态规划的重头戏了，要思考如何进行状态转移，这里就可以使用数学归纳的思想：
     * 假设已经知道 dp[0...3] 的所有结果，怎么推出 dp[4]。
     * 根据定义 dp[i] 表示以 nums[i] 这个数结尾的最长递增子序列的长度。num[4] = 2，递增子序列，找到比 num[4] = 2 小的子序列，长度+1， 可能有多个子序列，求最大的。
     *                      i
     *          j ->  j ->  j
     * index => 0  1  2  3  4
     * nums  => 1, 4, 3, 4, 2
     * dp    => 1  2  2  3  ?
     *
     * for (int j = 0; j < i; j++) {
     *     if (nums[j] < nums[i]) {
     *         dp[i] = Math.max(dp[i], dp[j] + 1);
     *     }
     * }
     *
     * 可以推出 dp[4]。 那么任意 dp[i] 也可以算出来。
     * for (int i = 0; i < nums.length; i++) {
     *     for (int j = 0; j < i; j++) {
     *         if (nums[j] < nums[i]) {
     *             dp[i] = Math.max(dp[i], dp[j] + 1);
     *         }
     *     }
     * }
     * 算法的时间复杂度应该为 O(N^2)
     *
     */

    static int lengthOfLIS0(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        // dp 数组全都初始化1
        Arrays.fill(dp, 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int result = 0;
        for (int i = 0; i < n; i++) {
            result = Math.max(result, dp[i]);
        }
        return result;
    }

    /**
     * 二分查找解法
     * 这个解法的时间复杂度会将为 O(NlogN)。
     * 根据题目的意思，我都很难想象这个问题竟然能和二分查找扯上关系。其实最长递增子序列和一种叫做 patience game 的纸牌游戏有关，甚至有一种排序方法就叫做 patience sorting（耐心排序）。
     *
     * 跳过所有数学证明，通过一个简化的例子来理解一下思路。
     * 首先，给你一排扑克牌，我们像遍历数组那样从左到右一张一张处理这些扑克牌，最终要把这些牌分成若干堆。
     * 6, 3, 5, 10, J, 2, 9, A, K, 7, 4, 8, Q (认为A最大，而不是1，去掉大小鬼(王))
     * 处理这些扑克牌要遵循以下规则：
     * <1> 只能把点数小的牌压到点数比它大的牌上。
     * <2> 如果当前牌点数较大没有可以放置的堆，则新建一个堆，把这张牌放进去。
     * <3> 如果当前牌有多个堆可供选择，则选择最左边的堆放置。
     *
     * 上述的扑克牌按照规则最终会被分成这样 5 堆：
     * 6  5  10  J  A
     * 3  4  9   8  K
     * 2     7      Q (堆顶)
     *
     * 这时可以注意到 为什么遇到多个可选择堆的时候要放到最左边的堆上呢？因为这样可以保证牌堆顶的牌有序 [2, 4, 7, 8, Q]
     * 按照上述规则执行，可以算出最长递增子序列，牌的堆数就是我们想求的最长递增子序列的长度，证明略。 最长递增子序列[3, 5, 7, 8, Q]
     * 只要把处理扑克牌的过程编程写出来即可。每次处理一张扑克牌要找一个合适的牌放堆顶。 牌堆顶的牌有序，这就能用到二分查找
     *
     * 这个解法确实很难想到。首先涉及数学证明，谁能想到按照这些规则执行，就能得到最长递增子序列
     * 其次还有二分查找的运用，要是对二分查找的细节不清楚，给了思路也很难写对。
     * 这个方法作为思维拓展好了。
     * 但动态规划的设计方法应该完全理解：假设之前的答案已知，利用数学归纳的思想正确进行状态的推演转移，最终得到答案。
     *
     * 算法的时间复杂度应该为 O(NlogN)
     *
     *
     */
    static int lengthOfLIS1(int[] nums) {
        int piles = 0/*牌堆初始化为0*/, n = nums.length;
        int[] top = new int[n];
        for (int p : nums) { // 需要处理的牌
            int left = 0, right = piles;
            // 搜索左侧边界的二分查找; 二分查找插入位置
            while (left < right) {
                int mid = (left + right) / 2;
                if (top[mid] >= p) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            // 没找到合适的牌堆，新建一堆
            if (left == piles) {
                piles++;
            }
            // 把这张牌放到牌堆顶
            top[left] = p;
        }
        // 牌堆数就是LIS长度
        return piles;
    }

    private static void testLengthOFLIS() {
        int[] nums = new int[]{1, 4, 3, 4, 2};
        int res = lengthOfLIS0(nums);
        System.out.println(res);

        res = lengthOfLIS1(nums);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testLengthOFLIS();
    }
}