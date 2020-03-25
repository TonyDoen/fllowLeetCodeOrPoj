package me.meet.data;

import java.util.Arrays;
import java.util.LinkedList;

public class OfferBinaryTreeMaxDistance {
    static class Node {
        Object val;
        Node left;
        Node right;

        public Node() {
        }

        public Node(Object val) {
            this.val = val;
        }

        public Node(Object val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    static class Result {
        int maxDist; // 最远距离
        int maxDepth; // 最大深度

        public Result() {
        }

        public Result(int maxDist, int maxDepth) {
            this.maxDepth = maxDepth;
            this.maxDist = maxDist;
        }
    }


    /**
     * 找出二叉树中最远结点的距离
     * 二叉树中什么距离是最远距离。以根节点为轴，左右子树的最大深度？当然这只是一部分。准确地说，最大深度是以根节点为轴，左右子树的最大深度之和与以各个子树的根节点为轴左右子树的最大深度之和的较大值
     * <p>
     * 计算一个二叉树的最大距离有两个情况:
     * 情况A: 路径经过左子树的最深节点，通过根节点，再到右子树的最深节点。
     * 情况B: 路径不穿过根节点，而是左子树或右子树的最大距离路径，取其大者。
     * 只需要计算这两个情况的路径距离，并取其大者，就是该二叉树的最大距离。
     */
    public static Result maxDistance(Node root) {
        if (null == root) {
            return new Result(0, -1);
        }
        Result leftResult = maxDistance(root.left);
        Result rightResult = maxDistance(root.right);

        Result result = new Result();
        result.maxDepth = Math.max(leftResult.maxDepth, rightResult.maxDepth) + 1;
        result.maxDist = Math.max(Math.max(leftResult.maxDist, rightResult.maxDist), leftResult.maxDepth + rightResult.maxDepth + 2);

        return result;
    }


    /**
     * 题目描述
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
     * <p>
     * 先来分析一下前序遍历和中序遍历得到的结果，
     * 前序遍历第一位是根节点；
     * 中序遍历中，根节点左边的是根节点的左子树，右边是根节点的右子树。
     * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}。
     * <p>
     * 首先，根节点 是{ 1 }；
     * 左子树是：前序{ 2,4,7 } ，中序{ 4,7,2 }；
     * 右子树是：前序{ 3,5,6,8 } ，中序{ 5,3,8,6 }；
     * 这时，如果我们把左子树和右子树分别作为新的二叉树，则可以求出其根节点，左子树和右子树。
     * 这样，一直用这个方式，就可以实现重建二叉树。
     */
    // 主功能函数
    public static Node reConstructBinaryTree(int[] pre, int[] in) {
        if (pre == null || in == null) {
            return null;
        }
        Node tn = reConstructBinaryTreeCore(pre, in, 0, pre.length - 1, 0, in.length - 1);
        return tn;
    }

    // 核心递归
    public static Node reConstructBinaryTreeCore(int[] pre, int[] in, int preStart, int preEnd, int inStart,
                                                 int inEnd) {
        Node tree = new Node(pre[preStart]);
        tree.left = null;
        tree.right = null;
        if (preStart == preEnd && inStart == inEnd) {
            return tree;
        }
        int root = 0;
        for (root = inStart; root < inEnd; root++) {
            if (pre[preStart] == in[root]) {
                break;
            }
        }
        int leifLength = root - inStart;
        int rightLength = inEnd - root;
        if (leifLength > 0) {
            tree.left = reConstructBinaryTreeCore(pre, in, preStart + 1, preStart + leifLength, inStart, root - 1);
        }
        if (rightLength > 0) {
            tree.right = reConstructBinaryTreeCore(pre, in, preStart + 1 + leifLength, preEnd, root + 1, inEnd);
        }
        return tree;
    }

    /**
     * 判断一个二叉排序树是不是平衡二叉树
     */
    private static int depth(Node node) {
        if (null == node) {
            return 0;
        } else {
            int ld = depth(node.left);
            int rd = depth(node.right);
            return 1 + (ld > rd ? ld : rd);
        }
    }

    static boolean isBalance(Node root) {
        if (null == root) {
            return true;
        }
        int dist = Math.abs(depth(root.left) - depth(root.right));
        if (dist > 1) {
            return false;
        } else {
            return isBalance(root.left) && isBalance(root.right);
        }
    }

    /**
     * 判断一个二叉树是不是满二叉树
     * 思路：
     * 1.空树，满
     * 2.左满右满，且左右深度相等，满
     * 3.否则，非满
     */
    static boolean isFull(Node root) {
        if (null == root) {
            return true;
        }
        int lDepth = depth(root.left);
        int rDepth = depth(root.right);
        return isFull(root.left) && isFull(root.right) && (lDepth == rDepth);
    }

    private static void testIsFull() {
        /**
         *         1
         *       /  \
         *      2    3
         *     / \  / \
         *    4  5 6   7
         *        /
         *       8
         */
        Node _8 = new Node(8, null, null);
        Node _7 = new Node(7, null, null);
        Node _6 = new Node(6, _8, null);
        Node _5 = new Node(5, null, null);
        Node _4 = new Node(4, null, null);
        Node _3 = new Node(3, _6, _7);
        Node _2 = new Node(2, _4, _5);
        Node _1 = new Node(1, _2, _3);

        boolean res = isFull(_1);
        System.out.println(res);
    }

    /**
     * 判断一个二叉树是不是完全二叉树
     *
     * 思路：
     * 完全二叉树特点1：
     * 只允许最后一层有空缺结点且空缺在右边，即叶子结点只能在层次最大的两层上出现；
     *
     * 完全二叉树特点2：
     * 对任一结点，如果其右子树的深度为j，则其左子树的深度必为j或j+1 即度为1的点只有1个或0个
     *
     * 完全二叉树树主要有两点：
     * 1. 当一个结点有右孩子，但是没有左孩子，直接返回false
     * 2. 当一个节点有左孩子无右孩子，那么接下来要遍历的节点必须是叶子结点。（叶子结点左右孩子为空）
     */
    static boolean isCompleteBinaryTree(Node root) {
        if (null == root) {
            return true;
        }
        LinkedList<Node> list = new LinkedList<>();
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);

        for (; !queue.isEmpty(); ) { // 层序遍历
            for (int size = queue.size(); size > 0; size--) {
                Node cur = queue.pop();
                if (null != cur) {
                    queue.add(cur.left);
                    queue.add(cur.right);
                }
                list.push(cur); // 头插
            }
        }
        for (; ; ) { // 去掉最左 空 结点
            Node cur = list.peek();
            if (null == cur) {
                list.pop();
            } else {
                break;
            }
        }

        for (Node cur; !list.isEmpty();) {
            cur = list.pop();
            if (null == cur) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个二叉树是不是完全二叉树
     * 思路：
     * 1. 层序遍历(层次遍历)
     * 2. 层序遍历时 2种情况 可以判断不是完全二叉树
     *   （1） 1    左子树null；右子树不是null
     *         \
     *          2
     *
     *   （2）         1       右子树是null；后继结点必然是叶子结点
     *              /  \
     *             2    3
     *            / \
     *           4  5
     *          /
     *         6
     */
    static boolean isCompleteBinaryTree1(Node root) {
        if (null == root) {
            return true;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        boolean isBreak = false;
        for (; !queue.isEmpty(); ) { // 层序遍历
            for (int size = queue.size(); size > 0; size--) {
                Node cur = queue.pop();
                // （2）右子树是null；后继结点必然是叶子结点
                if (isBreak && (null != cur.left || null != cur.right)) {
                    return false;
                }
                if (null == cur.right) {
                    isBreak = true;
                }
                // （1）左子树null；右子树不是null
                if (null == cur.left && null != cur.right) {
                    return false;
                }

                if (null != cur.left) {
                    queue.add(cur.left);
                }
                if (null != cur.right) {
                    queue.add(cur.right);
                }
            }
        }
        return true;
    }

    private static void testIsCompleteBinaryTree() {
        /**
         *         1
         *       /  \
         *      2    3
         *     / \  / \
         *    4  5 6   7
         *        /
         *       8
         */
        Node _8 = new Node(8, null, null);
        Node _7 = new Node(7, null, null);
        Node _6 = new Node(6, _8, null);
        Node _5 = new Node(5, null, null);
        Node _4 = new Node(4, null, null);
        Node _3 = new Node(3, _6, _7);
        Node _2 = new Node(2, _4, _5);
        Node _1 = new Node(1, _2, _3);

        boolean res = isCompleteBinaryTree1(_1);
        System.out.println(res);

        boolean res1 = isCompleteBinaryTree1(_1);
        System.out.println(res1);
    }


    /**
     * 求数列里的最大差值
     * a[1], a[2], a[3], ..., a[n]; what is max{a[j] - a[i]}, when j>i, a[i] > 0
     */
    private static void maxRdx(int[] arr) {
        int len = arr.length;
        if (len < 1) {
            return;
        }
        int res = 0, min = arr[0];
        for (int i = 0; i < len - 1; i++) {
            int tmp1 = arr[i+1] - arr[i];
            int tmp2 = arr[i+1] - min;
            if (tmp2 > tmp1 && tmp2 > res) {
                res = tmp2;
            } else if (tmp1 > res) {
                res = tmp1;
            }

            if (min > arr[i+1]) {
                min = arr[i+1];
            }
        }

        System.out.println("arr:"+ Arrays.toString(arr) + "; res:" + res);
    }

    private static void checkMaxRdx() {
        int[] in = new int[]{4,7,2,1,5,3,8,6};
        maxRdx(in);
    }

    private static void checkReConstructBinaryTree() {
        int[] pre = new int[]{1,2,4,7,3,5,6,8};
        int[] in = new int[]{4,7,2,1,5,3,8,6};
        Node result = reConstructBinaryTree(pre, in);
        System.out.println(result);
    }

    private static Node buildTree() {
        /**
         *      0
         *     / |
         *    1   2
         *   /
         *  3
         */
        Node _3 = new Node(3, null, null);
        Node _1 = new Node(1, _3, null);
        Node _2 = new Node(2, null, null);
        Node root = new Node(0, _1, _2);
        return root;
    }

    private static void checkMaxDistance() {
        Node root = buildTree();
        Result result = maxDistance(root);
        System.out.println(result);
    }

    private static void checkIsBlance() {
        /**
         *      0
         *     / |
         *    1   2
         *   /
         *  3
         */
        Node _3 = new Node(3, null, null);
        Node _1 = new Node(1, _3, null);
        Node _2 = new Node(2, null, null);
        Node root = new Node(0, _1, _2);
        boolean result = isBalance(root);
        System.out.println("平衡否？" + result);
    }

    public static void main(String[] args) {
        checkMaxDistance();
        checkReConstructBinaryTree();
        checkMaxRdx();
        checkIsBlance();
        testIsCompleteBinaryTree();
        testIsFull();
    }
}
