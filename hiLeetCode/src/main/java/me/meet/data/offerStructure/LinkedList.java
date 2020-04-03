package me.meet.data.offerStructure;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public final class LinkedList {
    private LinkedList() {
    }

    static class Node<T> {
        T data;
        Node<T> next;
        Node<T> random;

        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        Node(T data, Node<T> next, Node<T> random) {
            this.data = data;
            this.next = next;
            this.random = random;
        }
    }

    /**
     * 003-从尾到头打印链表
     * 
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

        List<Integer> result = new java.util.LinkedList<>();
        while (!stack.empty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private static void testPrintSingleLinkedList() {
        /**
         * 4 -> 3 -> 2 -> 1
         */
        Node<Integer> _4 = _4321();

        List<Integer> res = printSingleLinkedList(_4);
        System.out.println(res);
    }

    /**
     * 014-链表中倒数第k个结点
     * 
     * 为了能够只遍历一次就能找到倒数第k个节点，可以定义两个指针：
     * （1）第一个指针从链表的头指针开始遍历向前走k-1，第二个指针保持不动；
     * （2）从第k步开始，第二个指针也开始从链表的头指针开始遍历；
     * （3）由于两个指针的距离保持在k-1，当第一个（走在前面的）指针到达链表的尾结点时，第二个指针（走在后面的）指针正好是倒数第k个结点。
     */
    static Node<Integer> findKth2Tail(Node<Integer> head, int k) {
        if (null == head || k < 0) {
            return null;
        }
        Node<Integer> first = head;
        for (int i = k; i > 0; i--) { // 先走k-1步
            if (null == first) {
                return null;
            }
            first = first.next;
        }

        Node<Integer> second = head;
        do {
            second = second.next;
            first = first.next;
        } while (null != first);
        return second;
    }

    private static void testFindKth2Tail() {
        /**
         * 4 -> 3 -> 2 -> 1
         */
        Node<Integer> _4 = _4321();

        Node<Integer> res = findKth2Tail(_4, 2);
        System.out.println(res.data);
    }

    /**
     * 015-反转链表
     * 
     * 输入一个链表，反转链表后，输出新链表的表头。
     * 
     * 1、只需遍历一次，遍历时拆开链表，当前元素指向前一个元素
     * 2、使用两个指针，一个指向当前遍历的节点，一个指向新链表的头结点
     */
    static Node reverseList(Node head) {
        Node result = null;
        for (Node cur = head; null != cur; ) {
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
        Node<Integer> _4 = _4321();

        Node result = reverseList(_4);
        printNode(result);
    }

    /**
     * 016-合并两个或k个有序链表
     * 
     * 合并k个有序链表，不管合并几个，基本还是要两两合并。
     * 1. 合并2个有序链表
     * 2. 依次合并多个
     */
    static Node<Integer> mergeKthLinkedList(List<Node<Integer>> lt) {
        if (null == lt || lt.isEmpty()) {
            return null;
        }
        Node<Integer> result = null;
        for (Node<Integer> node : lt) {
            result = merge2LinkedList(result, node);
        }
        return result;
    }

    /**
     * 合并两个排序的链表
     * 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
     * 
     * 思路
     * 1、新建一个结点作为新链表头部的前一个元素
     * 2、逐步遍历两个链表，加入小的元素为新链表的下一个节点
     * 3、新链表最后一个元素指向剩余的链表
     */
    static Node<Integer> merge2LinkedList(Node<Integer> l1, Node<Integer> l2) { // a: 递增； b: 递增
        if (null == l1) {
            return l2;
        }
        if (null == l2) {
            return l1;
        }

        Node<Integer> res = new Node<>(-1, null);
        Node<Integer> cur = res;

        Node<Integer> t1 = l1, t2 = l2;
        for (; null != t1 && null != t2; ) {
            if (t1.data > t2.data) {
                cur.next = t2;
                t2 = t2.next;
            } else {
                cur.next = t1;
                t1 = t1.next;
            }
            cur = cur.next;
        }
        if (null != t1) {
            cur.next = t1;
        }
        if (null != t2) {
            cur.next = t2;
        }
        return res.next;
    }

    private static void testMergeKthLinkedList() {
        List<Node<Integer>> lt = new java.util.LinkedList<>();
        for (int i = 0; i < 3; i++) {
            lt.add(reverseList(_4321()));
        }
        Node<Integer> result = mergeKthLinkedList(lt);
        printNode(result);
    }

    /**
     * 025-复杂链表的复制
     * 
     * 思路1.
     * 先复制直链，在复制随机指针。 (不用Hash表) 时间复杂度 O(n), 空间复杂度 O(1)。
     * 
     * 思路2.
     * 在复制随机指针时，可以用哈希表。          时间复杂度 O(n)；空间复杂度 O(n)。
     * 
     * 思路3.
     * 1. 遍历链表，复制每个节点并将该复制后的新节点放至旧节点之后
     * 2. 重新遍历链表，复制旧节点的随机指针给新节点
     * 3. 拆分链表，将链表拆分为原链表和复制后的链表
     * 
     * eg:
     * 第一遍： 1 -> 2 -> 3  ====>  1 -> 1' -> 2 -> 2' -> 3 -> 3'
     * 第二遍： 复制随机指针
     * 第三遍： 拆分原来的链表/clone链表
     */
    static Node<Integer> cloneComplexLinkedList(Node<Integer> node) {
        if (null == node) {
            return null;
        }
        // 1. 遍历链表，复制每个节点并将该复制后的新节点放至旧节点之后
        Node<Integer> cur = node;
        for (; null != cur; ) {
            Node<Integer> cp = new Node<>(cur.data, cur.next, cur.random);

            Node<Integer> next = cur.next;
            cur.next = cp;
            cur = next;
        }
        // 2. 重新遍历链表，复制旧节点的随机指针给新节点
        cur = node;
        for (; null != cur; ) {
            if (null == cur.random) {
                cur.next.random = null;
            } else {
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }
        // 3. 拆分链表，将链表拆分为原链表和复制后的链表
        cur = node;
        Node<Integer> cloneHead = cur.next;
        for (; null != cur; ) {
            Node<Integer> cloneCur = cur.next;
            if (null == cloneCur.next) {
                cur.next = null;
            } else {
                Node<Integer> next = cloneCur.next;
                cloneCur.next = cloneCur.next.next;
                cur.next = next;
            }
            cur = cur.next;
        }
        return cloneHead;
    }

    private static void testCloneComplexLinkedList() {
        /**
         * 4 -> 3 -> 2 -> 1
         */
        Node<Integer> _1 = new Node<>(1, null);
        Node<Integer> _2 = new Node<>(2, _1);
        Node<Integer> _3 = new Node<>(3, _2);
        Node<Integer> _4 = new Node<>(4, _3);
        _4.random = _1;
        _3.random = _4;
        _2.random = _3;
        _1.random = _4;

        Node<Integer> result = cloneComplexLinkedList(_4);
        printNode(result);
    }

    /**
     * 036-两个链表的第一个公共结点
     *
     * 输入两个链表，找出它们的第一个公共结点。
     *
     * Y型:
     * 1 -> 2 -> 3
     *            \
     *             6 -> 7 -> 8
     *            /
     *      4 -> 5
     *
     * 分析：
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
     * 055-链表中环的入口结点
     *
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
     * 056-删除链表中重复的结点
     *
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

    private static Node<Integer> _4321() {
        /**
         * 4 -> 3 -> 2 -> 1
         */
        Node<Integer> _1 = new Node<>(1, null);
        Node<Integer> _2 = new Node<>(2, _1);
        Node<Integer> _3 = new Node<>(3, _2);
        Node<Integer> _4 = new Node<>(4, _3);
        return _4;
    }

    private static void printNode(Node<Integer> result) {
        for (; null != result; ) {
            System.out.print(result.data + " ");
            result = result.next;
        }
        System.out.println();

    }

    public static void main(String[] args) {
        // 003-从尾到头打印链表
        testPrintSingleLinkedList();
        // 014-链表中倒数第k个结点
        testFindKth2Tail();
        // 015-反转链表
        testReverseList();
        // 016-合并两个或k个有序链表
        testMergeKthLinkedList();
        // 025-复杂链表的复制
        testCloneComplexLinkedList();
        // 036-两个链表的第一个公共结点
        testFindFirstCommonNode();
        // 055-链表中环的入口结点
        testFindEntryNodeOfLoop();
        // 056-删除链表中重复的结点
        testDeleteDuplicate();
        testDeleteDuplicationRemainFirstOne();
        testDeleteDuplicationRemainLastOne();
    }
}
