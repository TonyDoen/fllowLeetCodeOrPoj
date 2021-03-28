package me.meet.data;

import java.math.BigDecimal;

public class OfferMathTrick0 {
    /**
     * url: https://juejin.im/post/5c909c25f265da611d742410
     * 题目：
     * 给你一个数 N，输出 N! 的值。
     *
     * 没有限定 n 的范围。 需要处理溢出
     */
    static BigDecimal stepTime1(int n) {
        BigDecimal sum = BigDecimal.ONE;
        for(int i = 1; i <= n; i++){
            sum = sum.multiply(new BigDecimal(i));
        }
        return sum;
    }

    static String stepTime2(int n) {
        String result = "1";
        for (int i = 1; i <= n; i++) {
            result = bigNumberMultiply(result, String.valueOf(i));
        }
        return result;
    }

    private static void testStepTime() {
        int n = Integer.MAX_VALUE;
        System.out.println(n);
//        BigDecimal res = stepTime1(n);
//        System.out.println(res);

        String res2 = stepTime2(5315);
        System.out.println(res2);
    }

    /**
     * 题目：
     * 大整数相乘
     */
    static String bigNumberMultiply(String s1, String s2) {
        int length = s1.length() + s2.length();
        char[] result = new char[length];
        for (int i = s1.length() - 1; i >= 0; i--) {
            int idx = length - 1;
            int prompt = 0;
            for (int j = s2.length() - 1; j >= 0; j--) {
                int tmp = (s1.charAt(i) - '0') * (s2.charAt(j) - '0') + result[idx] + prompt;
                prompt = tmp / 10;
                result[idx] = (char) (tmp % 10);
                idx--;
            }
            // 每趟乘下来的进位要进行保存。
            result[idx] = (char) prompt;
            length--;
        }
        // 最后把c中的字符加上 '0'
        for (int i = 0; i < result.length; i++) {
            result[i] += '0';
        }

        if (result[0] == '0') {
            return new String(result).substring(1);
        } else {
            return new String(result);
        }
    }

    private static void testBigNumberMultiply() {
        int a = Integer.MAX_VALUE, b = Integer.MAX_VALUE;
        String result = bigNumberMultiply(String.valueOf(a), String.valueOf(b));
        System.out.println(result);
    }

    /**
     * 题目：
     * 给定一个整数 N，那么 N 的阶乘 N! 末尾有多少个 0？
     * 例如： N = 10，则 N！= 3628800，那么 N! 的末尾有两个0。
     *
     * 思路：
     * 一个数乘以 10 就一定会在末尾产生一个零，
     * 于是，我们可以从“哪些数相乘能够得到 10 ”入手。
     *
     * 只有 2 * 5 才会产生 10。
     *
     * 注意，4 * 5 = 20 也能产生 0 啊，不过我们也可以把 20 进行分解啊，20 = 10 * 2。
     *
     * 问题转化为 N! 种能够分解成多少对 2*5
     * 再一步分析会发现，在 N！中能够被 2 整除的数一定比能够被 5 整除的数多，
     *
     * 当 N = 20 时，1~20 可以产生几个 5 ？答是 4 个，此时有 N / 5 = 4。
     * 当 N = 24 时，1~24 可以产生几个 5 ？答是 4 个，此时有 N / 5 = 4。
     * 当 N = 25 时，1~25 可以产生几个 5？答是 6 个，主要是因为 25 贡献了两个 5，此时有 N/5 + N/5^2 = 6。
     * ...
     * sum = N/5 + N/5^2 + N/5^3+….
     */
    static int stepTime0Count0(int n){
        int sum = 0;
        for(int i = 1; i <= n; i++){
            int j = i;
            while(j % 5 == 0){
                sum++;
                j = j / 5;
            }
        }
        return sum;
    }

    static int stepTime0Count1(int n) {
        int sum = 0;
        while(n != 0){
            sum += n / 5;
            n = n / 5;
        }
        return sum;
    }

    private static void testStepTime0Count() {
        int n = 5315;
        int res0 = stepTime0Count0(n);
        System.out.println(res0);

        int res1 = stepTime0Count1(n);
        System.out.println(res1);
    }

    /**
     * 题目：
     * 求 N! 的二进制表示中最低位 1 的位置。
     * 例如 3！= 6，二进制为 1010，所以 最低位 1 的位置在第二位。
     *
     *
     * 思路：
     * 仔细想一下，这道题也是求末尾有多少个 0
     * 你求出了末尾有多少个0自然知道 1 的位置（0的个数加1就是1的位置了）
     * 这道题是求二进制末尾有多少个 0
     *
     * 由于是二进制，所以每次乘以 2 末尾就会产生一个 0 。
     *
     */
    static int stepTime1Count0(int n) {
        int sum = 0;
        while(n != 0){
            sum += n / 2;
            n = n / 2;
        }
        return sum + 1;
    }
    // 等价于
    static int stepTime1Count1(int n) {
        int sum = 0;
        while(n != 0){
            n >>= 1;
            sum += n;
        }
        return sum + 1;
    }

    private static void testStepTime2Count() {
        int n = 5315;
        int res0 = stepTime1Count0(n);
        System.out.println(res0);

        int res1 = stepTime1Count1(n);
        System.out.println(res1);
    }

    public static void main(String[] args) {
        testBigNumberMultiply();
        testStepTime();
        testStepTime0Count();
        testStepTime2Count();
    }
}
