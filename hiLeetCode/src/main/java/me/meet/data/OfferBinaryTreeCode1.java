package me.meet.data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class OfferBinaryTreeCode1 {
    private OfferBinaryTreeCode1() {}

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
     * 树的子结构
     *
     * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
     *
     * 思路1：
     * 1、层次遍历root1, 找到与root2相同的节点(此步骤非递归)
     * 2、找到后判断以此节点为根节点，是否能在root1中找到与root2相同的树结构(此处判断用递归查找)
     */
    static boolean hasSubtree(Node<Integer> n1, Node<Integer> n2) {
        if (null == n1 || null == n2) {
            return false;
        }
        Queue<Node<Integer>> queue = new LinkedList<>();
        queue.add(n1);

        for (; !queue.isEmpty();) {
            for (int size = queue.size(), i = 0; i < size; i++) {
                Node<Integer> cur = queue.poll();
                if (null == cur) {
                    continue;
                }
                if (null != cur.value && cur.value.equals(n2.value) || cur.value == n2.value) {
                    if (isSameTree(cur, n2)) { // 找到了即返回
                        return true;
                    }
                }

                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
        }
        return true;
    }

    static boolean hasSubtree0(Node<Integer> n1, Node<Integer> n2) {
        if (null == n1 || null == n2) {
            return false;
        }
        return isSameTree(n1, n2) || hasSubtree0(n1.left, n2) || hasSubtree0(n2.right, n2);
    }

    private static boolean isSameTree(Node<Integer> n1, Node<Integer> n2) {
        if (null == n1 && null == n2) {
            return true;
        }
        if (null == n1 || null == n2) {
            return false;
        }
        if (null == n1.value && null == n2.value) {
            return true;
        }
        if (null == n1.value || null == n2.value) {
            return false;
        }
        if (!n1.value.equals(n2.value)) {
            return false;
        }

        boolean isLeft = isSameTree(n1.left, n2.left);
        boolean isRight = isSameTree(n1.right, n2.right);
        return isLeft && isRight;
    }

    private static void testHasSubtree() {
        /**
         *      1
         *    /  \
         *   2    3
         *  / \
         * 4   5
         */
        Node<Integer> _5 = new Node<>(5, null, null);
        Node<Integer> _4 = new Node<>(4, null, null);
        Node<Integer> _3 = new Node<>(3, null, null);
        Node<Integer> _2 = new Node<>(2, _4, _5);
        Node<Integer> _1 = new Node<>(1, _2, _3);

        /**
         *   2
         *  / \
         * 4   5
         */
        Node<Integer> _5u = new Node<>(5, null, null);
        Node<Integer> _4u = new Node<>(4, null, null);
        Node<Integer> _2u = new Node<>(2, _4u, _5u);
        boolean res = hasSubtree(_1, _2u);
        System.out.println(res);

        boolean res2 = hasSubtree0(_1, _2u);
        System.out.println(res2);
    }

    /**
     * 操作给定的二叉树，将其变换为源二叉树的镜像。
     *
     * 输入描述:
     * 二叉树的镜像定义：
     * 源二叉树
     *     	     8
     *     	   /  \
     *     	  6   10
     *     	 / \  / \
     *     	5  7 9 11
     * 镜像二叉树
     *     	     8
     *     	   /  \
     *     	  10   6
     *     	 / \  / \
     *     	11 9 7  5
     * ————————————————
     *
     * 思路1：
     * 1、交换root节点的左右子树
     * 2、递归交换root.left和root.right的左右子树 
     *
     */
    static void mirrorTree(Node node) {
        if (null == node || (null == node.left && null == node.right)) {
            return;
        }

        Node tmp = node.left;
        node.left = node.right;
        node.right = tmp;

        mirrorTree(node.left);
        mirrorTree(node.right);
    }

    static void mirrorTree2(Node node) {
        if (null == node || (null == node.left && null == node.right)) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);

        for (; !queue.isEmpty();) {
//            for (int size = queue.size(), i = 0; i < size; i++) {
//
//            }
            Node cur = queue.poll();
            if (null == cur) {
                continue;
            }
            if (null != cur.left || null != cur.right) {
                Node tmp = cur.left;
                cur.left = cur.right;
                cur.right = tmp;
            }

            if (null != cur.left) {
                queue.add(cur.left);
            }
            if (null != cur.right) {
                queue.add(cur.right);
            }
        }

    }

    private static void testMirrorTree() {
        /**
         *      1
         *    /  \
         *   2    3
         *  / \
         * 4   5
         */
        Node<Integer> _5 = new Node<>(5, null, null);
        Node<Integer> _4 = new Node<>(4, null, null);
        Node<Integer> _3 = new Node<>(3, null, null);
        Node<Integer> _2 = new Node<>(2, _4, _5);
        Node<Integer> _1 = new Node<>(1, _2, _3);

        mirrorTree(_1);
        mirrorTree2(_1);
        System.out.println();
    }

    /**
     * 从上往下打印二叉树
     *
     * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
     *
     * 思路:
     * 1、利用队列进行层次遍历
     * 2、每次弹出队列中的一个元素，并把左右孩子加入队列即可
     */
    static void printFromTopToBottom(Node node) {
        if (null == node) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);

        for (; !queue.isEmpty();) {
            Node cur = queue.poll();
            System.out.print(cur.value + " ");
            if (null != cur.left) {
                queue.add(cur.left);
            }
            if (null != cur.right) {
                queue.add(cur.right);
            }
        }
    }

    private static void testPrintFromTopToBottom() {
        /**
         *   2
         *  / \
         * 4   5
         */
        Node<Integer> _5u = new Node<>(5, null, null);
        Node<Integer> _4u = new Node<>(4, null, null);
        Node<Integer> _2u = new Node<>(2, _4u, _5u);

        printFromTopToBottom(_2u);
    }

    /**
     * 二叉搜索树的后序遍历序列
     *
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
     * 如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
     *
     * 思路(递归)：
     * 1、后序遍历的特征为 根节点在序列的最后 值为rootVal
     * 2、序列上半部分的值都小于rootVal，下部分的值都大于rootVal
     * 3、递归判断上半部分、下半部分的序列，是否是树的后序遍历序列
     */
    static boolean verifySequenceOfBST(int[] sequence) {
        if (null == sequence || sequence.length < 1) {
            return false;
        }
        return verifySequenceOfBST(sequence, 0, sequence.length - 1);
    }

    private static boolean verifySequenceOfBST(int[] sequence, int begin, int end) {
        if (begin >= end) {             // 一个元素时，为后序遍历序列
            return true;
        }
        int rootVal = sequence[end];    // root节点的值
        int leftEnd = begin;            // 序列中的最后一个左子树节点
        int i = begin;

        while (sequence[i] < rootVal) { // 遍历找到左子树的序列 与 右子树序列, 获取分割索引
            leftEnd = i;
            i++;
        }
        while (i < end) {               // 判断leftEnd序列后的值,如果存在元素小于rootVal,则不是后序序列
            if (sequence[i] < rootVal) {
                return false;
            }
            i++;
        }
        return verifySequenceOfBST(sequence, begin, leftEnd) && verifySequenceOfBST(sequence, leftEnd + 1, end - 1);
    }

    private static void testVerifySequenceOfBST() {
        int[] sequence = {1, 2, 3, 4, 5};
        boolean res = verifySequenceOfBST(sequence);
        System.out.println(res);
    }

    /**
     * 二叉树中和为某一值的路径
     *
     * 题目描述：
     * 输入一颗二叉树的跟节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
     * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
     * (注意: 在返回值的list中，数组长度大的数组靠前)
     * 
     * 思路：
     *  1、用深度优先搜索DFS
     *  2、每当DFS搜索到新节点时，都要保存该节点。而且每当找出一条路径之后，都将这个保存到list的路径保存到最终结果二维list中
     *  3、并且，每当DFS搜索到子节点，发现不是路径和时，返回上一个结点时，需要把该节点从list中移除
     */
    static List<List<Integer>> findPath(Node<Integer> root, int target) {
        if (null == root) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new LinkedList<>();
        findPath(result, new LinkedList<>(), target, root);
        return result;
    }

    private static void findPath(List<List<Integer>> result, LinkedList<Integer> one, int target, Node<Integer> node) {
        if (null == node) {
            return;
        }
        one.add(node.value);

        int remainVal = target - node.value;
        if (0 == remainVal && null == node.left && null == node.right) {
            result.add(new LinkedList<>(one));
        }

        findPath(result, one, remainVal, node.left);
        findPath(result, one, remainVal, node.right);
        one.removeLast();
    }

    private static void testFindPath() {
        /**
         *      1
         *    /  \
         *   2    3
         *  / \
         * 4   5
         */
        Node<Integer> _5 = new Node<>(5, null, null);
        Node<Integer> _4 = new Node<>(4, null, null);
        Node<Integer> _3 = new Node<>(3, null, null);
        Node<Integer> _2 = new Node<>(2, _4, _5);
        Node<Integer> _1 = new Node<>(1, _2, _3);

        List<List<Integer>> res = findPath(_1, 8);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testHasSubtree();
        testMirrorTree();
        testPrintFromTopToBottom();
        testVerifySequenceOfBST();
        testFindPath();
    }
}
