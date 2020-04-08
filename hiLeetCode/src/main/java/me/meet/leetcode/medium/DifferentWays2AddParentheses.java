package me.meet.leetcode.medium;

import java.util.*;

public class DifferentWays2AddParentheses {
    /**
     * url: https://www.cnblogs.com/grandyang/p/4682458.html
     *
     * Given a string of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators.
     * The valid operators are +, - and *.
     *
     * Example 1:
     * Input: "2-1-1"
     * Output: [0, 2]
     *
     * Explanation:
     * ((2-1)-1) = 0
     * (2-(1-1)) = 2
     *
     * Example 2:
     * Input: "2*3-4*5"
     * Output: [-34, -14, -10, -10, 10]
     *
     * Explanation:
     * (2*(3-(4*5))) = -34
     * ((2*3)-(4*5)) = -14
     * ((2*(3-4))*5) = -10
     * (2*((3-4)*5)) = -10
     * (((2*3)-4)*5) = 10
     *
     * 题意：添加括号的不同方式，求不同括号添加方式的计算结果
     * 思路：若 input 是数字和运算符的时候，比如 "1+1" 这种情况，那么加不加括号也没有任何影响，因为只有一个计算，结果一定是2。
     * 复杂一点的话，input 是 "2-1-1" 时，就有两种情况了，(2-1)-1 和 2-(1-1)，由于括号的不同，得到的结果也不同，
     * 但如果我们把括号里的东西当作一个黑箱的话，那么其就变为 ()-1  和 2-()，其最终的结果跟括号内可能得到的值是息息相关的，
     * 那么再概括一点，实际上就可以变成 () ? () 这种形式，两个括号内分别是各自的表达式，最终会分别计算得到两个整型数组，中间的问号表示运算符，可以是加，减，或乘。
     *
     * 那么问题就变成了从两个数组中任意选两个数字进行运算，
     * 这种左右两个括号代表的黑盒子就交给递归去计算，
     * 像这种分成左右两坨的 pattern 就是分治法 Divide and Conquer 了。
     *
     */
    static List<Integer> differentWaysToCompute(String input) {
        LinkedList<Integer> res = new LinkedList<>();
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char ci = input.charAt(i);
            if ('+' == ci || '-' == ci || '*' == ci) {
                List<Integer> left = differentWaysToCompute(input.substring(0, i));
                List<Integer> right = differentWaysToCompute(input.substring(i + 1, length));
                for (Integer l : left) {
                    for (Integer r : right) {
                        if ('+' == ci) {
                            res.push(l + r);
                        } else if ('-' == ci) {
                            res.push(l - r);
                        } else {
                            res.push(l * r);
                        }
                    }
                }
            }
        }
        if (res.isEmpty()) {
            res.push(Integer.valueOf(input));
        }
        return res;
    }

    private static void testDifferentWaysToCompute() {
        String input = "2*3-4*5";
        System.out.println(differentWaysToCompute(input));
    }

    private static final Map<String, List<Integer>> orderMap = new LinkedHashMap<>();

    static List<Integer> differentWaysToComputeUp(String input) {
        // 1.1 优化 避免递归中的重复计算
        List<Integer> tmp = orderMap.get(input);
        if (null != tmp) {
            return tmp;
        }
        LinkedList<Integer> res = new LinkedList<>();
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char ci = input.charAt(i);
            if ('+' == ci || '-' == ci || '*' == ci) {
                List<Integer> left = differentWaysToComputeUp(input.substring(0, i));
                List<Integer> right = differentWaysToComputeUp(input.substring(i + 1, length));
                for (Integer l : left) {
                    for (Integer r : right) {
                        if ('+' == ci) {
                            res.push(l + r);
                        } else if ('-' == ci) {
                            res.push(l - r);
                        } else {
                            res.push(l * r);
                        }
                    }
                }
            }
        }
        if (res.isEmpty()) {
            res.push(Integer.valueOf(input));
        }
        // 1.2 优化 避免递归中的重复计算
        orderMap.put(input, res);
        return res;
    }

    private static void testDifferentWaysToComputeUp() {
        String input = "2*3-4*5";
        System.out.println(differentWaysToComputeUp(input));
    }

    /**
     * 抽出计算部分: 模拟函数指针
     */
    abstract static class DifferentWaysToComputeTemplate {
        abstract List<Integer> differentWaysToCompute(String input);
    }

    static class DifferentWaysToCompute extends DifferentWaysToComputeTemplate {
        @Override
        List<Integer> differentWaysToCompute(String input) {
            LinkedList<Integer> res = new LinkedList<>();
            doCompute(res, input, this);
            if (res.isEmpty()) {
                res.push(Integer.valueOf(input));
            }
            return res;
        }
    }

    // 优化
    static class DifferentWaysToComputeUp extends DifferentWaysToComputeTemplate {
        private static final Map<String, List<Integer>> cacheMap = new HashMap<>();

        @Override
        List<Integer> differentWaysToCompute(String input) {
            // 1.1 优化 避免递归中的重复计算
            List<Integer> tmp = cacheMap.get(input);
            if (null != tmp) {
                return tmp;
            }

            LinkedList<Integer> res = new LinkedList<>();
            doCompute(res, input, this);
            if (res.isEmpty()) {
                res.push(Integer.valueOf(input));
            }
            // 1.2 优化 避免递归中的重复计算
            cacheMap.put(input, res);
            return res;
        }
    }

    private static void doCompute(LinkedList<Integer> res, String input, DifferentWaysToComputeTemplate template) {
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char ci = input.charAt(i);
            if ('+' == ci || '-' == ci || '*' == ci) {
                List<Integer> left = template.differentWaysToCompute(input.substring(0, i));
                List<Integer> right = template.differentWaysToCompute(input.substring(i + 1, length));
                for (Integer l : left) {
                    for (Integer r : right) {
                        if ('+' == ci) {
                            res.push(l + r);
                        } else if ('-' == ci) {
                            res.push(l - r);
                        } else {
                            res.push(l * r);
                        }
                    }
                }
            }
        }
    }

    private static void testDifferentWaysToComputeClass() {
        String input = "2*3-4*5";
        DifferentWaysToCompute compute = new DifferentWaysToCompute();
        System.out.println(compute.differentWaysToCompute(input));

        DifferentWaysToComputeUp computeUp = new DifferentWaysToComputeUp();
        System.out.println(computeUp.differentWaysToCompute(input));
    }

    public static void main(String[] args) {
        testDifferentWaysToCompute();
        testDifferentWaysToComputeUp();
        testDifferentWaysToComputeClass();
    }
}
