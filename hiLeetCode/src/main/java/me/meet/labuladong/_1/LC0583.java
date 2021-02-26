package me.meet.labuladong._1;

import java.util.Arrays;

public final class LC0583 {
    private LC0583() {
    }
    /**
     *
     * LeetCode 583 两个字符串的删除操作
     *
     * 给定2个单词 s1 和 s2, 找到使得 s1, s2 相同所需的最小步数，每步可以删除任意一个字符串中的一个字符。
     *
     * 例子1：
     * input:  "sea", "eat"
     * output: 2
     * explain:第一步将 "sea" 变成 "ea", 第二步将 "eat" 变成 "ea"
     *
     *
     * 思路：
     * 题目让我们计算将两个字符串变得相同的最少删除次数，那我们可以思考一下，最后这两个字符串会被删成什么样子？
     * 删除的结果就是它俩的最长公共子序列
     * 那么，要计算删除的次数，就可以通过最长公共子序列的长度推导出来
     *
     */
    static int minDistance(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int lcs = LC1143.longestCommonSubsequence2(s1, s2);
        return m - lcs + n - lcs;
    }

    private static void testMinDistance() {
        String s1 = "sea", s2 = "eat";
        int res = minDistance(s1, s2);
        System.out.println(res);
    }

    /**
     *
     * LeetCode 712 两个字符串的最小 ASCII 删除和
     * 给定2个字符串s1, s2, 找到使2个字符串想等所需要删除字符的 ASCII 值的最小和
     *
     * 例子1：
     * input:  s1 = "sea", s2 = "eat"
     * output: 231
     * explain:第一步将 "sea" 变成 "ea", 删除 "s"(115) 加入总和
     *         第二步将 "eat" 变成 "ea", 删除 "t"(116) 加入总和
     *         结束，2个字符串想等， 115+116=231 符合条件的最小和
     *
     * 思路：
     * 不能直接复用计算最长公共子序列的函数了，但是可以依照之前的思路，稍微修改 base case 和状态转移部分即可直接写出解法代码
     *
     * base case 有一定区别，计算lcs长度时，如果一个字符串为空，那么lcs长度必然是 0；但是这道题如果一个字符串为空，另一个字符串必然要被全部删除，所以需要计算另一个字符串所有字符的 ASCII 码之和。
     * 关于状态转移，当s1[i]和s2[j]相同时不需要删除，不同时需要删除，所以可以利用dp函数计算两种情况，得出最优的结果。其他的大同小异，就不具体展开了。
     * 
     */
    private static int[][] memo;

    static int minimumDeleteSum(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        // 备忘录值 -1 代表未曾计算
        memo = new int[m][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return help(s1, 0, s2, 0);
    }

    // 定义：将 s1[i...] 和 s2[j...] 删除成相同字符串，
    // 最小的 ASCII 码之和为 dp(s1, i, s2, j)。
    private static int help(String s1, int i, String s2, int j) {
        int res = 0;
        // base case
        if (s1.length() == i) {
            // 若 s1 到头， 那么 s2 剩下的都需要删除
            for (; j < s2.length(); j++) {
                res += s2.charAt(j);
            }
            return res;
        }
        if (s2.length() == j) {
            // 若 s2 到头，那么 s1 剩下的都需要删除
            for (; i < s1.length(); i++) {
                res += s1.charAt(i);
            }
            return res;
        }

        if (-1 != memo[i][j]) {
            return memo[i][j];
        }

        if (s1.charAt(i) == s2.charAt(j)) {
            // s1[i], s2[j] 都是在 LCS 中的，不用删除
            memo[i][j] = help(s1, i + 1, s2, j + 1);
        } else {
            // s1[i], s2[j] 至少有一个不在 LCS 中， 删除一个
            memo[i][j] = Math.min(s1.charAt(i) + help(s1, i + 1, s2, j), s2.charAt(j) + help(s1, i, s2, j + 1));
        }
        return memo[i][j];
    }

    private static void testMinimumDeleteSum() {
        String s1 = "sea", s2 = "eat";
        int res = minimumDeleteSum(s1, s2);
        System.out.println(res);

    }

    public static void main(String[] args) {
        testMinDistance();
        testMinimumDeleteSum();
    }
}
