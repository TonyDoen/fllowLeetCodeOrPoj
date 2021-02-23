package me.meet.labuladong._0;

import java.util.TreeMap;

public final class _00003 {
    private _00003() {
    }

    /**
     * 1438. 绝对差不超过限制的最长连续子数组
     *
     * 给你一个整数数组 nums ，和一个表示限制的整数 limit，请你返回最长连续子数组的长度，该子数组中的任意两个元素之间的绝对差必须小于或者等于 limit 。
     * 如果不存在满足条件的子数组，则返回 0 。
     *
     * 提示：
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 0 <= limit <= 10^9
     *
     *
     * 示例 1：
     * 输入：nums = [8,2,4,7], limit = 4
     * 输出：2
     *
     * 解释：所有子数组如下：
     * [8] 最大绝对差 |8-8| = 0 <= 4.
     * [8,2] 最大绝对差 |8-2| = 6 > 4.
     * [8,2,4] 最大绝对差 |8-2| = 6 > 4.
     * [8,2,4,7] 最大绝对差 |8-2| = 6 > 4.
     * [2] 最大绝对差 |2-2| = 0 <= 4.
     * [2,4] 最大绝对差 |2-4| = 2 <= 4.
     * [2,4,7] 最大绝对差 |2-7| = 5 > 4.
     * [4] 最大绝对差 |4-4| = 0 <= 4.
     * [4,7] 最大绝对差 |4-7| = 3 <= 4.
     * [7] 最大绝对差 |7-7| = 0 <= 4.
     * 因此，满足题意的最长子数组的长度为 2 。
     *
     * 示例 2：
     * 输入：nums = [10,1,2,4,7,2], limit = 5
     * 输出：4
     * 解释：满足题意的最长子数组是 [2,4,7,2]，其最大绝对差 |2-7| = 5 <= 5 。
     *
     * 示例 3：
     * 输入：nums = [4,2,2,2,4,4,2,2], limit = 0
     * 输出：3
     *
     *
     *
     * 思路：
     * 滑动窗口
     *
     *
     */
    static int longestSubarray(int[] nums, int limit) {
        TreeMap<Integer, Integer> window = new TreeMap<>();
        int left = 0, right = 0, mayLength = 0;
        while (right < nums.length) {
            int ir = nums[right];
            right++;

            // 进行窗口内数据的一系列更新
            window.put(ir, window.getOrDefault(ir, 0) + 1);

            // 判断左侧窗口是否收缩
            while (window.lastKey() - window.firstKey() > limit) {
                int il = nums[left];
                left++;

                // 进行窗口内数据的一系列更新
                window.put(il, window.get(il) - 1);
                if (0 == window.get(il)) {
                    window.remove(il);
                }
            }
            mayLength = Math.max(mayLength, right - left);
        }
        return mayLength;
    }

    static int longestSubArray1(int[] nums, int limit) {
        int n = nums.length, h1 = 0, h2 = 0, t1 = -1, t2 = -1, left = 0, right = 0, result = 0;
        int[] maxq = new int[n], minq = new int[n];

        while (right < n) {
            while (h1 <= t1 && nums[maxq[t1]] < nums[right]) {
                t1--;
            }
            while (h2 <= t2 && nums[minq[t2]] > nums[right]) {
                t2--;
            }
            maxq[++t1] = right;
            minq[++t2] = right;
            right++;

            while (nums[maxq[h1]] - nums[minq[h2]] > limit) {
                left++;
                if (left > maxq[h1]) {
                    h1++;
                }
                if (left > minq[h2]) {
                    h2++;
                }
            }
            result = Math.max(result, right - left);
        }
        return result;
    }

    private static void testLongestSubarray() {
        int[] nums = new int[]{8, 2, 4, 7};
        int limit = 4;
        int res = longestSubarray(nums, limit);
        System.out.println(res);

        res = longestSubArray1(nums, limit);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testLongestSubarray();
    }
}
