package me.meet.labuladong._1;

import java.util.HashMap;
import java.util.Map;

public final class LC0076 {
    private LC0076() {
    }

    /**
     * 滑动窗口模板
     *
     * int left = 0, right = 0;
     * while (right < s.size()) {
     *     // 增大窗口
     *     window.add(s[right]);
     *     right++;
     *
     *     while (window needs shrink) {
     *         // 缩小窗口
     *         window.remove(s[left]);
     *         left++;
     *     }
     * }
     *
     * 时间复杂度是 O(N)
     */
    /**
     * 滑动窗口算法框架
     *
    void slidingWindow (String s, String t) {
        Map<Character, Integer> need = new HashMap<>(), window = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0)+1);
        }

        int left = 0, right = 0, valid = 0;
        while (right < s.length()) {
            // c 是将要移入窗口的字符
            char c = s.charAt(right);
            // 右移窗口
            right++;
            // 进行窗口内数据的一系列更新
            // ...

            // debug
            System.out.println("window: {}...");

            // 判断左侧窗口是否收缩
            while (window needs shrink) {
                // d 是将要移出窗口的字符
                char d = s.charAt(left);
                // 左移窗口
                left++;
                // 进行窗口内数据的一系列更新
                // ...
            }
        }
    }
    */

    /**
     * LeetCode 76 ： Minimum Window Substring
     * 给你一个字符串 s, 一个字符串 t, 在 s 里找到包含t 所有字母的最小子串
     *
     * s = "BANCADBCEFGDAFSDFE"
     * t = "ABC"
     */
    static String minWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>(), window = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0, valid = 0;
        int start = 0, len = Integer.MAX_VALUE; // 记录最小覆盖子串起始索引和长度
        while (right < s.length()) {
            // c 是将要移入窗口的字符
            char c = s.charAt(right);
            // 右移窗口
            right++;

            // 进行窗口内数据的一系列更新
            Integer rCnt = need.get(c);
            if (null != rCnt) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (rCnt.equals(window.get(c))) {
                    valid++;
                }
            }

            // 判断左侧窗口是否收缩
            while (valid == need.size()) {
                // 这里更新最小覆盖字串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }

                // d 是将要移出窗口的字符
                char d = s.charAt(left);
                // 左移窗口
                left++;

                // 进行窗口内数据的一系列更新
                Integer lCnt = need.get(c);
                if (null != lCnt) {
                    if (lCnt.equals(window.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }

            }
        }
        return Integer.MAX_VALUE == len ? "" : s.substring(start, len);
    }

    private static void testMinWindow() {
        String s = "BANCADBCEFGDAFSDFE";
        String t = "ABC";

        String res = minWindow(s, t);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testMinWindow();
    }
}
