package me.meet.labuladong._1.LCNOT;

import java.util.Arrays;

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

    /**
     * 经典动态规划：高楼扔鸡蛋
     *
     * 一个很经典的算法问题，若干层楼，若干个鸡蛋，让你算出最少的尝试次数，找到鸡蛋恰好摔不碎的那层楼。国内大厂以及谷歌脸书面试都经常考察这道题，只不过他们觉得扔鸡蛋太浪费，改成扔杯子，扔破碗什么的。
     * 具体的问题等会再说，但是这道题的解法技巧很多，光动态规划就好几种效率不同的思路，最后还有一种极其高效数学解法。秉承咱们号一贯的作风，拒绝奇技淫巧，拒绝过于诡异的技巧，因为这些技巧无法举一反三，学了不太划算。
     *
     * 题目：
     * 你面前有一栋从 1 到N共N层的楼，然后给你K个鸡蛋（K至少为 1）。现在确定这栋楼存在楼层0 <= F <= N，在这层楼将鸡蛋扔下去，鸡蛋恰好没摔碎（高于F的楼层都会碎，低于F的楼层都不会碎）。
     * 现在问你，最坏情况下，你至少要扔几次鸡蛋，才能确定这个楼层F呢？
     * PS：F 可以为 0，比如说鸡蛋在 1 层都能摔碎，那么 F = 0。
     *
     * 也就是让你找摔不碎鸡蛋的最高楼层F，但什么叫「最坏情况」下「至少」要扔几次呢？我们分别举个例子就明白了。
     * 「最坏情况」
     * 先不管鸡蛋个数的限制，有 7 层楼，你怎么去找鸡蛋恰好摔碎的那层楼？
     * 最原始的方式就是线性扫描：我先在 1 楼扔一下，没碎，我再去 2 楼扔一下，没碎，我再去 3 楼…… 以这种策略，最坏情况应该就是我试到第 7 层鸡蛋也没碎（F = 7），也就是我扔了 7 次鸡蛋。
     * 现在你应该理解什么叫做「最坏情况」下了。鸡蛋破碎一定发生在搜索区间穷尽时，不会说你在第 1 层摔一下鸡蛋就碎了，这是你运气好，不是最坏情况。
     * 「至少」
     * 依然不考虑鸡蛋个数限制，同样是 7 层楼，我们可以优化策略。 最好的策略是使用二分查找思路，我先去第(1 + 7) / 2 = 4层扔一下：
     * 如果碎了说明F小于 4，我就去第(1 + 3) / 2 = 2层试……
     * 如果没碎说明F大于等于 4，我就去第(5 + 7) / 2 = 6层试……
     * 以这种策略，最坏情况应该是试到第 7 层鸡蛋还没碎（F = 7），或者鸡蛋一直碎到第 1 层（F = 0）。然而无论那种最坏情况，只需要试log7向上取整等于 3 次，比刚才的 7 次要少，这就是所谓的至少要扔几次。
     * PS：这有点像 Big O 表示法计算算法的复杂度。
     *
     * 实际上，如果不限制鸡蛋个数的话，二分思路显然可以得到最少尝试的次数，但问题是，现在给你了鸡蛋个数的限制K，直接使用二分思路就不行了。
     * 比如说只给你 1 个鸡蛋，7 层楼，你敢用二分吗？你直接去第 4 层扔一下，如果鸡蛋没碎还好，但如果碎了你就没有鸡蛋继续测试了，无法确定鸡蛋恰好摔不碎的楼层F了。这种情况下只能用线性扫描的方法，算法返回结果应该是 7。
     * 有的读者也许会有这种想法：二分查找排除楼层的速度无疑是最快的，那干脆先用二分查找，等到只剩 1 个鸡蛋的时候再执行线性扫描，这样得到的结果是不是就是最少的扔鸡蛋次数呢？
     * 很遗憾，并不是，比如说把楼层变高一些，100 层，给你 2 个鸡蛋，你在 50 层扔一下，碎了，那就只能线性扫描 1～49 层了，最坏情况下要扔 50 次。
     * 如果不要「二分」，变成「五分」「十分」都会大幅减少最坏情况下的尝试次数。比方说第一个鸡蛋每隔十层楼扔，在哪里碎了第二个鸡蛋一个个线性扫描，总共不会超过 20 次。 最优解其实是 14 次。最优策略非常多，而且并没有什么规律可言。
     * 说了这么多废话，就是确保大家理解了题目的意思，而且认识到这个题目确实复杂，就连我们手算都不容易，如何用算法解决呢？
     *
     *
     * 思路1：动态规划
     * 这个问题有什么「状态」，有什么「选择」，然后穷举。
     * 「状态」很明显，就是当前拥有的鸡蛋数K和需要测试的楼层数N。随着测试的进行，鸡蛋个数可能减少，楼层的搜索范围会减小，这就是状态的变化。
     * 「选择」其实就是去选择哪层楼扔鸡蛋。回顾刚才的线性扫描和二分思路，二分查找每次选择到楼层区间的中间去扔鸡蛋，而线性扫描选择一层层向上测试。不同的选择会造成状态的转移。
     *
     * 明确了「状态」和「选择」，动态规划的基本思路就形成了：肯定是个二维的dp数组或者带有两个状态参数的dp函数来表示状态转移；外加一个 for 循环来遍历所有选择，择最优的选择更新结果 ：
     * # 当前状态为 (K 个鸡蛋，N 层楼)
     * # 返回这个状态下的最优结果
     * def dp(K, N):
     *     int res
     *     for 1 <= i <= N:
     *         res = min(res, 这次在第 i 层楼扔鸡蛋)
     *     return res
     * 这段伪码还没有展示递归和状态转移
     *
     * 我们在第i层楼扔了鸡蛋之后，可能出现两种情况：鸡蛋碎了，鸡蛋没碎。注意，这时候状态转移就来了：
     * [如果鸡蛋碎了]，那么鸡蛋的个数K应该减一，搜索的楼层区间应该从[1..N]变为[1..i-1]共i-1层楼；
     * [如果鸡蛋没碎]，那么鸡蛋的个数K不变，搜索的楼层区间应该从 [1..N]变为[i+1..N]共N-i层楼。
     *
     * 令 (鸡蛋个数)K = 2, (楼层数)N = 8; 当 i = 4 时
     *                                      |        楼层
     *     没碎                              |        8
     * dp(k, n-i)   = dp(2, 8-4)        => {         7
     *                                      |        6
     *                                      |        5
     * dp(k, n)     = dp(2, 8)          =>        i  4
     *     碎了                              |        3
     * dp(k-1, i-1) = dp(1, 4-1)        => {         2
     *                                      |        1
     *
     *
     * PS：细心的读者可能会问，在第i层楼扔鸡蛋如果没碎，楼层的搜索区间缩小至上面的楼层，是不是应该包含第i层楼呀？不必，因为已经包含了。开头说了 F 是可以等于 0 的，向上递归后，第i层楼其实就相当于第 0 层，可以被取到，所以说并没有错误。
     *
     * 因为我们要求的是最坏情况下扔鸡蛋的次数，所以鸡蛋在第i层楼碎没碎，取决于那种情况的结果更大：
     * def dp(K, N):
     *     int res
     *     for 1 <= i <= N:
     *         # 最坏情况下的最少扔鸡蛋次数
     *         res = min(res,
     *                   max(dp(K - 1, i - 1), # 碎
     *                       dp(K, N - i)      # 没碎
     *                      ) + 1              # 在第 i 楼扔了一次
     *                  )
     *     return res
     *
     * base case
     * 很容易理解：当楼层数N等于 0 时，显然不需要扔鸡蛋；
     * 当鸡蛋数K为 1 时，显然只能线性扫描所有楼层：
     * def dp(K, N):
     *     if K == 1: return N
     *     if N == 0: return 0
     *     ...
     *
     * 算法的时间复杂度是多少呢？
     * 动态规划算法的时间复杂度就是子问题个数 × 函数本身的复杂度。
     * 函数本身的复杂度就是忽略递归部分的复杂度，这里dp函数中有一个 for 循环，所以函数本身的复杂度是 O(N)。
     * 子问题个数也就是不同状态组合的总数，显然是两个状态的乘积，也就是 O(KN)。
     *
     * 算法的总时间复杂度是 O(K*N^2),
     * 空间复杂度为子问题个数，即 O(KN)。
     *
     *
     * 这个问题很复杂，但是算法代码却十分简洁，这就是动态规划的特性，穷举加备忘录/DP table 优化，
     *
     */
    private static int[][] memo;

    static int eggDrop(int k, int n) {
        memo = new int[k + 1][n + 1];
        for (int[] one : memo) {
            Arrays.fill(one, -1);
        }
        return helpEggDrop(k, n);
    }

    private static int helpEggDrop(int k, int n) {
        // base case
        if (1 == k) {
            return n;
        }
        if (0 == n) {
            return 0;
        }
        // 避免重复计算
        if (-1 != memo[k][n]) {
            return memo[k][n];
        }
        // 穷举所有可能选择
        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            res = Math.min(res
                , Math.max(helpEggDrop(k - 1, i - 1) /* 碎 */
                    , helpEggDrop(k, n - i)             /* 没碎 */
                ) + 1                                  /* 在第 i 楼扔了一次 */
            );
        }
        // 计入备忘录
        memo[k][n] = res;
        return res;
    }

    /**
     * 首先，有读者可能不理解代码中为什么用一个 for 循环遍历楼层[1..N]，也许会把这个逻辑和之前探讨的线性扫描混为一谈。其实不是的，这只是在做一次「选择」。
     * 比方说你有 2 个鸡蛋，面对 10 层楼，你得拿一个鸡蛋去某一层楼扔对吧？那选择去哪一层楼扔呢？不知道，那就把这 10 层楼全试一遍。至于鸡蛋碎没碎，下次怎么选择不用你操心，有正确的状态转移，递归会算出每个选择的代价，我们取最优的那个就是最优解。
     *
     * 优化：
     * 其实，这个问题还有更好的解法，比如修改代码中的 for 循环为二分搜索，可以将时间复杂度降为 O(K*N*logN)；
     * 再改进动态规划解法可以将时间复杂度降为  O(KN)；
     * 使用数学方法解决，时间复杂度达到最优 O(K*logN)，空间复杂度达到 O(1)。
     *
     *
     * 二分的解法也有点误导性，你很可能以为它跟我们之前讨论的二分思路扔鸡蛋有关系，实际上没有半毛钱关系。
     * 能用二分搜索是因为状态转移方程的函数图像具有单调性，可以快速找到最小值。
     * min(max(...)) 这个函数图像时单调的
     *
     *
     * 我觉得吧，我们这种解法就够了：找状态，做选择，足够清晰易懂，可流程化，可举一反三。掌握这套框架学有余力的话，二分查找的优化应该可以看懂，之后的优化也就随缘吧。
     *
     * 优化1： 第一个二分优化是利用了dp函数的单调性，用二分查找技巧快速搜索答案。可以将时间复杂度降为 O(K*N*logN)；
     * 优化2： 第二种优化是巧妙地修改了状态转移方程，简化了求解了流程，但相应的，解题逻辑比较难以想到。可以将时间复杂度降为  O(KN)；
     * 优化3： 基于优化2，可以用一些数学方法和二分搜索进一步优化。时间复杂度达到最优 O(K*logN)，空间复杂度达到 O(1)。
     */
    /**
     *
     *         res = min(res,
     *                   max(dp(K - 1, i - 1), # 碎
     *                       dp(K, N - i)      # 没碎
     *                      ) + 1              # 在第 i 楼扔了一次
     *                  )
     * 这个状态转移函数为啥是单调递减的呢
     * 画图看一下
     * K /\
     *   |  dp(k, n-i)                                                              dp(k, n-i)
     *   |         \         /     |                                                   \
     *   |           \     /        } max(dp(k, n-i), dp(k-1, i-1))    单调递减的         \
     *   |             \ /         |                                   min(...)          \
     *   |            /  \                                             ===>               dp(k-1, i-1)
     *   |         /       \                                                                 \
     *   |       /                                                                             \
     *   |  dp(k-1, i-1)                                                                         \
     *   ----------------------------------------->
     *                      i =>                  N
     *
     *
     *
     * superEggDrop: 时间复杂度降为 O(K*N*logN)；
     *
     *
     *
     */
    static int superEggDrop(int k, int n) {
        memo = new int[k + 1][n + 1];
        for (int[] one : memo) {
            Arrays.fill(one, -1);
        }
        return helpSuperEggDrop(k, n);
    }

    private static int helpSuperEggDrop(int k, int n) {
        // base case
        if (1 == k) {
            return n;
        }
        if (0 == n) {
            return 0;
        }
        // 避免重复计算
        if (-1 != memo[k][n]) {
            return memo[k][n];
        }
        // 穷举所有可能选择
        int res = Integer.MAX_VALUE;
//        for (int i = 1; i <= n; i++) {
//            res = Math.min(res
//                , Math.max(helpEggDrop(k - 1, i - 1) /* 碎 */
//                    , helpEggDrop(k, n - i)             /* 没碎 */
//                ) + 1                                  /* 在第 i 楼扔了一次 */
//            );
//        }

        // upgrade: binary search
        int lo = 1, hi = n;
        while (lo <= hi) {
            int mid = lo + (hi-lo)/2;
            int broken = helpSuperEggDrop(k-1, mid-1); /* 碎 */
            int notBroken = helpSuperEggDrop(k, n-mid);   /* 没碎 */
            if (broken > notBroken) {
                hi = mid-1;
                res = Math.min(res, broken+1);
            } else {
                lo = mid+1;
                res = Math.min(res, notBroken+1);
            }
        }

        // 计入备忘录
        memo[k][n] = res;
        return res;
    }

    /**
     * upgrade: 重写状态转移
     *
     * -----------------------------------------------------------------------------------------------------------------
     * 前文 动态规划：不同的定义产生不同的解法 就提过，找动态规划的状态转移本就是见仁见智，比较玄学的事情。不同的状态定义可以衍生出不同的解法，其解法和复杂程度都可能有巨大差异。这里就是一个很好的例子。
     *
     * 之前定义的dp数组含义：
     * def dp(k, n) -> int
     * # 当前状态为 k 个鸡蛋，面对 n 层楼
     * # 返回这个状态下最少的扔鸡蛋次数
     *
     *
     * 用 dp 数组表示的话也是一样的：
     * dp[k][n] = m
     * # 当前状态为 k 个鸡蛋，面对 n 层楼
     * # 这个状态下最少的扔鸡蛋次数为 m
     *
     * 按照这个定义，就是确定当前的鸡蛋个数和面对的楼层数，就知道最小扔鸡蛋次数。最终我们想要的答案就是dp(K, N)的结果。
     * 这种思路下，肯定要穷举所有可能的扔法的，用二分搜索优化也只是做了「剪枝」，减小了搜索空间，但本质思路没有变，只不过是更聪明的穷举。
     * -----------------------------------------------------------------------------------------------------------------
     *
     * 现在，我们稍微修改dp数组的定义，确定当前的鸡蛋个数和最多允许的扔鸡蛋次数，就知道能够确定F的最高楼层数。
     *
     * 有点绕口，具体来说是这个意思：
     * dp[k][m] = n
     * # 当前有 k 个鸡蛋，可以尝试扔 m 次鸡蛋
     * # 这个状态下，最坏情况下最多能确切测试一栋 n 层的楼
     *
     * # 比如说 dp[1][7] = 7 表示：
     * # 现在有 1 个鸡蛋，允许你扔 7 次;
     * # 这个状态下最多给你 7 层楼，
     * # 使得你可以确定楼层 F 使得鸡蛋恰好摔不碎
     * # （一层一层线性探查嘛）
     *
     * 这其实就是我们原始思路的一个「反向」版本，我们先不管这种思路的状态转移怎么写，先来思考一下这种定义之下，最终想求的答案是什么？
     * 我们最终要求的其实是扔鸡蛋次数m，但是这时候m在状态之中而不是dp数组的结果，可以这样处理：
     * int superEggDrop(int K, int N) {
     *
     *     int m = 0;
     *     while (dp[K][m] < N) {
     *         m++;
     *         // 状态转移...
     *     }
     *     return m;
     * }
     *
     * 题目不是给你K鸡蛋，N层楼，让你求最坏情况下最少的测试次数m 吗？
     * while循环结束的条件是dp[K][m] == N，也就是给你K个鸡蛋，允许测试m次，最坏情况下最多能测试N层楼。
     * 注意看这两段描述，是完全一样的！所以说这样组织代码是正确的，关键就是状态转移方程怎么找呢？还得从我们原始的思路开始讲。之前的解法配了这样图帮助大家理解状态转移思路：
     *
     * 令 (鸡蛋个数)K = 2, (楼层数)N = 8; 当 i = 4 时
     *                                                        |        楼层
     *     没碎                                                |         8
     * dp(k, m-1) 层   = dp(k, n-i)   = dp(2, 8-4)        => {          7
     *                                                        |         6
     *                                                        |         5
     * 在这里扔了一次鸡蛋   dp(k, n)     = dp(2, 8)          =>        i   4
     *     碎了                                                |         3
     * dp(k-1, m-1) 层 = dp(k-1, i-1) = dp(1, 4-1)        => {           2
     *                                                        |          1
     *
     * 这个图描述的仅仅是某一个楼层i，原始解法还得线性或者二分扫描所有楼层，要求最大值、最小值。但是现在这种dp定义根本不需要这些了，基于下面两个事实：
     * 1、无论你在哪层楼扔鸡蛋，鸡蛋只可能摔碎或者没摔碎，碎了的话就测楼下，没碎的话就测楼上。
     * 2、无论你上楼还是下楼，总的楼层数 = 楼上的楼层数 + 楼下的楼层数 + 1（当前这层楼）。
     *
     * 根据这个特点，可以写出下面的状态转移方程： dp[k][m] = dp[k][m-1] + dp[k-1][m-1] + 1
     * dp[k][m - 1]就是楼上的楼层数，因为鸡蛋个数k不变，也就是鸡蛋没碎，扔鸡蛋次数m减一；
     * dp[k - 1][m - 1]就是楼下的楼层数，因为鸡蛋个数k减一，也就是鸡蛋碎了，同时扔鸡蛋次数m减一。
     * PS：这个m为什么要减一而不是加一？之前定义得很清楚，这个m是一个允许的次数上界，而不是扔了几次。
     *
     *
     * 至此，整个思路就完成了，只要把状态转移方程填进框架即可：
     * int superEggDrop(int K, int N) {
     *     // m 最多不会超过 N 次（线性扫描）
     *     int[][] dp = new int[K + 1][N + 1];
     *     // base case:
     *     // dp[0][..] = 0
     *     // dp[..][0] = 0
     *     // Java 默认初始化数组都为 0
     *     int m = 0;
     *     while (dp[K][m] < N) {
     *         m++;
     *         for (int k = 1; k <= K; k++)
     *             dp[k][m] = dp[k][m - 1] + dp[k - 1][m - 1] + 1;
     *     }
     *     return m;
     * }
     *
     *
     * 可以将时间复杂度降为  O(KN)；
     *
     * 看到这种代码形式就熟悉多了吧，因为我们要求的不是dp数组里的值，而是某个符合条件的索引m，所以用while循环来找到这个m而已。
     * 另外注意到dp[m][k]转移只和左边和左上的两个状态有关，所以很容易优化成一维dp数组，这里就不写了。
     */
    static int superEggDrop1(int k, int n) {
        // m 最多不会超过 N 次（线性扫描）
        int[][] dp = new int[k + 1][n + 1];

        // base case:
        // dp[0][..] = 0
        // dp[..][0] = 0
        // Java 默认初始化数组都为 0
        int m = 0;
        while (dp[k][m] < n) {
            m++;
            for (int i = 1; i <= k; i++) {
                dp[i][m] = dp[i][m - 1] + dp[i - 1][m - 1] + 1;
            }
        }
        return m;
    }
//    static int superEggDrop1(int K, int N) {
//        // m 最多不会超过 N 次（线性扫描）
//        int[][] dp = new int[K + 1][N + 1];
//        // base case:
//        // dp[0][..] = 0
//        // dp[..][0] = 0
//        // Java 默认初始化数组都为 0
//        int m = 0;
//        while (dp[K][m] < N) {
//            m++;
//            for (int k = 1; k <= K; k++)
//                dp[k][m] = dp[k][m - 1] + dp[k - 1][m - 1] + 1;
//        }
//        return m;
//    }

    /**
     * upgrade: 重写状态转移 + 用一些数学方法
     * 在刚才的思路之上，注意函数dp(m, k)是随着m单增的，因为鸡蛋个数k不变时，允许的测试次数越多，可测试的楼层就越高。
     * 这里又可以借助二分搜索算法快速逼近dp[K][m] == N这个终止条件，
     * 时间复杂度进一步下降为 O(KlogN)，
     *
     * 我们可以设g(k,m)等于...
     *
     * 不过可以肯定的是，根据二分搜索代替线性扫描 m 的取值，代码的大致框架肯定是修改穷举 m 的 for 循环：
     *
     * 简单总结一下吧，
     * 第一个二分优化是利用了dp函数的单调性，用二分查找技巧快速搜索答案；
     * 第二种优化是巧妙地修改了状态转移方程，简化了求解了流程，但相应的，解题逻辑比较难以想到；
     *
     * 后续还可以用一些数学方法和二分搜索进一步优化第二种解法
     */
    // TODO: fix
    static int superEggDrop2(int k, int n) {
        // m 最多不会超过 N 次（线性扫描）
        int[][] dp = new int[k + 1][n + 1];

        // base case:
        // dp[0][..] = 0
        // dp[..][0] = 0
        // Java 默认初始化数组都为 0
//        int m = 0;
//        while (dp[k][m] < n) {
//            m++;
//            for (int i = 1; i <= k; i++) {
//                dp[i][m] = dp[i][m - 1] + dp[i - 1][m - 1] + 1;
//            }
//        }

//        int lo = 1, hi = n;
//        while (lo < hi) {
//            int mid = lo + (hi-lo)/2;
//            if () {
//
//            }
//        }
//        return m;
        return 0;
    }

    private static void testEggDrop() {
        int k = 2, n = 100;
        int res = eggDrop(k, n);
        System.out.println(res);

        res = superEggDrop(k, n);
        System.out.println(res);

        res = superEggDrop1(k, n);
        System.out.println(res);
    }

    public static void main(String[] args) {
        // 经典动态规划：正则表达式
        testIsMatch();
        // 经典动态规划：高楼扔鸡蛋
        testEggDrop();
    }
}
