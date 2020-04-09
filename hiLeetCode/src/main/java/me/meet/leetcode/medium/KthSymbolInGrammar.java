package me.meet.leetcode.medium;

public final class KthSymbolInGrammar {
    private KthSymbolInGrammar() {}

    /**
     * K-th Symbol in Grammar
     *
     * On the first row, we write a 0. Now in every subsequent row, we look at the previous row and replace each occurrence of 0 with 01, and each occurrence of 1 with 10.
     * Given row N and index K, return the K-th indexed symbol in row N. (The values of K are 1-indexed.) (1 indexed).
     *
     * Examples:
     * Input: N = 1, K = 1
     * Output: 0
     *
     * Input: N = 2, K = 1
     * Output: 0
     *
     * Input: N = 2, K = 2
     * Output: 1
     *
     * Input: N = 4, K = 5
     * Output: 1
     *
     * Explanation:
     * row 1: 0
     * row 2: 01
     * row 3: 0110
     * row 4: 01101001
     *
     * Note:
     * N will be an integer in the range [1, 30].
     * K will be an integer in the range [1, 2^(N-1)].
     *
     *
     * 题意：语法中的第K个符号
     * 这道题说第一行写上了一个0，然后从第二行开始，遇到0，就变为01，遇到1，则变为10，问我们第N行的第K个数字是啥。
     *
     * 思路0：
     * 这是一道蛮有意思的题目，首先如果没啥思路的话，按照给定的方法，一行行generate出来，直到生成第N行，那么第K个数字也就知道了。但是这种brute force的方法无法通过OJ，这里就不多说了
     *
     * 思路1：
     * 遇到0变为01，那么可不可以把0和1看作上一层0的左右子结点呢，同时，把1和0看作上一层1的左右子结点，
     * 这样的话，我们整个结构就可以转为二叉树了，那么前四层的二叉树结构如下所示：
     *
     *               0                         row 1
     *        /             \
     *       0|              1|                row 2
     *    /     \         /     \
     *   0        1|      1|     0             row 3
     *  / \     / \     / \     / \
     * 0   1   1|  0   1   0|  0   1           row 4
     *
     * 我们仔细观察上面这棵二叉树，第四层K=3的那个红色的左子结点，其父结点的位置是第三层的第 (K+1)/2 = 2个红色结点，而第四层K=6的那个蓝色幽子结点，其父节点的位置是第三层的第 K/2 = 3个蓝色结点。
     * 那么我们就可以一层一层的往上推，直到到达第一层的那个0。所以我们的思路是根据当前层K的奇偶性来确定上一层中父节点的位置，然后继续往上一层推，直到推倒第一层的0，然后再返回确定路径上每一个位置的值，这天然就是递归的运行机制。
     * 我们可以根据K的奇偶性知道其是左结点还是右结点，由于K是从1开始的，所以当K是奇数时，其是左结点，当K是偶数时，其是右结点。而且还能观察出来的是，左子结点和其父节点的值相同，右子结点和其父节点值相反，这是因为0换成了01，1换成了10，左子结点保持不变，右子结点flip了一下。想通了这些，那么我们的递归解法就不难写出来了，
     *
     */
    static int kthGrammar0(int n, int k) {
        if (n < 1 || k < 1 || k > Math.pow(2, n-1)) {
            throw new IllegalArgumentException();
        }
        if (1 == n) {
            return 0;
        }
        if (0 == k%2) {
            if (0 == kthGrammar0(n-1, k/2)) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (0 == kthGrammar0(n-1, (k+1)/2)) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * 简化上述代码
     * 1. 我们知道偶数加1除以2，和其本身除以2的值是相同的，那么其实不论K是奇是偶，其父节点的位置都可以用 (K+1)/2 来表示
     * 2. 问题在于K本身的奇偶决定了其左右结点的位置，从而决定要不要flip父节点的值，这才是上面解法中我们要使用 if...else 结构的原因。
     *    实际上我们可以通过‘亦或’操作来实现一行搞定，我们来看下变换规则，0换成了01，1换成了10。
     *    1> 0 -> 01
     *       左子结点(0) = 父节点(0) ^ 0
     *       右子结点(1) = 父节点(0) ^ 1
     *    2> 1 -> 10
     *       左子结点(1) = 父节点(1) ^ 0
     *       右子结点(0) = 父节点(1) ^ 1
     *    那么只要我们知道了父结点的值和当前K的奇偶性就可以知道K的值了，因为左子结点就是父结点值‘亦或’0，右子结点就是父结点值‘亦或’1，
     *    由于左子结点的K是奇数，我们可以对其取反再‘与’1，所以就是 (~K & 1)，再‘亦或’上递归函数的返回值即可，
     */
    static int kthGrammar0Up(int n, int k) {
        if (n < 1 || k < 1 || k > Math.pow(2, n-1)) {
            throw new IllegalArgumentException();
        }
        if (1 == n) {
            return 0;
        }
        return (~k & 1) ^ kthGrammar0Up(n - 1, (k + 1) / 2);
    }

    private static void testKthGrammar() {
        int n = 4, k = 5;
        int res = kthGrammar0(n, k);
        System.out.println(res);

        res = kthGrammar0Up(n, k);
        System.out.println(res);

    }

    /**
     * 思路2：
     * 一切的一切都是从变换规则入手，0换成了01，1换成了10。
     *
     * 1. 那么当K是奇数的时候，我们之前分析了，其一定是左子结点，
     *    那么其是01或者10的第一个数字，因为只有这两种组合方式，所以如果第K个数是0的话，那么第K+1个数就是1，
     *    同样，如果第K个数是1的话，那么第K+1个数就是0，所以此时第K个数和第K+1个数一定相反，
     *    那么我们就可以通过‘亦或’1来实现这个一定相反的操作。
     *
     * 2. 当K是偶数的时候，那么其是01或者10的第二个数字，那么根据之前的分析，其是由上一层的第 K/2 位置的数字生成的，上一层的第 K/2 个数字和当前层的第 K/2 个数字是一样的，
     *    如果你仔细观察题目中的例子或者博主画的那个二叉树图，只要K不越界，每一层的第K个数字都是相等的。
     *    所以如果第K个数是0的话，那么第 K/2 个数就是1，同样，如果第K个数是1的话，那么第 K/2 个数就是0，所以此时第K个数和第 K/2 个数一定相反，那么我们也可以通过‘亦或’1来实现这个一定相反的操作。
     *
     * 于是乎，我们的操作就是，当K是奇数的时候，我们就将其换成K+1，当K是偶数的时候，我们将其换为K/2。然后每次都对结果res（初始化为0）进行‘亦或’1操作，循环的终止条件是当K等于1时，
     */
    static int kthGrammar2(int n, int k) {
        if (n < 1 || k < 1 || k > Math.pow(2, n-1)) {
            throw new IllegalArgumentException();
        }

        int res = 0;
        for (; k > 1; ) {
            k = (1 == k % 2) ? k + 1 : k / 2;
            res ^= 1;
        }
        return res;
    }

    /**
     * 优化上述代码
     * 下面这种解法跟解法三的思路完全相同，只不过使用了bitset这个内置的数据结构来快速的求出了K-1的二进制表达数中的位1的个数，Java中可以直接使用Integer.bitCount()函数，
     */
    static int kthGrammar2Up(int n, int k) {
        if (n < 1 || k < 1 || k > Math.pow(2, n-1)) {
            throw new IllegalArgumentException();
        }

        return Integer.bitCount(k-1)%2;
    }

    private static void testKthGrammar2() {
        int n = 4, k = 5;
        int res = kthGrammar2(n, k);
        System.out.println(res);

        res = kthGrammar2Up(n, k);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testKthGrammar();
        testKthGrammar2();
    }
}
