package me.meet.data.offerAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public final class Other {
    private Other() {
    }
    /**
     * 002-替换空格
     *
     * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。
     * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     *
     * 思路：
     * 1、利用String的库函数
     * 2、使用char数组
     */
    static String replaceSpace0(String src) {
        if (null == src) {
            return null;
        }
        return src.replaceAll(" ", "%20");
    }

    static String replaceSpace1(String src) {
        if (null == src || src.isEmpty()) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0, length = src.length(); i < length; i++) {
            char c = src.charAt(i);
            if (' ' == c) {
                res.append("%20");
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }

    private static void testReplaceSpace() {
        String src = "We Are Happy.";
        String res = replaceSpace0(src);
        System.out.println(res);

        res = replaceSpace1(src);
        System.out.println(res);
    }

    /**
     * 013-调整数组顺序使奇数位于偶数前面
     *
     * 输入一个整数数组，调整数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。
     * 要求时间复杂度为O(n)，不要求保留数组元素的相对位置。
     *
     * 思路1:
     * 1. 两个指针，一个指针从前往后（找偶数），一个指针从后往前（找奇数），交换
     * 时间复杂度为O(n)
     */
    static void oddBeforeEven0(int[] arr) {
        if (null == arr) {
            return;
        }

        for (int i = 0, j = arr.length-1; i < j;) {
            for (; i < j && 0 != arr[i]%2;) {
                i++;
            }
            for (; i < j && 0 == arr[j]%2;) {
                j--;
            }
            if (i < j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;

                i++;
                j--;
            }
        }
    }

    static void oddBeforeEven1(int[] arr) {
        if (null == arr) {
            return;
        }
        for (int length = arr.length, i = -1, j = 0; j < length; j++) {
            if (0 != arr[j]%2) {
                i++;
                // swap
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
    }

    private static void testOddBeforeEven0() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        oddBeforeEven0(arr);
        for (int i : arr) {
            System.out.print(i+ " ");
        }
        System.out.println();

        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        oddBeforeEven1(arr);
        for (int i : arr) {
            System.out.print(i+ " ");
        }
        System.out.println();
    }

    /**
     * 028-数组中出现次数超过一半的数字
     *
     * 只要不是特别大的内存开销，时间复杂度比较重要。因为改进时间复杂度对算法的要求更高
     *
     * 如果是顺序查找需要          O(N) 的时间;
     * 如果输入的是排序的数组则只需要 O(logN)的时间;
     * 如果事先已经构造好了哈希表，那查找在 O(1) 时间就能完成。
     *
     *
     * 思路1：
     * 题目给出的数组没有说是排序的，因此我们需要先给它排序。排序的时间复杂度是O(N*logN)。
     *
     * 思路2
     * 数组的特性：数组中有一个数字出现的次数超过了数组长度的一半。
     * 如果把这个数组『排序』，那么排序之后位于数组中间的数字一定就是那个出现次数超过数组长度一半的数字。
     * 也就是『中位数』
     * 有成熟的O(N)的算法得到数组中任意第k大的数字。
     *
     * 思路3
     * 前提：超过半数的数字存在
     * 数组中有一个数字出现的次数超过数组长度的一半，也就是说它出现的次数比其他所有数字出现次数的和还要多.
     * 因此我们可以考虑在遍历数组的时候保存两个值：一个是数组中的一个数字，一个是次数。
     * 1. 当我们遍历到下一个数字的时候，如果下一个数字和我们之前保存的数字相同，则次数加1;
     * 2. 如果下一个数字和我们之前保存的数字不同，则次数减1。
     * 3. 如果次数为零，我们需要保存下一个数字，并把次数设为1。
     *
     */
    static int findMoreThanHalfNumber(int[] arr) {
        if (null == arr || arr.length < 1) {
            return 0;
        }
        int may = arr[0], cnt = 1;
        for (int i = 1; i < arr.length; i++) {
            if (cnt < 0) {
                cnt = 1;
                may = arr[i];
            }
            if (may == arr[i]) {
                cnt++;
            } else {
                cnt--;
            }
        }
        return may;
    }
    private static void testFindMoreThanHalfNumber() {
        int[] arr = new int[]{9, 1, 2, 3, 3, 3, 3, 3, 4, 5};
        int res = findMoreThanHalfNumber(arr);
        System.out.println(res);
    }

    /**
     * 032-把数组排成最小的数
     *
     * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
     * 例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
     *
     * 思路1、全排列
     * 求出数组中所有数字的全排列，然后把每个全排列拼起来，求出拼出来的数字的最小值。
     *
     * 思路2、定义新的排序规则，排序后拼接
     * 例如：2和21 加起来比较221和212，212更小，于是说明21更小，把21放在2的前面
     */
    static void printOddQuickSortNumber(int[] arr) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> {
            // 重新定义比较模式
            String o12 = o1 + String.valueOf(o2);
            String o21 = String.valueOf(o2) + o1;
            if (Integer.valueOf(o12) < Integer.valueOf(o21)) {
                return -1;
            } else {
                return 1;
            }
        });
        for (int i : arr) {
            queue.add(i);
        }
        for (; null != queue.peek(); ) {
            System.out.print(queue.poll());
        }
        System.out.println();
    }

    private static void testPrintOddQuickSortNumber() {
        int[] arr = new int[]{3, 32, 321};
        printOddQuickSortNumber(arr);
    }

    /**
     * 033-丑数
     *
     * 把只包含质因子2、3和5的数称作丑数（Ugly Number）。
     * 例如6、8都是丑数，但14不是，因为它包含质因子7。
     * 习惯上我们把1当做是第一个丑数。
     * 求按从小到大的顺序的第N个丑数。
     *
     * 思路一：暴力搜索，时间复杂度不满足要求。
     * 思路二：动态规划方法，当前第n个丑数等于，前n-1个丑数中乘2，乘3，乘5中最小的数，且不再前n-1个丑数之中。
     */
    /**
     * 思路二：不必维护三个队列
     * 维护三个指针
     * 分别作用为 乘2,3或者5，最开始大于原数组最大的指针。
     */
    static int getUglyNumber(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        int[] dp = new int[n];
        dp[0] = 1; // 习惯上我们把1当做是第一个丑数。
        int p2 = 0, p3 = 0, p5 = 0;
        for (int i = 1; i < n; i++) {
            int x2 = dp[p2] * 2, x3 = dp[p3] * 3, x5 = dp[p5] * 5;
            int uglyNow = multiIntMin(x2, x3, x5);
            dp[i] = uglyNow;

            for (; x2 <= uglyNow; ) {
                p2++;
                x2 = dp[p2] * 2;
            }
            for (; x3 <= uglyNow; ) {
                p3++;
                x3 = dp[p3] * 3;
            }
            for (; x5 <= uglyNow; ) {
                p5++;
                x5 = dp[p5] * 5;
            }
        }
        return dp[n - 1];
    }

    private static int multiIntMin(int... arr) {
        int result = Integer.MAX_VALUE;
        for (int i : arr) {
            if (result > i) {
                result = i;
            }
        }
        return result;
    }

    private static void testGetUglyNumber() {
        int n = 5;
        int res = getUglyNumber(n);
        System.out.println(res);
    }

    /**
     * 041-和为S的连续正数序列(滑动窗口思想)
     *
     * 小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。但是他并不满足于此,他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。
     * 没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。
     * 现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列? Good Luck!
     *
     * 输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序。
     *
     * 思路：
     * 对于本题，由于是在一个连续序列中连续查找，可以使用类似滑动窗口的思想，使用双指针定位滑动窗口的上下边界，用两个数low和high分别指向当前序列中的最大和最小值，初始low为1，high为2。
     * 如果从low到high的序列的和大于给定的S，那么说明可以去掉一个比较小的值，即增大low的值（相当于去掉了一个最小值，窗口收缩）。反之，如果从low到high的序列和小于给定的S，则应该增加一个值，即增大high（相当于窗口扩张，让这个窗口包含更多的值）。
     * 这样依次查找就可以找到所有的满足条件的序列，并且符合序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序要求。
     * 另外，需要注意的是：循环的结束条件。由于要求序列至少包含两个数，因此当low追上high或者当low超过S的一半时，即可停止查找。
     */
    static List<int[]> findContinuousSequence(int sum) {
        List<int[]> result = new ArrayList<>();
        int left = 1, right = 2, curSum = 1 + 2;
        for (; left < right && left < (sum + 1) / 2; ) {
            if (sum == curSum) {
                int[] curArr = new int[right - left + 1];
                for (int i = left; i <= right; i++) {
                    curArr[i - left] = i;
                }
                result.add(curArr);

                curSum -= left;
                left++;
            } else if (sum < curSum) {
                curSum -= left;
                left++;
            } else {
                right++;
                curSum += right;
            }
        }
        return result;
    }

    private static void testFindContinuousSequence() {
        int sum = 100;
        List<int[]> res = findContinuousSequence(sum);
        for (int[] arr : res) {
            for (int i : arr) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    /**
     * 042-和为S的两个数字(双指针思想)
     *
     * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
     *
     * 思路一：
     * 双层循环，暴力解法，得到所有两数之和，时间复杂度为O(n^2)。
     *
     * 思路二：
     * 充分利用数组递增有序的特性，设置两指针到数组的两头，和大了，减小大指针，和小了，增大小指针，相等了返回，两指针相遇退出循环。
     * 同理如果输出两个数的乘积最大的，则初始两指针指向中间两个数，往两边移动即可。
     * tips:
     * 1 + 9 = 5 + 5
     * 1 * 9 < 5 * 5
     */
    static int[] findNumberWithSum(int[] arr, int s) { // arr: 数组递增有序
        int[] result = new int[2];
        if (null == arr || s < 1) {
            return result;
        }
        for (int left = 0, right = arr.length - 1; left < right; ) {
            int sum = arr[left] + arr[right];
            if (sum < s) {
                left++;
            } else if (sum > s) {
                right--;
            } else {
                result[0] = arr[left];
                result[1] = arr[right];
                return result;
            }
        }

        return result;
    }

    private static void testFindNumberWithSum() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int s = 4;
        int[] result = findNumberWithSum(arr, s);
        System.out.println(result[0] + " " + result[1]);
    }

    /**
     * 043-左旋转字符串(矩阵翻转)
     *
     * 汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。
     * 例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。
     *
     * 思路1：
     * 借鉴矩阵论中的转秩思想，将字符转分为两部分，每个部分分别进行反转，然后再对整体进行一次反转，就可以完成本题的要求。
     * eg:
     *      1. abc XYZdef
     *   => 2. cba fedZYX
     *   => 3. XYZdef abc
     *   => XYZdefabc(所求)
     */
    static String leftRotateString(String src, int n) {
        if (null == src || n < 1) {
            return src;
        }
        int length = src.length();
        if (n >= length) {
            n = n%length;
        }
        char[] result = new char[length];
        for (int i = 0, j = n-1; i < n; i++, j--) {
            result[i] = src.charAt(j);
        }
        for (int i = n, j = length-1; i < length; i++, j--) {
            result[i] = src.charAt(j);
        }
        for (int i = 0, j = length-1; i < j; i++, j--) {
            char tmp = result[i];
            result[i] = result[j];
            result[j] = tmp;
        }
        return String.valueOf(result);
    }

    private static void testLeftRotateString() {
        String src = "abcXYZdef";
        int n = 3;
        String res = leftRotateString(src, n);
        System.out.println(res);
    }

    /**
     * 046-孩子们的游戏-圆圈中最后剩下的数(约瑟夫环)
     *
     * 有个游戏是这样的:首先,让小朋友们围成一个大圈。然后,他随机指定一个数m,让编号为0的小朋友开始报数。
     * 每次喊到m-1的那个小朋友要出列唱首歌,然后可以在礼品箱中任意的挑选礼物,并且不再回到圈中,从他的下一个小朋友开始,继续0...m-1报数....
     * 这样下去....直到剩下最后一个小朋友,可以不用表演,挑选礼物
     *
     * 思路2：利用链表，每次移动m-1次，删除元素即可
     *
     * 思路1：递推公式
     *       f[1]=0;
     *       f[i]=(f[i-1]+m)%i;  (i>1)
     */
    static int lastRemaining(int n, int m) {
        if (n < 1 || m < 1) {
            return -1;
        }

        List<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            tmp.add(i);
        }

        int idx = -1;
        while (tmp.size() > 1) {
            idx = (idx + m) % tmp.size();
            tmp.remove(idx);
            idx--;
        }

        return tmp.get(0);
    }

    static int lastRemainForum(int n, int m) {
        if (n < 1 || m < 1) {
            return -1;
        }
        int f = 0;
        for (int i = 2; i <= n; i++) {
            f = (f + m) % i;
        }
        return f;
    }

    private static void testLastRemainForum() {
        int n = 9, m = 2;
        int res = lastRemaining(n, m);
        System.out.println(res);

        res = lastRemainForum(n, m);
        System.out.println(res);
    }

    /**
     * 051-构建乘积数组
     *
     * 给定一个数组A[0,1,...,n−1],请构建一个数组B[0,1,...,n−1],
     * 其中B中的元素B[i]=A[0]*A[1]*...*A[i−1]*A[i+1]*...*A[n−1]。(跳过了A[i])不能使用除法。
     *
     * 思路1：暴力解法双层循环，时间复杂度O(n^2);空间复杂度O(1)。
     *
     * 思路2：
     * 构建前向乘积数组 C[i]=A[0]*A[1]*...*A[i−1]
     * 构建后向乘积数组 D[i]=A[i+1]*...*A[n−1]=A[n−1]*A[n−2]*...A[n−i+1]， 即 D[i]=D[i+1]*A[i+1]
     * 通过 C[i],D[i] 来求 B[i];    B[i]=C[i]*D[i];   时间复杂度：O(n); 空间复杂度O(n)。
     * 但是C与D数组的临时存储可全程用B来代替，因此可将空间复杂度降为O(1)。
     */
    static int[] productOfArray(int[] arr) {
        if (null == arr || arr.length < 1) {
            return null;
        }
        int[] result = new int[arr.length];
        result[0] = 1;
        for (int i = 1; i < arr.length; i++) { // C[i]=A[0]*A[1]*...*A[i−1]
            result[i] = result[i - 1] * arr[i - 1];
        }
        int tmp = 1;
        for (int i = arr.length - 2; i >= 0; i--) { // D[i]=A[i+1]*...*A[n−1]=A[n−1]*A[n−2]*...A[n−i+1]
            tmp = tmp * arr[i + 1];
            result[i] = result[i] * tmp;
        }
        return result;
    }

    private static void testProductOfArray() {
        int[] arr = new int[]{2, 3, 1, 5};
        int[] res = productOfArray(arr);
        for (int i : res) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 002-替换空格
        testReplaceSpace();
        // 013-调整数组顺序使奇数位于偶数前面
        testOddBeforeEven0();
        // 028-数组中出现次数超过一半的数字
        testFindMoreThanHalfNumber();
        // 032-把数组排成最小的数
        testPrintOddQuickSortNumber();
        // 033-丑数
        testGetUglyNumber();
        // 041-和为S的连续正数序列(滑动窗口思想)
        testFindContinuousSequence();
        // 042-和为S的两个数字(双指针思想)
        testFindNumberWithSum();
        // 043-左旋转字符串(矩阵翻转)
        testLeftRotateString();
        // 046-孩子们的游戏-圆圈中最后剩下的数(约瑟夫环)
        testLastRemainForum();
        // 051-构建乘积数组
        testProductOfArray();
    }
}
