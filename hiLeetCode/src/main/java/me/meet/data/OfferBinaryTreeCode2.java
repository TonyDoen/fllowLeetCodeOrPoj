package me.meet.data;


import java.util.LinkedList;
import java.util.Queue;

public final class OfferBinaryTreeCode2 {
    private OfferBinaryTreeCode2() {}

    static class Node<T extends Comparable> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 把二叉树打印成多行
     * 从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。
     *
     * 思路1：
     * 1、初始化一个队列，初始元素为root
     * 2、遍历元素，每次首先获取当前队列的节点个数，即当前队列的size
     * 3、弹出size次元素，则本次遍历到的均为本层的元素
     * 4、每次弹出元素的同时，把元素的左右孩子加入队列，以便下次遍历
     *
     *
     *
     */
    static void levelPrint(Node node) {
        if (null == node) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);

        for (; !queue.isEmpty();) {
            for (int size = queue.size(), i = 0; i < size; i++) {
                Node cur = queue.poll();     // 弹出本层元素

                System.out.print(cur.value + " "); // print

                if (null != cur.left) {
                    queue.add(cur.left);
                }
                if (null != cur.right) {
                    queue.add(cur.right);
                }
            }
            System.out.println();
        }
    }

    private static void testLevelPrint() {
        /**
         *      4
         *    /  \
         *   2    5
         *  / \
         * 1   3
         */
        Node<Integer> _3 = new Node<>(3, null, null);
        Node<Integer> _1 = new Node<>(1, null, null);
        Node<Integer> _2 = new Node<>(2, _1, _3);
        Node<Integer> _5 = new Node<>(5, null, null);
        Node<Integer> _4 = new Node<>(4, _2, _5);

        levelPrint(_4);
    }

    /**
     * 二叉搜索树的第k个结点
     *
     * 给定一棵二叉搜索树，请找出其中的第k小的结点。
     * 例如， （5，3，7，2，4，6，8）    中，按结点数值大小顺序第三小结点的值为4。
     *
     * 思路
     * 1、二叉搜索树的中序遍历有序递增
     * 2、中序遍历二叉树(递归/非递归)，遍历到第k个元素即停止，则得到第k小元素
     *
     */
    static Node<Integer> kthInBST(Node<Integer> root, int k) {
        if (null == root || k <= 0) {
            return null;
        }
        LinkedList<Node<Integer>> cache = new LinkedList<>();
        helpKthInBST(cache, k, root);
        return k <= cache.size() ? cache.get(k - 1) : null;
    }

    private static void helpKthInBST(LinkedList<Node<Integer>> cache, int k, Node<Integer> node) {
        if (null == node) {
            return;
        }
        helpKthInBST(cache, k, node.left);
        cache.add(node);
        if (cache.size() == k) {
            return;
        }
        helpKthInBST(cache, k, node.right);
    }

    /**
     * 思路：
     * 1、非递归中序遍历，遍历到第k个元素停止遍历
     */
    static Node<Integer> kthInBST2(Node<Integer> root, int k) {
        if (null == root || k <= 0) {
            return null;
        }
        LinkedList<Node<Integer>> cache = new LinkedList<>();
        LinkedList<Node<Integer>> stack = new LinkedList<>();
        for (Node cur = root; !stack.isEmpty() || null != cur;) {
            for (; null != cur;) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            cache.add(cur);
            if (cache.size() == k) {
                break;
            }
            cur = cur.right;
        }
        return k <= cache.size() ? cache.get(k - 1) : null;
    }

    private static void testKthInBST() {
        /**
         *      4
         *    /  \
         *   2    5
         *  / \
         * 1   3
         */
        Node<Integer> _3 = new Node<>(3, null, null);
        Node<Integer> _1 = new Node<>(1, null, null);
        Node<Integer> _2 = new Node<>(2, _1, _3);
        Node<Integer> _5 = new Node<>(5, null, null);
        Node<Integer> _4 = new Node<>(4, _2, _5);

        Node<Integer> res = kthInBST(_4, 2);
        System.out.println(res.value);

        Node<Integer> res2 = kthInBST2(_4, 3);
        System.out.println(res2.value);
    }

    public static void main(String[] args) {
        testLevelPrint();
        testKthInBST();
    }
}
