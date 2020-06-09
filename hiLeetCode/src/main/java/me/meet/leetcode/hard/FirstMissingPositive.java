package me.meet.leetcode.hard;

import java.util.HashSet;

public final class FirstMissingPositive {
    private FirstMissingPositive() {}

    /**
     * Given an unsorted integer array, find the smallest missing positive integer.
     *
     * Example 1:
     * Input: [1,2,0]
     * Output: 3
     *
     * Example 2:
     * Input: [3,4,-1,1]
     * Output: 2
     *
     * Example 3:
     * Input: [7,8,9,11,12]
     * Output: 1
     * Note:
     *
     * Your algorithm should run in O(n) time and uses constant extra space.
     */

    /**
     * 这道题让我们找缺失的首个正数，
     *
     * 由于限定了O(n)的时间，所以一般的排序方法都不能用，最开始我没有看到还限制了空间复杂度，所以想到了用HashSet来解，这个思路很简单，第一遍遍历数组把所有的数都存入HashSet中，并且找出数组的最大值，下次循环从1开始递增找数字，哪个数字找不到就返回哪个数字，如果一直找到了最大的数字，则返回最大值+1，
     */
    static int getFirstMissingPositive(int[] arr) {
        int max = 0, n = arr.length;
        HashSet<Integer> set = new HashSet<>();
        for (int v : arr) {
            if (v < 0) {
                continue;
            }
            set.add(v);
            max = Math.max(max, v);
        }

        for (int i = 1; i <= max; i++) {
            if (i >= 0 && !set.contains(i)) {
                return i;
            }
        }
        return max + 1;
    }

    /**
     * O(1)的空间复杂度，所以我们需要另想一种解法，既然不能建立新的数组，那么我们只能覆盖原有数组，
     * 我们的思路是把1放在数组第一个位置nums[0]，2放在第二个位置nums[1]，即需要把nums[i]放在nums[nums[i] - 1]上，那么我们遍历整个数组，如果nums[i] != i + 1, 而nums[i]为整数且不大于n，另外nums[i]不等于nums[nums[i] - 1]的话，我们将两者位置调换，如果不满足上述条件直接跳过，最后我们再遍历一遍数组，如果对应位置上的数不正确则返回正确的数，
     */
    static int getFirstMissingPositiveConstantSpace(int[] arr) { //error
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int v = arr[i];
            while(v>0 && v <= n && arr[v-1] != v) {
                arr[i] = arr[v-1];
                arr[v-1] = v;
            }
        }
        for (int i = 1; i < n; i++) {
            if (arr[i] != i+1) return i+1;
        }
        return n+1;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7,8,9,11,12};
        System.out.println(getFirstMissingPositive(arr));
        System.out.println(getFirstMissingPositiveConstantSpace(arr));

        arr = new int[]{3,4,-1,-7,-9,1,233};
        System.out.println(getFirstMissingPositive(arr));
        System.out.println(getFirstMissingPositiveConstantSpace(arr));
    }
}
