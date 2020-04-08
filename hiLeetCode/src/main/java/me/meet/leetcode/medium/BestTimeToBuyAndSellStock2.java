package me.meet.leetcode.medium;

public final class BestTimeToBuyAndSellStock2 {
    private BestTimeToBuyAndSellStock2() {
    }
    /**
     * Say you have an array for which the i th element is 
     * the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit. 
     * You may complete as many transactions as you like 
     * (ie, buy one and sell one share of the stock multiple times).
     *
     * However, you may not engage in multiple transactions at the same time
     * (ie, you must sell the stock before you buy again).
     */
    /**
     * 题意：
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * 
     * 示例 1:
     * 输入: [7,1,5,3,6,4]
     * 输出: 7
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，
     * 在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     * 随后，在第 4 天（股票价格 = 3）的时候买入，
     * 在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
     * 
     * 示例 2:
     * 输入: [1,2,3,4,5]
     * 输出: 4
     * 解释: 在第 1 天（股票价格 = 1）的时候买入，
     * 在第 5 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     * 注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
     * 因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
     * 
     * 示例 3:
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     * 
     * 
     * 思路：
     * 1、初始化收益为0
     * 2、从左到右遍历，发现当前元素比刚遍历的元素大，即可取得收益，累加结果即为最大收益
     */
    static int getBestTimeToBuyAndSellStock0(int[] prices) {
        if (null == prices || prices.length < 2) {
            return 0;
        }
        int maxProfit = 0;
        for (int i = 1, length = prices.length; i < length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxProfit += (prices[i] - prices[i - 1]);
            }
        }
        return maxProfit;
    }

    private static void testGetBestTimeToBuyAndSellStock0() {
//        int[] arr = {7, 1, 5, 3, 6, 4};
        int[] arr = {1, 2, 5, 4, 5};
        int res = getBestTimeToBuyAndSellStock0(arr);
        System.out.println(res);
    }


    /**
     * Say you have an array for which the i th element
     * is the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit.
     * You may complete at most two transactions.
     *
     * Note:
     * You may not engage in multiple transactions at the same time
     * (ie, you must sell the stock before you buy again).
     */
    /**
     * 题意：（与上面的区别是只能完成 2 笔交易）
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 2 笔交易。
     * 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * 
     * 思路：
     * 思路：(贪心算法)
     * 1、用 sell1 表示初始时的利润为0，
     * buy1 表示最便宜股票的价格，
     * 用 sell2 表示交易两次的利润，
     * buy2 表示第一次售出股票后，再买入后面某一天股票后的收益
     * 2、从左到右遍历，buy1表示前些天买入最便宜股票的股价
     * sell1保存前些天买入最便宜股票后再在股票最高时卖出股票的最大利润
     * 3、buy2表示第一次售出股票后，再买入后面某一天股票后的净收益
     * sell2表示二次买卖或者一次买卖的最大收益
     * (buy2之前的净收益+curPrice今天卖出股票后收益)
     */
    static int getBestTimeToBuyAndSellStock2(int[] prices) {
        if (null == prices || prices.length < 2) {
            return 0;
        }

        int buy1 = Integer.MIN_VALUE, sell1 = 0, buy2 = Integer.MIN_VALUE, sell2 = 0;
        for (int curPrice : prices) {
            // 最便宜的股票价格
            buy1 = Math.max(buy1, -curPrice);
            // 一次交易的最大收益
            sell1 = Math.max(sell1, curPrice + buy1);
            // 之前天先进行第一次交易后，在买入今天股票后的净利润
            buy2 = Math.max(buy2, sell1 - curPrice);
            // 二次交易的收益(卖出今天股票后的收益)
            sell2 = Math.max(sell2, curPrice + buy2);
        }
        return sell2;
    }

    private static void testGetBestTimeToBuyAndSellStock2() {
        int[] arr = {1, 2, 5, 4, 5};
        int res = getBestTimeToBuyAndSellStock2(arr);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testGetBestTimeToBuyAndSellStock0();
        testGetBestTimeToBuyAndSellStock2();
    }
}
