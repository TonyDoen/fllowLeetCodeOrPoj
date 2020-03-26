package me.meet.data;

/**
 * url: https://blog.csdn.net/zangdaiyang1991/article/details/89339417
 * url: https://blog.csdn.net/zangdaiyang1991/article/details/90213738
 */
public final class OfferLinkedListCode2 {
    private OfferLinkedListCode2() {
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
     * 两个链表的第一个公共结点
     * 输入两个链表，找出它们的第一个公共结点。
     *
     * Y型:
     * 1 -> 2 -> 3
     *            \
     *             6 -> 7 -> 8
     *            /
     *      4 -> 5
     *
     * 思路：
     * 两个链表相交，因为链表元素只有一个指针，故相交后，后面都重合了，即相交只能是Y型，不能是X型相交
     *
     * 思路：
     * 分别遍历两个链表，获取长度。两个指针，一个先走|len1-len2|步，之后在同步遍历，获取第一个相同的元素即可。
     *
     * 思路：
     * 两个链表压入栈中，分别弹出元素，获取最后一个相同的元素即可
     *
     */
    static Node<Integer> findFirstCommonNode(Node<Integer> h1, Node<Integer> h2) {
        if (null == h1 || null == h2) {
            return null;
        }

        int len1 = 0;
        Node<Integer> cur1 = h1;
        for (; null != cur1; ) {
            len1++;
            cur1 = cur1.next;
        }

        int len2 = 0;
        Node<Integer> cur2 = h2;
        for (; null != cur2; ) {
            len2++;
            cur2 = cur2.next;
        }

        Node<Integer> fast = null, slow = null;
        int remain = 0;
        if (len1 > len2) {
            fast = h1;
            slow = h2;
            remain = len1 - len2;
        } else {
            fast = h2;
            slow = h1;
            remain = len2 - len1;
        }

        for (int i = 0; i < remain; i++) {
            fast = fast.next;
        }

        for (; null != fast && null != slow; ) {
            if (fast == slow) {
                return fast;
            }
            fast = fast.next;
            slow = slow.next;
        }

        return null;
    }

    private static void testFindFirstCommonNode() {
        /**
         * Y型:
         * 1 -> 2 -> 3
         *            \
         *             6 -> 7 -> 8
         *            /
         *      4 -> 5
         *
         */
        Node<Integer> _8 = new Node<>(8, null);
        Node<Integer> _7 = new Node<>(7, _8);
        Node<Integer> _6 = new Node<>(6, _7);
        Node<Integer> _3 = new Node<>(3, _6);
        Node<Integer> _2 = new Node<>(2, _3);
        Node<Integer> _1 = new Node<>(1, _2);

        Node<Integer> _5 = new Node<>(5, _6);
        Node<Integer> _4 = new Node<>(4, _5);

        Node<Integer> res = findFirstCommonNode(_1, _4);
        System.out.println(res.data);
    }

    /**
     * 链表中环的入口结点
     * 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
     *
     * 思路：
     * 1、快慢指针遍历链表，若相遇，则链表存在环
     * 2、一个指针从相遇的节点出发，一个从链表头部出发，两个指针相遇的位置即为环的入口节点
     *
     * 证明：
     * 1 -> 2 -> 3 -> 4 -> 5 -> 6
     *                |         |
     *                9 <- 8 <- 7
     *
     * 假设 slow  走 1步; fast 走 2步
     * 那么当 slow 和 fast 相遇时，既有  2 * slowDistance = fastDistance
     *
     * 令 链表头到环入口的长度(即上图 1 -> 2 -> 3 -> 4)            是 b
     *    环的长度(即上图 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 4)      是 c
     *    环入口 到 slow 和 fast 相遇点(即上图 4 -> 5 -> 6 -> 7) 是 a
     *    slow 和 fast 相遇点 到 环入口 的长度( -> 8 -> 9 -> 4)  是 c - a
     *
     * 计算
     * 2 * slowDistance    = fastDistance
     * 2 * (b + n * c + a) = (b + m * c + a)
     *                   b = (m-2n-1) * c + (c - a)
     *
     * 即：链表头到环入口的长度                 b
     *    slow 和 fast 相遇点 到 环入口 的长度 (c - a)
     *    相差 (m-2n-1)系数 个 完整的 环的长度  c
     *
     *    所以，思路中第2点得到证明
     *
     */
    static Node findEntryNodeOfLoop(Node head) {
        if (null == head || null == head.next) {
            return null;
        }
        // 1、快慢指针遍历链表，若相遇，则链表存在环
        // slow  走 1步; fast 走 2步
        Node meet = null, fast = head, slow = head;
        for (; null != slow && null != fast && null != fast.next; ) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                meet = slow;
                break;
            }
        }
        if (null == meet) { // no loop
            return null;
        }

        // 2、一个指针从相遇的节点出发，一个从链表头部出发，两个指针相遇的位置即为环的入口节点
        Node cur = head;
        for (; cur != meet; ) {
            cur = cur.next;
            meet = meet.next;
        }
        return cur;
    }

    private static Node<Integer> loop() {
        /**
         * 1 -> 2 -> 3 -> 4 -> 5 -> 6
         *                |         |
         *                9 <- 8 <- 7
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

        // loop
        _9.next = _4;
        return _1;
    }

    private static void testFindEntryNodeOfLoop() {
        Node<Integer> res = findEntryNodeOfLoop(loop());
        System.out.println(res.data);

    }

    /**
     * 链表中环的入口结点
     * 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
     *
     * 思路2：
     * 1、两个指针遍历链表，一个一次走一步，一个一次两步，能相交到一个点，则证明有环，相交点必在环上
     * 2、根据相交点，遍历一圈，再次到达原节点，即取得环的长度
     * 3、取得环的长度len后，一个指针先走len步，一个在一起走，第一次到达相同的节点即为环的起始节点
     */
    static Node findEntryNodeOfLoop2(Node head) {
        if (null == head || null == head.next) {
            return null;
        }
        // 1、快慢指针遍历链表，若相遇，则链表存在环
        // slow  走 1步; fast 走 2步
        Node meet = null, fast = head, slow = head;
        for (; null != slow && null != fast && null != fast.next; ) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                meet = slow;
                break;
            }
        }
        if (null == meet) { // no loop
            return null;
        }

        // 2、根据相交点，遍历一圈，再次到达原节点，即取得环的长度
        int loopLength = 0;
        Node cur = meet;
        do {
            cur = cur.next;
            loopLength++;
        } while (cur != meet);

        // 3、取得环的长度len后，一个指针先走len步，一个在一起走，第一次到达相同的节点即为环的起始节点
        cur = head;
        for (int i = 0; i < loopLength; i++) {
            cur = cur.next;
        }

        meet = head;
        for (; cur != meet; ) {
            cur = cur.next;
            meet = meet.next;
        }
        return cur;
    }

    private static void testFindEntryNodeOfLoop2() {
        Node<Integer> res = findEntryNodeOfLoop2(loop());
        System.out.println(res.data);
    }

    /**
     * 链表中环的入口结点
     * 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
     *
     * 思路3：
     * 1、断链法，可以改变原有链表结构时，可使用该方法
     * 2、两指针遍历链表，一个起始指向第二个节点，另一个指向头节点，后一个指针一直断链，并依次推进两个指针，当前一个指针为null时，后一个指针会指向环的入口节点
     */
    static Node findEntryNodeOfLoop3(Node head) {
        if (null == head || null == head.next) {
            return null;
        }
        Node slow = head, fast = head.next;
        for (; null != fast; ) {
            slow.next = null;
            slow = fast;
            fast = fast.next;
        }
        return slow;
    }

    private static void testFindEntryNodeOfLoop3() {
        Node<Integer> res = findEntryNodeOfLoop3(loop());
        System.out.println(res.data);
    }

    public static void main(String[] args) {
        testFindFirstCommonNode();
        testFindEntryNodeOfLoop();
        testFindEntryNodeOfLoop2();
        testFindEntryNodeOfLoop3();
    }
}
