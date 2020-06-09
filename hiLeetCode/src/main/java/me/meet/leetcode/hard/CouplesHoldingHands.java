package me.meet.leetcode.hard;

public final class CouplesHoldingHands {
    private CouplesHoldingHands() {
    }

    /**
     * url: http://www.cnblogs.com/grandyang/p/8716597.html
     * url: https://leetcode.com/problems/couples-holding-hands/discuss/113362/JavaC++-O(N)-solution-using-cyclic-swapping
     *
     * N couples sit in 2N seats arranged in a row and want to hold hands. We want to know the minimum number of swaps so that every couple is sitting side by side. A swap consists of choosing any two people, then they stand up and switch seats.
     * The people and seats are represented by an integer from 0 to 2N-1, the couples are numbered in order, the first couple being (0, 1), the second couple being (2, 3), and so on with the last couple being (2N-2, 2N-1).
     * The couples' initial seating is given by row[i] being the value of the person who is initially sitting in the i-th seat.
     *
     * Example 1:
     * Input: row = [0, 2, 1, 3]
     * Output: 1
     * Explanation: We only need to swap the second (row[1]) and third (row[2]) person.
     *
     * Example 2:
     * Input: row = [3, 2, 0, 1]
     * Output: 0
     * Explanation: All couples are already seated side by side.
     *
     * Note:
     * len(row) is even and in the range of [4, 60].
     * row is guaranteed to be a permutation of 0...len(row)-1.
     *
     */
    /**
     * 题意：两两握手
     * 这道题给了我们一个长度为n的数组，里面包含的数字是 [0, n-1] 范围内的数字各一个，让我们通过调换任意两个数字的位置，使得相邻的奇偶数靠在一起。因为要两两成对，所以题目限定了输入数组必须是偶数个。我们要明确的是，组成对儿的两个是从0开始，每两个一对儿的。比如0和1，2和3，像1和2就不行。而且检测的时候也是两个数两个数的检测，左右顺序无所谓，比如2和3，或者3和2都行。
     * <p>
     * 举个例子来说吧，比如： [3   1   4   0   2   5] 。我们如何将其重新排序
     * 首先明确，我们交换数字位置的动机是要凑对儿，如果我们交换的两个数字无法组成新对儿，那么这个交换就毫无意义。来手动交换吧，我们两个两个的来看数字，前两个数是3和1，我们知道其不成对儿，数字3的老相好是2，不是1，那么怎么办呢？我们就把1和2交换位置呗。好，那么现在3和2牵手成功，度假去了，再来看后面的：
     * [3   2   4   0   1   5]
     * 我们再取两数字，4和0，互不认识！4跟5有一腿儿，不是0，那么就把0和5，交换一下吧，得到：
     * [3   2   4   5   1   0]
     * 好了，再取最后两个数字，1和0，两口子，不用动！前面都成对的话，最后两个数字一定成对。而且这种方法所用的交换次数一定是最少的
     * 这就是一种贪婪算法Greedy Algorithm。
     * <p>
     * 注意
     * 我们要明确的是，组成对儿的两个是从0开始，每两个一对儿的。比如0和1，2和3，像1和2就不行。而且检测的时候也是两个数两个数的检测，左右顺序无所谓，比如2和3，或者3和2都行。
     * 这里在找老伴儿时用了一个trick，一个数‘异或’上1就是其另一个位，这个不难理解，如果是偶数的话，最后位是0，‘异或’上1等于加了1，变成了可以的成对奇数。如果是奇数的话，最后位是1，‘异或’上1后变为了0，变成了可以的成对偶数
     */
    static int minSwapCouples(int[] arr) {
        if (null == arr) {
            throw new IllegalArgumentException();

        }
        int res = 0, n = arr.length;
        if (0 != n % 2) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < n; i += 2) {
            int target = (arr[i] ^ 1);
            if (arr[i + 1] == target) {
                continue;
            }
            ++res;
            for (int j = i + 1; j < n; ++j) {
                if (arr[j] == target) { // swap
                    arr[j] = arr[i + 1];
                    arr[i + 1] = target;
                    break;
                }
            }
        }
        return res;
    }

    private static void testMinSwapCouples() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        System.out.println(minSwapCouples(arr));
    }

    /**
     * 下面我们来看一种使用联合查找Union Find的解法
     * 该解法对于处理群组问题时非常有效，比如岛屿数量有关的题就经常使用UF解法。
     * 核心思想是用一个root数组，每个点开始初始化为不同的值，如果两个点属于相同的组，就将其中一个点的root值赋值为另一个点的位置，这样只要是相同组里的两点，通过find函数会得到相同的值。
     * <p>
     * ????
     * 那么如果总共有n个数字，则共有 n/2 对儿，所以我们初始化 n/2 个群组，我们还是每次处理两个数字。每个数字除以2就是其群组号，那么属于同一组的两个数的群组号是相同的，比如2和3，其分别除以2均得到1，所以其组号均为1。那么这对解题有啥作用呢？作用忒大了，由于我们每次取的是两个数，且计算其群组号，并调用find函数，那么如果这两个数的群组号相同，那么find函数必然会返回同样的值，我们不用做什么额外动作，因为本身就是一对儿。如果两个数不是一对儿，那么其群组号必然不同，在二者没有归为一组之前，调用find函数返回的值就不同，此时我们将二者归为一组，并且cnt自减1，忘说了，cnt初始化为总群组数，即 n/2。那么最终cnt减少的个数就是交换的步数，
     */
    static int unionFindMinSwapCouples(int[] arr) {
        if (null == arr) {
            throw new IllegalArgumentException();

        }
        int n = arr.length, cnt = n / 2;
        if (0 != n % 2) {
            throw new IllegalArgumentException();
        }

        int[] root = new int[n];
        for (int i = 0; i < n; i++) {
            root[i] = i;
        }
        for (int i = 0; i < n; i += 2) {
            int x = unionFind(root, arr[i] / 2);
            int y = unionFind(root, arr[i + 1] / 2);
            if (x != y) {
                root[x] = y;
                --cnt;
            }
        }
        return n / 2 - cnt;
    }

    private static int unionFind(int[] arr, int i) {
        return (i == arr[i] ? i : unionFind(arr, arr[i]));
    }

    private static void testUnionFindMinSwapCouples() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        System.out.println(unionFindMinSwapCouples(arr));
    }

    public static void main(String[] args) {
        testMinSwapCouples();
        testUnionFindMinSwapCouples();
    }
}
