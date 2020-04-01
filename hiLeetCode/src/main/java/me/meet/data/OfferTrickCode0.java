package me.meet.data;

import java.util.*;

public class OfferTrickCode0 {
    /**
     * Chess Placing
     * 题意：给了一维的一个棋盘，共有n（n必为偶数）个格子。棋盘上是黑白相间的。现在棋盘上有n/2个棋子，让你全部移动到黑色格子或者白色格子，要求步数最少，并输出步数。
     * 题解：由于黑色或者白色都是固定位置，我们对棋子位置排个序，然后全部移动到任意一个颜色，取两个最小步数的最小值就好了。
     */
    static int chessPlacing(int n) {
        if (0 != n % 2) {
            throw new IllegalArgumentException();
        }
        int chessNum = n / 2;
        Set<Integer> set = new HashSet<>();
        Random r = new Random();
        do {
            set.add(r.nextInt(n));
        } while (set.size() < chessNum);

        Integer[] arr = new Integer[chessNum];
        arr = set.toArray(arr);

        Arrays.sort(arr);
        int res1 = 0, res2 = 0;
        for (int i = 0; i < chessNum; i++) {
            res1 += Math.abs(arr[i] - i * 2);
            res2 += Math.abs(arr[i] - (i * 2 - 1));
        }

        return Math.min(res1, res2);
    }

    private static void testChessPlacing() {
        int res = chessPlacing(12);
        System.out.println(res);
    }

    /**
     * 猴子爬山
     * 有n级台阶，小猴子要么跳1步，要么跳3步，求n级台阶有多少种跳法
     */
    public static int countStep(int n) {
        if (n < 1) {
            return -1;
        }
        int[] f = new int[n + 1];
        f[1] = 1;
        f[2] = 1;
        f[3] = 2;
        for (int k = 4; k <= n; k++)
            f[k] = f[k - 1] + f[k - 3];
        return f[n];
    }

    /**
     * 009-变态跳台阶
     * 一只青蛙一次可以跳上1级台阶，也可以跳上3级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
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
     */

    private static void testCountStep() {
        int res = countStep(12);
        System.out.println(res);
    }

    /**
     * 量水问题
     * 有2个无刻度两杯 A, B, 其容积分别是 a 升和 b 升(a > b); 允许量杯从水缸中取水或倒回水缸。量杯可以互相倒。
     * 1. 求 量杯A 得到 c 升(c < b < a) 水 的可能性
     */
    public static boolean minSteps(int a, int b, int c) {
        int r;
        while (true) {
            r = a % b;
            if (r != 0) {
                a = b;
                b = r;
            } else {
                break;
            }
        }
        return c % b == 0;
    }

    private static void testMinSteps() {
        boolean tf = minSteps(3, 2, 1);
        System.out.println(tf);
    }

    /**
     * 给出循环左移 n 位的单调递增数组，问当前数组左移 n 具体是多少
     * eg:
     * 4 5 6 7 1 2 3 => n = 3
     *
     * 分析:
     * 1 2 3 4 5 6 7 => 左移 3 位 => 4 5 6 7 1 2 3
     *
     *
     * 情况1: 2 3 4 5 6 7 1 => 左移 1 位
     *    left     mid    right          => left > right; mid > right; mid > left;
     *
     *
     * 情况2: 3 4 5 6 7 1 2 => 左移 2 位
     *    left     mid    right          => left > right; mid > right; mid > left;
     *
     *
     * 情况3: 7 1 2 3 4 5 6 => 左移 6 位
     *    left     mid    right          => left > right; mid < right; mid < left;
     *
     *
     * 情况4: 1 2 3 4 5 6 7 => 左移 0 位
     *    left     mid    right          => left < right; (mid < right; mid >left;)
     */
    public static int moveLeftStep(int[] arr) {
        if (null == arr || arr.length < 1) {
            return 0;
        }

        int left = 0, right = arr.length - 1;
        for (; left < right; ) {
            int mid = (left + right) / 2;
            if ((arr[mid] < arr[mid - 1] && arr[mid] < arr[mid + 1])) {
                return arr.length - mid;
            }
            if (left + 1 == right && arr[left] > arr[right]) {
                return 1; // 情况1
            }

            if (arr[left] < arr[right]) {
                return 0; // 情况4
            } else {
                if (arr[mid] > arr[left] && arr[mid] > arr[right]) {
                    left = mid;  // 情况2
                } else if (arr[mid] < arr[left] && arr[mid] < arr[right]) {
                    right = mid; // 情况3
                }
            }
        }
        return 0;
    }

    private static void testMoveLeftStep() {
        int[] arr = new int[]{18, 1, 2, 3, 4, 5, 6, 7, 8, 13};
        int res = moveLeftStep(arr);
        System.out.println(res);
    }

    /**
     * 对于一个有序数组，我们通常采用二分查找的方式来定位某一元素，请编写二分查找的算法，在数组中查找指定元素。
     *
     *   3个需要注意的题目问题：
     *    （1）有序数组；
     *    （2）二分查找；
     *    （3）若元素出现多次，要返回第一次出现的位置。
     *
     */
    public static int getTargetPos(int[] arr, int target) { // arr: 递增
        if (null == arr || arr.length < 1) {
            return -1;
        }
        if (1 == arr.length && arr[0] == target) {
            return 0;
        }
        int left = 0, right = arr.length - 1;
        for (; left < right;) {
            int mid = left + (right - left)/2;
            if (arr[mid] > target) {
                right = mid - 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        if (arr[right] == target) {
            return right;
        }
        return -1;
    }

    private static void testGetTargetPos() {
//        int[] arr = new int[]{1, 2, 2, 3, 4, 5};
        int[] arr = new int[]{2};
        int target = 2;
        int res = getTargetPos(arr, target);
        System.out.println(res);
    }

    /**
     * 数字 翻译成 汉语表达
     *
     * eg:
     * 1          -> 一
     * 1999999999 -> 一十九亿九千九百九十九万九千九百九十九
     *
     */
    private static final String[] ChSectionPos = {"", "万", "亿", "万亿"}; //中文数字节权位

    public static String number2Chinese(int num) {
        if (0 == num) {
            return "零";
        }
        int sectionPos = 0;
        StringBuilder result = new StringBuilder(), oneSection;
        for (; num > 0; ) {
            int section = num % 10000;
            oneSection = number2ChineseSection(section);
            if (0 != section) { //当前小节不为0时，添加节权
                oneSection.append(ChSectionPos[sectionPos]);
            }
            sectionPos++;
            num = num / 10000; //去掉已经转换的末尾4位数
            result.insert(0, oneSection);
        }
        if ('零' == result.charAt(0)) {
            return result.substring(1);
        }
        return result.toString();
    }

    private static final String[] ChNumber = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] ChPos = {"", "十", "百", "千"};

    private static StringBuilder number2ChineseSection(int num) {
        StringBuilder result = new StringBuilder();
        boolean zeroFlag = true;
        for (int i = 0; i < 4; i++) {
            int end = num % 10;
            if (0 == end) {
                if (!zeroFlag) {
                    zeroFlag = true;
                    result.insert(0, ChNumber[0]);
                }
            } else {
                zeroFlag = false;
                result.insert(0, ChPos[i]).insert(0, ChNumber[end]);
            }
            num = num / 10;
        }
        return result;
    }

    private static void testNumber2Chinese() {
        String res = number2Chinese(1999999999);
        System.out.println(res);
    }

    /**
     *
     * 给定一个字符串，要求把字符串前面的若干个字符移动到字符串的尾部，如把字符串“abcdef”前面的2个字符'a'和'b'移动到字符串的尾部，使得原字符串变成字符串“cdefab”。
     * 请写一个函数完成此功能，要求对长度为n的字符串操作的时间复杂度为 O(n)，空间复杂度为 O(1)。
     *
     */
    static String leftRotateStr(String s, int k) {
        if (null == s || k > s.length()) {
            throw new IllegalArgumentException();
        }

        char[] arr = s.toCharArray();
        reverseStr(arr, 0, k - 1);
        reverseStr(arr, k, arr.length - 1);
        reverseStr(arr, 0, arr.length - 1);
        return new String(arr);
    }
    private static void reverseStr(char[] s, int from, int to) {
        while (from < to) {
            char t = s[from];
            s[from++] = s[to];
            s[to--] = t;
        }
    }

    public static void testLeftRotateStr() {
        String res = leftRotateStr("abcdefj", 3);
        System.out.println(res);
    }

    /**
     * 计算 base 的 n次方
     * 时间复杂度 O(logN)
     *
     * n为偶数，a^n=a^n/2*a^n/2;
     * n为奇数，a^n=（a^（n-1）/2）*（a^（n-1/2））*a
     * 时间复杂度 O(logN)
     */
    static int pow(int base, int n) {
        if (0 == n) {
            return 1;
        }
        if (1 == n) {
            return base;
        }
        if (n >= 0) {
            if (1 == n % 2) {
                return pow(base * base, n / 2) * base;
            } else {
                return pow(base * base, n / 2);
            }
        } else {
            return 1 / pow(base, -n);
        }
    }

    private static void testPow() {
        int res = pow(3, 17);
        System.out.println(res);
    }

    /**
     * 求数列里的最大差值
     * a[1], a[2], a[3], ..., a[n]; what is max{a[j] - a[i]}, when j>i, a[i] > 0
     */
    private static void maxRdx(int[] arr) {
        int len = arr.length;
        if (len < 1) {
            return;
        }
        int res = 0, min = arr[0];
        for (int i = 0; i < len - 1; i++) {
            int tmp1 = arr[i + 1] - arr[i];
            int tmp2 = arr[i + 1] - min;
            if (tmp2 > tmp1 && tmp2 > res) {
                res = tmp2;
            } else if (tmp1 > res) {
                res = tmp1;
            }

            if (min > arr[i + 1]) {
                min = arr[i + 1];
            }
        }

        System.out.println("arr:" + Arrays.toString(arr) + "; res:" + res);
    }

    private static void testMaxRdx() {
        int[] in = new int[]{4, 7, 2, 1, 5, 3, 8, 6};
        maxRdx(in);
    }

    public static void main(String[] args) {
        testChessPlacing();
        testCountStep();
        testMinSteps();
        testMoveLeftStep();
        testGetTargetPos();
        testNumber2Chinese();
        testLeftRotateStr();
        testPow();
        testMaxRdx();
    }
}
