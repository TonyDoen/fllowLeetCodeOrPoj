package me.meet.leetcode.easy;

import java.util.ArrayList;
import java.util.List;

public class ReorderDataInLogFiles {
    /**
     * 937. Reorder Data in Log Files
     * 
     * You have an array of `logs`.  Each log is a space delimited string of words.
     * For each log, the first word in each log is an alphanumeric identifier.  Then, either:
     * 1> Each word after the identifier will consist only of lowercase letters, or;
     * 2> Each word after the identifier will consist only of digits.
     * We will call these two varieties of logs letter-logs and digit-logs.  It is guaranteed that each log has at least one word after its identifier.
     * Reorder the logs so that all of the letter-logs come before any digit-log.  The letter-logs are ordered lexicographically ignoring identifier, with the identifier used in case of ties.  The digit-logs should be put in their original order.
     * Return the final order of the logs.
     * 
     * Example 1:
     * Input: logs = ["dig1 8 1 5 1","let1 art can","dig2 3 6","let2 own kit dig","let3 art zero"]
     * Output: ["let1 art can","let3 art zero","let2 own kit dig","dig1 8 1 5 1","dig2 3 6"]
     * 
     * Constraints:
     * 1. 0 <= logs.length <= 100
     * 2. 3 <= logs[i].length <= 100
     * 3. logs[i] is guaranteed to have an identifier, and a word after the identifier.
     * 
     * 
     * 题意：日志文件的重新排序
     * 思路：这道题让给日志排序，每条日志是由空格隔开的一些字符串，第一个字符串是标识符，可能由字母和数字组成，后面的是日志的内容，只有两种形式的，要么都是数字的，要么都是字母的。排序的规则是对于内容是字母的日志，按照字母顺序进行排序，假如内容相同，则按照标识符的字母顺序排。而对于内容的是数字的日志，放到最后面，且其顺序相对于原顺序保持不变。博主感觉这道题似曾相识啊，貌似之前在很多 OA 中见过，最后还是被 LeetCode 收入囊中了。其实这道题就是个比较复杂的排序的问题，两种日志需要分开处理，对于数字日志，不需要排序，但要记录其原始顺序。这里就可以用一个数组专门来保存数字日志，这样最后加到结果 res 后面，就可以保持其原来顺序。关键是要对字母型日志进行排序，同时还要把标识符提取出来，这样在遍历日志的时候，先找到第一空格的位置，这样前面的部分就是标识符了，后面的内容就是日志内容了，此时判断紧跟空格位置的字符，假如是数字的话，说明当前日志是数字型的，加入数组 digitLogs 中，并继续循环。如果不是的话，将两部分分开，存入到一个二维数组 data 中。之后要对 data 数组进行排序，并需要重写排序规则，要根据日志内容排序，若日志内容相等，则根据标识符排序。最后把排序好的日志按顺序合并，存入结果 res 中，最后别忘了把数字型日志也加入 res，
     */
    static List<String> reorderLogFiles(String[] logs) {
        List<String> res = new ArrayList<>(), digitLogs = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        for (String log : logs) {
            int pos = log.indexOf(" ");
            char cp1 = log.charAt(pos + 1);
            if (cp1 >= '0' && cp1 <= '9') {
                digitLogs.add(log);
                continue;
            }
            List<String> d = new ArrayList<>();
            d.add(log.substring(0, pos));
            d.add(log.substring(pos + 1));
            data.add(d);
        }
        data.sort((o1, o2) -> o1.get(1).compareTo(o2.get(1)) < 1 || (o1.get(1).equals(o2.get(1)) && o1.get(0).compareTo(o2.get(0)) < 1) ? 1 : 0);
        for (List<String> a : data) {
            res.add(a.get(0) + " " + a.get(1));
        }
        for (String log : digitLogs) {
            res.add(log);
        }
        return res;
    }

    private static void testReorderLogFiles() {
        String[] logs = new String[]{"dig1 8 1 5 1", "let1 art can", "dig2 3 6", "let2 own kit dig", "let3 art zero"};
        List<String> res = reorderLogFiles(logs);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testReorderLogFiles();
    }
}
