package me.meet.labuladong._1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class _00004 {
    private _00004() {
    }

    /**
     * two sum
     * 
     * LeetCode's two sum 要求返回索引，这里我们来返回元素的值
     * 
     * 如果假设输入一个数组 nums 和一个目标和target, 请你返回 nums 中能够凑出 target 的两个元素的值，
     * 如输入 nums = [5, 3, 1, 6]; target = 9; 那么算法返回2个元素 [3, 6]; 可以假设只有且仅有一对元素可以凑出 target。
     * 
     * 先对 nums 排序, 然后用 [双指针技巧] 从两端相向而行
     */
    static int[] twoSum(int[] nums, int target) {
        // 先对数组排序
        Arrays.sort(nums);
        // 左右指针
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int sum = nums[lo] + nums[hi];
            // 根据 sum 和 target 的比较，移动左右指针
            if (sum < target) {
                lo++;
            } else if (sum > target) {
                hi--;
            } else {
                return new int[]{nums[lo], nums[hi]};
            }
        }
        return new int[]{};
    }

    /**
     * two sum
     * LeetCode's two sum 要求返回索引
     * nums 中可能有多对元素和等于 target 请你的算法返回所有和 target 的元素对, 其中不能重复
     */

    // 这样会有重复的元素对
    static List<int[]> twoSumDuplicateTarget(int[] nums, int target) {
        // 先对数组排序
        Arrays.sort(nums);
        List<int[]> res = new ArrayList<>();
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int sum = nums[lo] + nums[hi];
            // 根据 sum 和 target 的比较, 移动左右指针
            if (sum < target) {
                lo++;
            } else if (sum > target) {
                hi--;
            } else {
                res.add(new int[]{nums[lo], nums[hi]});
                lo++;
                hi--;

            }
        }
        return res;
    }

    // 这样没有重复的元素对
    static List<int[]> twoSumSingleTarget(int[] nums, int target) {
        // 先对数组排序
        Arrays.sort(nums);
        List<int[]> res = new ArrayList<>();
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int sum = nums[lo] + nums[hi], left = nums[lo], right = nums[hi];
            if (sum < target) {
                while (lo < hi && nums[lo] == left) {
                    lo++;
                }
            } else if (sum > target) {
                while (lo < hi && nums[hi] == right) {
                    hi--;
                }
            } else {
                res.add(new int[]{left, right});
                while (lo < hi && nums[lo] == left) {
                    lo++;
                }
                while (lo < hi && nums[hi] == right) {
                    hi--;
                }
            }
        }
        return res;
    }

    private static void testTwoSum() {
        int[] nums1 = new int[]{5, 3, 1, 6};
        int target1 = 9;
        int[] res1 = twoSum(nums1, target1);
        for (int i : res1) {
            System.out.print(i + ", ");
        }
        System.out.println();

        int[] nums2 = new int[]{5, 3, 1, 3, 1, 3, 1, 3, 1, 6, 6, 6, 6, 6};
        int target2 = 9;
        List<int[]> res2 = twoSumDuplicateTarget(nums2, target2);
        for (int[] pair : res2) {
            if (pair.length < 2) {
                continue;
            }
            System.out.println("pair{" + pair[0] + ", " + pair[1] + "}");
        }

        int[] nums3 = new int[]{5, 3, 1, 3, 1, 3, 1, 3, 1, 6, 6, 6, 6, 6};
        int target3 = 9;
        List<int[]> res3 = twoSumSingleTarget(nums3, target3);
        for (int[] pair : res3) {
            if (pair.length < 2) {
                continue;
            }
            System.out.println("pair{" + pair[0] + ", " + pair[1] + "}");
        }

    }

    public static void main(String[] args) {
        testTwoSum();
    }
}
