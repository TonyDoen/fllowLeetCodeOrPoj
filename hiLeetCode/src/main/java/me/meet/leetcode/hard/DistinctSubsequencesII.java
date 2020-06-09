package me.meet.leetcode.hard;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class DistinctSubsequencesII {
    /**
     * 940. Distinct Subsequences II
     * Given a string S, count the number of distinct, non-empty subsequences of S .
     * Since the result may be large, return the answer modulo 10^9 + 7.
     * 
     * Example 1:
     * Input: "abc"
     * Output: 7
     * Explanation: The 7 distinct subsequences are "a", "b", "c", "ab", "ac", "bc", and "abc".
     * 
     * Example 2:
     * Input: "aba"
     * Output: 6 Explanation: The 6 distinct subsequences are "a", "b", "ab", "ba", "aa" and "aba".
     * 
     * Example 3:
     * Input: "aaa"
     * Output: 3 Explanation: The 3 distinct subsequences are "a", "aa" and "aaa".
     * 
     * Note:
     * S contains only lowercase letters.
     * 1 <= S.length <= 2000
     * 
     * 
     * 题意：不同的子序列之二
     * 思路：这道题是之前那道 [Distinct Subsequences](http://www.cnblogs.com/grandyang/p/4294105.html) 的类似题目，这里只有一个字符串，让找出所有不同的子序列，如果字符串中没有重复字符，可以直接得到子序列的个数，但是这里由于重复字符的存在，就大大增加了难度。由于题目中提示了结果可能非常大，要对一个超大数取余，就相当于明确说了要用动态规划 Dynamic Programming 来做，下面就要来考虑 dp 数组的定义和状态转移方程的推导了。刚开始博主也是考虑用一个一维数组 dp，其中 dp[i] 表示以 S[i] 结尾的不同子序列的个数，就像 [这个帖子](https://leetcode.com/problems/distinct-subsequences-ii/discuss/192030/Java-DP-ON2-time-greater-ON-time-greater-O1-space) 中定义的一样，但是状态转移方程不好推导，那个帖子虽然代码可以跑通，但是解释的却不通，博主也纳闷这算是歪打正着么，希望哪位大神来解释一下。这里还是根据 [lee215 大神的帖子](https://leetcode.com/problems/distinct-subsequences-ii/discuss/192017/C%2B%2BJavaPython-4-lines-ON-Time-O1-Space) 来讲解吧。这里使用一个大小为 26 的一维数组 dp，其中 dp[i] 表示以字符 i+'a' 结尾的不同子序列的个数，因为题目中限定了只有小写字母，所以只有 26 个。以 aba 这个例子来分析一下，当遇到开头的a时，那么以a结尾的子序列只有一个，就是a，当遇到中间的b时，此时知道以b结尾的子序列有2个，分别是 b 和 ab，是怎么得来的呢，其实是空串和a后面分别加个b得来的，此时貌似得到的值和通过 sum(dp)+1 计算的结果相等，再来验证一下这个成不成立。当遇到末尾的a的时候，那么此时以a结尾的子序列就有4个，分别是 a，aa，ba，aba，是怎么得来的？在这个a加入之前，当前所有的子序列有，a，b，ab，如果再算上一个空串，[]，a，b，ab，则在其后面各加上一个b，就可以得到结果了，貌似也符合 sum(dp)+1 的规律，这其实也并不难理解，因为在当前不同序列的基础上，加上任何一个字符都会得到另一个不同的子序列，后面的加1是为了加上空串的情况，这个就是状态转移方程了，最终的结果是把 dp 数组累加起来取余后返回即可，
     */
    static int distinctSubsequencesII(String src) {
        int n = src.length(), res = 0, m = (int) (1e9 + 7);
        Map<Character, Long> mp = new HashMap<>();
        long[] a = new long[n], b = new long[n];
        a[0] = 1;
        mp.put(src.charAt(0), 0L);
        for (int i = 1; i < n; i++) {
            b[i] = (a[i - 1] + b[i - 1]) % m;
            char ci = src.charAt(i);
            if (!mp.containsKey(ci)) {
                a[i] = (b[i] + 1) % m;
            } else {
                a[i] = (m + b[i] - mp.get(ci)) % m;
            }
        }
        return (int) ((a[n - 1] + b[n - 1]) % m);
    }

    private static void testDistinctSubsequencesII() {
        String src = "abc";
        int res = distinctSubsequencesII(src);
        System.out.println(res);
    }

    static int dis2(String src) {
//        int MOD = 1_000_000_007;
        int mod = 1000000007;
        int[][] dp = new int[src.length()][26];
        dp[0][src.charAt(0) - 'a'] = 1;
        for (int i = 1; i < src.length(); i++) {
            int temp = 0;
            for (int j = 0; j < 26; j++) {
                dp[i][j] = dp[i - 1][j];
                temp = (temp + dp[i - 1][j]);
            }
            temp++;
            dp[i][src.charAt(i) - 'a'] = temp;
        }
        int res = 0;
        for (int i = 0; i < 26; i++) {
            res = (res + dp[src.length() - 1][i]);
        }
        return res;
    }

    /**
     * from collections import defaultdict
     * class Solution:
     *     def distinctSubseqII(self, S: str) -> int:
     *         dp = defaultdict(int)
     *
     *         res = 0
     *         for c in S:
     *             res, dp[c] = 2 * res - dp[c] + 1, res + 1
     *
     *         return res % (10 ** 9 + 7)
     *
     * 作者：iberryful
     * 链接：https://leetcode-cn.com/problems/distinct-subsequences-ii/solution/san-chong-dpfang-fa-shi-jian-fu-za-du-cong-on2dao-/
     */

    static int dis4(String src) {
        int result = 1, mod = 1000000007;
        int[] dp = new int[26];
        for (int length = src.length(), i = 0; i < length; i++) {
            int idx = src.charAt(i) - 'a';

            int before = dp[idx];
            dp[idx] = result;
            result = (2 * result - before);

            // 溢出处理
            result = (result < 0 ? result + mod : result % mod);
        }
        return result - 1;
    }

    static int dis3(String src) {
        BigInteger result = BigInteger.ZERO, one = BigInteger.ONE, two = BigInteger.valueOf(2);
        // 1. S contains only lowercase letters.
        BigInteger[] dp = new BigInteger[26];
        for (int i = 0; i < 26; i++) {
            dp[i] = BigInteger.ZERO;
        }

        for (int length = src.length(), i = 0; i < length; i++) {
            int idx = src.charAt(i) - 'a';

            BigInteger before = dp[idx];
            dp[idx] = result.add(one);
            result = result.multiply(two).add(before.negate()).add(one);
        }

        return result.mod(BigInteger.valueOf(1000000007)).intValue();
    }



    public static void main(String[] args) {
//        testDistinctSubsequencesII();
//        dis3("abc");
        String src = "zchmliaqdgvwncfatcfivphddpzjkgyygueikthqzyeeiebczqbqhdytkoawkehkbizdmcnilcjjlpoeoqqoqpswtqdpvszfaksn";
        int res = dis3(src);
        int res2 = dis4(src);
        System.out.println(res);
        System.out.println(res2);
    }
}
