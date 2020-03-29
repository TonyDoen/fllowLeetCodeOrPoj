package me.meet.data;

/**
 * url: https://blog.csdn.net/zangdaiyang1991/article/details/88616832
 */
public final class OfferLinkedListCode0 {
    private OfferLinkedListCode0() {
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
     * 单链表快速排序
     *
     * 思路：
     * 1、以头节点为base，依次遍历，与base比较，比base小的放前面；返回base指针
     * 2、递归调用上述过程
     *
     */
    static void quickSort(Node<Integer> begin, Node<Integer> end) {
        if (null == begin || null == begin.next) {
            return;
        }
        if (begin != end && begin.next != end) {
            Node<Integer> p = partition(begin, end);
            quickSort(begin, p);
            quickSort(p.next, end);
        }
    }

    private static Node<Integer> partition(Node<Integer> begin, Node<Integer> end) {
        int baseVal = begin.data;
        Node<Integer> base = begin, cur = begin.next;
        // 快速排序之单向划分方式
        for (; cur != end; ) {
            if (cur.data < baseVal) {
                base = base.next;
                // swap(base, cur);
                int tmp = base.data;
                base.data = cur.data;
                cur.data = tmp;
            }
            cur = cur.next;
        }
        // swap(base, begin);
        int tmp = base.data;
        base.data = begin.data;
        begin.data = tmp;

        return base;
    }

    private static void testQuickSort() {
        // 5 -> 4 -> 3 -> 2 -> 1
        Node<Integer> _1 = new Node<>(1, null);
        Node<Integer> _2 = new Node<>(2, _1);
        Node<Integer> _3 = new Node<>(3, _2);
        Node<Integer> _4 = new Node<>(4, _3);
        Node<Integer> _5 = new Node<>(5, _4);

        quickSort(_5, null);
        Node<Integer> cur = _5;
        for (; null != cur;) {
            System.out.print(cur.data + " ");
            cur = cur.next;
        }
        System.out.println();
    }


    public static void main(String[] args) {
        testQuickSort();
    }
}
