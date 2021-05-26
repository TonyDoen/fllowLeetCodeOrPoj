package me.meet.labuladong._1;

public final class LC1312 {
    private LC1312() {
    }

    /**
     * LeetCode 1312 题「让字符串成为回文串的最少插入次数」，难度 Hard。
     * 
     * 给你一个字符串 s; 每次操作你都可以在字符串的任意位置插入任意字符。请你返回让 s 成为回文串的最少操作次数。
     * [回文串] 是正读和反读都相同的字符串
     * 
     * 
     * 比如说输入s = "abcea"，算法返回 2，因为可以给s插入 2 个字符变成回文串"abeceba"或者"aebcbea"。
     * 如果输入s = "aba"，则算法返回 0，因为s已经是回文串，不用插入任何字符。
     * 
     * 
     * 思路：
     * 首先，要找最少的插入次数，那肯定得穷举喽，如果我们用暴力算法穷举出所有插入方法，时间复杂度是多少？
     * 每次都可以在两个字符的中间插入任意一个字符，外加判断字符串是否为回文字符串，这时间复杂度肯定爆炸，是指数级。
     *
     * 那么无疑，这个问题需要使用动态规划技巧来解决。
     * 之前的文章说过，回文问题一般都是从字符串的中间向两端扩散，构造回文串也是类似的。
     * 我们定义一个二维的dp数组，dp[i][j]的定义如下：对字符串s[i..j]，最少需要进行dp[i][j]次插入才能变成回文串。
     * 我们想求整个s的最少插入次数，根据这个定义，也就是想求dp[0][n-1]的大小（n为s的长度）。
     * 同时，base case 也很容易想到，当i == j时dp[i][j] = 0，因为当i == j时s[i..j]就是一个字符，本身就是回文串，所以不需要进行任何插入操作。
     * 接下来就是动态规划的重头戏了，利用数学归纳法思考状态转移方程。
     *
     * [状态转移方程]
     * 状态转移就是从小规模问题的答案推导更大规模问题的答案，从 base case 向其他状态推导嘛。
     * 如果我们现在想计算dp[i][j]的值，而且假设我们已经计算出了子问题dp[i+1][j-1]的值了，你能不能想办法推出dp[i][j]的值呢？
     * dp[i][j] = ?
     * i           j
     * x  a  a  b  y
     *    i+1   j-1
     *    dp[i+1][j-1] = 1  ==> 需要在第1个 'a' 前插入 'b'
     * 
     * 既然已经算出dp[i+1][j-1]，即知道了s[i+1..j-1]成为回文串的最小插入次数，那么也就可以认为s[i+1..j-1]已经是一个回文串了，
     * 所以通过dp[i+1][j-1]推导dp[i][j]的关键就在于s[i]和s[j]这两个字符。
     * dp[i][j] = ?
     * i              j
     * x  b  a  a  b  y
     * ?  i+1   j-1   ?
     *
     * 这个得分情况讨论，
     * <1>
     * 如果s[i] == s[j]的话，我们不需要进行任何插入，只要知道如何把s[i+1..j-1]变成回文串即可：
     * dp[i][j] = ?
     * i              j
     * y  b  a  a  b  y
     * ?  i+1   j-1   ?
     *
     * 翻译成代码就是这样：
     * if (s[i] == s[j]) {
     *     dp[i][j] = dp[i + 1][j - 1];
     * }
     *
     * <2>
     * 如果s[i] != s[j]的话，就比较麻烦了，比如下面这种情况：
     * dp[i][j] = ?
     * i              j
     * x  y  b  a  a  b  y  x
     * ?  i+1   j-1   ?
     *
     * PS：当然，把s[j]插到s[i]左边，然后把s[i]插到s[j]左边也是一样的，后面会分析。
     * 但是，这是不是就意味着代码可以直接这样写呢？
     * if (s[i] != s[j]) {
     *     // 把 s[j] 插到 s[i] 右边，把 s[i] 插到 s[j] 右边
     *     dp[i][j] = dp[i + 1][j - 1] + 2;
     * }
     *
     * 不对，比如说如下这两种情况，只需要插入一个字符即可使得s[i..j]变成回文：
     *    i+1      j
     * x  a  a  a  a            pic1
     *
     *    i        j-1
     *    a  a  a  a  x         pic2
     *
     * 所以说，当s[i] != s[j]时，无脑插入两次肯定是可以让s[i..j]变成回文串，但是不一定是插入次数最少的，最优的插入方案应该被拆解成如下流程：
     * 步骤一，做选择，先将s[i..j-1]或者s[i+1..j]变成回文串。怎么做选择呢？谁变成回文串的插入次数少，就选谁呗。
     * 比如图二的情况，将s[i+1..j]变成回文串的代价小，因为它本身就是回文串，根本不需要插入；同理，对于图三，将s[i..j-1]变成回文串的代价更小。
     * 然而，如果 s[i+1..j]和s[i..j-1]都不是回文串，都至少需要插入一个字符才能变成回文，所以选择哪一个都一样：
     *
     * 那我怎么知道s[i+1..j]和s[i..j-1]谁变成回文串的代价更小呢？
     * 回头看看dp数组的定义是什么，dp[i+1][j]和dp[i][j-1]不就是它们变成回文串的代价么？
     *
     * 步骤二，根据步骤一的选择，将s[i..j]变成回文。
     * 如果你在步骤一中选择把s[i+1..j]变成回文串，那么在s[i+1..j]右边插入一个字符s[i]一定可以将s[i..j]变成回文；同理，如果在步骤一中选择把s[i..j-1]变成回文串，在s[i..j-1]左边插入一个字符s[j]一定可以将s[i..j]变成回文。
     * 那么根据刚才对dp数组的定义以及以上的分析，s[i] != s[j]时的代码逻辑如下：
     * if (s[i] != s[j]) {
     *     // 步骤一选择代价较小的
     *     // 步骤二必然要进行一次插入
     *     dp[i][j] = min(dp[i + 1][j], dp[i][j - 1]) + 1;
     * }
     *
     * 综合起来，状态转移方程如下：
     * if (s[i] == s[j]) {
     *     dp[i][j] = dp[i + 1][j - 1];
     * } else {
     *     dp[i][j] = min(dp[i + 1][j], dp[i][j - 1]) + 1;
     * }
     * 这就是动态规划算法核心，我们可以直接写出解法代码了。
     *
     *
     *
     *
     * [代码实现]
     * 首先想想 base case 是什么，当i == j时dp[i][j] = 0，因为这时候s[i..j]就是单个字符，本身就是回文串，不需要任何插入；
     * 最终的答案是dp[0][n-1]（n是字符串s的长度）。那么 dp table 长这样：
     * 0               dp[0][n-1]
     *   0
     *     0
     *       0
     *         0
     *           0
     *
     * 又因为状态转移方程中dp[i][j]和dp[i+1][j]，dp[i]-1]，dp[i+1][j-1]三个状态有关，为了保证每次计算dp[i][j]时，
     * 这三个状态都已经被计算，我们一般选择从下向上，从左到右遍历dp数组：
     *
     * 从下到上从左到右遍历
     * 0 ------------>  /|
     *   0 ---------->   |
     *     0 -------->   |
     *       0 ------>   |
     *         0 ---->   |
     *           0 -->   |
     *
     *
     */
    static int minInsertions(String s) {
        int n = s.length();
        // 定义：对 s[i..j]，最少需要插入 dp[i][j] 次才能变成回文
        int[][] dp = new int[n][n];
        // base case：i == j 时 dp[i][j] = 0，单个字符本身就是回文
        // dp 数组已经全部初始化为 0，base case 已初始化

        // 从下向上遍历
        for (int i = n - 2; i >= 0; i--) {
            // 从左向右遍历
            for (int j = i + 1; j < n; j++) {
                // 根据 s[i] 和 s[j] 进行状态转移
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
                }
            }
        }
        // 根据 dp 数组的定义，题目要求的答案
        return dp[0][n - 1];
    }

    static int minInsertionCompress(String s) {
        int n = s.length();
        // 定义：对 s[i..j]，最少需要插入 dp[i][j] 次才能变成回文
        int[] dp = new int[n];

        int temp = 0;
        for (int i = n - 2; i >= 0; i--) {
            // 记录 dp[i+1][j-1]
            int pre = 0;
            for (int j = i + 1; j < n; j++) {
                temp = dp[j];

                if (s.charAt(i) == s.charAt(j)) {
                    // dp[i][j] = dp[i+1][j-1];
                    dp[j] = pre;
                } else {
                    // dp[i][j] = min(dp[i+1][j], dp[i][j-1]) + 1;
                    dp[j] = Math.min(dp[j], dp[j - 1]) + 1;
                }

                pre = temp;
            }
        }

        return dp[n - 1];
    }

    private static void testMinInsertion() {
        String s = "abcea";
        int res = minInsertions(s);
        System.out.println(res);
    }

    private static void testMinInsertionCompress() {
        String s = "abcea";
        int res = minInsertionCompress(s);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testMinInsertion();
        testMinInsertionCompress();
    }
}
