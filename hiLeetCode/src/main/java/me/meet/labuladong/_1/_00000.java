package me.meet.labuladong._1;

import java.util.HashMap;
import java.util.Map;

public final class _00000 {
    private _00000() {
    }

    /**
     * 编辑距离这个问题，
     * 它看起来十分困难，解法却出奇得简单漂亮，而且它是少有的比较实用的算法
     * （是的，我承认很多算法问题都不太实用）。
     *
     * 给定2个字符串 s1, s2, 计算将s1 转换成s2 所使用的最少操作数。
     * 可以对字符串进行如下3种操作
     * <1> 插入一个字符
     * <2> 删除一个字符
     * <3> 替换一个字符
     *
     * 例子1：
     * input:  s1="horse", s2="ros"
     * output: 3
     * explain:
     *     horse -> rorse (将 'h' 替换为 'r')
     *     rorse -> rose  (将 'r' 删除)
     *     rose  -> ros   (将 'e' 删除)
     *
     * 例子2：
     * input:  s1="intention", s2="execution"
     * output: 5
     * explain:
     *     intention -> inention  (将 't' 删除)
     *     inention  -> enention  (将 'i' 替换为 'e')
     *     enention  -> exention  (将 'n' 替换为 'x')
     *     exention  -> exection  (将 'n' 替换为 'c')
     *     exection  -> execution (将 'u' 插入到 'c' 之后)
     *
     *
     * 思路：
     * 为什么说这个问题难呢，因为显而易见，它就是难，让人手足无措，望而生畏。
     *
     * 在日常生活中用到了这个算法。写错位了一段内容，我决定修改这部分内容让逻辑通顺。但是公众号文章最多只能修改 20 个字，且只支持增、删、替换操作（跟编辑距离问题一模一样），于是我就用算法求出了一个最优方案，只用了 16 步就完成了修改。
     * 高大上一点的应用，DNA 序列是由 A,G,C,T 组成的序列，可以类比成字符串。编辑距离可以衡量两个 DNA 序列的相似度，编辑距离越小，说明这两段 DNA 越相似。
     *
     *
     * 编辑距离问题就是给我们两个字符串s1和s2，只能用三种操作，让我们把s1变成s2，求最少的操作数。
     *
     * 前文[最长公共子序列]说过，解决两个字符串的动态规划问题，
     * 一般都是用两个指针i,j分别指向两个字符串的最后，然后一步步往前走，缩小问题的规模。
     *
     * 令 s1="rad"; s2="apple", 把 s1 变成 s2
     * init:
     *       s1 => r a d
     *                 i (指针)
     *       s2 => a p p l e
     *                     j (指针)
     *
     * step1: insert 'e'
     *       s1 => r a d e
     *                 i (指针)
     *       s2 => a p p l e
     *                   j (指针)
     *
     * step2: insert 'l'
     *       s1 => r a d l e
     *                 i (指针)
     *       s2 => a p p l e
     *                 j (指针)
     *
     * step3: insert 'p'
     *       s1 => r a d p l e
     *                 i (指针)
     *       s2 => a p p l e
     *                 j (指针)
     *
     * step4: replace 'd' -> 'p'
     *       s1 => r a p p l e
     *                 i (指针)
     *       s2 => a p p l e
     *               j (指针)
     *
     * step5: match and skip
     *       s1 => r a p p l e
     *               i (指针)
     *       s2 => a p p l e
     *             j (指针)
     *
     * step5: delete 'r'
     *       s1 => [r] a p p l e
     *             i (指针)
     *       s2 => a p p l e
     *             j (指针)
     *
     * 字符串操作需要5步
     *
     * 上面，就是j走完s2时，如果i还没走完s1，那么只能用删除操作把s1缩短为s2
     * 类似的，如果i走完s1时j还没走完了s2，那就只能用插入操作把s2剩下的字符全部插入s1。
     * 等会会看到，这两种情况就是算法的 base case。
     *
     * 伪代码：
     *   if s1[i] == s2[j]:
     *       skip
     *       i,j 同时前进
     *   else:
     *       三选一:
     *           insert (插入)
     *           delete (删除)
     *           replace(替换)
     *
     * 注意：
     *   这个「三选一」到底该怎么选择呢？很简单，全试一遍，哪个操作最后得到的编辑距离最小，就选谁。这里需要递归技巧，理解需要点技巧，先看下代码：
     *   def minDistance(s1, s2) -> int:
     *       def dp(i, j):
     *           # base case
     *           if i == -1: return j+1  # i 走完， 0~j+1 之间的字符删除
     *           if j == -1: return i+1  # j 走完， 0~i+1 之间的字符删除
     *
     *           if s1[i] == s2[j]:
     *               return dp(i-1, j-1) # skip
     *           else:
     *               return min(
     *                   dp(i, j-1) + 1,  # insert
     *                   dp(i-1, j) + 1,  # delete
     *                   dp(i-1, j-1)+1,  # replace
     *               )
     *
     *
     * 怎么能一眼看出存在重叠子问题呢？前文 动态规划之正则表达式 有提过，这里再简单提一下，需要抽象出本文算法的递归框架：
     *
     * def dp(i, j):
     *     dp(i - 1, j - 1) #1
     *     dp(i, j - 1)     #2
     *     dp(i - 1, j)     #3
     * 对于子问题dp(i-1,j-1)，如何通过原问题dp(i,j)得到呢？有不止一条路径，比如dp(i,j)->#1和dp(i,j)->#2->#3。一旦发现一条重复路径，就说明存在巨量重复路径，也就是重叠子问题。
     *
     *
     * 对于重叠子问题呢，前文 动态规划详解 介绍过，优化方法无非是备忘录或者 DP table。
     */
    static int minDistance(String s1, String s2) {
        return helpMinDistance(s1, s2, s1.length() - 1, s2.length() - 1);
    }

    private static final Map<String, Integer> memo = new HashMap<>();

    private static int helpMinDistance(String s1, String s2, int i, int j) {
        String key = i + "->" + j;
        Integer mayVal = memo.get(key);
        if (null != mayVal) {
//            System.out.println("check");
            return mayVal;
        }

        // base case
        if (-1 == i) { // i 走完， 0~j+1 之间的字符删除
            return j + 1;
        }
        if (-1 == j) { // j 走完， 0~i+1 之间的字符删除
            return i + 1;
        }

        if (s1.charAt(i) == s2.charAt(j)) {
            int res = helpMinDistance(s1, s2, i - 1, j - 1); // skip

            memo.put(key, res);
            return res;
        } else {
            int insert = helpMinDistance(s1, s2, i, j - 1) + 1; // insert
            int delete = helpMinDistance(s1, s2, i - 1, j) + 1; // delete
            int replace = helpMinDistance(s1, s2, i - 1, j - 1) + 1; // replace

            // min(insert, delete, replace)
            int res = Math.min(Math.min(insert, delete), replace);

            memo.put(key, res);
            return res;
        }
    }

    /**
     * 主要解释 DP table 的解法：
     *
     * 首先明确 dp 数组的含义，dp 数组是一个二维数组，
     * 长这样：
     *  s1 \ s2  ""  a  p  p  l  e
     *  ""       0   1  2  3  4  5
     *  r        1   1  2  3  4  5
     *  a        2   1  2  3  4  5
     *  d        3   2  2  3  4  5
     *
     *  先写 第0行 和 第0列的情况：比较好写(也是 base case)
     *  再写 矩阵内部的值
     *
     *  dp[i][j]的含义和之前的 dp 函数类似：
     *  def dp(i, j) -> int  # 返回 s1[0..i] 和 s2[0..j] 的最小编辑距离
     *  dp[i-1][j-1]         # 存储 s1[0..i] 和 s2[0..j] 的最小编辑距离
     *
     *  dp 函数的 base case 是i,j等于 -1，而数组索引至少是 0，所以 dp 数组会偏移一位，
     *  dp[..][0]和dp[0][..]对应 base case。
     *
     */
    static int minDistanceDpTable(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        // base case
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }

        // 自底向上求解
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];           // skip
                } else {
                    dp[i][j] = min(dp[i - 1][j] + 1, // insert
                        dp[i][j - 1] + 1,                  // delete
                        dp[i - 1][j - 1] + 1                 // replace
                    );
                }
            }
        }
        // 存储整个 s1, s2 的最小编辑距离
        return dp[m][n];
    }

    private static int min(int... args) {
        int res = Integer.MAX_VALUE;
        for (int i : args) {
            res = Math.min(res, i);
        }
        return res;
    }

    /**
     * 一般来说，处理两个字符串的动态规划问题，都是按本文的思路处理，建立 DP table。为什么呢，因为易于找出状态转移的关系
     *
     * 比如编辑距离的 DP table：
     * dp[i-1][j-1]      dp[i-1][j]          ====>    replace or skip         delete
     *             \         |                                          \         |
     *              \        |                                           \        |
     *               \      \|                                            \      \|
     * dp[i][j-1] -----> dp[i][j]                         insert  ----------->   ?
     *
     * 注意：
     * 1> 有一个细节，既然每个dp[i][j]只和它附近的三个状态有关，
     * 空间复杂度是可以压缩成 O(min(M,N)); M，N 是两个字符串的长度
     *
     * 2> 可能还会问，这里只求出了最小的编辑距离，那具体的操作是什么？
     * 之前举的修改公众号文章的例子，只有一个最小编辑距离肯定不够，还得知道具体怎么修改才行。这个其实很简单，代码稍加修改，给 dp 数组增加额外的信息即可
     * // int[][] dp
     * Op[][] dp;
     *
     * class Op {
     *     int val;
     *     int choice; // 0: skip; 1: insert; 2: delete; 3: replace
     * }
     *
     *
     */


    private static void testMinDistance() {
        String s1 = "rad", s2 = "apple";
        int res = minDistance(s1, s2);
        System.out.println(res);

        res = minDistanceDpTable(s1, s2);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testMinDistance();
    }
}
