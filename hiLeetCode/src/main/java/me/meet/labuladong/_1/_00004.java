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
     * LeetCode's two sum 要求返回索引，这里我们来返回元素的值
     * nums 中可能有多对元素和等于 target 请你的算法返回所有和 target 的元素对, 其中不能重复
     *
     * 双指针操作的时间复杂度 O(N)
     * 排序的时间复杂度 O(N*logN)
     *
     * 整体时间复杂度 O(N*logN)
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

    /**
     * 3 sum
     *
     * LeetCode 三数之和
     * 给你一个包含n个整数的数组 nums, 判断 nums 中是否存在3个元素 a, b, c, 使得 a+b+c=0? 请找出所有满足条件且不重复的 三元组。
     * 注意： 答案中不可以包含重复的三元组
     *
     * 例子1：
     * 给定数组 nums=[-1, 0, 1, 2, -1, -4]
     * 满足要求的三元组集合
     * [[-1, 0, 1],
     *  [-1, -1, 2]
     * ]
     *
     * 思路：
     * 在整数的数组 nums 中，找到和为0的三元组，
     * 泛化一下，找到和为 target 的三元组,
     * 思路穷举, 找和为 target 的三元组, 第一个数字在 nums[i] 中的每一个元素
     * 第二个数字是 和为 target - nums[i] 的2个数字 复用上面的twoSum 方法
     *
     * 不重复的 三元组 重点在第一个元素不重复，后面2个数字 有上面的twoSum 不重复方法保证
     *
     * 时间复杂度
     * 排序时间复杂度 O(N*logN)
     * twoSumStartTarget (双指针)的时间复杂度 O(N)
     * threeSumTarget 的时间复杂度  O(N*logN + N^2) = O(N^2)
     *
     */
    // 这样没有重复的元素对
    private static List<int[]> twoSumStartTarget(int[] nums, int start, int target) {
//        // 先对数组排序
//        Arrays.sort(nums);
        List<int[]> res = new ArrayList<>();
        int lo = start, hi = nums.length - 1;
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

    static List<int[]> threeSumTarget(int[] nums, int target) {
        Arrays.sort(nums);

        List<int[]> res = new ArrayList<>();
        // 穷举 three sum 的第一个数
        for (int i = 0, n = nums.length; i < n; i++) {
            // 对 target - nums[i] 计算 two sum
            List<int[]> tmp = twoSumStartTarget(nums, i + 1, target - nums[i]);
            // 如果存在满足条件的二元组， 再加上nums[i] 就是结果三元组
            for (int[] t : tmp) {
                if (t.length < 2) {
                    continue;
                }
                res.add(new int[]{nums[i], t[0], t[1]});
            }
            // 跳过第一个数字重复的情况，
            while (i < n - 1 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        return res;
    }

    private static void testThreeSum() {
        int[] nums1 = new int[]{-1, 0, 1, 2, -1, -4};
        int target1 = 0;

        List<int[]> res = threeSumTarget(nums1, target1);
        for (int[] t : res) {
            if (t.length < 3) {
                continue;
            }
            System.out.println("triple{" + t[0] + ", " + t[1] + ", " + t[2] + "}");
        }
    }

    /**
     * 4 sum
     * 给你一个包含n个整数的数组 nums, 判断 nums 中是否存在4个元素 a, b, c, d 使得 a+b+c+d=target? 请找出所有满足条件且不重复的 四元组。
     * 注意： 答案中不可以包含重复的四元组
     *
     * 例子1：
     * 给定数组 nums=[-1, 0, 1, 0, -2, 2]; target = 0
     * 满足要求的三元组集合
     * [[-1, 0, 0, 1],
     *  [-2, -1, 1, 2],
     *  [-2, 0, 0, 2]
     * ]
     *
     * 时间复杂度 O(N^3)
     *
     */
    private static List<int[]> threeSumStartTarget(int[] nums, int start, int target) {
//        Arrays.sort(nums);

        List<int[]> res = new ArrayList<>();
        // 穷举 three sum 的第一个数
        for (int i = start, n = nums.length; i < n; i++) {
            // 对 target - nums[i] 计算 two sum
            List<int[]> tmp = twoSumStartTarget(nums, i + 1, target - nums[i]);
            // 如果存在满足条件的二元组， 再加上nums[i] 就是结果三元组
            for (int[] t : tmp) {
                if (t.length < 2) {
                    continue;
                }
                res.add(new int[]{nums[i], t[0], t[1]});
            }
            // 跳过第一个数字重复的情况，
            while (i < n - 1 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        return res;
    }

    static List<int[]> fourSumTarget(int[] nums, int target) {
        Arrays.sort(nums);
        List<int[]> res = new ArrayList<>();
        // 穷举 three sum 的第一个数
        for (int i = 0, n = nums.length; i < n; i++) {
            // 对 target - nums[i] 计算 three sum
            List<int[]> tmp = threeSumStartTarget(nums, i + 1, target - nums[i]);
            for (int[] t : tmp) {
                if (t.length < 3) {
                    continue;
                }
                res.add(new int[]{nums[i], t[0], t[1], t[2]});
            }
            // 跳过第一个数字重复的情况，
            while (i < n - 1 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        return res;
    }

    private static void testFourSum() {
        int[] nums1 = new int[]{-1, 0, 1, 0, -2, 2};
        int target1 = 0;

        List<int[]> res = fourSumTarget(nums1, target1);
        for (int[] t : res) {
            if (t.length < 3) {
                continue;
            }
            System.out.println("four tuple{" + t[0] + ", " + t[1] + ", " + t[2] + ", " + t[3] + "}");
        }
    }

    /**
     * 100 sum
     *
     * 观察上面的解法，都遵从相同的模式
     */
    private static List<List<Integer>> nSumStartTarget(int[] nums, int n, int start, int target) {
        // 默认 nums 已经排序
        int length = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        // 至少是 two sum; 且数组大小不小于 n
        if (n < 2 || length < n) {
            return res;
        }
        if (2 == n) {
            // two sum 是 base case.
            // 双指针操作
            int lo = start, hi = length - 1;
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
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(left);
                    tmp.add(right);

                    res.add(tmp);
                    while (lo < hi && nums[lo] == left) {
                        lo++;
                    }
                    while (lo < hi && nums[hi] == right) {
                        hi--;
                    }
                }
            }
        } else {
            // n > 2 时, 递归计算 n-1 sum 的结果
            for (int i = start; i < length; i++) {
                List<List<Integer>> tmp = nSumStartTarget(nums, n - 1, i + 1, target - nums[i]);
                for (List<Integer> t : tmp) {
                    // n-1 sum 加上 nums[i] 就是 n sum
                    t.add(nums[i]);
                    res.add(t);
                }

                // 跳过数字重复的情况，
                while (i < length - 1 && nums[i] == nums[i + 1]) {
                    i++;
                }
            }
        }
        return res;
    }

    static List<List<Integer>> nSumTarget(int[] nums, int n, int target) {
        Arrays.sort(nums);
        return nSumStartTarget(nums, n, 0, target);
    }

    private static void testNSum() {
        int[] nums1 = new int[]{-1, 0, 1, 0, -2, 2, 0, 1, 0, -2, 2};
        int n1 = 7;
        int target1 = 0;
        List<List<Integer>> res1 = nSumTarget(nums1, n1, target1);
        System.out.println(n1 + " tuple" + res1);
    }

    public static void main(String[] args) {
        testTwoSum();
        testThreeSum();
        testFourSum();
        testNSum();
    }
}
