package me.meet.labuladong._0;

import me.meet.labuladong.common.TreeNode;

public final class LC0099 {
    private LC0099() {
    }

    /**
     * 【LeetCode】99. Recover Binary Search Tree 解题报告
     * 二叉搜索树中的两个节点被错误地交换。请在不改变其结构的情况下，恢复这棵树。
     */

    /**
     * 思路1：中序遍历将数值取出来，然后排序，然后再插入进入，使用的空间复杂度是O(n)
     *
     * 对一个BST进行中序遍历，结果是一个递增的序列。如果一个BST两个结点交换了，那么中序遍历的结果中对应结点的值也会交换。
     */
//    static void recoverBST(TreeNode root) {
//        if (null == root) {
//            return;
//        }
//        LinkedList<Integer> list = new LinkedList<>();
//
//    }
//
//    private static void inOrder(TreeNode node, LinkedList<Integer> list) {
//        if (null == node) {
//            return;
//        }
//        inOrder(node.getLeft(), list);
//        list.add(node.getVal());
//        inOrder(node.getRight(), list);
//    }
//
//    private static void chNode(TreeNode node, LinkedList<Integer> list) {
//        int i = 0;
//        Map<Integer, Integer> map = new HashMap<>();
//        for (Integer one : list) {
//            map.put(one, i++);
//        }
//        Collections.sort(list);
//
//        int firstVal, secondVal;
//        for (i = list.size()-1; i >= 0; i--) {
//            int val = list.get(i);
//            if (map.get(val) != i) {
//
//            }
//        }
//
//    }


    /**
     * 思路2：对树进行中序遍历，在遍历过程中如果发现上一个节点的值大于当前节点的值，则说明上一个节点是被交换了的。一共会有两个节点会发生上述的问题，最后交换这两个节点的值。
     */
    private static TreeNode first = null;
    private static TreeNode second = null;
    private static TreeNode last = new TreeNode(Integer.MIN_VALUE);

    static void recoverBST2(TreeNode node) {
        if (null == node) {
            return ;
        }
        inOrder(node);

        // swap
        int tmp = first.getVal();
        first.setVal(second.getVal());
        second.setVal(tmp);
    }

    private static void inOrder(TreeNode node) {
        if (null == node) {
            return;
        }

        inOrder(node.getLeft());

        if (null == first && last.getVal() > node.getVal()) {
            first = last;
        }
        if (null != first && last.getVal() > node.getVal()) {
            second = node;
        }
        last = node;

        inOrder(node.getRight());
    }
}
