package me.meet.leetcode.medium;

public final class NumberOfLongestIncreasingSubsequence {
    private NumberOfLongestIncreasingSubsequence() {
    }
    /**
     * Number of Longest Increasing Subsequence
     * Given an unsorted array of integers, find the number of longest increasing subsequence.
     *
     * Example 1:
     * Input: [1,3,5,4,7]
     * Output: 2
     * Explanation: The two longest increasing subsequence are [1, 3, 4, 7] and [1, 3, 5, 7].
     *
     * Example 2:
     * Input: [2,2,2,2,2]
     * Output: 5
     * Explanation: The length of longest continuous increasing subsequence is 1, and there are 5 subsequences' length is 1, so output 5.
     *
     * Note: Length of the given array will be not exceed 2000 and the answer is guaranteed to be fit in 32-bit signed int.
     *
     *
     * 题意：
     * 最长递增序列的个数
     * 这道题给了我们一个数组，让求最长递增序列的个数，题目中的两个例子也很好的说明了问题。那么对于这种求极值的问题，直觉告诉我们应该要使用动态规划 Dynamic Programming 来做。其实这道题在设计 DP 数组的时候有个坑，如果将 dp[i] 定义为到i位置的最长子序列的个数的话，则递推公式不好找。但是如果将 dp[i] 定义为以 nums[i] 为结尾的递推序列的个数的话，再配上这些递推序列的长度，将会比较容易的发现递推关系。这里用 len[i] 表示以 nums[i] 为结尾的递推序列的长度，用 cnt[i] 表示以 nums[i] 为结尾的递推序列的个数，初始化都赋值为1，只要有数字，那么至少都是1。然后遍历数组，对于每个遍历到的数字 nums[i]，再遍历其之前的所有数字 nums[j]，当 nums[i] 小于等于 nums[j] 时，不做任何处理，因为不是递增序列。反之，则判断 len[i] 和 len[j] 的关系，如果 len[i] 等于 len[j] + 1，说明 nums[i] 这个数字可以加在以 nums[j] 结尾的递增序列后面，并且以 nums[j] 结尾的递增序列个数可以直接加到以 nums[i] 结尾的递增序列个数上。如果 len[i] 小于 len[j] + 1，说明找到了一条长度更长的递增序列，那么此时将 len[i] 更新为 len[j]+1，并且原本的递增序列都不能用了，直接用 cnt[j] 来代替。在更新完 len[i] 和 cnt[i] 之后，要更新 mx 和结果 res，如果 mx 等于 len[i]，则把 cnt[i] 加到结果 res 之上；如果 mx 小于 len[i]，则更新 mx 为 len[i]，更新结果 res 为 cnt[i]，
     * 
     * 思路： 
     * 动态规划-> dp[i]表示第i个元素的最长序列组合
     * 1、从后往前求每个元素的最长序列组合
     * 2、dp[i]即为dp[i+1],dp[i+2]...判断nums[i]是否能加入序列中后，取最大值即可
     *
     */
    static int findNumberOfLIS(int[] arr) {
        int res = 0, mx = 0, length = arr.length;
        int[] len = new int[length], cnt = new int[length];
        for (int i = 0; i < length; i++) {
            len[i] = 1;
            cnt[i] = 1;
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] <= arr[j]) {
                    continue;
                }
                if (len[i] == len[j] + 1) {
                    cnt[i] += cnt[j];
                } else if (len[i] < len[j] + 1) {
                    len[i] = len[j] + 1;
                    cnt[i] = cnt[j];
                }
            }
            if (mx == len[i]) {
                res += cnt[i];
            } else if (mx < len[i]) {
                mx = len[i];
                res = cnt[i];
            }
        }
        return res;
    }

    private static void testFindNumberOfLIS() {
        int[] arr = new int[]{1, 3, 5, 4, 7};
        int res = findNumberOfLIS(arr);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testFindNumberOfLIS();
    }
}
