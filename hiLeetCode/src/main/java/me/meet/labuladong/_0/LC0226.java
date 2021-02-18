package me.meet.labuladong._0;

import me.meet.labuladong.common.TreeNode;

public final class LC0226 {
    private LC0226() {
    }

    /**
     * LeetCode 226
     * 翻转二叉树
     * 例子1：
     *        4                              4
     *      /   \                          /   \
     *    2       7      ====>           7       2
     *  /  \    /  \                   /  \    /  \
     * 1    3  6    9                9     6  3    1
     *
     * 只要把二叉树上的每一个节点的左右子节点进行交换，最后结果就是完全翻转的二叉树
     *
     * 关键思路子阿与发现翻转整颗树就是交换每个节点的左右子节点，于是我们把交换左右子节点的代码放在前序遍历的位置。
     *
     * 把交换左右节点的方法放到后序遍历的位置也可以。放到中序遍历的地方不行，按照 左->根->右 的顺序 左侧树交换2次，右侧树没有交换
     *
     * 二叉树题目的难点就是把题目的要求细化成每个节点需要做的事情。
     */
    static TreeNode invertTree(TreeNode node) {
        // base case
        if (null == node) {
            return null;
        }

        // 前序遍历的位置
        // node 节点需要交换它的左右子节点
        TreeNode tmp = node.getLeft();
        node.setLeft(node.getRight());
        node.setRight(tmp);

        // 左右子节点继续翻转它们的子节点
        invertTree(node.getLeft());
        invertTree(node.getRight());
        return node;
    }

    private static void testInvertTree() {
        TreeNode node = TreeNode.prepareTree1();
        invertTree(node);
        node.println();
    }

    public static void main(String[] args) {
        testInvertTree();
    }
}
