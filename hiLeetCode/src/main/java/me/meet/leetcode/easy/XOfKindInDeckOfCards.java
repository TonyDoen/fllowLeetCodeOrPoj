package me.meet.leetcode.easy;

import java.util.HashMap;

public final class XOfKindInDeckOfCards {
    private XOfKindInDeckOfCards() {
    }
    /**
     * X of a Kind in a Deck of Cards
     * In a deck of cards, each card has an integer written on it.
     * Return true if and only if you can choose X >= 2 such that it is possible to split the entire deck into 1 or more groups of cards, where:
     * Each group has exactly X cards.
     * All the cards in each group have the same integer.
     *
     * Example 1:
     * Input: [1,2,3,4,4,3,2,1]
     * Output: true
     * Explanation: Possible partition [1,1],[2,2],[3,3],[4,4]
     *
     * Example 2:
     * Input: [1,1,1,2,2,2,3,3]
     * Output: false Explanation: No possible partition.
     *
     * Example 3:
     * Input: [1]
     * Output: false Explanation: No possible partition.
     *
     * Example 4:
     * Input: [1,1]
     * Output: true Explanation: Possible partition [1,1]
     *
     * Example 5:
     * Input: [1,1,2,2,2,2]
     * Output: true Explanation: Possible partition [1,1],[2,2],[2,2]
     *
     * Note:
     * 1 <= deck.length <= 10000
     * 0 <= deck[i] < 10000
     */

    /**
     * 题意：一副牌中的X
     * 思路：这道题给了一堆牌，问我们能不能将这副牌分成若干堆，每堆均有X个，且每堆的牌数字都相同（这里不考虑花色）。既然要将相同的牌归类，肯定要统计每种牌出现的个数，所以使用一个 HashMap 来建立牌跟其出现次数之间的映射。由于每堆X个，则若果某张牌的个数小于X，则肯定无法分，所以X的范围是可以确定的，为 [2, mn]，其中 mn 是数量最少的牌的个数。遍历一遍 HashMap，找出最小的映射值 mn，若 mn 小于2，可以直接返回 false。否则就从2遍历到 mn，依次来检验候选值X。检验的方法是看其他每种牌的个数是否能整除候选值X，不一定非要相等，比如 [1, 1, 2, 2, 2, 2], K=2 时就可以分为三堆 [1, 1], [2, 2], [2, 2]，即相同的牌也可以分到其他堆里，所以只要每种牌的个数能整除X即可，一旦有牌数不能整除X了，则当前X一定不行，还得继续检验下一个X值；若所有牌数都能整除X，可以返回 true。循环结束后返回 false，
     */
    static boolean hasGroupsSizeX1(int[] deck) {
        HashMap<Integer, Integer> cardCnt = new HashMap<>();
        for (int card : deck) {
            cardCnt.put(card, cardCnt.getOrDefault(card, 0) + 1);
        }

        int mn = Integer.MAX_VALUE;
        for (HashMap.Entry<Integer, Integer> entry : cardCnt.entrySet()) {
            mn = Math.min(mn, entry.getValue());
        }

        if (mn < 2) {
            return false;
        }
        for (int i = 2; i <= mn; i++) {
            boolean success = true;
            for (HashMap.Entry<Integer, Integer> entry : cardCnt.entrySet()) {
                if (0 != entry.getValue() % i) {
                    success = false;
                    break;
                }
            }
            if (success) {
                return true;
            }
        }
        return false;
    }

    /**
     * 思路：
     * 使用了一个基于最大公约数 Greatest Common Divisor 的解法，写起来很简洁，但需要记住最大公约函数的写法，或者直接使用内置的 gcd 函数（感觉有点耍赖哈～）。其实原理都差不多，
     * 这里是找每种牌数之间的最大公约数，只要这个 gcd 是大于1的，就表示可以找到符合题意的X，
     */
    static boolean hasGroupsSizeX2(int[] deck) {
        HashMap<Integer, Integer> cardCnt = new HashMap<>();
        for (int card : deck) {
            cardCnt.put(card, cardCnt.getOrDefault(card, 0) + 1);
        }
        int res = 0;
        for (HashMap.Entry<Integer, Integer> entry : cardCnt.entrySet()) {
            res = gcd(entry.getValue(), res);
        }
        return res > 1;
    }

    private static int gcd(int a, int b) {
        return 0 == a ? b : gcd(b % a, a); // 最大公约数 Greatest Common Divisor
    }

    public static void main(String[] args) {
        // [1,2,3,4,4,3,2,1]
        int[] deck = new int[]{1, 2, 3, 4, 4, 3, 2, 1};
        boolean res = hasGroupsSizeX1(deck);
        System.out.println(res);

        res = hasGroupsSizeX2(deck);
        System.out.println(res);

    }
}
