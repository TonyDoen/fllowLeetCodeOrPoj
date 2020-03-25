package me.meet.data;

import java.util.*;

/**
 * url: https://blog.csdn.net/zangdaiyang1991/article/details/89339417
 * url: https://blog.csdn.net/zangdaiyang1991/article/details/90213738
 */
public final class OfferLinkedListCode1 {
    private OfferLinkedListCode1() {
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
     * 从尾到头打印单链表
     * 输入一个链表，按链表值从尾到头的顺序返回一个 List。
     *
     * 思路：
     * 遍历链表，把元素压入栈中，利用栈后进先出特性，遍历栈中元素，逐个打印
     */
    static List<Integer> printSingleLinkedList(Node<Integer> head) {
        if (null == head) {
            return Collections.emptyList();
        }
        Stack<Integer> stack = new Stack<>();
        Node<Integer> tmp = head;
        while (tmp != null) {
            stack.push(tmp.data);
            tmp = tmp.next;
        }

        List<Integer> result = new ArrayList<>();
        while (!stack.empty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private static void testPrintSingleLinkedList() {
        /**
         * 4 -> 3 -> 2 -> 1
         */
        Node<Integer> _1 = new Node<>(1, null);
        Node<Integer> _2 = new Node<>(2, _1);
        Node<Integer> _3 = new Node<>(3, _2);
        Node<Integer> _4 = new Node<>(4, _3);

        List<Integer> res = printSingleLinkedList(_4);
        System.out.println(res);
    }

    /**
     * 反转链表
     * 输入一个链表，反转链表后，输出新链表的表头。
     *
     * 1、只需遍历一次，遍历时拆开链表，当前元素指向前一个元素
     * 2、使用两个指针，一个指向当前遍历的节点，一个指向新链表的头结点
     *
     * 思路：
     * 遍历链表，把元素压入栈中，利用栈后进先出特性，遍历栈中元素，重组链表实现翻转
     *
     * 分析：此方式简单易行，但是需要额外空间，需要遍历两次(一次链表+一次栈遍历)
     */
    public static Node reverseListByStack(Node head) {
        // 1、判空
        if (head == null) {
            return null;
        }

        // 2、链表元素压入栈中
        Node curNode = head;
        Stack<Node> stack = new Stack<>();
        while (curNode != null) {
            stack.push(curNode);
            curNode = curNode.next;
        }

        // 3、重组新链表
        Node newNode = stack.pop();

        // 遍历方案1
        /*
        ListNode tmpNode = newNode;
        while (!stack.isEmpty()) {
            ListNode curPopNode = stack.pop();
            tmpNode.next = curPopNode;
            tmpNode = curPopNode;
        }
        tmpNode.next = null;
        */
        // 遍历方案2
        while (!stack.isEmpty()) {
            Node curPopNode = stack.pop();
            curPopNode.next.next = curPopNode;
            curPopNode.next = null;
        }

        return newNode;
    }

    /**
     * 思路：
     * 遍历链表，把元素压入栈中，利用栈后进先出特性，遍历栈中元素，重组链表实现翻转
     * 
     * 分析：此方式简单易行，但是需要额外空间，需要遍历两次(一次链表+一次栈遍历)
     */
    public static Node reverseList(Node head) {
        Node result = null;
        for (Node cur = head; null != cur;) {
            Node next = cur.next;
            cur.next = result;
            result = cur;
            cur = next;
        }
        return result;
    }

    private static void testReverseList() {
        /**
         * 4 -> 3 -> 2 -> 1
         */
        Node<Integer> _1 = new Node<>(1, null);
        Node<Integer> _2 = new Node<>(2, _1);
        Node<Integer> _3 = new Node<>(3, _2);
        Node<Integer> _4 = new Node<>(4, _3);

//        Node result = reverseList(_4);
        Node result = reverseListByStack(_4);
        for (;null != result;) {
            System.out.print(result.data + " ");
            result = result.next;
        }
        System.out.println();
    }

    /**
     * [] 括号正则
     *
     * eg:
     * "2[dd3[cc]]" => ddccccccddcccccc
     *
     */
    static String solveRegex(String src) {
        int len = src.length();
        char[] pieces = src.toCharArray();
        LinkedList<Integer> num = new LinkedList<Integer>();
        List<Character> chars = new ArrayList<Character>();
        for (int i = 0; i < len; i++) {
            char c = pieces[i];
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == '[' || c == ']')) {
                chars.add(0, c);
            } else {
                num.add(0, Integer.valueOf(String.valueOf(c)));
            }
        }

        StringBuilder sbr = new StringBuilder();
        for (Character c : chars) {
            if (c == ']') {
                continue;
            }
            if (c == '[') {
                Integer n = num.poll();
                String tmp = sbr.toString();

                for (int i = 0; i < n - 1; i++) {
                    sbr.append(tmp);
                }
                continue;
            }
            sbr.insert(0, c);
        }

        System.out.println(sbr.toString());
        return sbr.toString();
    }
    private static void testSolveRegex() {
        solveRegex("2[dd3[cc]]");
    }

    /**
     * 合并两个排序的链表
     * 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
     *
     * 思路
     * 1、新建一个结点作为新链表头部的前一个元素
     * 2、逐步遍历两个链表，加入小的元素为新链表的下一个节点
     * 3、新链表最后一个元素指向剩余的链表
     *
     */
    static Node mergeLinkedList(Node<Integer> a, Node<Integer> b) { // a: 递增； b: 递增
        if (null == a) {
            return b;
        }
        if (null == b) {
            return a;
        }

        Node<Integer> cur = new Node<>(0, null);
        for (; null != a && null != b;) {
            if (a.data > b.data) {
                cur.next = b;
                b = b.next;
            } else {
                cur.next = a;
                a = a.next;
            }
            cur = cur.next;
        }
        if (null != a) {
            cur.next = a;
        }
        if (null != b) {
            cur.next = b;
        }
        return cur.next;
    }

//    private static void testMergeLinkedList() {
//        Node<Integer> _
//        Node<Integer> a = new Node<>(1, null);
//        Node<Integer> b
//    }

    public static void main(String[] args) {
        testPrintSingleLinkedList();
        testReverseList();
        testSolveRegex();
    }
}
