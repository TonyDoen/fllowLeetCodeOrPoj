package me.meet.labuladong._1;

import java.util.HashMap;
import java.util.Map;

public final class LC0567 {
    private void LC0567() {
    }
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
     * 时间复杂度是 O(N)
     */

    /**
     * LeetCode 567 题， Permutation in String
     *
     * 给定2个字符串 s1, s2; 判断 s2 是否包含 s1 的排列
     * 即, 第一个字符串的排列之一是第二个字符串的子串
     *
     * 例子1：
     * input: s1 = "ab"; s2 = "eidbaooo"
     * output: true
     * explain: s2 包含 s1 的排列之一 ["ba"]
     *
     * 例子2：
     * input: s1 = "ab"; s2 = "eidboaooo"
     * output: false
     * explain:
     *
     * 注意： s1 是可以包含重复字符的
     *
     * 这种题目，是明显的滑动窗口
     * 给你一个S和T，请问S中是否存在一个子串，包含T中所有字符且不包含其他字符
     *
     */
    static boolean checkInclusion(String t, String s) {
        Map<Character, Integer> need = new HashMap<>(), window = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0, valid = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
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
            while (right - left >= t.length()) {
                // 在这里判断是否找到合法子串
                if (valid == need.size()) {
                    return true;
                }

                char d = s.charAt(left);
                left++;

                // 进行窗口内数据的一系列更新
                Integer lCnt = need.get(d);
                if (null != lCnt) {
                    if (lCnt.equals(window.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }

        return false;
    }

    private static void testCheckInclusion() {
        String t = "ab";
        String s = "eidbaooo";

        boolean res = checkInclusion(t, s);
        System.out.println(res);

    }

    public static void main(String[] args) {
        testCheckInclusion();
    }

}
