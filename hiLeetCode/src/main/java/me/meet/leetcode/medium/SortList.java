package me.meet.leetcode.medium;

public final class SortList {
    static class Node {
        int val;
        Node next;

        Node(int x, Node next) {
            this.val = x;
            this.next = next;
        }
    }

    /**
     * Sort a linked list in O(n log n) time using constant space complexity.
     *
     * Example 1:
     * Input: 4->2->1->3
     * Output: 1->2->3->4
     *
     * Example 2:
     * Input: -1->5->3->4->0
     * Output: -1->0->3->4->5
     */
    /**
     * 题意：给链表排序
     *
     * 思路：
     * 由于时间复杂度要求o(n Log n),空间复杂度要求固定，则需要使用归并排序
     * 1、分解链表时，获取中间节点后，可以把中间节点的next指向null，便于后续获取中间节点及归并处理
     * 2、合并两个有序链表时，可定义要返回的新链表的头节点的pre节点，避免确定新链表头节点时的比较操作
     *
     * 时间复杂度要求O(n Log n)
     */
    static Node sortList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node mid = getMid(head);
        Node another;
        if (mid == null) {
            another = null;
        } else {
            another = mid.next;
            // 变原链表为两个独立的链表，很巧妙
            mid.next = null;
        }
        return mergeSort(sortList(head), sortList(another));
    }

    // 合并两个有序链表为一个链表
    private static Node mergeSort(Node first, Node second) {
        if (first == null && second == null) {
            return null;
        }
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }

        // 虚拟一个head的前缀节点，避免前缀额外的操作确定头节点
        Node pre = new Node(0, null);
        Node curNode = pre;

        Node cur1 = first;
        Node cur2 = second;
        while (cur1 != null && cur2 != null) {
            if (cur1.val <= cur2.val) {
                curNode.next = cur1;
                cur1 = cur1.next;
            } else {
                curNode.next = cur2;
                cur2 = cur2.next;
            }
            curNode = curNode.next;
        }

        // 处理剩余的元素
        if (cur1 != null) {
            curNode.next = cur1;
        }
        if (cur2 != null) {
            curNode.next = cur2;
        }

        return pre.next;
    }

    // 获取链表的中间节点
    private static Node getMid(Node head) { // 快慢指针
        if (head == null || head.next == null) {
            return head;
        }
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    private static void testSortList() {
        Node _3 = new Node(3, null);
        Node _1 = new Node(1, _3);
        Node _2 = new Node(2, _1);
        Node _4 = new Node(4, _2);
        Node tmp = sortList(_4);
        for (; null != tmp; ) {
            System.out.print(tmp.val + " ");
            tmp = tmp.next;
        }
        System.out.println();
    }

    /**
     * 插入排序
     *
     * 时间复杂度为 O(n2); 空间复杂度为 O(1)
     *
     * 思路：
     * 1、两层循环
     * 2、外层循环，从头节点开始，每次访问节点，内部循环找到后面元素的最小值，插入到当前位置
     * 3、内部循环，从当前外层节点的下一个节点开始，比较内外层节点的值，小的放到外层节点位置
     */
    static Node insertSort(Node head) {
        if (null == head || null == head.next) {
            return head;
        }
        Node cur = head;
        for (; null != cur; ) {          // 从头节点开始，每次访问节点，内部循环找到后面元素的最小值，插入到当前位置
            Node tmp = cur.next;
            for (; null != tmp; ) {
                if (cur.val > tmp.val) { // 遍历余下的节点，值比当前节点大，则交换值到当前位置
                    // swap
                    int t = cur.val;
                    cur.val = tmp.val;
                    tmp.val = t;
                }
                tmp = tmp.next;          // 推进内层循环节点
            }
            cur = cur.next;
        }
        return head;
    }

    private static void testInsertSort() {
        Node _3 = new Node(3, null);
        Node _1 = new Node(1, _3);
        Node _2 = new Node(2, _1);
        Node _4 = new Node(4, _2);
        Node tmp = insertSort(_4);
        for (; null != tmp; ) {
            System.out.print(tmp.val + " ");
            tmp = tmp.next;
        }
        System.out.println();
    }

    /**
     * 快速排序(Java)
     *
     * 时间复杂度为 O(n Log n);
     *
     * 思路：
     * 1、以头节点为base，依次遍历，与base比较，比base小的放前面；返回base指针
     * 2、递归调用上述过程
     */
    static Node quickSort(Node head) {
        if (null == head || null == head.next) {
            return head;
        }
        qSort(head, null);
        return head;
    }

    private static void qSort(Node begin, Node end) {
        if (begin != end && begin.next != end) {
            Node p = partition(begin, end);
            qSort(begin, p);
            qSort(p.next, end);
        }
    }

    private static Node partition(Node begin, Node end) {
        int baseVal = begin.val;
        Node base = begin, cur = begin.next;
        for (; cur != end; ) {
            if (cur.val < baseVal) {
                base = base.next;
                // swap (base, cur)
                int tmp = base.val;
                base.val = cur.val;
                cur.val = tmp;
            }
            cur = cur.next;
        }
        // swap (base, begin)
        int tmp = base.val;
        base.val = begin.val;
        begin.val = tmp;

        return base;
    }

    private static void testQuickSort() {
        Node _3 = new Node(3, null);
        Node _1 = new Node(1, _3);
        Node _2 = new Node(2, _1);
        Node _4 = new Node(4, _2);
        Node tmp = quickSort(_4);
        for (; null != tmp; ) {
            System.out.print(tmp.val + " ");
            tmp = tmp.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        testSortList();
        testInsertSort();
        testQuickSort();
    }
}
