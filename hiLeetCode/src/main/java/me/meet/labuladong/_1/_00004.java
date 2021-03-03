package me.meet.labuladong._1;

public final class _00004 {
    private _00004() {
    }
    /**
     * 正则表达式匹配是一个很精妙的算法，而且难度也不小。
     *
     * 动态规划的经典应用：正则表达式。
     * 本文主要写两个正则符号的算法实现：点号「.」和星号「*」，如果你用过正则表达式，应该明白他们的用法
     *
     * 算法的设计是一个螺旋上升、逐步求精的过程，绝不是一步到位就能写出正确算法的。本文会带你解决这个较为复杂的问题，让你明白如何化繁为简，逐个击破，从最简单的框架搭建出最终的答案。
     *
     * 题目：
     * 给定一个字符串(s)和一个字符模式(p)。实现支持'.', '*'的正则表达式匹配
     * '.' 匹配任意单个字符
     * '*' 匹配0个或多个前面的元素
     *
     * 例子1：
     * input:  s = 'aa'; p = 'a*'
     * output: true
     * explain:'*'代表可匹配0个或者多个前面的元素，即可以匹配'a'。因此，重复'a'一次，字符串可以变成'aa'
     *
     * 例子2：
     * input:  s = 'aab'; p = 'c*a*b'
     * output: true
     * explain:'c'可以出现0次，'a'可以被重复一次。因此可以匹配字符串"aab"
     *
     * 例子3：
     * input:  s = 'ab'; p = '.*'
     * output: true
     * explain:'.*' 表示可以匹配0个或者多个('*') 任意字符('.')
     *
     *
     * 思路：
     * 这道题分的情况的要复杂一些，需要用递归Recursion来解：
     * - 若p为空，且s也为空，返回true，反之返回false
     * - 若p的长度为1，且s长度也为1，且相同或是p为'.'则返回true，反之返回false
     * - 若p的第二个字符不为*，且此时s为空则返回false，否则判断首字符是否匹配，且从各自的第二个字符开始调用递归函数匹配
     * - 若p的第二个字符为*，s不为空且字符匹配，调用递归函数匹配s和去掉前两个字符的p，若匹配返回true，否则s去掉首字母
     * - 返回调用递归函数匹配s和去掉前两个字符的p的结果
     *
     */
    private static boolean isMatch(String text, String pattern) {
        if (null == pattern || pattern.isEmpty()) {
            return null == text || text.isEmpty();
        }
        if (1 == pattern.length()) {
            return (1 == text.length() && (text.charAt(0) == pattern.charAt(0) || '.' == pattern.charAt(0)));
        }
        if ('*' != pattern.charAt(1)) {
            if (text.isEmpty()) {
                return false;
            }
            return (text.charAt(0) == pattern.charAt(0) || '.' == pattern.charAt(0)) && isMatch(text.substring(1), pattern.substring(1));
        }
        while (!text.isEmpty() && (text.charAt(0) == pattern.charAt(0) || '.' == pattern.charAt(0))) {
            if (isMatch(text, pattern.substring(2))) {
                return true;
            }
            text = text.substring(1);
        }
        return isMatch(text, pattern.substring(2));
    }

    /**
     * 思路：
     * 每次从s中取出一个字符与p中的字符匹配。基本特殊情况只需要考虑涉及到’*‘的匹配
     * p[i+1]不是’*‘，判断当前字符是否相等，或如果出现’.‘，直接跳过。
     * 如果p[i+1]是’‘，需要判断当前字符是否能匹配，如果当前字符匹配，则s中字符后移，而p中字符不变，需判断该x是否仍能匹配。如果当前字符不匹配，那么直接跳过这个x*即可。
     * 当然，处理边界条件很重要，笔者在边界上就出了很大问题
     *
     * ......
     *
     */

    /**
     * 也可以用DP来解，定义一个二维的DP数组，其中dp[i][j]表示s[0,i)和p[0,j)是否match，然后有下面三种情况
     * 1.  P[i][j] = P[i - 1][j - 1],                                          if p[j - 1] != '*' && (s[i - 1] == p[j - 1] || p[j - 1] == '.');
     * 2.  P[i][j] = P[i][j - 2],                                              if p[j - 1] == '*' and the pattern repeats for 0 times;
     * 3.  P[i][j] = P[i - 1][j] && (s[i - 1] == p[j - 2] || p[j - 2] == '.'), if p[j - 1] == '*' and the pattern repeats for at least 1 times.
     */
    static boolean isMatch0(String text, String pattern) {
        int m = text.length(), n = pattern.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
//        for (boolean[] one : dp) {
//            Arrays.fill(one, false);
//        }
        dp[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (j > 1 && '*' == pattern.charAt(j - 1)) {
                    dp[i][j] = dp[i][j - 2]
                        || (i > 0 && (text.charAt(i - 1) == pattern.charAt(j - 2) || '.' == pattern.charAt(j - 2)) && dp[i - 1][j]);
                } else {
                    dp[i][j] = i > 0
                        && dp[i - 1][j - 1]
                        && (text.charAt(i - 1) == pattern.charAt(j - 1) || '.' == pattern.charAt(j - 1));
                }
            }
        }
        return dp[m][n];
    }

    private static void testIsMatch() {
        String s = "aab", p = "c*a*b";
        boolean res = isMatch(s, p);
        System.out.println(res);

        res = isMatch0(s, p);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testIsMatch();
    }
}
