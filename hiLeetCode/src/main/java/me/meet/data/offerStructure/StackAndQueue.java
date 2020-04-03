package me.meet.data.offerStructure;

import java.util.List;

public final class StackAndQueue {
    private StackAndQueue() {
    }

    static class Node<T> {
        T data;
        Node<T> next;

        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * 005-用两个栈实现队列
     */
    static class SelfQueueBy2Stack<T extends Comparable<T>> {
        private SelfStack<T> s1 = new SelfStack<>(), s2 = new SelfStack<>();

        public boolean isEmpty() {
            return s1.isEmpty() && s2.isEmpty();
        }

        public void offer(T data) {
            s1.push(data);
        }

        public T poll() {
            if (s1.isEmpty() && s2.isEmpty()) {
                return null;
            }
            if (!s2.isEmpty()) {
                return s2.pop();
            }
            for (; !s1.isEmpty(); ) {
                s2.push(s1.pop());
            }
            return s2.pop();
        }
    }

    /**
     * 006-自己实现队列
     */
    static class SelfQueue<T extends Comparable<T>> {
        private Node<T> head, tail;

        public boolean isEmpty() {
            return null == head;
        }

        public void offer(T data) {
            if (null == head) {
                head = new Node<>(data, null);
                tail = head;
                return;
            }
            tail.next = new Node<>(data, null);
            tail = tail.next;
        }

        public T poll() {
            if (null == head) {
                return null;
            }
            T result = head.data;
            head = head.next;
            if (null == head) {
                tail = null;
            }
            return result;
        }
    }

    /**
     * 020-包含min/max函数的栈
     */
    static class SelfStack<T extends Comparable<T>> {
        private Node<T> head;
        private Node<T> max;
        private Node<T> min;

        public boolean isEmpty() {
            return null == head;
        }

        public void push(T data) {
            if (null == data) {
                return;
            }

            if (null == head) {
                head = new Node<>(data, null);
                max = head;
                min = head;
                return;
            }
            // head
            head = new Node<>(data, head); // 头插

            // max
            if (data.compareTo(max.data) > 0) {
                max = new Node<>(data, max);
            } else {
                max = new Node<>(max.data, max);
            }

            // min
            if (data.compareTo(min.data) < 0) {
                min = new Node<>(data, min);
            } else {
                min = new Node<>(min.data, min);
            }
        }

        public T pop() {
            if (null == head) {
                return null;
            }

            Node<T> res = head;
            head = head.next;
            max = max.next;
            min = min.next;
            return res.data;
        }

        public T peek() {
            if (null == head) {
                return null;
            }
            return head.data;
        }

        public T min() {
            if (null == min) {
                return null;
            }
            return min.data;
        }

        public T max() {
            if (null == max) {
                return null;
            }
            return max.data;
        }
    }

    private static void testSelfQueueStack() {
        SelfQueueBy2Stack<Integer> s1 = new SelfQueueBy2Stack<>();
        SelfQueue<Integer> q1 = new SelfQueue<>();
        SelfStack<Integer> s2 = new SelfStack<>();
        for (int i = 0; i < 10; i++) {
            s1.offer(i);
            q1.offer(i);
            s2.push(i);
        }

        for (; !s1.isEmpty(); ) {
            System.out.print(s1.poll() + " ");
        }
        System.out.println();

        for (; !q1.isEmpty(); ) {
            System.out.print(q1.poll() + " ");
        }
        System.out.println();

        for (; !s2.isEmpty(); ) {
            System.out.print("; min:" + s2.min() + "; max:" + s2.max()+"; cur:" + s2.pop());
        }
        System.out.println();
    }

    /**
     * 021-栈的压入、弹出序列
     *
     * 栈的压入、弹出序列
     *
     * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。
     * 假设压入栈的所有数字均不相等。
     * 例如序列1,2,3,4,5是某栈的压入顺序，序列4,5,3,2,1是该压栈序列对应的一个弹出序列，
     * 但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）
     *
     *
     * 思路1：
     * 基于元素入栈序列，模拟出栈过程，最后还有元素残留，即为不是正确出栈序列
     * 1、push 元素依次入栈，必须先入栈元素，否则最后一个元素无法正确处理
     * 2、循环--如果栈顶元素是要出栈的元素，则弹出，出栈元素索引+1
     * 3、最后返回，栈是否为空 即可
     *
     */
    static boolean isPopOrder(int[] push, int[] pop) {
        if (null == push || null == pop || push.length != pop.length || push.length < 1) {
            return false;
        }
        SelfStack<Integer> st = new SelfStack<>();
        for (int i = 0, j = 0; i < push.length; i++) {
            st.push(push[i]);                                     // 入栈元素
            for (; !st.isEmpty() && st.peek().equals(pop[j]); ) { // 栈顶元素=要弹出的元素，则栈中元素出栈
                st.pop();
                j++;
            }
        }
        return st.isEmpty();
    }

    private static void testIsPopOrder() {
        int[] push = new int[]{1, 2, 3, 4, 5};
        int[] pop = new int[]{4, 5, 3, 2, 1};
        boolean res = isPopOrder(push, pop);
        System.out.println(res);
    }

    /**
     * 044-翻转单词顺序列(栈)
     *
     * 最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。
     * 同事Cat对Fish写的内容颇感兴趣，有一天他向Fish借来翻看，但却读不懂它的意思。
     * 例如，“student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，
     * 正确的句子应该是“I am a student.”。
     *
     * 思路1：
     * 1、stack
     *
     * 思路2：
     * 1. 每个单词反转 am -> ma
     * 2. 整个句子反转
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

    private static void testReverseSentence() {
        String src = " student. a am I ";
        String res = reverseSentence(src);
        System.out.println(res);
    }

    /**
     * 064-滑动窗口的最大值(双端队列)
     *
     * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。
     * 例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，
     * 那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}；
     * 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
     * {[2,3,4],2,6,2,5,1}，
     * {2,[3,4,2],6,2,5,1}，
     * {2,3,[4,2,6],2,5,1}，
     * {2,3,4,[2,6,2],5,1}，
     * {2,3,4,2,[6,2,5],1}，
     * {2,3,4,2,6,[2,5,1]}。
     *
     * 思路：
     * 1、遍历数组，每次取滑动窗口的所有值，遍历窗口中的元素，取出最大值
     */
    static List<Integer> maxInWindow(int[] arr, int size) {
        List<Integer> res = new java.util.LinkedList<>();
        if (null == arr || size < 1) {
            return res;
        }

        // find max
        if (arr.length <= size) {
            int max = Integer.MIN_VALUE;
            for (int i : arr) {
                max = Math.max(max, i);
            }
            res.add(max);
            return res;
        }

        int maxIdx = arr.length - size;
        for (int i = 0; i <= maxIdx; i++) {
            int curMax = arr[i];
            for (int j = i + 1; j < i + size; j++) {
                curMax = curMax > arr[j] ? curMax : arr[j];
            }
            res.add(curMax);
        }
        return res;
    }

    private static void testMaxInWindow() {
        int[] arr = new int[]{2, 3, 4, 2, 6, 2, 5, 1};
        int size = 3;
        List<Integer> res = maxInWindow(arr, size);
        System.out.println(res);
    }

    public static void main(String[] args) {
        // 005-用两个栈实现队列
        // 006-自己实现队列
        // 020-包含min/max函数的栈
        testSelfQueueStack();
        // 021-栈的压入、弹出序列
        testIsPopOrder();
        // 044-翻转单词顺序列
        testReverseSentence();
        // 064-滑动窗口的最大值(双端队列)
        testMaxInWindow();
    }
}
