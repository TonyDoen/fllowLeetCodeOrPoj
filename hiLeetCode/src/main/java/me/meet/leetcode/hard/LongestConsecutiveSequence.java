package me.meet.leetcode.hard;

import java.util.HashMap;
import java.util.Map;

public final class LongestConsecutiveSequence {
    private LongestConsecutiveSequence() {
    }
    /**
     * Given an unsorted array of integers, find the length of 
     * the longest consecutive elements sequence.
     *
     * For example,
     * Given[100, 4, 200, 1, 3, 2],
     * The longest consecutive elements sequence is[1, 2, 3, 4]. 
     * Return its length:4.
     *
     * Your algorithm should run in O(n) complexity.
     */
    /**
     * 题意：给出乱序数组中 最长的有序串长度
     * 思路：
     * 由于要求o(n)复杂度，所以不能排序
     * 1、遍历一遍数组构建一个Map, key value均为数组中的值
     * 2、遍历数组，记录该元素所在的连续序列长度，最短为元素本身，长度为1
     * 3、返回最大的连续序列长度
     */
    static int countLongestConsecutive(int[] arr) {
        if (null == arr || arr.length < 1) {
            return 0;
        }
        if (arr.length < 2) {
            return 1;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            map.put(i, i);
        }
        // 二次遍历，获取最长子序列的长度
        int longest = 0;
        for (int i : arr) {
            longest = Math.max(longest, getLongestConsecutiveSequence(i, map));
        }
        return longest;
    }

    // 获取元素 i 所在的连续序列长度, 并从map中删除统计到的连续序列元素
    private static int getLongestConsecutiveSequence(int i, Map<Integer, Integer> map) {
        // 元素所在的序列已经被统计过
        if (null == map.get(i)) {
            return 0;
        }
        int count = 1; // 连续序列长度初始化为1，i 元素

        // 统计小于 i 的连续元素个数
        int pre = i - 1;
        while (null != map.get(pre)) {
            count++;
            // 删除元素，避免该序列元素被重复统计
            map.remove(pre);
            pre--;
        }
        // 统计大于 i 的连续元素个数
        int post = i + 1;
        while (null != map.get(post)) {
            count++;
            // 删除元素，避免该序列元素被重复统计
            map.remove(post);
            post++;
        }
        return count;
    }

    private static void testCountLongestConsecutive() {
        int[] arr = {100, 4, 200, 1, 3, 2};
        int res = countLongestConsecutive(arr);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testCountLongestConsecutive();
    }
}
