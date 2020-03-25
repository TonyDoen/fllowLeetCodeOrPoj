package me.meet.test;

public class KMP {
    /**
     * 假设现在我们面临这样一个问题：有一个文本串S，和一个模式串P，现在要查找P在S中的位置，怎么查找呢？
     *
     * 如果用暴力匹配的思路，
     * 并假设现在文本串S匹配到 i 位置，模式串P匹配到 j 位置，则有：
     * 如果当前字符匹配成功（即S[i] == P[j]），则i++，j++，继续匹配下一个字符；
     * 如果失配（即S[i]! = P[j]），令i = i - (j - 1)，j = 0。相当于每次匹配失败时，i 回溯，j 被置为0。
     *
     * 理清楚了暴力匹配算法的流程及内在的逻辑，咱们可以写出暴力匹配的代码，
     */

    /**
     * Knuth-Morris-Pratt 字符串查找算法，简称为 “KMP算法”，常用于在一个文本串S内查找一个模式串P 的出现位置，这个算法由Donald Knuth、Vaughan Pratt、James H. Morris三人于1977年联合发表，故取这3人的姓氏命名此算法。
     * 下面先直接给出KMP的算法流程（如果感到一点点不适，没关系，坚持下，稍后会有具体步骤及解释，越往后看越会柳暗花明☺）：
     *
     * 假设现在文本串S匹配到 i 位置，模式串P匹配到 j 位置
     * 1. 如果j = -1，或者当前字符匹配成功（即S[i] == P[j]），都令i++，j++，继续匹配下一个字符；
     * 2. 如果j != -1，且当前字符匹配失败（即S[i] != P[j]），则令 i 不变，j = next[j]。
     *    此举意味着失配时，模式串P相对于文本串S向右移动了j - next [j] 位。
     *    换言之，当匹配失败时，模式串向右移动的位数为：失配字符所在位置 - 失配字符对应的next 值（next 数组的求解会在下文的3.3.3节中详细阐述），
     *    即移动的实际位数为：j - next[j]，且此值大于等于1。
     *
     * 很快，你也会意识到next 数组各值的含义：代表当前字符之前的字符串中，有多大长度的相同前缀后缀。例如如果next [j] = k，代表j 之前的字符串中有最大长度为k 的相同前缀后缀。
     * 也意味着在某个字符失配时，该字符对应的next 值会告诉你下一步匹配中，模式串应该跳到哪个位置（跳到next [j] 的位置）。如果next [j] 等于0或-1，则跳到模式串的开头字符，若next [j] = k 且 k > 0，代表下次匹配跳到j 之前的某个字符，而不是跳到开头，且具体跳过了k 个字符。
     */

    static void kmp(String source, String pattern) {
        int[] N = getN(pattern);
        int res = 0;
        int sourceLength = source.length();
        int patternLength = pattern.length();
        for (int i = 0; i <= (sourceLength - patternLength); ) {
            res++;
            String str = source.substring(i, i + patternLength);//要比较的字符串
            System.out.println(str);
            int count = getNext(pattern, str, N);
            System.out.println("移动" + count + "步");
            if (count == 0) {
                System.out.println("KMP：匹配成功");
                break;
            }
            i = i + count;
        }
        System.out.println("KMP：一共匹配" + res + "次数");
    }

    private static int getNext(String pattern, String str, int[] N) {
        int n = pattern.length();
        char[] v1 = str.toCharArray();
        char[] v2 = pattern.toCharArray();
        int x = 0;
        while (n-- != 0) {
            if (v1[x] != v2[x]) {
                if (x == 0) {
                    return 1;//如果第一个不相同，移动1步
                }
                return x - N[x - 1];//x:第一次出现不同的索引的位置，即j
            }
            x++;
        }
        return 0;
    }

    private static int[] getN(String pattern) {
        char[] pat = pattern.toCharArray();
        int j = pattern.length() - 1;
        int[] N = new int[j + 1];
        for (int i = j; i >= 2; i--) {
            N[i - 1] = getK(i, pat);
        }
        for (int a : N)
            System.out.println(a);
        return N;
    }

    private static int getK(int j, char[] pat) {
        int x = j - 2;
        int y = 1;
        while (x >= 0 && compare(pat, 0, x, y, j - 1)) {
            x--;
            y++;
        }
        return x + 1;
    }

    private static boolean compare(char[] pat, int b1, int e1, int b2, int e2) {
        int n = e1 - b1 + 1;
        while (n-- != 0) {
            if (pat[b1] != pat[b2]) {
                return true;
            }
            b1++;
            b2++;
        }
        return false;
    }

    public static void main(String[] args) {
        String source = "jikijkcidiefjhijklmnijkl";
        String pattern = "ijkl";
        kmp(source, pattern);
    }
}

