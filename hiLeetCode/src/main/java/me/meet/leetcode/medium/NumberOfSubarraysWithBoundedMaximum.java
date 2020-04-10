package me.meet.leetcode.medium;

public final class NumberOfSubarraysWithBoundedMaximum {
    private NumberOfSubarraysWithBoundedMaximum() {
    }

    /**
     * url: http://www.cnblogs.com/grandyang/p/9237967.html
     * url: https://leetcode.com/problems/number-of-subarrays-with-bounded-maximum/discuss/117585/Java-9-liner
     *
     * We are given an array A of positive integers, and two positive integers L and R (L <= R).
     * Return the number of (contiguous*, non-empty) subarrays such that the value of the maximum array element in that subarray is at least L and at most R.
     *
     * Example :
     * Input:
     * A = [2, 1, 4, 3]
     * L = 2
     * R = 3
     * Output: 3
     * Explanation: There are three subarrays that meet the requirements: [2], [2, 1], [3].
     *
     * Note:
     * L, R  and A[i] will be an integer in the range [0, 10^9].
     * The length of A will be in the range of [1, 50000].
     *
     *
     * 题意：
     * 有界限最大值的子数组数量
     *
     * 思路：
     * 既然是求子数组的问题，那么最直接，最暴力的方法就是遍历所有的子数组，然后维护一个当前的最大值，只要这个最大值在[L, R]区间的范围内，结果res自增1即可
     * 这种最原始，最粗犷的暴力搜索法，OJ不答应
     * 优化的方法是，首先，如果当前数字大于R了，那么其实后面就不用再遍历了，不管当前这个数字是不是最大值，它都已经大于R了，那么最大值可能会更大，所以没有必要再继续遍历下去了。同样的剪枝也要加在内层循环中加，当curMax大于R时，直接break掉内层循环即可
     *
     */
    static int numSubarrayBoundedMax(int[] A, int L, int R) {
        int res = 0, n = A.length;
        for (int i = 0; i < n; ++i) {
            if (A[i] > R) continue;
            int curMax = Integer.MIN_VALUE;
            for (int j = i; j < n; ++j) {
                curMax = Math.max(curMax, A[j]);
                if (curMax > R) break;
                if (curMax >= L) ++res;
            }
        }
        return res;
    }

    /**
     其实如果长度为n的数组的最大值在范围[-∞, x]内的话，其所有连续非空子数组都是符合题意的，而长度为n的数组共有n(n+1)/2个连续非空子子数组，刚好是等差数列的求和公式
     要求最大值在[L, R]范围内的子数组的个数 = 最大值在[-∞, R]范围内的子数组的个数 - 最大值在[-∞, L-1]范围内的子数组的个数
     */
    static int numSubarrayBoundedMax2(int[] A, int L, int R) {
        return count(A, R) - count(A, L - 1);
    }
    private static int count(int[] A, int bound) {
        int res = 0, cur = 0;
        for (int x : A) {
            cur = (x <= bound) ? cur + 1 : 0;
            res += cur;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] A = {2, 1, 4, 3};
        System.out.println(count(A, 3));
        System.out.println(count(A, 1));
    }
}
