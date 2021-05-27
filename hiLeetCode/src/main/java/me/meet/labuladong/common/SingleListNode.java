package me.meet.labuladong.common;

public class SingleListNode {
    public int val;
    public SingleListNode next;

    SingleListNode(int x, SingleListNode nt) {
        this.val = x;
        this.next = nt;
    }

    public void print() {
        printSingleListNode(this);
    }

    public static SingleListNode prepareSingleListNode0() {
        /*
         * head
         * 1  ->  2  ->  3  ->  4  ->  5  ->  6  ->  NULL
         */
        SingleListNode _6 = new SingleListNode(6, null);
        SingleListNode _5 = new SingleListNode(5, _6);
        SingleListNode _4 = new SingleListNode(4, _5);
        SingleListNode _3 = new SingleListNode(3, _4);
        SingleListNode _2 = new SingleListNode(2, _3);
        SingleListNode _1 = new SingleListNode(1, _2);
        return _1;
    }

    public static SingleListNode prepareSingleListNode1() {
        /*
         * head
         * 1  ->  2  ->  3  ->  4  ->  5  ->  NULL
         */
        SingleListNode _5 = new SingleListNode(5, null);
        SingleListNode _4 = new SingleListNode(4, _5);
        SingleListNode _3 = new SingleListNode(3, _4);
        SingleListNode _2 = new SingleListNode(2, _3);
        SingleListNode _1 = new SingleListNode(1, _2);
        return _1;
    }

    public static void printSingleListNode(SingleListNode head) {
        if (null == head) {
            System.out.println("head is null");
        }
        SingleListNode cur = head;
        for (; null != cur; ) {
            System.out.print(cur.val);
            if (null != cur.next) {
                System.out.print(" -> ");
            }

            cur = cur.next;
        }
        System.out.println();
    }
}