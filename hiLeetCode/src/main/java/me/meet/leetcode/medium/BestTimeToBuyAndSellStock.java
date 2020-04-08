package me.meet.leetcode.medium;

public final class BestTimeToBuyAndSellStock {
    private BestTimeToBuyAndSellStock() {}
    /**
     * Say you have an array for which the i th element is the price of a given stock on day i.
     * If you were only permitted to complete at most one transaction
     * (ie, buy one and sell one share of the stock), 
     * design an algorithm to find the maximum profit.
     */
    /**
     * 题意：
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 如果你最多只允许完成一笔交易（即买入和卖出一支股票），
     * 设计一个算法来计算你所能获取的最大利润。
     * 注意你不能在买入股票前卖出股票。
     * 
     * 示例 1:
     * 输入: [7,1,5,3,6,4]
     * 输出: 5
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
     *
     * 示例 2:
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。     
     *
     * 思路1：(贪心算法)
     * 1、初始化数组的第一个元素为最低价格
     * 2、从左到右遍历，更新最低价格，更新最大收益值
     * 
     */
    static int getBestTimeToBuyAndSellStock0(int[] prices) {
        if (null == prices || prices.length < 2) {
            return 0;
        }
        int min = prices[0], maxProfit = 0;
        for (int cur : prices) {
            if (cur <= min) { // 更新最低价格
                min = cur;
            } else { // 更新利润
                maxProfit = Math.max(maxProfit, cur - min);
            }
        }
        return maxProfit;
    }

    private static void testGetBestTimeToBuyAndSellStock0() {
        int[] arr = {7, 1, 5, 3, 6, 4};
        int res = getBestTimeToBuyAndSellStock0(arr);
        System.out.println(res);
    }

    /**
     * 思路2：
     * 1、用sell表示初始时的利润为0，buy表示最便宜股票的价格
     * 2、从左到右遍历，buy表示前些天买入最便宜股票的股价
     * 3、sell保存前些天买入最便宜股票后再在股票最高时卖出股票的最大利润
     * 延伸：
     * 此思路可类推进行两次交易的最大利益。
     */
    static int getBestTimeToBuyAndSellStock1(int[] prices) {
        if (null == prices || prices.length < 2) {
            return 0;
        }
        // 记录最便宜股票的价格(买入即利润为负值); // 记录利润
        int buy = -prices[0], maxProfit = 0;
        for (int cur : prices) {
            buy = Math.max(buy, -cur);                  // 更新最低价格
            maxProfit = Math.max(maxProfit, cur + buy); // 更新利润
        }
        return maxProfit;
    }

    private static void testGetBestTimeToBuyAndSellStock1() {
        int[] arr = {7, 1, 5, 3, 6, 4};
        int res = getBestTimeToBuyAndSellStock1(arr);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testGetBestTimeToBuyAndSellStock0();
        testGetBestTimeToBuyAndSellStock1();
    }
}
