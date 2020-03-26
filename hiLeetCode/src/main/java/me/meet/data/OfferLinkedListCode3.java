package me.meet.data;

/**
 * url: https://blog.csdn.net/zangdaiyang1991/article/details/88616832
 */
public final class OfferLinkedListCode3 {
    private OfferLinkedListCode3() {
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
     * 删除链表中重复的结点
     * 在一个排序的链表中,存在重复的结点,请删除该链表中重复的结点,重复的结点不保留,返回链表头指针。
     *
     * 例如,链表 1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5 处理后为 1 -> 2 -> 5
     *
     * 思路
     * 1、新建前驱节点,next指向pHead,方便处理头节点元素重复的场景
     * 2、双指针,一个pre指针指向前一个不重复的节点,一个cur指向当前遍历的节点,分情况处理
     * 3、当遍历到重复的节点时,pre指针的next指向当前重复节点的最后一个节点的next,即删除重复元素,cur指针后移
     * 4、当遍历到不重复的节点,只需要pre、cur指针同时后移即可
     * 
     */
    static Node deleteDuplicate(Node head) {
        if (null == head) {
            return null;
        }
        Node res = new Node(-1, head);           // 1、新建前驱节点,next指向head,方便处理头节点元素重复的场景
        Node pre = res, cur = head;                   // 2、双指针,一个pre指针指向前一个不重复的节点,一个cur指向当前遍历的节点,分情况处理
        for (; null != cur && null != cur.next; ) {
            if (cur.data == cur.next.data) {          // 3、当遍历到重复的节点时,pre指针的next指向当前重复节点的最后一个节点的next,即删除重复元素,cur指针后移
                for (; null != cur.next && cur.data == cur.next.data; ) {
                    cur = cur.next;
                }
                pre.next = cur.next;
            } else {                                  // 4、当遍历到不重复的节点,只需要pre、cur指针同时后移即可
                pre = cur;
            }
            cur = cur.next;
        }
        return res.next;
    }

    private static void testDeleteDuplicate() {
        Node src = provideLinkedNode();
        Node res = deleteDuplicate(src);
        for (; null != res;) {
            System.out.print(res.data + " ");
            res = res.next;
        }
        System.out.println();
    }

    private static Node<Integer> provideLinkedNode() {
        // 1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5
        Node<Integer> _5 = new Node<>(5, null);
        Node<Integer> _4d = new Node<>(4, _5);
        Node<Integer> _4 = new Node<>(4, _4d);
        Node<Integer> _3d = new Node<>(3, _4);
        Node<Integer> _3 = new Node<>(3, _3d);
        Node<Integer> _2 = new Node<>(2, _3);
        Node<Integer> _1 = new Node<>(1, _2);
        return _1;
    }

    /**
     * 删除链表中重复的结点
     * 在一个排序的链表中,存在重复的结点,请删除该链表中重复的结点,保留重复的结点第一个结点,返回链表头指针。
     *
     * 例如,链表 1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5 处理后为 1 -> 2 -> 3 -> 4 -> 5
     *
     * 思路：
     * 1、遍历链表,一个指针指向前驱节点  一个指向下一个节点
     * 2、返回新的链表(新链表,头结点引用)
     *
     */
    static Node deleteDuplicationRemainFirstOne(Node head) {
        if (null == head) {
            return null;
        }

        Node res = new Node(-1, head);
        Node pre = res, cur;
        for (; null != pre; ) {
            cur = pre.next;
            if (cur != null && pre.data == cur.data) { // 元素重复时 删除后续重复元素
                pre.next = cur.next;
            } else {                                   // 不重复时 pre后移一位
                pre = pre.next;
            }
        }

        return res.next;
    }

    private static void testDeleteDuplicationRemainFirstOne() {
        Node src = provideLinkedNode();
        Node res = deleteDuplicationRemainFirstOne(src);
        for (; null != res; ) {
            System.out.print(res.data + " ");
            res = res.next;
        }
        System.out.println();
    }

    /**
     * 删除链表中重复的结点
     * 在一个排序的链表中,存在重复的结点,请删除该链表中重复的结点,保留重复的结点最后一个结点,返回链表头指针。
     *
     * 例如,链表 1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5 处理后为 1 -> 2 -> 3 -> 4 -> 5
     *
     * 思路：
     * 1、遍历链表,一个指针指向前驱节点  一个指向下一个节点
     * 2、返回新的链表(新链表,头结点引用)
     *
     */
    static Node deleteDuplicationRemainLastOne(Node head) {
        if (null == head) {
            return null;
        }

        Node res = head;
        Node pre = res, cur = res.next;
        if (pre.data == cur.data) {               // 当头结点与下一结点值相同,需要删除头结点及后续相同节点
            while (pre.data == cur.data) {
                pre = pre.next;
                cur = cur.next;
            }
            res = pre;
        }

        for (; null != cur.next; ) {              // 头结点与下一结点值不同
            if (cur.data != cur.next.data) {
                pre = cur;
            } else {
                pre.next = cur.next;
            }
            cur = cur.next;
        }

        return res;
    }

    private static void testDeleteDuplicationRemainLastOne() {
        Node src = provideLinkedNode();
        Node res = deleteDuplicationRemainLastOne(src);
        for (; null != res; ) {
            System.out.print(res.data + " ");
            res = res.next;
        }
        System.out.println();
    }

    /**
     * 题意：
     * 给出一个链表,每 k 个节点一组进行翻转,并返回翻转后的链表。
     * k 是一个正整数,它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍,那么将最后剩余节点保持原有顺序。
     * 
     * 给定这个链表：1->2->3->4->5
     * 当 k = 2 时,应当返回: 2->1->4->3->5
     * 当 k = 3 时,应当返回: 3->2->1->4->5
     * 
     * 说明：
     * 1. 你的算法只能使用常数的额外空间。
     * 2. 你不能只是单纯的改变节点内部的值,而是需要实际的进行节点交换。
     *
     * 思路：
     * 1. 同翻转链表一致,边遍历链表,边翻转;只是需要继续新链表已经翻转的长度,到达k时,不再翻转
     * 2. 维护新链表的尾部元素指针
     */
    static Node reverseKStep(Node head, int k) {
        if (null == head || k < 2) {
            return head;
        }
        Node res = new Node(-1, head);
        Node pre = res, cur = head;
        int cnt = 0;
        for (; null != cur; ) {
            cnt++;
            Node next = cur.next;
            if (cnt == k) {
                pre = reverse(pre, next);
                cnt = 0;
            }
            cur = next;
        }
        return res;
    }

    private static Node reverse(Node pre, Node end) {
        if (null == pre || null == pre.next) {
            return pre;
        }
        Node head = pre.next;
        Node cur = pre.next.next;
        for (; cur != end; ) {
            Node next = cur.next; //缓冲cur后面的链表
            cur.next = pre.next;  //翻转单链表
            pre.next = cur;       //让pre.next的指针后移，类似于后移head
            cur = next;           //后移cur的指针
        }
        head.next = end;
        return head;
    }

    private static void testReverseKStep() {
        /**
         * 1 -> 2 -> 3 -> 4 -> 5 -> 7 -> 8 -> 9
         */
        Node<Integer> _9 = new Node<>(9, null);
        Node<Integer> _8 = new Node<>(8, _9);
        Node<Integer> _7 = new Node<>(7, _8);
        Node<Integer> _6 = new Node<>(6, _7);
        Node<Integer> _5 = new Node<>(5, _6);
        Node<Integer> _4 = new Node<>(4, _5);
        Node<Integer> _3 = new Node<>(3, _4);
        Node<Integer> _2 = new Node<>(2, _3);
        Node<Integer> _1 = new Node<>(1, _2);

        Node res = reverseKStep(_1, 3);
        for (; null != res; ) {
            System.out.print(res.data + " ");
            res = res.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
//        testDeleteDuplicate();
//        testDeleteDuplicationRemainFirstOne();
//        testDeleteDuplicationRemainLastOne();
//        testReverseKStep();

        double res = Math.pow(2, 31)/1024/1024/1024;
        System.out.println(res);
    }
}
