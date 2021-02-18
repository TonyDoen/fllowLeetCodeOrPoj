package me.meet.labuladong._0;

public final class LC0188 {
    private LC0188() {
    }
    /**
     * LeetCode 188 题,
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     * 涉及一个算法来计算你所能获得的最大利润，你最多可以完成 k 笔交易。
     * 注意：
     * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
     *
     * 示例1：
     * input: [2, 4, 1]; k = 2
     * output: 2
     * explain: 在第 1 天（股票价格 = 2）的时候买入，在第 2 天（股票价格 = 4）的时候卖出，交易利润 4 - 2 = 2
     *
     * 示例2：
     * input: [3, 2, 6, 5, 0, 3]; k = 2
     * output: 7
     * explain: 在第 2 天（股票价格 = 2）的时候买入
     *        ，在第 3 天（股票价格 = 6）的时候卖出，交易利润 6 - 2 = 4。
     *        ，在第 5 天（股票价格 = 0）的时候买入，
     *        ，在第 6 天（股票价格 = 3）的时候卖出，交易利润 3 - 0 = 3。
     *
     *
     */
    /**
     * 思路：
     * 我们不用递归思想进行穷举,而是利用「状态」进行穷举。我们具体到每一天,看看总共有几种可能的「状态」,
     * 再找出每个「状态」对应的「选择」。我们要穷举所有「状态」,穷举的目的是根据对应的「选择」更新状态。
     * 听起来抽象,你只要记住「状态」和「选择」两个词就行,
     *
     * for 状态1 in 状态1所有取值:
     *     for 状态2 in 状态2所有取值:
     *         for ...
     *             dp[状态1][状态2][...] = 择优(选择1, 选择2, ...)
     *
     * 每天交易有3种「选择」： 买入(buy), 卖出(sell), 无操作(rest)
     * 有限制情况：
     * <1> sell 必须在 buy 之后; buy 必须在 sell 之后。
     * <2> rest 操作分2种状态， buy 之后 rest (持有了股票); 一种是 sell 之后的 rest (没有持有股票)
     * <3> 交易次数 k 限制，buy 还只能在 k > 0 的前提下操作
     *
     *
     * 这个问题有3种「状态」： 天数, 允许交易的最大次数, 当前的持有状态(rest的状态, 1持有, 0没有持有)
     *
     * 用一个三维数组可以装下这些状态 dp[i][k][j];  i >= 0 && i <= n-1 && k >= 1 && k <= K && j = {0,1};
     * n : 天数
     * k : 允许交易的最大次数
     * j : 当前的持有状态(rest的状态, 1持有, 0没有持有)
     *
     * for 0 <= i < n :
     *     for 1 <= k <= K :
     *         for j in {0, 1} :
     *             dp[i][k][j] = max(buy, sell, rest)
     *
     *
     * 我们写一下状态转移方程
     * dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i])
     *               max( 选择 rest   ,  选择 sell )
     * 解释:今天我没有持有股票,有两种可能:
     * 要么是我昨天就没有持有,然后今天选择 rest,所以我今天还是没有持有;
     * 要么是我昨天持有股票,但是今天我 sell 了,所以我今天没有持有股票了。
     *
     * dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
     *               max( 选择 rest   ,  选择 buy )
     * 解释:今天我持有着股票,有两种可能:
     * 要么我昨天就持有着股票,然后今天选择 rest,所以我今天还持有着股票;
     * 要么我昨天本没有持有,但今天我选择 buy,所以今天我就持有股票了。
     *
     *
     * 最后一点点,就是定义 base case,即最简单的情况
     * dp[-1][k][0] = 0           解释:因为 i 是从 0 开始的,所以 i = -1 意味着还没有开始,这时候的利润当然是 0 。
     * dp[-1][k][1] = -infinity   解释:还没开始的时候,是不可能持有股票的,用负无穷表示这种不可能。
     * dp[i][0][0] = 0            解释:因为 k 是从 1 开始的,所以 k = 0 意味着根本不允许交易,这时候利润当然是 0 。
     * dp[i][0][1] = -infinity    解释:不允许交易的情况下,是不可能持有股票的,用负无穷表示这种不可能 。
     *
     *
     * 综上
     * base case:
     * dp[-1][k][0] = dp[i][0][0] = 0
     * dp[-1][k][1] = dp[i][0][1] = -infinity
     *
     * 状态转移方程:
     * dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i])
     * dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
     */

    /**
     * 第一题， k=1
     * dp[-1][1][0] = dp[i][0][0] = 0
     * dp[-1][1][1] = dp[i][0][1] = -infinity
     *
     * dp[i][1][0] = max(dp[i-1][1][0], dp[i-1][1][1] + prices[i])
     * dp[i][1][1] = max(dp[i-1][1][1], dp[i-1][0][0] - prices[i]) = max(dp[i-1][1][1], -prices[i])
     *
     * k=1 固定值，对状态转移没有影响
     * dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
     * dp[i][1] = max(dp[i-1][1], -prices[i])
     *
     */
    static int sellStockI(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 == -1) {
                dp[i][0] = 0;          // dp[i][0]=max(dp[-1][0], dp[-1][1] + prices[i])=max(0, -infinity + prices[i])
                dp[i][1] = -prices[i]; // dp[i][1]=max(dp[-1][1], dp[-1][0] - prices[i])=max(-infinity, 0 - prices[i])
                continue;
            }

            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[n - 1][0];
    }

    static int sellStockIUp(int[] prices) {
        int dp0 = 0, dp1 = Integer.MIN_VALUE; // base case: dp[-1][0] = 0, dp[-1][1] = -infinity
        for (int price : prices) {
            dp0 = Math.max(dp0, dp1 + price); // dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
            dp1 = Math.max(dp1, -price);      // dp[i][1] = max(dp[i-1][1], -prices[i])
        }
        return dp0;
    }

    /**
     * 第二题， k=+infinity
     * dp[-1][+infinity][0] = dp[i][0][0] = 0
     * dp[-1][+infinity][1] = dp[i][0][1] = -infinity
     *
     * 状态转移方程:
     * dp[i][+infinity][0] = max(dp[i-1][+infinity][0], dp[i-1][+infinity][1] + prices[i])
     * dp[i][+infinity][1] = max(dp[i-1][+infinity][1], dp[i-1][+infinity-1][0] - prices[i])
     *
     * k=+infinity 固定值，对状态转移没有影响
     * dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
     * dp[i][1] = max(dp[i-1][1], dp[i-1][0] - prices[i])
     */
    static int sellStockII(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 == -1) {
                dp[i][0] = 0;          // dp[i][0]=max(dp[-1][0], dp[-1][1] + prices[i])=max(0, -infinity + prices[i])
                dp[i][1] = -prices[i]; // dp[i][1]=max(dp[-1][1], dp[-1][0] - prices[i])=max(-infinity, 0 - prices[i])
                continue;
            }

            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    static int sellStockIIUp(int[] prices) {
        int dp0 = 0, dp1 = Integer.MIN_VALUE;
        for (int price : prices) {
            int tmp = dp0;
            dp0 = Math.max(dp0, dp1 + price);
            dp1 = Math.max(dp1, tmp - price);
        }
        return dp0;
    }

    /**
     * 第三题,k = +infinity with cooldown
     * 每次 sell 之后要等一天才能继续交易。只要把这个特点融入上一题的状态转移方程即可
     *
     * k=+infinity 固定值，对状态转移没有影响
     * dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
     * dp[i][1] = max(dp[i-1][1], dp[i-2][0] - prices[i])   // 解释:第 i 天选择 buy 的时候,要从 i-2 的状态转移,而不是 i-1 。
     */
    static int sellStockIII(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 == -1) {
                dp[i][0] = 0;          // dp[i][0]=max(dp[-1][0], dp[-1][1] + prices[i])=max(0, -infinity + prices[i])
                dp[i][1] = -prices[i]; // dp[i][1]=max(dp[-1][1], dp[-2][0] - prices[i])=max(-infinity, 0 - prices[i])
                continue;
            }
            if (i - 1 == 0) {
                dp[i][0] = Math.max(0, -prices[i - 1] + prices[i]); // dp[i][0]=max(dp[0][0], dp[0][1] + prices[i])=max(0, -prices[i-1] + prices[i])
                dp[i][1] = Math.max(-prices[i - 1], -prices[i]);    // dp[i][1]=max(dp[0][1], dp[-1][0] - prices[i])=max(-prices[i-1], -prices[i])
                continue;
            }

            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    static int sellStockIIIUp(int[] prices) {
        int dpPre = 0, dp0 = 0, dp1 = Integer.MIN_VALUE;
        for (int price : prices) {
            int tmp = dp0;
            dp0 = Math.max(dp0, dp1 + price);
            dp1 = Math.max(dp1, dpPre - price);
            dpPre = tmp;
        }
        return dp0;
    }

    /**
     * 第四题,k = +infinity with fee
     * 每次交易要支付手续费,只要把手续费从利润中减去即可。只要把这个特点融入上一题的状态转移方程即可
     *
     * k=+infinity 固定值，对状态转移没有影响
     * dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
     * dp[i][1] = max(dp[i-1][1], dp[i-1][0] - prices[i] - fee)   // 解释:相当于买入股票的价格升高了。在第一个式子里减也是一样的,相当于卖出股票的价格减小了。
     */
    static int sellStockIV(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 == -1) {
                dp[i][0] = 0;          // dp[i][0]=max(dp[-1][0], dp[-1][1] + prices[i])=max(0, -infinity + prices[i])
                dp[i][1] = -prices[i]; // dp[i][1]=max(dp[-1][1], dp[-1][0] - prices[i])=max(-infinity, 0 - prices[i])
                continue;
            }

            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i] - fee);
        }
        return dp[n - 1][0];
    }

    static int sellStockIVUp(int[] prices, int fee) {
        int dp0 = 0, dp1 = Integer.MIN_VALUE;
        for (int price : prices) {
            int tmp = dp0;
            dp0 = Math.max(dp0, dp1+price);
            dp1 = Math.max(dp1, tmp-price-fee);
        }
        return dp0;
    }

    /**
     * 第五题,k = 2
     * k = 2 和前面题目的情况稍微不同,因为上面的情况都和 k 的关系不太大。
     * 要么 k 是正无穷,状态转移和 k 没关系了;要么 k = 1,跟 k = 0 这个 base case 挨得近,最后也没有存在感。
     * 这道题 k = 2 和后面要讲的 k 是任意正整数的情况中,对 k 的处理就凸显出来了。
     *
     * 前面总结的「穷举框架」吗?就是说我们必须穷举所有状态。其实我们之前的解法,都在穷举所有状态,只是之前的题目中 k 都被化简掉了。比如说第一题,k = 1
     * int max_k = 2;
     * int[][][] dp = new int[n][max_k + 1][2];
     * for (int i = 0; i < n; i++) {
     *     for (int k = max_k; k >= 1; k--) {
     *         if (i - 1 == -1) { //处理 base case  }
     *         dp[i][k][0]=max(dp[i-1][k][0],dp[i-1][k][1]+prices[i]);
     *         dp[i][k][1]=max(dp[i-1][k][1],dp[i-1][k-1][0]-prices[i]);
     *     }
     * }
     *
     * 这里 k 取值范围比较小,所以可以不用 for 循环,直接把 k = 1 和 2 的情况 全部列举出来也可以:
     * dp[i][2][0] = max(dp[i-1][2][0], dp[i-1][2][1] + prices[i])
     * dp[i][2][1] = max(dp[i-1][2][1], dp[i-1][1][0] - prices[i])
     * dp[i][1][0] = max(dp[i-1][1][0], dp[i-1][1][1] + prices[i])
     * dp[i][1][1] = max(dp[i-1][1][1], -prices[i])
     *
     *
     */
    static int sellStockVUp(int[] prices) {
        int dp10 = 0, dp11 = Integer.MIN_VALUE, dp20 = 0, dp21 = Integer.MIN_VALUE;
        for (int price : prices) {
            dp20 = Math.max(dp20, dp21 + price);
            dp21 = Math.max(dp21, dp10 - price);
            dp10 = Math.max(dp10, dp11 + price);
            dp11 = Math.max(dp11, -price);
        }
        return dp20;
    }

    /**
     * 第六题,k = any integer
     * 有了上一题 k = 2 的铺垫,这题应该和上一题的第一个解法没啥区别。
     *
     * 原来是传入的 k 值会非常大,dp 数组太大了。交易次数 k 最多有多大呢?
     * 一次交易由买入和卖出构成,至少需要两天。所以说有效的限制 k 应该不超过 n/2,
     * 如果超过,就没有约束作用了,相当于 k = +infinity。这种情况是之前解决过的。
     *
     *
     * 最后一点点,就是定义 base case,即最简单的情况
     * dp[-1][k][0] = 0           解释:因为 i 是从 0 开始的,所以 i = -1 意味着还没有开始,这时候的利润当然是 0 。
     * dp[-1][k][1] = -infinity   解释:还没开始的时候,是不可能持有股票的,用负无穷表示这种不可能。
     * dp[i][0][0] = 0            解释:因为 k 是从 1 开始的,所以 k = 0 意味着根本不允许交易,这时候利润当然是 0 。
     * dp[i][0][1] = -infinity    解释:不允许交易的情况下,是不可能持有股票的,用负无穷表示这种不可能 。
     */
    static int sellStockVIUp(int[] prices, int maxK) {
        int n = prices.length;
        if (maxK > n / 2) {
            // 相当于 k = +infinity
            int dp0 = 0, dp1 = Integer.MIN_VALUE;
            for (int price : prices) {
                int tmp = dp0;
                dp0 = Math.max(dp0, dp1 + price);
                dp1 = Math.max(dp1, tmp - price);
            }
            return dp0;
        } else {
            // k 的处理
            int[][][] dp = new int[n][maxK + 1][2];
            for (int i = 0; i < n; i++) {
                for (int k = maxK; k >= 1; k--) {
                    if (i - 1 == -1) {
                        //处理 base case
                        dp[i][k][0] = 0;          // dp[i][k][0]=max(dp[-1][k][0], dp[-1][k][1] + prices[0])=max(0, -infinity+prices[0])
                        dp[i][k][1] = -prices[i]; // dp[i][k][1]=max(dp[-1][k][1], dp[-1][k-1][0] - prices[0])=max(-infinity, 0-prices[0])
                        continue;
                    }
                    dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
                    dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i]);
                }
            }
            return dp[n - 1][maxK][0];
        }
    }

    private static void testSellStock() {
        int[] prices = new int[]{3, 2, 6, 5, 0, 3};
        int res1 = sellStockI(prices);
        System.out.println(res1);

        int res2 = sellStockIUp(prices);
        System.out.println(res2);

        int res3 = sellStockII(prices);
        System.out.println(res3);

        int res4 = sellStockIIUp(prices);
        System.out.println(res4);

        int res5 = sellStockIII(prices);
        System.out.println(res5);

        int res6 = sellStockIIIUp(prices);
        System.out.println(res6);

        int fee = 1;
        int res7 = sellStockIV(prices, fee);
        System.out.println(res7);

        int res8 = sellStockIVUp(prices, fee);
        System.out.println(res8);

        int res9 = sellStockVUp(prices);
        System.out.println(res9);

        int maxK = 2;
        int res10 = sellStockVIUp(prices, maxK);
        System.out.println(res10);
    }

    public static void main(String[] args) {
        testSellStock();
    }

    /**
     * 给大家讲了如何通过状态转移的方法解决复杂的问题,用一个状态转移方程秒杀了 6 道股票买卖问题,现在想想,其实也不算难对吧?这已经属于动态规划问题中较困难的
     *
     * 关键就在于列举出所有可能的「状态」,然后想想怎么穷举更新这些「状态」。
     * 一般用一个多维 dp 数组储存这些状态,从 base case 开始向后推进,推进到最后的状态,就是我们想要的答案。
     *
     * 具体到股票买卖问题,我们发现了三个状态,使用了一个三维数组,无非还是穷举 + 更新,不过我们可以说的高大上一点,这叫「三维 DP」
     */
}
