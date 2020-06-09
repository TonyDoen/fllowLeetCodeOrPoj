package me.meet.leetcode.medium;

public final class MaximumSumCircularSubarray {
    /**
     * Given a circular array C of integers represented by A, find the maximum possible sum of a non-empty subarray of C.
     * Here, a circular array means the end of the array connects to the beginning of the array.  (Formally, C[i] = A[i] when 0 <= i < A.length, and C[i+A.length] = C[i] when i >= 0.)
     * Also, a subarray may only include each element of the fixed buffer A at most once.  (Formally, for a subarray C[i], C[i+1], ..., C[j], there does not exist i <= k1, k2 <= j with k1 % A.length = k2 % A.length.)
     * 
     * Example 1:
     * Input: [1,-2,3,-2]
     * Output: 3
     * Explanation: Subarray [3] has maximum sum 3
     * 
     * Example 2:
     * Input: [5,-3,5]
     * Output: 10 Explanation: Subarray [5,5] has maximum sum 5 + 5 = 10
     * 
     * Example 3:
     * Input: [3,-1,2,-1]
     * Output: 4
     * Explanation: Subarray [2,-1,3] has maximum sum 2 + (-1) + 3 = 4
     * 
     * Example 4:
     * Input: [3,-2,2,-3]
     * Output: 3 Explanation: Subarray [3] and [3,-2,2] both have maximum sum 3
     * 
     * Example 5:
     * Input: [-2,-3,-1]
     * Output: -1 Explanation: Subarray [-1] has maximum sum -1
     * 
     * Note:
     * -30000 <= A[i] <= 30000
     * 1 <= A.length <= 30000
     * 
     * 
     * 题意： 环形子数组的最大和
     * 这道题让求环形子数组的最大和，对于环形数组，我们应该并不陌生，之前也做过类似的题目 Circular Array Loop，就是说遍历到末尾之后又能回到开头继续遍历。
     * 假如没有环形数组这一个条件，其实就跟之前那道 Maximum Subarray 一样，解法比较直接易懂。这里加上了环形数组的条件，难度就增加了一些，需要用到一些 trick。
     * 既然是子数组，则意味着必须是相连的数字，而由于环形数组的存在，说明可以首尾相连，这样的话，最长子数组的范围可以有两种情况，
     * 
     * 一种是正常的，数组中的某一段子数组，
     * 另一种是分为两段的，即首尾相连的，可以参见 大神 lee215 的帖子 中的示意图。
     * 
     * 对于第一种情况，其实就是之前那道题 Maximum Subarray 的做法，
     * 对于第二种情况，需要转换一下思路，除去两段的部分，中间剩的那段子数组其实是和最小的子数组，只要用之前的方法求出子数组的最小和，用数组总数字和一减，同样可以得到最大和。两种情况的最大和都要计算出来，取二者之间的较大值才是真正的和最大的子数组。
     * 但是这里有个 corner case 需要注意一下，假如数组中全是负数，那么和最小的子数组就是原数组本身，则求出的差值是0，而第一种情况求出的和最大的子数组也应该是负数，那么二者一比较，返回0就不对了，所以这种特殊情况需要单独处理一下，
     */
    static int maxSubarraySumCircular(int[] arr) {
        int sum = 0, mn = Integer.MAX_VALUE, mx = Integer.MIN_VALUE, curMax = 0, curMin = 0;
        for (int num : arr) {
            curMin = Math.min(curMin + num, num);
            mn = Math.min(mn, curMin);
            curMax = Math.max(curMax + num, num);
            mx = Math.max(mx, curMax);
            sum += num;
        }
        return (sum - mn == 0) ? mx : Math.max(mx, sum - mn);
    }

    static int maxSubarraySumCircularQuick(int[] arr) {
        int s = 0; // sum(arr)
        for (int x : arr) {
            s += x;
        }

        int ans1 = kadane(arr, 0, arr.length - 1, 1);
        int ans2 = s + kadane(arr, 0, arr.length - 1, -1);
        if (0 == ans2) {
            ans2 = arr[0];
        }
        return Math.max(ans1, ans2);
    }

    private static int kadane(int[] arr, int i, int j, int sign) {
        // the maximum non-empty subarray for array
        // [sign * arr[i], sign * arr[i+1], ..., sign * arr[j]]
        int ans = Integer.MIN_VALUE, cur = Integer.MIN_VALUE;
        for (int k = i; k <= j; k++) {
            cur = sign * arr[k] + Math.max(cur, 0);
            ans = Math.max(ans, cur);
        }
        return ans;
    }

    private static void testMaxSubarraySumCircular() {
        int[] src = new int[]{1, -2, 3, -2};
        int res = maxSubarraySumCircular(src);
        System.out.println(res);

        int res2 = maxSubarraySumCircularQuick(src);
        System.out.println(res2);
    }

    public static void main(String[] args) {
        testMaxSubarraySumCircular();
    }
}
