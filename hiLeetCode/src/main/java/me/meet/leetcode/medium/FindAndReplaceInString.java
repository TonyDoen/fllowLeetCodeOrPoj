package me.meet.leetcode.medium;

import java.util.*;

public final class FindAndReplaceInString {
    private FindAndReplaceInString() {}
    /**
     * Find And Replace in String
     * url: https://www.cnblogs.com/grandyang/p/10352323.html
     * url:
     *
     * To some string S, we will perform some replacement operations that replace groups of letters with new ones (not necessarily the same size).
     * Each replacement operation has 3 parameters: a starting index i, a source word x and a target word y.  The rule is that if x starts at position i in the original string S, then we will replace that occurrence of x with y.  If not, we do nothing.
     * For example, if we have S = "abcd" and we have some replacement operation i = 2, x = "cd", y = "ffff", then because "cd" starts at position 2 in the original string S, we will replace it with "ffff".
     * Using another example on S = "abcd", if we have both the replacement operation i = 0, x = "ab", y = "eee", as well as another replacement operation i = 2, x = "ec", y = "ffff", this second operation does nothing because in the original string S[2] = 'c', which doesn't match x[0] = 'e'.
     * All these operations occur simultaneously.  It's guaranteed that there won't be any overlap in replacement: for example, S = "abc", indexes = [0, 1], sources = ["ab","bc"] is not a valid test case.
     *
     * Example 1:
     * Input: S = "abcd", indexes = [0,2], sources = ["a","cd"], targets = ["eee","ffff"]
     * Output: "eeebffff"
     *
     * Explanation:
     * "a" starts at index 0 in S, so it's replaced by "eee".
     * "cd" starts at index 2 in S, so it's replaced by "ffff".
     *
     * Example 2:
     * Input: S = "abcd", indexes = [0,2], sources = ["ab","ec"], targets = ["eee","ffff"]
     * Output: "eeecd"
     *
     * Explanation:
     * "ab" starts at index 0 in S, so it's replaced by "eee".
     * "ec" doesn't starts at index 2 in the original S, so we do nothing.
     *
     * Notes:
     * 0 <= indexes.length = sources.length = targets.length <= 100
     * 0 < indexes[i] < S.length <= 1000
     * All characters in given inputs are lowercase letters.
     *
     *
     * 题目：在字符串中查找和替换
     * 这道题给了我们一个字符串S，并给了一个坐标数组，还有一个源字符串数组，还有目标字符串数组，意思是若某个坐标位置起，源字符串数组中对应位置的字符串出现了，将其替换为目标字符串。题目真的是好长，但好在给了两个例子可以帮助我们很好的理解题意。
     * 此题的核心操作就两个，查找和替换，需要注意的是，由于替换操作会改变原字符串，但是我们查找始终是基于最初始的S，比如例子2中，当完成了第一次替换后，S变为了 "eeecd"，好像此时 "ec" 出现了，但仍然不能替换，因为一切查找都是基于最原始的那个S。
     * 那么正向的替换可能会产生这样的问题，我们注意到题目中有个限制条件，就是说不会有重叠产生，比如 "abc"，如果让在0位置上查找 "ab" 了，就不会让在1位置上查找 "bc"，这样的话，其实我们可以从后往前开始查找替换，因为不会有重叠，所以后面替换了的字符不会影响到前面。
     *
     * 思路：
     * 这里还是使用HashMap来保存映射对，先做个check，只要当发现可以查找到源字符串位置的时候，才添加映射对儿，这样就排除了所有不能替换的情况。然后我们遍历原字符串S，对于每个遍历到的位置，我们都到HashMap中查找，如果发现需要替换，我们就把目标字符串提取出来，加入结果res中，注意此时i也需要加上源字符串的长度。若不需要替换，则直接将字符加入结果res中，然后i移动到下一个位置，
     */
    static String findReplaceString(String src, int[] indexes, String[] sources, String[] targets) {
        Map<Integer, Integer> mp = new HashMap<>();
        for (int i = 0; i < indexes.length; i++) {
            String sub = src.substring(indexes[i], indexes[i] + sources[i].length());
            if (sources[i].equals(sub)) {
                mp.put(indexes[i], i);
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0, length = src.length(); i < length; ) {
            Integer idx = mp.get(i);
            if (null != idx) {
                result.append(targets[idx]);
                i += sources[idx].length();
            } else {
                result.append(src.charAt(i));
                i++;
            }
        }
        return result.toString();
    }

    private static void testFindReplaceString() {
        String src = "abcd";
        int[] indexes = new int[]{0, 2};
        String[] sources = new String[]{"a", "cd"};
        String[] targets = new String[]{"eee", "ffff"};

        String res = findReplaceString(src, indexes, sources, targets);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testFindReplaceString();
    }
}
