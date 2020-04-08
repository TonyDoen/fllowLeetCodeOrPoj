package me.meet.leetcode.medium;

import java.util.LinkedList;

public final class EvaluateReversePolishNotation {
    /**
     * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
     * Valid operators are +, -, *, /. Each operand may be an integer or another expression.
     *
     * Note:
     * Division between two integers should truncate toward zero.
     * The given RPN expression is always valid. That means the expression would always evaluate to a result and there won't be any divide by zero operation.
     *
     * Example 1:
     * Input: ["2", "1", "+", "3", "*"]
     * Output: 9
     * Explanation: ((2 + 1) * 3) = 9
     *
     * Example 2:
     * Input: ["4", "13", "5", "/", "+"]
     * Output: 6
     * Explanation: (4 + (13 / 5)) = 6
     *
     * Example 3:
     * Input: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
     * Output: 22
     * Explanation:
     *   ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
     * = ((10 * (6 / (12 * -11))) + 17) + 5
     * = ((10 * (6 / -132)) + 17) + 5
     * = ((10 * 0) + 17) + 5
     * = (0 + 17) + 5
     * = 17 + 5
     * = 22
     *
     * 题意：计算逆波兰表达式
     * 逆波兰表达式就是把操作数放前面，把操作符后置的一种写法
     *
     * 思路：
     * 1、把数组从第一元素开始压入栈中
     * 2、如果是操作符，则取出栈中的前两个元素进行运算，运算后的结果压入栈中
     * 3、如果是数字，直接压入栈中即可
     *
     * 前提：输入的数组都是能正确运算的
     */
    static String evaluateReversePolishNotation(String[] arr) {
        if (null == arr || arr.length < 1) {
            return "0";
        }
        LinkedList<String> stack = new LinkedList<>();
        for (String str : arr) {
            // 是运算符，则计算结果入栈
            if ("+".equals(str) || "-".equals(str) || "*".equals(str) || "/".equals(str)) {
                int second = Integer.valueOf(stack.pop());
                int first = Integer.valueOf(stack.pop());
                int result = 0;
                switch(str){
                    case "+":
                        result = first + second;
                        break;
                    case "-":
                        result = first - second;
                        break;
                    case "*":
                        result = first * second;
                        break;
                    case "/":
                        result = first / second;
                        break;
                }
                stack.push(String.valueOf(result));
            } else { // 非运算符，入栈
                stack.push(str);
            }
        }
        return stack.pop();
    }

    private static void testEvaluateReversePolishNotation() {
        String[] arr = new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        String res = evaluateReversePolishNotation(arr);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testEvaluateReversePolishNotation();
    }
}
