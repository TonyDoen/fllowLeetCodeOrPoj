package me.meet.labuladong.common;

import me.meet.data.offerStructure.Tree;

public class TreeNode {
    private Integer val;
    private TreeNode left;

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    private TreeNode right;

    public Integer getVal() {
        return val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public TreeNode(Integer val) {
        this.val = val;
    }

    public TreeNode(Integer val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode prepareTree1() {
        /**
         *         -1
         *       /   \
         *     2      -3
         *   /  \    /  \
         * 2     4  5   -8
         */
        TreeNode _2 = new TreeNode(2);
        TreeNode _4 = new TreeNode(4);
        TreeNode _5 = new TreeNode(5);
        TreeNode _8 = new TreeNode(-8);
        TreeNode _3 = new TreeNode(-3, _5, _8);
        TreeNode _2u = new TreeNode(2, _2, _4);
        TreeNode _1 = new TreeNode(-1, _2u, _3);
        return _1;
    }

    public void printTreeNode() {
        printTreeNode(this);
    }

    public static void printTreeNode(TreeNode node) {
        if (null == node) {
            return;
        }
//        System.out.println(String.format("{root:%s; left:%s; right:%s}", node.val, node.left.val));
        System.out.println(node.val); // 前序遍历
        printTreeNode(node.left);
        printTreeNode(node.right);

    }
}