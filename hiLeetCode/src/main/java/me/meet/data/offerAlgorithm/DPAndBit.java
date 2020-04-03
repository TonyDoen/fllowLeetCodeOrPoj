package me.meet.data.offerAlgorithm;

public final class DPAndBit {
    private DPAndBit() {
    }

    /**
     * 030-连续子数组的最大和
     *
     * 给一个数组,返回它的最大连续子序列的和？(子向量的长度至少是1)
     * 例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止).
     *
     * 思路：
     * 令 函数 f(i)表示以第 i 个数字结尾的子数组的和,那我们这要求出 max(f(i)), i=1,2,...,n
     *        | arr[i]              i=0 or f(i-1)<=0
     * f(i) = {
     *        | arr[i] + f(i-1)    i!=0 or f(i-1)>0
     *
     * 从动态规划的状态转移方程来看,本来需要维护一张二维表记录每个阶段的最大和,再取最大值即为所求问题的解.
     * 但是 f(i) 只与 f(i−1) 状态有关,那么用一个临时变量记录 f(i−1), 另一个变量记录最大值,就可将空间复杂度降为常数级.
     *
     * 时间复杂度O(n),空间复杂度O(1)
     */
    static int findGreatestSum(int[] arr) {
        if (null == arr) {
            return -1;
        }
        int sum = 0, max = 0;
        for (int value : arr) {
            if (sum < 0) {
                sum = value;
            } else {
                sum += value;
            }
            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }

    private static void testFindGreatestSum() {
        int[] arr = new int[]{6,-3,-2,7,-15,1,2,2};
        int res = findGreatestSum(arr);
        System.out.println(res);
    }

    /**
     * 正则表达式匹配
     *
     * 请实现一个函数用来匹配包括'.'和'*'的正则表达式。
     * 模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。
     * 在本题中，匹配是指字符串的所有字符匹配整个模式。
     * 例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配
     *
     * 思路：
     * 1、逐个比较两个字符串的元素
     * 2、遇到后者的.元素是任意元素，遇到*则绑定与前一个元素，进行0-n的匹配
     *
     *
     * 当模式中的第二个字符不是“*”时：
     * 1、如果字符串第一个字符和模式中的第一个字符相匹配，那么字符串和模式都后移一个字符，然后匹配剩余的。
     * 2、如果 字符串第一个字符和模式中的第一个字符相不匹配，直接返回false。
     *
     * 而当模式中的第二个字符是“*”时：
     * 如果字符串第一个字符跟模式第一个字符不匹配，则模式后移2个字符，继续匹配。如果字符串第一个字符跟模式第一个字符匹配，可以有3种匹配方式：
     * 1、模式后移2字符，相当于x*被忽略；
     * 2、字符串后移1字符，模式后移2字符；
     * 3、字符串后移1字符，模式不变，即继续匹配字符下一位，因为*可以匹配多位；
     *
     */
    static boolean match(String src, String pattern) {
        if (null == src || null == pattern) {
            return false;
        }
        return match(src.toCharArray(), 0, pattern.toCharArray(), 0);
    }
    private static boolean match(char[] src, int srcIndex, char[] pattern, int patternIndex) {
        //有效性检验：str到尾，pattern到尾，匹配成功
        if (srcIndex == src.length && patternIndex == pattern.length) {
            return true;
        }
        //pattern先到尾，匹配失败
        if (srcIndex != src.length && patternIndex == pattern.length) {
            return false;
        }
        //模式第2个是*，且字符串第1个跟模式第1个匹配,分3种匹配模式；如不匹配，模式后移2位
        if (patternIndex + 1 < pattern.length && pattern[patternIndex + 1] == '*') {
            if ((srcIndex != src.length && pattern[patternIndex] == src[srcIndex]) || (pattern[patternIndex] == '.' && srcIndex != src.length)) {
                return match(src, srcIndex, pattern, patternIndex + 2)//模式后移2，视为x*匹配0个字符
                        || match(src, srcIndex + 1, pattern, patternIndex + 2)//视为模式匹配1个字符
                        || match(src, srcIndex + 1, pattern, patternIndex);//*匹配1个，再匹配str中的下一个
            } else {
                return match(src, srcIndex, pattern, patternIndex + 2);
            }
        }
        //模式第2个不是*，且字符串第1个跟模式第1个匹配，则都后移1位，否则直接返回false
        if ((srcIndex != src.length && pattern[patternIndex] == src[srcIndex]) || (pattern[patternIndex] == '.' && srcIndex != src.length)) {
            return match(src, srcIndex + 1, pattern, patternIndex + 1);
        }
        return false;
    }

    /**
     * url: https://blog.csdn.net/jfkidear/article/details/90261170
     */
    static boolean isMatch(String text, String pattern) {
        boolean[][] memo = new boolean[text.length() + 1][pattern.length() + 1];
        memo[text.length()][pattern.length()] = true;

        for (int i = text.length(); i >= 0; i--){
            for (int j = pattern.length() - 1; j >= 0; j--){
                boolean curMatch = (i < text.length() &&
                        (pattern.charAt(j) == text.charAt(i) ||
                                pattern.charAt(j) == '.'));
                if (j + 1 < pattern.length() && pattern.charAt(j+1) == '*'){
                    memo[i][j] = memo[i][j+2] || curMatch && memo[i+1][j];
                } else {
                    memo[i][j] = curMatch && memo[i+1][j+1];
                }
            }
        }
        return memo[0][0];
    }

    private static void testMatch() {
        String src = "aaa";
        String pattern = "ab*ac*a";
        boolean res = match(src, pattern);
        System.out.println(res);

        boolean res2 = isMatch(src, pattern);
        System.out.println(res2);
    }


    /**
     * 011-二进制中1的个数
     *
     * 原码：
     * 原码(true form)是一种计算机中对数字的二进制定点表示方法。
     * 原码表示法在数值前面增加了一位符号位（即最高位为符号位）：正数该位为0，负数该位为1
     * （0有两种表示：+0和-0），其余位表示数值的大小
     * 【例】
     * 十进制(7)，原码表示为 0 0000111
     * 十进制(-7)，原码表示为 1 0000111
     *
     *
     * 补码：
     * 1、正整数(符号位为0)的补码是其二进制表示，与原码相同
     * 2、负整数(符号位为1)的补码，将其原码除符号位外的所有位取反（0变1，1变0，符号位为1不变）后加1
     * 【例1】+9的补码是00001001
     * 【例2】求-5的补码。
     *       -5对应正数5（00000101）→所有位取反（11111010）→加1(11111011)
     *       所以-5的补码是11111011。
     * 【例3】数0的补码表示是唯一的。
     *       [+0]补=[+0]反=[+0]原=00000000
     *       [-0]补=11111111+1=00000000
     *
     *
     * 反码：
     * 正数的反码与其原码相同；
     * 负数的反码是对正数逐位取反，符号位保持为1.
     * 【例】
     * [+7]反= 0 0000111
     * [-7]反= 1 1111000
     *
     *
     * 思路1：
     * 1、数字n逐位与1进行"与运算"
     * 2、如果为1，则说明此位上为1，count++
     *
     * 思路2：(摘自牛客网)
     * 如果一个整数不为0，那么这个整数至少有一位是1。如果我们把这个整数减1，
     * 那么原来处在整数最右边的1就会变为0，原来在1后面的所有的0都会变成1
     * (如果最右边的1后面还有0的话)。其余所有位将不会受到影响。
     */
    static int numberOf1InInt(int n) {
        int count = 0;
        while (n != 0) {
            if ((n & 1) == 1) {
                count++;
            }
            // 注意此处要使用无符号右移，因为对于负数>>右移，高位补1，而无符号右移>>>，高位补0，适用此处场景
            n = n >>> 1;
        }
        return count;
    }

    /**
     * 011-二进制中1的个数
     *
     * 思路：
     * 如果一个整数不为0，那么这个整数至少有一位是1。如果我们把这个整数减1，
     * 那么原来处在整数最右边的1就会变为0，原来在1后面的所有的0都会变成1
     * (如果最右边的1后面还有0的话)。其余所有位将不会受到影响。
     *
     * 举个例子：一个二进制数1100，从右边数起第三位是处于最右边的一个1。减去1后，第三位变成0，
     * 它后面的两位0变成了1，而前面的1保持不变，因此得到的结果是1011.
     * 我们发现减1的结果是把最右边的一个1开始的所有位都取反了。
     * 这个时候如果我们再把原来的整数和减去1之后的结果做与运算，从原来整数最右边一个1那一位开始所有位都会变成0。
     * 如1100&1011=1000.也就是说，把一个整数减去1，再和原整数做与运算，会把该整数最右边一个1变成0.
     * 那么一个整数的二进制有多少个1，就可以进行多少次这样的操作。
     *
     */
    static int numberOf1InInt2(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = n & (n-1);
        }
        return count;
    }

    private static void testNumberOf1InInt() {
        int n = 3;
        int res1 = numberOf1InInt(n);
        System.out.println(res1);

        int res2 = numberOf1InInt2(n);
        System.out.println(res2);
    }

    /**
     * 012-数值的整数次方
     *
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
     * 040-数组中只出现一次的数字
     *
     * 题意: 一个整型数组里除了两个数字之外，其他的数字都出现了偶数次。如何快速找出数组中只出现一次的两个数
     *
     * 思路1:
     * HashMap
     *
     * 思路2:
     * 1、对于出现两次的元素，使用“异或”操作后结果肯定为0，那么我们就可以遍历一遍数组，对所有元素使用异或操作，那么得到的结果就是两个出现一次的元素的异或结果。
     * 2、因为这两个元素不相等，所以异或的结果中肯定有一位是1，找到为1的那一位在什么位置。
     * 3、根据那一位的位置将数组分为2组，一组该位都是0，一组都是1，每组分别异或就是最后的结果。
     * 4、再次遍历原数组，将每个元素右移到该位置与1相与，得出最后的结果
     */
    static int[] find2NumberInDuplicate(int[] arr) {
        int result = 0;
        // 1、对于出现两次的元素，使用“异或”操作后结果肯定为0，那么我们就可以遍历一遍数组，对所有元素使用异或操作，那么得到的结果就是两个出现一次的元素的异或结果。
        for (int value : arr) {
            result = result ^ value;
        }
        // 2、因为这两个元素不相等，所以异或的结果中肯定有一位是1，找到为1的那一位在什么位置。
        int index = 0;
        for (int i = 0; i <= 32; i++) {
            //将两个不同数异或的结果result右移N位，如果与1相与结果为1，就证明该N位是1
            if ((result >> i & 1) == 1) {
                index = i;
                break;
            }
        }
        // 3、根据那一位的位置将数组分为2组，一组该位都是0，一组都是1，每组分别异或就是最后的结果。
        int[] result2 = new int[2];
        for (int value : arr) {
            //将原数组右移上面的N位与1相与分成两组，得出结果
            if ((value >> index & 1) == 0) {
                result2[0] = result2[0] ^ value;
            } else {
                result2[1] = result2[1] ^ value;
            }
        }
        return result2;
    }

    private static void testFind2NumberInDuplicate() {
        int[] arr = {1, 2, 2, 4, 4, 6, 6, 8, 8, 5, 5, 7};
        int[] res = find2NumberInDuplicate(arr);
        for (int val : res) {
            System.out.print(val+" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 030-连续子数组的最大和
        testFindGreatestSum();
        // 052-正则表达式匹配
        testMatch();
        // 011-二进制中1的个数
        testNumberOf1InInt();
        // 012-数值的整数次方
        testPow();
        // 040-数组中只出现一次的数字
        testFind2NumberInDuplicate();
    }
}
