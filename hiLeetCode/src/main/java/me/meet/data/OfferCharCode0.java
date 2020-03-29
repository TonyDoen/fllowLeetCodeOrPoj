package me.meet.data;

import java.util.*;

public final class OfferCharCode0 {
    private OfferCharCode0() {
    }

    /**
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

    /**
     * 题目：
     * 字符串的排列
     * 
     * 题目描述：
     * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。
     * 例如输入字符串abc,则打印出由字符a,b,c 所能排列出来的所有字符串
     * abc,acb,bac,bca,cab和cba。
     * 
     * 输入描述:
     * 输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。
     */
    static List<String> permutation(String src) {
        if (null == src || src.isEmpty()) {
            return Collections.emptyList();
        }

        LinkedList<String> result = new LinkedList<>();
        permutation(result, 0, src.toCharArray());
        Collections.sort(result);
        return result;
    }

    private static void permutation(LinkedList<String> cache, int begin, char[] arr) {
        if (begin == arr.length - 1) {
            cache.add(new String(arr));
            return;
        }

        int len = arr.length;
        for (int i = begin; i < len; i++) {
            // 与begin不同位置的元素相等，不需要交换
            if (i != begin && arr[i] == arr[begin]) {
                continue;
            }
            // 交换元素
            swap(arr, begin, i);
            // 处理后续元素
            permutation(cache, begin + 1, arr);
            // 数组复原
            swap(arr, begin, i);
        }
    }

    private static void swap(char[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        arr[i] = (char) (arr[i] ^ arr[j]);
        arr[j] = (char) (arr[i] ^ arr[j]);
        arr[i] = (char) (arr[i] ^ arr[j]);
    }

    private static void testPermutation() {
        String src = "accc";
        List<String> result = permutation(src);
        System.out.println(result);
    }

    /**
     * 题目：
     * 第一个只出现一次的字符
     *
     * 题目描述：
     * 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,
     * 并返回它的位置, 如果没有则返回 -1（需要区分大小写）.
     *
     * 思路：
     * 1、遍历字符串，Hash存储字符串每个字符出现的次数
     * 2、顺序遍历上面存储的结果，如果该字符出现次数为1次，则找到该字符及其位置
     */
    static int firstNotRepeatingChar(String src) {
        if (null == src || src.isEmpty()) {
            return -1;
        }

        Map<Character, String> m = new LinkedHashMap<>(); // 有序
        char[] arr = src.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            String idxCnt = m.get(c);
            if (null == idxCnt) {
                m.put(c, 1 + "|" + i); // cnt and first idx
            } else {
                String[] piece = idxCnt.split("\\|");
                idxCnt = (Integer.valueOf(piece[0])+1) + "|" + piece[1];
                m.put(c, idxCnt);
            }
        }

        for (Map.Entry<Character, String> entry : m.entrySet()) {
            String value = entry.getValue();
            if (value.startsWith("1|")) {
                return Integer.valueOf(value.split("\\|")[1]);
            }
        }
        return -1;
    }

    private static void testFirstNotRepeatingChar() {
        int res = firstNotRepeatingChar("google");
        System.out.println(res);
    }

    /**
     * 左旋转字符串
     * 汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。
     * 对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。
     * 例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。
     *
     * 思路
     * 如循环左移2位，asdfgh => dfghas
     * 1. 可先分别反转前两位元素、后几位元素(asdfgh =>sahgfd)，
     * 2. 在全量反转字符串(=>dfghsa)，得到目标字符串
     *
     *
     */
    static String leftRotateString(String src, int step) {
        if (null == src || src.isEmpty() || step < 1) {
            return src;
        }
        char[] arr = src.toCharArray();
        reverse(arr, 0, step - 1);
        reverse(arr, step, arr.length - 1);
        reverse(arr, 0, arr.length - 1);
        return new String(arr);
    }

    private static void reverse(char[] arr, int start, int end) {
        if (end - start < 1) {
            return;
        }
        for (; start < end; ) { // 交换两个位置上的元素; 使用异或运算交换，不需要额外空间
            arr[start] = (char) (arr[start] ^ arr[end]);
            arr[end] = (char) (arr[start] ^ arr[end]);
            arr[start] = (char) (arr[start] ^ arr[end]);
            start++;
            end--;
        }
    }

    private static void testLeftRotateString() {
        String res = leftRotateString("ABC DEF GHI  JK ", 2);
        System.out.println(res);
    }

    /**
     * reverseSentence
     * 最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。
     * 同事Cat对Fish写的内容颇感兴趣，有一天他向Fish借来翻看，但却读不懂它的意思。
     * 例如，“student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，
     * 正确的句子应该是“I am a student.”。
     *
     * 思路：
     * 1、先把每个单词都进行反转
     * 2、再把字符串全部反转
     */
    static String reverseSentence(String src) {
        if (null == src || src.isEmpty()) {
            return src;
        }
        char[] arr = src.toCharArray();
        int pre = 0;
        for (int i = 0; i < arr.length; i++) {
            if (' ' == arr[i]) {
                reverse(arr, pre, i - 1);
                pre = i;
            }
        }
        reverse(arr, 0, arr.length - 1);
        return new String(arr);
    }

    private static void testReverseSentence() {
        String src = " student. a am I ";
        String res = reverseSentence(src);
        System.out.println(res);
    }

    /**
     * 将一个字符串转换成一个整数(实现Integer.valueOf(string)的功能，
     * 但是string不符合数字要求时返回0)，要求不能使用字符串转换整数的库函数。
     * 数值为0或者字符串不是一个合法的数值则返回0。
     *
     * 输入描述:
     * 输入一个字符串,包括数字字母符号,可以为空
     * 输出描述:
     * 如果是合法的数值表达则返回该数字，否则返回0
     *
     * 1、正负号判断
     * 2、有符号无符号
     * 3、'0'的ASCII码为48
     */
    static int str2Int(String src) {
        if (null == src || src.isEmpty()) {
            return 0;
        }
        char[] arr = src.toCharArray();
        boolean isNegative = false;
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            char cur = arr[i];
            if (i == 0 && (cur == '-' || cur == '+')) {
                isNegative = cur == '-';
            } else if (cur >= '0' && cur <= '9') {
                // res * 10 = res << 1 + res << 3
//                res = (res << 1) + (res << 3) + ((int) cur - 48);
                res = res * 10 + ((int) cur - 48);
            } else {
                return 0;
            }
        }
        if (isNegative) {
            return -1 * res;
        } else {
            return res;
        }
    }

    private static void testStr2Int() {
        String src = "+2182";
        System.out.println(str2Int(src));
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
     * 表示数值的字符串
     *
     * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
     * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
     * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
     *
     * 思路
     * 1、使用正则表达式
     * 2、遍历字符串，逐个情况判断
     */
    /**
     * 思路：
     * 1、使用正则表达式
     */
    static boolean isNumeric(String src) {
        if (null == src || src.length() <= 0) {
            return false;
        }
        return src.matches("[+-]?[0-9]*(\\.[0-9]*)?([eE][+-]?[0-9]+)?");
    }

    static boolean isNumeric2(char[] str) {
        if (str == null || str.length <= 0) {
            return false;
        }
        // 标记符号、小数点、e是否出现过
        boolean symbol = false, decimal = false, hasE = false;
        int len = str.length;
        for (int i = 0; i < len; i++) {
            if (str[i] == 'e' || str[i] == 'E') {
                if (i == len-1) {
                    return false; // e后面一定要接数字
                }
                if (hasE) {
                    return false;  // 不能同时存在两个e
                }
                hasE = true;
            } else if (str[i] == '+' || str[i] == '-') {
                // 第二次出现+-符号，则必须紧接在e之后
                if (symbol && str[i-1] != 'e' && str[i-1] != 'E') {
                    return false;
                }
                // 第一次出现+-符号，且不是在字符串开头，则也必须紧接在e之后
                if (!symbol && i > 0 && str[i-1] != 'e' && str[i-1] != 'E') {
                    return false;
                }
                symbol = true;
            } else if (str[i] == '.') {
                // e后面不能接小数点，小数点不能出现两次
                if (hasE || decimal) {
                    return false;
                }
                decimal = true;
            } else if (str[i] < '0' || str[i] > '9') // 不合法字符
                return false;
        }
        return true;
    }

    private static void testIsNumeric() {
        boolean res = isNumeric("5e2");
        System.out.println(res);

        boolean res2 = isNumeric2("5e2".toCharArray());
        System.out.println(res2);
    }

    /**
     * 字符流中第一个不重复的字符
     *
     * 请实现一个函数用来找出字符流中第一个只出现一次的字符。
     * 例如，当从字符流中只读出前两个字符"go"时，第一个只出现一次的字符是"g"。
     * 当从该字符流中读出前六个字符“google"时，第一个只出现一次的字符是"l"。
     *
     * 输出描述:
     * 如果当前字符流没有存在出现一次的字符，返回#字符。
     *
     * 思路:
     * 1、用一个128大小的数组统计每个字符出现的次数
     * 2、用一个队列，如果第一次遇到ch字符，则插入队列；其他情况不在插入
     * 3、求解第一个出现的字符，判断队首元素是否只出现一次，如果是直接返回，否则删除继续第3步骤
     */

    /**
     * 最小覆盖子串
     *
     * 目标：
     * 给定一个字符串source和一个目标字符串target，在字符串source中找到包括所有目标字符串字母的子串。
     *
     * 注意事项：
     * 如果在source中没有这样的子串，返回""，如果有多个这样的子串，返回起始位置最小的子串。
     * 
     * 思路：
     * 1、子串构建一个map,里面存储每个char字符出现的个数(首先采用一个大小为256的数组充当hashmap的功能，记录tartget中字母出现次数)
     * 2、遍历source数组，开始时start=0，i=0; start记录当前字串的起点，i相当于当前字串的终点。
     * 3、用found表示当前字串中包含target中字符的数目，如果found=target.length()则表明当前字串包含了target中所有字符，如果满足，进入下一步。
     * 4、删除当前start占用的字符的数目，found计数器也要减1
     * 5、将start后移，取出start前面多余的元素，已达到字串最小的目标。
     * 6、判断，如果当前字串小于历史搜到的最小字串，则将当前字串的长度，起始点，结束点都记录，更新。
     * 7、将start后移，寻找下一个字串
     *
     */
    static String minWindow4(String s, String t) {
        if (s == null || t == null || s.isEmpty() || t.isEmpty() || s.length() < t.length()) {
            return "";
        }

        char[] tArr = t.toCharArray();
        int[] tCount = new int[256];
        for (char c : tArr) {
            tCount[c] += 1;
        }

        String res = "";
        char[] sArr = s.toCharArray();
        int sLen = sArr.length;
        int[] sCount = new int[256];

        int satisfy = 0;
        int satTarget = tArr.length;

        int minLen = sLen;

        for (int i=0, j=0; j<sLen; j++) {
            char cur = sArr[j];
            sCount[cur] += 1;
            if (sCount[cur] <= tCount[cur]) {
                satisfy++;
            }

            // 前移i
            while (i<=j && tCount[sArr[i]] < sCount[sArr[i]]) {
                sCount[sArr[i]] -= 1;
                i++;
            }

            if (satisfy == satTarget && j-i+1 <= minLen) {
                res = s.substring(i,j+1);
                minLen = j-i+1;
            }
        }

        return res;
    }
    private static void testMinWindow4() {
        String s = "ABC";
        String t = "AC";

        String res = minWindow4(s, t);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testPermutation();
        testFirstNotRepeatingChar();
        testLeftRotateString();
        testReverseSentence();
        testStr2Int();
        testMatch();
        testIsNumeric();
        testMinWindow4();
    }
}
