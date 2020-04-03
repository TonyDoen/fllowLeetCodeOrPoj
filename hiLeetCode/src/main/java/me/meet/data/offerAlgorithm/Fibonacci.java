package me.meet.data.offerAlgorithm;

public final class Fibonacci {
    private Fibonacci() {
    }

    /**
     * 007-斐波拉契数列
     *
     * 要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。n<=39
     *
     * value: 0, 1, 1, 2, 3, 5, 8, ...
     * index: 0  1  2  3  4  5  6  ...
     *
     * 利用动态规划算法求解此问题：
     * 1. 阶段划分，状态表示，
     * 2. 状态转移方程：F(n) = F(n-1) + F(n-2)
     * 3. 边界条件：F(0) = 0, F(1) = 1.
     *
     * 解决此问题的三种方式：
     * 1. 递归-时间复杂度 O(2^n)
     * 2. 备忘录递归-时间复杂度 O(n)
     * 3. 自底向上迭代-时间复杂度 O(n)
     */
    static int getFibonacciNumber(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (n < 2) {
            return n;
        }
        return getFibonacciNumber(n - 1) + getFibonacciNumber(n - 2);
    }

    static int getFibonacciNumber2(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (n < 2) {
            return n;
        }
        int[] memo = new int[n+1]; // 2. 备忘录递归-时间复杂度 O(n)
        return helpGetFibonacciNumber2(memo, n);
    }
    private static int helpGetFibonacciNumber2(int[] memo, int n) {
        if (0 == n) {
            return 0;
        }
        if (0 != memo[n]) {
            return memo[n];
        }
        if (n < 3) {
            memo[n] = 1;
        } else {
            memo[n] = helpGetFibonacciNumber2(memo, n-1) + helpGetFibonacciNumber2(memo, n-2);
        }
        return memo[n];
    }

    static int getFibonacciNumber3(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (n < 2) {
            return n;
        }
        int result = 0, first = 0, second = 1;
        for (int i = 1; i < n; i++) {
            result = first+second;
            first = second;
            second = result;
        }
        return result;
    }

    private static void testGetFibonacciNumber() {
        int res = getFibonacciNumber(5);
        System.out.println(res);
        res = getFibonacciNumber2(5);
        System.out.println(res);
        res = getFibonacciNumber3(5);
        System.out.println(res);
    }

    /**
     * 008-跳台阶
     * 一只青蛙一次可以跳上1级台阶，也可以跳上3级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     */
    static int jumpFrog(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        int[] dp = new int[n+1];
        dp[1] = 1;
        dp[2] = 1;
        dp[3] = 2;
        for (int i = 4; i <= n; i++) {
            dp[i] = dp[i-1]+dp[i-3];
        }
        return dp[n];
    }

    private static void testJumpFrog() {
        int res = jumpFrog(5);
        System.out.println(res);
    }

    /**
     * 009-变态跳台阶
     * 一只青蛙一次可以跳上1级台阶，也可以跳上3级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     *
     * 这里主要有两种思路，我感觉第二种更好理解一点。
     * 1. 假设：f(n)表示：n个台阶第一次1,2,...n阶的跳法数; 若第一次跳了1阶，则还剩n-1阶，
     * 　　假设：f(n-1)表示：n-1个台阶第一次1,2,...n-1阶的跳法数; 若第一次跳了2阶，则还剩n-2阶，
     * 　　假设：f(n-2)表示：n-1个台阶第一次1,2,...n-2阶的跳法数;
     *     ...
     * 　　把所以可能的情况（第一次可能跳1,2,...,n阶）加起来：
     * 　　可以求出：f(n) = f(n-1) + f(n-2) + ... + f(1)
     * 　　递归：f(n-1) = f(n-2) + ... + f(1)
     * 　　可以求出：f(n) = 2*f(n-1)
     *
     * 2. 每个台阶可以跳或者不跳
     *   Sum(C(x, n-1)) = 2^(n-1)
     */
    static int freakJumpFrog1(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (n < 3) {
            return n;
        }
        return 2 * freakJumpFrog1(n-1);
    }

    static int freakJumpFrog2(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (n < 3) {
            return n;
        }
        int result = 1;
        for (int i = 0; i < n; i++) {
            result *= 2;
        }
        return result;
    }
    private static void testFreakJumpFrog() {
        int n = 5;
        int res = freakJumpFrog1(n);
        System.out.println(res);

        res = freakJumpFrog1(n);
        System.out.println(res);
    }

    /**
     * 010-矩形覆盖
     *
     * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。
     * 请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
     *  _               _____________________
     * | |             |                     |
     * |_|             |_____________________|
     * 2*1的小矩形       2*n的大矩形
     *
     * 动态规划算法求解此问题：
     * 1. 阶段划分，状态表示，
     * 2. 状态转移方程：F(n) = F(n-1) + F(n-2)
     * 3. 边界条件：F(0) = 0, F(1) = 1, F(2) = 2
     *
     * 状态转移方程的含义为 ：
     * 要覆盖n个2*1  有f(n)种，
     * 1. 第一种是从n-1个2*1的f(n-1)最后一格竖着覆盖一种方式，
     * 2. 第二种是从n-2个2*1的f(n-2)最后两格横着覆盖一种方式得到的。
     *
     * 思路同Fibonacci数列
     */
    static int rectCover(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i-1]+dp[i-2];
        }
        return dp[n];
    }

    private static void testRectCover() {
        int n = 5;
        int res = rectCover(n);
        System.out.println(res);
    }

    public static void main(String[] args) {
        // 007-斐波拉契数列
        testGetFibonacciNumber();
        // 008-跳台阶
        testJumpFrog();
        // 009-变态跳台阶
        testFreakJumpFrog();
        // 010-矩形覆盖
        testRectCover();
    }
}
