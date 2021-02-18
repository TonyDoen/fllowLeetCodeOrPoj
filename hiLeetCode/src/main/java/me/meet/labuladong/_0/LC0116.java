package me.meet.labuladong._0;

import me.meet.labuladong.common.TreeNode;

public final class LC0116 {
    private LC0116() {
    }

    /**
     * LeetCode 116
     * 填充每个节点的下一个右侧节点指针
     *
     * 给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都有两个子节点。
     * struct Node {
     *     int val;
     *     Node *left;
     *     Node *right;
     *     Node *next;
     * }
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将next指针设置为NULL。
     * 初始状态下，所有next指针都被设置为NULL
     *
     * 例子1：
     *        4                              4 ---> nil
     *      /   \                          /   \
     *    2       7      ====>           2 ----> 7 ---> nil
     *  /  \    /  \                   /  \    /   \
     * 1    3  6    9                1 --> 3  6 --> 9 ---> nil
     *
     * 且题目说，输入是一颗[完美二叉树],
     * 形象地说整个二叉树是正三角形，除了最右侧的节点next指针会指向NULL，其他节点的右侧一定有相邻的节点
     *
     * 题目的要求细化到每个节点需要做的事情
     * 一个节点做不到[跨父节点]的两个相邻节点连接；就给安排2个节点，[将每一层二叉树节点连接] => [将每2个相邻节点都连接起来]
     *
     */
    static TreeNode connect(TreeNode node) {
        if (null == node) {
            return null;
        }
        connect2Node(node.getLeft(), node.getRight());
        return node;
    }

    // 输入2个节点，将他们连起来
    private static void connect2Node(TreeNode t1, TreeNode t2) {
        if (null == t1 || null == t2) {
            return ;
        }

        // 前序遍历的位置
        // 连接传入的2个节点
        t1.setNext(t2);

        // 连接相同父节点的2个子节点
        connect2Node(t1.getLeft(), t1.getRight());
        connect2Node(t2.getLeft(), t2.getRight());

        // 连接跨越父节点的2个子节点
        connect2Node(t1.getRight(), t2.getLeft());
    }

    private static void testConnectNode() {
        TreeNode node = TreeNode.prepareTree1();
        connect(node);
        node.println();
    }

    public static void main(String[] args) {
        testConnectNode();
    }
}
