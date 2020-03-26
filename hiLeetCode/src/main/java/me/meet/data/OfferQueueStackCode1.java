package me.meet.data;

import java.util.Stack;

/**
 * url: https://blog.csdn.net/zangdaiyang1991/article/details/88624667
 */
public final class OfferQueueStackCode1 {
    /**
     * 自己实现一个栈
     *
     * 思路：
     * 1、使用链表实现一个栈，便于扩容(不用考虑数组扩容场景)，维护根节点和最后入栈的节点
     * 2、节点中维护pre指针，便于pop()时快速找到上一个节点
     *
     */
    static class SelfStack<T> {
        private static class Node<T> {
            T value;
            Node<T> pre;

            Node(T val) {
                this.value = val;
            }
        }

        private Node<T> head;
        private Node<T> tail;

        public void push(T val) {
            if (null == head) {
                head = new Node<>(val);
                tail = head;
                return;
            }
            Node<T> node = new Node<>(val);
            node.pre = tail;
            tail = node;
        }

        public T pop() {
            if (null == head) {
                return null;
            }
            T val;
            if (head == tail) {
                val = head.value;
                head = null;
                tail = null;
                return val;
            }
            val = tail.value;
            tail = tail.pre;
            return val;
        }
    }

    private static void testSelfStack() {
        SelfStack<Integer> stack = new SelfStack<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        for (; ; ) {
            Integer val = stack.pop();
            if (null == val) {
                break;
            }
            System.out.print(val + " ");
        }
        System.out.println();
    }

    /**
     * 实现一个队列，自定义栈，用链表实现，方便扩容
     */
    static class SelfQueue<T> {
        private static class Node<T> {
            T value;
            Node<T> next;

            Node(T val) {
                this.value = val;
            }
        }

        private Node<T> head;
        private Node<T> tail;
        private int size = 0;

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return null == head || 0 == size;
        }

        public void offer(T val) {
            size++;
            if (null == head) {
                head = new Node<>(val);
                tail = head;
                return;
            }
            Node<T> node = new Node<>(val);
            tail.next = node;
            tail = node;
        }

        public T poll() {
            if (null == head) {
                return null;
            }
            size--;
            T val;
            if (head == tail) {
                val = head.value;
                head = null;
                tail = null;
                return val;
            }
            val = head.value;
            head = head.next;
            return val;
        }
    }

    private static void testSelfQueue() {
        SelfQueue<Integer> stack = new SelfQueue<>();
        for (int i = 0; i < 10; i++) {
            stack.offer(i);
        }
        for (; ; ) {
            Integer val = stack.poll();
            if (null == val) {
                break;
            }
            System.out.print(val + " ");
        }
        System.out.println();
    }

    /**
     * 两个队列实现一个栈
     *
     * 一个队列加入元素，弹出元素时，需要把队列中的 元素放到另外一个队列中，删除最后一个元素
     * 两个队列始终保持只有一个队列是有数据的
     */
    static class StackByQueue<T> {
        private final SelfQueue<T> sq1 = new SelfQueue<>();
        private final SelfQueue<T> sq2 = new SelfQueue<>();

        public void push(T val) {
            if (!sq1.isEmpty()) {
                sq1.offer(val);
            } else {
                sq2.offer(val);
            }
        }

        public T pop() {
            if (sq1.isEmpty() && sq2.isEmpty()) {
                return null;
            }
            if (!sq1.isEmpty() && sq2.isEmpty()) {
                for(; sq1.size() > 1;) {
                    sq2.offer(sq1.poll());
                }
                return sq1.poll();
            }
            if (sq1.isEmpty() && !sq2.isEmpty()) {
                for (; sq2.size() > 1;) {
                    sq1.offer(sq2.poll());
                }
                return sq2.poll();
            }
            return null;
        }
    }

    private static void testStackByQueue() {
        StackByQueue<Integer> stack = new StackByQueue<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        for (; ; ) {
            Integer val = stack.pop();
            if (null == val) {
                break;
            }
            System.out.print(val + " ");
        }
        System.out.println();
    }

    /**
     * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
     *
     * 思路
     * 1、一个栈存放进入的元素
     * 2、另外一个栈，删除元素，如果没有元素，则把上一个栈的元素入栈后删除元素
     */
    static class QueueByStack<T> {
        private final Stack<T> st1 = new Stack<>(); // input
        private final Stack<T> st2 = new Stack<>(); // output

        public void offer(T node) {
            st1.push(node);
        }

        public T poll() {
            if (st2.isEmpty() && st1.isEmpty()) {
                return null;
            }
            if (!st2.isEmpty()) {
                return st2.pop();
            }
            for (; !st1.isEmpty(); ) {
                st2.push(st1.pop());
            }
            return st2.pop();
        }
    }

    private static void testQueueByStack() {
        QueueByStack<Integer> queue = new QueueByStack<>();
        for (int i = 0; i < 10; i++) {
            queue.offer(i);
        }

        for (Integer val = queue.poll(); null != val; ) {
            System.out.print(val + " ");
            val = queue.poll();
        }
        System.out.println();
    }

    /**
     * 定义栈的数据结构，请在该类型中实现一个能够得到栈中
     * 所含最小元素的min函数（时间复杂度应为O（1））
     *
     * 思路：
     * 1、使用两个栈，第一个容纳元素，第二个记录当前的最小值
     * 2、第二个栈，push时，如果当前元素比栈顶元素小，则push一次当前栈顶元素，
     * 3、pop时，直接弹出栈顶元素即可
     *
     */
    static class StackWithMin<T extends Comparable> {
        private final Stack<T> st = new Stack<>();
        private final Stack<T> minSt = new Stack<>();

        public boolean isEmpty() {
            return st.isEmpty();
        }

        public void push(T node) {
            st.push(node);
            if (minSt.isEmpty()) {
                minSt.push(node);
            } else {
                T peek = minSt.peek();
                minSt.push(node.compareTo(peek) < 0 ? node : peek);
            }
        }

        public T pop() {
            minSt.pop();
            return st.pop();
        }

        public T min() {
            return minSt.peek();
        }
    }

    private static void testStackWithMin() {
        StackWithMin<Integer> stack = new StackWithMin<>();

//        for (int i = 9; i > 0; i--) {
//            stack.push(i);
//        }
        stack.push(4);
        stack.push(5);
        stack.push(6);
        stack.push(1);
        stack.push(2);
        stack.push(3);

        for (; !stack.isEmpty(); ) {
            System.out.print(stack.min() + " ");
            stack.pop();
        }
        System.out.println();
    }

    static class StackWithMinMax<T extends Comparable> {
        private static class Nd<T extends Comparable> {
            T data;
            Nd next;

            Nd(T data, Nd next) {
                this.data = data;
                this.next = next;
            }
        }

        private Nd head;
        private Nd max;
        private Nd min;
        
        public boolean isEmpty() {
            return null == head;
        }

        public void push(T data) {
            if (null == data) {
                return;
            }

            if (null == head) {
                head = new Nd<>(data, null);
                max = head;
                min = head;
            }
            // head
            Nd tmp = new Nd<>(data, head); // 头插
            head = tmp;

            // max
            if (data.compareTo(max.data) > 0) {
                tmp = new Nd<>(data, max);
                max = tmp;
            } else {
                tmp = new Nd<>(max.data, max);
                max = tmp;
            }

            // min
            if (data.compareTo(min.data) < 0) {
                tmp = new Nd<>(data, min);
                min = tmp;
            } else {
                tmp = new Nd<>(min.data, min);
                min = tmp;
            }
        }

        public T pop() {
            if (null == head) {
                return null;
            }

            Nd<T> res = head;
            head = head.next;
            max = max.next;
            min = min.next;
            return res.data;
        }

        public T min() {
            if (null == min) {
                return null;
            }
            return (T) min.data;
        }

        public T max() {
            if (null == max) {
                return null;
            }
            return (T) max.data;
        }
    }

    private static void testStackWithMinMax() {
        StackWithMinMax<Integer> stack = new StackWithMinMax<>();
        stack.push(4);
        stack.push(5);
        stack.push(6);
        stack.push(1);
        stack.push(2);
        stack.push(3);

        for (; !stack.isEmpty(); ) {
            System.out.print(stack.min() + " ");
            stack.pop();
        }
        System.out.println();
    }


    /**
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
        Stack<Integer> st = new Stack<>();
        for (int i = 0, j = 0; i < push.length; i++) {
            st.push(push[i]);                                     // 入栈元素
            for (; !st.isEmpty() && st.peek().equals(pop[j]); ) { // 栈顶元素=要弹出的元素，则栈中元素出栈
                st.pop();
                j++;
            }
        }
        return st.isEmpty();
    }
    /**
     * 思路2：
     * 基于元素出栈序列，模拟入栈过程
     * 解决这个问题很直观的想法就是建立一个辅助栈，把输入的第一个序列中的数字依次压入该辅助栈，并按照第二个序列的顺序依次从该栈中弹出数字。
     *
     * 判断一个序列是不是栈的弹出序列的规律：如果下一个弹出的数字刚好是栈顶数字，那么直接弹出。
     * 如果下一个弹出的数字不在栈顶，我们把压栈序列中还没有入栈的数字压入辅助栈，直到把下一个需要弹出的数字压入栈顶为止。
     * 如果所有的数字都压入栈了仍然没有找到下一个弹出的数字，那么该序列不可能是一个弹出序列。
     */
    static boolean isPopOrder1(int[] push, int[] pop) {
        if (null == push || null == pop || push.length != pop.length || push.length < 1) {
            return false;
        }
        Stack<Integer> st = new Stack<>();
        for (int i = 0, j = 0; i < push.length; i++) {
            int cur = pop[i];
            for (; j < push.length && (st.isEmpty() || st.peek() != cur); ) { // 如果当前元素不是要弹出的元素 则数据入栈
                st.push(push[j]);
                j++;
            }
            if (st.peek() == cur) {                                           // 弹出当前元素
                st.pop();
            } else {
                return false;
            }
        }
        return true;
    }

    private static void testIsPopOrder() {
        int[] push = new int[]{1, 2, 3, 4, 5};
        int[] pop = new int[]{4, 5, 3, 2, 1};
        boolean res = isPopOrder(push, pop);
        System.out.println(res);

        res = isPopOrder1(push, pop);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testSelfStack();
        testSelfQueue();
        testStackByQueue();
        testQueueByStack();
        testStackWithMinMax();
        testStackWithMin();
        testIsPopOrder();
    }
}
