package me.meet.leetcode.medium;

import java.util.HashSet;
import java.util.Set;

public final class SingleNumber {
    private SingleNumber() {
    }
    /**
     * Given a non-empty array of integers, every element appears twice except for one. Find that single one.
     *
     * Note:
     * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
     *
     * Example 1:
     * Input: [2,2,1]
     * Output: 1
     *
     * Example 2:
     * Input: [4,1,2,1,2]
     * Output: 4
     *
     *
     * 题意：
     * 这道题给了我们一个非空的整数数组，说是除了一个数字之外所有的数字都正好出现了两次，让我们找出这个只出现一次的数字。题目中让我们在线性的时间复杂度内求解，那么一个非常直接的思路就是使用 HashSet，利用其常数级的查找速度。
     * 
     * 
     * 思路：
     * 两个相同的数异或运算结果为0,所以数组中的元素,
     * 依次进行异或运算，最后剩下的就是只存在一次的数字
     * 
     * 数字在计算机是以二进制存储的，每位上都是0或1，
     * 如果我们把两个相同的数字异或，0与0 '异或' 是0，1与1 '异或' 也是0，那么我们会得到0。
     */
    static int singleNumber(int[] arr) {
        int res = 0;
        for (int num : arr) {
            res ^= num;
        }
        return res;
    }

    /**
     * 思路：
     * 使用 HashSet，利用其常数级的查找速度。
     */
    static int singleNumber2(int[] arr) {
        Set<Integer> st = new HashSet<>();
        for (int num : arr) {
            if (st.contains(num)) {
                st.remove(num);
            } else {
                st.add(num);
            }
        }
        for (Integer i : st) {
            return i;
        }
        return -1;
    }

    private static void testSingleNumber() {
        int[] arr = new int[]{4, 1, 2, 1, 2};
        int res = singleNumber(arr);
        System.out.println(res);

        res = singleNumber2(arr);
        System.out.println(res);
    }

    /**
     * single number ii
     * Given an array of integers, every element appears three times except for one.
     * Find that single one.
     *
     * Note:
     * Your algorithm should have a linear runtime complexity. 
     * Could you implement it without using extra memory?
     *
     *
     * 题意：
     * 这道题给了我们一个非空的整数数组，说是除了一个数字之外所有的数字都正好出现了3次，
     */
    static int singleNumberIn3(int[] arr) {
        if (null == arr || arr.length < 1) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < 32; i++) { // int 32bit
            int curBitCnt = 0;
            for (int num : arr) { // 遍历数据，处理当前第i个二进制位上的数据
                int bit = (num >> i) & 1;
                curBitCnt += bit;
            }
            curBitCnt %= 3;
            result |= curBitCnt << i; // 方案2、curBitCnt左移i位，后与result取或
            // result |= ((result >> i) | curBitCount) << i; // 方案1、result右移i位与curBitCnt取或后，再左移i位，后与result取或
        }
        return result;
    }

    static int singleNumberII1(int[] arr) {
        if (null == arr || arr.length < 1) {
            return 0;
        }
        int ones = 0; // 记录只出现过1次的bits
        int twos = 0; // 记录只出现过2次的bits
        int threes;
        for (int a : arr) {
            twos |= ones & a; // 要在更新ones前面更新twos
            ones ^= a;
            threes = ones & twos; // ones和twos中都为1即出现了3次
            ones &= ~threes; // 抹去出现了3次的bits
            twos &= ~threes;
        }
        return ones;
    }

    static int singleNumberII2(int[] A) {
        if (A == null || A.length <= 0) {
            return 0;
        }
        int low = 0;
        int high = 0;
        for (int a : A) {
            low = (low ^ a) & ~high;
            high = (high ^ a) & ~low;
        }
        return low;
    }

    private static void testSingleNumberIn3() {
        int[] arr = new int[]{4, 1, 2, 1, 2, 1, 2};
        int res = singleNumberIn3(arr);
        System.out.println(res);

        res = singleNumberII1(arr);
        System.out.println(res);

        res = singleNumberII2(arr);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testSingleNumber();
        testSingleNumberIn3();
    }
}
