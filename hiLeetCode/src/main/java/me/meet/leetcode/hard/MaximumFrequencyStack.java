package me.meet.leetcode.hard;

import java.util.HashMap;
import java.util.LinkedList;

public final class MaximumFrequencyStack {
    private MaximumFrequencyStack() {
    }

    /**
     * Implement FreqStack, a class which simulates the operation of a stack-like data structure.
     * FreqStack has two functions:
     * push(int x), which pushes an integer xonto the stack.
     * pop(), which removes and returns the most frequent element in the stack.
     * If there is a tie for most frequent element, the element closest to the top of the stack is removed and returned.
     *
     * Example 1:
     * Input:
     * ["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
     * [[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
     * Output: [null,null,null,null,null,null,null,5,7,5,4]
     *
     * Explanation:
     * After making six .push operations, the stack is [5,7,5,7,4,5] from bottom to top.  Then:
     * pop() -> returns 5, as 5 is the most frequent.
     * The stack becomes [5,7,5,7,4].
     *
     * pop() -> returns 7, as 5 and 7 is the most frequent, but 7 is closest to the top.
     * The stack becomes [5,7,5,4].
     *
     * pop() -> returns 5.
     * The stack becomes [5,7,4].
     *
     * pop() -> returns 4.
     * The stack becomes [5,7].
     *
     * Note:
     * Calls to FreqStack.push(int x) will be such that 0 <= x <= 10^9.
     * It is guaranteed that FreqStack.pop() won't be called if the stack has zero elements.
     * The total number of FreqStack.push calls will not exceed 10000 in a single test case.
     * The total number of FreqStack.pop calls will not exceed 10000 in a single test case.
     * The total number of FreqStack.push and FreqStack.pop calls will not exceed 150000across all test cases.
     */
    /**
     * 题意：最大频率栈
     * 这道题让我们实现一种最大频率栈，有入栈和出栈功能，需要每次出栈的都是栈中出现频率最大的数字，若有多个数字的频率相同，那么离栈顶最近的元素先出栈。
     *
     * 思路：
     * 这道题的解法却要比之前那两道要简单的多。这里只跟数字出现的频率有关，只有在频率相等的情况下才会考虑栈的后入先出的特性，所以一定是需要统计栈中每个数字出现的频率的，我们使用一个 HashMap 来建立每个数字跟其出现次数之间的映射。由于频率相等的数字可能有多个，所以我们必须知道某个特性频率下都有哪些数字，再用一个 HashMap 来建立频率和该频率下所有的数字之间的映射，可以将这些数组放到一个数组或者一个栈中，这里为了简便起见，就使用一个数组了。另外，我们还需要维护一个当前最大频率的变量，可以通过这个值到 HashMap 中快速定位数组的位置。好，一切准备就绪之后就开始解题吧，对于入栈函数 push()，首先需要将x对应的映射值加1，并更新最大频率 mxFreq，然后就是要把x加入当前频率对应的数组中，注意若某个数字出现了3次，那么数字会分别加入频率为 1，2，3 的映射数组中。接下来看出栈函数 pop() 如何实现，由于我们知道当前最大频率 mxFreq，就可以直接去 HashMap 中取出该频率下的所有数字的数组，题目说了若频率相等，取离栈顶最近的元素，这里就是数组末尾的数组，取到之后，要将该数字从数组末尾移除。移除之后，我们要检测一下，若数组此时为空了，说明当前最大频率下之后一个数字，取出之后，最大频率就要自减1，还有不要忘记的就是取出数字的自身的频率值也要自减1，参见代码如下：
     */
    static class FreqStack {
        private int mxFreq;
        private HashMap<Integer, Integer> freq = new HashMap<>();
        private HashMap<Integer, LinkedList<Integer>> mp = new HashMap<>();

        void push(int x) {
            Integer f = freq.get(x);
            f = (null == f ? 0 : f);
            mxFreq = Math.max(mxFreq, ++f);
            freq.put(x, f);

            mp.computeIfAbsent(f, o -> new LinkedList<>()).add(x);
        }

        int pop() {
            LinkedList<Integer> mxL = mp.get(mxFreq);
            if (null == mxL) {
                return -1;
            }
            Integer x = mxL.pollLast();

            Integer f = freq.get(x);
            LinkedList<Integer> mxL2 = mp.get(f);
            freq.put(x, --f);

            if (null == mxL2 || mxL2.isEmpty()) {
                mxFreq--;
            }

            return x;
        }
    }

    public static void main(String[] args) {
        FreqStack fs = new FreqStack();
        fs.push(5);
        fs.push(7);
        fs.push(5);
        fs.push(7);
        fs.push(4);
        fs.push(5);

        fs.pop();
        fs.pop();
        fs.pop();
        fs.pop();
        fs.pop();
        fs.pop();
    }

}
