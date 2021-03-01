package me.meet.labuladong._0;

public final class LC0518 {
    private LC0518() {
    }
    /**
     *  LeetCode 第 518 题 Coin Change 2，题目如下：
     *
     *  给定不同面额的硬币和一个总金额，写出函数来计算可以凑成总金额的硬币组合数。
     *  假设每一种面额的硬币有无限个。
     *  例子1：
     *  input:  amount = 5, coins = [1, 2, 5]
     *  output: 4
     *  explain:有4种方式可以凑成：
     *      5 = 5
     *      5 = 2 + 2 + 1
     *      5 = 2 + 1 + 1 + 1
     *      5 = 1 + 1 + 1 + 1 + 1
     *
     *  例子2：
     *  input:  amount = 3, coins = [2]
     *  output: 0
     *  explain:只有面额2的硬币凑不出金额3
     *
     *
     *  思路：
     *  我们可以把这个问题转化为背包问题的描述形式：
     *  有一个背包，最大容量为amount，有一系列物品coins，每个物品的重量为coins[i]，每个物品的数量无限。请问有多少种方法，能够把背包恰好装满？
     *
     *  这个问题和我们前面讲过的两个背包问题，有一个最大的区别就是，每个物品的数量是无限的，这也就是传说中的「完全背包问题」，
     *  没啥高大上的，无非就是状态转移方程有一点变化而已。
     *
     *  <1> 第一步要明确两点，「状态」和「选择」。
     *  状态有两个，就是「背包的容量」和「可选择的物品」，选择就是「装进背包」或者「不装进背包」。
     *  明白了状态和选择，动态规划问题基本上就解决了，只要往这个框架套就完事儿了：
     *  for 状态1 in 状态1的所有取值：
     *     for 状态2 in 状态2的所有取值：
     *         for ...
     *             dp[状态1][状态2][...] = 计算(选择1, 选择2...)
     *
     *  <2> 第二步要明确dp数组的定义。
     *  首先看看刚才找到的「状态」，有两个，也就是说我们需要一个二维dp数组。
     *  dp[i][j]的定义如下： 若只使用前i个物品，当背包容量为j时，有dp[i][j]种方法可以装满背包。
     *
     *  换句话说，翻译回我们题目的意思就是： 若只使用coins中的前i个硬币的面值，若想凑出金额j，有dp[i][j]种凑法。
     *
     *  base case 为dp[0][..] = 0， dp[..][0] = 1。
     *  因为如果不使用任何硬币面值，就无法凑出任何金额；如果凑出的目标金额为 0，那么“无为而治”就是唯一的一种凑法。
     *  我们最终想得到的答案就是dp[N][amount]，其中N为coins数组的大小。
     *
     *  int dp[N+1][amount+1]
     *  dp[0][..] = 0
     *  dp[..][0] = 1
     *
     *  for i in [1..N]:
     *     for j in [1..amount]:
     *         把物品 i 装进背包,
     *         不把物品 i 装进背包
     *  return dp[N][amount]
     *
     *  <3> 第三步，根据「选择」，思考状态转移的逻辑。
     *  注意，我们这个问题的特殊点在于物品的数量是无限的，所以这里和之前写的背包问题文章有所不同。
     *  如果你不把这第i个物品装入背包，也就是说你不使用coins[i]这个面值的硬币，那么凑出面额j的方法数dp[i][j]应该等于dp[i-1][j]，继承之前的结果。
     *  如果你把这第i个物品装入了背包，也就是说你使用coins[i]这个面值的硬币，那么dp[i][j]应该等于dp[i][j-coins[i-1]]。
     *
     *  首先由于i是从 1 开始的，所以coins的索引是i-1时表示第i个硬币的面值。
     *  dp[i][j-coins[i-1]]也不难理解，如果你决定使用这个面值的硬币，那么就应该关注如何凑出金额j - coins[i-1]。
     *  比如说，你想用面值为 2 的硬币凑出金额 5，那么如果你知道了凑出金额 3 的方法，再加上一枚面额为 2 的硬币，不就可以凑出 5 了嘛。
     *
     *  综上就是两种选择，而我们想求的dp[i][j]是「共有多少种凑法」，所以dp[i][j]的值应该是以上两种选择的结果之和：
     *  for (int i = 1; i <= n; i++) {
     *     for (int j = 1; j <= amount; j++) {
     *         if (j - coins[i-1] >= 0)
     *             dp[i][j] = dp[i - 1][j]
     *                      + dp[i][j-coins[i-1]];
     *  return dp[N][W]
     *
     *
     *  时间复杂度 O(N*amount)，
     *  空间复杂度 O(N*amount)。
     *
     */
    static int change(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        // base case
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= amount; j++) {
                if (j - coins[i - 1] >= 0) {
                    dp[i][j] = dp[i - 1][j]
                        + dp[i][j - coins[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][amount];
    }

    /**
     * 压缩状态
     * 通过观察可以发现，dp数组的转移只和dp[i][..]和dp[i-1][..]有关，所以可以压缩状态，进一步降低算法的空间复杂度：
     *
     * 时间复杂度 O(N*amount)，
     * 空间复杂度 O(amount)。
     */
    static int changeCompress(int amount, int[] coins) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        dp[0] = 1; // base case
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= amount; j++) {
                if (j - coins[i] >= 0) {
                    dp[j] = dp[j] + dp[j - coins[i]];
                }
            }
        }
        return dp[amount];
    }

    private static void testChange() {
        int amount = 5;
        int[] coins = new int[]{1, 2, 5};
        int res = change(amount, coins);
        System.out.println(res);

        res = changeCompress(amount, coins);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testChange();
    }
}
