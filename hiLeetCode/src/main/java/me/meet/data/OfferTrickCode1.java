package me.meet.data;

public class OfferTrickCode1 {
    /**
     * Implement int sqrt(int x). Compute and return the square root of x.
     * 
     * 时间复杂度： O(logN)
     */
    static int sqrt1(int x) {
        // 牛顿迭代法
        if (0 == x || 1 == x) {
            return x;
        }
        double x0 = x;
        double t = x;
        x0 = x0 / 2 + t / (2 * x0);
        while (Math.abs(x0 * x0 - t) > 0.00000001) {
            x0 = x0 / 2 + t / (2 * x0);
        }
        return (int) x0;
    }

    static int sqrt2(int x) {
        // 二分查找法
        if (0 == x || 1 == x) {
            return x;
        }
        int mid = 0, left = 1, right = x;
        while (left < right) {
            mid = left + (right - left) / 2;
            if (mid > x / mid) {
                right = mid - 1;
            } else if (mid == x / mid) {
                return mid;
            } else {
                if ((mid + 1) > x / (mid + 1)) {
                    return mid;
                }
                if ((mid + 1) == x / (mid + 1)) {
                    return mid + 1;
                }
                left = mid + 1;
            }
        }
        return (left + right) / 2;
    }

    /**
     * http://read.pudn.com/downloads203/sourcecode/game/955182/3D%20Geometry%20Tuts/FastInverseSqrt.pdf
     * https://jcf94.com/2016/01/14/2016-01-14-carmack/
     */
    static float sqrt3(float x) {
        final int MAGIC_NUMBER_1 = 0x5f3759df; // Greg Walsh ; McEniry 确信这一常数或许最初便是以 “在可容忍误差范围内使用二分法” 的方式求得。
        final int MAGIC_NUMBER_2 = 0x5f375a86; // Chris Lomont

        // 一次 牛顿迭代法
        float half = 0.5f * x;
        int i = Float.floatToRawIntBits(x);
        i = (MAGIC_NUMBER_1 - (i >> 1));      // this line hides a LOT of math
        x = Float.intBitsToFloat(i);
        x = x * (1.5f - half * x * x);        // repeat this statement for a better approximation
        return 1.0f / x;
    }

    private static void testSqrt1() {
        int res1 = sqrt1(7);
        System.out.println(res1);

        int res2 = sqrt2(7);
        System.out.println(res2);

        double res3 = sqrt3(7);
        System.out.println(res3);
    }

    /**
     * int power(int base, int n);  Compute and return base^n
     * 
     * Exponentiation by squaring
     * 
     * 时间复杂度： O(logN)
     */
    static int pow1(int base, int n) {
        // 递归快速幂
        // base^n = 1                        , if n = 0
        // base^n = base^(n/2) * base^(n/2)  , if n is even but not 0
        // base^n = base^(n-1) * base        , if n is odd

        if (0 == n) {
            return 1;
        } else if (1 == n % 2) {
            return pow1(base, n - 1) * base;
        } else {
            int tmp = pow1(base, n / 2);
            return tmp * tmp;
        }
    }

    static int pow2(int base, int n) {
        final int MOD = 1000000007;
        if (0 == n) {
            return 1;
        } else if (1 == n % 2) {
            return pow2(base, n - 1) * base % MOD;
        } else {
            int tmp = pow1(base, n / 2) % MOD;
            return tmp * tmp % MOD;
        }
    }

    static int pow3(int base, int n) {
        int ans = 1;
        while (0 != n) {
            if (1 == n % 2) {
                ans *= base;
            }
            base *= base;
            n >>= 1;
        }
        return ans;
    }

    private static void testPow() {
        int base = 3, n = 5;
        int res1 = pow1(base, n);
        System.out.println(res1);

        int res2 = pow2(base, n);
        System.out.println(res2);

        int res3 = pow3(base, n);
        System.out.println(res3);
    }

    /**
     * n! = 1 * 2 * 3 .....* n
     *
     * 时间复杂度： O(logN)
     *
     * Stirling's approximation (斯特林公式是一条用来取n的阶乘的近似值的数学公式)
     * n! = sqrt(2 * pi * n) * (n/e)^n
     *
     *
     * Gergő Nemes在2007年提出了一个近似公式，它的精确度与Windschitl的公式相等，但更加简单：
     * n! = sqrt(2 * pi / n) * ((1/e) * (n + 1/(12n-1/10*n)))^n
     *
     */
    static long nF(int n) {
        if (n < 1) {
            return 1;
        }
        final long MOD = (int) (1e9 + 7);
//        final long MOD = (int) (1e9+9);

        long res = 1;
        for (int i = 1; i <= n; i++) {
            res = (res * i);
        }
        return res;
    }

    static double stirlingF(int n) {
        return Math.sqrt(2 * Math.PI * n) * Math.pow((n / Math.E), n);
    }

    private static void testStirlingF() {
        int n = 25;
        double res = stirlingF(n);
        System.out.println(res);

        long res2 = nF(n);
        System.out.println(res2);
    }

    /**
     * http://acm.hdu.edu.cn/showproblem.php?pid=1018
     * 求 N! 的位数
     *
     * N的范围是: 1<= N <= 10^7
     *
     * 思路：
     * 5! = 1 * 2 * 3 * 4 * 5 = 120
     *
     * 120 和 100 位数相同 且 100 = 10^2 且 位数 3 = 2+1
     * ...
     * 5! 的位数 = log10(5!) + 1
     * ...
     * N! 的位数 = log10(N!) + 1
     *
     * N! = 1 * 2 * 3 * ... * N
     * log10(N!) = log10(1) + log10(2) + log10(3) + ... + log10(N)
     *
     */
    static int nSeat(int n) {
        double res = 0;
        for (int i = 1; i <= n && i > 0; i++) {
            double lgi = Math.log10(i);
            if (lgi < 0) {
                throw new IllegalArgumentException();
            }
            res += lgi;
        }
        return (int) (res + 1);
    }

    private static void testNSeat() {
        int seat = nSeat(Integer.MAX_VALUE);
        System.out.println(seat);
    }

    public static void main(String[] args) {
        testSqrt1();
        testPow();
        testStirlingF();
        testNSeat();
    }
}
