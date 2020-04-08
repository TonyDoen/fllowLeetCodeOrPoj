package me.meet.leetcode.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FindAndReplacePattern {
    private FindAndReplacePattern() {}

    /**
     * 890. Find and Replace Pattern
     * You have a list of `words` and a `pattern`, and you want to know which words in `words` matches the pattern.
     * A word matches the pattern if there exists a permutation of letters p so that after replacing every letter x in the pattern with p(x), we get the desired word.
     * (Recall that a permutation of letters is a bijection from letters to letters: every letter maps to another letter, and no two letters map to the same letter.)
     * Return a list of the words in words that match the given pattern.
     * You may return the answer in any order.
     *
     * Example 1:
     * Input: words = ["abc","deq","mee","aqq","dkd","ccc"], pattern = "abb"
     * Output: ["mee","aqq"]
     *
     * Explanation: "mee" matches the pattern because there is a permutation {a -> m, b -> e, ...}.
     * "ccc" does not match the pattern because {a -> c, b -> c, ...} is not a permutation,
     * since a and b map to the same letter.
     *
     * Note:
     * 1 <= words.length <= 50
     * 1 <= pattern.length = words[i].length <= 20
     *
     * 题意：查找和替换模式
     * 思路0：
     * 这道题给了我们一个字符串数组 words，还有一个 pattern 单词，问 words 数组中的单词是否满足 pattern 的模式，并给了一个例子。比如 pattern 是 abb 的话，表示后两个字母是相同的，比如 mee 和 aqq，那么一个很直接的想法就是建立每个单词 word 和 pattern 中每个字符之间的映射，比如 mee->abb 的话，就是 m->a, e->b，在建立映射之前要判断，若已经存在了该映射，且映射值不是当前 pattern 中的对应字符时，就是无法匹配的，比如 mm 和 ab，在第一次建立了 m->a 的映射，当遍历到第二个m的时候，发现m的映射已经存在，但不是b，就不能再建立 m->b 的映射，则表示无法匹配。
     * 分析到这里，你可能感觉没啥问题，但其实我们忽略一种情况，word 和 pattern 中的每个字符必须是一一对应的，任何一个方向的多对一都是不行了，比如 mn 和 aa，刚开始建立了 m->a 的映射，遍历到n的时候，发现没有n的映射，此时也不能建立 n->a 的映射，因为 pattern 中的a已经被占用了，所以还需要一个 HashMap 来建立反方向的映射，只有两个 HashMap 中都不存在的，才能建立映射，只要有一个已经存在了，直接 break 掉。
     * 在 for 循环结束后，看是否已经到达了 word 的末尾，没有提前 break 掉的话，就将 word 加入结果 res 中即可，
     */
    static List<String> findAndReplacePattern0(String[] words, String pattern) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            Map<Character, Character> w2p = new HashMap<>(), p2w = new HashMap<>();
            int i = 0, n = word.length();
            for (; i < n; i++) {
                char iwc = word.charAt(i), ipc = pattern.charAt(i);
                Character w2pChar = w2p.get(iwc), p2wChar = p2w.get(ipc);

                if (null != w2pChar && !w2pChar.equals(ipc)) {
                    break;
                }
                w2p.put(iwc, ipc);
                if (null != p2wChar && !p2wChar.equals(iwc)) {
                    break;
                }
                p2w.put(ipc, iwc);
            }
            if (i == n) {
                result.add(word);
            }
        }
        return result;
    }

    private static void testFindAndReplacePattern0() {
        String[] words = new String[]{"abc","deq","mee","aqq","dkd","ccc"};
        String pattern = "abb";
        List<String> result = findAndReplacePattern0(words, pattern);
        System.out.println(result);
    }

    /**
     * 思路1：
     * 也可以不用 HashMap，改用两个长度为 26 的数组，因为这道题默认都是小写字母
     * 是要把字母减去 'a' 来转为对应的坐标，还有一点跟上面解法不同的地方就是，字母是跟起坐标位置加1来建立映射（加1的原因是默认值是0，而当 i=0 时为了区分默认值，就要加1），因为两个字母都跟一个特定的值相等，其实也等价于这两个字母之间建立的映射（a->c, b->c => a->b）。
     *
     */
    static List<String> findAndReplacePattern1(String[] words, String pattern) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            int[] w2p = new int[26], p2w = new int[26];
            int i = 0, n = word.length();
            for (; i < n; i++) {
                char iwc = word.charAt(i), ipc = pattern.charAt(i);
                int wIdx = iwc - 'a', pIdx = ipc - 'a';
                if (w2p[wIdx] != p2w[pIdx]) {
                    break;
                }
                p2w[pIdx] = i + 1;
                w2p[wIdx] = p2w[pIdx];
            }
            if (i == n) {
                result.add(word);
            }
        }
        return result;
    }

    private static void testFindAndReplacePattern1() {
        String[] words = new String[]{"abc", "deq", "mee", "aqq", "dkd", "ccc"};
        String pattern = "abb";
        List<String> result = findAndReplacePattern1(words, pattern);
        System.out.println(result);
    }

    /**
     * 思路2：
     * 这种解法相当于把所有的单词都转为了一种特定的模式，具体来说，就是用一个 HashMap，建立每个字母跟其之前出现过的字母种类个数之前的映射，比如 mee->011，aqq->011，这样相同的模式映射的值是一样的，
     * 具体的做法是若当前字母没有出现过，则建立和当前 HashMap 中的映射个数之间的映射，是一种很巧妙的设计思路，只不过最后又给每个数字加上了 'a'，转为了字母的 pattern，即 mee->abb，aqq->abb，
     */
    static List<String> findAndReplacePattern2(String[] words, String pattern) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (helper(word).equals(helper(pattern))) {
                result.add(word);
            }
        }
        return result;
    }

    private static String helper(String word) {
        Map<Character, Integer> mp = new HashMap<>();
        int length = word.length();
        for (int i = 0; i < length; i++) {
            char ic = word.charAt(i);
            Integer cnt = mp.get(ic);
            if (null == cnt) {
                mp.put(ic, mp.size());
            }
        }
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = (char) ('a' + mp.get(word.charAt(i)));
        }
        return String.valueOf(result);
    }

    private static void testFindAndReplacePattern2() {
        String[] words = new String[]{"abc","deq","mee","aqq","dkd","ccc"};
        String pattern = "abb";
        List<String> result = findAndReplacePattern2(words, pattern);
        System.out.println(result);
    }

    public static void main(String[] args) {
        testFindAndReplacePattern0();
        testFindAndReplacePattern1();
        testFindAndReplacePattern2();
    }
}
