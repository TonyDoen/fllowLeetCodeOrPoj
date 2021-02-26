package me.meet.labuladong._1;

import java.util.Arrays;

public final class LC1143 {
    private LC1143() {
    }

    /**
     *
     * LeetCode 1143 题
     * 计算最长公共子序列（Longest Common Subsequence，简称 LCS）是一道经典的动态规划题目
     *
     * 给你输入两个字符串s1和s2，请你找出他们俩的最长公共子序列，返回这个子序列的长度。
     * 比如说输入s1 = "zabcde", s2 = "acez"，它俩的最长公共子序列是lcs = "ace"，长度为 3，所以算法返回 3。
     *
     * 思路：
     * 1. 一个最简单的暴力算法就是，把s1和s2的所有子序列都穷举出来，然后看看有没有公共的，然后在所有公共子序列里面再寻找一个长度最大的。 显然，这种思路的复杂度非常高，你要穷举出所有子序列，这个复杂度就是指数级的
     * 2. 正确的思路是不要考虑整个字符串，而是细化到s1和s2的每个字符。前文 子序列解题模板 中总结的一个规律：
     *    对于两个字符串求子序列的问题，都是用两个指针i和j分别在两个字符串上移动，大概率是动态规划思路。
     *    最长公共子序列的问题也可以遵循这个规律，我们可以先写一个dp函数
     *
     *    // 定义：计算 s1[i..] 和 s2[j..] 的最长公共子序列长度
     *    int dp(String s1, int i, String s2, int j)
     *
     *    dp函数的定义是：dp(s1, i, s2, j)计算s1[i..]和s2[j..]的最长公共子序列长度。
     *          i
     *    s1 => z a b c d e
     *          j
     *    s2 => a c e z
     *    只看s1[i]和s2[j]，
     *    如果s1[i] == s2[j]，说明这个字符一定在lcs中：
     *    => dp(s1, i, s2, j) = 1 + dp(s1, i + 1, s2, j + 1)
     *
     *    如果s1[i] != s2[j]，说明s1[i]和s2[j]中至少有一个字符不在lcs中: 3种情况
     *    <1> 情况一、s1[i] 不在 lcs 中.
     *              i
     *    s1 => z a b c d e x
     *              j
     *    s2 => a c e z t s p
     *
     *    <2> 情况二、s2[j] 不在 lcs 中. 这个例子里没有
     *                  i
     *    s1 => z a b c d e x
     *                  j
     *    s2 => a c e z t s p
     *
     *    <3> 情况三、都不在 lcs 中. 这个例子里没有
     *                      i
     *    s1 => z a b c d e x
     *                      j
     *    s2 => a c e z t s p
     *
     *    => dp(s1, i, s2, j) = max(
     *        // 情况一、s1[i] 不在 lcs 中
     *        dp(s1, i + 1, s2, j),
     *        // 情况二、s2[j] 不在 lcs 中
     *        dp(s1, i, s2, j + 1),
     *        // 情况三、都不在 lcs 中
     *        dp(s1, i + 1, s2, j + 1)
     *    )
     *
     *    还有一个小的优化，情况三「s1[i]和s2[j]都不在 lcs 中」其实可以直接忽略。
     *    因为我们在求最大值嘛，情况三在计算s1[i+1..]和s2[j+1..]的lcs长度，这个长度肯定是小于等于情况二s1[i..]和s2[j+1..]中的lcs长度的，因为s1[i+1..]比s1[i..]短嘛，那从这里面算出的lcs当然也不可能更长
     *    同理，情况三的结果肯定也小于等于情况一。说白了，情况三被情况一和情况二包含了，所以我们可以直接忽略掉情况三
     *
     *
     * 抽象出我们核心dp函数的递归框架：
     * int dp(int i, int j) {
     *     dp(i + 1, j + 1); // #1
     *     dp(i, j + 1);     // #2
     *     dp(i + 1, j);     // #3
     * }
     *
     * 假设我想从dp(i, j)转移到dp(i+1, j+1)，有不止一种方式，可以直接走#1，也可以走#2 -> #3，也可以走#3 -> #2。
     * 这就是重叠子问题，如果我们不用memo备忘录消除子问题，那么dp(i+1, j+1)就会被多次计算，这是没有必要的。
     */

    // 备忘录，消除重叠子问题
    private static int[][] memo;

    static int longestCommonSubsequence1(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        // 备忘录值为 -1 代表未曾计算
        memo = new int[m][n];
        for (int[] row : memo)
            Arrays.fill(row, -1);
        // 计算 s1[0..] 和 s2[0..] 的 lcs 长度
        return helpLCS1(s1, 0, s2, 0);
    }

    // 定义：计算 s1[i..] 和 s2[j..] 的最长公共子序列长度
    private static int helpLCS1(String s1, int i, String s2, int j) {
        // base case
        if (s1.length() == i || s2.length() == j) {
            return 0;
        }

        // 如果之前计算过，则直接返回备忘录中的答案
        if (-1 != memo[i][j]) {
            return memo[i][j];
        }
        // 根据 s1[i] 和 s2[j] 的情况做选择
        if (s1.charAt(i) == s2.charAt(j)) {
            // s1[i] 和 s2[j] 必然在 lcs 中
            memo[i][j] = 1 + helpLCS1(s1, i + 1, s2, j + 1);
        } else {
            // s1[i] 和 s2[j] 至少有一个不在 lcs 中
            memo[i][j] = Math.max(
                helpLCS1(s1, i + 1, s2, j),
                helpLCS1(s1, i, s2, j + 1)
            );
        }
        return memo[i][j];
    }

    /**
     * 上面用的是自顶向下带备忘录的动态规划思路，我们当然也可以使用自底向上的迭代的动态规划思路，和我们的递归思路一样，关键是如何定义dp数组
     *
     * 自底向上的解法中dp数组定义的方式和我们的递归解法有一点差异，而且由于数组索引从 0 开始，有索引偏移，不过思路和我们的递归解法完全相同，
     * 另外，自底向上的解法可以通过我们前文讲过的 动态规划状态压缩技巧 来进行优化，把空间复杂度压缩为 O(N)
     *
     */
    static int longestCommonSubsequence2(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        // 定义：s1[0..i-1] 和 s2[0..j-1] 的 lcs 长度为 dp[i][j]
        // 目标：s1[0..m-1] 和 s2[0..n-1] 的 lcs 长度，即 dp[m][n]
        // base case: dp[0][..] = dp[..][0] = 0

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 现在 i 和 j 从 1 开始，所以要减一
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // s1[i-1] 和 s2[j-1] 必然在 lcs 中
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // s1[i-1] 和 s2[j-1] 至少有一个不在 lcs 中
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        return dp[m][n];
    }

    private static void testLongestCommonSubsequence() {
        String s1 = "zabcde", s2 = "acez";
        int res = longestCommonSubsequence1(s1, s2);
        System.out.println(res);

        res = longestCommonSubsequence2(s1, s2);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testLongestCommonSubsequence();
    }
}
