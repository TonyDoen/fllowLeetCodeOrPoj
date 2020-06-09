package me.meet.leetcode.hard;

import java.util.Arrays;

public final class OrderlyQueue {
    private OrderlyQueue(){}

    /**
     * A string S of lowercase letters is given.  Then, we may make any number of moves.
     * In each move, we choose one of the first K letters (starting from the left), remove it, and place it at the end of the string.
     * Return the lexicographically smallest string we could have after any number of moves.
     *
     * Example 1:
     * Input: S = "cba", K = 1
     * Output: "acb"
     * Explanation:
     * In the first move, we move the 1st character ("c") to the end, obtaining the string "bac".
     * In the second move, we move the 1st character ("b") to the end, obtaining the final result "acb".
     *
     * Example 2:
     * Input: S = "baaca", K = 3
     * Output: "aaabc"
     * Explanation:
     * In the first move, we move the 1st character ("b") to the end, obtaining the string "aacab".
     * In the second move, we move the 3rd character ("c") to the end, obtaining the final result "aaabc".
     *
     * Note:
     * 1 <= K <= S.length <= 1000
     * S consists of lowercase letters only.
     */
    /**
     * 题意：有序队列
     * 这道题给了我们一个只有小写字母的字符串，说是每次可以把前K个字母中的任意一个移动到末尾，让我们返回可以变换成的字母顺序最小的字符串。
     *
     * 思路：
     * 1. 刚开始看到的时候，博主感觉就是一个 BFS 遍历，对于每一个状态，都生成K个新的状态，然后将没有遍历过的加入 queue 中去遍历，然后维护一个全局最小的 res 即可，写完之后拿到 OJ 中去测试了，结果跪了，Time Limit Exceeded！
     *
     * 2. 正确的思路其实是跟K值有关的，若 K=1，其实只有K中不同的情况，我们可以都生成，然后比较出其中最小的那个返回即可。关键是 K>1 的时候，比较 tricky，其实是可以转换成有序的，即相当于直接对S串进行排序即可。
     *    我们就拿 S="53214", K=2 来举例吧，转换过程如下所示：
     *    5 3 2 1 4
     *    3 2 1 4 5
     *    3 1 4 5 2
     *    1 4 5 2 3
     *    1 5 2 3 4
     *    1 2 3 4 5
     *    虽然不知道如何严格的证明当 K>1 时，一定能转成有序的排序，但是博主试了几个例子，都是可以的，论坛上说是一种类似于冒泡排序 Bubble Sort 的过程。
     */
    static String orderlyQueue(String s, int k) {
        if (k > 1) {
            char[] s2 = s.toCharArray();
            Arrays.sort(s2);
            return new String(s2);
        }
        String res = s;
        for (int i = 1; i < s.length(); i++) {
            String tmp = s.substring(i) + s.substring(0, i);
            if (res.compareTo(tmp) > 0) res = tmp;
        }
        return res;
    }

    private static void testOrderlyQueue() {
        String src = "cba";
        String res = orderlyQueue(src, 1);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testOrderlyQueue();
    }
}
