package me.meet.labuladong._1;

import java.util.HashMap;
import java.util.Map;

public final class LC0003 {
    private LC0003() {
    }

    /**
     * LeetCode 第3题 Longest Substring Without Repeating Characters
     *
     * 给定一个字符串，请你找出其中不含重复字符的最长子串的长度
     *
     * 例子1:
     * input: "abcabcbb"
     * output: 3
     * explain: 因为无重复字符的最长子串 "abc"，所以长度为3
     *
     * 例子2:
     * input: "bbbbb"
     * output: 1
     * explain: 因为无重复字符的最长子串 "b"，所以长度为1
     *
     * 例子3:
     * input: "pwwkew"
     * output: 3
     * explain: 因为无重复字符的最长子串 "wke"，所以长度为3
     *
     */
    static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> window = new HashMap<>();

        int left = 0, right = 0, res = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;

            // 进行窗口内数据的一系列更新
            window.put(c, window.getOrDefault(c, 0) + 1);

            // 判断左侧窗口是否收缩
            while (window.get(c) > 1) {

                char d = s.charAt(left);
                left++;

                // 进行窗口内数据的一系列更新
                window.put(d, window.getOrDefault(d, 0) - 1);
            }

            // 这里更新答案
            res = Math.max(res, right - left);
        }
        return res;
    }

    private static void testLengthOfLongestSubstring() {
        String s = "pwwkew";
        int res = lengthOfLongestSubstring(s);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testLengthOfLongestSubstring();
    }
}
