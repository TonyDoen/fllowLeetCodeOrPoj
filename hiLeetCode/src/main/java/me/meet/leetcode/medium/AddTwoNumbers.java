package me.meet.leetcode.medium;

public final class AddTwoNumbers {

    /**
     * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
     * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
     *
     * Example:
     * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
     * Output: 7 -> 0 -> 8
     * Explanation: 342 + 465 = 807.
     */

    /**
     * 题意：两个数字相加
     * 这道并不是什么难题，算法很简单，链表的数据类型也不难，就是建立一个新链表，然后把输入的两个链表从头往后撸，每两个相加，添加一个新节点到新链表后面。为了避免两个输入链表同时为空，我们建立一个 dummy 结点，将两个结点相加生成的新结点按顺序加到 dummy 结点之后，由于 dummy 结点本身不能变，所以用一个指针 cur 来指向新链表的最后一个结点。好，可以开始让两个链表相加了，这道题好就好在最低位在链表的开头，所以可以在遍历链表的同时按从低到高的顺序直接相加。while 循环的条件两个链表中只要有一个不为空行，由于链表可能为空，所以在取当前结点值的时候，先判断一下，若为空则取0，否则取结点值。然后把两个结点值相加，同时还要加上进位 carry。然后更新 carry，直接 sum/10 即可，然后以 sum%10 为值建立一个新结点，连到 cur 后面，然后 cur 移动到下一个结点。之后再更新两个结点，若存在，则指向下一个位置。while 循环退出之后，最高位的进位问题要最后特殊处理一下，若 carry 为1，则再建一个值为1的结点，代码如下：
     */
    static Node<Integer> addTwoNumbers(Node<Integer> l1, Node<Integer> l2) {
        Node<Integer> res = new Node<>(-1, null);
        Node<Integer> cur = res;
        int carry = 0;
        while (null != l1 || null != l2) {
            int d1 = l1 == null ? 0 : l1.item;
            int d2 = l2 == null ? 0 : l2.item;
            int sum = d1 + d2 + carry;
            carry = sum >= 10 ? 1 : 0;
            cur.next = new Node<>(sum%10, null);
            cur = cur.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        if (carry == 1) cur.next = new Node<>(1, null);
        return res.next;
    }
    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        Node<Integer> _3 = new Node<>(3, null);
        Node<Integer> _4 = new Node<>(4, _3);
        Node<Integer> _2 = new Node<>(2, _4);

        Node<Integer> _4_1 = new Node<>(4, null);
        Node<Integer> _6 = new Node<>(6, _4_1);
        Node<Integer> _5 = new Node<>(5, _6);

        Node<Integer> res = addTwoNumbers(_2, _5);
        Node<Integer> tmp = res;
        for (; null != tmp; ) {
            System.out.println(tmp.item);
            tmp = tmp.next;
        }
    }
}
