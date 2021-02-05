package me.meet.labuladong._1;

import me.meet.labuladong.common.TreeNode;

public final class LC0114 {
    private LC0114() {
    }

    /**
     * LeetCode 114
     * 二叉树展开为链表
     *
     * 给定一个二叉树，原地将它展开为一个单链表。
     * 例如1：
     *        4
     *      /   \
     *    2       7      ====>           4 -> 2 -> 1 -> 3 -> 7 -> 6 -> 9
     *  /  \    /  \
     * 1    3  6    9
     *
     * 过程：
     *        4                              4                   4
     *      /   \                          /   \                  \
     *    2       7      ====>           2       7       ====>     2
     *  /  \    /  \                      \        \                \
     * 1    3  6    9                      1        6                1
     *                                      \        \                \
     *                                       3        9                3
     *                                                                  \
     *                                                                   7
     *                                                                    \
     *                                                                     6
     *                                                                      \
     *                                                                       9
     * 按照题目的意思把一颗树拉成一个链表，
     * 1. 将 node 的左子树和右子树拉平
     * 2. 将 node 的左子树后面连上右侧子树
     *
     * 后序遍历是因为需要拉平 node 的左右子树，才能继续node 的操作
     *
     */
    // 定义：将 node 为根的树拉平为链表
    static void flatten(TreeNode node) {
        // base case
        if (null == node) {
            return;
        }

        flatten(node.getLeft());
        flatten(node.getRight());

        // 后序遍历位置
        // 1. 左右子树已经被拉平成一个链表
        TreeNode left = node.getLeft();
        TreeNode right = node.getRight();

        // 2. 将左子树作为右侧子树
        node.setLeft(null);
        node.setRight(left);

        // 3. 将原来的右侧子树连接到当前右侧子树的末端
        TreeNode t = node;
        while (null != t.getRight()){
            t = t.getRight();
        }
        t.setRight(right);
    }

    private static void testFlatten() {
        TreeNode node = TreeNode.prepareTree4();
        flatten(node);
        node.println();
    }

    public static void main(String[] args) {
        testFlatten();
    }

}
