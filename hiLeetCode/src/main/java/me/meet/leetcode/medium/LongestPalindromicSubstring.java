package me.meet.leetcode.medium;

public final class LongestPalindromicSubstring {
    private LongestPalindromicSubstring() {
    }

    /**
     * Longest Palindromic Substring (最长回文子串)
     *
     * 示例 1:
     *   输入: "babad"
     *   输出: "bab"
     *   注意: "aba" 也是一个有效答案。
     *
     * 示例 2:
     *   输入: "cbbd"
     *   输出: "bb"
     */

    /**
     * 中心扩展算法
     * 中心扩展就是把给定的字符串的每一个字母或两个字母之间空隙当做中心，向两边扩展，这样来找
     * 1. 长度为奇数的回文串，比如a, aba, abcba，以字母为中心
     * 2. 长度为偶数的回文串，比如aa, abba，以两个字母之间空隙为中心
     * 
     * 时间复杂度：O(n²)。
     * 空间复杂度：O(1)。
     */
    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length()
                && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    static String longestPalindromicSubstring1(String s) {
        if (null == s || s.length() <= 1) {
            return s;
        }
        int start = 0, end = 0, length = s.length(); // 记录回文子串的开始位置
        for (int i = 0; i < length; i++) {
            // 以每个字符为中心去扩展，例如"aba"就是以'b'为中心
            int len1 = expandAroundCenter(s, i, i);
            // 以两字母之间为中心去扩展，例如 "abba" 的中心在两个 'b'之间
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);

            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }


    /**
     * 暴力解法为遍历所有子串，逐个判断是否是回文字串。
     * 
     * 解释1:
     * 在动态规划的思想中，总是希望把问题划分成相关联的子问题；然后从最基本的子问题出发来推导较大的子问题，直到所有的子问题都解决。
     * 假设字符串s的长度为length，建立一个 length*length 的矩阵dp。
     * 令 dp[i][j] 表示 S[i] 至 S[j] 所表示的子串是否是回文子串。
     * 
     * 1. 当 i == j，dp[i][j] 是回文子串（单字符都是回文子串）；
     * 2. 当 j - i < 3，只要 S[i] == S[j]，则 dp[i][j] 是回文子串（如"aa"，"aba"），否则不是；
     * 3. 当 j - i >= 3，如果 S[i] == S[j] && dp[i+1][j-1] ，则 dp[i][j] 是回文子串，否则不是 。
     * 
     * 由此可以写出状态转移方程：
     *           ⎪ true,                            i == j
     * dp[i][j]= ⎨ S[i] == S[j],                    j-i < 3
     *           ⎪ S[i] == S[j] && dp[i+1][j-1],    j-i >= 3
     * 
     * 
     * 解释2:
     * 接下来我们来优化暴力解法，暴力解法的问题在于没有用到回文字串的特性，只是用了定义去检验一个字串是不是回文，所以这个题的题眼在于利用回文字串的特性。
     * 如果一个字串是回文字串，那么去掉左右两边的字符之后依然是回文。
     * 也可以说是一个回文字串，左右两边加上相同的字符，也是回文字串。
     * 使用索引 i 和 j 来表示一个字符串从索引 i 到 j 的子串，则：
     * 
     * 1> dp[i][j]表示索引i到j的子串是否是回文
     * 2> dp[i][j] = true表示是回文，反之则为false
     * 3> dp[i][i]只有一个字符，必是回文
     * 4> 关键点在于找到关系：dp[i][j] = dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
     * 5> 长的子串dp[i][j]依赖于短的子串dp[i + 1][j - 1]，所以由短到长依次计算
     * 1、先计算一个字符，全为true
     * 2、再计算两个字符，如果两个字符一样则为true
     * 3、然后计算大于三个字符，直到整个字符串
     * 
     * 具体步骤：
     * 1、定义二维数组存储dp的结果值
     * 2、单个字符（起点终点索引相同）全部为true
     * 3、两个字符如果字符相同为true（注意数组不要越界）
     * 4、依次循环三个字符、四个字符......
     * 5、有起点索引 i，有子串长度 k 则可以得到终点索引 j （同样注意数组越界问题）
     * 6、比较回文子串长度与保存的result长度
     * 
     * 时间复杂度：O(n²)。
     * 空间复杂度：O(n²)。
     */
    static String longestPalindromicSubstring(String s) {
        int len = s.length();
        //1、定义二维数组存储dp的结果值
        boolean[][] dp = new boolean[len][len];
        String result = s.substring(0, 1);

        //2、单个字符（起点终点索引相同）全部为true
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        //3、两个字符如果字符相同为true（注意数组不要越界）
        for (int i = 0; i < len - 1; i++) {
            dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
            if (dp[i][i + 1]) {
                result = s.substring(i, i + 1 + 1);
            }
        }
        //4、依次循环三个字符、四个字符......
        for (int k = 3; k <= len; k++) {
            for (int i = 0; (i + k) <= len; i++) {
                //5、有起点索引 i，有子串长度 k 则可以得到终点索引 j （同样注意数组越界问题）
                int j = i + k - 1;
                dp[i][j] = dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
                //6、比较回文子串长度与保存的result长度
                if (dp[i][j] && (j - i + 1) > result.length()) {
                    result = s.substring(i, j + 1);
                }
            }
        }
        return result;
    }

    static String longestPalindromicSubstring0(String s) {
        int length = s.length();
        boolean[][] dp = new boolean[length][length];
        String result = s.substring(0, 1);

        //4、依次循环单个字符、两个字符、三个字符、四个字符......
        for (int gap = 0; gap < length; gap++) {
            for (int i = 0; i < length - gap; i++) {
                int j = i + gap;
                if (s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    if (j + 1 - i > result.length()) {
                        result = s.substring(i, j + 1);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Manacher（马拉车）算法
     * Manacher算法，又叫"马拉车"算法，可以在时间复杂度为O(n)的情况下求解一个字符串的最长回文子串长度的问题。
     * 1. 将初始字符串每个字符左右两边填充’#’(也可以是其它字符)，巧妙地解决对称数量奇偶的问题（如"aba"变成"#a#b#a#","bb"变成"#b#b#",处理后的回文子串都是奇数）；
     * 2. 遍历整个字符串，用一个数组来记录以该字符为中心的回文子串半径，并记录已经扩展到的右边界；
     * 3. 每一次遍历的时候，如果该字符在已知回文串最右边界的覆盖下，那么就计算其相对最右边界回文串中心对称的位置，得出已知回文串的长度；
     * 4. 判断该长度和右边界，如果达到了右边界，那么需要继续进行中心扩展探索。当然，如果第3步该字符没有在最右边界的"羽翼"下，则直接进行中心扩展探索。进行中心扩展探索的时候，同时又更新右边界；
     * 5. 最后得到最长回文子串之后，去掉其中的特殊符号即可。
     * 
     * 时间复杂度：O(n)，这个算法在循环的时候，要么扩展右边界，要么直接得出结论，时间复杂度可以到O(n)。
     * 空间复杂度：O(n)。
     */
    static String longestPalindromicSubstring3(String s) {
        // 1. 将初始字符串每个字符左右两边填充’#’(也可以是其它字符)
        int len = s.length(); //
        StringBuilder sb = new StringBuilder(2 * len + 1);
        sb.append('#');
        for (int i = 0; i < len; i++) {
            sb.append(s.charAt(i));
            sb.append('#');
        }

        // 2. 遍历整个字符串，用一个数组来记录以该字符为中心的回文子串半径，并记录已经扩展到的右边界；
        len = sb.length(); // 处理后的字串长度
        int right = 0, rightCenter = 0, center = 0, longestHalf = 0;
        int[] halfLenArr = new int[len];
        for (int i = 0; i < len; i++) {
            boolean needCalc = true; //
            // 3. 每一次遍历的时候，如果该字符在已知回文串最右边界的覆盖下，那么就计算其相对最右边界回文串中心对称的位置，得出已知回文串的长度；
            if (right > i) {
                int leftCenter = 2 * rightCenter - i;
                halfLenArr[i] = halfLenArr[leftCenter];
                if (i + halfLenArr[i] > right) {
                    halfLenArr[i] = right - i;
                }
                if (i + halfLenArr[leftCenter] < right) {
                    needCalc = false;
                }
            }
            // 4. 判断该长度和右边界，如果达到了右边界，那么需要继续进行中心扩展探索。当然，如果第3步该字符没有在最右边界的"羽翼"下，则直接进行中心扩展探索。进行中心扩展探索的时候，同时又更新右边界；
            // 中心扩展
            if (needCalc) {
                while (i - 1 - halfLenArr[i] >= 0 && i + 1 + halfLenArr[i] < len) {
                    if (sb.charAt(i + 1 + halfLenArr[i]) == sb.charAt(i - 1 - halfLenArr[i])) {
                        halfLenArr[i]++;
                    } else {
                        break;
                    }
                }
                // 更新右边界及中心
                right = i + halfLenArr[i];
                rightCenter = i;
                // 记录最长回文串
                if (halfLenArr[i] > longestHalf) {
                    center = i;
                    longestHalf = halfLenArr[i];
                }
            }
        }

        // 5. 最后得到最长回文子串之后，去掉其中的特殊符号即可。去掉之前添加的#
        StringBuilder res = new StringBuilder();
        for (int i = center - longestHalf + 1; i <= center + longestHalf; i += 2) {
            res.append(sb.charAt(i));
        }
        return res.toString();
    }

    public static void main(String[] args) {
        String src = "fkjlaabbccddeeddccbbaaljkd";
//        String src = "eabbaf";
//        String result = longestPalindromicSubstring(src);
        String result = longestPalindromicSubstring1(src);

//        String result = longestPalindromicSubstring3(src);
//        String result = longestPalindromicSubstring0(src);
        System.out.println(result);
    }
}
